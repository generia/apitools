package de.generia.tools.model.xecore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class AbstractXmlReader {
	private InputStream mInputStream;
	protected Document mDocument;
	protected Element mCurrentElement;
	
	public AbstractXmlReader(InputStream pInputStream) {
		mInputStream = pInputStream;
	}

	protected void readDocument() {
		DocumentBuilderFactory lFactory = DocumentBuilderFactory.newInstance();
		lFactory.setNamespaceAware(true);
		lFactory.setValidating(false);
		DocumentBuilder lBuilder;
		try {
			lBuilder = lFactory.newDocumentBuilder();
			mDocument = lBuilder.parse(mInputStream);
		} catch (Exception e) {
			throw new RuntimeException("can't load document", e);
		}
	}
	
	protected Element readFirstChildElem(Element pElement) {
		return nextElem(pElement.getFirstChild());
	}

	
	protected Element readElem(String pTag) {
		Element lElement = readElem();
		while (lElement != null && !lElement.getTagName().equals(pTag)) {
			lElement = readElem();
		}
		return lElement;
	}
	
	protected Element readElem() {
		Element lElement = null;
		if (mCurrentElement == null) {
			if (mDocument == null) {
				readDocument();
			}
			lElement = mDocument.getDocumentElement();
		} else {
			lElement = nextElem(mCurrentElement.getFirstChild());
			if (lElement == null) {
				lElement = nextElem(mCurrentElement);
			}
			if (lElement == null) {
				lElement = nextElem(mCurrentElement.getParentNode());
			}
		}
		mCurrentElement = lElement;
		return lElement;
	}

	protected Element nextElem(Node pNode) {
		if (pNode == null) {
			return null;
		}
		org.w3c.dom.Node lNode = pNode.getNextSibling();
		while (lNode != null) {
			if (lNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				return (Element)lNode;
			}
			lNode = lNode.getNextSibling();
		}
		return null;
	}


	private String parseTextContent(Element pElement) {
		String lTextContent = pElement.getTextContent();
		LineNumberReader lReader = new LineNumberReader(new StringReader(lTextContent));
		StringWriter lStringWriter = new StringWriter();
		PrintWriter lResult = new PrintWriter(lStringWriter);
		try {
			boolean lFirstLine = true;
			int lIndent = -1;
			String lLine = lReader.readLine();
			while (lLine != null) {
				if (lFirstLine) {
					lLine = lLine.trim();
					if (!lLine.equals("")) {
						lResult.println(lLine);
					}
					lFirstLine = false;
				} else if (lIndent == -1) {
					String lText = lLine.trim();
					if (!lText.equals("")) {
						lIndent = lLine.indexOf(lText);
					}
					lResult.println(lText);
				} else {
					String lText;
					if (lLine.length() < lIndent) {
						lText = lLine.trim();
					} else {
						String lIndentText = lLine.substring(0, lIndent);
						if (lIndentText.trim().equals("")) {
							lText = lLine.substring(lIndent);
						} else {
							lText = lLine.trim();
						}
					}
					lResult.println(lText);
				}
				lLine = lReader.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("error while parsing '" + pElement.getTagName() + "' text-content '" + lTextContent + "'", e);
		}
		return lStringWriter.toString().trim();
	}
}
