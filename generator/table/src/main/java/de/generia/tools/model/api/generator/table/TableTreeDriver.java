package de.generia.tools.model.api.generator.table;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.generator.table.model.EObjectTreeDriver;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectFactory;

public class TableTreeDriver extends EObjectTreeDriver {

	private Map<EObject, TableTreeDriver.ParentInfo> parentInfoMap = new HashMap<>();

	public TableTreeDriver(EObjectFactory objectFactory) {
		super(objectFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void getChildrenAddChild(List<Object> children, Object parent, EReference childReference, Object child) {
		super.getChildrenAddChild(children, parent, childReference, child);
		if (childReference.isMany()) {
			for (Object item : (Collection<Object>)child) {
				parentInfoMap.put((EObject)item, new ParentInfo((EObject) parent, childReference));					
			}
		} else {
			parentInfoMap.put((EObject)child, new ParentInfo((EObject) parent, childReference));
		}
	}
	
	@Override
	public Object getParent(Object object) {
		TableTreeDriver.ParentInfo parentInfo = parentInfoMap.get(object);
		if (parentInfo == null) {
			return null;
		}
		return parentInfo.getParent();
	}
	
	public EReference getParentChildReference(Object child) {
		TableTreeDriver.ParentInfo parentInfo = parentInfoMap.get(child);
		if (parentInfo == null) {
			return null;
		}
		return parentInfo.getChildReference();
	}
	
	private static class ParentInfo {

		private EObject parent;
		private EReference childReference;

		public ParentInfo(EObject parent, EReference childReference) {
			this.parent = parent;
			this.childReference = childReference;
		}
		
		public EObject getParent() {
			return parent;
		}
		
		public EReference getChildReference() {
			return childReference;
		}
	}
}