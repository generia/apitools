package de.generia.tools.model.api;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.ETypedElement;

/**
 * The 'EStructuralFeature' class was generated from 'api/EStructuralFeature' EClass api element.
 * 
 */
public abstract class EStructuralFeature extends ETypedElement  {
    
    /**
     * Holds the 'transient' property-value.
     * 
     */
    private boolean _transient;
    
    /**
     * Gets the 'transient' property of the 'EStructuralFeature' EClass.
     * 
     * @return 'transient' property of 'EStructuralFeature' EClass
     */
    public boolean isTransient() {
    	return _transient;
    }
    
    /**
     * Sets the 'transient' property of the 'EStructuralFeature' EClass.
     * 
     * @param _transient new value for the 'transient' property
     */
    public void setTransient(boolean _transient) {
    	this._transient = _transient;
    }
    
    /**
     * Holds the 'defaultValueLiteral' property-value.
     * 
     */
    private String defaultValueLiteral;
    
    /**
     * Gets the 'defaultValueLiteral' property of the 'EStructuralFeature' EClass.
     * 
     * @return 'defaultValueLiteral' property of 'EStructuralFeature' EClass
     */
    public String getDefaultValueLiteral() {
    	return defaultValueLiteral;
    }
    
    /**
     * Sets the 'defaultValueLiteral' property of the 'EStructuralFeature' EClass.
     * 
     * @param defaultValueLiteral new value for the 'defaultValueLiteral' property
     */
    public void setDefaultValueLiteral(String defaultValueLiteral) {
    	this.defaultValueLiteral = defaultValueLiteral;
    }
    
    /**
     * Holds the 'containingClass' property-value.
     * 
     */
    private EClass containingClass = null;
    
    /**
     * Gets the 'containingClass' property of the 'EStructuralFeature' EClass.
     * 
     * @return 'containingClass' property of 'EStructuralFeature' EClass
     */
    public EClass getContainingClass() {
    	return containingClass;
    }
    
    /**
     * Sets the 'containingClass' property of the 'EStructuralFeature' EClass.
     * 
     * @param containingClass new value for the 'containingClass' property
     */
    public void setContainingClass(EClass containingClass) {
    	this.containingClass = containingClass;
    }
}
