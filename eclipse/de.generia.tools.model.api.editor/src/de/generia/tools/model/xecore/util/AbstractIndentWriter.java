package de.generia.tools.model.xecore.util;

import java.io.PrintWriter;
import java.io.Writer;

public abstract class AbstractIndentWriter {
	protected String mInd = "";
	protected PrintWriter mWriter;

	public AbstractIndentWriter(Writer pWriter) {
		mWriter = new PrintWriter(pWriter);
	}

	protected void indent() {
		mInd += "\t";
	}
	
	protected void outdent() {
		try {
			mInd = mInd.substring(0, mInd.length()-1);
		} catch (Exception e) {
			
		}
	}

	protected boolean isEmpty(String pString) {
		return pString == null || pString.trim().isEmpty();
	}
	
	public void flush() {
		mWriter.flush();
	}
}
