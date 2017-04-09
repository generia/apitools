package de.generia.tools.model.api;

import java.util.ArrayList;
import java.util.List;

import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.ENamedElement;

/**
 * The 'EPackage' class was generated from 'api/EPackage' EClass api element.
 * 
 */
public class EPackage extends ENamedElement  {
    
    /**
     * Holds the 'classifiers' property-value.
     * 
     */
    private List<EClassifier> classifiers = new ArrayList<EClassifier>();
    
    /**
     * Gets the 'classifiers' property of the 'EPackage' EClass.
     * 
     * @return 'classifiers' property of 'EPackage' EClass
     */
    public List<EClassifier> getClassifiers() {
    	return classifiers;
    }
    
    /**
     * Sets the 'classifiers' property of the 'EPackage' EClass.
     * 
     * @param classifiers new value for the 'classifiers' property
     */
    public void setClassifiers(List<EClassifier> classifiers) {
    	this.classifiers = classifiers;
    }
    
    /**
     * Holds the 'subPackages' property-value.
     * 
     */
    private List<EPackage> subPackages = new ArrayList<EPackage>();
    
    /**
     * Gets the 'subPackages' property of the 'EPackage' EClass.
     * 
     * @return 'subPackages' property of 'EPackage' EClass
     */
    public List<EPackage> getSubPackages() {
    	return subPackages;
    }
    
    /**
     * Sets the 'subPackages' property of the 'EPackage' EClass.
     * 
     * @param subPackages new value for the 'subPackages' property
     */
    public void setSubPackages(List<EPackage> subPackages) {
    	this.subPackages = subPackages;
    }
    
    /**
     * Holds the 'superPackage' property-value.
     * 
     */
    private EPackage superPackage = null;
    
    /**
     * Gets the 'superPackage' property of the 'EPackage' EClass.
     * 
     * @return 'superPackage' property of 'EPackage' EClass
     */
    public EPackage getSuperPackage() {
    	return superPackage;
    }
    
    /**
     * Sets the 'superPackage' property of the 'EPackage' EClass.
     * 
     * @param superPackage new value for the 'superPackage' property
     */
    public void setSuperPackage(EPackage superPackage) {
    	this.superPackage = superPackage;
    }
}
