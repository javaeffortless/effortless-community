package org.effortless.server.binder;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.effortless.core.PropertyUtils;

public class BeanMapLoadOnDemand extends Object implements Map<String, Object> {

	protected BeanMapLoadOnDemand () {
		super();
		this.bean = null;
	}
	
	public BeanMapLoadOnDemand (Object bean) {
		this();
		this.bean = bean;
	}
	
	protected Object bean;
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Object key) {
		return PropertyUtils.getProperty(this.bean, (String)key);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		Object result = null;
		result = get(key);
		PropertyUtils.setProperty(this.bean, key, value);
		return result;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
