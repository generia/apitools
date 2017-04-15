package de.generia.tools.model.api.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;

public class EObject implements Map<String, Object> {
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
		String name = type.getName();
		if (type.getPackage() != null) {
			name = type.getPackage().getName() + "." + name;
		}
		return name;
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

	@Override
	public int size() {
		return valueMap.size();
	}

	@Override
	public boolean isEmpty() {
		return valueMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return valueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return valueMap.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return this.get((String)key);
	}

	@Override
	public Object put(String key, Object value) {
		Object oldValue = valueMap.get(key);
		set(key, value);
		return oldValue; 
	}

	@Override
	public Object remove(Object key) {
		return put((String)key, null);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for (Map.Entry<? extends String, ? extends Object> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		valueMap.clear();
	}

	@Override
	public Set<String> keySet() {
		if (type == null) {
			return valueMap.keySet();
		}
		return structuralFeatureMap.keySet();
	}

	@Override
	public Collection<Object> values() {
		if (type == null) {
			return valueMap.values();
		}
		Collection<Object> values = new ArrayList<>();
		for (String feature : structuralFeatureMap.keySet()) {
			Object value = get(feature);
			if (value != null) {
				values.add(value);
			}
		}
		return values;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		if (type == null) {
			return valueMap.entrySet();
		}
		Set<Entry<String,Object>> entrySet = new LinkedHashSet<>();
		for (String feature : structuralFeatureMap.keySet()) {
			Object value = get(feature);
			if (value != null) {
				Entry<String, Object> entry = new MapEntry(feature, value);
				entrySet.add(entry);
			}
		}
		return entrySet;
	}
	
	private static class MapEntry implements Entry<String, Object> {
		
		private String key;
		private Object value;
		
		public MapEntry(String key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public Object setValue(Object value) {
			Object oldValue = value;
			this.value = value;
			return oldValue;
		}
	}
}
