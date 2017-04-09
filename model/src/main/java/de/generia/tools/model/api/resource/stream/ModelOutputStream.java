package de.generia.tools.model.api.resource.stream;

import de.generia.tools.model.api.resource.NamingConvention;


public class ModelOutputStream extends AbstractModelStream {

	public ModelOutputStream(NamingConvention namingConvention) {
		super(namingConvention);
	}
/*
	public interface NamespaceHandler {
		boolean isNamespace(EObject pObject);
		EObject getNamespace(EObject pObject);
		Map.Entry<String, String> getNamespaceMapEntry(EObject pNamespace);
		String getReference(EObject pObject, EObject pNamespace);
		
		// Default: test auf many -> loop �ber list, aufruf mit list-index
		String toValue(EObject pContext, EReference pReference, EObject pTarget);
		String toValue(EObject pContext, EReference pReference, EObject pTarget, Integer pListIndex);

		String toValue(EObject pContext, EAttribute pAttribute, Object pValue, Integer pListIndex);
		// implizit in toValue() ?? 
		// String toDefaultValue(EObject pContext, EAttribute pAttribute);
	}
	
	public ModelOutputStream(BindingInfo pBindingInfo) {
		super(pBindingInfo);
	}

	private class XmlWriter extends AbstractXmlWriter {
		private static final String XML_LIST_SEP = ",";

		private int mTextWrapSize = 20;
		private int mCurrentTextSize = 0;

		public XmlWriter(Writer pWriter) {
			super(pWriter);
			writeXmlDecl();
		}

		public void writeObject(EObject pObject) {
			EClass lClass = pObject.eClass();
			String lElementName;
			boolean lIndent;
			if (isMixedContentMode()) {
				lElementName = mNamingConventionHandler.toAttributeName(lClass.getName());
				lIndent = false;
			} else {
				lElementName = mNamingConventionHandler.toElementName(lClass.getName());
				lIndent = true;
			}

			boolean lIsMixedContentClass = lClass.getEAnnotation(MIXED_CONTENT_ANNOTATION) != null;
			if (!lIsMixedContentClass) {
				writeElemBeg(lElementName, lIndent);
			}

			// write xml attributes
			for (EStructuralFeature lFeature : lClass.getEAllStructuralFeatures()) {
				if (lFeature.isTransient()) {
					continue;
				}
				if (isXmlAttribute(lFeature)) {
					if (lFeature instanceof EReference) {
						writeReference(pObject, lFeature);
					} else {
						writeAttribute(pObject, lFeature);
					}
				}
			}
			
			// write xml elements
			for (EStructuralFeature lFeature : lClass.getEAllStructuralFeatures()) {
				if (lFeature.isTransient()) {
					continue;
				}
				if (!isXmlAttribute(lFeature)) {
					writeContainment(pObject, lFeature);
				}
			}
			if (!lIsMixedContentClass) {
				writeElemEnd(lElementName, lIndent);
			}
		}

		private boolean isXmlAttribute(EStructuralFeature pFeature) {
			if (pFeature instanceof EReference) {
				EReference lReference = (EReference)pFeature;
				return !lReference.isContainment() && !mBindingInfo.isExternalStoreFeature(pFeature);
			}
			return true;
		}

		private void writeContainment(EObject pObject, EStructuralFeature pFeature) {
			Object lValue = pObject.eGet(pFeature);
			if (isExternalContainment(pObject, pFeature, lValue)) {
				return;
			}

			
			String lElementName = mNamingConventionHandler.toAttributeName(pFeature.getName());
			
			if (pFeature.isMany()) {
				@SuppressWarnings("unchecked")
				List<EObject> lList = (List<EObject>) lValue;
				if (!lList.isEmpty()) {
					if (!isMixedContentMode()) {
						writeElemBeg(lElementName);
					}
					for (EObject lObject : lList) {
						if (isExternalContainment(pObject, lObject)) {
							continue;
						}
						if (isMixedContentMode() && mTextContentClass.equals(lObject.eClass())) {
							Object lText = lObject.eGet(mTextFeature);
							writeText(lText, false);
						} else {
							writeObject(lObject);
						}
					}
					if (!isMixedContentMode()) {
						writeElemEnd(lElementName);
					}
				}
			} else {
				
				// handle scalar and mixed content
				EObject lObject = (EObject)lValue;
				if (isMixedContentMode() && mTextContentClass.equals(lObject.eClass())) {
					Object lText = lObject.eGet(mTextFeature);
					writeText(lText, false);
				} else {
					writeElemBeg(lElementName, !isMixedContentMode());
					checkAndEnterMixedContentMode((EReference) pFeature);
					writeObject(lObject);
					writeElemEnd(lElementName, !isMixedContentMode());
					EAnnotation lAnnotation = lObject.eClass().getEAnnotation(MIXED_CONTENT_ANNOTATION);
					if (lAnnotation != null) {
						checkAndLeaveMixedContentMode(lObject.eClass());
						mWriter.println();
					}
				}
			}
		}

		private boolean isExternalContainment(EObject pObject, EStructuralFeature pFeature, Object pValue) {
			if (pValue == null) {
				return true;
			}
			
			if (!pFeature.isMany()) {
				return isExternalContainment(pObject, (EObject) pValue);
			}

			@SuppressWarnings("unchecked")
			List<EObject> lList = (List<EObject>) pValue;
			for (EObject lObject : lList) {
				if (!isExternalContainment(pObject, lObject)) {
					return false;
				}
			}
			return true;
		}

		private boolean isExternalContainment(EObject pParent, EObject pChild) {
			Resource lParentResource = pParent.eResource();
			Resource lChildResource = pChild.eResource();
			if (lParentResource != null && lChildResource != null && !lParentResource.equals(lChildResource)) {
				return true;
			}
			return false;
		}

		private boolean writeReference(EObject pObject, EStructuralFeature pFeature) {
			String lAttribute = mNamingConventionHandler.toAttributeName(pFeature.getName());
			Object lValue = pObject.eGet(pFeature);

			String lValueString;
			String lDefaultString = null;

			if (pFeature.isMany()) {
				@SuppressWarnings("unchecked")
				List<EObject> lList = (List<EObject>) lValue;
				List<String> lReferenceUris = new ArrayList<String>();
				for (EObject lReference : lList) {
					String lReferenceUri = getReferenceUri(pObject, lReference);
					lReferenceUris.add(lReferenceUri);
				}
				lValueString = toXmlList(lReferenceUris);
			} else {
				lValueString = getReferenceUri(pObject, (EObject) lValue);
			}
			return writeAttr(lAttribute, lValueString, lDefaultString);
		}

		private String toXmlList(List<String> pValues) {
			String lSep = "";
			StringBuilder lXmlList = new StringBuilder();
			for (String lValue : pValues) {
				lXmlList.append(lSep).append(lValue);
				// TODO: better handling of list sep
				lSep = XML_LIST_SEP;
			}
			return lXmlList.toString();
		}

		private String getReferenceUri(EObject pObject, EObject pReference) {
			if (pReference == null) {
				return null;
			}
			Resource lThisResource = pObject.eResource();
			Resource lOtherResource = pReference.eResource();
			// TODO: This is a hack.
			//       Copy'n'Paste in the Ecore-Editor does not set the resource in the EObject.
			//       Check for proper handling ...
			if (lOtherResource == null) {
				lOtherResource = lThisResource;
			}
			String lReferenceUri = lOtherResource.getURIFragment(pReference);

			if (!lThisResource.equals(lOtherResource)) {
				URI lOtherUri = lOtherResource.getURI();
				// make relative uri
				lOtherUri = lOtherUri.deresolve(lThisResource.getURI(), true, true, false);
				lReferenceUri = lOtherUri.appendFragment(lReferenceUri).toString();
			}
			return lReferenceUri;
		}

		private boolean writeAttribute(EObject pObject, EStructuralFeature pFeature) {
			String lAttribute = mNamingConventionHandler.toAttributeName(pFeature.getName());
			EClassifier lType = pFeature.getEType();
			Object lValue = pObject.eGet(pFeature);

			boolean lAttrWritten = false;
			if (pFeature.isMany()) {
				throw new UnsupportedOperationException();
			} else {
				EFactory lFactory = lType.getEPackage().getEFactoryInstance();
				Object lDefaultValue = pFeature.getDefaultValue();

				String lDefaultString = null;
				if (lDefaultValue != null) {
					lDefaultString = lFactory.convertToString((EDataType) lType, lDefaultValue);
				}
				String lValueString = lFactory.convertToString((EDataType) lType, lValue);
				lAttrWritten = writeAttr(lAttribute, lValueString, lDefaultString);
			}
			return lAttrWritten;
		}
		
		@Override
		protected void writeElemBeg(String pElement, boolean pIndent) {
			super.writeElemBeg(pElement, pIndent);
			if (!pIndent) {
				mCurrentTextSize += pElement.length() + 2;
			}
		}
		
		@Override
		protected void writeElemEnd(String pElement, boolean pIndent) {
			super.writeElemEnd(pElement, pIndent);
			if (!pIndent) {
				mCurrentTextSize += pElement.length() + 3;
			}
		}
		
		@Override
		protected void writeText(Object pValue, boolean pIndent) {
			String lText = wrapText(pValue.toString());
			super.writeText(lText, pIndent);
		}
		
		private String wrapText(String pText) {
			int lTextSize = pText.length();
			
//			if (mCurrentTextSize + lTextSize < mTextWrapSize) {
//				mCurrentTextSize += lTextSize;
//				return pText;
//			}

			String lSep = "\n\t" + mInd;
			StringBuilder lBuilder = new StringBuilder();
			
			int lLineBegPos = 0;
			int lLineEndPos = mTextWrapSize - mCurrentTextSize;
			if (lLineEndPos < 0) {
				lLineEndPos = 0;
			}
			String lSep2 = "";
			while (lLineEndPos <= lTextSize) {
				lBuilder.append(lSep2);
				lLineEndPos = pText.indexOf(" ", lLineEndPos);
				if (lLineEndPos == -1) {
					break;
				}
				lBuilder.append(pText.substring(lLineBegPos, lLineEndPos));
				lLineBegPos = lLineEndPos + 1;
				lLineEndPos = lLineBegPos + mTextWrapSize;
			
				lSep2 = lSep;
			}

			if (lLineEndPos != -1) {
				lBuilder.append(lSep2);
				mCurrentTextSize = lTextSize - lLineBegPos;
			} else {
				mCurrentTextSize += (lTextSize - lLineBegPos);
			}
			lBuilder.append(pText.substring(lLineBegPos));

			return lBuilder.toString();
		}
	}

	public void write(OutputStream pOutputStream, EObject pObject) {
		XmlWriter lWriter = new XmlWriter(new OutputStreamWriter(pOutputStream));
		lWriter.writeObject(pObject);
		lWriter.flush();
	}
	*/
}
