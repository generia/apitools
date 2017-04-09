package de.generia.tools.model.xecore.binding.stream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import de.generia.tools.model.xecore.binding.ModelResourceBinding.BindingInfo;


public class ModelInputStream extends AbstractModelStream {

	public interface ValueHandler {
		void handleReference(EObject pContext, EReference pReference, String pValue, String pNamespace);
		void handleAttribute(EObject pContext, EAttribute pAttribute, String pValue);
	}
	
	private class SaxHandler extends DefaultHandler {

		private Stack<EReference> mContextFeatureStack = new Stack<EReference>();

		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void startElement(String pUri, String pLocalName, String pName, Attributes pAttributes) throws SAXException {
			//System.out.println("element BEG: " + pLocalName + " - " + pAttributes);

			// create new context
			if (mRoot == null) {
				String lClassName = mNamingConventionHandler.toClassName(pLocalName);
				EClass lClass = findRootClass(lClassName);
				mRoot = mModelSchema.newInstance(lClass);
				mContext = mRoot;
			} else {
				EObject lChild = createChild(pLocalName);
				if (lChild == null) {
					return;
				}
				mContext = lChild;
			}

			// map attributes
			for (int i=0; i<pAttributes.getLength(); i++) {
				String lAttributeName = pAttributes.getLocalName(i);
				String lAttributeValue = pAttributes.getValue(i);

				String lPropertyName = mNamingConventionHandler.toPropertyName(lAttributeName);
				EStructuralFeature lFeature = mContext.eClass().getEStructuralFeature(lPropertyName);
				EClassifier lType = lFeature.getEType();
				if (lType instanceof EDataType) {
					mReferenceResolver.handleAttribute(mContext, (EAttribute) lFeature, lAttributeValue);
				} else {
					mReferenceResolver.handleReference(mContext, (EReference) lFeature, lAttributeValue, null);
				}
			}
		}

		private EObject createChild(String pLocalName) {
			EClass lChildClass;
			EReference lReference;
			EReference lContextFeature = mContextFeatureStack.isEmpty() ? null : mContextFeatureStack.peek();
			if (lContextFeature == null || isMixedContentMode()) {
				EReference lReferenceOut[] = new EReference[1];
				lChildClass = findChildClass(pLocalName, lReferenceOut);
				lReference = lReferenceOut[0];
				if (lChildClass == null) {
					mContextFeatureStack.push(lReference);
					return null;
				}
			} else {
				lChildClass = findChildClass(pLocalName);
				lReference = lContextFeature;
				mContextFeatureStack.push(null);
			}

			EObject lChild = mModelSchema.newInstance(lChildClass);
			if (lReference.isMany()) {
				@SuppressWarnings("unchecked")
				EList<EObject> lList = (EList<EObject>) mContext.eGet(lReference);
				lList.add(lChild);
			} else {
				mContext.eSet(lReference, lChild);
			}
			return lChild;
		}

		@Override
		public void endElement(String pUri, String pLocalName, String pName) throws SAXException {
			//System.out.println("element END: " + pLocalName);
			EReference lContextFeature = (mContextFeatureStack.isEmpty() || isMixedContentMode()) ? null : mContextFeatureStack.pop();
			if (lContextFeature == null && mContext != null) {
				EClass lClass = mContext.eClass();
				if (lClass.getEAnnotation(MIXED_CONTENT_ANNOTATION) != null) {
					normalizeMixedContent(mContext);
					checkAndLeaveMixedContentMode(lClass);
				}
				mContext = mContext.eContainer();
			}
		}

		private void normalizeMixedContent(EObject pObject) {
			@SuppressWarnings("unchecked")
			List<EObject> lChildren = (List<EObject>) pObject.eGet(mChildrenFeature);
			int i = 0;
			int lSize = lChildren.size() - 1;
			EObject lPrevChild = null;
			List<EObject> lRemoveTextNodes = new ArrayList<EObject>();
			for (EObject lChild : lChildren) {
				if (lChild.eClass().equals(mTextContentClass)) {
					String lText = (String) lChild.eGet(mTextFeature);

					// collapse whitespace
					lText = lText.replaceAll("[ \\t\\n]+", " ");
					if (i == 0) {
						// trim left
						lText = lText.replaceAll("^ ", "");
					}
					if (i == lSize) {
						// trim right
						lText = lText.replaceAll(" $", "");
					}
					if (lPrevChild != null && lPrevChild.eClass().equals(mTextContentClass)) {
						String lPrevText = (String) lPrevChild.eGet(mTextFeature);
						lText = (lPrevText + lText).replaceAll("[ \\t\\n]+", " ");
						lPrevChild.eSet(mTextFeature, lText);
						lRemoveTextNodes.add(lChild);
					} else {
						lChild.eSet(mTextFeature, lText);
						lPrevChild = lChild;
					}
				} else {
					normalizeMixedContent(lChild);
					lPrevChild = lChild;
				}
				i++;
			}
			lChildren.removeAll(lRemoveTextNodes);
		}

		@Override
		public void characters(char[] pChars, int pStart, int pLength) throws SAXException {
			super.characters(pChars, pStart, pLength);
			//System.out.println("chars: '" + new String(pChars, pStart, pLength) + "'");

			if (!isMixedContentMode()) {
				return;
			}
			String lText = new String(pChars, pStart, pLength);

			EObject lTextNode = mModelSchema.newInstance(mTextContentClass);
			lTextNode.eSet(mTextFeature, lText);
			@SuppressWarnings("unchecked")
			List<EObject> lChildren = (List<EObject>) mContext.eGet(mChildrenFeature);
			lChildren.add(lTextNode);
		}

		public EObject getRoot() {
			return mRoot;
		}

		private EClass findRootClass(String pClassName) {
			List<EClass> lClasses = new ArrayList<EClass>();
			for (EPackage lPackage : mModelSchema.getPackages()) {
				EClassifier lClassifier = lPackage.getEClassifier(pClassName);
				if (lClassifier instanceof EClass) {
					lClasses.add((EClass) lClassifier);
				}
			}
			if (lClasses.size() == 1) {
				return lClasses.get(0);
			}
			if (lClasses.size() == 0) {
				throw new RuntimeException("can't find root class for class name '" + pClassName + "'");
			}
			throw new RuntimeException("root class is not unique for class name '" + pClassName + "', got '" + lClasses + "' matches");
		}

		private EClass findChildClass(String pElementName, EReference[] pReferenceOut) {
			String lPropertyName = mNamingConventionHandler.toPropertyName(pElementName);
			EStructuralFeature lFeature = mContext.eClass().getEStructuralFeature(lPropertyName);
			if (!isMixedContentMode() && lFeature != null && lFeature instanceof EReference && lFeature.getEType() instanceof EClass) {
				pReferenceOut[0] = (EReference) lFeature;
				// check for mixed content
				checkAndEnterMixedContentMode(pReferenceOut[0]);
				if (isMixedContentMode()) {
					return (EClass) lFeature.getEType();
				}
				return null;
			}

			String lClassName = mNamingConventionHandler.toClassName(pElementName);
			Set<EClass> lClasses = mModelSchema.getClasses(lClassName);

			for (EReference lReference : mContext.eClass().getEAllReferences()) {
				if (!lReference.isContainment()) {
					continue;
				}
				EClassifier lType = lReference.getEType();
				if (!(lType instanceof EClass)) {
					continue;
				}
				for (EClass lClass : lClasses) {
					if (((EClass)lType).isSuperTypeOf(lClass)) {
						pReferenceOut[0] = lReference;
						return lClass;
					}
				}
			}
			throw new RuntimeException("can't find containment reference for element name '" + pElementName + "' in context '" + mContext + "'");
		}

		private EClass findChildClass(String pElementName) {
			String lClassName = mNamingConventionHandler.toClassName(pElementName);
			Set<EClass> lClasses = mModelSchema.getClasses(lClassName);

			EClassifier lType = mContextFeatureStack.peek().getEType();
			for (EClass lClass : lClasses) {
				if (((EClass)lType).isSuperTypeOf(lClass)) {
					return lClass;
				}
			}
			throw new RuntimeException("can't find containment reference for element name '" + pElementName + "' in context '" + mContext + "'");
		}
	}

	private ValueHandler mReferenceResolver;

	public ModelInputStream(BindingInfo pBindingInfo, ValueHandler pReferenceResolver) {
		super(pBindingInfo);
		mReferenceResolver = pReferenceResolver;
	}

	public EObject read(InputStream pInputStream) {
		try {
			XMLReader lReader = XMLReaderFactory.createXMLReader();
			SaxHandler lHandler = new SaxHandler();
			lReader.setContentHandler(lHandler);
			lReader.parse(new InputSource(pInputStream));
			return lHandler.getRoot();
		} catch (Exception e) {
			throw new RuntimeException("can't parse input source", e);
		}
	}
}
