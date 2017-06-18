package de.generia.tools.model.api.generator.java.base;

import java.util.HashMap;
import java.util.Map;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class JavaProperty extends AbstractJavaComponent {
	protected EStructuralFeature mFeature;
	

	@Override
	public EModelElement getModelNode() {
		return mFeature;
	}

	public JavaProperty(TrafoGenerator pGenerator, JavaClass pContext, EStructuralFeature pFeature) {
		super(pGenerator, pContext);
		mFeature = pFeature;
		setIndent(true);
	}
	
	public String getName() {
		return Name.firstUp(mFeature.getName());
	}
	
	public String getFieldType() {
		EClass lEmbedded = getEmbeddedClass();
		if (lEmbedded != null) {
			String lEmbeddedClassName = getClassName(lEmbedded);
			return lEmbeddedClassName;
		}
		return getType();
	}
	
	protected EClass getEmbeddedClass() {
		return null;
	}

	public String getFieldName() {
		return getFieldName(mFeature);
	}

	public String getGetterName() {
		String name = getName();
		String type = getType();
		if (type.toLowerCase().equals("boolean")) {
			return "is" + name;
		}
		return "get" + name;
	}

	public String getConstName() {
		return Name.toConstName(mFeature.getName());
	}
	
	public String getType() {
		return getFeatureType(mFeature, null);
	}
	
	public boolean isScalarType() {
		return !mFeature.isMany();
	}
	
	public boolean isOptional() {
		return !mFeature.isRequired();
	}

	public String getCollectionType() {
		return getCollectionType(mFeature);
	}
	
	public String getCollectionTypeDoc() {
		return getCollectionType().toLowerCase();
	}
	
	public String getCollectionTypeImpl() {
		return getCollectionTypeImpl(mFeature);
	}

	public boolean getNeedSetter() {
		if (mFeature instanceof EAttribute) {
			return true;
		}
		return !mFeature.isMany();
	}
	
	public String getModelAnnotation() {
		boolean lId = false;
		boolean lContainment = false;
		boolean lMany = mFeature.isMany();
		boolean lRequired = mFeature.isRequired();
		boolean lOrdered = mFeature.isOrdered();
		boolean lTransient = mFeature.isTransient();
		String lOpposite = null;
		String lType = lMany ? getType(mFeature.getType()) : null;
		if (mFeature instanceof EAttribute) {
			lId = ((EAttribute)mFeature).isId();
		} else {
			lContainment = ((EReference)mFeature).isContainment();
			EReference lOppositeReference = ((EReference)mFeature).getOpposite();
			lOpposite = lOppositeReference != null ? lOppositeReference.getName() : null;
		}
		Map<String, String> lFieldMap = new HashMap<String, String>();
		if (lId) {
			lFieldMap.put("id", "true");
		}
		if (lContainment) {
			lFieldMap.put("containment", "true");
		}
		if (lMany) {
			lFieldMap.put("many", "true");
		}
		if (lRequired) {
			lFieldMap.put("required", "true");
		}
		if (!lOrdered) {
			lFieldMap.put("ordered", "false");
		}
		if (lTransient) {
			lFieldMap.put("isTransient", "true");
		}
		if (lOpposite != null) {
			lFieldMap.put("opposite", "\"" + lOpposite + "\"");
		}
		if (lType != null) {
			lFieldMap.put("type", lType + ".class");
		}
		if (lFieldMap.isEmpty()) {
			return "";
		}
		StringBuilder lBuilder = new StringBuilder("@de.ugis.m0.common.model.ModelProperty(");
		String lSep = "";
		for (Map.Entry<String, String> lEntry : lFieldMap.entrySet()) {
			lBuilder.append(lSep).append(lEntry.getKey());
			lBuilder.append("=").append(lEntry.getValue());
			lSep = ", ";
		}
		lBuilder.append(")\n");
		return lBuilder.toString();
	}
}
