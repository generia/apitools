/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EClass#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClass#isInterface <em>Interface</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClass#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClass#getStructuralFeatures <em>Structural Features</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClass#getOperations <em>Operations</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EClass#getNestedClassifiers <em>Nested Classifiers</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEClass()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='InterfaceIsAbstract AtMostOneID UniqueFeatureNames UniqueOperationSignatures NoCircularSuperTypes WellFormedMapEntryClass ConsistentSuperTypes DisjointFeatureAndOperationSignatures'"
 * @generated
 */
public interface EClass extends EClassifier {
	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getEClass_Abstract()
	 * @model dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EClass#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Interface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface</em>' attribute.
	 * @see #setInterface(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getEClass_Interface()
	 * @model dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isInterface();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EClass#isInterface <em>Interface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface</em>' attribute.
	 * @see #isInterface()
	 * @generated
	 */
	void setInterface(boolean value);

	/**
	 * Returns the value of the '<em><b>Super Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Type</em>' reference.
	 * @see #setSuperType(EClass)
	 * @see de.generia.tools.model.api.ApiPackage#getEClass_SuperType()
	 * @model unsettable="true" suppressedIsSetVisibility="true" suppressedUnsetVisibility="true"
	 * @generated
	 */
	EClass getSuperType();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EClass#getSuperType <em>Super Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type</em>' reference.
	 * @see #getSuperType()
	 * @generated
	 */
	void setSuperType(EClass value);

	/**
	 * Returns the value of the '<em><b>Structural Features</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EStructuralFeature}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EStructuralFeature#getContainingClass <em>Containing Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structural Features</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structural Features</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEClass_StructuralFeatures()
	 * @see de.generia.tools.model.api.EStructuralFeature#getContainingClass
	 * @model opposite="containingClass" containment="true"
	 * @generated
	 */
	EList<EStructuralFeature> getStructuralFeatures();

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EOperation}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EOperation#getContainingClass <em>Containing Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEClass_Operations()
	 * @see de.generia.tools.model.api.EOperation#getContainingClass
	 * @model opposite="containingClass" containment="true"
	 * @generated
	 */
	EList<EOperation> getOperations();

	/**
	 * Returns the value of the '<em><b>Nested Classifiers</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EClassifier}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EClassifier#getContainingClass <em>Containing Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Classifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nested Classifiers</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEClass_NestedClassifiers()
	 * @see de.generia.tools.model.api.EClassifier#getContainingClass
	 * @model opposite="containingClass" containment="true"
	 * @generated
	 */
	EList<EClassifier> getNestedClassifiers();

} // EClass
