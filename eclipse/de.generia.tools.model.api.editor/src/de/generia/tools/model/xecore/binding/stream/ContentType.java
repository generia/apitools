package de.generia.tools.model.xecore.binding.stream;

import java.util.Map;
import java.util.TreeMap;

public class ContentType {
    private String mMediaType = "";
    private String mMediaSubType = "";
    private Map<String, String> mParamMap = null;

    private ContentType() {
    	// internal use only
    }

    public ContentType(ContentType pContentType) {
    	mMediaType = pContentType.getMediaType();
    	mMediaSubType = pContentType.getMediaSubType();
    	if (pContentType.mParamMap == null) {
    		return;
    	}
    	mParamMap = new TreeMap<String, String>();
    	for (Map.Entry<String, String> lEntry : pContentType.mParamMap.entrySet()) {
    		mParamMap.put(lEntry.getKey(), lEntry.getValue());
    	}
    }

    public String getMediaType() {
        return mMediaType;
    }

    public void setMediaType(String pMediaType) {
        mMediaType = pMediaType;
    }

    public String getMediaSubType() {
        return mMediaSubType;
    }

    public void setMediaSubType(String pMediaSubType) {
        mMediaSubType = pMediaSubType;
    }

    public Map<String, String> getParamMap() {
        return mParamMap;
    }

    public void addParam(String pParam, String pValue) {
        if (mParamMap == null) {
            mParamMap = new TreeMap<String, String>();
        }
        mParamMap.put(pParam, pValue);
    }

    public String getParam(String pParam) {
    	return mParamMap.get(pParam);
    }

    public static ContentType parseContentType(String pContentType) {
        ContentType lContentType = new ContentType();

        String[] lParts = pContentType.split(";");

        if (lParts.length == 0) {
            return lContentType;
        }

        // parse type
        String lType = lParts[0];
        int i = lType.indexOf("/");
        String lMediaType = lType;
        String lMediaSubType = "";
        if (i != -1) {
            lMediaType = lType.substring(0, i).trim();
            lMediaSubType = lType.substring(i+1).trim();
        }

        lContentType.setMediaType(lMediaType);
        lContentType.setMediaSubType(lMediaSubType);

        for (i=1; i<lParts.length; i++) {
            String[] lKeyValue = lParts[i].split("=");
            if (lKeyValue.length != 2) {
                throw new IllegalArgumentException("can't parse key-value pair for parameter '" + i + "' for content-type '" + pContentType + "'");
            }
            String lParam = lKeyValue[0];
            String lValue = lKeyValue[1];
            if (lValue.startsWith("\"")) {
                lValue = lValue.substring(1);
            }
            if (lValue.endsWith("\"")) {
                lValue = lValue.substring(0, lValue.length()-1);
            }
            lContentType.addParam(lParam, lValue);
        }
        return lContentType;
    }

    @Override
    public boolean equals(Object pObject) {
        if (pObject == null) {
            return false;
        }
        if (pObject == this) {
            return true;
        }
        if (!(pObject instanceof ContentType)) {
            return false;
        }
        ContentType lContentType = (ContentType)pObject;
        if (!mMediaType.equals(lContentType.mMediaType)) {
            return false;
        }
        if (!mMediaSubType.equals(lContentType.mMediaSubType)) {
            return false;
        }
        if (mParamMap == null && lContentType.mParamMap == null) {
        	return true;
        }
        if (mParamMap != null && lContentType.mParamMap != null) {
        	return mParamMap.equals(lContentType.mParamMap);
        }
        return false;
    }

    @Override
    public int hashCode() {
    	return toString().hashCode();
    }

    @Override
    public String toString() {
    	StringBuilder lBuilder = new StringBuilder();
    	lBuilder.append(mMediaType);
    	if (!mMediaSubType.equals("")) {
    		lBuilder.append("/").append(mMediaSubType);
    	}
    	if (mParamMap != null) {
    		for (Map.Entry<String, String> lEntry : mParamMap.entrySet()) {
    			lBuilder.append("; ");
    			lBuilder.append(lEntry.getKey());
    			lBuilder.append("=\"").append(lEntry.getValue()).append("\"");
    		}
    	}
    	return lBuilder.toString();
    }
}
