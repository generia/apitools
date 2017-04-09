/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EStructural Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EStructuralFeature#isTransient <em>Transient</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EStructuralFeature#getDefaultValueLiteral <em>Default Value Literal</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EStructuralFeature#getContainingClass <em>Containing Class</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEStructuralFeature()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidDefaultValueLiteral'"
 * @generated
 */
public interface EStructuralFeature extends ETypedElement {
	/**
	 * Returns the value of the '<em><b>Transient</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transient</em>' attribute.
	 * @see #setTransient(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getEStructuralFeature_Transient()
	 * @model default="false" dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isTransient();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EStructuralFeature#isTransient <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transient</em>' attribute.
	 * @see #isTransient()
	 * @generated
	 */
	void setTransient(boolean value);

	/**
	 * Returns the value of the '<em><b>Default Value Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value Literal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value Literal</em>' attribute.
	 * @see #setDefaultValueLiteral(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEStructuralFeature_DefaultValueLiteral()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getDefaultValueLiteral();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EStructuralFeature#getDefaultValueLiteral <em>Default Value Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value Literal</em>' attribute.
	 * @see #getDefaultValueLiteral()
	 * @generated
	 */
	void setDefaultValueLiteral(String value);

	/**
	 * Returns the value of the '<em><b>Containing Class</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EClass#getStructuralFeatures <em>Structural Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containing Class</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containing Class</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEStructuralFeature_ContainingClass()
	 * @see de.generia.tools.model.api.EClass#getStructuralFeatures
	 * @model opposite="structuralFeatures" resolveProxies="false" changeable="false"
	 * @generated
	 */
	EClass getContainingClass();

} // EStructuralFeature
