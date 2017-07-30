package de.generia.tools.model.api.runtime.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.generia.tools.model.api.runtime.EObject;

/**
 * Optimized Set implementation for supporting object check when serializing object graphs.
 */
public class EObjectSet implements Set<EObject> {
	private static final int UNDEFINED_SET_ID = 0;
	private static int setIdSequence = 0;
	
	private int setId;
	
	public EObjectSet() {
		clear();
	}
	
	public static int nextId() {
		return ++setIdSequence;
	}

	@Override
	public boolean contains(Object object) {
		Item item = getItem(object);
		boolean contains = item.eGetObjectSetId() == setId;
		return contains;
	}

	private Item getItem(Object object) {
		if (object instanceof Item) {
			return (Item) object;
		}
		throw new IllegalArgumentException("given object is of type '" + object.getClass().getName() + "' but not an instance of type '" + Item.class.getName() + "'");
	}

	@Override
	public boolean add(EObject object) {
		Item item = getItem(object);
		boolean contains = item.eGetObjectSetId() == setId;
		item.eSetObjectSetId(setId);
		return !contains;
	}

	@Override
	public boolean remove(Object object) {
		Item item = getItem(object);
		boolean contains = item.eGetObjectSetId() == setId;
		item.eSetObjectSetId(UNDEFINED_SET_ID);
		return contains;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		boolean contains = true;
		for (Object object : collection) {
			if (!contains(object)) {
				contains = false;
				break;
			}
		}
		return contains;
	}

	@Override
	public boolean addAll(Collection<? extends EObject> collection) {
		boolean changed = false;
		for (EObject object : collection) {
			if (add(object)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean changed = false;
		for (Object object : collection) {
			if (remove(object)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public void clear() {
		this.setId = nextId();
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<EObject> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	public interface Item {
		int eGetObjectSetId();
		void eSetObjectSetId(int setId);
	}
}
