package org.effortless.model.log;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProxyList<Type extends Object> extends Object implements List<Type> {

	protected ProxyList () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateList();
	}
	
	public ProxyList (List<Type> list) {
		this();
		setList(list);
	}
	
	protected List<Type> list;

	protected void initiateList() {
		this.list = null;
	}

	@javax.persistence.Transient
	public List<Type> getList() {
		return this.list;
	}

	protected void setList(List<Type> newValue) {
		this.list = newValue;
	}
	
	@Override
	public boolean add(Type o) {
		boolean result = false;
		result = this.list.add(o);
		return result;
	}

	@Override
	public void add(int index, Type o) {
		this.list.add(index, o);
	}

	@Override
	public boolean addAll(Collection<? extends Type> collection) {
		boolean result = false;
		result = this.list.addAll(collection);
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Type> collection) {
		boolean result = false;
		result = this.list.addAll(collection);
		return result;
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public boolean contains(Object o) {
		boolean result = false;
		result = this.list.contains(o);
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		boolean result = false;
		result = this.list.containsAll(collection);
		return result;
	}

	@Override
	public Type get(int index) {
		Type result = null;
		result = this.list.get(index);
		return result;
	}

	@Override
	public int indexOf(Object o) {
		int result = -1;
		result = this.list.indexOf(o);
		return result;
	}

	@Override
	public boolean isEmpty() {
		boolean result = false;
		result = this.list.isEmpty();
		return result;
	}

	@Override
	public Iterator<Type> iterator() {
		Iterator<Type> result = null;
		result = this.list.iterator();
		return result;
	}

	@Override
	public int lastIndexOf(Object o) {
		int result = 0;
		result = this.list.lastIndexOf(o);
		return result;
	}

	@Override
	public ListIterator<Type> listIterator() {
		ListIterator<Type> result = null;
		result = this.list.listIterator();
		return result;
	}

	@Override
	public ListIterator<Type> listIterator(int index) {
		ListIterator<Type> result = null;
		result = this.list.listIterator(index);
		return result;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = false;
		result = this.list.remove(o);
		return result;
	}

	@Override
	public Type remove(int index) {
		Type result = null;
		result = this.list.remove(index);
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean result = false;
		result = this.list.removeAll(collection);
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		boolean result = false;
		result = this.list.retainAll(collection);
		return result;
	}

	@Override
	public Type set(int index, Type o) {
		Type result = null;
		result = this.list.set(index, o);
		return result;
	}

	@Override
	public int size() {
		int result = 0;
		result = this.list.size();
		return result;
	}

	@Override
	public List<Type> subList(int fromIndex, int toIndex) {
		List<Type> result = null;
		result = this.list.subList(fromIndex, toIndex);
		return result;
	}

	@Override
	public Object[] toArray() {
		Object[] result = null;
		result = this.list.toArray();
		return result;
	}

	@Override
	public <T> T[] toArray(T[] array) {
		T[] result = null;
		result = this.list.toArray(array);
		return result;
	}

}
