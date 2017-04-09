package de.generia.tools.model.xecore.binding;

import org.eclipse.emf.common.util.URI;

public interface ModelReferenceHandler {
	URI fromValue(String pValue);
	String toValue(URI pUri);
}
