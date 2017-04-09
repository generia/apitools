/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.impl.EClassImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassImpl#isInterface <em>Interface</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassImpl#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassImpl#getStructuralFeatures <em>Structural Features</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EClassImpl#getNestedClassifiers <em>Nested Classifiers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EClassImpl extends EClassifierImpl implements EClass {
	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * The default value of the '{@link #isInterface() <em>Interface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInterface()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERFACE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInterface() <em>Interface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInterface()
	 * @generated
	 * @ordered
	 */
	protected boolean interface_ = INTERFACE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperType()
	 * @generated
	 * @ordered
	 */
	protected EClass superType;

	/**
	 * This is true if the Super Type reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean superTypeESet;

	/**
	 * The cached value of the '{@link #getStructuralFeatures() <em>Structural Features</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructuralFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> structuralFeatures;

	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<EOperation> operations;

	/**
	 * The cached value of the '{@link #getNestedClassifiers() <em>Nested Classifiers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedClassifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassifier> nestedClassifiers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected org.eclipse.emf.ecore.EClass eStaticClass() {
		return ApiPackage.Literals.ECLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ECLASS__ABSTRACT, oldAbstract, abstract_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInterface() {
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterface(boolean newInterface) {
		boolean oldInterface = interface_;
		interface_ = newInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ECLASS__INTERFACE, oldInterface, interface_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSuperType() {
		if (superType != null && ((EObject)superType).eIsProxy()) {
			InternalEObject oldSuperType = (InternalEObject)superType;
			superType = (EClass)eResolveProxy(oldSuperType);
			if (superType != oldSuperType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ApiPackage.ECLASS__SUPER_TYPE, oldSuperType, superType));
			}
		}
		return superType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetSuperType() {
		return superType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperType(EClass newSuperType) {
		EClass oldSuperType = superType;
		superType = newSuperType;
		boolean oldSuperTypeESet = superTypeESet;
		superTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApiPackage.ECLASS__SUPER_TYPE, oldSuperType, superType, !oldSuperTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSuperType() {
		EClass oldSuperType = superType;
		boolean oldSuperTypeESet = superTypeESet;
		superType = null;
		superTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ApiPackage.ECLASS__SUPER_TYPE, oldSuperType, null, oldSuperTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSuperType() {
		return superTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EStructuralFeature> getStructuralFeatures() {
		if (structuralFeatures == null) {
			structuralFeatures = new EObjectContainmentWithInverseEList<EStructuralFeature>(EStructuralFeature.class, this, ApiPackage.ECLASS__STRUCTURAL_FEATURES, ApiPackage.ESTRUCTURAL_FEATURE__CONTAINING_CLASS);
		}
		return structuralFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EOperation> getOperations() {
		if (operations == null) {
			operations = new EObjectContainmentWithInverseEList<EOperation>(EOperation.class, this, ApiPackage.ECLASS__OPERATIONS, ApiPackage.EOPERATION__CONTAINING_CLASS);
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EClassifier> getNestedClassifiers() {
		if (nestedClassifiers == null) {
			nestedClassifiers = new EObjectContainmentWithInverseEList<EClassifier>(EClassifier.class, this, ApiPackage.ECLASS__NESTED_CLASSIFIERS, ApiPackage.ECLASSIFIER__CONTAINING_CLASS);
		}
		return nestedClassifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ApiPackage.ECLASS__STRUCTURAL_FEATURES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStructuralFeatures()).basicAdd(otherEnd, msgs);
			case ApiPackage.ECLASS__OPERATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOperations()).basicAdd(otherEnd, msgs);
			case ApiPackage.ECLASS__NESTED_CLASSIFIERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getNestedClassifiers()).basicAdd(otherEnd, msgs);
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
			case ApiPackage.ECLASS__STRUCTURAL_FEATURES:
				return ((InternalEList<?>)getStructuralFeatures()).basicRemove(otherEnd, msgs);
			case ApiPackage.ECLASS__OPERATIONS:
				return ((InternalEList<?>)getOperations()).basicRemove(otherEnd, msgs);
			case ApiPackage.ECLASS__NESTED_CLASSIFIERS:
				return ((InternalEList<?>)getNestedClassifiers()).basicRemove(otherEnd, msgs);
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
			case ApiPackage.ECLASS__ABSTRACT:
				return isAbstract();
			case ApiPackage.ECLASS__INTERFACE:
				return isInterface();
			case ApiPackage.ECLASS__SUPER_TYPE:
				if (resolve) return getSuperType();
				return basicGetSuperType();
			case ApiPackage.ECLASS__STRUCTURAL_FEATURES:
				return getStructuralFeatures();
			case ApiPackage.ECLASS__OPERATIONS:
				return getOperations();
			case ApiPackage.ECLASS__NESTED_CLASSIFIERS:
				return getNestedClassifiers();
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
			case ApiPackage.ECLASS__ABSTRACT:
				setAbstract((Boolean)newValue);
				return;
			case ApiPackage.ECLASS__INTERFACE:
				setInterface((Boolean)newValue);
				return;
			case ApiPackage.ECLASS__SUPER_TYPE:
				setSuperType((EClass)newValue);
				return;
			case ApiPackage.ECLASS__STRUCTURAL_FEATURES:
				getStructuralFeatures().clear();
				getStructuralFeatures().addAll((Collection<? extends EStructuralFeature>)newValue);
				return;
			case ApiPackage.ECLASS__OPERATIONS:
				getOperations().clear();
				getOperations().addAll((Collection<? extends EOperation>)newValue);
				return;
			case ApiPackage.ECLASS__NESTED_CLASSIFIERS:
				getNestedClassifiers().clear();
				getNestedClassifiers().addAll((Collection<? extends EClassifier>)newValue);
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
			case ApiPackage.ECLASS__ABSTRACT:
				setAbstract(ABSTRACT_EDEFAULT);
				return;
			case ApiPackage.ECLASS__INTERFACE:
				setInterface(INTERFACE_EDEFAULT);
				return;
			case ApiPackage.ECLASS__SUPER_TYPE:
				unsetSuperType();
				return;
			case ApiPackage.ECLASS__STRUCTURAL_FEATURES:
				getStructuralFeatures().clear();
				return;
			case ApiPackage.ECLASS__OPERATIONS:
				getOperations().clear();
				return;
			case ApiPackage.ECLASS__NESTED_CLASSIFIERS:
				getNestedClassifiers().clear();
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
			case ApiPackage.ECLASS__ABSTRACT:
				return abstract_ != ABSTRACT_EDEFAULT;
			case ApiPackage.ECLASS__INTERFACE:
				return interface_ != INTERFACE_EDEFAULT;
			case ApiPackage.ECLASS__SUPER_TYPE:
				return isSetSuperType();
			case ApiPackage.ECLASS__STRUCTURAL_FEATURES:
				return structuralFeatures != null && !structuralFeatures.isEmpty();
			case ApiPackage.ECLASS__OPERATIONS:
				return operations != null && !operations.isEmpty();
			case ApiPackage.ECLASS__NESTED_CLASSIFIERS:
				return nestedClassifiers != null && !nestedClassifiers.isEmpty();
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
		result.append(" (abstract: ");
		result.append(abstract_);
		result.append(", interface: ");
		result.append(interface_);
		result.append(')');
		return result.toString();
	}

} //EClassImpl
