/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.ETypedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ETyped Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.impl.ETypedElementImpl#isOrdered <em>Ordered</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.ETypedElementImpl#isUnique <em>Unique</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.ETypedElementImpl#isMany <em>Many</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.ETypedElementImpl#isRequired <em>Required</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.ETypedElementImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.ETypedElementImpl#getKeyType <em>Key Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ETypedElementImpl extends ENamedElementImpl implements ETypedElement {
	/**
	 * The default value of the '{@link #isOrdered() <em>Ordered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOrdered()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ORDERED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOrdered() <em>Ordered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOrdered()
	 * @generated
	 * @ordered
	 */
	protected boolean ordered = ORDERED_EDEFAULT;

	/**
	 * The default value of the '{@link #isUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnique()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNIQUE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnique()
	 * @generated
	 * @ordered
	 */
	protected boolean unique = UNIQUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMany() <em>Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMany()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MANY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMany() <em>Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMany()
	 * @generated
	 * @ordered
	 */
	protected boolean many = MANY_EDEFAULT;

	/**
	 * The default value of the '{@link #isRequired() <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REQUIRED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRequired() <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequired()
	 * @generated
	 * @ordered
	 */
	protected boolean required = REQUIRED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClassifier type;

	/**
	 * The cached value of the '{@link #getKeyType() <em>Key Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyType()
	 * @generated
	 * @ordered
	 */
	protected EClassifier keyType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ETypedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApiPackage.Literals.ETYPED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOrdered() {
		return ordered;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrdered(boolean newOrdered) {
		boolean oldOrdered = ordered;
		ordered = newOrdered;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ETYPED_ELEMENT__ORDERED, oldOrdered, ordered));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnique(boolean newUnique) {
		boolean oldUnique = unique;
		unique = newUnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ETYPED_ELEMENT__UNIQUE, oldUnique, unique));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMany() {
		return many;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMany(boolean newMany) {
		boolean oldMany = many;
		many = newMany;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ETYPED_ELEMENT__MANY, oldMany, many));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequired(boolean newRequired) {
		boolean oldRequired = required;
		required = newRequired;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ETYPED_ELEMENT__REQUIRED, oldRequired, required));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier getType() {
		if (type != null && ((EObject)type).eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EClassifier)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ApiPackage.ETYPED_ELEMENT__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EClassifier newType) {
		EClassifier oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ETYPED_ELEMENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier getKeyType() {
		if (keyType != null && ((EObject)keyType).eIsProxy()) {
			InternalEObject oldKeyType = (InternalEObject)keyType;
			keyType = (EClassifier)eResolveProxy(oldKeyType);
			if (keyType != oldKeyType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ApiPackage.ETYPED_ELEMENT__KEY_TYPE, oldKeyType, keyType));
			}
		}
		return keyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetKeyType() {
		return keyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyType(EClassifier newKeyType) {
		EClassifier oldKeyType = keyType;
		keyType = newKeyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ETYPED_ELEMENT__KEY_TYPE, oldKeyType, keyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ApiPackage.ETYPED_ELEMENT__ORDERED:
				return isOrdered();
			case ApiPackage.ETYPED_ELEMENT__UNIQUE:
				return isUnique();
			case ApiPackage.ETYPED_ELEMENT__MANY:
				return isMany();
			case ApiPackage.ETYPED_ELEMENT__REQUIRED:
				return isRequired();
			case ApiPackage.ETYPED_ELEMENT__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case ApiPackage.ETYPED_ELEMENT__KEY_TYPE:
				if (resolve) return getKeyType();
				return basicGetKeyType();
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
			case ApiPackage.ETYPED_ELEMENT__ORDERED:
				setOrdered((Boolean)newValue);
				return;
			case ApiPackage.ETYPED_ELEMENT__UNIQUE:
				setUnique((Boolean)newValue);
				return;
			case ApiPackage.ETYPED_ELEMENT__MANY:
				setMany((Boolean)newValue);
				return;
			case ApiPackage.ETYPED_ELEMENT__REQUIRED:
				setRequired((Boolean)newValue);
				return;
			case ApiPackage.ETYPED_ELEMENT__TYPE:
				setType((EClassifier)newValue);
				return;
			case ApiPackage.ETYPED_ELEMENT__KEY_TYPE:
				setKeyType((EClassifier)newValue);
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
			case ApiPackage.ETYPED_ELEMENT__ORDERED:
				setOrdered(ORDERED_EDEFAULT);
				return;
			case ApiPackage.ETYPED_ELEMENT__UNIQUE:
				setUnique(UNIQUE_EDEFAULT);
				return;
			case ApiPackage.ETYPED_ELEMENT__MANY:
				setMany(MANY_EDEFAULT);
				return;
			case ApiPackage.ETYPED_ELEMENT__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case ApiPackage.ETYPED_ELEMENT__TYPE:
				setType((EClassifier)null);
				return;
			case ApiPackage.ETYPED_ELEMENT__KEY_TYPE:
				setKeyType((EClassifier)null);
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
			case ApiPackage.ETYPED_ELEMENT__ORDERED:
				return ordered != ORDERED_EDEFAULT;
			case ApiPackage.ETYPED_ELEMENT__UNIQUE:
				return unique != UNIQUE_EDEFAULT;
			case ApiPackage.ETYPED_ELEMENT__MANY:
				return many != MANY_EDEFAULT;
			case ApiPackage.ETYPED_ELEMENT__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case ApiPackage.ETYPED_ELEMENT__TYPE:
				return type != null;
			case ApiPackage.ETYPED_ELEMENT__KEY_TYPE:
				return keyType != null;
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
		result.append(" (ordered: ");
		result.append(ordered);
		result.append(", unique: ");
		result.append(unique);
		result.append(", many: ");
		result.append(many);
		result.append(", required: ");
		result.append(required);
		result.append(')');
		return result.toString();
	}

} //ETypedElementImpl
