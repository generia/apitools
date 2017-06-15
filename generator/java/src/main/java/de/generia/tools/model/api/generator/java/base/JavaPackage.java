package de.generia.tools.model.api.generator.java.base;

import java.util.List;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class JavaPackage extends AbstractJavaComponent {
	protected EPackage mPackage;
	public JavaPackage(TrafoGenerator pGenerator, EPackage pPackage) {
		this(pGenerator, null, pPackage);
	}

	public JavaPackage(TrafoGenerator pGenerator, JavaPackage pContext, EPackage pPackage) {
		super(pGenerator, pContext);
		mPackage = pPackage;
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaPackage> getPackages() {
		return wrapList(EPackage.class, getClass(), mPackage.getSubPackages());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaEnum> getEnums() {
		return wrapFilterList(EEnum.class, JavaEnum.class, mPackage.getClassifiers());
	}
	
	@SuppressWarnings("unchecked")
	public List<JavaClass> getClasses() {
		return wrapFilterList(EClass.class, JavaClass.class, mPackage.getClassifiers());
	}
}
