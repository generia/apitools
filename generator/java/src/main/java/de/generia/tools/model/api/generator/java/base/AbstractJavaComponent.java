package de.generia.tools.model.api.generator.java.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.generator.java.AbstractApiGeneratorComponent;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;
import de.generia.tools.model.api.util.AnnotationUtil;

public abstract class AbstractJavaComponent extends AbstractApiGeneratorComponent {

	public static final String IMPL_SUFFIX = "Impl";
	public static final String IMPL_PACKAGE = "impl";

	public AbstractJavaComponent(TrafoGenerator pGenerator, AbstractApiGeneratorComponent pContext) {
		super(pGenerator, pContext);
	}
	
	public File getFile(EClassifier pClassifier) {
		// inner classifiers wont need a file
		if (isNestedClassifier()) {
			return null;
		}
		File lOutputDir = generator().getJavaOutputDir();
		String lPath = getClassName(pClassifier).replace('.', '/');
		return new File(lOutputDir, lPath + ".java");
	}
	

	public boolean isNestedClassifier() {
		EModelElement lModelNode = getModelNode();
		if (lModelNode == null) {
			return false;
		}
		if (!(lModelNode instanceof  EClassifier)) {
			return false;
		}
		EClassifier classifier = (EClassifier) lModelNode;
		for (EClassifier pkgClassifier : classifier.getPackage().getClassifiers()) {
			if (pkgClassifier.equals(classifier)) {
				return false;
			}
		}
		return true;
	}

	public boolean isPrimitive(String pInstanceTypeName) {
		Class<?> lClass;
		try {
			lClass = Class.forName(pInstanceTypeName);
			return lClass.isPrimitive();
		} catch (ClassNotFoundException e) {
			// just return, since we only wanted to check primitives
			return true;
		}
	}
	
	public static String getPackage(EModelElement pNode) {
		if (isDataType(pNode)) {
			EDataType lDataType = (EDataType)pNode;
			String lType = lDataType.getInstanceTypeName();
			int i = lType.lastIndexOf(".");
			String lPackage = null;
			if (i != -1) {
				lPackage = lType.substring(0, i);
			}
			return lPackage;
		}
		if (!(pNode instanceof EClassifier)) {
			throw new IllegalArgumentException("can't find package for model-element '" + pNode + "'");
		}
		EPackage pkg = ((EClassifier)pNode).getPackage();
		return getPackageName(pkg);
	}
	
	private static String getPackageName(EPackage pkg) {
		if (pkg == null) {
			return null;
		}
		String pkgName = pkg.getName();
		if (pkg.getSuperPackage() != null) {
			String superName = getPackageName(pkg.getSuperPackage());
			if (superName != null) {
				pkgName = superName + "." + pkgName;
			}
		}
		return pkgName;
	}

	public static String getClassName(EClassifier pClassifier) {
		if (isDataType(pClassifier)) {
			EDataType lDataType = (EDataType)pClassifier;
			String instanceType = lDataType.getInstanceTypeName();
			if (instanceType == null || instanceType.isEmpty()) {
				throw new IllegalArgumentException("can't get instance-type from data-type '" + pClassifier + "'");
			}
			return instanceType;
		}
		EModelElement lContainer = pClassifier.getParent();
		String lPrefix = "";
		if (lContainer instanceof EPackage) {
			lPrefix = getPackage(pClassifier);
		} else if (lContainer instanceof EClassifier) {
			lPrefix = getClassName((EClassifier) lContainer);
		} else {
			throw new IllegalArgumentException("can't get class-name for classifier '" + pClassifier + "', expected another classifier or package as direct parent of the node");
		}
		return lPrefix + "." + pClassifier.getName();
	}
	
	public static String getImplPackage(EClass pNode) {
		EModelElement lContainer = pNode.getParent();
		while (!(lContainer instanceof EPackage)) {
			//throw new IllegalArgumentException("can't get package for node '" + pNode + "', expected package as direct parent of the node");
			lContainer = lContainer.getParent();
		}
		String lNodeName = getNodeName(lContainer);
		return lNodeName.toLowerCase().replace('/', '.') + "." + IMPL_PACKAGE;
	}

	public static String getImplClassName(EClass pClass) {
		EModelElement lContainer = pClass.getParent();
		String lPrefix = "";
		if (lContainer instanceof EPackage) {
			lPrefix = getImplPackage(pClass);
		} else if (lContainer instanceof EClass) {
			lPrefix = getImplClassName((EClass) lContainer);
		} else {
			throw new IllegalArgumentException("can't get impl class-name for class '" + pClass + "', expected another classifier or package as direct parent of the node");
		}
		return lPrefix + "." + pClass.getName() + IMPL_SUFFIX;
	}

	public static String getEntityName(EClass pClass) {
		return pClass.getName();
	}

	public static String getFieldName(EStructuralFeature pFeature) {
		return pFeature.getName();
	}

	protected EClassifier getTopLevelClassifier(EClassifier pClassifier) {
		if (pClassifier == null) {
			return null;
		}
		EModelElement lContainer = pClassifier.getParent();
		if (lContainer instanceof EPackage) {
			return pClassifier;
		}
		return getTopLevelClassifier((EClassifier) lContainer);
	}

	protected static boolean isDataType(EModelElement pNode) {
		if (pNode == null) {
			return false;
		}
		return pNode instanceof EDataType;
	}

	public String getCollectionType(ETypedElement pElement) {
		if (pElement.isMany()) {
			if (pElement.isOrdered()) {
				return "List";
			}
			return "Set";
		}
		return null;
	}
	
	public String getCollectionTypeImpl(ETypedElement pElement) {
		if (pElement.isMany()) {
			if (pElement.isOrdered()) {
				return "ArrayList";
			}
			return "HashSet";
		}
		return null;
	}
	
	protected EClassifier getFeatureType(EStructuralFeature pFeature) {
		EClassifier lType = pFeature.getType();
		if (lType == null) {
			throw new NullPointerException("type of feature '" + getNodeName(pFeature) + "' is null");
		}
		return lType;
	}

	public String getType(EClassifier pClassifier) {
		String lClassName = getClassName(pClassifier);
		String lPackage = getPackage(pClassifier);
		String lType = lClassName;
		if (lPackage != null) {
			if (!lClassName.startsWith(lPackage)) {
				throw new RuntimeException("can't strip package '" + lPackage + "' name from class-name '" + lClassName + "'"); 
			}
			lType = lClassName.substring(lPackage.length()+1);
		}
		return lType;
	}
	
	public String getFeatureType(ETypedElement pElement, String pDefault) {
		String lType = pDefault;
		EClassifier lClassifier = pElement.getType();
		if (lClassifier != null) {
			lType = getType(lClassifier);
			if (pElement.isMany()) {
				lType = getCollectionType(pElement) + "<" + lType + ">";
			}
		}
		if (lType == null) {
			throw new NullPointerException("type of element '" + getNodeName(pElement) + "' is null");
		}
		return lType;
	}
	
	public String getAnnotations() {
		StringBuffer lBuffer = new StringBuffer();
		EModelElement lNode = (EModelElement) getModelNode();
		List<EAnnotation> lDeclaredAnnotations = lNode.getAnnotations();
		List<EAnnotation> lDefaultAnnotations = getDefaultAnnotations();
		Collection<EAnnotation> lAnnotations = AnnotationUtil.mergeAnnotations(lDefaultAnnotations, lDeclaredAnnotations); 
		for (EAnnotation lAnnotation : lAnnotations) {
			lBuffer.append(getAnnotation(lAnnotation)).append("\n");
		}
		return lBuffer.toString();
	}

	protected List<EAnnotation> getDefaultAnnotations() {
		return new ArrayList<EAnnotation>();
	}

	private String getAnnotation(EAnnotation pAnnotation) {
		String lAnnotation = "@" + pAnnotation.getSource();
		String lSep = "(";
		boolean lHasContent = false;
		for (EAnnotation lSubAnnotation : pAnnotation.getAnnotations()) {
			if (!lHasContent) {
				lAnnotation += lSep;
				lSep = "{\n\t";
			}
			lAnnotation += lSep + getAnnotation(lSubAnnotation);
			lSep = ",\n\t";
			lHasContent = true;
		}
		if (lHasContent) {
			if (!pAnnotation.getAnnotations().isEmpty()) {
				lAnnotation += "\n}";
			}
			lAnnotation += ")";
		}
		return lAnnotation;
	}
}
