package de.generia.tools.model.api.generator.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.generia.tools.dom2table.marshaller.binding.ObjectBuilder;
import de.generia.tools.dom2table.model.Property;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.generator.table.model.EObjectObjectDriver;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectProxy;

public class TableTreeBuilder implements ObjectBuilder<EClassifier> {
	private static final String REF_PREFIX = "#";
	private static final String LIST_SEP = ",";
	static final String TYPE = "type";
	static final String LOCATION = "location";
	static final String ID = "id";
	static final String PARENT_TYPE = "parent-type";
	static final String CHILD_PROPERTY = "child-property";

	private EObjectObjectDriver objectDriver;
	private TableTreeDriver treeDriver;
	Object contextNode;
	private Object currentNode;
	private Map<String, EObject> namespace = new TreeMap<String, EObject>();
	private Map<String, List<EObjectProxy>> proxyHolderMap = new HashMap<String, List<EObjectProxy>>();

	public TableTreeBuilder(EObjectObjectDriver objectDriver, TableTreeDriver treeDriver) {
		this.objectDriver = objectDriver;
		this.treeDriver = treeDriver;
	}

	@Override
	public Object createObject(EClassifier type, Map<String, Object> metaInfo) {
		String typeName = (String) metaInfo.get(TYPE);
		String path = (String) metaInfo.get(ID);
		String location = (String) metaInfo.get(LOCATION);

		EClassifier typeClass = objectDriver.getTypeClass(typeName);
		currentNode = createChild(typeClass, location, contextNode);
		joinNamespace(path, (EObject) currentNode);
		return currentNode;
	}

	private void joinNamespace(String id, EObject object) {
		if (id == null) {
			return;
		}
		namespace.put(id, object);
		List<EObjectProxy> proxyHolders = proxyHolderMap.remove(id);
		if (proxyHolders != null) {
			for (EObjectProxy ph : proxyHolders) {
				ph.setDelegate(object);
			}
		}
	}

	@Override
	public void initMetaInfo(Object rowObject, Map<String, Object> metaInfo) {
		currentNode = rowObject;
		String typeName = objectDriver.getTypeName(objectDriver.getObjectType(rowObject));
		metaInfo.put(TYPE, typeName);
		metaInfo.put(ID, getId(rowObject));
		metaInfo.put(LOCATION, getLocation(rowObject));
		metaInfo.put(PARENT_TYPE, getParentType(rowObject));
		metaInfo.put(CHILD_PROPERTY, getChildProperty(rowObject));
	}

	@Override
	public void setProperty(Property<EClassifier> property, Object value) {
		EStructuralFeature feature = objectDriver.getProperty(property.getDefinedIn(), property.getName());
		if (feature == null) {
			return;
		}
		Object object = value;
		if (feature instanceof EReference && value instanceof String && value.toString().startsWith(REF_PREFIX)) {				
			EClass eClass = (EClass) property.getDefinedIn();
			if (feature.isMany()) {
				String[] parts = value.toString().split(LIST_SEP);
				Collection<Object> collection = treeDriver.getObjectFactory().createCollection(feature);
				for (String part : parts) {
					EObject ref = lookupReference(part, eClass);
					collection.add(ref);
				}
				object = collection;
			} else {
				object = lookupReference(value.toString(), eClass);
			}						
		}
		objectDriver.setValue(currentNode, property.getName(), object);
	}

	private EObject lookupReference(String value, EClass typeClass) {
		String id = value.toString().substring(1);
		EObject object = namespace.get(id);
		if (object == null) {
			List<EObjectProxy> proxyHolders = proxyHolderMap.get(id);
			if (proxyHolders == null) {
				proxyHolders = new ArrayList<EObjectProxy>();
				proxyHolderMap.put(id, proxyHolders);
			}
			EObjectProxy proxy = treeDriver.getObjectFactory().createObjectProxy(typeClass, id);
			proxyHolders.add(proxy);
			object = proxy;
		}
		return object;
	}

	@Override
	public Object getProperty(Property<EClassifier> property) {
		EClassifier currentType = objectDriver.getObjectType(currentNode);
		if (!objectDriver.hasProperty(currentType, property.getName())) {
			return null;
		}
		Object value = objectDriver.getValue(currentNode, property.getName());
		if (value != null) {
			EClassifier propertyType = objectDriver.getPropertyType(property.getDefinedIn(), property.getName());
			if (objectDriver.isObjectType(propertyType)) {
				if (value instanceof Iterable) {
					StringBuilder paths = new StringBuilder();
					@SuppressWarnings("unchecked")
					Iterable<Object> values = (Iterable<Object>) value;;
					String sep = "";
					for (Object v : values) {
						paths.append(sep).append(REF_PREFIX).append(getId(v));
						sep = LIST_SEP;
					}
					return paths.toString();
				} else {
					return REF_PREFIX + getId(value);
				}
			}
		}
		return value;
	}

	@Override
	public EClassifier getPropertyType(Property<EClassifier> property) {
		if (property.getDefinedIn() == null) {
			return null;
		}
		return objectDriver.getPropertyType(property.getDefinedIn(), property.getName());
	}

	@Override
	public EClassifier getObjectType() {
		return objectDriver.getObjectType(currentNode);
	}

	@Override
	public boolean hasProperty(Property<EClassifier> property) {
		EClassifier objectType = getObjectType();
		if (!objectDriver.isAssignableFrom(property.getDefinedIn(), objectType)) {
			return false;
		}
		return objectDriver.hasProperty(objectType, property.getName());
	}
	
	private Object createChild(EClassifier type, String location, Object context) {
		Object parent = getObjectByLocation(context, location);
		if (parent == null) {
			return context;
		}
		Object child = objectDriver.create(type);
		treeDriver.addChild(parent, child);
		return child;
	}

	private Object getObjectByLocation(Object context, String location) {
		if (location == null) {
			return null;
		}
		String[] steps = location.split("\\.");
		Object o = context;
		for (String step : steps) {
			int index = Integer.parseInt(step);
			List<Object> children = treeDriver.getChildren(o);
			if (index < children.size()) {
				Object child = children.get(index);
				o = child;
			} else {
				return o;
			}
		}
		return o;
	}

	private String getLocation(Object object) {
		String location = null;
		Object o = object;
		while (o != null) {
			int index = treeDriver.getChildrenIndex(o);
			if (index != -1) {
				String format = String.format("%03d", index);
				location = location == null ? format : format + "." + location;
			}
			o = treeDriver.getParent(o);
		}
		return location;
	}

	private String getId(Object object) {
		String id = null;
		EObject o = (EObject) object;
		if (o != null) {
			id = o.eGetId();
		}
		return id;
	}

	private Object getParentType(Object rowObject) {
		EObject parent = (EObject) treeDriver.getParent(rowObject);
		if (parent == null) {
			return null;
		}
		return objectDriver.getTypeName(parent.eGetType());
	}

	private String getChildProperty(Object rowObject) {
		EReference childReference = treeDriver.getParentChildReference(rowObject);
		if (childReference == null) {
			return null;
		}
		return childReference.getName();
	}
}