package de.generia.tools.model.api;

import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.ETypedElement;

/**
 * The 'EParameter' class was generated from 'api/EParameter' EClass api element.
 * 
 */
public class EParameter extends ETypedElement  {
    
    /**
     * Holds the 'operation' property-value.
     * 
     */
    private EOperation operation = null;
    
    /**
     * Gets the 'operation' property of the 'EParameter' EClass.
     * 
     * @return 'operation' property of 'EParameter' EClass
     */
    public EOperation getOperation() {
    	return operation;
    }
    
    /**
     * Sets the 'operation' property of the 'EParameter' EClass.
     * 
     * @param operation new value for the 'operation' property
     */
    public void setOperation(EOperation operation) {
    	this.operation = operation;
    }
}
