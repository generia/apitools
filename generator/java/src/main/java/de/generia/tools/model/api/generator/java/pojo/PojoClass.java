package de.generia.tools.model.api.generator.java.pojo;

import java.util.List;
import java.util.Set;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EOperation;
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
	
	public String getClassKeyword() {
		return mClass.isInterface() ? "interface" : "class";
	}

	protected void addCollectionTypeImports(Set<String> pImports, ETypedElement pElement) {
		super.addCollectionTypeImports(pImports, pElement);
		
		String lCollectionTypeImpl = getCollectionTypeImpl(pElement);
		if (lCollectionTypeImpl != null && !mClass.isInterface()) {
			if (pElement instanceof ETypedElement) {
				ETypedElement lTypedElement = pElement;
				if (lTypedElement.isOrdered()) {
					pImports.add(java.util.ArrayList.class.getName());
				} else {
					pImports.add(java.util.HashSet.class.getName());
				}
			}
		}
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
