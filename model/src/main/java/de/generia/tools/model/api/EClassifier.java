package de.generia.tools.model.api;

import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EPackage;

/**
 * The 'EClassifier' class was generated from 'api/EClassifier' EClass api element.
 * 
 */
public abstract class EClassifier extends ENamedElement  {
    
    /**
     * Holds the 'instanceTypeName' property-value.
     * 
     */
    private String instanceTypeName;
    
    /**
     * Gets the 'instanceTypeName' property of the 'EClassifier' EClass.
     * 
     * @return 'instanceTypeName' property of 'EClassifier' EClass
     */
    public String getInstanceTypeName() {
    	return instanceTypeName;
    }
    
    /**
     * Sets the 'instanceTypeName' property of the 'EClassifier' EClass.
     * 
     * @param instanceTypeName new value for the 'instanceTypeName' property
     */
    public void setInstanceTypeName(String instanceTypeName) {
    	this.instanceTypeName = instanceTypeName;
    }
    
    /**
     * Holds the 'defaultValue' property-value.
     * 
     */
    private String defaultValue;
    
    /**
     * Gets the 'defaultValue' property of the 'EClassifier' EClass.
     * 
     * @return 'defaultValue' property of 'EClassifier' EClass
     */
    public String getDefaultValue() {
    	return defaultValue;
    }
    
    /**
     * Sets the 'defaultValue' property of the 'EClassifier' EClass.
     * 
     * @param defaultValue new value for the 'defaultValue' property
     */
    public void setDefaultValue(String defaultValue) {
    	this.defaultValue = defaultValue;
    }
    
    /**
     * Holds the 'package' property-value.
     * 
     */
    private EPackage _package = null;
    
    /**
     * Gets the 'package' property of the 'EClassifier' EClass.
     * 
     * @return 'package' property of 'EClassifier' EClass
     */
    public EPackage getPackage() {
    	return _package;
    }
    
    /**
     * Sets the 'package' property of the 'EClassifier' EClass.
     * 
     * @param _package new value for the 'package' property
     */
    public void setPackage(EPackage _package) {
    	this._package = _package;
    }
}
