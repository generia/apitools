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
 * A representation of the model object '<em><b>EEnum</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EEnum#getLiterals <em>Literals</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEEnum()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UniqueEnumeratorNames UniqueEnumeratorLiterals'"
 * @generated
 */
public interface EEnum extends EDataType {
	/**
	 * Returns the value of the '<em><b>Literals</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EEnumLiteral}.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EEnumLiteral#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Literals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literals</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEEnum_Literals()
	 * @see de.generia.tools.model.api.EEnumLiteral#getEnum
	 * @model opposite="enum" containment="true"
	 * @generated
	 */
	EList<EEnumLiteral> getLiterals();

} // EEnum
