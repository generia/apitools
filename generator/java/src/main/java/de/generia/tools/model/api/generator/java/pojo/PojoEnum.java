package de.generia.tools.model.api.generator.java.pojo;

import java.util.List;

import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.generator.java.base.JavaEnum;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class PojoEnum extends JavaEnum {
	
	public static class PojoEnumLiteral extends JavaEnumLiteral {

		public PojoEnumLiteral(TrafoGenerator pGenerator, PojoEnum pContext, EEnumLiteral pEnumLiteral) {
			super(pGenerator, pContext, pEnumLiteral);
		}
	}
	
	public PojoEnum(TrafoGenerator pGenerator, PojoPackage pContext, EEnum pEnum) {
		super(pGenerator, pContext, pEnum);
	}
	
	public PojoEnum(TrafoGenerator pGenerator, PojoClass pContext, EEnum pEnum) {
		super(pGenerator, pContext, pEnum);
	}
	

	@SuppressWarnings("unchecked")
	public List<JavaEnumLiteral> getLiterals() {
		return wrapList(EEnumLiteral.class, PojoEnumLiteral.class, mEnum.getLiterals());
	}
}
