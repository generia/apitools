package de.generia.tools.model.api.runtime.generic;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectProxy;
import de.generia.tools.model.api.runtime.util.EObjectSet;

public class GenericEObjectProxy extends GenericEObject implements EObjectProxy {

	protected EObject delegate;
	
	public GenericEObjectProxy(EClass type, String id) {
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
	public EClass eGetType() {
		// get the type from the delegate.
		// in case of a polymorphic proxy the type 
		// might change with the specific delegate 
		EObject d = getDelegate();
		if (d == null) {
			return super.eGetType();
		}
		EClass delegateType = d.eGetType();
		// check for polymorphic type change and update the proxy for consistency
		if (!type.equals(delegateType)) {
			eSetType(delegateType);
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

	@Override
	public int eGetObjectSetId() {
		if (delegate == null) {
			return super.eGetObjectSetId();
		}
		return ((EObjectSet.Item)delegate).eGetObjectSetId();
	}

	@Override
	public void eSetObjectSetId(int setId) {
		if (delegate == null) {
			super.eSetObjectSetId(setId);
		} else {
			((EObjectSet.Item)delegate).eSetObjectSetId(setId);
		}
	}
}
