package de.generia.tools.model.xecore.binding;

public class LowerCaseNamingConvention implements NamingConvention {

	@Override
	public String toClassName(String pElementName) {
//		String[] lTokens = pElementName.toLowerCase().split("-");
//		StringBuilder lClassName = new StringBuilder();
//		for (String lToken : lTokens) {
//			lClassName.append(Character.toUpperCase(lToken.charAt(0)));
//			lClassName.append(lToken.substring(1));
//		}
//		return lClassName.toString();
		return pElementName;
	}

	@Override
	public String toPropertyName(String pPropertyName) {
		String[] lTokens = pPropertyName.toLowerCase().split("-");
		StringBuilder lClassName = new StringBuilder();
		boolean lIsFirstToken = true;
		for (String lToken : lTokens) {
			if (lIsFirstToken) {
				lClassName.append(lToken);
				lIsFirstToken = false;
			} else {
				lClassName.append(Character.toUpperCase(lToken.charAt(0)));
				lClassName.append(lToken.substring(1));
			}
		}
		return lClassName.toString();
	}

	@Override
	public String toAttributeName(String pPropertyName) {
		return toLowerCase(pPropertyName);
	}

	@Override
	public String toElementName(String pClassName) {
		return pClassName;
	}

	private String toLowerCase(String pName) {
		StringBuilder lLowerCaseName = new StringBuilder();
		boolean lIsPrevLowerCase = true;
		for (int i=0; i<pName.length(); i++) {
			char lChar = pName.charAt(i);
			if (i > 0 && lIsPrevLowerCase && Character.isUpperCase(lChar)) {
				lLowerCaseName.append('-');
			}
			lIsPrevLowerCase = Character.isLowerCase(lChar);
			lLowerCaseName.append(Character.toLowerCase(lChar));
		}
		return lLowerCaseName.toString();
	}
}
