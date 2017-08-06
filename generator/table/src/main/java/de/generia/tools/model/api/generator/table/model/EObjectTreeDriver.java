package de.generia.tools.model.api.generator.table.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.generia.tools.dom2table.model.tree.TreeDriver;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectFactory;

public class EObjectTreeDriver implements TreeDriver {

	private static final EReference UNDEFINED_REFERENCE = new EReference();

	private Map<EClass, EReference> parentReferenceMap = new HashMap<>();
	private Map<EClass, List<EReference>> childReferencesMap = new HashMap<>();

	private EObjectFactory objectFactory;
	
	public EObjectTreeDriver(EObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
	
	public EObjectFactory getObjectFactory() {
		return objectFactory;
	}
	
	@Override
	public Object getParent(Object object) {
		EObject o = (EObject) object;
		EReference parentReference = getParentReference(o.eGetType());
		if (parentReference.equals(UNDEFINED_REFERENCE)) {
			return null;
		}
		return o.get(parentReference.getName());
	}

	@Override
	public List<Object> getChildren(Object object) {
		final EObject o = (EObject) object;
		List<Object> children = new ArrayList<>();
		for (EReference childReference : getChildReferences(o.eGetType())) {
			Object value = o.get(childReference.getName());
			if (value != null) {
				getChildrenAddChild(children, object, childReference, value);
			}
		}
		return children;
	}

	@SuppressWarnings("unchecked")
	protected void getChildrenAddChild(List<Object> children, Object parent, EReference childReference, Object child) {
		if (childReference.isMany()) {
			children.addAll((Collection<? extends Object>) child);
		} else {
			children.add(child);
		}
	}

	@Override
	public boolean hasChildren(Object object) {
		return !getChildren(object).isEmpty();
	}

	@Override
	public int getChildrenIndex(Object o) {
		Object parent = getParent(o);
		return parent == null ? -1 : getChildren(parent).indexOf(o);
	}


	@Override
	public void addChild(Object parent, Object child) {
		EObject p = (EObject) parent;
		EObject c = (EObject) child;
		for (EReference childReference : getChildReferences(p.eGetType())) {
			if (isAssignable(childReference.getType(), c.eGetType())) {
				setOrAddValue(p, c, childReference);
				EReference opposite = childReference.getOpposite();
				if (opposite != null) {
					setOrAddValue(c, p, opposite);
				}
				break;
			}
		}
	}

	@Override
	public Object getRoot(Object object, boolean create) {
		return object;
	}

	@Override
	public Iterable<Object> getAllChildren(final Object object) {
		return new Iterable<Object>() {
			@Override
			public Iterator<Object> iterator() {
				List<Object> children = getChildren(object);
				Collection<Iterable<Object>> iterables = new ArrayList<>();
				for (Object child : children) {
					Iterable<Object> iterable = getAllChildren(child);
					iterables.add(iterable);
				}
				return new IterablesIterator(iterables);
			}
		};
	}

	private EReference getParentReference(EClass type) {
		EReference parentReference = parentReferenceMap.get(type);
		if (parentReference != null) {
			return parentReference;
		}
		parentReference = null;
		for (EStructuralFeature feature : type.getStructuralFeatures()) {
			if (feature instanceof EReference) {
				EReference reference = (EReference) feature;
				EReference opposite = reference.getOpposite();
				if (!reference.isContainment() && opposite != null && opposite.isContainment()) {
					parentReference = reference;
					break;
				}
			}
		}
		if (parentReference == null) {
			EClass superType = type.getSuperType();
			if (superType != null) {
				parentReference = getParentReference(superType);
			}
		}
		if (parentReference == null) {
			parentReference = UNDEFINED_REFERENCE;
		}
		parentReferenceMap.put(type, parentReference);
		return parentReference;
	}


	private List<EReference> getChildReferences(EClass type) {
		List<EReference> childReferences = childReferencesMap.get(type);
		if (childReferences != null) {
			return childReferences;
		}
		childReferences = new ArrayList<>();
		for (EStructuralFeature feature : type.getStructuralFeatures()) {
			if (feature instanceof EReference) {
				EReference reference = (EReference) feature;
				if (reference.isContainment()) {
					childReferences.add(reference);
				}
			}
		}
		EClass superType = type.getSuperType();
		if (superType != null) {
			Collection<? extends EReference> superTypeChildReferences = getChildReferences(superType);
			childReferences.addAll(superTypeChildReferences);
		}
		childReferencesMap.put(type, childReferences);
		return childReferences;
	}
	

	private boolean isAssignable(EClassifier targetType, EClass type) {
		EClass t = type;
		while (t != null) {
			if (t.equals(targetType)) {
				return true;
			}
			t = t.getSuperType();
		}
		return false;
	}

	private void setOrAddValue(EObject object, EObject value, EReference reference) {
		if (reference.isMany()) {
			@SuppressWarnings("unchecked")
			Collection<Object> children = (Collection<Object>) object.get(reference.getName());
			if (children == null) {
				children = objectFactory.createCollection(reference);
				object.set(reference.getName(), children);
			}
			children.add(value);
		} else {
			object.set(reference.getName(), value);
		}
	}
	
	private static class IterablesIterator implements Iterator<Object> {

		private Iterator<Iterable<Object>> iterables;
		private Iterator<Object> currentIterable;
		
		public IterablesIterator(Collection<Iterable<Object>> iterables) {
			this.iterables = iterables.iterator();
		}

		@Override
		public boolean hasNext() {
			if (currentIterable == null) {
				while (iterables.hasNext()) {
					currentIterable = iterables.next().iterator();
					if (currentIterable.hasNext()) {
						return true;
					}
				}
			} else {
				if (currentIterable.hasNext()) {
					return true;
				} else {
					currentIterable = null;
					return hasNext();
				}
			}
			return false;
		}

		@Override
		public Object next() {
			return currentIterable.next();
		}
	}
}
