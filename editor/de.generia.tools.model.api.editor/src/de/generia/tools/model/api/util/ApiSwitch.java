/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.util;

import de.generia.tools.model.api.*;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.generia.tools.model.api.ApiPackage
 * @generated
 */
public class ApiSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ApiPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApiSwitch() {
		if (modelPackage == null) {
			modelPackage = ApiPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ApiPackage.EMODEL_ELEMENT: {
				EModelElement eModelElement = (EModelElement)theEObject;
				T result = caseEModelElement(eModelElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EANNOTATION: {
				EAnnotation eAnnotation = (EAnnotation)theEObject;
				T result = caseEAnnotation(eAnnotation);
				if (result == null) result = caseEModelElement(eAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EANNOTATION_ELEMENT: {
				EAnnotationElement eAnnotationElement = (EAnnotationElement)theEObject;
				T result = caseEAnnotationElement(eAnnotationElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.ENAMED_ELEMENT: {
				ENamedElement eNamedElement = (ENamedElement)theEObject;
				T result = caseENamedElement(eNamedElement);
				if (result == null) result = caseEModelElement(eNamedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.ECLASSIFIER: {
				EClassifier eClassifier = (EClassifier)theEObject;
				T result = caseEClassifier(eClassifier);
				if (result == null) result = caseENamedElement(eClassifier);
				if (result == null) result = caseEModelElement(eClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EDATA_TYPE: {
				EDataType eDataType = (EDataType)theEObject;
				T result = caseEDataType(eDataType);
				if (result == null) result = caseEClassifier(eDataType);
				if (result == null) result = caseENamedElement(eDataType);
				if (result == null) result = caseEModelElement(eDataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EENUM: {
				EEnum eEnum = (EEnum)theEObject;
				T result = caseEEnum(eEnum);
				if (result == null) result = caseEDataType(eEnum);
				if (result == null) result = caseEClassifier(eEnum);
				if (result == null) result = caseENamedElement(eEnum);
				if (result == null) result = caseEModelElement(eEnum);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EENUM_LITERAL: {
				EEnumLiteral eEnumLiteral = (EEnumLiteral)theEObject;
				T result = caseEEnumLiteral(eEnumLiteral);
				if (result == null) result = caseENamedElement(eEnumLiteral);
				if (result == null) result = caseEModelElement(eEnumLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.ECLASS: {
				EClass eClass = (EClass)theEObject;
				T result = caseEClass(eClass);
				if (result == null) result = caseEClassifier(eClass);
				if (result == null) result = caseENamedElement(eClass);
				if (result == null) result = caseEModelElement(eClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.ETYPED_ELEMENT: {
				ETypedElement eTypedElement = (ETypedElement)theEObject;
				T result = caseETypedElement(eTypedElement);
				if (result == null) result = caseENamedElement(eTypedElement);
				if (result == null) result = caseEModelElement(eTypedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.ESTRUCTURAL_FEATURE: {
				EStructuralFeature eStructuralFeature = (EStructuralFeature)theEObject;
				T result = caseEStructuralFeature(eStructuralFeature);
				if (result == null) result = caseETypedElement(eStructuralFeature);
				if (result == null) result = caseENamedElement(eStructuralFeature);
				if (result == null) result = caseEModelElement(eStructuralFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EATTRIBUTE: {
				EAttribute eAttribute = (EAttribute)theEObject;
				T result = caseEAttribute(eAttribute);
				if (result == null) result = caseEStructuralFeature(eAttribute);
				if (result == null) result = caseETypedElement(eAttribute);
				if (result == null) result = caseENamedElement(eAttribute);
				if (result == null) result = caseEModelElement(eAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EREFERENCE: {
				EReference eReference = (EReference)theEObject;
				T result = caseEReference(eReference);
				if (result == null) result = caseEStructuralFeature(eReference);
				if (result == null) result = caseETypedElement(eReference);
				if (result == null) result = caseENamedElement(eReference);
				if (result == null) result = caseEModelElement(eReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EOPERATION: {
				EOperation eOperation = (EOperation)theEObject;
				T result = caseEOperation(eOperation);
				if (result == null) result = caseETypedElement(eOperation);
				if (result == null) result = caseENamedElement(eOperation);
				if (result == null) result = caseEModelElement(eOperation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EPARAMETER: {
				EParameter eParameter = (EParameter)theEObject;
				T result = caseEParameter(eParameter);
				if (result == null) result = caseETypedElement(eParameter);
				if (result == null) result = caseENamedElement(eParameter);
				if (result == null) result = caseEModelElement(eParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApiPackage.EPACKAGE: {
				de.generia.tools.model.api.EPackage ePackage = (de.generia.tools.model.api.EPackage)theEObject;
				T result = caseEPackage(ePackage);
				if (result == null) result = caseENamedElement(ePackage);
				if (result == null) result = caseEModelElement(ePackage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEModelElement(EModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EAnnotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EAnnotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEAnnotation(EAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EAnnotation Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EAnnotation Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEAnnotationElement(EAnnotationElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ENamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ENamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseENamedElement(ENamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EClassifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EClassifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEClassifier(EClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EData Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EData Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEDataType(EDataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEnum</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEnum</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEnum(EEnum object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEnum Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEnum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEnumLiteral(EEnumLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EClass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEClass(EClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ETyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ETyped Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseETypedElement(ETypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EStructural Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EStructural Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEStructuralFeature(EStructuralFeature object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEAttribute(EAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEReference(EReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EOperation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EOperation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEOperation(EOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EParameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EParameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEParameter(EParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EPackage</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EPackage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEPackage(de.generia.tools.model.api.EPackage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ApiSwitch
