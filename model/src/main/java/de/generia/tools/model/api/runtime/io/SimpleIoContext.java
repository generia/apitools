package de.generia.tools.model.api.runtime.io;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectFactory;
import de.generia.tools.model.api.runtime.EPackageManager;

public class SimpleIoContext implements EObjectWriter.Context, EObjectReader.Context {

	private static final String TYPE_HINT_PROPERTY = "@type";

	private EPackageManager packageManager;

	private EObjectFactory objectFactory;
	
	private boolean useContainmentFlag;
	
	public SimpleIoContext(EPackageManager packageManager) {
		this(packageManager, new SimpleObjectFactory(), true);
	}
	
	public SimpleIoContext(EPackageManager packageManager, EObjectFactory objectFactory, boolean useContainmentFlag) {
		this.packageManager = packageManager;
		this.objectFactory = objectFactory;
		this.useContainmentFlag = useContainmentFlag;
	}
	
	public void setUseContainmentFlag(boolean useContainmentFlag) {
		this.useContainmentFlag = useContainmentFlag;
	}
	
	@Override
	public EObjectFactory getObjectFactory() {
		return objectFactory;
	}
	
	@Override
	public String getObjectId(EObject object) {
		EAttribute idAttribute = getIdAttribute(object.eGetType());
		if (idAttribute == null) {
			return null;
		}
		String id = (String) object.get(idAttribute.getName());
		return id;
	}

	@Override
	public EAttribute getIdAttribute(EClass type) {
		return packageManager.getIdAttribute(type);
	}

	@Override
	public boolean isAggregationFeature(EStructuralFeature feature) {
		if (useContainmentFlag) {
			if (feature instanceof EReference) {
				EReference reference = (EReference) feature;
				return reference.isContainment();
			}
		}
		return true;
	}

	@Override
	public boolean needsTypeHint(EStructuralFeature feature) {
		if (feature == null) {
			return false;
		}
		EClassifier type = feature.getType();
		if (!(type instanceof EClass)) {
			return false;
		}
		List<EClass> subTypes = packageManager.getSubTypes((EClass)type);
		return subTypes != null && !subTypes.isEmpty();
	}

	@Override
	public String getTypeHintProperty() {
		return TYPE_HINT_PROPERTY;
	}

	@Override
	public EClassifier getType(String typeHint) {
		return packageManager.lookupElement(typeHint);
	}

	@Override
	public String getTypeHint(EClass type) {
		return packageManager.getElementName(type);
	}

	@Override
	public Collection<EStructuralFeature> getFilteredFeatures(EObject object, Stack<EStructuralFeature> featureStack) {
		if (!useContainmentFlag || featureStack.isEmpty()) {
			return object.getStructuralFeatures();
		}
		EStructuralFeature activeFeature = featureStack.peek();
		if (activeFeature instanceof EReference) {
			EReference reference = (EReference) activeFeature;
			if (reference.isContainment()) {
				return object.getStructuralFeatures();
			}
			return Collections.emptyList();
		}
		throw new IllegalArgumentException("can't filter features for object '" + object.eGetId() + "' of type '" + object.eGetType().getName() + "' due to inconsistent feature-stack '" + featureStack + "', expected reference feature, but got attribute '" + activeFeature.getName() + "'");
	}
}
