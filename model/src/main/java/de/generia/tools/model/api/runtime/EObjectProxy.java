package de.generia.tools.model.api.runtime;

public interface EObjectProxy extends EObject {
	
	EObject getDelegate();
	void setDelegate(EObject delegate);
}
