package de.generia.tools.model.api.resource.stream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({"unchecked", "rawtypes"})
public class BeanUtil {


	private static final String SETTER_PREFIX = "set";
	private static final String GETTER_PREFIX = "get";
	private static final String BOOL_GETTER_PREFIX = "is";
	private static final String CLASS_PROPERTY = "class";

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
	 * Returns the Getter for  pName of class pClass , where pPrefixLength characters are cut off from the attribute name
	 * @param pClass
	 * @param pName
	 * @param pPrefix
	 * @return
	 */
	public static Method getGetter(Class pClass, String pName, int pPrefixLength) {
		if (pName.length() <= pPrefixLength) {
			return null;
		}
		return getGetter(pClass, pName.substring(pPrefixLength));
	}
	
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
	 * Returns the Setter for  pName of class pClass , where pPrefixLength characters are cut off from the attribute name
	 * @param pClass
	 * @param pName
	 * @param pParamType
	 * @param pPrefix
	 * @return
	 */
	public static Method getSetter(Class pClass, String pName, Class pParamType, int pPrefixLength) {
		if (pName.length() <= pPrefixLength) {
			return null;
		}
		return getSetter(pClass, pName.substring(pPrefixLength), pParamType);
	}
	
	public static String firstDown ( String pName ) {
		if (pName.equals("")) {
			return "";
		}
		String lName = pName.substring(0,1).toLowerCase() + pName.substring(1);
		return lName;
	}

	public static String firstUp ( String pName ) {
		if (pName.equals("")) {
			return "";
		}
		String lName = pName.substring(0,1).toUpperCase() + pName.substring(1);
		return lName;
	}
	
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

	public static class View extends PropertyNode {
		private Class mClass;
		
		public View(Class pClass) {
			mClass = pClass;
		}

		public Class getBeanClass() {
			return mClass;
		}
		
		public String getName() {
			return mClass.getSimpleName();
		}
		
		public View copy() {
			try {
				View lView = (View) clone();
				return lView;
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("can't copy view '" + this + "'", e);
			}
		}

		public void setClass(Class pClass) {
			mClass = pClass;
		}
	}

	public static abstract class PropertyNode implements Cloneable {
		private List<Property> mChildren = new ArrayList<Property>();
		
		public abstract String getName();
	
		public Property add(String pName) {
			Property lProperty = get(pName);
			if (lProperty != null) {
				return lProperty;
			}
			lProperty = new Property(pName);
			mChildren.add(lProperty);
			return lProperty;
		}
		
	
		public Property add(String pName, View pView) {
			Property lProperty = add(pName);
			if (pView != null) {
				View lView = pView.copy();
				((PropertyNode)lProperty).mChildren = lView.getChildren();
			}
			return lProperty;
		}
	
		private Property get(String pName) {
			for (Property lProperty : mChildren) {
				if (lProperty.getName().equals(pName)) {
					return lProperty;
				}
			}
			return null;
		}
		
		protected void collectProperties(StringBuffer pBuffer, String pContext) {
			String lProperty = (pContext == null) ? getName() : pContext + "." + getName();
			pBuffer.append(lProperty).append("\n");
			for (Property lChild : mChildren) {
				lChild.collectProperties(pBuffer, lProperty);
			}
		}
		
		@Override
		protected Object clone() throws CloneNotSupportedException {
			PropertyNode lNode = (PropertyNode) super.clone();
			mChildren = new ArrayList<Property>();
			for (Property lChild : lNode.mChildren) {
				mChildren.add((Property) lChild.clone());
			}
			return lNode;
		}
		
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
		
		public List<Property> getChildren() {
			return mChildren;
		}
		
		@Override
		public String toString() {
			StringBuffer lBuffer = new StringBuffer();
			collectProperties(lBuffer, null);
			return lBuffer.toString();
		}
	}

	public static class Property extends PropertyNode {
		private String mName;
		public Property(String pName) {
			mName = pName;
		}
		public String getName() {
			return mName;
		}
	}

	public static abstract class Processor {
		public abstract Object process(Object pObject, PropertyNode pPropertyNode);
		private Object mContext = null;
		public Object getContext() {
			return mContext;
		}
		public void setContext(Object pContext) {
			mContext = pContext;
		} 
	}
	
	public static class PropertyRef {
		private Object mBean = null;
		private String mProperty = null;
		private Method mGetter = null;
		private Method mSetter = null;

		public PropertyRef(Object pBean, String pProperty) {
			mBean = pBean;
			mProperty = pProperty;
		}
		
		public Object getBean() {
			return mBean;
		}
		
		public void setBean(Object pBean) {
			mBean = pBean;
		}
		
		public String getProperty() {
			return mProperty;
		}
		public void setProperty(String pProperty) {
			mProperty = pProperty;
		}

		public Method getGetter() {
			if (mGetter == null) {
				mGetter = BeanUtil.getGetter(mBean.getClass(), mProperty);
			}
			return mGetter;
		}
		
		public Method getSetter() {
			if (mSetter == null) {
				Method lGetter = getGetter();
				mSetter = BeanUtil.getSetter(mBean.getClass(), mProperty, lGetter.getReturnType());
			}
			return mSetter;
		}
		
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
		
		@Override
		public String toString() {
			return mBean + "." + mProperty;
		}
		
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
		
		@Override
		public int hashCode() {
			// we don't use toString().hashCode() here
			// since the toString() might be overwritten for the bean
			return (mBean.hashCode() + mProperty).hashCode();
		}
	}

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

	public interface PropertyFilter {
		public boolean accept(String pProperty, Method pGetter);
	}
	
	public static List<String> getProperties(Class pClass) {
		return getProperties(pClass, null);
	}
	
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
