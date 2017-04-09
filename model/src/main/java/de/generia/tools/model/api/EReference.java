package de.generia.tools.model.api;

import de.generia.tools.model.api.EStructuralFeature;

/**
 * The 'EReference' class was generated from 'api/EReference' EClass api element.
 * 
 */
public class EReference extends EStructuralFeature  {
    
    /**
     * Holds the 'containment' property-value.
     * 
     */
    private boolean containment;
    
    /**
     * Gets the 'containment' property of the 'EReference' EClass.
     * 
     * @return 'containment' property of 'EReference' EClass
     */
    public boolean isContainment() {
    	return containment;
    }
    
    /**
     * Sets the 'containment' property of the 'EReference' EClass.
     * 
     * @param containment new value for the 'containment' property
     */
    public void setContainment(boolean containment) {
    	this.containment = containment;
    }
    
    /**
     * Holds the 'opposite' property-value.
     * 
     */
    private EReference opposite = null;
    
    /**
     * Gets the 'opposite' property of the 'EReference' EClass.
     * 
     * @return 'opposite' property of 'EReference' EClass
     */
    public EReference getOpposite() {
    	return opposite;
    }
    
    /**
     * Sets the 'opposite' property of the 'EReference' EClass.
     * 
     * @param opposite new value for the 'opposite' property
     */
    public void setOpposite(EReference opposite) {
    	this.opposite = opposite;
    }
}
