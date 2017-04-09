/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EPackage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.impl.EPackageImpl#getClassifiers <em>Classifiers</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EPackageImpl#getSubPackages <em>Sub Packages</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EPackageImpl#getSuperPackage <em>Super Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EPackageImpl extends ENamedElementImpl implements EPackage {
	/**
	 * The cached value of the '{@link #getClassifiers() <em>Classifiers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassifier> classifiers;

	/**
	 * The cached value of the '{@link #getSubPackages() <em>Sub Packages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> subPackages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EPackageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApiPackage.Literals.EPACKAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EClassifier> getClassifiers() {
		if (classifiers == null) {
			classifiers = new EObjectContainmentWithInverseEList<EClassifier>(EClassifier.class, this, ApiPackage.EPACKAGE__CLASSIFIERS, ApiPackage.ECLASSIFIER__PACKAGE);
		}
		return classifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getSubPackages() {
		if (subPackages == null) {
			subPackages = new EObjectContainmentWithInverseEList<EPackage>(EPackage.class, this, ApiPackage.EPACKAGE__SUB_PACKAGES, ApiPackage.EPACKAGE__SUPER_PACKAGE);
		}
		return subPackages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getSuperPackage() {
		if (eContainerFeatureID() != ApiPackage.EPACKAGE__SUPER_PACKAGE) return null;
		return (EPackage)eInternalContainer();
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
			case ApiPackage.EPACKAGE__CLASSIFIERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getClassifiers()).basicAdd(otherEnd, msgs);
			case ApiPackage.EPACKAGE__SUB_PACKAGES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubPackages()).basicAdd(otherEnd, msgs);
			case ApiPackage.EPACKAGE__SUPER_PACKAGE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, ApiPackage.EPACKAGE__SUPER_PACKAGE, msgs);
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
			case ApiPackage.EPACKAGE__CLASSIFIERS:
				return ((InternalEList<?>)getClassifiers()).basicRemove(otherEnd, msgs);
			case ApiPackage.EPACKAGE__SUB_PACKAGES:
				return ((InternalEList<?>)getSubPackages()).basicRemove(otherEnd, msgs);
			case ApiPackage.EPACKAGE__SUPER_PACKAGE:
				return eBasicSetContainer(null, ApiPackage.EPACKAGE__SUPER_PACKAGE, msgs);
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
			case ApiPackage.EPACKAGE__SUPER_PACKAGE:
				return eInternalContainer().eInverseRemove(this, ApiPackage.EPACKAGE__SUB_PACKAGES, EPackage.class, msgs);
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
			case ApiPackage.EPACKAGE__CLASSIFIERS:
				return getClassifiers();
			case ApiPackage.EPACKAGE__SUB_PACKAGES:
				return getSubPackages();
			case ApiPackage.EPACKAGE__SUPER_PACKAGE:
				return getSuperPackage();
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
			case ApiPackage.EPACKAGE__CLASSIFIERS:
				getClassifiers().clear();
				getClassifiers().addAll((Collection<? extends EClassifier>)newValue);
				return;
			case ApiPackage.EPACKAGE__SUB_PACKAGES:
				getSubPackages().clear();
				getSubPackages().addAll((Collection<? extends EPackage>)newValue);
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
			case ApiPackage.EPACKAGE__CLASSIFIERS:
				getClassifiers().clear();
				return;
			case ApiPackage.EPACKAGE__SUB_PACKAGES:
				getSubPackages().clear();
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
			case ApiPackage.EPACKAGE__CLASSIFIERS:
				return classifiers != null && !classifiers.isEmpty();
			case ApiPackage.EPACKAGE__SUB_PACKAGES:
				return subPackages != null && !subPackages.isEmpty();
			case ApiPackage.EPACKAGE__SUPER_PACKAGE:
				return getSuperPackage() != null;
		}
		return super.eIsSet(featureID);
	}

} //EPackageImpl
