package de.generia.tools.model.api.generator.java;

import java.util.List;

import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.generator.trafo.TrafoComponent;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public abstract class AbstractApiGeneratorComponent extends TrafoComponent {

	private static final Object JAVA_LANG_PACKAGE = "java.lang";
	private static final Object JAVA_UTIL_PACKAGE = "java.util";
	private static final Object LIB_DATATYPE_PACKAGE = "lib.basetypes";


	public AbstractApiGeneratorComponent(TrafoGenerator pGenerator, TrafoComponent pContext) {
		super(pGenerator, pContext);
	}

	protected ApiGenerator generator() {
		return (ApiGenerator)mGenerator;
	}
	

	public EModelElement getModelNode() {
		return null;
	}

	public String getNodeName() {
		EModelElement lNode = getModelNode();
		return getNodeName(lNode);
	}
	
	public static String getNodeName(EModelElement pNode) {
		if (pNode == null) {
			return null;
		}
		
		String lPrefix = "";
		if (pNode instanceof ENamedElement) {
			return lPrefix + ((ENamedElement)pNode).getName();
		}
		return lPrefix + pNode.getClass().getSimpleName();
	}
	
	public String getNodeSimpleName() {
		EModelElement lNode = getModelNode();
		if (lNode == null) {
			return null;
		}
		if (lNode instanceof ENamedElement) {
			return ((ENamedElement)lNode).getName();
		}
		return lNode.getClass().getSimpleName();
	}	

	
	@SuppressWarnings("unchecked")
	public List wrapFilterList(final Class pContentClass, Class pWrapperClass, List<? extends Object> pList) {
		List lFilteredList = generator().filterList(pList, new ContentFilter() {
			public boolean accept(Object pContent) {
				return pContentClass.isAssignableFrom(pContent.getClass());
			}
		});
		return wrapList(pContentClass, pWrapperClass, lFilteredList);
	}
}
