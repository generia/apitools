package de.generia.tools.model.api.generator.java.base;

import java.util.StringTokenizer;

public class Name implements Comparable<Name> {
	protected String mName;
	protected final static String NAME_SEP = "/";

	public Name() {
		mName = "";
	}
	
	public Name(String pName) {
		mName = pName;
	}

	public Name(Class<?> pClass) {
		mName = pClass.getName().replace('.', '/');
	}

	public Name(Package pPackage) {
		mName = pPackage.getName().replace('.', '/');
	}

	public Name getContextName() {
		int i = mName.lastIndexOf(NAME_SEP);
		if (i == -1) {
			return null;
		}
		return new Name(mName.substring(0, i));
	}

	public String getSimpleName() {
		int i = mName.lastIndexOf(NAME_SEP);
		if (i == -1) {
			return mName;
		}
		return mName.substring(i+1);
	}

	public String getFirstName() {
		int i = mName.indexOf(NAME_SEP);
		if (i == -1) {
			return mName;
		}
		return mName.substring(0, i);
	}

	public Name getNextName() {
		int i = mName.indexOf(NAME_SEP);
		if (i == -1) {
			return null;
		}
		return new Name(mName.substring(i+1));
	}

	public Name append(String pNodeName) {
		return new Name(mName + NAME_SEP + pNodeName);
	}

	public Name append(Name pName) {
		return new Name(mName + NAME_SEP + pName);
	}

	public String[] getPath() {
		StringTokenizer lTokenizer = new StringTokenizer(mName, NAME_SEP);
		String[] lPath = new String[lTokenizer.countTokens()];
		for (int i=0; i<lPath.length; i++) {
			lPath[i] = lTokenizer.nextToken();
		}
		return lPath;
	}

	public String toString() {
		return mName;
	}
	
	public Name getClosestCommonContext(Name pName) {
		if (pName == null) {
			return null;
		}
		String[] lPath = pName.getPath();
		String lContext = "";
		for (int i=0; i<lPath.length; i++) {
			String lPrefix = lContext + (i>0 ? NAME_SEP : "") + lPath[i];
			if (!mName.startsWith(lPrefix)) {
				return (lContext.equals("") ? null : new Name(lContext));
			}
			lContext = lPrefix;
		}
		return new Name(lContext);
	}
	
	public int hashCode() {
		return mName.hashCode();
	}
	
	public boolean equals(Object pObject) {
		String lName = null;
		if (pObject instanceof Name) {
			lName = ((Name)pObject).mName;
		} else if (pObject instanceof String) {
			lName = (String)pObject;
		}
		if (lName == null) {
			return false;
		}
		return mName.equals(lName);
	}
	
	public int compareTo(Name pName) {
		return this.toString().compareTo(pName.toString());
	}
	
	public boolean startsWith(Name pPrefix) {
		return mName.startsWith(pPrefix.toString());
	}
	
	public boolean endsWith(Name pPostfix) {
		return mName.endsWith(pPostfix.toString());
	}
	
	public Name getNameAfter(Name pPrefix) {
		String lPrefix = pPrefix.toString();
		if (mName.equals(lPrefix)) {
			return null;
		}
		if (!mName.startsWith(lPrefix)) {
			return null;
		}
		String lSubName = mName.substring(lPrefix.length()+1);
		return new Name(lSubName);
	}
	
	public String getClassName() {
		String lPackage = getPackage();
		if (lPackage.equals("")) {
			return getSimpleName();
		}
		return lPackage + "." + getSimpleName();
	}
	
	public String getPackage() {
		Name lContextName = getContextName();
		if (lContextName == null) {
			return "";
		}
		return lContextName.toString().replace(NAME_SEP.charAt(0), '.').toLowerCase();
	}
	
	
	public String toUri() {
		return mName;
	}

	
	public static String toConstName(String pMethodName) {
		StringBuffer lJavaConst = new StringBuffer();
		boolean lIsPrevLowerCase = true;
		for (int i=0; i<pMethodName.length(); i++) {
			char lChar = pMethodName.charAt(i);
			if (i > 0 && lIsPrevLowerCase && Character.isUpperCase(lChar)) {
				lJavaConst.append('_');
			}
			lIsPrevLowerCase = Character.isLowerCase(lChar);
			lJavaConst.append(Character.toUpperCase(lChar));
		}
		return lJavaConst.toString();
	}

	public static String toJavaDocName(String pName) {
		StringBuffer lJavaConst = new StringBuffer();
		boolean lIsPrevLowerCase = true;
		for (int i=0; i<pName.length(); i++) {
			char lChar = pName.charAt(i);
			if (i > 0 && lIsPrevLowerCase && Character.isUpperCase(lChar)) {
				lJavaConst.append('-');
			}
			lIsPrevLowerCase = Character.isLowerCase(lChar);
			lJavaConst.append(Character.toLowerCase(lChar));
		}
		return lJavaConst.toString();
	}
}
