package de.generia.tools.model.api.runtime;

import java.util.Collection;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EStructuralFeature;

public interface EObjectFactory {
	EObject createObject(EClass type);
	EObjectProxy createObjectProxy(EClass type, String id);
	Collection<Object> createCollection(EStructuralFeature feature);
	Object createEnumValue(EEnum type, String name);
	String toEnumName(Object value);
	boolean isEnumValue(Object value);
}
