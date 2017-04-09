/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EEnum Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EEnumLiteral#getValue <em>Value</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EEnumLiteral#getLiteral <em>Literal</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EEnumLiteral#getEnum <em>Enum</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEEnumLiteral()
 * @model
 * @generated
 */
public interface EEnumLiteral extends ENamedElement {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(int)
	 * @see de.generia.tools.model.api.ApiPackage#getEEnumLiteral_Value()
	 * @model dataType="de.generia.tools.model.api.int"
	 * @generated
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EEnumLiteral#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(int value);

	/**
	 * Returns the value of the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Literal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' attribute.
	 * @see #setLiteral(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEEnumLiteral_Literal()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getLiteral();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EEnumLiteral#getLiteral <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' attribute.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(String value);

	/**
	 * Returns the value of the '<em><b>Enum</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EEnum#getLiterals <em>Literals</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEEnumLiteral_Enum()
	 * @see de.generia.tools.model.api.EEnum#getLiterals
	 * @model opposite="literals" resolveProxies="false" changeable="false"
	 * @generated
	 */
	EEnum getEnum();

} // EEnumLiteral
