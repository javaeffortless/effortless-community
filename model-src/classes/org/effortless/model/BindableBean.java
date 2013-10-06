package org.effortless.model;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface BindableBean {

    public void addPropertyChangeListener(PropertyChangeListener listener);
	
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public List<PropertyChangeListener> getPropertyChangeListeners ();
    
    public List<PropertyChangeListener> getPropertyChangeListeners (String propertyName);

    public boolean hasListeners (String propertyName);

    public void removePropertyChangeListener (PropertyChangeListener listener);

    public void removePropertyChangeListener (String propertyName, PropertyChangeListener listener);

    public boolean containsPropertyChangeListener (PropertyChangeListener listener);

    public boolean containsPropertyChangeListener (String propertyName, PropertyChangeListener listener);
	
}
