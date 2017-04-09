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
 * A representation of the model object '<em><b>EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EReference#isContainment <em>Containment</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EReference#getOpposite <em>Opposite</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EReference#getKeys <em>Keys</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEReference()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ConsistentOpposite SingleContainer ConsistentKeys ConsistentUnique'"
 * @generated
 */
public interface EReference extends EStructuralFeature {
	/**
	 * Returns the value of the '<em><b>Containment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containment</em>' attribute.
	 * @see #setContainment(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getEReference_Containment()
	 * @model dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isContainment();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EReference#isContainment <em>Containment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containment</em>' attribute.
	 * @see #isContainment()
	 * @generated
	 */
	void setContainment(boolean value);

	/**
	 * Returns the value of the '<em><b>Opposite</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opposite</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opposite</em>' reference.
	 * @see #setOpposite(EReference)
	 * @see de.generia.tools.model.api.ApiPackage#getEReference_Opposite()
	 * @model
	 * @generated
	 */
	EReference getOpposite();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EReference#getOpposite <em>Opposite</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opposite</em>' reference.
	 * @see #getOpposite()
	 * @generated
	 */
	void setOpposite(EReference value);

	/**
	 * Returns the value of the '<em><b>Keys</b></em>' reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keys</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keys</em>' reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEReference_Keys()
	 * @model
	 * @generated
	 */
	EList<EAttribute> getKeys();

} // EReference
