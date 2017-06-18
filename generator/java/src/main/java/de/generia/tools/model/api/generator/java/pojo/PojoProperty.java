package de.generia.tools.model.api.generator.java.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.generator.java.base.JavaProperty;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class PojoProperty extends JavaProperty {
	
	private static Set<String> KEYWORDS;

	static {
		KEYWORDS = new HashSet<String>();
		KEYWORDS.add("enum");
		KEYWORDS.add("class");
		KEYWORDS.add("interface");
		KEYWORDS.add("package");
		KEYWORDS.add("abstract");
		KEYWORDS.add("transient");
	}
	
	public PojoProperty(TrafoGenerator pGenerator, PojoClass pContext, EStructuralFeature pFeature) {
		super(pGenerator, pContext, pFeature);
	}
	
	public String getTransient() {
		if (mFeature.isTransient()) {
			return " transient";
		}
		return "";
	}
	
	
	public String getFieldName() {
		String fieldName = super.getFieldName();
		if (KEYWORDS.contains(fieldName)) {
			fieldName = "_" + fieldName;
		}
		return fieldName;
	}

	public String getCastToFieldType() {
		EClass lEmbedded = getEmbeddedClass();
		if (lEmbedded != null) {
			return "(" + getFieldType() + ")";
		}
		return "";
	}
	
	public String getDefaultClause() {
		String lDefaultClause = " = ";
		EClassifier lType = mFeature.getType();
		
		if (mFeature.isMany()) {
			String lCollectionTypeImpl = getCollectionTypeImpl();
			String lTypeName = getType(lType);
			String lSimpleSet = "new " + lCollectionTypeImpl + "<" + lTypeName + ">()";
			return " = " + lSimpleSet;
		}
		String lDefault = mFeature.getDefaultValueLiteral();

		if (lType instanceof EEnum) {
			lDefaultClause += lDefault == null ? "null" : lType.getName() + "." + lDefault; 
		
		} else if (lType instanceof EClass) {
			lDefaultClause += "null";
			
		} else if (lType instanceof EDataType) {
			EDataType lDataType = (EDataType)lType;
			if (getClassName(lDataType).equals(String.class.getName())) {
				if (lDefault != null && lDefault.equals("\"\"")) {
					lDefaultClause += lDefault;
				} else if (lDefault == null) {
					lDefaultClause = "";
				} else {
					lDefaultClause += lDefault == null ? "null" : "\"" + lDefault + "\"";
				}
			} else if (isPrimitive(getInstanceTypeName(lDataType))) {
				lDefaultClause = lDefault == null ? "" : lDefaultClause + lDefault;
			} else {
				lDefaultClause += lDefault == null ? "null" : "{" + getFieldName() + " = new " + lDataType.getInstanceTypeName() + "(); " + getFieldName() + ".fromString(\"" + lDefault + "\");}"; 
			}
		}
		return lDefaultClause;
	}


	@Override
	protected List<EAnnotation> getDefaultAnnotations() {
		List<EAnnotation> lAnnotations = super.getDefaultAnnotations();
		return lAnnotations;
	}
}