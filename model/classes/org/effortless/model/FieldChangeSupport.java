package org.effortless.model;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Externalizable;
//import java.beans.javax_swing_border_MatteBorder_PersistenceDelegate;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class FieldChangeSupport extends Object implements Externalizable, Serializable {

	@javax.persistence.Transient
	protected transient Map<String, List<PropertyChangeListener>> listeners;

	@javax.persistence.Transient
	protected transient Object source;

	protected FieldChangeSupport () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.listeners = new HashMap<String, List<PropertyChangeListener>>();
		this.source = null;
		initLoadedProperties();
	}
	
	public FieldChangeSupport (Object source) {
		this();
		if (source == null) {
			throw new NullPointerException();
		}
		this.source = source;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(null);
			if (listeners == null) {
				listeners = new ArrayList<PropertyChangeListener>();
				this.listeners.put(null, listeners);
			}
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	public boolean containsPropertyChangeListener(PropertyChangeListener listener) {
		boolean result = false;
		if (listener != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(null);
			result = (listeners != null && listeners.contains(listener));
		}
		return result;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(null);
			if (listeners != null) {
				listeners.remove(listener);
			}
		}
	}

	@javax.persistence.Transient
	public List<PropertyChangeListener> getPropertyChangeListeners() {
		List<PropertyChangeListener> result = null;
		result = this.listeners.get(null);
		return result;
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener != null && propertyName != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(propertyName);
			if (listeners == null) {
				listeners = new ArrayList<PropertyChangeListener>();
				this.listeners.put(propertyName, listeners);
			}
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener != null && propertyName != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(propertyName);
			if (listeners != null) {
				listeners.remove(listener);
			}
		}
	}

	public List<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
		List<PropertyChangeListener> result = null;
		result = (propertyName != null ? this.listeners.get(propertyName) : null);
		return result;
	}

	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		boolean result = false;
		if (listener != null && propertyName != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(propertyName);
			result = (listeners != null && listeners.contains(listener));
		}
		return result;
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		firePropertyChange(new PropertyChangeEvent(this.source, propertyName, oldValue, newValue));
	}

	public void firePropertyChange(PropertyChangeEvent evt) {
//		Object oldValue = evt.getOldValue();
//		Object newValue = evt.getNewValue();
		String propertyName = evt.getPropertyName();
		List<PropertyChangeListener> common = getPropertyChangeListeners();
		List<PropertyChangeListener> named = (propertyName != null	? getPropertyChangeListeners(propertyName) : null);

		fire(common, evt);
		fire(named, evt);
	}

	protected void fire(List<PropertyChangeListener> listeners, PropertyChangeEvent event) {
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				listener.propertyChange(event);
			}
		}
	}

	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
		firePropertyChange(new IndexedPropertyChangeEvent(this.source, propertyName, oldValue, newValue, index));
	}

	public boolean hasListeners(String propertyName) {
		boolean result = false;
		
		List<PropertyChangeListener> common = getPropertyChangeListeners();
		result = (common != null && common.size() > 0);
		if (!result) {
			List<PropertyChangeListener> named = (propertyName != null	? getPropertyChangeListeners(propertyName) : null);
			result = (named != null && named.size() > 0);
		}
		
		return result;
	}
	
//	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
//		Kryo kryo = new Kryo()
//		
//		Input input = new Input(stream)
//		this.listeners = kryo.readObject(input, Map.class)
//		this.source = kryo.readObject(input, Object.class)
//		
//		input.close()
//	}
//
//	private void writeObject(java.io.ObjectOutputStream stream)	throws IOException {
//		Kryo kryo = new Kryo()
//		Output output = new Output(stream)
//		
//		kryo.writeObject(output, this.listeners)
//		kryo.writeObject(output, this.source)
//		
//		output.close()
//	}
	
	public void readExternal(ObjectInput stream) throws IOException, ClassNotFoundException {
		throw new java.lang.UnsupportedOperationException();
//		Kryo kryo = new Kryo();
//		
//		Input input = new Input(new ObjectInputStream(stream));
//		this.listeners = kryo.readObject(input, Map.class);
//		this.source = kryo.readObject(input, Object.class);
//		
//		input.close();
	}
		   
	public void writeExternal(ObjectOutput stream) throws IOException {
		throw new java.lang.UnsupportedOperationException();
//		Kryo kryo = new Kryo();
//		Output output = new Output(stream);
//		
//		kryo.writeObject(output, this.listeners);
//		kryo.writeObject(output, this.source);
//		
//		output.close();
	}

	
	
	
	protected String _loadedProperties_;
	protected static final String SEPARATOR_PROPERTIES = ",";
	
	protected void initLoadedProperties () {
		this._loadedProperties_ = null;
	}
	
//	public boolean checkLoaded(String property, boolean force) {
//		boolean result = false;
//		result = (this._loadedProperties_ != null && property != null ? this._loadedProperties_.contains(property + SEPARATOR_PROPERTIES) : false);
//		return result;
//	}
//
	public boolean checkLoaded(String property, boolean save) {
		boolean result = false;
		result = (this._loadedProperties_ != null && property != null ? this._loadedProperties_.contains(property + SEPARATOR_PROPERTIES) : false);
		if (!result && save && property != null) {
			this._loadedProperties_ = (this._loadedProperties_ != null ? this._loadedProperties_ : "") + property + SEPARATOR_PROPERTIES;
		}
		return result;
	}

	public boolean unloadProperty(String property) {
		boolean result = false;
		result = this.unloadProperty(property, null, null, true);
		return result;
	}

	public boolean unloadProperty(String property, Object oldValue, Object newValue, boolean notify) {
		boolean result = false;
		if (property != null && notify) {
			this.firePropertyChange(property, oldValue, newValue);
		}
		return result;
	}

	public boolean unloadProperty(String property, boolean notify) {
		boolean result = false;
		result = this.unloadProperty(property, null, null, notify);
		return result;
	}

	public void unloadProperties() {
		String[] array = (this._loadedProperties_ != null ? this._loadedProperties_.split(SEPARATOR_PROPERTIES) : null);
		if (array != null) {
			for (String property : array) {
				unloadProperty(property);
			}
		}
		this._loadedProperties_ = null;
	}

	public String doGetLoadedProperties() {
		String result = null;
		result = (this._loadedProperties_ != null ? this._loadedProperties_.substring(0, this._loadedProperties_.length() - 1) : this._loadedProperties_);
		return result;
	}

	
	
	
	
	
	
	public boolean checkRead(String property, boolean force) {
		boolean result = false;
		// TODO Auto-generated method stub
		return result;
	}

	public void unreadProperty(String property) {
		// TODO Auto-generated method stub
		
	}

	public void unreadProperties() {
		// TODO Auto-generated method stub
		
	}

	public String doGetReadProperties() {
		String result = null;
		// TODO Auto-generated method stub
		return result;
	}

	public void enableNotifyChanges() {
		// TODO Auto-generated method stub
		
	}

	public void enableDisableChangeEvents() {
		// TODO Auto-generated method stub
		
	}

	public void disableDisableChangeEvents() {
		// TODO Auto-generated method stub
		
	}

	public void disableNotifyChanges() {
		// TODO Auto-generated method stub
		
	}

}
