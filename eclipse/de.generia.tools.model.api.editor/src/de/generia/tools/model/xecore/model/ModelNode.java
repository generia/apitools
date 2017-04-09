package de.generia.tools.model.xecore.model;

import org.eclipse.emf.ecore.EObject;

public interface ModelNode extends EObject {
	String getName();
	void setName(String pName);
}
