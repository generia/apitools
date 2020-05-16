package de.generia.tools.model.api.generator.java.base;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class JavaClass extends AbstractJavaComponent {
	protected EClass mClass;

	public JavaClass(TrafoGenerator pGenerator, JavaPackage pContext, EClass pClass) {
		super(pGenerator, pContext);
		mClass = pClass;
	}
	
	public JavaClass(TrafoGenerator pGenerator, JavaClass pContext, EClass pClass) {
		super(pGenerator, pContext);
		mClass = pClass;
		setIndent(true);
	}
	
	public File getFile() {
		return getFile(mClass);
	}
	
	@Override
	public EModelElement getModelNode() {
		return mClass;
	}
	
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
		addCollectionTypeImports(pImports, pElement);
		addMapTypeImports(pImports, pElement);
		if (isDataType(lType)) {
			String lInstanceTypeName = getInstanceTypeName(lType);
			if (lInstanceTypeName == null || lInstanceTypeName.equals("void") || isPrimitive(lInstanceTypeName)) {
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

	protected void addCollectionTypeImports(Set<String> pImports, ETypedElement pElement) {
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
	}
	
	protected void addMapTypeImports(Set<String> pImports, ETypedElement pElement) {
		String lCollectionType = getMapType(pElement);
		if (lCollectionType != null) {
			if (pElement instanceof ETypedElement) {
				ETypedElement lTypedElement = pElement;
				if (lTypedElement.getKeyType() != null) {
					pImports.add(java.util.Map.class.getName());
					collectImports(lTypedElement.getKeyType(), pImports);
				}
			}
		}
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

	public String getClassName() {
		return getClassName(mClass);
	}
	
	public String getClassSimpleName() {
		return mClass.getName();
	}
	
	public String getExtendsClause() {
		if (mClass.getSuperType() == null) {
			return "";
		}
		EClass lSuperType = mClass.getSuperType();
		String lClause = "extends " + lSuperType.getName();
		return lClause + " ";
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaProperty> getProperties() {
		return wrapFilterList(EStructuralFeature.class, JavaProperty.class, mClass.getStructuralFeatures());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaEnum> getEnums() {
		return wrapFilterList(EEnum.class, JavaEnum.class, mClass.getNestedClassifiers());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaClass> getClasses() {
		return wrapFilterList(EClass.class, JavaClass.class, mClass.getNestedClassifiers());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaOperation> getOperations() {
		return wrapFilterList(EOperation.class, JavaOperation.class, mClass.getOperations());
	}
}
