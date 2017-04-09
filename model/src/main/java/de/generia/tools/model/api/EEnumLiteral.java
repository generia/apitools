package de.generia.tools.model.api;

import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.ENamedElement;

/**
 * The 'EEnumLiteral' class was generated from 'api/EEnumLiteral' EClass api element.
 * 
 */
public class EEnumLiteral extends ENamedElement  {
    
    /**
     * Holds the 'value' property-value.
     * 
     */
    private int value;
    
    /**
     * Gets the 'value' property of the 'EEnumLiteral' EClass.
     * 
     * @return 'value' property of 'EEnumLiteral' EClass
     */
    public int getValue() {
    	return value;
    }
    
    /**
     * Sets the 'value' property of the 'EEnumLiteral' EClass.
     * 
     * @param value new value for the 'value' property
     */
    public void setValue(int value) {
    	this.value = value;
    }
    
    /**
     * Holds the 'literal' property-value.
     * 
     */
    private String literal;
    
    /**
     * Gets the 'literal' property of the 'EEnumLiteral' EClass.
     * 
     * @return 'literal' property of 'EEnumLiteral' EClass
     */
    public String getLiteral() {
    	return literal;
    }
    
    /**
     * Sets the 'literal' property of the 'EEnumLiteral' EClass.
     * 
     * @param literal new value for the 'literal' property
     */
    public void setLiteral(String literal) {
    	this.literal = literal;
    }
    
    /**
     * Holds the 'enum' property-value.
     * 
     */
    private EEnum _enum = null;
    
    /**
     * Gets the 'enum' property of the 'EEnumLiteral' EClass.
     * 
     * @return 'enum' property of 'EEnumLiteral' EClass
     */
    public EEnum getEnum() {
    	return _enum;
    }
    
    /**
     * Sets the 'enum' property of the 'EEnumLiteral' EClass.
     * 
     * @param _enum new value for the 'enum' property
     */
    public void setEnum(EEnum _enum) {
    	this._enum = _enum;
    }
}
