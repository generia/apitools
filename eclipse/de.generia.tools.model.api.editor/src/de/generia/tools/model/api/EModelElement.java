/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;

import org.eclipse.emf.common.util.EList;

import de.generia.tools.model.xecore.model.ModelNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EModel Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EModelElement#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EModelElement#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEModelElement()
 * @model abstract="true"
 * @extends ModelNode
 * @generated
 */
public interface EModelElement extends ModelNode {
	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EAnnotation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEModelElement_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<EAnnotation> getAnnotations();

	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation</em>' attribute.
	 * @see #setDocumentation(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEModelElement_Documentation()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getDocumentation();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EModelElement#getDocumentation <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' attribute.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(String value);

} // EModelElement
