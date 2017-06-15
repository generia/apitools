package de.generia.tools.model.api.generator.java.pojo;

import java.util.List;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.generator.java.base.JavaClass;
import de.generia.tools.model.api.generator.java.base.JavaEnum;
import de.generia.tools.model.api.generator.java.base.JavaPackage;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class PojoPackage extends JavaPackage {

	public PojoPackage(TrafoGenerator pGenerator, EPackage pPackage) {
		this(pGenerator, null, pPackage);
	}

	public PojoPackage(TrafoGenerator pGenerator, PojoPackage pContext, EPackage pPackage) {
		super(pGenerator, pContext, pPackage);
	}

	
	@SuppressWarnings("unchecked")
	public List<JavaClass> getClasses() {
		return wrapFilterList(EClass.class, PojoClass.class, mPackage.getClassifiers());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaPackage> getPackages() {
		return wrapList(EPackage.class, getClass(), mPackage.getSubPackages());
	}

	@SuppressWarnings("unchecked")
	public List<JavaEnum> getEnums() {
		return wrapFilterList(EEnum.class, PojoEnum.class, mPackage.getClassifiers());
	}
}
