package de.generia.tools.model.api.generator.java.pojo;

import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.generator.java.base.JavaEnum;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class PojoEnum extends JavaEnum {
	
	public PojoEnum(TrafoGenerator pGenerator, PojoPackage pContext, EEnum pEnum) {
		super(pGenerator, pContext, pEnum);
	}
	
	public PojoEnum(TrafoGenerator pGenerator, PojoClass pContext, EEnum pEnum) {
		super(pGenerator, pContext, pEnum);
	}
}
