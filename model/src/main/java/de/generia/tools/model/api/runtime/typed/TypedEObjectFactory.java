package de.generia.tools.model.api.runtime.typed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectFactory;
import de.generia.tools.model.api.runtime.EObjectProxy;

public class TypedEObjectFactory implements EObjectFactory {
	private String modelPackageRoot;
	private Map<EClassifier, Class<?>> typeInterfaceMap = new HashMap<>();
	
	public TypedEObjectFactory() {
		this(null);
	}
	
	public TypedEObjectFactory(String modelPackageRoot) {
		this.modelPackageRoot = modelPackageRoot;
	}
	
	@Override
	public EObject createObject(EClass type) {
		Class<?> typeInterface = getTypeInterface(type);
		return (EObject) TypedEObject.create(typeInterface, type);
	}

	@Override
	public EObjectProxy createObjectProxy(EClass type, String id) {
		Class<?> typeInterface = getTypeInterface(type);
		return (EObjectProxy) TypedEObjectProxy.create(typeInterface, type, id);
	}

	@Override
	public Collection<Object> createCollection(EStructuralFeature feature) {
		return feature.isOrdered() ? new ArrayList<>() : new LinkedHashSet<>();
	}

	@Override
	public Object createEnum(EEnumLiteral literal) {
		Class<?> enumType = getTypeInterface(literal.getEnum());
		Object[] enumConstants= enumType.getEnumConstants();
		for (Object enumConstant : enumConstants) {
			if (enumConstant.toString().equals(literal.getName())) {
				return enumConstant;
			}
		}
		throw new IllegalArgumentException("can't create enum value for literal '" + literal.getName() + "' of enum '" + literal.getEnum().getName() + "'");
	}

	private Class<?> getTypeInterface(EClassifier type) {
		Class<?> typeInterface = typeInterfaceMap.get(type);
		if (typeInterface != null) {
			return typeInterface;
		}
		
		String className = getClassName(type);
		try {
			typeInterface = Class.forName(className);
			typeInterfaceMap.put(type, typeInterface);
			return typeInterface;
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("can't lookup type interface '" + className + "'", e);
		}
	}

	private String getClassName(EClassifier type) {
		EModelElement parent = type.getParent();
		String className;
		if (parent instanceof EPackage) {
			className = getPackageName((EPackage)parent) + "." + type.getName();
		} else if (parent instanceof EClass) {
			className = getClassName((EClass) parent) + "$" + type.getName();
		} else {
			throw new IllegalArgumentException("inconsistent api-model detected, parent of type '" + type.getName() + "' needs to be either package or class, but was '" + parent.getClass().getName() + "'");
		}		
		return className;
	}

	private String getPackageName(EPackage pkg) {
		String pkgName;
		if (pkg.getSuperPackage() == null) {
			if (modelPackageRoot != null) {
				pkgName = modelPackageRoot;
			} else {
				pkgName = pkg.getName();
			}
		} else {
			pkgName = getPackageName(pkg.getSuperPackage()) + "." + pkg.getName();
		}
		return pkgName;
	}
}
