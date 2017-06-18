package de.generia.tools.model.api.runtime.typed;

import java.lang.reflect.InvocationHandler;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.runtime.EObjectProxy;
import de.generia.tools.model.api.runtime.generic.GenericEObjectProxy;

public class TypedEObjectProxy extends TypedEObject {
	
	public TypedEObjectProxy(Class<?> typeInterface, EObjectProxy delegate) {
		super(typeInterface, delegate);
	}
	
	public static <T> T create(Class<T> typeInterface, EClass type, String id) {
		EObjectProxy delegate = new GenericEObjectProxy(type, id);
		InvocationHandler handler = new TypedEObjectProxy(typeInterface, delegate);
		Class<?>[] interfaces = new Class<?>[]{typeInterface, EObjectProxy.class};
		return create(handler, interfaces);
	}

	public EObjectProxy getDelegate() {
		return (EObjectProxy) delegate;
	}
}
