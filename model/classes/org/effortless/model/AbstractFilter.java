package org.effortless.model;

import java.beans.PropertyChangeListener;

//import java.beans.PropertyChangeSupport;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.effortless.core.GlobalContext;
//import org.effortless.MySession;
import org.effortless.core.ObjectUtils;
import org.effortless.security.Resource;
import org.effortless.security.SecurityResponse;
import org.effortless.security.SecuritySeverity;
import org.effortless.security.SecuritySystem;
import org.effortless.security.resources.ResourceType;
import org.effortless.server.binder.BeanMapLoadOnDemand;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

public abstract class AbstractFilter<Type extends Object> extends Object/*AbstractList<Type>*/ implements List<Type>, Filter<Type> {

	public AbstractFilter () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initFcs();
		initiateDeleted();
		initiatePaginated();
		initiatePageIndex();
		initiatePageSize();
		initiateOrderBy();
		this._map = null;
	}
	
	public void addPropertyChangeListener (PropertyChangeListener listener) {
		this._fcs.addPropertyChangeListener(listener);
	}
	
	protected Class<?> doGetFilterClass () {
		Class<Type> result = null;
		Object superClass = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = null;
		parameterizedType = (ParameterizedType)superClass;
//		try {
//			parameterizedType = (ParameterizedType)superClass;
//		}
//		catch (ClassCastException e) {
//			try {
//				Class<?> clazz = (Class)superClass;
//				superClass = clazz.getGenericSuperclass();
//				parameterizedType = (ParameterizedType)superClass;
//			}
//			catch (ClassCastException e1) {
//				
//			}
//			
//		}
		result = (parameterizedType != null ? (Class) parameterizedType.getActualTypeArguments()[0] : null);
		return result;
	}
	
	protected ClassMetadata doGetMetadata () {
		ClassMetadata result = null;

		Class<?> clazz = doGetFilterClass();		
		
		SessionFactory sf = SessionManager.loadSessionFactory(clazz);
		result = sf.getClassMetadata(clazz);

		return result;
	}
	
	protected FieldChangeSupport _fcs;
	
	protected void initFcs () {
		this._fcs = new FieldChangeSupport(this);
	}
	
	protected Boolean deleted;
	
	protected void initiateDeleted () {
		this.deleted = Boolean.FALSE;
	}
	
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean newValue) {
		this._setProperty("deleted", this.deleted, this.deleted = newValue);
//		Boolean oldValue = this.deleted;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.deleted = newValue;
//			updateFilter();
//			this._fcs.firePropertyChange("deleted", oldValue, newValue);
//		}
	}

	protected Boolean paginated;
	
	protected void initiatePaginated () {
		this.paginated = Boolean.TRUE;
	}
	
	public Boolean getPaginated() {
		return this.paginated;
	}

	public void setPaginated(Boolean newValue) {
		this._setProperty("paginated", this.paginated, this.paginated = newValue);
//		Boolean oldValue = this.paginated;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.paginated = newValue;
//			updateFilter();
//			this._fcs.firePropertyChange("paginated", oldValue, newValue);
//		}
	}

	protected Integer pageIndex;

	protected void initiatePageIndex () {
		this.pageIndex = Integer.valueOf(0);
	}
	
	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Integer newValue) {
		this._setProperty("pageIndex", this.pageIndex, this.pageIndex = newValue);
//		Integer oldValue = this.pageIndex;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.pageIndex = newValue;
//			updateFilter();
//			this._fcs.firePropertyChange("pageIndex", oldValue, newValue);
//		}
	}
	
	public void previousPage () {
		Integer pageIndex = getPageIndex();
		if (pageIndex != null) {
			int idx = (pageIndex.intValue());
			if (idx > 0) {
				setPageIndex(Integer.valueOf(idx - 1));
			}
		}
	}
	
	public void nextPage () {
		Integer pageIndex = getPageIndex();
		if (pageIndex != null) {
			int idx = (pageIndex.intValue());
			Integer _totalPages = getTotalPages();
			int totalPages = (_totalPages != null ? _totalPages.intValue() : 0);
			if (idx < (totalPages - 1)) {
				setPageIndex(Integer.valueOf(idx + 1));
			}
		}
		
	}

	protected Integer pageSize;
	
	protected void initiatePageSize () {
		this.pageSize = loadDefaultPageSize();
//		this.pageSize = Integer.valueOf(25);
//		this.pageSize = Integer.valueOf(1);
	}
	
	public static Integer loadDefaultPageSize () {
		Integer result = null;
		try { result = GlobalContext.get(GlobalContext.DEFAULT_PAGE_SIZE, Integer.class); }	catch (Throwable t) {}
		result = (result != null && result.intValue() > 0 ? result : Integer.valueOf(25));
		return result;
	}
	
	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer newValue) {
		this._setProperty("pageSize", this.pageSize, this.pageSize = newValue);
//		Integer oldValue = this.pageSize;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.pageSize = newValue;
//			updateFilter();
//			this._fcs.firePropertyChange("pageSize", oldValue, newValue);
//		}
	}

	public Integer getTotalPages () {
		Integer result = null;
		int numElements = size();
		int pageSize = (this.pageSize != null ? this.pageSize.intValue() : 0);
		pageSize = (pageSize > 0 ? pageSize : numElements);
		int total = (pageSize > 0 ? (numElements / pageSize) + ((numElements % pageSize > 0) ? 1 : 0) : 0);
		result = Integer.valueOf(total);
		return result;
	}
	
	public void setTotalPages (Integer newValue) {
		int total = (newValue != null ? newValue.intValue() : 0);
		if (total > 0) {
			int numElements = size();
			int pageSize = (numElements / total) + ((numElements % total > 0) ? 1 : 0);
			setPageSize(Integer.valueOf(pageSize));
		}
	}
	
	protected String orderBy;
	
	protected void initiateOrderBy () {
		this.orderBy = null;
	}
	
	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String newValue) {
		this._setProperty("orderBy", this.orderBy, this.orderBy = newValue);
//		String oldValue = this.orderBy;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.orderBy = newValue;
//			updateFilter();
//			this._fcs.firePropertyChange("orderBy", oldValue, newValue);
//		}
	}

	protected void updateFilter() {
	}

	public List<Type> listPage() {
		List<Type> result = null;
		if (this.paginated != null && this.paginated) { 
			result = new ArrayList<Type>();
			int pageSize = (this.pageSize != null ? this.pageSize.intValue() : 0);
			int size = size();
			int offset = 0;
			int length = (this.pageSize != null ? this.pageSize.intValue() : 0);
			for (int i = 0; i < pageSize; i++) {
				
			}
			
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends Type> c) {
		boolean result = false;
		if (c != null) {
			Iterator<? extends Type> iterator = c.iterator();
			if (iterator != null) {
				result = true;
				while (iterator.hasNext()) {
					 Type item = iterator.next();
					 if (!add(item)) {
						 result = false;
					 }
				}
			}
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Type> c) {
		boolean result = false;
		if (c != null) {
			Iterator<? extends Type> iterator = c.iterator();
			if (iterator != null) {
				result = true;
				while (iterator.hasNext()) {
					Type item = iterator.next();
					add(index, item);
				}
			}
		}
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean result = false;
		if (c != null) {
			Iterator<?> iterator = c.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					result = true;
					Object item = iterator.next();
					if (!contains(item)) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public int indexOf(Object o) {
		int result = -1;
		if (o != null) {
			Iterator<Type> iterator = iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					result += 1;
					Type item = iterator.next();
					if (o.equals(item)) {
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
//		return false;
		return size() <= 0;
	}
	
	public Integer getSize () {
		return Integer.valueOf(size());
	}

	@Override
	public int lastIndexOf(Object o) {
		int result = -1;
		if (o != null) {
			Iterator<Type> iterator = iterator();
			if (iterator != null) {
				int index = -1;
				while (iterator.hasNext()) {
					result += 1;
					Type item = iterator.next();
					if (o.equals(item)) {
						index = result;
					}
				}
				result = index;
			}
		}
		return result;
	}

	@Override
	public ListIterator<Type> listIterator() {
		return listIterator(0);
	}

	@Override
	public Type remove(int index) {
		Type result = null;
		result = get(index);
		if (result != null) {
			remove(result);
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = false;
		if (c != null) {
			Iterator<?> iterator = c.iterator();
			if (iterator != null) {
				result = true;
				while (iterator.hasNext()) {
					Object item = iterator.next();
					if (!remove(item)) {
						result = false;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] result = null;
		List<Object> list = new ArrayList<Object>();
		Iterator<Type> iterator = iterator();
		if (iterator != null) {
			while (iterator.hasNext()) {
				Type item = iterator.next();
				list.add(item);
			}
		}
		result = list.toArray();
		return result;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	@Override
	public abstract boolean add(Type e);
//		throw new org.effortless.model.exceptions.ModelException(new UnsupportedOperationException());

	@Override
	public abstract void add(int index, Type element);

	@Override
	public abstract void clear();

	@Override
	public abstract boolean contains(Object o);

	@Override
	public abstract Type get(int index);
	@Override
	public abstract Iterator<Type> iterator();

	@Override
	public abstract ListIterator<Type> listIterator(int index);

	public abstract boolean remove(Object o);

	@Override
	public Type set(int index, Type element) {
		throw new org.effortless.core.ModelException(new UnsupportedOperationException());
	}

	@Override
	public abstract int size();

	@Override
	public List<Type> subList(int fromIndex, int toIndex) {
		List<Type> result = null;
		if (fromIndex >= 0 && toIndex >= 0 && toIndex > fromIndex) {
			result = new ArrayList<Type>();
			for (int i = fromIndex; i < toIndex; i++) {
				Type item = get(i);
				result.add(item);
			}
		}
		return result;
	}

	
	
	
	protected boolean _checkSecuritySystem () {
		boolean result = true;
		SecuritySystem securitySystem = SessionManager.getSecuritySystem();
		if (securitySystem != null) {
			Resource resource = new Resource();
			resource.setType(ResourceType.FILTER);
			resource.setObject(this);
			SecurityResponse response = securitySystem.check(resource);
			if (response != null && !response.isAllow()) {
				SecuritySeverity severity = response.getSeverity();
				if (SecuritySeverity.ERROR.equals(severity)) {
					response.throwSecurityException();
				}
				else if (SecuritySeverity.NULL.equals(severity)) {
					result = false;
				}
				else {
					response.throwSecurityException();
				}
			}
		}
		return result;
	}
	
	protected boolean _checkSecurityAction (String actionName) {
		return _checkSecurityAction(actionName, null, null);
	}
	
	protected boolean _checkSecurityAction (String actionName, Class<?> returnType, Class<?>[] parameters) {
		boolean result = true;
		SecuritySystem securitySystem = SessionManager.getSecuritySystem();
		if (securitySystem != null) {
			Resource resource = new Resource();
			resource.setObject(this.doGetFilterClass());
			resource.setName(actionName);
			resource.setType(ResourceType.ACTION);
			resource.setReturnType(returnType);
			resource.setParamTypes(parameters);
			
			SecurityResponse response = securitySystem.check(resource);
			if (response != null && !response.isAllow()) {
				SecuritySeverity severity = response.getSeverity();
				if (SecuritySeverity.ERROR.equals(severity)) {
					response.throwSecurityException();
				}
				else if (SecuritySeverity.NULL.equals(severity)) {
					result = false;
				}
				else {
					response.throwSecurityException();
				}
			}
		}
		return result;
	}

	
	protected Object _setProperty (String propertyName, Object oldValue, Object newValue) {
		if (!_equals(oldValue, newValue)) {
			_doChangeProperty(propertyName, oldValue, newValue);
			updateFilter();
			firePropertyChange(propertyName, oldValue, newValue);
		}
		return oldValue;
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this._fcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void _doChangeProperty (String propertyName, Object oldValue, Object newValue) {
	}
	
	protected boolean _equals (Object value1, Object value2) {
		return ObjectUtils.equals(value1, value2);
	}
	
	protected Map<String, Object> _map;

	public Map<String, Object> toMap () {
		if (this._map == null) {
//			this._map = new BeanMap(this);
			this._map = new BeanMapLoadOnDemand(this);
		}
		return this._map;
	}

	
	
}
