package de.generia.tools.model.api.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EStructuralFeature;

public class EPackageManager {
	private static final EAttribute NOT_EXISTING = new EAttribute();

	private String schemaId;
	private EPackage pkg;
	private EObjectFactory objectFactory;
	private Map<String, EModelElement> nameElementMap = new HashMap<>();
	private Map<String, EModelElement> aliasElementMap = new HashMap<>();
	private Map<EModelElement, String> elementNameMap = new HashMap<>();
	private Map<EClass, List<EClass>> subTypesMap = new HashMap<>();
	protected Map<EClass, EAttribute> typeIdAttributedMap = new HashMap<>();


	public EPackageManager(String schemaId, EPackage pkg) {
		this(schemaId, pkg, null);
	}

	public EPackageManager(String schemaId, EPackage pkg, EObjectFactory objectFactory) {
		this.schemaId = schemaId;
		this.pkg = pkg;
		this.objectFactory = objectFactory;
		init(pkg, "");
	}

	public String getSchemaId() {
		return schemaId;
	}
	
	public EPackage getPackage() {
		return pkg;
	}
	
	public EObjectFactory getObjectFactory() {
		return objectFactory;
	}
	
	public void setObjectFactory(EObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T lookupElement(String name) {
		T element = (T) nameElementMap.get(name);
		if (element == null) {
			element = (T) aliasElementMap.get(name);
		}
		return element;
	}
	
	public EObject create(String type) {
		EModelElement element = getTypeChecked(type, EClass.class);
		EClass clazz = (EClass) element;
		if (objectFactory != null) {
			return objectFactory.createObject(clazz);
		}
		return new EObject(clazz);
	}

	@SuppressWarnings("unchecked")
	private <T> T getTypeChecked(String type, Class<T> expectedType) {
		EModelElement element = lookupElement(type);
		if (element == null) {
			throw new IllegalArgumentException("can't find type for given name '" + type + "'");
		}
		if (!expectedType.isAssignableFrom(element.getClass())) {
			throw new IllegalArgumentException("type mismatch, expected '" + expectedType.getName() + "' model-element for given type '" + type + "', but got '" + element.getClass().getName() + "'");
		}
		return (T) element;
	}
	
	public boolean isInstance(EObject object, String type) {
		if (object.eGetType() == null) {
			return false;
		}
		EClass objectType = object.eGetType();
		EClass expectedType = object.eGetType();
		try {
			expectedType = getTypeChecked(type, EClass.class);
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		while (objectType != null) {
			if (objectType.equals(expectedType)) {
				return true;
			}
			objectType = objectType.getSuperType();
		}
		return false;
	}
	
	private void init(EModelElement element, String path) {
		if (element instanceof EPackage) {
			initPackage((EPackage)element, path);
		} else if (element instanceof EClass) {
			initClass((EClass) element, path);
		} else if (element instanceof EOperation) {
			initOperation((EOperation) element, path);
		} else if (element instanceof ENamedElement) {
			initNamedElement((ENamedElement) element, path);
		}
	}

	private void initOperation(EOperation element, String path) {
		String name = initNamedElement(element, path);
		for (EParameter parameter : element.getParameters()) {
			init(parameter, name);
		}
	}

	private void initClass(EClass element, String path) {
		String name = initNamedElement(element, path);
		EClass superType = element.getSuperType();
		if (superType != null) {
			List<EClass> subTypes = subTypesMap.get(superType);
			if (subTypes == null) {
				subTypes = new ArrayList<>();
				subTypesMap.put(superType, subTypes);
			}
			subTypes.add(element);
		}
		for (EStructuralFeature feature : element.getStructuralFeatures()) {
			init(feature, name);
		}
		for (EClassifier classifier : element.getNestedClassifiers()) {
			init(classifier, name);
		}
	}

	private void initPackage(EPackage element, String path) {
		String name = initNamedElement(element, path);
		for (EClassifier classifier : element.getClassifiers()) {
			init(classifier, name);
		}
		for (EPackage subPackage : element.getSubPackages()) {
			init(subPackage, name);
		}
	}

	private String initNamedElement(ENamedElement element, String path) {
		String name = path + "/" + element.getName();
		nameElementMap.put(name, element);
		elementNameMap.put(element, name);
		initAliasMap(name, element);
		return name;
	}

	private void initAliasMap(String name, ENamedElement element) {
		String dottedName = name.replace('/', '.');
		if (dottedName.startsWith(".")) {
			dottedName = dottedName.substring(1);
		}
		aliasElementMap.put(dottedName, element);
		aliasElementMap.put(element.getName(), element);
	}

	public List<EClass> getSubTypes(EClass type) {		
		return subTypesMap.get(type);
	}

	public String getElementName(EModelElement type) {
		return elementNameMap.get(type);
	}

	public EAttribute getIdAttribute(EClass type) {
		EAttribute attribute = typeIdAttributedMap.get(type);
		if (attribute != null) {
			return attribute != NOT_EXISTING ? attribute : null;
		}
		
		EClass t = type;
		while (t != null) {
			for (EStructuralFeature feature : t.getStructuralFeatures()) {
				if (feature instanceof EAttribute) {
					attribute = (EAttribute) feature;
					if (attribute.isId()) {
						typeIdAttributedMap.put(type, attribute);
						return attribute;
					}
					
				}
			}
			t = t.getSuperType();
		}
		typeIdAttributedMap.put(type, NOT_EXISTING);
		return null;
	}
}
