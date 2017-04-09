/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EClassifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EClassifier#getInstanceTypeName <em>Instance Type Name</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClassifier#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClassifier#getPackage <em>Package</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClassifier#getContainingClass <em>Containing Class</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEClassifier()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='WellFormedInstanceTypeName UniqueTypeParameterNames'"
 * @generated
 */
public interface EClassifier extends ENamedElement {
	/**
	 * Returns the value of the '<em><b>Instance Type Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Type Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Type Name</em>' attribute.
	 * @see #setInstanceTypeName(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEClassifier_InstanceTypeName()
	 * @model default="" dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getInstanceTypeName();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EClassifier#getInstanceTypeName <em>Instance Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Type Name</em>' attribute.
	 * @see #getInstanceTypeName()
	 * @generated
	 */
	void setInstanceTypeName(String value);

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEClassifier_DefaultValue()
	 * @model default="" dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EClassifier#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EPackage#getClassifiers <em>Classifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEClassifier_Package()
	 * @see de.generia.tools.model.api.EPackage#getClassifiers
	 * @model opposite="classifiers" changeable="false"
	 * @generated
	 */
	EPackage getPackage();

	/**
	 * Returns the value of the '<em><b>Containing Class</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EClass#getNestedClassifiers <em>Nested Classifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containing Class</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containing Class</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEClassifier_ContainingClass()
	 * @see de.generia.tools.model.api.EClass#getNestedClassifiers
	 * @model opposite="nestedClassifiers" resolveProxies="false" changeable="false"
	 * @generated
	 */
	EClass getContainingClass();

} // EClassifier
