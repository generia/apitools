package de.generia.tools.model.xecore.binding;
public interface NamingConvention {
	String toClassName(String pElementName);
	String toPropertyName(String pAttributeName);

	String toElementName(String pClassName);
	String toAttributeName(String pPropertyName);
}
