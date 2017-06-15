package de.generia.tools.model.api.generator.java.base;

import de.generia.tools.model.api.EPackage;

public class MappingUtil {
	public static final String ID_COLUMN = "_ID";
	private static final String VERSION_COLUMN = "_VERSION";
	private static final String TYPE_COLUMN = "_TYPE";
	private static final String COLLECTION_INDEX_COLUMN = "COLLECTION_INDEX";

	private static String toDbName(String pName) {
		StringBuffer lJavaConst = new StringBuffer();
		boolean lIsPrevLowerCase = true;
		for (int i=0; i<pName.length(); i++) {
			char lChar = pName.charAt(i);
			if (i > 0 && lIsPrevLowerCase && Character.isUpperCase(lChar)) {
				lJavaConst.append('_');
			}
			lIsPrevLowerCase = !Character.isUpperCase(lChar);
			lJavaConst.append(Character.toUpperCase(lChar));
		}
		return lJavaConst.toString();
	}
	
	private static String mapTableRaw(String pTableName) {
		return toDbName(pTableName);
	}

	private static String mapColumnRaw(String pColumnName) {
		return toDbName(pColumnName); //pColumnName.toUpperCase();
	}

	
	public static String mapTable(String pTableName, String pPrefix, String pTableType) {
		String lPrefix = pPrefix; 
		String lType   = ""; // table-types are discareded as well -- pTableType + "_";
		if (pPrefix != null && !pPrefix.equals("") && !pPrefix.endsWith("_")) {
			lPrefix = pPrefix + "_";			
		}
		String lTableName = trim(mapTableRaw(pTableName), MAX_TABLE_LENGTH);
		return lPrefix + lType + lTableName;
	}

	public static String mapColumn(String pColumnName) {
		return trim(mapColumnRaw(pColumnName), MAX_COLUMN_LENGTH); // + C_SUFFIX;
	}

	public static String mapIdColumn(String pColumnName) {
		//return trim(mapColumnRaw(pColumnName), MAX_TABLE_LENGTH) + ID_SUFFIX;
		return ID_COLUMN;
	}

	public static String mapVersionColumn(String pColumnName) {
		//return trim(mapColumnRaw(pColumnName), MAX_TABLE_LENGTH) + VER_SUFFIX;
		return VERSION_COLUMN;
	}

	public static String mapRefColumn(String pColumnName) {
		return trim(mapColumnRaw(pColumnName), MAX_COLUMN_LENGTH) + REF_SUFFIX;
	}

	public static String mapTypeColumn(String pColumnName) {
		//return trim(mapColumnRaw(pColumnName), MAX_TABLE_LENGTH) + TYP_SUFFIX;
		return TYPE_COLUMN;
	}

	public static String mapIdxColumn(String pColumnName) {
		//return trim(mapColumnRaw(pColumnName), MAX_TABLE_LENGTH) + IDX_SUFFIX;
		return COLLECTION_INDEX_COLUMN;
	}

	public String getPackagePrefix(EPackage pPackage) {
		String lPrefix = "??"; // TODO: lComponent.getDbPrefix();
		return toDbName(lPrefix);
	}
	
//	// prefix for entity-tables
//	private static final String ENTITY_PREFIX      = "ETY";
//
//	// prefix for 1:n related tables
//	private static final String RELATED_PREFIX     = "REL";
//	
//	// prefix for n:m related tables
//	private final static String ASSOCIATION_PREFIX = "ASC";
	
	// schema for naming: (oracle has only 30 chars)
	// tables:  <comp> '_' <technical-type> '_' <name>
	//             2    1         3          1  MAX_TABLE_LENGTH <= 30
	// table-prefix is discareded
	
	// columns:   <name> '_' <suffix>
	// MAX_COLUMN_LENGTH  1      4   <= 30
	public final static int MAX_TABLE_LENGTH = 30 - 2; // leave two chars left jsut in case 
	protected final static int MAX_COLUMN_LENGTH = 30 - 5;

	protected final static String C_SUFFIX = "_C";
	protected final static String ID_SUFFIX = "_ID";
	protected final static String REF_SUFFIX = "_REF";
	protected final static String VER_SUFFIX = "_VER";
	protected final static String IDX_SUFFIX = "_IDX";
	protected final static String TYP_SUFFIX = "_TYP";

	private final static String mVocals = "aeiouAEIOU"; 
	private static String trim(String pString, int pMaxLength) {
		int lLength = pString.length();
		if (lLength <= pMaxLength) {
			return pString;
		}
		String lSuffix = "";
		String lString = pString; 
		// strip vocals
		StringBuffer lBuffer = new StringBuffer(pString);
		for (int i=lLength-1; i >= 0; i--) {
			char lChar = pString.charAt(i);
			if (mVocals.indexOf(lChar) != -1) {
				if (i > 0 && pString.charAt(i-1) != '_') {
					lBuffer.replace(i, i+1, "");
					lLength--;
				}
			}
			if (lLength <= pMaxLength) {
				return lBuffer.toString();
			}
		}

		// cut middle part
		int lCutStart = (pMaxLength+1) / 2;
		int lCutStop = pMaxLength / 2;
		lString = lBuffer.toString();
		return lString.substring(0, lCutStart) + lString.substring(lString.length()-lCutStop) + lSuffix;
	}
}
