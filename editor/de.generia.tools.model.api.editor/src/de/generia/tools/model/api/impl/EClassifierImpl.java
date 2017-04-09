/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EClassifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.impl.EClassifierImpl#getInstanceTypeName <em>Instance Type Name</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassifierImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassifierImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassifierImpl#getContainingClass <em>Containing Class</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EClassifierImpl extends ENamedElementImpl implements EClassifier {
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
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApiPackage.Literals.ECLASSIFIER;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ECLASSIFIER__INSTANCE_TYPE_NAME, oldInstanceTypeName, instanceTypeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ECLASSIFIER__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getPackage() {
		if (eContainerFeatureID() != ApiPackage.ECLASSIFIER__PACKAGE) return null;
		return (EPackage)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public de.generia.tools.model.api.EClass getContainingClass() {
		if (eContainerFeatureID() != ApiPackage.ECLASSIFIER__CONTAINING_CLASS) return null;
		return (de.generia.tools.model.api.EClass)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ApiPackage.ECLASSIFIER__PACKAGE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, ApiPackage.ECLASSIFIER__PACKAGE, msgs);
			case ApiPackage.ECLASSIFIER__CONTAINING_CLASS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, ApiPackage.ECLASSIFIER__CONTAINING_CLASS, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ApiPackage.ECLASSIFIER__PACKAGE:
				return eBasicSetContainer(null, ApiPackage.ECLASSIFIER__PACKAGE, msgs);
			case ApiPackage.ECLASSIFIER__CONTAINING_CLASS:
				return eBasicSetContainer(null, ApiPackage.ECLASSIFIER__CONTAINING_CLASS, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ApiPackage.ECLASSIFIER__PACKAGE:
				return eInternalContainer().eInverseRemove(this, ApiPackage.EPACKAGE__CLASSIFIERS, EPackage.class, msgs);
			case ApiPackage.ECLASSIFIER__CONTAINING_CLASS:
				return eInternalContainer().eInverseRemove(this, ApiPackage.ECLASS__NESTED_CLASSIFIERS, de.generia.tools.model.api.EClass.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ApiPackage.ECLASSIFIER__INSTANCE_TYPE_NAME:
				return getInstanceTypeName();
			case ApiPackage.ECLASSIFIER__DEFAULT_VALUE:
				return getDefaultValue();
			case ApiPackage.ECLASSIFIER__PACKAGE:
				return getPackage();
			case ApiPackage.ECLASSIFIER__CONTAINING_CLASS:
				return getContainingClass();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ApiPackage.ECLASSIFIER__INSTANCE_TYPE_NAME:
				setInstanceTypeName((String)newValue);
				return;
			case ApiPackage.ECLASSIFIER__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
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
			case ApiPackage.ECLASSIFIER__INSTANCE_TYPE_NAME:
				setInstanceTypeName(INSTANCE_TYPE_NAME_EDEFAULT);
				return;
			case ApiPackage.ECLASSIFIER__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
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
			case ApiPackage.ECLASSIFIER__INSTANCE_TYPE_NAME:
				return INSTANCE_TYPE_NAME_EDEFAULT == null ? instanceTypeName != null : !INSTANCE_TYPE_NAME_EDEFAULT.equals(instanceTypeName);
			case ApiPackage.ECLASSIFIER__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case ApiPackage.ECLASSIFIER__PACKAGE:
				return getPackage() != null;
			case ApiPackage.ECLASSIFIER__CONTAINING_CLASS:
				return getContainingClass() != null;
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
		result.append(" (instanceTypeName: ");
		result.append(instanceTypeName);
		result.append(", defaultValue: ");
		result.append(defaultValue);
		result.append(')');
		return result.toString();
	}

} //EClassifierImpl
