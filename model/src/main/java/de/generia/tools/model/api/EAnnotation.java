package de.generia.tools.model.api;

import java.util.ArrayList;
import java.util.List;

import de.generia.tools.model.api.EModelElement;

/**
 * The 'EAnnotation' class was generated from 'api/EAnnotation' EClass api element.
 * 
 */
public class EAnnotation extends EModelElement  {
    
    /**
     * Holds the 'source' property-value.
     * 
     */
    private String source;
    
    /**
     * Gets the 'source' property of the 'EAnnotation' EClass.
     * 
     * @return 'source' property of 'EAnnotation' EClass
     */
    public String getSource() {
    	return source;
    }
    
    /**
     * Sets the 'source' property of the 'EAnnotation' EClass.
     * 
     * @param source new value for the 'source' property
     */
    public void setSource(String source) {
    	this.source = source;
    }
    
    /**
     * Holds the 'elements' property-value.
     * 
     */
    private List<String> elements = new ArrayList<String>();
    
    /**
     * Gets the 'elements' property of the 'EAnnotation' EClass.
     * 
     * @return 'elements' property of 'EAnnotation' EClass
     */
    public List<String> getElements() {
    	return elements;
    }
    
    /**
     * Sets the 'elements' property of the 'EAnnotation' EClass.
     * 
     * @param elements new value for the 'elements' property
     */
    public void setElements(List<String> elements) {
    	this.elements = elements;
    }
}
