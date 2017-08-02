package de.generia.tools.model.api.generator.table.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.generia.tools.dom2table.model.ObjectDriver;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EPackageManager;
import de.generia.tools.model.api.runtime.generic.GenericEObject;

public class EObjectObjectDriver implements ObjectDriver<EClassifier> {
	EPackageManager packageManager;
	
	public EObjectObjectDriver(EPackageManager packageManager) {
		this.packageManager = packageManager;
	}
	
	@Override
	public EClassifier getTypeClass(String typeName) {
		Object element = packageManager.lookupElement(typeName);
		if (!(element instanceof EClassifier)) {
			throw new IllegalArgumentException("can't get type class for type '" + typeName + "'");
		}
		return (EClassifier) element;
	}

	@Override
	public String getTypeName(EClassifier typeClass) {
		return typeClass.getName();
	}

	@Override
	public boolean isObjectType(EClassifier type) {
		return type instanceof EClass;
	}

	@Override
	public boolean isAssignableFrom(EClassifier targetType, EClassifier type) {
		if (targetType == null || type == null) {
			return false;
		}
		return isSupertypeOf(targetType, type);
	}

	@Override
	public boolean isSupertypeOf(EClassifier supertype, EClassifier type) {
		if (!(supertype instanceof EClass) && !(type instanceof EClass)) {
			return false;
		}
		EClass t = (EClass) type;
		while (t != null) {
			if (t.equals(supertype)) {
				return true;
			}
			t = t.getSuperType();
		}
		return false;
	}

	@Override
	public EClassifier getSupertype(EClassifier type) {
		if (!(type instanceof EClass)) {
			return null;
		}
		return ((EClass) type).getSuperType();
	}

	@Override
	public EClassifier[] getSupertypes(EClassifier type) {
		EClassifier supertype = getSupertype(type);
		if (supertype == null) {
			return null;
		}
		return new EClassifier[]{supertype};
	}

	@Override
	public boolean isManyProperty(EClassifier type, String property) {
		if (!(type instanceof EClass)) {
			return false;
		}
		for (EStructuralFeature feature : ((EClass)type).getStructuralFeatures()) {
			if (feature.getName().equals(property)) {
				return feature.isMany();
			}
		}
		return false;
	}

	@Override
	public boolean hasProperty(EClassifier type, String property) {
		if (!(type instanceof EClass)) {
			return false;
		}
		for (EStructuralFeature feature : ((EClass)type).getStructuralFeatures()) {
			if (feature.getName().equals(property)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String[] getProperties(EClassifier type) {
		if (!(type instanceof EClass)) {
			return new String[0];
		}
		List<String> propeties = new ArrayList<>();
		for (EStructuralFeature feature : ((EClass)type).getStructuralFeatures()) {
			propeties.add(feature.getName());
		}
		return (String[]) propeties.toArray(new String[propeties.size()]);
	}

	@Override
	public EClassifier getPropertyType(EClassifier type, String property) {
		if (!(type instanceof EClass)) {
			return null;
		}
		for (EStructuralFeature feature : ((EClass)type).getStructuralFeatures()) {
			if (feature.getName().equals(property)) {
				return feature.getType();
			}
		}
		return null;
	}

	@Override
	public Object create(EClassifier type) {
		if (!(type instanceof EClass)) {
			throw new IllegalArgumentException("can't create object of type '" + type + "'");
		}
		return new GenericEObject((EClass) type);
	}

	@Override
	public EClass getObjectType(Object object) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("can't get object type of object '" + object + "'");
		}
		return ((EObject)object).eGetType();
	}

	@Override
	public boolean isInstanceOf(Object object, EClassifier type) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("can't get instance of object '" + object + "', expected '" + EObject.class.getName() + "' but got '" + object.getClass().getName() + "'");
		}
		return isAssignableFrom(((EObject)object).eGetType(), type);
	}

	@Override
	public Object getValue(Object object, String property) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("can't get value of object '" + object + "', expected '" + EObject.class.getName() + "' but got '" + object.getClass().getName() + "'");
		}
		return ((EObject)object).get(property);
	}

	@Override
	public void setValue(Object object, String property, Object value) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("can't set value of object '" + object + "', expected '" + EObject.class.getName() + "' but got '" + object.getClass().getName() + "'");
		}
		((EObject)object).set(property, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Object> getValues(Object object, String property) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("can't get values of object '" + object + "', expected '" + EObject.class.getName() + "' but got '" + object.getClass().getName() + "'");
		}
		EObject o = (EObject) object;
		EStructuralFeature feature = o.eGetFeature(property);
		if (!feature.isMany()) {
			throw new IllegalArgumentException("can't get values of object '" + object + "' and property '" + property + "', expected 'many' feature but is not");
		}
		return (Iterable<Object>) o.get(property);
	}

	@Override
	public void addValue(Object object, String property, Object value) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("can't add value of object '" + object + "', expected '" + EObject.class.getName() + "' but got '" + object.getClass().getName() + "'");
		}
		EObject o = (EObject) object;
		EStructuralFeature feature = o.eGetFeature(property);
		if (!feature.isMany()) {
			throw new IllegalArgumentException("can't add value of object '" + object + "' and property '" + property + "', expected 'many' feature but is not");
		}
		@SuppressWarnings("unchecked")
		Collection<Object> values = (Collection<Object>) o.get(property);
		values.add(value);
	}
}
