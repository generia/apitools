package de.generia.tools.model.api.resource.stream;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.resource.LowerCaseNamingConvention;


public class ModelInputStream extends AbstractModelStream {
	
	private class SaxHandler extends DefaultHandler {
		private String mContextPath = "";
		private Stack<EModelElement> mContextStack = new Stack<>();
		private Stack<List<EModelElement>> mContextFeatureStack = new Stack<>();
		private Stack<Boolean> mFeatureModeStack = new Stack<>();
		private Map<String, EModelElement> mElementMap = new HashMap<>(); 
		private List<Link> links = new ArrayList<>();
		
		private class Link {
			public Link(EModelElement element, Method setter, String path) {
				this.element = element;
				this.setter = setter;
				this.path = path;
			}
			EModelElement element;
			Method setter;
			String path; 
		}
		
		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void endDocument() throws SAXException {
			for (Link link : links) {
				linkElement(link);
			}
		}

		private void linkElement(Link link) {
			EModelElement element = mElementMap.get(link.path);
			if (element == null) {
				throw new IllegalArgumentException("can't resolve link '" + link.path + "'");
			}
			try {
				link.setter.invoke(link.element, new Object[]{element});
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new IllegalArgumentException("can't link '" + link.setter.getName() + "' to target '" + element + "' in context '" + link.element + "'");
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public void startElement(String pUri, String pLocalName, String pName, Attributes pAttributes) throws SAXException {
			//System.out.println("element BEG: " + pLocalName + " - " + pAttributes);

			// create new context
			if (!mFeatureModeStack.isEmpty() && mFeatureModeStack.peek()) {
				String propertyName = mNamingConventionHandler.toPropertyName(pLocalName);
				EModelElement context = mContextStack.peek();
				Method getter = BeanUtil.getGetter(context.getClass(), propertyName);
				Object featureList = null;
				try {
					featureList = getter.invoke(context, new Object[0]);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new IllegalArgumentException("feature '" + pLocalName + "' is now allowed in context '" + mContextStack.peek() + "'");
				}
				if (!(featureList instanceof List)) {
					throw new IllegalArgumentException("can't find feature-list for element '" + pLocalName + "' in context '" + context.getClass() + "'");
				}
				mContextFeatureStack.push((List<EModelElement>) featureList);
				mFeatureModeStack.push(false);
			} else {
				EModelElement lChild = createChild(pLocalName);
				if (!mContextStack.isEmpty()) {
					setParent(mContextStack.peek(), lChild);
				}
				mContextStack.push(lChild);
				mFeatureModeStack.push(true);
				
				if (mContextFeatureStack.isEmpty()) {
					if (!(lChild instanceof EPackage)) {
						throw new IllegalArgumentException("root-element must be of type '" + EPackage.class.getSimpleName() + "' but was '" + pLocalName + "' instead");
					}
 					mRoot = (EPackage) lChild;
				} else {
					List<EModelElement> featureList = mContextFeatureStack.peek();
					featureList.add(lChild);
				}
				if (lChild instanceof ENamedElement) {
					String name = pAttributes.getValue("name");
					if (name == null || name.isEmpty()) {
						throw new IllegalArgumentException("missing 'name' attribute for named element '" + pLocalName + "' in context '" + mContextStack.peek() + "'");
					}
					mContextPath += "/" + name;
					if (mElementMap.containsKey(mContextPath)) {
						throw new IllegalArgumentException("duplicate name '" + mContextPath + "' in context '" + mContextStack.peek() + "'");						
					}
					mElementMap.put(mContextPath, lChild);
				}
				// map attributes
				for (int i=0; i<pAttributes.getLength(); i++) {
					String lAttributeName = pAttributes.getLocalName(i);
					Object lAttributeValue = parseAttributeValue(pAttributes.getValue(i));
					setAttributeValue(lChild, lAttributeName, lAttributeValue);
				}
			}
		}

		private void setParent(EModelElement parent, EModelElement child) {
			child.setParent(parent);
			if (child instanceof EClassifier) {
				if (parent instanceof EPackage) {
					((EClassifier)child).setPackage((EPackage) parent);
				} else if (parent instanceof EClassifier) {
					((EClassifier)child).setPackage(((EClassifier) parent).getPackage());
				}
			} else if (child instanceof EStructuralFeature) {
				((EStructuralFeature)child).setContainingClass((EClass) parent);
			} else if (child instanceof EEnumLiteral) {
				((EEnumLiteral)child).setEnum((EEnum) parent);
			} else if (child instanceof EOperation) {
				((EOperation)child).setContainingClass((EClass) parent);
			} else if (child instanceof EParameter) {
				((EParameter)child).setOperation((EOperation) parent);
			}
		}

		@Override
		public void endElement(String pUri, String pLocalName, String pName) throws SAXException {
			//System.out.println("element END: " + pLocalName);
			Boolean featureMode = mFeatureModeStack.pop();
			if (featureMode) {
				EModelElement element = mContextStack.pop();

				if (element instanceof ENamedElement) {
					int i = mContextPath.lastIndexOf("/");
					if (i != -1 ) {
						mContextPath = mContextPath.substring(0, i);
					} else {
						mContextPath = "";
					}
				}
			} else {
				mContextFeatureStack.pop();
			}

		}

		private void setAttributeValue(EModelElement element, String attribute, Object value) {
			String property = mNamingConventionHandler.toPropertyName(attribute);
			Method setter = BeanUtil.getSetter(element.getClass(), property);
			if (setter == null) {
				throw new IllegalArgumentException("no such attribute '" + attribute + "' for context '" + mContextStack.peek() + "'");
			}
			Class<?> type = setter.getParameterTypes()[0];
			if (EModelElement.class.isAssignableFrom(type)) {
				Link link = new Link(element, setter, (String)value);
				links.add(link);
			} else {
				try {
					setter.invoke(element, new Object[]{value});
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new IllegalArgumentException("can't set attribute '" + attribute + "' to value '" + value + "' in context '" + mContextStack.peek() + "'");
				}
			}
		}

		private Object parseAttributeValue(String string) {
			Object value = string;
			if (string.equals("true")) {
				value = true;
			} else if (string.equals("false")) {
				value = false;
			} else if (!string.isEmpty() && Character.isDigit(string.charAt(0))) {
				try {
					value = Integer.parseInt(string);
				} catch (NumberFormatException e) {
					value = string;
				}
			}
			return value;
		}

		private EModelElement createChild(String pLocalName) {
			String className = EModelElement.class.getPackage().getName() + "." + pLocalName;
			try {
				Class<?> clazz = Class.forName(className);
				Object object = clazz.newInstance();
				if (!(object instanceof EModelElement)) {
					throw new ClassNotFoundException();
				}
				return (EModelElement) object;
			} catch (Exception e) {
				throw new IllegalArgumentException("can't create child for element '" + pLocalName + "' in context '" + mContext + "'");
			}
		}

		@Override
		public void characters(char[] pChars, int pStart, int pLength) throws SAXException {
			super.characters(pChars, pStart, pLength);
			//System.out.println("chars: '" + new String(pChars, pStart, pLength) + "'");
		}

		public EPackage getRoot() {
			return mRoot;
		}
	}

	public ModelInputStream() {
		super(new LowerCaseNamingConvention());
	}

	public EPackage read(InputStream pInputStream) {
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
