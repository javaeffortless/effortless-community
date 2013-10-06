package org.effortless.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import org.effortless.core.ObjectUtils;

//@Entity
//@Table(name="APP_CFG")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
@MappedSuperclass
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class AbstractCfg<Type extends AbstractCfg<Type>> extends AbstractIdEntity<Type> {

	public AbstractCfg () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateCreationDate();
		initiateLastModification();
		initiateEnable();
		initiateAuthor();
		initiateDefaultPageSize();
		initiateComment();
	}

	public void onPreCreate () {
		super.onPreCreate();
		Date creationDate = getCreationDate();
		if (creationDate == null) {
			creationDate = new Date();
			setCreationDate(creationDate);
		}
		Date lastModification = getLastModification();
		if (lastModification == null) {
			setLastModification(creationDate);
		}
		Integer defaultPageSize = getDefaultPageSize();
		if (defaultPageSize == null) {
			defaultPageSize = Integer.valueOf(25);
			setDefaultPageSize(defaultPageSize);
		}
		Boolean enable = getEnable();
		if (enable == null) {
			enable = Boolean.TRUE;
			setEnable(enable);
		}
	}
	
	public void onPreUpdate () {
		super.onPreUpdate();
		Date lastModification = new Date();
		setLastModification(lastModification);
	}
	
	
	protected Date creationDate;

	protected void initiateCreationDate() {
		this.creationDate = null;
	}

	@javax.persistence.Column(name="CFG_CREATIONDATE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date newValue) {
		Date oldValue = getCreationDate();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.creationDate = newValue;
			firePropertyChange("creationDate", oldValue, newValue);
		}
	}
	
	protected Date lastModification;

	protected void initiateLastModification() {
		this.lastModification = null;
	}

	@javax.persistence.Column(name="CFG_LASTMODIFICATION")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Date getLastModification() {
		return this.lastModification;
	}

	public void setLastModification(Date newValue) {
		Date oldValue = this.lastModification;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.lastModification = newValue;
			firePropertyChange("lastModification", oldValue, newValue);
		}
	}
	
	protected Boolean enable;

	protected void initiateEnable() {
		this.enable = null;
	}

	@javax.persistence.Column(name="CFG_ENABLE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean newValue) {
		Boolean oldValue = getEnable();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.enable = newValue;
			firePropertyChange("enable", oldValue, newValue);
		}
	}
	
	protected String author;

	protected void initiateAuthor() {
		this.author = null;
	}

	@javax.persistence.Column(name="CFG_AUTHOR")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String newValue) {
		String oldValue = getAuthor();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.author = newValue;
			firePropertyChange("author", oldValue, newValue);
		}
	}
	
	protected Integer defaultPageSize;

	protected void initiateDefaultPageSize() {
		this.defaultPageSize = null;
	}

	@javax.persistence.Column(name="CFG_DEFAULTPAGESIZE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Integer getDefaultPageSize() {
		return this.defaultPageSize;
	}

	public void setDefaultPageSize(Integer newValue) {
		Integer oldValue = getDefaultPageSize();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.defaultPageSize = newValue;
			firePropertyChange("defaultPageSize", oldValue, newValue);
		}
	}

	protected String comment;

	protected void initiateComment() {
		this.comment = null;
	}

	@javax.persistence.Column(name="CFG_COMMENT", length=3072)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String newValue) {
		String oldValue = getComment();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.comment = newValue;
			firePropertyChange("comment", oldValue, newValue);
		}
	}
	
	protected Map<String, Object> properties;

	protected void initiateProperties() {
		this.properties = new HashMap<String, Object>();
	}

	@javax.persistence.Column(name="CFG_PROPERTIES")
	@Lob
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	@org.hibernate.annotations.Type(type="org.effortless.model.MapUserType")
	protected Map<String, Object> getProperties() {
		return this.properties;
	}

	protected void setProperties(Map<String, Object> newValue) {
		Map<String, Object> oldValue = getProperties();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.properties = newValue;
			firePropertyChange("properties", oldValue, newValue);
		}
	}

	public boolean existsProperty (String property) {
		boolean result = false;
		Map<String, Object> properties = getProperties();
		result = (properties != null && properties.containsKey(property));
		return result;
	}
	
	@javax.persistence.Transient
	public <T extends Object> T getProperty (Class<T> clazz, String property, T defaultValue) {
		T result = defaultValue;
		Map<String, Object> properties = getProperties();
		if (properties != null && properties.containsKey(property)) {
			result = (T)properties.get(property);
		}
		return result;
	}

	public void setProperty (String property, Object newValue) {
		Object oldValue = getProperty(Object.class, property, null);
		if (!ObjectUtils.equals(oldValue, newValue) || (newValue != null && oldValue == null)) {
			Map<String, Object> properties = getProperties();
			if (properties == null) {
				properties = new HashMap<String, Object>();
				setProperties(properties);
			}
			properties.put(property, newValue);
			firePropertyChange(property, oldValue, newValue);
		}
	}

	public static AbstractCfg getCurrent () {
		return AbstractCfg.getCurrent(AbstractCfg.class);
	}
	
	public static <T extends AbstractCfg<T>> T getCurrent (Class<T> clazz) {
		T result = null;
		
		Filter<T> filter = new EntityFilter(clazz).isTrue("enable").sortBy("lastModification DESC");
		
//		CurrentAppCfgFilter filter = new CurrentAppCfgFilter(clazz);
		filter.setPageIndex(Integer.valueOf(0));
		filter.setPageSize(Integer.valueOf(1));
		filter.setPaginated(Boolean.TRUE);
		if (filter.size() > 0) {
			try {
				result = (T)filter.get(0);
			}
			catch (Throwable t) {
				throw new org.effortless.core.ModelException(t);
			}
		}
		try {
			result = (result != null ? result : clazz.newInstance());
		} catch (Throwable e) {
			throw new org.effortless.core.ModelException(e);
		}
		return result;
	}
	
}
