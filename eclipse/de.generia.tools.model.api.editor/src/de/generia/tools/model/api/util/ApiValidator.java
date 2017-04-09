/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.util;

import de.generia.tools.model.api.*;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.xecore.model.basetype.HashMap;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see de.generia.tools.model.api.ApiPackage
 * @generated
 */
public class ApiValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ApiValidator INSTANCE = new ApiValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "de.generia.tools.model.api";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApiValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return ApiPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case ApiPackage.EMODEL_ELEMENT:
				return validateEModelElement((EModelElement)value, diagnostics, context);
			case ApiPackage.EANNOTATION:
				return validateEAnnotation((EAnnotation)value, diagnostics, context);
			case ApiPackage.EANNOTATION_ELEMENT:
				return validateEAnnotationElement((EAnnotationElement)value, diagnostics, context);
			case ApiPackage.ENAMED_ELEMENT:
				return validateENamedElement((ENamedElement)value, diagnostics, context);
			case ApiPackage.ECLASSIFIER:
				return validateEClassifier((EClassifier)value, diagnostics, context);
			case ApiPackage.EDATA_TYPE:
				return validateEDataType((EDataType)value, diagnostics, context);
			case ApiPackage.EENUM:
				return validateEEnum((EEnum)value, diagnostics, context);
			case ApiPackage.EENUM_LITERAL:
				return validateEEnumLiteral((EEnumLiteral)value, diagnostics, context);
			case ApiPackage.ECLASS:
				return validateEClass((EClass)value, diagnostics, context);
			case ApiPackage.ETYPED_ELEMENT:
				return validateETypedElement((ETypedElement)value, diagnostics, context);
			case ApiPackage.ESTRUCTURAL_FEATURE:
				return validateEStructuralFeature((EStructuralFeature)value, diagnostics, context);
			case ApiPackage.EATTRIBUTE:
				return validateEAttribute((EAttribute)value, diagnostics, context);
			case ApiPackage.EREFERENCE:
				return validateEReference((EReference)value, diagnostics, context);
			case ApiPackage.EOPERATION:
				return validateEOperation((EOperation)value, diagnostics, context);
			case ApiPackage.EPARAMETER:
				return validateEParameter((EParameter)value, diagnostics, context);
			case ApiPackage.EPACKAGE:
				return validateEPackage((de.generia.tools.model.api.EPackage)value, diagnostics, context);
			case ApiPackage.BOOLEAN:
				return validateboolean((Boolean)value, diagnostics, context);
			case ApiPackage.INT:
				return validateint((Integer)value, diagnostics, context);
			case ApiPackage.STRING:
				return validateString((String)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEModelElement(EModelElement eModelElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)eModelElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAnnotation(EAnnotation eAnnotation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)eAnnotation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAnnotationElement(EAnnotationElement eAnnotationElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)eAnnotationElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateENamedElement(ENamedElement eNamedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eNamedElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eNamedElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the WellFormedName constraint of '<em>ENamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateENamedElement_WellFormedName(ENamedElement eNamedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "WellFormedName", getObjectLabel(eNamedElement, context) },
						 new Object[] { eNamedElement },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClassifier(EClassifier eClassifier, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eClassifier, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_WellFormedInstanceTypeName(eClassifier, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_UniqueTypeParameterNames(eClassifier, diagnostics, context);
		return result;
	}

	/**
	 * Validates the WellFormedInstanceTypeName constraint of '<em>EClassifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClassifier_WellFormedInstanceTypeName(EClassifier eClassifier, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "WellFormedInstanceTypeName", getObjectLabel(eClassifier, context) },
						 new Object[] { eClassifier },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the UniqueTypeParameterNames constraint of '<em>EClassifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClassifier_UniqueTypeParameterNames(EClassifier eClassifier, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueTypeParameterNames", getObjectLabel(eClassifier, context) },
						 new Object[] { eClassifier },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEDataType(EDataType eDataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eDataType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_WellFormedInstanceTypeName(eDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_UniqueTypeParameterNames(eDataType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEEnum(EEnum eEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eEnum, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_WellFormedInstanceTypeName(eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_UniqueTypeParameterNames(eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validateEEnum_UniqueEnumeratorNames(eEnum, diagnostics, context);
		if (result || diagnostics != null) result &= validateEEnum_UniqueEnumeratorLiterals(eEnum, diagnostics, context);
		return result;
	}

	/**
	 * Validates the UniqueEnumeratorNames constraint of '<em>EEnum</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEEnum_UniqueEnumeratorNames(EEnum eEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueEnumeratorNames", getObjectLabel(eEnum, context) },
						 new Object[] { eEnum },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the UniqueEnumeratorLiterals constraint of '<em>EEnum</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEEnum_UniqueEnumeratorLiterals(EEnum eEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueEnumeratorLiterals", getObjectLabel(eEnum, context) },
						 new Object[] { eEnum },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEEnumLiteral(EEnumLiteral eEnumLiteral, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eEnumLiteral, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eEnumLiteral, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eEnumLiteral, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eClass, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_WellFormedInstanceTypeName(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClassifier_UniqueTypeParameterNames(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_InterfaceIsAbstract(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_AtMostOneID(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_UniqueFeatureNames(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_UniqueOperationSignatures(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_NoCircularSuperTypes(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_WellFormedMapEntryClass(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_ConsistentSuperTypes(eClass, diagnostics, context);
		if (result || diagnostics != null) result &= validateEClass_DisjointFeatureAndOperationSignatures(eClass, diagnostics, context);
		return result;
	}

	/**
	 * Validates the InterfaceIsAbstract constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_InterfaceIsAbstract(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "InterfaceIsAbstract", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the AtMostOneID constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_AtMostOneID(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "AtMostOneID", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the UniqueFeatureNames constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_UniqueFeatureNames(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueFeatureNames", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the UniqueOperationSignatures constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_UniqueOperationSignatures(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueOperationSignatures", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the NoCircularSuperTypes constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_NoCircularSuperTypes(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "NoCircularSuperTypes", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the WellFormedMapEntryClass constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_WellFormedMapEntryClass(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "WellFormedMapEntryClass", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the ConsistentSuperTypes constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_ConsistentSuperTypes(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ConsistentSuperTypes", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the DisjointFeatureAndOperationSignatures constraint of '<em>EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEClass_DisjointFeatureAndOperationSignatures(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "DisjointFeatureAndOperationSignatures", getObjectLabel(eClass, context) },
						 new Object[] { eClass },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateETypedElement(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eTypedElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidLowerBound(eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidUpperBound(eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ConsistentBounds(eTypedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidType(eTypedElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ValidLowerBound constraint of '<em>ETyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateETypedElement_ValidLowerBound(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ValidLowerBound", getObjectLabel(eTypedElement, context) },
						 new Object[] { eTypedElement },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the ValidUpperBound constraint of '<em>ETyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateETypedElement_ValidUpperBound(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ValidUpperBound", getObjectLabel(eTypedElement, context) },
						 new Object[] { eTypedElement },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the ConsistentBounds constraint of '<em>ETyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateETypedElement_ConsistentBounds(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ConsistentBounds", getObjectLabel(eTypedElement, context) },
						 new Object[] { eTypedElement },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the ValidType constraint of '<em>ETyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateETypedElement_ValidType(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ValidType", getObjectLabel(eTypedElement, context) },
						 new Object[] { eTypedElement },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEStructuralFeature(EStructuralFeature eStructuralFeature, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eStructuralFeature, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidLowerBound(eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidUpperBound(eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ConsistentBounds(eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidType(eStructuralFeature, diagnostics, context);
		if (result || diagnostics != null) result &= validateEStructuralFeature_ValidDefaultValueLiteral(eStructuralFeature, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ValidDefaultValueLiteral constraint of '<em>EStructural Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEStructuralFeature_ValidDefaultValueLiteral(EStructuralFeature eStructuralFeature, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ValidDefaultValueLiteral", getObjectLabel(eStructuralFeature, context) },
						 new Object[] { eStructuralFeature },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAttribute(EAttribute eAttribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eAttribute, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidLowerBound(eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidUpperBound(eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ConsistentBounds(eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidType(eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateEStructuralFeature_ValidDefaultValueLiteral(eAttribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateEAttribute_ConsistentTransient(eAttribute, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ConsistentTransient constraint of '<em>EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAttribute_ConsistentTransient(EAttribute eAttribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ConsistentTransient", getObjectLabel(eAttribute, context) },
						 new Object[] { eAttribute },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEReference(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eReference, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidLowerBound(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidUpperBound(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ConsistentBounds(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidType(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateEStructuralFeature_ValidDefaultValueLiteral(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateEReference_ConsistentOpposite(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateEReference_SingleContainer(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateEReference_ConsistentKeys(eReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateEReference_ConsistentUnique(eReference, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ConsistentOpposite constraint of '<em>EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEReference_ConsistentOpposite(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ConsistentOpposite", getObjectLabel(eReference, context) },
						 new Object[] { eReference },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the SingleContainer constraint of '<em>EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEReference_SingleContainer(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "SingleContainer", getObjectLabel(eReference, context) },
						 new Object[] { eReference },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the ConsistentKeys constraint of '<em>EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEReference_ConsistentKeys(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ConsistentKeys", getObjectLabel(eReference, context) },
						 new Object[] { eReference },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the ConsistentUnique constraint of '<em>EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEReference_ConsistentUnique(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ConsistentUnique", getObjectLabel(eReference, context) },
						 new Object[] { eReference },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEOperation(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eOperation, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidLowerBound(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidUpperBound(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ConsistentBounds(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidType(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateEOperation_UniqueParameterNames(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateEOperation_UniqueTypeParameterNames(eOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateEOperation_NoRepeatingVoid(eOperation, diagnostics, context);
		return result;
	}

	/**
	 * Validates the UniqueParameterNames constraint of '<em>EOperation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEOperation_UniqueParameterNames(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueParameterNames", getObjectLabel(eOperation, context) },
						 new Object[] { eOperation },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the UniqueTypeParameterNames constraint of '<em>EOperation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEOperation_UniqueTypeParameterNames(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "UniqueTypeParameterNames", getObjectLabel(eOperation, context) },
						 new Object[] { eOperation },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the NoRepeatingVoid constraint of '<em>EOperation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEOperation_NoRepeatingVoid(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "NoRepeatingVoid", getObjectLabel(eOperation, context) },
						 new Object[] { eOperation },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEParameter(EParameter eParameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eParameter, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidLowerBound(eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidUpperBound(eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ConsistentBounds(eParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateETypedElement_ValidType(eParameter, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEPackage(de.generia.tools.model.api.EPackage ePackage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)ePackage, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)ePackage, diagnostics, context);
		if (result || diagnostics != null) result &= validateENamedElement_WellFormedName(ePackage, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateboolean(boolean boolean_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateint(int int_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateString(String string, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //ApiValidator
