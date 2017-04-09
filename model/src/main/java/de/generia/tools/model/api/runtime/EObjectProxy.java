package de.generia.tools.model.api.runtime;

import de.generia.tools.model.api.EClass;

public class EObjectProxy extends EObject {

	protected EObject delegate;
	
	public EObjectProxy(EClass type, String id) {
		super(type);
		this.id = id;
	}
	
	public EObject getDelegate() {
		return delegate;
	}
	
	public void setDelegate(EObject delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public EClass getType() {
		// get the type from the delegate.
		// in case of a polymorphic proxy the type 
		// might change with the specific delegate 
		EObject d = getDelegate();
		if (d == null) {
			return super.getType();
		}
		EClass delegateType = d.getType();
		// check for polymorphic type change and update the proxy for consistency
		if (!type.equals(delegateType)) {
			setType(delegateType);
		}
		return type;
	}
	
	@Override
	public Object get(String property) {
		EObject d = getDelegate();
		if (d == null) {
			return super.get(property);
		}
		return d.get(property);
	}
	
	@Override
	public void set(String property, Object value) {
		if (delegate == null) {
			super.set(property, value);
		} else {
			delegate.set(property, value);
		}
	}
}
