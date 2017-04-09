package de.generia.tools.model.xecore.util;

import java.io.Writer;
import java.util.Stack;

public abstract class AbstractXmlWriter extends AbstractIndentWriter {

	private Stack<String> mElementStack = new Stack<String>();
	private boolean mHasChildren = false;
	
	public AbstractXmlWriter(Writer pWriter) {
		super(pWriter);
	}

	protected void writeXmlDecl() {
		mWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	}
	

	protected void writeElemBeg(String pElement) {
		writeElemBeg(pElement, true);
	}
	
	protected void writeElemBeg(String pElement, boolean pIndent) {
		finishElementBeg(pIndent);
		String lElement = toElem(pElement);
		if (pIndent) {
			mWriter.print(mInd);
		}
		mWriter.print("<" + lElement);
		//mHasChildrenStack.push(true);
		mHasChildren = false;
		mElementStack.push(lElement);
	}

	private String toElem(String pElement) {
		return pElement.replace('/', '-');
	}

	protected void writeElemEnd(String pElement) {
		writeElemEnd(pElement, true);
	}
	
	protected void writeElemEnd(String pElement, boolean pIndent) {
		String lElement = mElementStack.pop();
		if (!toElem(pElement).equals(lElement)) {
			throw new IllegalStateException("writeElemEnd: element '" + pElement + "' does not match element '" + lElement + "' on element-stack");
		}
		if (mHasChildren) {
			if (pIndent) {
				outdent();
				mWriter.println(mInd + "</" + lElement + ">");
			} else {
				mWriter.print("</" + lElement + ">");
			}
		} else {
			mWriter.println("/>");
		}
		mHasChildren = true;
	}

	
	protected void writeAttr(String pAttr, Object pValue) {
		writeAttr(pAttr, pValue, null);
	}

	protected boolean writeAttr(String pAttr, Object pValue, Object pDefault) {
		Object lValue;
		if (pValue == null || isEmpty(pValue.toString())) {
			lValue = pDefault == null ? null : pDefault.toString();
		} else {
			lValue = pDefault == null ? pValue : pDefault.equals(pValue) ? null : pValue;
		}
		boolean lAttrWritten = false;
		if (lValue != null && !lValue.toString().isEmpty()) {
			mWriter.print(" " + pAttr + "=\"" + toXml(lValue.toString()) + "\"");
			lAttrWritten = true;
		}
		return lAttrWritten;
	}

	protected void writeText(Object pValue) {
		writeText(pValue, true);
	}
	
	protected void writeText(Object pValue, boolean pIndent) {
		finishElementBeg(pIndent);
		mHasChildren = true;
		String lXml = toXml(pValue.toString());
		if (pIndent) {
			mWriter.println(mInd + lXml);
		} else {
			mWriter.print(lXml);
		}
	}

	protected String toXml(String pString) {
		String lString = pString.replace("&", "&amp;");
		lString = lString.replace("<", "&lt;");
		lString = lString.replace("\"", "&quot;");
		return lString;
	}

	private void finishElementBeg(boolean pIndent) {
		if (!mHasChildren && !mElementStack.isEmpty()) {
			if (pIndent) {
				mWriter.println(">");
				indent();
			} else {
				mWriter.print(">");
			}
		}
	}
}
