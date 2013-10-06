package org.effortless.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.effortless.core.PropertyUtils;


public class MapFilter extends Object implements Map<String, Object> {

	public MapFilter () {
		super();
		initiate();
	}
	
	public MapFilter (Filter filter) {
		this();
		this.filter = filter;
	}
	
	protected void initiate () {
		initiateFilter();
	}
	
	protected Filter filter;
	
	protected void initiateFilter () {
		this.filter = null;
	}
	
	public Filter getFilter () {
		return this.filter;
	}
	
	public void setFilter (Filter newValue) {
		this.filter = newValue;
	}
	
	@Override
	public void clear() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
//		boolean result = false;
//		result = PropertyUtils.containsProperty(this.filter, (String)key);
//		return result;
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		Object result = null;
		result = PropertyUtils.getProperty(this.filter, (String)key);
		return result;
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		Set<String> result = null;
		String[] names = PropertyUtils.getPropertyNames(this.filter);
		result = new HashSet<String>();
		int length = (names != null ? names.length : 0);
		for (int i = 0; i < length; i++) {
			String name = names[i];
			result.add(name);
		}
		return result;
	}

	@Override
	public Object put(String key, Object value) {
		Object result = null;
		result = get(key);
		PropertyUtils.setProperty(this.filter, key, value);
		return result;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}

}
