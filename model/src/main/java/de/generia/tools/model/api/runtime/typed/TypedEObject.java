package de.generia.tools.model.api.runtime.typed;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.generic.GenericEObject;

public class TypedEObject implements InvocationHandler {
	protected EObject delegate;
	protected Class<?> typeInterface;
	
	public TypedEObject(Class<?> typeInterface, EObject delegate) {
		this.delegate = delegate;
		this.typeInterface = typeInterface;
	}
	
	public static <T> T create(Class<T> typeInterface, EClass type) {
		EObject delegate = new GenericEObject(type);
		InvocationHandler handler = new TypedEObject(typeInterface, delegate);
		Class<?>[] interfaces = new Class<?>[]{typeInterface, EObject.class};
		return create(handler, interfaces);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T create(InvocationHandler handler, Class<?>[] interfaces) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = TypedEObject.class.getClassLoader();
		}
		Object proxy = Proxy.newProxyInstance(classLoader, interfaces, handler);
		return (T) proxy;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		TypedEObject typedObject = (TypedEObject) Proxy.getInvocationHandler(proxy);
		EObject delegate = typedObject.getDelegate();
		
		Class<?> declaringClass = method.getDeclaringClass();
		if (declaringClass.getPackage().getName().startsWith("java.")) {
			return method.invoke(delegate, args);
		}
		
		String property = getProperty(method);
		
		// if not a property method, call it directly on the delegate
		Object result = null;
		if (property == null) {
			// throw new IllegalArgumentException("can't access property for typed object '" + typeInterface.getName() + "', method '" + method.getName() + "' does not match getter/setter conventions.");
			result = method.invoke(delegate, args);
		} else if (method.getName().startsWith("set")) {
			delegate.set(property, args[0]);
		} else {
			result = delegate.get(property);
		}
		return result;
	}

	private String getProperty(Method method) {
		String name = method.getName();
		String feature;
		int params = method.getParameterTypes().length;
		if (name.startsWith("is") && params == 0) {
			feature = name.substring("is".length());
		} else if (name.startsWith("get") && params == 0) {
			feature = name.substring("get".length());
		} else if (name.startsWith("set") && params == 1) {
			feature = name.substring("set".length());
		} else {
			return null;
		}
		return Character.toLowerCase(feature.charAt(0)) + feature.substring(1);
	}

	public EObject getDelegate() {
		return delegate;
	}
}
