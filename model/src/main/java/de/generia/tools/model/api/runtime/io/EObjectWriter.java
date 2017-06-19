package de.generia.tools.model.api.runtime.io;

import java.io.IOException;
import java.util.Collection;
import java.util.Stack;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;

public interface EObjectWriter {
	
	void write(Object value) throws IOException;
	
	public interface Context {
		String getObjectId(EObject object);
		boolean isAggregationFeature(EStructuralFeature feature);
		boolean needsTypeHint(EStructuralFeature activeFeature);
		String getTypeHintProperty();
		String getTypeHint(EClass type);
		EAttribute getIdAttribute(EClass type);
		Collection<EStructuralFeature> getFilteredFeatures(EObject value, Stack<EStructuralFeature> featureStack);
		boolean isEnumValue(Object value);
		String toEnumName(Object value);
	}
}
