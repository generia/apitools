package de.generia.tools.model.uml.profilebuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

public class EcoreProfileBuilder extends UmlProfileBuilder {

	private static final String ECORE_UML_ANNOTATION_NS = "http://www.eclipse.org/uml2/2.0.0/UML";
	private static final String PROFILE_BUILDER_NS = "http://www.generia.de/uml/profilebuilder";
	private static final String PROFILE_BUILDER_MAPPING = PROFILE_BUILDER_NS + "-mapping";
	private static final String PROFILE_BUILDER_STEREOTYPE = PROFILE_BUILDER_NS + "-stereotype";
	private static final String PROFILE_BUILDER_STEREOTYPE_HELPER = PROFILE_BUILDER_NS + "-stereotype-helper";

	private static final String PROFILE_BUILDER_INFO_EXTENSION = PROFILE_BUILDER_NS + "-info-extension";
	private static final String PROFILE_BUILDER_INFO_AGGREGATION = PROFILE_BUILDER_NS + "-info-aggregation";
	
	private Profile mProfile;
	private Map<String, Type> mTypeMap = new HashMap<String, Type>();
	private Map<EClass, Stereotype> mClassStereotypeMap = new HashMap<EClass, Stereotype>();
	private UMLResource mProfileResource;
	private Map<EObject, EClassifier> mElementMetaclassMap = new HashMap<EObject, EClassifier>();
	private List<EClass> mStereotypes = new ArrayList<EClass>(); 
	private List<EClass> mStereotypeHelpers = new ArrayList<EClass>();
	private List<EClassifier> mClassifiers = new ArrayList<EClassifier>();
	private String mPathmapRootUri = null;
	private Model mUmlMetaModel = null;

	public static void main(String[] pArgs) {
		File lEclipseRoot = new File("C:/home/bin/java/eclipse");
		File lRoot = new File("C:/home/prj/hvb/mds/ws/de.generia.tools.model.uml.profilebuilder");

		URI lInputUri = URI.createURI(new File(lRoot, "tst/mdsApi.ecore").toURI().toString());
		URI lOutputUri = URI.createURI(new File(lRoot, "tst/mdsApi.ecore.profile.uml").toURI().toString());
//		URI lInputUri = URI.createURI(new File(lRoot, "tst/profile/uml2api.ecore").toURI().toString());
//		URI lOutputUri = URI.createURI(new File(lRoot, "tst/profile/uml2api.ecore.profile.uml").toURI().toString());

		//File lOutputFile = new File(lRoot, "src/" + API_PROFILE_RESOURCE);
		String lPathmapRootUri = "jar:file:" + lEclipseRoot + "/plugins/org.eclipse.uml2.uml.resources_3.0.0.v200906011111.jar!/";

		EcoreProfileBuilder lProfileBuilder = new EcoreProfileBuilder(lPathmapRootUri);
		lProfileBuilder.buildProfile(lInputUri, lOutputUri);
	}
	
	/**
	 * Init in standalone mode with uri like
	 * <pre>
	 * "jar:file:" + lEclipseRoot + "/plugins/org.eclipse.uml2.uml.resources_3.0.0.v200906011111.jar!/";
	 * </pre>
	 * otherwise <code>null</code> is fine.
	 * @param pPathmapRootUri
	 */
	public EcoreProfileBuilder(String pPathmapRootUri) {
		mPathmapRootUri = pPathmapRootUri;
	}
	
	private EPackage readEcorePackage(URI pInputUri) {
		ResourceSet lResourceSet = createResourceSet();
		registerPackages(lResourceSet);

		EPackage lProfilePackage;
		Resource lResource = lResourceSet.createResource(pInputUri);
		try {
			lResource.load(null);
			EList<EObject> lContents = lResource.getContents();
			if (lContents.isEmpty()) {
				return null;
			}
			lProfilePackage = (EPackage) lContents.get(0);
		} catch (IOException e) {
			throw new RuntimeException("can't load input '" + pInputUri + "'", e);
		}
		return lProfilePackage;
	}
	
	private void initReferenceTypes(EClass pClass) {
		for (EStructuralFeature lFeature : pClass.getEStructuralFeatures()) {
			EClassifier lType = lFeature.getEType();
			initWorkingSetsMappings(lType);
			if (!mClassifiers.contains(lType)) {
				mClassifiers.add(lType);
			}
		}
	}

	public Profile buildProfile(URI pInputUri, URI pOutputUri) {
		registerResourceFactories();
		if (mPathmapRootUri != null) {
			registerPathmaps(URI.createURI(mPathmapRootUri));
		}
		EPackage lPackage = readEcorePackage(pInputUri);
		return buildProfile(lPackage, pOutputUri);
	}
	
	public Profile buildProfile(EPackage pPackage, URI pOutputUri) {
		
		parseEcoreObject(pPackage, new HashSet<EObject>());

		// init uml metamodel
		mUmlMetaModel = (Model) load(URI.createURI(UMLResource.UML_METAMODEL_URI));

		ResourceSet lResourceSet = createResourceSet();
		registerPackages(lResourceSet);

		out("Creating profile...");


		
		for (EClass lClass : mStereotypes) {
			initReferenceTypes(lClass);
		}

		mProfile = createProfile(pPackage.getName(), pOutputUri);

		// extends enum literal
//		Stereotype lEnumLiteralStereotype = createStereotype(mProfile, "EnumLiteral", false);
//		UmlModelBuilder.createAttribute(lEnumLiteralStereotype, "value", getType("Integer"), 0, 1);
//		org.eclipse.uml2.uml.Class lEnumLiteralMetaclass = referenceMetaclass(mProfile, UMLPackage.Literals.ENUMERATION_LITERAL.getName());
//		createExtension(lEnumLiteralMetaclass, lEnumLiteralStereotype, false);

		out("Creating basetypes...");
		for (EClassifier lClassifier : mClassifiers) {
			buildClassifier(pPackage, lClassifier);
		}

		out("Creating stereotypes...");
		for (EClass lClass : mStereotypes) {
			buildStereotype(lClass);
		}

		out("Creating stereotype generalizations...");
		for (EClass lClass : mStereotypes) {
			buildGeneralizations(lClass);
		}

		out("Referencing metaclasses...");
		out("Creating extensions...");
		for (EClass lClass : mStereotypes) {
			buildMetaclassExtension(lClass);
		}
		for (EClass lClass : mStereotypeHelpers) {
			buildMetaclassExtensionHelper(lClass);
		}

		out("Defining profile...");
		// the id and nsURI must be kept, in order to get the existing 
		// stereotype applications working
		String lId = null;
		String lNsURI = null;
		EPackage lPackage = null;
		EAnnotation lAnnotation = mProfile.getEAnnotation(ECORE_UML_ANNOTATION_NS);
		if (lAnnotation != null) {
			lPackage = (EPackage) lAnnotation.getContents().get(0);
			lNsURI = lPackage.getNsURI();
			lId = mProfileResource.getID(lPackage);
			mProfile.getEAnnotations().remove(lAnnotation);
		}
		defineProfile(mProfile);
		if (lId != null) {
			lAnnotation = mProfile.getEAnnotation(ECORE_UML_ANNOTATION_NS);
			lPackage = (EPackage) lAnnotation.getContents().get(0);
			lPackage.setNsURI(lNsURI);
		}
		out("Saving profile...");
		UMLResource resource = (UMLResource) createResourceSet().createResource(pOutputUri);
		if (lId != null) {
			resource.setID(lPackage, lId);
		}
		EList<EObject> contents = resource.getContents();
		contents.add(mProfile);
		try {
			resource.save(null);
			out("Done.");
		} catch (IOException ioe) {
			err(ioe.getMessage());
		}
		return mProfile;
	}

	private Profile createProfile(String pName, URI pUri) {
		Profile lProfile  = null;
		try {
			mProfileResource = (UMLResource) createResourceSet().getResource(pUri, true);
			lProfile  = (Profile) EcoreUtil.getObjectByType(mProfileResource.getContents(), UMLPackage.Literals.PACKAGE);
		} catch (Exception e) {
			lProfile = createProfile(pName);
		}
		return lProfile;
	}

	private void buildMetaclassExtension(EClass pClass) {
		String lName = findUmlMetaclass(pClass);
		if (lName == null) {
			return;
		}
		Stereotype lStereotype = mClassStereotypeMap.get(pClass);
		if (lStereotype != null) {
			org.eclipse.uml2.uml.Class lMetaclass = referenceMetaclass(mUmlMetaModel, mProfile, lName);
			createExtension(lMetaclass, lStereotype, false);
		}
	}

	private void buildMetaclassExtensionHelper(EClass pClass) {
		String lName = findUmlMetaclass(pClass);
		if (lName == null) {
			return;
		}
		for (EReference lReference : pClass.getEReferences()) {
			Stereotype lStereotype = mClassStereotypeMap.get(lReference.getEType());
			if (lStereotype != null) {
				org.eclipse.uml2.uml.Class lMetaclass = referenceMetaclass(mUmlMetaModel, mProfile, lName);
				createExtension(lMetaclass, lStereotype, false);
			}
		}
	}

	private String findUmlMetaclass(EClass pClass) {
		EClassifier lClassifier = mElementMetaclassMap.get(pClass);
		if (lClassifier != null) {
			return lClassifier.getName();
		}
		EClass lSuperType = getSuperType(pClass);
		if (lSuperType != null) {
			return findUmlMetaclass(lSuperType);
		}
		return null;
	}

	private void parseEcoreObject(EObject pObject, Set<EObject> pVisited) {
		if (pVisited.contains(pObject)) {
			return;
		}
		pVisited.add(pObject);
		initWorkingSets(pObject);
		
		for (EObject lObject : pObject.eContents()) {
			parseEcoreObject(lObject, pVisited);
		}
		if (pObject instanceof EClass) {
			EClass lClass = (EClass) pObject;
			for (EClass lSuperType : lClass.getESuperTypes()) {
				parseEcoreObject(lSuperType, pVisited);
			}
		}
	}

	private void initWorkingSets(EObject pObject) {
		initWorkingSetsMappings(pObject);
		if (pObject == null || !(pObject instanceof EModelElement)) {
			return;
		}
		EModelElement lModelElement = (EModelElement) pObject;
		EAnnotation lAnnotation = lModelElement.getEAnnotation(PROFILE_BUILDER_MAPPING);
		if (lAnnotation != null) {
			return;
		}
		lAnnotation = lModelElement.getEAnnotation(PROFILE_BUILDER_STEREOTYPE);
		if (lAnnotation != null && lModelElement instanceof EClass) {
			mStereotypes.add((EClass) lModelElement);
			return;
		}
		lAnnotation = lModelElement.getEAnnotation(PROFILE_BUILDER_STEREOTYPE_HELPER);
		if (lAnnotation != null && lModelElement instanceof EClass) {
			mStereotypeHelpers.add((EClass) lModelElement);
		}
	}

	private void initWorkingSetsMappings(EObject pObject) {
		if (pObject == null || !(pObject instanceof EModelElement)) {
			return;
		}
		EModelElement lModelElement = (EModelElement) pObject;
		EAnnotation lAnnotation = lModelElement.getEAnnotation(PROFILE_BUILDER_MAPPING);
		if (lAnnotation != null) {
			initWorkingSetsFromMappingAnnotation(lModelElement, lAnnotation);
			return;
		}
		// check for UML super type
		if (lModelElement instanceof EClass) {
			EClass lElementClass = (EClass) lModelElement;
			EList<EClass> lSuperTypes = lElementClass.getESuperTypes();
			if (!lSuperTypes.isEmpty()) {
				EClass lSuperType = lSuperTypes.get(0);
				if (!isInUmlPackage(lElementClass) && isInUmlPackage(lSuperType)) {
					out("uml super " + lModelElement + ", " + lSuperType);
					mElementMetaclassMap.put(lSuperType, lSuperType);
				}
			}
		}
	}
	
	private void initWorkingSetsFromMappingAnnotation(EModelElement lModelElement, EAnnotation lAnnotation) {
		EList<EObject> lReferences = lAnnotation.getReferences();
		EObject lSource = lModelElement;
		EObject lTarget = null;
		if (lReferences.size() == 2) {
			lSource = lReferences.get(0);
			lTarget = lReferences.get(1);
		} else if (lReferences.size() == 1) {
			lTarget = lReferences.get(0);
		} else {
			error("invalid '" + PROFILE_BUILDER_MAPPING + "' annotation in element '" + lModelElement + "'");
		}
		if (lTarget.eIsProxy()) {
			return;
		}
		if (!(lTarget instanceof EClassifier)) {
			error("mapping target for model-element '" +  lModelElement + "' must be an uml named element");
		}
		EClassifier lTargetClass = (EClassifier)lTarget;
		if (!isInUmlPackage(lTargetClass)) {
			error("mapping target for model-element '" +  lModelElement + "' must be in the uml-package '" + UMLPackage.eNS_URI + "'");
		}
		mElementMetaclassMap.put(lSource, lTargetClass);
	}

	private boolean isInUmlPackage(EObject pObject) {
		return isInPackage(UMLPackage.eINSTANCE, pObject);
	}

	private boolean isInPackage(EPackage pPackage, EObject pObject) {
		EObject lContainer = pObject.eContainer();
		if (lContainer instanceof EPackage) {
			EPackage lPackage = (EPackage) lContainer;
			return lPackage.getNsURI().equals(pPackage.getNsURI());
		}
		return false;
	}

	private void error(String pString) {
		throw new IllegalArgumentException(pString);
	}

	private boolean isElementExtension(EClass pClass) {
		EClass lSuperType = getSuperType(pClass);
		if (lSuperType == null) {
			return false;
		}
		if (mElementMetaclassMap.containsKey(pClass)) {
			return true;
		}
		return isElementExtension(lSuperType);
	}

	private void buildGeneralizations(EClass pClass) {
		EClass lSuperType = getSuperType(pClass);

		Stereotype lSuperStereotype = mClassStereotypeMap.get(lSuperType);
		Stereotype lDerivedStereotype = mClassStereotypeMap.get(pClass);
		if (lSuperStereotype != null && lDerivedStereotype != null) {
			UmlModelBuilder.createGeneralization(lDerivedStereotype, lSuperStereotype);
		}
	}

	private EClass getSuperType(EClass pClass) {
		EList<EClass> lSuperTypes = pClass.getESuperTypes();
		if (lSuperTypes.isEmpty()) {
			return null;
		}
		EClass lSuperType = lSuperTypes.get(0);
		return lSuperType;
	}

	private void buildStereotype(EClass pClass) {
		String lName = pClass.getName();
		boolean lAbstract = pClass.isAbstract();
		Stereotype lStereotype = createStereotype(mProfile, lName, lAbstract);
		mClassStereotypeMap.put(pClass, lStereotype);
		
		String lSource = isElementExtension(pClass) ? PROFILE_BUILDER_INFO_EXTENSION : PROFILE_BUILDER_INFO_AGGREGATION;
		EAnnotation lAnnotation = lStereotype.getEAnnotation(lSource);
		if (lAnnotation == null) {
			lAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			lAnnotation.setSource(lSource);
			lStereotype.getEAnnotations().add(lAnnotation);
		}
		out("Creating stereotype '" + lName + "' properties ...");

		for (EStructuralFeature lFeature : pClass.getEStructuralFeatures()) {
			Type lAttrType = getType(lFeature.getEType());
			if (lAttrType != null) {
				Property lProperty = UmlModelBuilder.createAttribute(lStereotype, lFeature.getName(), lAttrType, 0, 1);
				String lDefault = lFeature.getDefaultValueLiteral();
				if (lDefault != null) {
					lProperty.setStringDefaultValue(lDefault);
				}
			}
		}
	}


	private void buildClassifier(EPackage pPackage, EClassifier pClassifier) {
		if (!isInPackage(pPackage, pClassifier)) {
			// get type to assure an import
			getType(pClassifier);
			return;
		}
		// create types define in the profile
		if (pClassifier instanceof EEnum) {
			buildEnumTypes((EEnum) pClassifier);
		} else if (pClassifier instanceof EDataType) {
			buildBaseTypes((EDataType) pClassifier);
		} else {
			buildClassType((EClass) pClassifier);
		}
	}

	private void buildClassType(EClass pClass) {
		// TODO: implement profile defined classes
		throw new UnsupportedOperationException();
	}

	private void buildBaseTypes(EDataType pType) {
		// TODO: implement profile defined data-types
		throw new UnsupportedOperationException();
	}

	private void buildEnumTypes(EEnum pEnum) {
		Enumeration lEnumeration = UmlModelBuilder.createEnumeration(mProfile, pEnum.getName());
		mTypeMap.put(lEnumeration.getName(), lEnumeration);
		
		out("Creating enumeration '" + pEnum.getName() + "' literals...");

		for (EEnumLiteral lLiteral : pEnum.getELiterals()) {
			UmlModelBuilder.createEnumerationLiteral(lEnumeration, lLiteral.getName());
		}
	}

	private Type getType(EClassifier pType) {
		String lTypeName;
		EClassifier lMappedType = mElementMetaclassMap.get(pType);
		if (lMappedType == null) {
			//throw new RuntimeException("can't map type '" + pType + "' to uml type");
			lTypeName = pType.getName();
		} else {
			lTypeName = lMappedType.getName();
		}
		Type lType = mTypeMap.get(lTypeName);
		// check for primitive type, if nothing found
		if (lType == null) {
			lType = importType(mProfile, lTypeName);
			mTypeMap.put(lTypeName, lType);
		}
		return lType;
	}
}
