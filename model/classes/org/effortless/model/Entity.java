package org.effortless.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Externalizable;
import java.io.Serializable;
import java.util.List;

public interface Entity<Type extends Entity<Type>> extends RestrictionsEntity, EntityEvents, EntityLog, AutoEntity<Type>, Externalizable {
	
//	public List<Type> listBy (String methodName, Object... parameters);
//	
//	public List<Type> listBy (String methodName, String orderBy, Object... parameters);
//
//	public Type findBy (String methodName, Object... parameters);
//	
//	public Type findBy (String methodName, String orderBy, Object... parameters);
//	
//	public Long countBy (String methodName, Object... parameters);

	public Serializable doGetIdentifier ();

	public static final String CALL_ON_READ = "Entity.CALL_ON_READ";
	
	public void onRead ();
	
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public boolean containsPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
	@javax.persistence.Transient
	public List<PropertyChangeListener> getPropertyChangeListeners();
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public List<PropertyChangeListener> getPropertyChangeListeners(String propertyName);
	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue);
	public void firePropertyChange(PropertyChangeEvent evt);
	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue);
	public boolean hasListeners(String propertyName);
	
	public boolean hasBeenChanged();
	public boolean hasBeenCreated();
	public boolean hasBeenDeleted();
	
	
	
	public boolean existsAttribute (String attribute);
	
	public <T extends Object> T getAttribute (Class<T> clazz, String attribute, T defaultValue);
	
	public Object getAttribute (String attribute, Object defaultValue);
	
	public Object getAttribute (String attribute);
	
	public <T extends Object> T getAttribute (Class<T> clazz, String attribute);
	
	public void setAttribute (String attribute, Object newValue);

	
	public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException;
	  
	public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException;
	
	public Boolean getCheckHasId ();
	
	public Type clone ();

}
