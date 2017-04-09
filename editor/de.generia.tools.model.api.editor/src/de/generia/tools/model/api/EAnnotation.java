/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAnnotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EAnnotation#getSource <em>Source</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EAnnotation#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation()
 * @model
 * @generated
 */
public interface EAnnotation extends EModelElement {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation_Source()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EAnnotation#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EAnnotationElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<EAnnotationElement> getElements();

} // EAnnotation
