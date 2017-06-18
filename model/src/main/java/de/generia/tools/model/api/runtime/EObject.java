package de.generia.tools.model.api.runtime;

import java.util.Collection;
import java.util.Map;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EStructuralFeature;

public interface EObject extends Map<String, Object> {
	
	EClass eGetType();
	String eGetId();

	Collection<EStructuralFeature> eGetStructuralFeatures();
	EStructuralFeature eGetFeature(String property);
	
	Object get(String property);
	void set(String property, Object value);
}
