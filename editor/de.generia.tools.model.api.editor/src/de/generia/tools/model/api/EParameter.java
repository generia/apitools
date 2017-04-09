/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EParameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EParameter#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEParameter()
 * @model
 * @generated
 */
public interface EParameter extends ETypedElement {
	/**
	 * Returns the value of the '<em><b>Operation</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.generia.tools.model.api.EOperation#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' container reference.
	 * @see de.generia.tools.model.api.ApiPackage#getEParameter_Operation()
	 * @see de.generia.tools.model.api.EOperation#getParameters
	 * @model opposite="parameters" resolveProxies="false" changeable="false"
	 * @generated
	 */
	EOperation getOperation();

} // EParameter
