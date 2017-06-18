package de.generia.tools.model.api.runtime.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectFactory;
import de.generia.tools.model.api.runtime.EObjectProxy;

public class GenericEObjectFactory implements EObjectFactory {

	@Override
	public EObject createObject(EClass type) {
		return new GenericEObject(type);
	}

	@Override
	public EObjectProxy createObjectProxy(EClass type, String id) {
		return new GenericEObjectProxy(type, id);
	}

	@Override
	public Collection<Object> createCollection(EStructuralFeature feature) {
		return feature.isOrdered() ? new ArrayList<>() : new LinkedHashSet<>();
	}

	@Override
	public Object createEnum(EEnumLiteral literal) {
		return literal;
	}
}
