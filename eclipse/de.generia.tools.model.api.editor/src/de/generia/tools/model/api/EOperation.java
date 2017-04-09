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
 * A representation of the model object '<em><b>EOperation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EOperation#getContainingClass <em>Containing Class</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EOperation#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EOperation#getExceptions <em>Exceptions</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEOperation()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UniqueParameterNames UniqueTypeParameterNames NoRepeatingVoid'"
 * @generated
 */
public interface EOperation extends ETypedElement {
	/**
	 * Returns the value of the '<em><b>Containing Class</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EClass#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containing Class</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containing Class</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEOperation_ContainingClass()
	 * @see de.generia.tools.model.api.EClass#getOperations
	 * @model opposite="operations" resolveProxies="false" changeable="false"
	 * @generated
	 */
	EClass getContainingClass();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EParameter}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EParameter#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEOperation_Parameters()
	 * @see de.generia.tools.model.api.EParameter#getOperation
	 * @model opposite="operation" containment="true"
	 * @generated
	 */
	EList<EParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Exceptions</b></em>' reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EClassifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exceptions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exceptions</em>' reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEOperation_Exceptions()
	 * @model unsettable="true" suppressedIsSetVisibility="true" suppressedUnsetVisibility="true"
	 * @generated
	 */
	EList<EClassifier> getExceptions();

} // EOperation
