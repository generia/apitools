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
 * A representation of the model object '<em><b>EPackage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EPackage#getClassifiers <em>Classifiers</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EPackage#getSubPackages <em>Sub Packages</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EPackage#getSuperPackage <em>Super Package</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEPackage()
 * @model
 * @generated
 */
public interface EPackage extends ENamedElement {
	/**
	 * Returns the value of the '<em><b>Classifiers</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EClassifier}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EClassifier#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classifiers</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEPackage_Classifiers()
	 * @see de.generia.tools.model.api.EClassifier#getPackage
	 * @model opposite="package" containment="true"
	 * @generated
	 */
	EList<EClassifier> getClassifiers();

	/**
	 * Returns the value of the '<em><b>Sub Packages</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EPackage}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EPackage#getSuperPackage <em>Super Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Packages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Packages</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEPackage_SubPackages()
	 * @see de.generia.tools.model.api.EPackage#getSuperPackage
	 * @model opposite="superPackage" containment="true"
	 * @generated
	 */
	EList<EPackage> getSubPackages();

	/**
	 * Returns the value of the '<em><b>Super Package</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EPackage#getSubPackages <em>Sub Packages</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Package</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Package</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEPackage_SuperPackage()
	 * @see de.generia.tools.model.api.EPackage#getSubPackages
	 * @model opposite="subPackages" changeable="false"
	 * @generated
	 */
	EPackage getSuperPackage();

} // EPackage
