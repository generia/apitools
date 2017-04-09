package de.generia.tools.model.api.trafo.uml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAnnotationElement;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.resource.ApiResourceUtil;

public class Api2UmlTrafo {
	
	private Model mModel;
	private Map<EModelElement, NamedElement> mApiUmlMap = new HashMap<EModelElement, NamedElement>();

	protected static void registerResourceFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
			UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	}

	protected static void registerPathmaps(URI uri) {
		URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
			uri.appendSegment("libraries").appendSegment(""));

		URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
			uri.appendSegment("metamodels").appendSegment(""));

		URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP),
			uri.appendSegment("profiles").appendSegment(""));
	}

	public static void main(String[] args) throws Exception {
		registerResourceFactories();

		String lPathmapRoot = "jar:file:C:/home/bin/java/eclipse/plugins/org.eclipse.uml2.uml.resources_3.0.0.v200906011111.jar!/";
		registerPathmaps(URI.createURI(lPathmapRoot));

		//File lRoot = new File("C:/home/dev/ws/modelweb");
		File lRoot = new File("C:/home/prj/hvb/mds/ws/api-tools");
		//File lRoot = new File("C:/home/prj/mds/ws/default-saved/workspace");
    	//File lRoot = new File("C:/home/prj/mds/ws/default-saved/workspace/mds/source/plugins/api-tools");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/bondipv/bondipv.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/sandbox/companymgmt/companymgmt.api");
		//File lInputFile = new File(lRoot, "/tst/company-test.api");
		File lInputFile = new File(lRoot, "/tst/bondipv.api");

		URIConverter.URI_MAP.put(URI.createURI("pathmap://API_PROFILES/"), URI.createFileURI(lRoot.getAbsolutePath()).appendSegment("model").appendSegment("profiles").appendSegment(""));
		URIConverter.URI_MAP.put(URI.createURI("platform:/plugin/api-tools/"), URI.createFileURI(lRoot.getAbsolutePath()).appendSegment(""));


		//String lProfileLoc = "pathmap://API_PROFILES/Api.profile.uml";
		String lProfileLoc = "platform:/plugin/api-tools/model/profiles/Api.profile.uml";
		//String lProfileLoc = lRoot.getAbsoluteFile().toURI() + "/model/profiles/Api.profile.uml";
		
		//File lWorkspaceRoot = new File("C:/home/prj/mds/ws/default-saved/workspace");
		//File lOutputDir = new File(lWorkspaceRoot, "/mds/generated");
		File lOutputFile = new File(lInputFile.getAbsolutePath() + ".uml");

		ResourceSet lProfileResourceSet = ApiResourceUtil.createResourceSet();
		URI lProfileUri = URI.createURI(lProfileLoc);
		Resource lProfileResource = lProfileResourceSet.createResource(lProfileUri);
		lProfileResource.load(null);
		Profile lProfile = (Profile) lProfileResource.getContents().get(0);

		ResourceSet lResourceSet = ApiResourceUtil.createResourceSet();
		URI lUri = URI.createFileURI(lInputFile.getAbsolutePath());
		Resource lResource = lResourceSet.createResource(lUri);
		lResource.load(null);
		EPackage lPackage = (EPackage) lResource.getContents().get(0);
		
		Model lModel = UMLFactory.eINSTANCE.createModel();
		Api2UmlTrafo lTrafo = new Api2UmlTrafo(lModel, lProfile);
		lTrafo.transform(lPackage, lModel);
		
		//ResourceSet lOutputResourceSet = ResourceSetUtil.createResourceSet();
		URI lOutputUri = URI.createFileURI(lOutputFile.getAbsolutePath());
		// NOTE: using UMLResource explicitly here, since the XMI2UMLResource stores the profile application tag badly
		Resource lOutputResource = UMLResource.Factory.INSTANCE.createResource(lOutputUri);
		EList<EObject> lContents = lOutputResource.getContents();
		lContents.add(lModel);
		for (Element lElement : lModel.allOwnedElements()) {
			lContents.addAll(lElement.getStereotypeApplications());
		}

		lOutputResource.save(null);
	}
	
	public Api2UmlTrafo(Model pModel, Profile pProfile) {
		mModel = pModel;
		if (pProfile != null) {
			mModel.applyProfile(pProfile);
		}
	}
	
	public Package transform(EPackage pPackage, Package pTarget) {
		Package lPackage = buildPackage(pPackage, pTarget);
		linkPackage();
		return lPackage;
	}
	
	private void linkPackage() {
		for (EModelElement lModelElement : mApiUmlMap.keySet()) {
			if (lModelElement instanceof EAttribute || lModelElement instanceof EParameter) {
				linkTypedElement((ETypedElement)lModelElement);
			} else if (lModelElement instanceof EReference) {
				linkReference((EReference)lModelElement);
			} else if (lModelElement instanceof EOperation) {
				linkOperation((EOperation)lModelElement);
			} else if (lModelElement instanceof EClass) {
				linkClass((EClass)lModelElement);
			}
		}
	}
	
	private void linkClass(EClass pClass) {
		EClass lSuperType = pClass.getSuperType();
		if (lSuperType != null) {
			Class lClass = (Class) mapElement(pClass);
			Classifier lUmlSuperType = (Classifier) mapElement(lSuperType);
			Generalization lGeneralization = UMLFactory.eINSTANCE.createGeneralization();
			lGeneralization.setGeneral(lUmlSuperType);
			lClass.getGeneralizations().add(lGeneralization);
		}
	}

	private void linkReference(EReference pReference) {
		Association lAssociation = (Association) mApiUmlMap.get(pReference);
		String lName = pReference.getName();
		buildAssociationEnd(pReference, lAssociation);
		EReference lOpposite = pReference.getOpposite();
		if (lOpposite != null) {
			buildAssociationEnd(lOpposite, lAssociation);
			lName += "<->" + lOpposite.getName();
		} else {
			Property lProperty  = UMLFactory.eINSTANCE.createProperty();
			NamedElement lNamedElement = mApiUmlMap.get(pReference.getContainingClass());
			if (lNamedElement != null) {
				lProperty.setType((Type) lNamedElement);
			}
			lProperty.setLower(0);
			lProperty.setUpper(-1);
			lName = pReference.getContainingClass().getName() + "-->" + pReference.getName();
			if (pReference.isContainment()) {
				lProperty.setAggregation(AggregationKind.COMPOSITE_LITERAL);
			} else {
				lProperty.setAggregation(AggregationKind.NONE_LITERAL);
			}
			lAssociation.getOwnedEnds().add(lProperty);
		}
		// skip this: lAssociation.setName(lName);
	}

	private void buildAssociationEnd(EReference pReference, Association pTarget) {
		Property lProperty  = UMLFactory.eINSTANCE.createProperty();
		lProperty.setName(pReference.getName());
		
		setMultiplicity(pReference, lProperty);

		NamedElement lNamedElement = mapType(pReference);
		if (lNamedElement != null) {
			lProperty.setType((Type) lNamedElement);
		}
		pTarget.getOwnedEnds().add(lProperty);
		pTarget.getNavigableOwnedEnds().add(lProperty);
		if (pReference.isContainment()) {
			lProperty.setAggregation(AggregationKind.COMPOSITE_LITERAL);
		}
	}

	private void linkTypedElement(ETypedElement pTypedElement) {
		NamedElement lNamedElement = mapType(pTypedElement);
		if (lNamedElement == null) {
			return;
		}
		TypedElement lTypedElement = (TypedElement) mApiUmlMap.get(pTypedElement);
		lTypedElement.setType((Type) lNamedElement);
	}
	
	private void linkOperation(EOperation pOperation) {
		NamedElement lNamedElement = mapType(pOperation);
		if (lNamedElement == null) {
			return;
		}
		Operation lOperation = (Operation) mApiUmlMap.get(pOperation);
		lOperation.setType((Type) lNamedElement);
	}

	private NamedElement mapType(ETypedElement pTypedElement) {
		EClassifier lType = pTypedElement.getType();
		if (lType == null) {
			return null;
		}
		return mapElement(lType);
	}

	private NamedElement mapElement(EClassifier pType) {
		NamedElement lNamedElement = mApiUmlMap.get(pType);
		if (lNamedElement == null) {
			throw new RuntimeException("can't find uml-element for type '" + pType + "'");
		}
		return lNamedElement;
	}

	private Package buildPackage(EPackage pPackage, Package pTarget) {
		Package lPackage = UMLFactory.eINSTANCE.createPackage();
		lPackage.setName(pPackage.getName());
		addChild(pTarget, lPackage, pPackage);

		for (EClassifier lClassifier : pPackage.getClassifiers()) {
			buildClassifier(lClassifier, lPackage);
		}

		for (EPackage lSubPackage : pPackage.getSubPackages()) {
			buildPackage(lSubPackage, lPackage);
		}
		return lPackage;
	}

	private void buildClassifier(EClassifier pClassifier, Namespace pTarget) {
		if (pClassifier instanceof EClass) {
			buildClass((EClass)pClassifier, pTarget);
		} else if (pClassifier instanceof EEnum) {
			buildEnum((EEnum)pClassifier, pTarget);
		} else if (pClassifier instanceof EDataType) {
			buildDataType((EDataType)pClassifier, pTarget);
		}
	}

	private void buildEnum(EEnum pEnum, Namespace pTarget) {
		Enumeration lEnumeration = UMLFactory.eINSTANCE.createEnumeration();
		lEnumeration.setName(pEnum.getName());
		addChild(pTarget, lEnumeration, pEnum);
		
		for (EEnumLiteral lLiteral : pEnum.getLiterals()) {
			EnumerationLiteral lEnumerationLiteral = UMLFactory.eINSTANCE.createEnumerationLiteral();
			lEnumerationLiteral.setName(lLiteral.getName());
			addChild(lEnumeration, lEnumerationLiteral, lLiteral);
		}
	}
	
	private void buildDataType(EDataType pDataType, Namespace pTarget) {
		DataType lDataType = UMLFactory.eINSTANCE.createDataType();
		lDataType.setName(pDataType.getName());
		addChild(pTarget, lDataType, pDataType);
	}
	
	private void buildClass(EClass pClass, Namespace pTarget) {
		if (pClass.isInterface()) {
			buildInterface(pClass, pTarget);
			return;
		}
		org.eclipse.uml2.uml.Class lClass = UMLFactory.eINSTANCE.createClass();
		lClass.setName(pClass.getName());
		addChild(pTarget, lClass, pClass);
		
		for (EStructuralFeature lFeature : pClass.getStructuralFeatures()) {
			buildFeature(lFeature, lClass);
		}
		
		for (EOperation lOperation : pClass.getOperations()) {
			buildOperation(lOperation, lClass);
		}

		for (EClassifier lClassifier : pClass.getNestedClassifiers()) {
			buildClassifier(lClassifier, lClass);
		}
	}
	
	private void buildInterface(EClass pInterface, Namespace pTarget) {
		Interface lInterface = UMLFactory.eINSTANCE.createInterface();
		lInterface.setName(pInterface.getName());
		addChild(pTarget, lInterface, pInterface);
		
		for (EOperation lOperation : pInterface.getOperations()) {
			buildOperation(lOperation, lInterface);
		}
		
		for (EClassifier lClassifier : pInterface.getNestedClassifiers()) {
			buildClassifier(lClassifier, lInterface);
		}
	}

	private void buildOperation(EOperation pOperation, Namespace pTarget) {
		Operation lOperation = UMLFactory.eINSTANCE.createOperation();
		lOperation.setName(pOperation.getName());
		addChild(pTarget, lOperation, pOperation);
		
		
		int lLower = 0;
		if (pOperation.isRequired()) {
			lLower = 1;
		}
		lOperation.setLower(lLower);

		int lUpper = 1;
		if (pOperation.isMany()) {
			lUpper = -1;
		}
		lOperation.setUpper(lUpper);
		
		for (EParameter lParameter : pOperation.getParameters()) {
			buildParameter(lParameter, lOperation);
		}
		
	}

	private void buildParameter(EParameter pParameter, Operation pTarget) {
		Parameter lParameter  = UMLFactory.eINSTANCE.createParameter();
		lParameter.setName(pParameter.getName());
		addChild(pTarget, lParameter, pParameter);
		setMultiplicity(pParameter, lParameter);
	}

	private void buildFeature(EStructuralFeature pFeature, Class pTarget) {
		if (pFeature instanceof EAttribute) {
			buildAttribute((EAttribute)pFeature, pTarget);
		} else {
			buildReference((EReference)pFeature, pTarget);
		}
	}

	private void buildAttribute(EAttribute pAttribute, Class pTarget) {
		Property lProperty  = UMLFactory.eINSTANCE.createProperty();
		lProperty.setName(pAttribute.getName());
		lProperty.setIsDerived(pAttribute.isTransient());
		addChild(pTarget, lProperty, pAttribute);
		
		setMultiplicity(pAttribute, lProperty);
		
		String lDefaultValueLiteral = pAttribute.getDefaultValueLiteral();
		if (lDefaultValueLiteral != null) {
			lProperty.setDefault(lDefaultValueLiteral);
		}
	}

	private void setMultiplicity(ETypedElement pTypedElement, MultiplicityElement pMultiplicityElement) {
		int lLower = 0;
		if (pTypedElement.isRequired()) {
			lLower = 1;
		}
		pMultiplicityElement.setLower(lLower);

		int lUpper = 1;
		if (pTypedElement.isMany()) {
			lUpper = -1;
		}
		pMultiplicityElement.setUpper(lUpper);
		
		pMultiplicityElement.setIsOrdered(pTypedElement.isOrdered());
	}
	
	private void buildReference(EReference pReference, Class pTarget) {
		EReference lOwner = getAssociationOwner(pReference);
		if (mApiUmlMap.containsKey(lOwner)) {
			return;
		}
		Association lAssociation  = UMLFactory.eINSTANCE.createAssociation();
		addChild((Namespace) pTarget.getPackage(), lAssociation, lOwner);
	}

	private EReference getAssociationOwner(EReference pReference) {
		EReference lOpposite = pReference.getOpposite();
		if (lOpposite == null) {
			return pReference;
		}
		if (pReference.getContainingClass().getName().compareTo(lOpposite.getContainingClass().getName()) < 0) {
			return pReference;
		}
		return lOpposite;
	}

	private void addChild(Namespace pTarget, NamedElement pElement, EModelElement pSource) {
		if (pTarget instanceof Package) {
			((Package)pTarget).getPackagedElements().add((PackageableElement) pElement);
		} else if (pTarget instanceof Class) {
			if (pElement instanceof Property) {
				((Class)pTarget).getOwnedAttributes().add((Property) pElement);
			} else if (pElement instanceof Operation) {
				((Class)pTarget).getOwnedOperations().add((Operation) pElement);
			} else if (pElement instanceof Classifier) {
					((Class)pTarget).getNestedClassifiers().add((Classifier) pElement);
			} else {
				throw new UnsupportedOperationException();
			}
		} else if (pTarget instanceof Interface) {
			if (pElement instanceof Operation) {
				((Interface)pTarget).getOwnedOperations().add((Operation) pElement);
			} else if (pElement instanceof Classifier) {
					((Interface)pTarget).getNestedClassifiers().add((Classifier) pElement);
			} else {
				throw new UnsupportedOperationException();
			}
		} else if (pElement instanceof Parameter) {
			((Operation)pTarget).getOwnedParameters().add((Parameter) pElement);
		} else if (pTarget instanceof Enumeration) {
			((Enumeration)pTarget).getOwnedLiterals().add((EnumerationLiteral) pElement);
		} else {
			throw new UnsupportedOperationException();
		}
		mApiUmlMap.put(pSource, pElement);
		mapAnnotationsToStereotypes(pSource, pElement);
	}

	private void mapAnnotationsToStereotypes(EModelElement pSource, NamedElement pElement) {
		for (EAnnotation annotation : pSource.getAnnotations()) {
			for (EAnnotationElement element : annotation.getElements()) {
				mapAnnotationAttributeToStereotypeProperty(pSource, annotation.getSource(), element.getKey(), pElement, "uml2api::" + annotation.getSource(), element.getKey());				
			}
		}
	}
	private void mapAnnotationsToStereotypes2(EModelElement pSource, NamedElement pElement) {
		if (pSource instanceof EPackage) {
			mapAnnotationAttributeToStereotypeProperty(pSource, "Table", "prefix", pElement, "Api::Entities", "tablePrefix");
		} else if (pSource instanceof EClass) {
			EClass lClass = (EClass) pSource;
			if (lClass.isInterface()) {
				return;
			}
			mapAnnotationToStereotype(pSource, "Entity", pElement, "Api::Entity");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Table", "name", pElement, "Api::Entity", "tableName");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Inheritance", "strategy", pElement, "Api::Entity", "inheritanceStrategy");
			mapAnnotationToStereotype(pSource, "Embeddable", pElement, "Api::Embeddable");

		} else if (pSource instanceof EAttribute) {
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "name", pElement, "Api::Column", "name");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "length", pElement, "Api::Column", "length");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "precision", pElement, "Api::Column", "precision");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "scale", pElement, "Api::Column", "scale");

		} else if (pSource instanceof EDataType) {
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "length", pElement, "Api::Column", "length");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "precision", pElement, "Api::Column", "precision");
			mapAnnotationAttributeToStereotypeProperty(pSource, "Column", "scale", pElement, "Api::Column", "scale");
		
		} else if (pSource instanceof EEnumLiteral) {
			EEnumLiteral lLiteral = (EEnumLiteral) pSource;
			setStereotypeValue(pElement, "Api::EnumLiteral", "value", lLiteral.getValue());
		}
	}

	private void mapAnnotationToStereotype(EModelElement pSource, String pAnnotation, NamedElement pElement, String pStereotype) {
		EAnnotation lAnnotation = getAnnotation(pSource, pAnnotation);
		if (lAnnotation == null) {
			return;
		}
		setStereotype(pElement, pStereotype);
	}

	private void mapAnnotationAttributeToStereotypeProperty(EModelElement pSource, String pAnnotation, String pAttribute, NamedElement pElement, String pStereotype, String pProperty) {
		Object lValue = getAnnotationAttribute(pSource, pAnnotation, pAttribute);
		if (lValue == null) {
			return;
		}
		setStereotypeValue(pElement, pStereotype, pProperty, lValue);
	}

	private void setStereotype(NamedElement pElement, String pStereotype) {
		Stereotype lStereotype = pElement.getAppliedStereotype(pStereotype);
		if (lStereotype == null) {
			lStereotype = pElement.getApplicableStereotype(pStereotype);
			if (lStereotype == null) {
				throw new IllegalArgumentException("can't find applicable stereotype '" + pStereotype + "' for uml element '" + pElement + "'");
				// ignore stereotype for now
				//return;
			}
			pElement.applyStereotype(lStereotype);
		}
	}

	private void setStereotypeValue(NamedElement pElement, String pStereotype, String pProperty, Object pValue) {
		Stereotype lStereotype = pElement.getAppliedStereotype(pStereotype);
		if (lStereotype == null) {
			lStereotype = pElement.getApplicableStereotype(pStereotype);
			if (lStereotype == null) {
				//throw new IllegalArgumentException("can't find applicable stereotype '" + pStereotype + "' for uml element '" + pElement + "'");
				return;
			}
			pElement.applyStereotype(lStereotype);
		}
		//Object lValue = pElement.getValue(lStereotype, pProperty);
		pElement.setValue(lStereotype, pProperty, pValue);
		//Object lNewValue = pElement.getValue(lStereotype, pProperty);
	}

	private Object getAnnotationAttribute(EModelElement pElement, String pAnnotation, String pAtttribute) {
		EAnnotation lAnnotation = getAnnotation(pElement, pAnnotation);
		if (lAnnotation == null) {
			return null;
		}
		for (EAnnotationElement element : lAnnotation.getElements()) {
			if (element.getKey().equals(pAtttribute)) {
				return element.getValue();
			}
		}
		return null;
	}

	private EAnnotation getAnnotation(EModelElement pElement, String pAnnotation) {
		for (EAnnotation lAnnotation : pElement.getAnnotations()) {
			if (lAnnotation.getSource() != null && lAnnotation.getSource().equals(pAnnotation)) {
				return lAnnotation;
			}
		}
		return null;
	}

	private Package getPackage(EPackage pPackage) {
		Package lPackage = findPackage(pPackage);
		if (lPackage == null) {
			lPackage =  createPackage(pPackage);
		}
		return lPackage;
	}
	
	private Package findPackage(EPackage pPackage) {
		if (pPackage == null) {
			return mModel;
		}
		EObject lContainer = pPackage.eContainer();
		if (lContainer == null) {
			return (Package) getPackagedElement(mModel, UMLPackage.eINSTANCE.getPackage(), pPackage.getName());
		}
		Package lParent = findPackage((EPackage) lContainer);
		if (lParent == null) {
			return null;
		}
		return (Package) getPackagedElement(lParent, UMLPackage.eINSTANCE.getPackage(), pPackage.getName());
	}
	
	private Package createPackage(EPackage pPackage) {
		Package lParent = getPackage((EPackage) pPackage.eContainer());
		Package lPackage = UMLFactory.eINSTANCE.createPackage();
		lPackage.setName(pPackage.getName());
		lParent.getPackagedElements().add(lPackage);
		return lPackage;
	}

	private PackageableElement getPackagedElement(Package pPackage, org.eclipse.emf.ecore.EClass pClass, String pName) {
		return pPackage.getPackagedElement(pName, false, pClass, false);
	}
}
