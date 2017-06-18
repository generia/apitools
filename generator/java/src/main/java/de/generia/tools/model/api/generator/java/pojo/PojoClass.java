package de.generia.tools.model.api.generator.java.pojo;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.generator.java.base.JavaClass;
import de.generia.tools.model.api.generator.java.base.JavaEnum;
import de.generia.tools.model.api.generator.java.base.JavaOperation;
import de.generia.tools.model.api.generator.java.base.JavaProperty;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class PojoClass extends JavaClass {


	public PojoClass(TrafoGenerator pGenerator, PojoPackage pContext, EClass pClass) {
		super(pGenerator, pContext, pClass);
	}
	
	public PojoClass(TrafoGenerator pGenerator, PojoClass pContext, EClass pClass) {
		super(pGenerator, pContext, pClass);
	}
	
	public String getModifiers() {
		String lModifiers = "";
		String lSep = "";
		if (isNestedClassifier()) {
			lModifiers += lSep + "static";
			lSep = " ";
		}
		if (mClass.isAbstract()) {
			lModifiers += lSep + "abstract";
			lSep = " ";
		}
		return lModifiers + lSep;
	}
	
	public String getClassSimpleName() {
		return mClass.getName();
	}
	
	@Override
	public String getPackage() {
		return getPackage(mClass);
	}

	public Set<String> getImports() {
		Set<String> lImports = new TreeSet<String>();
		collectImports(mClass, lImports);
		return lImports;
	}

	private void collectImports(EClassifier pClassifier, Set<String> lImports) {
		if (pClassifier instanceof EClass) {
			EClass lClass = (EClass)pClassifier;
			EClass lSuperType = lClass.getSuperType();
			if (lSuperType != null) {
				addImport(lImports, lSuperType);
			}
			
			for (EStructuralFeature lFeature : lClass.getStructuralFeatures()) {
				addImport(lImports, lFeature);
			}
			for (EOperation lOperation : lClass.getOperations()) {
				addImport(lImports, lOperation);
				for (EParameter lParameter : lOperation.getParameters()) {
					addImport(lImports, lParameter);
				}
			}
			for (EClassifier lNestedClassifier : lClass.getNestedClassifiers()) {
				collectImports(lNestedClassifier, lImports);
			}
		}
		addImport(lImports, pClassifier);
	}

	protected void addImport(Set<String> pImports, ETypedElement pElement) {
		EClassifier lType = pElement.getType();
		if (lType == null || getClassName(lType).equals(getClassName(mClass))) {
			return;
		}
		String lCollectionType = getCollectionType(pElement);
		if (lCollectionType != null) {
			if (pElement instanceof ETypedElement) {
				ETypedElement lTypedElement = pElement;
				if (lTypedElement.isOrdered()) {
					pImports.add(java.util.List.class.getName());
				} else {
					pImports.add(java.util.Set.class.getName());
				}
			}
		}
		String lCollectionTypeImpl = getCollectionTypeImpl(pElement);
		if (lCollectionTypeImpl != null) {
			if (pElement instanceof ETypedElement) {
				ETypedElement lTypedElement = pElement;
				if (lTypedElement.isOrdered()) {
					pImports.add(java.util.ArrayList.class.getName());
				} else {
					pImports.add(java.util.HashSet.class.getName());
				}
			}
		}
		if (isDataType(lType)) {
			String lInstanceTypeName = lType.getInstanceTypeName();
			if (lInstanceTypeName.equals("void") || isPrimitive(lInstanceTypeName)) {
				return;
			}
		} else {

			// check, if the property-type is in the class
			if (lType.getParent() == mClass) {
				return;
			}
		}
		addImport(pImports, lType);
	}
	
	protected void addImport(Set<String> pImports, EClassifier pType) {
		EClassifier lTopLevelClassifier = getTopLevelClassifier(pType);
		String lClassName = getClassName(lTopLevelClassifier);
		if (lClassName.startsWith("java.lang.")) {
			return;
		}
		if (!lClassName.equals(getClassName(mClass))) {
			pImports.add(lClassName);
		}
	}
	
	public File getFile(EClassifier pClassifier) {
		// inner classifiers wont need a file
		if (isNestedClassifier()) {
			return null;
		}
		File lOutputDir = generator().getJavaOutputDir();
		String lPath = getClassName((EClass) pClassifier).replace('.', '/');
		return new File(lOutputDir, lPath + ".java");
	}
	
	public String getExtendsClause() {
		if (mClass.getSuperType() == null) {
			return "";
		}
		EClass lSuperType = mClass.getSuperType();
		String lClause = "extends " + lSuperType.getName();
		return lClause + " ";
	}

	@Override
	protected List<EAnnotation> getDefaultAnnotations() {
		List<EAnnotation> lAnnotations = super.getDefaultAnnotations();
		return lAnnotations;
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaProperty> getProperties() {
		return wrapFilterList(EStructuralFeature.class, PojoProperty.class, mClass.getStructuralFeatures());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaClass> getClasses() {
		return wrapList(EClass.class, PojoClass.class, mClass.getNestedClassifiers());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaOperation> getOperations() {
		return wrapFilterList(EOperation.class, PojoOperation.class, mClass.getOperations());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaEnum> getEnums() {
		return wrapFilterList(EEnum.class, PojoEnum.class, mClass.getNestedClassifiers());
	}
}
