/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EAttribute#isId <em>Id</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEAttribute()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ConsistentTransient'"
 * @generated
 */
public interface EAttribute extends EStructuralFeature {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(boolean)
	 * @see de.generia.tools.model.api.ApiPackage#getEAttribute_Id()
	 * @model dataType="de.generia.tools.model.api.boolean"
	 * @generated
	 */
	boolean isId();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EAttribute#isId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #isId()
	 * @generated
	 */
	void setId(boolean value);

} // EAttribute
