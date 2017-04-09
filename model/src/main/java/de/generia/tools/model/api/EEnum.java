package de.generia.tools.model.api;

import java.util.ArrayList;
import java.util.List;

import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnumLiteral;

/**
 * The 'EEnum' class was generated from 'api/EEnum' EClass api element.
 * 
 */
public class EEnum extends EDataType  {
    
    /**
     * Holds the 'literals' property-value.
     * 
     */
    private List<EEnumLiteral> literals = new ArrayList<EEnumLiteral>();
    
    /**
     * Gets the 'literals' property of the 'EEnum' EClass.
     * 
     * @return 'literals' property of 'EEnum' EClass
     */
    public List<EEnumLiteral> getLiterals() {
    	return literals;
    }
    
    /**
     * Sets the 'literals' property of the 'EEnum' EClass.
     * 
     * @param literals new value for the 'literals' property
     */
    public void setLiterals(List<EEnumLiteral> literals) {
    	this.literals = literals;
    }
}
