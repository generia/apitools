/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAnnotationElement;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EAnnotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.impl.EAnnotationImpl#getSource <em>Source</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EAnnotationImpl#getInstanceTypeName <em>Instance Type Name</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EAnnotationImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EAnnotationImpl extends EModelElementImpl implements EAnnotation {
	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected String source = SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInstanceTypeName() <em>Instance Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_TYPE_NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getInstanceTypeName() <em>Instance Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceTypeName()
	 * @generated
	 * @ordered
	 */
	protected String instanceTypeName = INSTANCE_TYPE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<EAnnotationElement> elements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApiPackage.Literals.EANNOTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(String newSource) {
		String oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.EANNOTATION__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceTypeName() {
		return instanceTypeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceTypeName(String newInstanceTypeName) {
		String oldInstanceTypeName = instanceTypeName;
		instanceTypeName = newInstanceTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.EANNOTATION__INSTANCE_TYPE_NAME, oldInstanceTypeName, instanceTypeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EAnnotationElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<EAnnotationElement>(EAnnotationElement.class, this, ApiPackage.EANNOTATION__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ApiPackage.EANNOTATION__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ApiPackage.EANNOTATION__SOURCE:
				return getSource();
			case ApiPackage.EANNOTATION__INSTANCE_TYPE_NAME:
				return getInstanceTypeName();
			case ApiPackage.EANNOTATION__ELEMENTS:
				return getElements();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ApiPackage.EANNOTATION__SOURCE:
				setSource((String)newValue);
				return;
			case ApiPackage.EANNOTATION__INSTANCE_TYPE_NAME:
				setInstanceTypeName((String)newValue);
				return;
			case ApiPackage.EANNOTATION__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends EAnnotationElement>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ApiPackage.EANNOTATION__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case ApiPackage.EANNOTATION__INSTANCE_TYPE_NAME:
				setInstanceTypeName(INSTANCE_TYPE_NAME_EDEFAULT);
				return;
			case ApiPackage.EANNOTATION__ELEMENTS:
				getElements().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ApiPackage.EANNOTATION__SOURCE:
				return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT.equals(source);
			case ApiPackage.EANNOTATION__INSTANCE_TYPE_NAME:
				return INSTANCE_TYPE_NAME_EDEFAULT == null ? instanceTypeName != null : !INSTANCE_TYPE_NAME_EDEFAULT.equals(instanceTypeName);
			case ApiPackage.EANNOTATION__ELEMENTS:
				return elements != null && !elements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (source: ");
		result.append(source);
		result.append(", instanceTypeName: ");
		result.append(instanceTypeName);
		result.append(')');
		return result.toString();
	}

} //EAnnotationImpl
