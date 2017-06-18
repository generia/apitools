package de.generia.tools.model.api.runtime;

import java.util.Collection;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EStructuralFeature;

public interface EObjectFactory {
	EObject createObject(EClass type);
	EObjectProxy createObjectProxy(EClass type, String id);
	Collection<Object> createCollection(EStructuralFeature feature);
	Object createEnum(EEnumLiteral literal);
}
