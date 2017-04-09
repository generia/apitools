package de.generia.tools.model.api.resource.stream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class BeanUtil.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BeanUtil {


	/** The Constant SETTER_PREFIX. */
	private static final String SETTER_PREFIX = "set";
	
	/** The Constant GETTER_PREFIX. */
	private static final String GETTER_PREFIX = "get";
	
	/** The Constant BOOL_GETTER_PREFIX. */
	private static final String BOOL_GETTER_PREFIX = "is";
	
	/** The Constant CLASS_PROPERTY. */
	private static final String CLASS_PROPERTY = "class";

	/**
	 * Gets the getter.
	 *
	 * @param pClass the class
	 * @param pName the name
	 * @return the getter
	 */
	public static Method getGetter(Class pClass, String pName) {
		if (pClass == null || pName == null) {
			return null;
		}
		try {
			String lGetterName = GETTER_PREFIX + BeanUtil.firstUp(pName);
			Method lGetter = pClass.getMethod(lGetterName);
			return lGetter;
		} catch (Exception e) {
			// try boolean-getter
			try {
				String lGetterName = BOOL_GETTER_PREFIX + BeanUtil.firstUp(pName);
				Method lGetter;
				lGetter = pClass.getMethod(lGetterName);
				return lGetter;
			} catch (Exception e1) {
				return null;
			}
		}
	}

	/**
	 * Returns the Getter for  pName of class pClass , where pPrefixLength characters are cut off from the attribute name.
	 *
	 * @param pClass the class
	 * @param pName the name
	 * @param pPrefixLength the prefix length
	 * @return the getter
	 */
	public static Method getGetter(Class pClass, String pName, int pPrefixLength) {
		if (pName.length() <= pPrefixLength) {
			return null;
		}
		return getGetter(pClass, pName.substring(pPrefixLength));
	}
	
	/**
	 * Gets the setter.
	 *
	 * @param pClass the class
	 * @param pName the name
	 * @return the setter
	 */
	public static Method getSetter(Class pClass, String pName) {
		if (pClass == null || pName == null) {
			return null;
		}
		Method lGetter = getGetter(pClass, pName);
		if (lGetter == null) {
			return null;
		}
		return getSetter(pClass, pName, lGetter.getReturnType());
	}
	
	/**
	 * Gets the setter.
	 *
	 * @param pClass the class
	 * @param pName the name
	 * @param pParamType the param type
	 * @return the setter
	 */
	public static Method getSetter(Class pClass, String pName, Class pParamType) {
		if (pClass == null || pName == null) {
			return null;
		}
		try {
			String lSetterName = SETTER_PREFIX + BeanUtil.firstUp(pName);
			Method lSetter = pClass.getMethod(lSetterName, new Class[]{pParamType});
			return lSetter;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the Setter for  pName of class pClass , where pPrefixLength characters are cut off from the attribute name.
	 *
	 * @param pClass the class
	 * @param pName the name
	 * @param pParamType the param type
	 * @param pPrefixLength the prefix length
	 * @return the setter
	 */
	public static Method getSetter(Class pClass, String pName, Class pParamType, int pPrefixLength) {
		if (pName.length() <= pPrefixLength) {
			return null;
		}
		return getSetter(pClass, pName.substring(pPrefixLength), pParamType);
	}
	
	/**
	 * First down.
	 *
	 * @param pName the name
	 * @return the string
	 */
	public static String firstDown ( String pName ) {
		if (pName.equals("")) {
			return "";
		}
		String lName = pName.substring(0,1).toLowerCase() + pName.substring(1);
		return lName;
	}

	/**
	 * First up.
	 *
	 * @param pName the name
	 * @return the string
	 */
	public static String firstUp ( String pName ) {
		if (pName.equals("")) {
			return "";
		}
		String lName = pName.substring(0,1).toUpperCase() + pName.substring(1);
		return lName;
	}
	
	/**
	 * Gets the reserved properties.
	 *
	 * @param pClass the class
	 * @param pProperties the properties
	 */
	public static void getReservedProperties(Class pClass, List<String> pProperties) {
		if (pClass == null) {
			return;
		}
		pProperties.addAll(getProperties(pClass));
		Class lSuper = pClass.getSuperclass();
		if (lSuper != Object.class) {
			getReservedProperties(lSuper, pProperties);
		}
		for (Class lInterface : pClass.getInterfaces()) {
			getReservedProperties(lInterface, pProperties);
		}
	}

	/**
	 * The Class View.
	 */
	public static class View extends PropertyNode {
		
		/** The m class. */
		private Class mClass;
		
		/**
		 * Instantiates a new view.
		 *
		 * @param pClass the class
		 */
		public View(Class pClass) {
			mClass = pClass;
		}

		/**
		 * Gets the bean class.
		 *
		 * @return the bean class
		 */
		public Class getBeanClass() {
			return mClass;
		}
		
		/* (non-Javadoc)
		 * @see de.generia.tools.model.api.resource.stream.BeanUtil.PropertyNode#getName()
		 */
		public String getName() {
			return mClass.getSimpleName();
		}
		
		/**
		 * Copy.
		 *
		 * @return the view
		 */
		public View copy() {
			try {
				View lView = (View) clone();
				return lView;
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("can't copy view '" + this + "'", e);
			}
		}

		/**
		 * Sets the class.
		 *
		 * @param pClass the new class
		 */
		public void setClass(Class pClass) {
			mClass = pClass;
		}
	}

	/**
	 * The Class PropertyNode.
	 */
	public static abstract class PropertyNode implements Cloneable {
		
		/** The m children. */
		private List<Property> mChildren = new ArrayList<Property>();
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public abstract String getName();
	
		/**
		 * Adds the.
		 *
		 * @param pName the name
		 * @return the property
		 */
		public Property add(String pName) {
			Property lProperty = get(pName);
			if (lProperty != null) {
				return lProperty;
			}
			lProperty = new Property(pName);
			mChildren.add(lProperty);
			return lProperty;
		}
		
	
		/**
		 * Adds the.
		 *
		 * @param pName the name
		 * @param pView the view
		 * @return the property
		 */
		public Property add(String pName, View pView) {
			Property lProperty = add(pName);
			if (pView != null) {
				View lView = pView.copy();
				((PropertyNode)lProperty).mChildren = lView.getChildren();
			}
			return lProperty;
		}
	
		/**
		 * Gets the.
		 *
		 * @param pName the name
		 * @return the property
		 */
		private Property get(String pName) {
			for (Property lProperty : mChildren) {
				if (lProperty.getName().equals(pName)) {
					return lProperty;
				}
			}
			return null;
		}
		
		/**
		 * Collect properties.
		 *
		 * @param pBuffer the buffer
		 * @param pContext the context
		 */
		protected void collectProperties(StringBuffer pBuffer, String pContext) {
			String lProperty = (pContext == null) ? getName() : pContext + "." + getName();
			pBuffer.append(lProperty).append("\n");
			for (Property lChild : mChildren) {
				lChild.collectProperties(pBuffer, lProperty);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#clone()
		 */
		@Override
		protected Object clone() throws CloneNotSupportedException {
			PropertyNode lNode = (PropertyNode) super.clone();
			mChildren = new ArrayList<Property>();
			for (Property lChild : lNode.mChildren) {
				mChildren.add((Property) lChild.clone());
			}
			return lNode;
		}
		
		/**
		 * Apply.
		 *
		 * @param pObject the object
		 * @param pProcessor the processor
		 * @return the object
		 */
		public Object apply(Object pObject, Processor pProcessor) {
			if (pObject == null) {
				return null;
			}
			Object lResult = pProcessor.process(pObject, this);				
			if (pObject instanceof Iterable) {
				for (Object lEntry : (Iterable)pObject) {
					//pProcessor.process(lEntry);
					applyChildren(lEntry, pProcessor);
				}
			} else {
				applyChildren(pObject, pProcessor);
			}
			return lResult;
		}
		
		/**
		 * Apply children.
		 *
		 * @param pObject the object
		 * @param pProcessor the processor
		 */
		private void applyChildren(Object pObject, Processor pProcessor) {
			Object lPrevContext  = pProcessor.getContext();
			pProcessor.setContext(pObject);
			for (Property lProperty : mChildren) {
				Method lGetter = BeanUtil.getGetter(pObject.getClass(), lProperty.getName());
				if (lGetter == null) {
					throw new IllegalStateException("can't process property '" + lProperty.getName() + "' for object '" + pObject + "'");
				}
				Object lValue;
				try {
					lValue = lGetter.invoke(pObject, new Object[]{});
				} catch (Exception e) {
					throw new IllegalStateException("can't get property '" + lProperty.getName() + "' for object '" + pObject + "'", e);
				}
				lProperty.apply(lValue, pProcessor);
			}
			pProcessor.setContext(lPrevContext);
		}
		
		/**
		 * Gets the children.
		 *
		 * @return the children
		 */
		public List<Property> getChildren() {
			return mChildren;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer lBuffer = new StringBuffer();
			collectProperties(lBuffer, null);
			return lBuffer.toString();
		}
	}

	/**
	 * The Class Property.
	 */
	public static class Property extends PropertyNode {
		
		/** The m name. */
		private String mName;
		
		/**
		 * Instantiates a new property.
		 *
		 * @param pName the name
		 */
		public Property(String pName) {
			mName = pName;
		}
		
		/* (non-Javadoc)
		 * @see de.generia.tools.model.api.resource.stream.BeanUtil.PropertyNode#getName()
		 */
		public String getName() {
			return mName;
		}
	}

	/**
	 * The Class Processor.
	 */
	public static abstract class Processor {
		
		/**
		 * Process.
		 *
		 * @param pObject the object
		 * @param pPropertyNode the property node
		 * @return the object
		 */
		public abstract Object process(Object pObject, PropertyNode pPropertyNode);
		
		/** The m context. */
		private Object mContext = null;
		
		/**
		 * Gets the context.
		 *
		 * @return the context
		 */
		public Object getContext() {
			return mContext;
		}
		
		/**
		 * Sets the context.
		 *
		 * @param pContext the new context
		 */
		public void setContext(Object pContext) {
			mContext = pContext;
		} 
	}
	
	/**
	 * The Class PropertyRef.
	 */
	public static class PropertyRef {
		
		/** The m bean. */
		private Object mBean = null;
		
		/** The m property. */
		private String mProperty = null;
		
		/** The m getter. */
		private Method mGetter = null;
		
		/** The m setter. */
		private Method mSetter = null;

		/**
		 * Instantiates a new property ref.
		 *
		 * @param pBean the bean
		 * @param pProperty the property
		 */
		public PropertyRef(Object pBean, String pProperty) {
			mBean = pBean;
			mProperty = pProperty;
		}
		
		/**
		 * Gets the bean.
		 *
		 * @return the bean
		 */
		public Object getBean() {
			return mBean;
		}
		
		/**
		 * Sets the bean.
		 *
		 * @param pBean the new bean
		 */
		public void setBean(Object pBean) {
			mBean = pBean;
		}
		
		/**
		 * Gets the property.
		 *
		 * @return the property
		 */
		public String getProperty() {
			return mProperty;
		}
		
		/**
		 * Sets the property.
		 *
		 * @param pProperty the new property
		 */
		public void setProperty(String pProperty) {
			mProperty = pProperty;
		}

		/**
		 * Gets the getter.
		 *
		 * @return the getter
		 */
		public Method getGetter() {
			if (mGetter == null) {
				mGetter = BeanUtil.getGetter(mBean.getClass(), mProperty);
			}
			return mGetter;
		}
		
		/**
		 * Gets the setter.
		 *
		 * @return the setter
		 */
		public Method getSetter() {
			if (mSetter == null) {
				Method lGetter = getGetter();
				mSetter = BeanUtil.getSetter(mBean.getClass(), mProperty, lGetter.getReturnType());
			}
			return mSetter;
		}
		
		/**
		 * Gets the.
		 *
		 * @return the object
		 */
		public Object get() {
			Method lGetter = getGetter();
			if (lGetter == null) {
				throw new RuntimeException("no getter defined  for property '" + mProperty + "' in bean-class '" + mBean.getClass().getName() + "'");
			}
			try {
				return lGetter.invoke(mBean);
			} catch (Exception e) {
				throw new RuntimeException("can't get bean-property '" + mProperty + "' for object '" + mBean + "'", e);
			}
		}
		
		/**
		 * Sets the.
		 *
		 * @param pObject the object
		 * @return the object
		 */
		public Object set(Object pObject) {
			Method lSetter = getSetter();
			if (lSetter == null) {
				throw new RuntimeException("no setter defined  for property '" + mProperty + "' in bean-class '" + mBean.getClass().getName() + "'");
			}
			try {
				return lSetter.invoke(mBean, new Object[]{pObject});
			} catch (Exception e) {
				throw new RuntimeException("can't set bean-property '" + mProperty + "' for object '" + mBean + "' and value '" + pObject + "'", e);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return mBean + "." + mProperty;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object pObject) {
			if (!(pObject instanceof PropertyRef)) {
				return false;
			}
			PropertyRef lPropertyRef = (PropertyRef)pObject;
			if (!mBean.equals(lPropertyRef.mBean)) {
				return false;
			}
			return mProperty.equals(lPropertyRef.mProperty);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			// we don't use toString().hashCode() here
			// since the toString() might be overwritten for the bean
			return (mBean.hashCode() + mProperty).hashCode();
		}
	}

	/**
	 * Gets the annotation.
	 *
	 * @param pClass the class
	 * @param pProperty the property
	 * @param pAnnotation the annotation
	 * @return the annotation
	 */
	public static Annotation getAnnotation(Class pClass, String pProperty, Class<? extends Annotation> pAnnotation) {
		Method lGetter = getGetter(pClass, pProperty);
		if (lGetter != null) {
			Annotation lAnnotation = lGetter.getAnnotation(pAnnotation);
			if (lAnnotation != null) {
				return lAnnotation;
			}
			
			Method lSetter = getSetter(pClass, pProperty, lGetter.getReturnType());
			if (lSetter != null) {
				lAnnotation = lSetter.getAnnotation(pAnnotation);
				if (lAnnotation != null) {
					return lAnnotation;
				}
			}
		}
	
		try {
			Field lField = pClass.getDeclaredField(pProperty);
			Annotation lAnnotation = lField.getAnnotation(pAnnotation);
			return lAnnotation;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * The Interface PropertyFilter.
	 */
	public interface PropertyFilter {
		
		/**
		 * Accept.
		 *
		 * @param pProperty the property
		 * @param pGetter the getter
		 * @return true, if successful
		 */
		public boolean accept(String pProperty, Method pGetter);
	}
	
	/**
	 * Gets the properties.
	 *
	 * @param pClass the class
	 * @return the properties
	 */
	public static List<String> getProperties(Class pClass) {
		return getProperties(pClass, null);
	}
	
	/**
	 * Gets the properties.
	 *
	 * @param pClass the class
	 * @param pFilter the filter
	 * @return the properties
	 */
	public static List<String> getProperties(Class pClass, PropertyFilter pFilter) {
		List<String> lProperties = new ArrayList<String>();
		for (Method lMethod : pClass.getMethods()) {
			if (lMethod.getReturnType() == Void.class) {
				continue;
			}
			if (lMethod.getParameterTypes().length > 0) {
				continue; 
			}
			String lName = lMethod.getName();
			String lProperty = null;
			if (lName.startsWith(GETTER_PREFIX)) {
				lName = lName.substring(GETTER_PREFIX.length());
				lProperty = BeanUtil.firstDown(lName);
			} else if (lName.startsWith(BOOL_GETTER_PREFIX)) {
				lName = lName.substring(BOOL_GETTER_PREFIX.length());
				lProperty = BeanUtil.firstDown(lName);
			}
			if (lProperty != null && !lProperty.equals(CLASS_PROPERTY)) {
				if  (pFilter == null || pFilter.accept(lProperty, lMethod)) {
					lProperties.add(lProperty);
				}
			}
		}
		return lProperties;
	}
    
}
