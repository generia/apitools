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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EParameter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EOperation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.generia.tools.model.api.impl.EOperationImpl#getContainingClass <em>Containing Class</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EOperationImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.generia.tools.model.api.impl.EOperationImpl#getExceptions <em>Exceptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EOperationImpl extends ETypedElementImpl implements EOperation {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<EParameter> parameters;

	/**
	 * The cached value of the '{@link #getExceptions() <em>Exceptions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExceptions()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassifier> exceptions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EOperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApiPackage.Literals.EOPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public de.generia.tools.model.api.EClass getContainingClass() {
		if (eContainerFeatureID() != ApiPackage.EOPERATION__CONTAINING_CLASS) return null;
		return (de.generia.tools.model.api.EClass)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentWithInverseEList<EParameter>(EParameter.class, this, ApiPackage.EOPERATION__PARAMETERS, ApiPackage.EPARAMETER__OPERATION);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EClassifier> getExceptions() {
		if (exceptions == null) {
			exceptions = new EObjectResolvingEList.Unsettable<EClassifier>(EClassifier.class, this, ApiPackage.EOPERATION__EXCEPTIONS);
		}
		return exceptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetExceptions() {
		if (exceptions != null) ((InternalEList.Unsettable<?>)exceptions).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetExceptions() {
		return exceptions != null && ((InternalEList.Unsettable<?>)exceptions).isSet();
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
			case ApiPackage.EOPERATION__CONTAINING_CLASS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, ApiPackage.EOPERATION__CONTAINING_CLASS, msgs);
			case ApiPackage.EOPERATION__PARAMETERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParameters()).basicAdd(otherEnd, msgs);
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
			case ApiPackage.EOPERATION__CONTAINING_CLASS:
				return eBasicSetContainer(null, ApiPackage.EOPERATION__CONTAINING_CLASS, msgs);
			case ApiPackage.EOPERATION__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
			case ApiPackage.EOPERATION__CONTAINING_CLASS:
				return eInternalContainer().eInverseRemove(this, ApiPackage.ECLASS__OPERATIONS, de.generia.tools.model.api.EClass.class, msgs);
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
			case ApiPackage.EOPERATION__CONTAINING_CLASS:
				return getContainingClass();
			case ApiPackage.EOPERATION__PARAMETERS:
				return getParameters();
			case ApiPackage.EOPERATION__EXCEPTIONS:
				return getExceptions();
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
			case ApiPackage.EOPERATION__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends EParameter>)newValue);
				return;
			case ApiPackage.EOPERATION__EXCEPTIONS:
				getExceptions().clear();
				getExceptions().addAll((Collection<? extends EClassifier>)newValue);
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
			case ApiPackage.EOPERATION__PARAMETERS:
				getParameters().clear();
				return;
			case ApiPackage.EOPERATION__EXCEPTIONS:
				unsetExceptions();
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
			case ApiPackage.EOPERATION__CONTAINING_CLASS:
				return getContainingClass() != null;
			case ApiPackage.EOPERATION__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case ApiPackage.EOPERATION__EXCEPTIONS:
				return isSetExceptions();
		}
		return super.eIsSet(featureID);
	}

} //EOperationImpl
