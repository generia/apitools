package de.generia.tools.model.api.trafo.uml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import de.generia.tools.model.api.ApiFactory;
import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAnnotationElement;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.resource.ApiResourceUtil;

public class Uml2ApiTrafo {
	
	private static final String MDT_INTERNAL_PROPERTY = "base_";
	private static final String API_EXTENSION_ANNOTATION = "api:extension";
	private static final String API_AGGREGATION_ANNOTATION = "api:aggregation";
	
	private Model mModel;
	private Map<NamedElement, EModelElement> mUmlApiMap = new HashMap<NamedElement, EModelElement>();
	private String mProfilePrefix = "uml2api";
	private Set<NamedElement> mProcessedUmlElements = new HashSet<NamedElement>();

	public interface ResourceSetHandler {
		Resource createResource(URI pUri);
		void demandLoad(Resource pResource) throws IOException;
	}
	
	public class Uml2ApiResourceSet extends ResourceSetImpl {
		private ResourceSet mApiResourceSet;
		
		private Uml2ApiResourceSet(ResourceSet pApiResourceSet) {
			mApiResourceSet = pApiResourceSet;
		}

		public void demandLoad(Resource pResource) throws IOException {
			super.demandLoad(pResource);
			
			URI lUmlUri = pResource.getURI();
			if (!lUmlUri.fileExtension().equals(UMLResource.FILE_EXTENSION)) {
				return;
			}
			URI lApiUri = URI.createURI(lUmlUri + ".api");
			Resource lApiResource = mApiResourceSet.createResource(lApiUri);
			EPackage lApiPackage = transform(pResource);
			lApiResource.getContents().add(lApiPackage);
		}
	}
	
	public static void main(String[] pArgs) throws Exception {
    	//File lRoot = new File("C:/home/dev/ws/modelweb");
		File lRoot = new File("C:/home/prj/hvb/mds/ws/api-tools-generator");
		//File lRoot = new File("C:/home/prj/mds/ws/default-saved/workspace");
    	//File lRoot = new File("C:/home/prj/mds/ws/default-saved/workspace/mds/source/plugins/api-tools");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/bondipv/bondipv.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/sandbox/companymgmt/companymgmt.api");
		//File lInputFile = new File(lRoot, "/tst/company-test.api.uml");
		File lInputFile = new File(lRoot, "/tst/trafo/bondipv.api.uml");
		//File lInputFile = new File(lRoot, "/model/company.uml2");
		//File lWorkspaceRoot = new File("C:/home/prj/mds/ws/default-saved/workspace");
		//File lOutputDir = new File(lWorkspaceRoot, "/mds/generated");
		//File lOutputDir = new File(lRoot, "src-gen");
		File lOutputFile = new File(lInputFile.getAbsolutePath() + ".api");
		
		ResourceSet lResourceSet = ApiResourceUtil.createResourceSet();
//		Resource.Factory lUmlResourceFactory = new UMLResourceFactoryImpl();
//		Resource.Factory lUmlResourceFactory  = new UML212UMLResourceFactoryImpl();
		URI lUri = URI.createFileURI(lInputFile.getAbsolutePath());
		//Resource lResource = lUmlResourceFactory.createResource(lUri);
		Resource lResource = lResourceSet.createResource(lUri);
		lResource.load(null);
		Model lModel = (Model) lResource.getContents().get(0);
		
		Uml2ApiTrafo lTrafo = new Uml2ApiTrafo();
		EPackage lPackage = lTrafo.transform(lModel.getNestedPackages().get(0), null);
		
		ResourceSet lOutputResourceSet = ApiResourceUtil.createResourceSet();
		URI lOutputUri = URI.createFileURI(lOutputFile.getAbsolutePath());
		Resource lOutputResource = lOutputResourceSet.createResource(lOutputUri);
		lOutputResource.getContents().add(lPackage);
		lOutputResource.save(null);
	}
	
	
	public Package getUmlPackage(Resource pResource) {
		EList<EObject> lContents = pResource.getContents();
		if (lContents.isEmpty()) {
			return null;
		}
		EObject lObject = lContents.get(0);
		if (lObject instanceof Model) {
			Model lModel = (Model) lObject;
			EList<Package> lPackages = lModel.getNestedPackages();
			if (lPackages.isEmpty()) {
				return null;
			}
			lObject = lPackages.get(0);
		}
		if (lObject instanceof Package) {
			return (Package) lObject;
		}
		return null;
	}

	public Uml2ApiResourceSet createResourceSet(ResourceSet pApiResourceSet) {
		return new Uml2ApiResourceSet(pApiResourceSet);
	}
	
	public EPackage transform(Resource pUmlResource) {
		ResourceSet lResourceSet = pUmlResource.getResourceSet();
		if (!(lResourceSet instanceof Uml2ApiResourceSet)) {
			throw new IllegalArgumentException("uml resource must be loaded inside a '" + Uml2ApiResourceSet.class.getName() + "' resource-set");
		}
		Package lUmlPackage = getUmlPackage(pUmlResource);
		return transform(lUmlPackage, null);
	}
	
	public EPackage transform(Package pPackage, EPackage pTarget) {
		Set<NamedElement> lSavedSet = new HashSet<NamedElement>(mProcessedUmlElements);
		mProcessedUmlElements.clear();
		EPackage lPackage = buildPackage(pPackage, pTarget);
		linkPackage();
		mapStereotypes();
		mProcessedUmlElements.clear();
		mProcessedUmlElements.addAll(lSavedSet);
		return lPackage;
	}
	
	private void mapStereotypes() {
		for (NamedElement lUmlElement : mProcessedUmlElements) {
			EModelElement lApiElement = mUmlApiMap.get(lUmlElement);
			mapStereotypesToAnnotations(lUmlElement, lApiElement);
		}
	}

	private void linkPackage() {
		Set<NamedElement> lProcessedElements = new HashSet<NamedElement>(mProcessedUmlElements);
		for (NamedElement lNamedElement : lProcessedElements) {
			if (lNamedElement instanceof Property || lNamedElement instanceof Parameter) {
				linkTypedElement((TypedElement)lNamedElement);
			} else if (lNamedElement instanceof Association) {
				linkAssociation((Association)lNamedElement);
			} else if (lNamedElement instanceof Class) {
				linkClass((Class)lNamedElement);
			} else if (lNamedElement instanceof Operation) {
				linkOperation((Operation)lNamedElement);
			} else if (lNamedElement instanceof Package) {
				linkPackage((Package)lNamedElement);
			}
		}
	}
	
	private void linkPackage(Package pPackage) {
		Map<String, PackageableElement> lNestedElements = new TreeMap<String, PackageableElement>();
		for (PackageableElement lElement : pPackage.getPackagedElements()) {
			if (isNestedElement(lElement)) {
				lNestedElements.put(lElement.getName(), lElement);
			}
		}
		for (PackageableElement lElement : lNestedElements.values()) {
			EModelElement lModelElement = mUmlApiMap.get(lElement);
			adjustNestedElement((ENamedElement) lModelElement);
		}
	}


	private boolean isNestedElement(PackageableElement pElement) {
		String lName = pElement.getName();
		return lName != null && lName.contains(".");
	}


	private void linkClass(Class pClass) {
		EList<Class> lSuperClasses = pClass.getSuperClasses();
		if (lSuperClasses.size() > 1) {
			throw new RuntimeException("class '" + pClass + "' must have at most one super-type");
		}
		EClass lClass = (EClass) mUmlApiMap.get(pClass);
		if (lSuperClasses.size() == 1) {
			EClass lSuperType = (EClass) mUmlApiMap.get(lSuperClasses.get(0));
			
			lClass.setSuperType(lSuperType);
		}
	}

	private void adjustNestedElement(ENamedElement pElement) {
		int i = pElement.getName().indexOf(".");
		if (i == -1) {
			return;
		}
		//log("adjusting element '" + pElement.getName() + "' inside '" + pElement.eContainer()+ "' ...");
		String lContextName = pElement.getName().substring(0, i);
		String lSubName = pElement.getName().substring(i+1);
		
		ENamedElement lContext = lookupChild(pElement.eContainer(), lContextName);
		if (lContext == null) {
			throw new RuntimeException(
				"can't find context element '" + lContextName + "' in container '" + pElement.eContainer()+ "'" +
				" with children '" + pElement.eContainer().eContents() + "' while adjusting nested element '" + pElement.getName() + "'"
			);
		}
		pElement.setName(lSubName);
		addChild(lContext, pElement);
		
		adjustNestedElement(pElement);
	}


	private ENamedElement lookupChild(EObject pObject, String pName) {
		for (EObject lChild : pObject.eContents()) {
			//log("lookup child '" + ((ENamedElement)lChild).getName() + "' for name '" + pName + "' ...");
			if (lChild instanceof ENamedElement) {
				if (((ENamedElement)lChild).getName().equals(pName)) {
					return (ENamedElement) lChild;
				}
			}
		}
		return null;
	}


	private void linkAssociation(Association pAssociation) {
		EList<Property> lMemberEnds = pAssociation.getMemberEnds();
		if (lMemberEnds.size() != 2) {
			throw new RuntimeException("association '" + pAssociation + "' must have exactly two end-points");
		}
		Property lEnd1 = lMemberEnds.get(0);
		Property lEnd2 = lMemberEnds.get(1);

		EReference lReference1 = null;
		EReference lReference2 = null;
		
		// check, if navigable
		if (isNavigableEnd(lEnd1)) {
			EClass lEnd1Class = (EClass) mUmlApiMap.get(lEnd2.getType());
			lReference1 = buildReference(lEnd1, lEnd1Class);
			linkTypedElement(lEnd1);
		}

		// check, if navigable
		if (isNavigableEnd(lEnd2)) {
			EClass lEnd2Class = (EClass) mUmlApiMap.get(lEnd1.getType());
			lReference2 = buildReference(lEnd2, lEnd2Class);
			linkTypedElement(lEnd2);
		}
		
		if (lReference1 !=  null && lReference2 != null) {
			lReference1.setOpposite(lReference2);
			lReference2.setOpposite(lReference1);
		}
	}

	private boolean isNavigableEnd(Property pEnd) {
		String lName = pEnd.getName();
		if (lName == null) {
			return false;
		}
		// check, if empty or default name
		if (lName.trim().equals("") || lName.equals("src") || lName.equals("dst")) {
			return false;
		}
		return true;
	}


	private EReference buildReference(Property pAttribute, EClass pTarget) {
		EReference lReference  = ApiFactory.eINSTANCE.createEReference();
		lReference.setName(pAttribute.getName());
		lReference.setTransient(pAttribute.isDerived());
		lReference.setContainment(pAttribute.getAggregation() == AggregationKind.COMPOSITE_LITERAL);
		addChild(pTarget, lReference, pAttribute);
		
		setMultiplicity(pAttribute, lReference);
		return lReference;
	}

	private void linkTypedElement(TypedElement pTypedElement) {
		ETypedElement lTypedElement = (ETypedElement) mUmlApiMap.get(pTypedElement);
		EClassifier lClassifier = mapType(pTypedElement);
		if (lClassifier != null) {
			lTypedElement.setType(lClassifier);
		}
	}
	
	private void linkOperation(Operation pOperation) {
		Type lType = pOperation.getType();
		if (lType == null) {
			return;
		}
		EClassifier lClassifier = (EClassifier) mUmlApiMap.get(lType);
		if (lClassifier == null) {
			throw new RuntimeException("can't find api-element for type '" + lType + "'");
		}
		EOperation lOperation = (EOperation) mUmlApiMap.get(pOperation);
		lOperation.setType(lClassifier);
	}

	private EClassifier mapType(TypedElement pTypedElement) {
		Type lType = pTypedElement.getType();
		if (lType == null) {
			return null;
		}
		EClassifier lClassifier = (EClassifier) mUmlApiMap.get(lType);
		if (lClassifier == null) {
			throw new RuntimeException("can't find api-element for type '" + lType + "'");
		}
		return lClassifier;
	}

	private EPackage buildPackage(Package pPackage, EModelElement pTarget) {
		EPackage lPackage = ApiFactory.eINSTANCE.createEPackage();
		lPackage.setName(pPackage.getName());
		addChild(pTarget, lPackage, pPackage);

		for (PackageableElement lPackagableElement : pPackage.getPackagedElements()) {
			buildNamedElement(lPackagableElement, lPackage);
		}
		return lPackage;
	}

	private void buildNamedElement(NamedElement pNamedElement, EModelElement pTarget) {
		if (pNamedElement instanceof Package) {
			buildPackage((Package)pNamedElement, pTarget);
		} else if (pNamedElement instanceof Class) {
			buildClass((Class)pNamedElement, pTarget);
		} else if (pNamedElement instanceof Interface) {
			buildInterface((Interface)pNamedElement, pTarget);
		} else if (pNamedElement instanceof Enumeration) {
			buildEnum((Enumeration)pNamedElement, pTarget);
		} else if (pNamedElement instanceof DataType) {
			buildDataType((DataType)pNamedElement, pTarget);
		} else if (pNamedElement instanceof Association) {
			buildAssociation((Association)pNamedElement);
		} else {
			//throw new UnsupportedOperationException("can't map named element '" + pNamedElement.getName() + "'");
			log("skipping named element '" + pNamedElement.getName() + ":" + pNamedElement.eClass().getName() + "'");
		}
	}

	private void log(String pMessage) {
		System.out.println("Uml2ApiTrafo: " + pMessage);
	}


	private void buildAssociation(Association pAssociation) {
		// hanlde in linkAssociation
		mUmlApiMap.put(pAssociation, null);
		mProcessedUmlElements.add(pAssociation);
	}


	private void buildEnum(Enumeration pEnumeration, EModelElement pTarget) {
		EEnum lEnum = ApiFactory.eINSTANCE.createEEnum();
		lEnum.setName(pEnumeration.getName());
		addChild(pTarget, lEnum, pEnumeration);
		
		for (EnumerationLiteral lLiteral : pEnumeration.getOwnedLiterals()) {
			EEnumLiteral lEnumLiteral = ApiFactory.eINSTANCE.createEEnumLiteral();
			lEnumLiteral.setName(lLiteral.getName());
			addChild(lEnum, lEnumLiteral, lLiteral);
		}
	}
	
	private void buildDataType(DataType pDataType, EModelElement pTarget) {
		EDataType lDataType = ApiFactory.eINSTANCE.createEDataType();
		lDataType.setName(pDataType.getName());
		addChild(pTarget, lDataType, pDataType);
	}
	
	private void buildClass(Class pClass, EModelElement pTarget) {
		EClass lClass = ApiFactory.eINSTANCE.createEClass();
		lClass.setName(pClass.getName());
		addChild(pTarget, lClass, pClass);
		
		for (Property lProperty : pClass.getOwnedAttributes()) {
			buildAttribute(lProperty, lClass);
		}
		
		for (Operation lOperation : pClass.getOperations()) {
			buildOperation(lOperation, lClass);
		}

		for (Classifier lClassifier : pClass.getNestedClassifiers()) {
			buildNamedElement(lClassifier, lClass);
		}
	}
	
	private void buildInterface(Interface pInterface, EModelElement pTarget) {
		EClass lInterface = ApiFactory.eINSTANCE.createEClass();
		lInterface.setInterface(true);
		lInterface.setName(pInterface.getName());
		addChild(pTarget, lInterface, pInterface);
		
		for (Operation lOperation : pInterface.getOwnedOperations()) {
			buildOperation(lOperation, lInterface);
		}
		
		for (Classifier lClassifier : pInterface.getNestedClassifiers()) {
			buildNamedElement(lClassifier, lInterface);
		}
	}

	private void buildOperation(Operation pOperation, EModelElement pTarget) {
		EOperation lOperation = ApiFactory.eINSTANCE.createEOperation();
		lOperation.setName(pOperation.getName());
		addChild(pTarget, lOperation, pOperation);

		boolean lRequired = false;
		if (pOperation.getLower() > 0) {
			lRequired = true;
		}
		lOperation.setRequired(lRequired);

		boolean lMany = false;
		if (pOperation.getUpper() > 1 || pOperation.getUpper() == -1) {
			lMany = true;
		}
		lOperation.setMany(lMany);
		lOperation.setOrdered(pOperation.isOrdered());
		lOperation.setUnique(pOperation.isUnique());

		for (Parameter lParameter : pOperation.getOwnedParameters()) {
			if (lParameter.getDirection() != ParameterDirectionKind.RETURN_LITERAL) {
				buildParameter(lParameter, lOperation);
			}
		}
	}

	private void buildParameter(Parameter pParameter, EOperation pTarget) {
		EParameter lParameter  = ApiFactory.eINSTANCE.createEParameter();
		lParameter.setName(pParameter.getName());
		addChild(pTarget, lParameter, pParameter);
		setMultiplicity(pParameter, lParameter);
	}

	private void buildAttribute(Property pAttribute, EClass pTarget) {
		EAttribute lAttrribute  = ApiFactory.eINSTANCE.createEAttribute();
		lAttrribute.setName(pAttribute.getName());
		lAttrribute.setTransient(pAttribute.isDerived());
		addChild(pTarget, lAttrribute, pAttribute);
		
		setMultiplicity(pAttribute, lAttrribute);
		
		String lDefaultValueLiteral = pAttribute.getDefault();
		if (lDefaultValueLiteral != null) {
			lAttrribute.setDefaultValueLiteral(lDefaultValueLiteral);
		}
	}

	private void setMultiplicity(MultiplicityElement pMultiplicityElement, ETypedElement pTypedElement) {
		boolean lRequired = false;
		if (pMultiplicityElement.getLower() > 0) {
			lRequired = true;
		}
		pTypedElement.setRequired(lRequired);

		boolean lMany = false;
		if (pMultiplicityElement.getUpper() > 1 || pMultiplicityElement.getUpper() == -1) {
			lMany = true;
		}
		pTypedElement.setMany(lMany);
		
		pTypedElement.setOrdered(pMultiplicityElement.isOrdered());
		pTypedElement.setUnique(pMultiplicityElement.isUnique());
	}

	private void addChild(EModelElement pTarget, EModelElement pElement) {
		if (pTarget instanceof EPackage) {
			if (pElement instanceof EPackage) {
				((EPackage)pTarget).getSubPackages().add((EPackage) pElement);
			} else if (pElement instanceof EClassifier) {
				((EPackage)pTarget).getClassifiers().add((EClassifier) pElement);
			} else {
				throw new UnsupportedOperationException();
			}
		} else if (pTarget instanceof EClass) {
			addClassChild((EClass) pTarget, pElement);
		} else if (pTarget instanceof EOperation) {
			((EOperation)pTarget).getParameters().add((EParameter) pElement);
		} else if (pTarget instanceof EEnum) {
			((EEnum)pTarget).getLiterals().add((EEnumLiteral) pElement);
		} else if (pTarget != null){
			throw new UnsupportedOperationException();
		}
	}

	private void addClassChild(EClass pTarget, EModelElement pElement) {
		if (pElement instanceof EStructuralFeature) {
			pTarget.getStructuralFeatures().add((EStructuralFeature) pElement);
		} else if (pElement instanceof EClassifier) {
			pTarget.getNestedClassifiers().add((EClassifier) pElement);
		} else if (pElement instanceof EOperation) {
			pTarget.getOperations().add((EOperation) pElement);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	private void addChild(EModelElement pTarget, EModelElement pElement, NamedElement pSource) {
		addChild(pTarget, pElement);
		mUmlApiMap.put(pSource, pElement);
		mProcessedUmlElements.add(pSource);
	}


	private void mapStereotypesToAnnotations(NamedElement pSource, EModelElement pElement) {
		if (pSource instanceof EnumerationLiteral) {
			Stereotype lStereotype = getStereotype(pSource, "EnumLiteral");
			if (lStereotype != null) {
				EnumerationLiteral lLiteral = (EnumerationLiteral) pSource;
				Object lValue = getStereotypeValue(lLiteral, lStereotype, "value", null);
				if (lValue != null) {
					EEnumLiteral lTarget = (EEnumLiteral) pElement;
					lTarget.setValue(Integer.parseInt(lValue.toString()));
				}
			}
		} else if (pSource instanceof DataType) {
			Stereotype lStereotype = getStereotype(pSource, "Basetype");
			if (lStereotype != null) {
				String lInstanceType = (String) getStereotypeValue(pSource, lStereotype, "instanceType", null);
				EDataType lTarget = (EDataType) pElement;
				lTarget.setInstanceTypeName(lInstanceType);
			}
		} else if (pSource instanceof TypedElement) {
			Stereotype lStereotype = getStereotype(pSource, "Map");
			if (lStereotype != null) {
				EClassifier lKeyType = (EClassifier) getStereotypeValue(pSource, lStereotype, "keyType", null);
				ETypedElement lTarget = (ETypedElement) pElement;
				lTarget.setKeyType(lKeyType);
			}
		}
		
		for (Stereotype lStereotype : pSource.getAppliedStereotypes()) {
			if (lStereotype.getProfile().getName().equals(mProfilePrefix)) {
				continue;
			}
			EAnnotation lAnnotation = getAnnotation(pElement, lStereotype.getName());
			mapStereotypeToAnnotation(pSource, lStereotype, lAnnotation);
		}
	}

	private void mapStereotypeToAnnotation(NamedElement pElement, Stereotype pStereotype, EAnnotation pAnnotation) {
		String lApiElementType = getApiElementType(pStereotype);
		if (lApiElementType != null) {
			getAnnotation(pAnnotation, lApiElementType);
		}
		for (Property lProperty : pStereotype.getOwnedAttributes()) {
			String lName = lProperty.getName();
			if (!lName.startsWith(MDT_INTERNAL_PROPERTY)) {
				String lDefault = lProperty.getDefault();
				if (lDefault != null && lDefault.trim().equals("")) {
					lDefault = null;
				}
				Object lValue = getStereotypeValue(pElement, pStereotype, lName, lDefault);
				String lStringValue = null;
				if (lValue != null) {
					lStringValue = lValue.toString();
				}
				EAnnotationElement annotationElement = ApiFactory.eINSTANCE.createEAnnotationElement();
				annotationElement.setKey(lName);
				annotationElement.setValue(lStringValue);
				pAnnotation.getElements().add(annotationElement);
			}
		}
	}


	private String getApiElementType(Stereotype pStereotype) {
		org.eclipse.emf.ecore.EAnnotation lAnnotation = pStereotype.getEAnnotation(API_EXTENSION_ANNOTATION);
		if (lAnnotation != null) {
			return API_EXTENSION_ANNOTATION;
		}
		lAnnotation = pStereotype.getEAnnotation(API_AGGREGATION_ANNOTATION);
		if (lAnnotation != null) {
			return API_AGGREGATION_ANNOTATION;
		}
		return null;
	}

	private EAnnotation getAnnotation(EModelElement pElement, String pAnnotation) {
		EList<EAnnotation> lAnnotations = pElement.getAnnotations();
		for (EAnnotation lAnnotation : lAnnotations) {
			if (lAnnotation.getSource().equals(pAnnotation)) {
				return lAnnotation;
			}
		}
		EAnnotation lAnnotation = ApiFactory.eINSTANCE.createEAnnotation();
		pElement.getAnnotations().add(lAnnotation);
		lAnnotation.setSource(pAnnotation);
		lAnnotations.add(lAnnotation);
		return lAnnotation;
	}

	private Object getStereotypeValue(NamedElement pElement, Stereotype pStereotype, String pProperty, String pDefault) {
		if (pStereotype == null) {
			return null;
		}
		Object lValue = pElement.getValue(pStereotype, pProperty);
		if (lValue == null) {
			return null;
		}
		if (lValue instanceof Integer) {
			if (((Integer)lValue) == -1) {
				return null;
			}
		} else if (lValue instanceof EnumerationLiteral) {
			EnumerationLiteral lLiteral = (EnumerationLiteral) lValue;
			lValue = lLiteral.getName();
		} else if (lValue instanceof Classifier) {
			lValue = mapStereotypeClassifierValue(lValue);
		}
		if (lValue != null && pDefault != null && lValue.toString().equals(pDefault)) {
			return null;
		}
		return lValue;
	}


	private Object mapStereotypeClassifierValue(Object pValue) {
		EClassifier lClassifier = (EClassifier) mUmlApiMap.get(pValue);
		if (lClassifier == null) {
			throw new RuntimeException("can't find api-element for type '" + pValue + "'");
		}
		pValue = lClassifier;
		return pValue;
	}

	private Stereotype getStereotype(NamedElement pElement, String pStereotype) {
		Stereotype lStereotype = pElement.getAppliedStereotype(mProfilePrefix + "::" + pStereotype);
		return lStereotype;
	}

	private Package getPackage(EPackage pPackage) {
		Package lPackage = findPackage(pPackage);
		if (lPackage == null) {
			lPackage = createPackage(pPackage);
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
