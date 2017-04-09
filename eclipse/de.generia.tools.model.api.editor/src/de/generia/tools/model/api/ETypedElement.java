/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ETyped Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.ETypedElement#isOrdered <em>Ordered</em>}</li>
 *   <li>{@link de.generia.tools.model.api.ETypedElement#isUnique <em>Unique</em>}</li>
 *   <li>{@link de.generia.tools.model.api.ETypedElement#isMany <em>Many</em>}</li>
 *   <li>{@link de.generia.tools.model.api.ETypedElement#isRequired <em>Required</em>}</li>
 *   <li>{@link de.generia.tools.model.api.ETypedElement#getType <em>Type</em>}</li>
 *   <li>{@link de.generia.tools.model.api.ETypedElement#getKeyType <em>Key Type</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getETypedElement()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidLowerBound ValidUpperBound ConsistentBounds ValidType'"
 * @generated
 */
public interface ETypedElement extends ENamedElement {
	/**
	 * Returns the value of the '<em><b>Ordered</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ordered</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ordered</em>' attribute.
	 * @see #setOrdered(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getETypedElement_Ordered()
	 * @model default="false" dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isOrdered();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.ETypedElement#isOrdered <em>Ordered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ordered</em>' attribute.
	 * @see #isOrdered()
	 * @generated
	 */
	void setOrdered(boolean value);

	/**
	 * Returns the value of the '<em><b>Unique</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique</em>' attribute.
	 * @see #setUnique(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getETypedElement_Unique()
	 * @model default="true" dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isUnique();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.ETypedElement#isUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see #isUnique()
	 * @generated
	 */
	void setUnique(boolean value);

	/**
	 * Returns the value of the '<em><b>Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many</em>' attribute.
	 * @see #setMany(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getETypedElement_Many()
	 * @model dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isMany();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.ETypedElement#isMany <em>Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Many</em>' attribute.
	 * @see #isMany()
	 * @generated
	 */
	void setMany(boolean value);

	/**
	 * Returns the value of the '<em><b>Required</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required</em>' attribute.
	 * @see #setRequired(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getETypedElement_Required()
	 * @model default="false" dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isRequired();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.ETypedElement#isRequired <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required</em>' attribute.
	 * @see #isRequired()
	 * @generated
	 */
	void setRequired(boolean value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClassifier)
	 * @see de.generia.tools.model.api.ApiPackage#getETypedElement_Type()
	 * @model suppressedIsSetVisibility="true" suppressedUnsetVisibility="true"
	 * @generated
	 */
	EClassifier getType();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.ETypedElement#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClassifier value);

	/**
	 * Returns the value of the '<em><b>Key Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Type</em>' reference.
	 * @see #setKeyType(EClassifier)
	 * @see de.generia.tools.model.api.ApiPackage#getETypedElement_KeyType()
	 * @model suppressedIsSetVisibility="true" suppressedUnsetVisibility="true"
	 * @generated
	 */
	EClassifier getKeyType();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.ETypedElement#getKeyType <em>Key Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Type</em>' reference.
	 * @see #getKeyType()
	 * @generated
	 */
	void setKeyType(EClassifier value);

} // ETypedElement
