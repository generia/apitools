package de.generia.tools.model.api;

import java.util.ArrayList;
import java.util.List;

import de.generia.tools.model.api.EAnnotation;

/**
 * The 'EModelElement' class was generated from 'api/EModelElement' EClass api element.
 * 
 */
public abstract class EModelElement  {
    
    /**
     * Holds the 'parent' property-value.
     * 
     */
	private EModelElement parent;
	
    /**
     * Holds the 'annotations' property-value.
     * 
     */
    private List<EAnnotation> annotations = new ArrayList<EAnnotation>();
    
    
	/**
     * Gets the 'parent' property of the 'EModelElement' EClass.
     * 
     * @return 'parent' property of 'EModelElement' EClass
     */
    public EModelElement getParent() {
		return parent;
	}
    
    /**
     * Sets the 'parent' property of the 'EModelElement' EClass.
     * 
     * @param parent new value for the 'parent' property
     */
	public void setParent(EModelElement parent) {
		this.parent = parent;
	}

	/**
     * Gets the 'annotations' property of the 'EModelElement' EClass.
     * 
     * @return 'annotations' property of 'EModelElement' EClass
     */
    public List<EAnnotation> getAnnotations() {
    	return annotations;
    }
    
    /**
     * Sets the 'annotations' property of the 'EModelElement' EClass.
     * 
     * @param annotations new value for the 'annotations' property
     */
    public void setAnnotations(List<EAnnotation> annotations) {
    	this.annotations = annotations;
    }
}
