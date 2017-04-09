package de.generia.tools.model.api;

import de.generia.tools.model.api.EModelElement;

/**
 * The 'ENamedElement' class was generated from 'api/ENamedElement' EClass api element.
 * 
 */
public abstract class ENamedElement extends EModelElement  {
    
    /**
     * Holds the 'name' property-value.
     * 
     */
    private String name;
    
    /**
     * Gets the 'name' property of the 'ENamedElement' EClass.
     * 
     * @return 'name' property of 'ENamedElement' EClass
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Sets the 'name' property of the 'ENamedElement' EClass.
     * 
     * @param name new value for the 'name' property
     */
    public void setName(String name) {
    	this.name = name;
    }
}
