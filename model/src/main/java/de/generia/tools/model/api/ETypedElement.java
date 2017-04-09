package de.generia.tools.model.api;

import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.ENamedElement;

/**
 * The 'ETypedElement' class was generated from 'api/ETypedElement' EClass api element.
 * 
 */
public abstract class ETypedElement extends ENamedElement  {
    
    /**
     * Holds the 'ordered' property-value.
     * 
     */
    private boolean ordered;
    
    /**
     * Gets the 'ordered' property of the 'ETypedElement' EClass.
     * 
     * @return 'ordered' property of 'ETypedElement' EClass
     */
    public boolean isOrdered() {
    	return ordered;
    }
    
    /**
     * Sets the 'ordered' property of the 'ETypedElement' EClass.
     * 
     * @param ordered new value for the 'ordered' property
     */
    public void setOrdered(boolean ordered) {
    	this.ordered = ordered;
    }
    
    /**
     * Holds the 'many' property-value.
     * 
     */
    private boolean many;
    
    /**
     * Gets the 'many' property of the 'ETypedElement' EClass.
     * 
     * @return 'many' property of 'ETypedElement' EClass
     */
    public boolean isMany() {
    	return many;
    }
    
    /**
     * Sets the 'many' property of the 'ETypedElement' EClass.
     * 
     * @param many new value for the 'many' property
     */
    public void setMany(boolean many) {
    	this.many = many;
    }
    
    /**
     * Holds the 'required' property-value.
     * 
     */
    private boolean required;
    
    /**
     * Gets the 'required' property of the 'ETypedElement' EClass.
     * 
     * @return 'required' property of 'ETypedElement' EClass
     */
    public boolean isRequired() {
    	return required;
    }
    
    /**
     * Sets the 'required' property of the 'ETypedElement' EClass.
     * 
     * @param required new value for the 'required' property
     */
    public void setRequired(boolean required) {
    	this.required = required;
    }
    
    /**
     * Holds the 'type' property-value.
     * 
     */
    private EClassifier type = null;
    
    /**
     * Gets the 'type' property of the 'ETypedElement' EClass.
     * 
     * @return 'type' property of 'ETypedElement' EClass
     */
    public EClassifier getType() {
    	return type;
    }
    
    /**
     * Sets the 'type' property of the 'ETypedElement' EClass.
     * 
     * @param type new value for the 'type' property
     */
    public void setType(EClassifier type) {
    	this.type = type;
    }
}
