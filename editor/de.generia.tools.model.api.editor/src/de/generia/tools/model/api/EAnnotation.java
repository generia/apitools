/**
 */
package de.generia.tools.model.api;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAnnotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.EAnnotation#getSource <em>Source</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EAnnotation#getInstanceTypeName <em>Instance Type Name</em>}</li>
 *   <li>{@link de.generia.tools.model.api.EAnnotation#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation()
 * @model
 * @generated
 */
public interface EAnnotation extends EModelElement {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation_Source()
	 * @model dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EAnnotation#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

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
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation_InstanceTypeName()
	 * @model default="" dataType="de.generia.tools.model.api.String"
	 * @generated
	 */
	String getInstanceTypeName();

	/**
	 * Sets the value of the '{@link de.generia.tools.model.api.EAnnotation#getInstanceTypeName <em>Instance Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Type Name</em>' attribute.
	 * @see #getInstanceTypeName()
	 * @generated
	 */
	void setInstanceTypeName(String value);

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link de.generia.tools.model.api.EAnnotationElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see de.generia.tools.model.api.ApiPackage#getEAnnotation_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<EAnnotationElement> getElements();

} // EAnnotation
