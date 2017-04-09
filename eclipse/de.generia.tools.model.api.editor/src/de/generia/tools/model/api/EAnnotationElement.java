/**
 */
package de.generia.tools.model.api;

import de.generia.tools.model.xecore.model.ModelNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAnnotation Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EAnnotationElement#getKey <em>Key</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EAnnotationElement#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEAnnotationElement()
 * @model
 * @extends ModelNode
 * @generated
 */
public interface EAnnotationElement extends ModelNode {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotationElement_Key()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EAnnotationElement#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotationElement_Value()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EAnnotationElement#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // EAnnotationElement
