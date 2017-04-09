package de.generia.tools.model.api;

import de.generia.tools.model.api.EStructuralFeature;

/**
 * The 'EAttribute' class was generated from 'api/EAttribute' EClass api element.
 * 
 */
public class EAttribute extends EStructuralFeature  {
    
    /**
     * Holds the 'id' property-value.
     * 
     */
    private boolean id;
    
    /**
     * Gets the 'id' property of the 'EAttribute' EClass.
     * 
     * @return 'id' property of 'EAttribute' EClass
     */
    public boolean isId() {
    	return id;
    }
    
    /**
     * Sets the 'id' property of the 'EAttribute' EClass.
     * 
     * @param id new value for the 'id' property
     */
    public void setId(boolean id) {
    	this.id = id;
    }
}
