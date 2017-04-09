package de.generia.tools.model.api;

import java.util.ArrayList;
import java.util.List;

import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EStructuralFeature;

/**
 * The 'EClass' class was generated from 'api/EClass' EClass api element.
 * 
 */
public class EClass extends EClassifier  {
    
    /**
     * Holds the 'abstract' property-value.
     * 
     */
    private boolean _abstract;
    
    /**
     * Gets the 'abstract' property of the 'EClass' EClass.
     * 
     * @return 'abstract' property of 'EClass' EClass
     */
    public boolean isAbstract() {
    	return _abstract;
    }
    
    /**
     * Sets the 'abstract' property of the 'EClass' EClass.
     * 
     * @param _abstract new value for the 'abstract' property
     */
    public void setAbstract(boolean _abstract) {
    	this._abstract = _abstract;
    }
    
    /**
     * Holds the 'interface' property-value.
     * 
     */
    private boolean _interface;
    
    /**
     * Gets the 'interface' property of the 'EClass' EClass.
     * 
     * @return 'interface' property of 'EClass' EClass
     */
    public boolean isInterface() {
    	return _interface;
    }
    
    /**
     * Sets the 'interface' property of the 'EClass' EClass.
     * 
     * @param _interface new value for the 'interface' property
     */
    public void setInterface(boolean _interface) {
    	this._interface = _interface;
    }
    
    /**
     * Holds the 'superType' property-value.
     * 
     */
    private EClass superType = null;
    
    /**
     * Gets the 'superType' property of the 'EClass' EClass.
     * 
     * @return 'superType' property of 'EClass' EClass
     */
    public EClass getSuperType() {
    	return superType;
    }
    
    /**
     * Sets the 'superType' property of the 'EClass' EClass.
     * 
     * @param superType new value for the 'superType' property
     */
    public void setSuperType(EClass superType) {
    	this.superType = superType;
    }
    
    /**
     * Holds the 'structuralFeatures' property-value.
     * 
     */
    private List<EStructuralFeature> structuralFeatures = new ArrayList<EStructuralFeature>();
    
    /**
     * Gets the 'structuralFeatures' property of the 'EClass' EClass.
     * 
     * @return 'structuralFeatures' property of 'EClass' EClass
     */
    public List<EStructuralFeature> getStructuralFeatures() {
    	return structuralFeatures;
    }
    
    /**
     * Sets the 'structuralFeatures' property of the 'EClass' EClass.
     * 
     * @param structuralFeatures new value for the 'structuralFeatures' property
     */
    public void setStructuralFeatures(List<EStructuralFeature> structuralFeatures) {
    	this.structuralFeatures = structuralFeatures;
    }
    
    /**
     * Holds the 'operations' property-value.
     * 
     */
    private List<EOperation> operations = new ArrayList<EOperation>();
    
    /**
     * Gets the 'operations' property of the 'EClass' EClass.
     * 
     * @return 'operations' property of 'EClass' EClass
     */
    public List<EOperation> getOperations() {
    	return operations;
    }
    
    /**
     * Sets the 'operations' property of the 'EClass' EClass.
     * 
     * @param operations new value for the 'operations' property
     */
    public void setOperations(List<EOperation> operations) {
    	this.operations = operations;
    }
    
    /**
     * Holds the 'nestedClassifiers' property-value.
     * 
     */
    private List<EClassifier> nestedClassifiers = new ArrayList<EClassifier>();
    
    /**
     * Gets the 'nestedClassifiers' property of the 'EClass' EClass.
     * 
     * @return 'nestedClassifiers' property of 'EClass' EClass
     */
    public List<EClassifier> getNestedClassifiers() {
    	return nestedClassifiers;
    }
    
    /**
     * Sets the 'nestedClassifiers' property of the 'EClass' EClass.
     * 
     * @param nestedClassifiers new value for the 'nestedClassifiers' property
     */
    public void setNestedClassifiers(List<EClassifier> nestedClassifiers) {
    	this.nestedClassifiers = nestedClassifiers;
    }
}
