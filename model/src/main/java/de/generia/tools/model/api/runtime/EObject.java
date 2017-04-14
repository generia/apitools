package de.generia.tools.model.api.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;

public class EObject {
	protected EClass type;
	protected String id;
	private Map<String, EStructuralFeature> structuralFeatureMap;
	
	private Map<String, Object> valueMap = new HashMap<>();
	
	public EObject() {
		this(null);
	}
	
	public EObject(EClass type) {
		if (type != null) {
			if (type.isAbstract() || type.isInterface()) {
				throw new IllegalArgumentException("can't instantiate type '" + typeName() + "'");
			}
			setType(type);
		} else {
			structuralFeatureMap = Collections.emptyMap();
		}
	}

	public EClass getType() {
		return type;
	}

	protected void setType(EClass type) {
		this.type = type;
		structuralFeatureMap = new LinkedHashMap<>();
		buildFeatureMap(type);
	}
	
	public String getId() {
		return id;
	}

	public Collection<EStructuralFeature> getStructuralFeatures() {
		return structuralFeatureMap.values();
	}

	public EStructuralFeature getFeature(String property) {
		return structuralFeatureMap.get(property);
	}
	
	public Object get(String property) {
		checkProperty(property);
		Object value = valueMap.get(property);
		return value;
	}
	
	public void set(String property, Object value) {
		EStructuralFeature feature = checkProperty(property);
		if (value == null) {
			valueMap.remove(property);
		} else {
			checkType(property, feature, value);
			valueMap.put(property, value);
			if (feature instanceof EAttribute) {
				EAttribute attribute = (EAttribute) feature;
				if (attribute.isId()) {
					id = value.toString();
				}
			}
		}
	}
	
	private String typeName() {
		return type.getPackage().getName() + "." + type.getName();
	}

	private void buildFeatureMap(EClass type) {
		if (type.getSuperType() != null) {
			buildFeatureMap(type.getSuperType());
		}
		for (EStructuralFeature feature : type.getStructuralFeatures()) {
			structuralFeatureMap.put(feature.getName(), feature);
		}
	}

	private EStructuralFeature checkProperty(String property) {
		if (property == null) {
			throw new IllegalArgumentException("property can't be null");
		}
		if (type == null) {
			return null;
		}
		EStructuralFeature feature = structuralFeatureMap.get(property);
		if (feature == null) {
			throw new IllegalArgumentException("property '" + property + "' does not exisit for type '" + typeName() + "'");
		}
		return feature;
	}

	private void checkType(String property, EStructuralFeature feature, Object value) {
		if (type == null) {
			return;
		}
		if (feature.isMany()) {
			if (feature.isOrdered()) {
				checkType(property, value, List.class);
			} else {
				checkType(property, value, Set.class);				
			}
		} else {
			EClassifier featureType = feature.getType();
			if (feature instanceof EReference) {
				// TODO: refine for explicit EObject type
				checkType(property, value, EObject.class);
			} else {
				if (!(featureType instanceof EDataType)) {
					throw new IllegalArgumentException("schema problem: 'attribute' features need to map to EDataType for property '" + property + "' in type '" + typeName() + "'");
				}
				// TODO check simple type in EDataType
			}
		}
		
	}

	private void checkType(String property, Object value, Class<?> type) {
		if (!type.isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException("can't assign type '" + value.getClass().getName() + "' to property '" + property + "' in type '" + typeName() + "', expected type '" + type.getName() + "'");
		}
	}
	
	@Override
	public int hashCode() {
		if (id == null) {
			return -1;
		}
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (!(object instanceof EObject)) {
			return false;
		}
		EObject other = (EObject) object;
		if (id == null || other.id == null) {
			return false;
		}
		return id.equals(other.id);
	}
}
