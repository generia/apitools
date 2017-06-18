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
import de.generia.tools.model.api.EReference;
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
		
		EClass lSuperType = mClass.getSuperType();
		if (lSuperType != null) {
			String lClassName = getClassName(lSuperType);
			lImports.add(lClassName);
		}
		
		for (EStructuralFeature lFeature : mClass.getStructuralFeatures()) {
			addImport(lImports, lFeature);
		}
		for (EOperation lOperation : mClass.getOperations()) {
			addImport(lImports, lOperation);
			for (EParameter lParameter : lOperation.getParameters()) {
				addImport(lImports, lParameter);
			}
		}
		return lImports;
	}

	protected void addImport(Set<String> pImports, ETypedElement pElement) {
		EClassifier lType = pElement.getType();
		if (lType == null) {
			return;
		}
		String lCollectionType = getCollectionType(pElement);
		if (lCollectionType != null) {
			if (pElement instanceof EReference) {
				pImports.add(java.util.Set.class.getName());
			} else {
				pImports.add(java.util.List.class.getName());
			}
		}
		if (isDataType(lType)) {
			String lInstanceTypeName = lType.getInstanceTypeName();
			if (lInstanceTypeName.equals("void")) {
				return;
			}
			isPrimitive(lInstanceTypeName);
		} else {
			// check, if the property-type is in the package of the class
			if (lType.getParent() == mClass.getParent()) {
				return;
			}

			// check, if the property-type is in the class
			if (lType.getParent() == mClass) {
				return;
			}

			// check, if the property-type is the class
			if (lType == mClass) {
				return;
			}
		}
		String lClassName = getClassName(lType);
		if (lClassName.startsWith("java.lang.")) {
			return;
		}
		pImports.add(lClassName);
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
