package de.generia.tools.model.xecore.model.basetype;


public class HashMap extends java.util.HashMap<String,String> {
	private static final long serialVersionUID = 1L;

	public HashMap(String pValue) {
		fromString(pValue);
	}

	public HashMap() {
	}

	private void fromString(String pValue) {
		clear();
		// strip surrounding braces
		String lValue = pValue.substring(1,pValue.length()-1);
		String[] lKeyValues = lValue.split(",");
		for (String lKeyValue : lKeyValues) {
			String[] lPair = lKeyValue.split("=");
			String lKey = lPair[0].trim();
			String lVal = null;
			if (lPair.length > 1) {
				lVal = lPair[1].trim();
			}
			put(lKey, lVal);
		}
	}

	@Override
	public String toString() {
		String lString = super.toString();
		lString = lString.replace('\n', ' ');
		return lString;
	}
}
