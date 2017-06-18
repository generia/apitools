package de.generia.tools.model.api.generator.java.base;

import java.io.File;
import java.util.List;

import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class JavaEnum extends AbstractJavaComponent {
	protected EEnum mEnum;
	
	public static class JavaEnumLiteral extends AbstractJavaComponent {
		EEnumLiteral mEnumLiteral;
		public JavaEnumLiteral(TrafoGenerator pGenerator, JavaEnum pContext, EEnumLiteral pEnumLiteral) {
			super(pGenerator, pContext);
			mEnumLiteral = pEnumLiteral;
		}
		
		public String getSimpleName() {
			return mEnumLiteral.getName();
		}
		
		public int getValue() {
			return mEnumLiteral.getValue();
		}
		
		@Override
		public EModelElement getModelNode() {
			return mEnumLiteral;
		}
	}
	
	public JavaEnum(TrafoGenerator pGenerator, JavaPackage pContext, EEnum pEnum) {
		super(pGenerator, pContext);
		mEnum = pEnum;
	}
	
	public JavaEnum(TrafoGenerator pGenerator, JavaClass pContext, EEnum pEnum) {
		super(pGenerator, pContext);
		mEnum = pEnum;
		setIndent(true);
	}
	
	public File getFile() {
		return getFile(mEnum);
	}

	@Override
	public EModelElement getModelNode() {
		return mEnum;
	}
	
	public String getClassSimpleName() {
		return getNodeSimpleName();
	}
	
	public String getPackage() {
		return getPackage(mEnum);
	}

	@SuppressWarnings("unchecked")
	public List<JavaEnumLiteral> getLiterals() {
		return wrapList(EEnumLiteral.class, JavaEnumLiteral.class, mEnum.getLiterals());
	}
}
