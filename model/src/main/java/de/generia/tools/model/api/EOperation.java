package de.generia.tools.model.api;

import java.util.ArrayList;
import java.util.List;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.ETypedElement;

/**
 * The 'EOperation' class was generated from 'api/EOperation' EClass api element.
 * 
 */
public class EOperation extends ETypedElement  {
    
    /**
     * Holds the 'containingClass' property-value.
     * 
     */
    private EClass containingClass = null;
    
    /**
     * Gets the 'containingClass' property of the 'EOperation' EClass.
     * 
     * @return 'containingClass' property of 'EOperation' EClass
     */
    public EClass getContainingClass() {
    	return containingClass;
    }
    
    /**
     * Sets the 'containingClass' property of the 'EOperation' EClass.
     * 
     * @param containingClass new value for the 'containingClass' property
     */
    public void setContainingClass(EClass containingClass) {
    	this.containingClass = containingClass;
    }
    
    /**
     * Holds the 'parameters' property-value.
     * 
     */
    private List<EParameter> parameters = new ArrayList<EParameter>();
    
    /**
     * Gets the 'parameters' property of the 'EOperation' EClass.
     * 
     * @return 'parameters' property of 'EOperation' EClass
     */
    public List<EParameter> getParameters() {
    	return parameters;
    }
    
    /**
     * Sets the 'parameters' property of the 'EOperation' EClass.
     * 
     * @param parameters new value for the 'parameters' property
     */
    public void setParameters(List<EParameter> parameters) {
    	this.parameters = parameters;
    }
    
    /**
     * Holds the 'exceptions' property-value.
     * 
     */
    private List<EClassifier> exceptions = new ArrayList<EClassifier>();
    
    /**
     * Gets the 'exceptions' property of the 'EOperation' EClass.
     * 
     * @return 'exceptions' property of 'EOperation' EClass
     */
    public List<EClassifier> getExceptions() {
    	return exceptions;
    }
    
    /**
     * Sets the 'exceptions' property of the 'EOperation' EClass.
     * 
     * @param exceptions new value for the 'exceptions' property
     */
    public void setExceptions(List<EClassifier> exceptions) {
    	this.exceptions = exceptions;
    }
}
