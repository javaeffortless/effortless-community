package org.effortless.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import org.effortless.model.log.LogChangeItem;
import org.effortless.model.log.ProxyList;
import org.effortless.util.CvtrUtils;

public class ListPropertyChanges<Type extends Entity<Type>> extends ProxyList<Type> implements PropertyChangeListener, LogChangeItem {

	protected ListPropertyChanges () {
		super();
	}
	
	public ListPropertyChanges(Entity<?> parent, String propertyName, List<Type> list, boolean exclusive) {
		super(list);
		this.parent = parent;
		this.propertyName = propertyName;
		this.persistList = null;
		this.clear = false;
		this.exclusive = exclusive;
	}
	
	public void setList(List<Type> newValue) {
		super.setList(newValue);
	}
	
	protected boolean exclusive;
	
	protected Entity<?> parent;
	
	protected List<Type> persistList;
	
	public List<Type> toPersist () {
		return this.persistList;
	}
	
	public void clearPersist () {
		this.persistList = null;
	}
	
	protected boolean clear;
	
	public void clear () {
		super.clear();
		this.clear = true;
	}
	
	@Override
	public boolean add(Type o) {
		boolean result = false;
		if (o != null && !contains(o)) {
			applyListener(o);
			this.persistList = (this.persistList != null ? this.persistList : new ArrayList<Type>());
			this.persistList.add(o);
			result = super.add(o);
		}
		return result;
	}
	
	@Override
	public Type get(int index) {
		Type result = null;
		result = super.get(index);
		applyListener(result);
		return result;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean result = false;
		if (o != null) {
			if (this.exclusive) {
				AbstractIdEntity toDelete = null;
				try {
					toDelete = (AbstractIdEntity)o;
				}
				catch (ClassCastException e) {
				}
				if (toDelete != null ) {
					toDelete.setDeleted(Boolean.TRUE);
				}
			}
			result = super.remove(o);
			if (this.exclusive) {
				this.persistList = (this.persistList != null ? this.persistList : new ArrayList<Type>());
				if (!this.persistList.contains(o)) {
					this.persistList.add((Type)o);
					notifyParent();
				}
			}
		}
		return result;
	}
	
	protected void notifyParent () {
		if (this.parent != null) {
			this.parent.firePropertyChange(this.propertyName, null, this);
		}
	}
	
	protected void applyListener (Type o) {
		if (o != null && !o.containsPropertyChangeListener(this)) {
			o.addPropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null) {
			Type evtSource = (Type)event.getSource();
			if (evtSource != null) {
				if (this.persistList == null || !this.persistList.contains(evtSource)) {
					this.persistList = (this.persistList != null ? this.persistList : new ArrayList<Type>());
					this.persistList.add(evtSource);
					notifyParent();
				}
			}
		}
	}

	protected String propertyName;
	
	protected void initiatePropertyName () {
		this.propertyName = null;
	}
	
	@Override
	public String getPropertyName() {
		return this.propertyName;
	}
	
	protected void setPropertyName (String newValue) {
		this.propertyName = newValue;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		this.propertyName = (String)in.readObject();
		this.persistList = (List<Type>)in.readObject();
		this.clear = in.readBoolean();
		this.exclusive = in.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.propertyName);
		out.writeObject(this.persistList);
		out.writeBoolean(this.clear);
		out.writeBoolean(this.exclusive);
	}

	@Override
	public String toText() {
		if (this.text == null && this.persistList != null && this.persistList.size() > 0) {
			String result = null;
	
			String propertyName = toText(this.propertyName);
			
				String changes = toText(false, false, true);
				String deletes = toText(false, true, false);
				String creates = toText(true, false, false);
				
				if (changes.length() > 0 || deletes.length() > 0 || creates.length() > 0 || this.clear) {
					String strClear = (this.clear ? propertyName + "_clear" : "");
					String strChanges = (changes != null ? propertyName + "_changes=" + changes : "");
					String strDeletes = (deletes != null ? propertyName + "_deletes=" + deletes : "");
					String strCreates = (creates != null ? propertyName + "_creates=" + creates : "");
					boolean flagChanges = (strChanges != null && strChanges.length() > 0);
					boolean flagDeletes = (strDeletes != null && strDeletes.length() > 0);
					boolean flagCreates = (strCreates != null && strCreates.length() > 0);
					result = "";
					result += strClear;
					result += (this.clear && flagChanges ? "\n": "");
					result += strChanges;
					result += ((flagChanges || this.clear) && flagDeletes ? "\n": "");
					result += strDeletes;
					result += ((flagChanges || flagDeletes || this.clear) && flagCreates ? "\n": "");
					result += strCreates;
				}
				
			this.text = result;
		}
		return this.text;
	}
	
	protected String toText (boolean created, boolean deleted, boolean changed) {
		String result = "";
		if (this.persistList != null) {
			for (Type item : this.persistList) {
				if (item != null) {
					Type selected = null;
					if (created && item.hasBeenCreated()) {
						selected = item;
					}
					else if (deleted && item.hasBeenDeleted()) {
						selected = item;
					}
					else if (changed && item.hasBeenChanged()) {
						selected = item;
					}
					if (selected != null) {
						String text = toText(selected);
						result += (result.length() > 0 && text.length() > 0 ? ", " : "") + text;
					}
				}
			}
		}
		return result;
	}

	
	
	public String toString () {
		return toText();
	}
	
	protected String text;
	
	protected String toText (Object value) {
		String result = null;
		result = CvtrUtils.getInstance().toText(value);
//		result = (value != null ? value.toString() : null);
		result = (result == null ? "(null)" : result);
		return result;
	}

	
	

	@Override
	public void restore(Object target) {
		// TODO Auto-generated method stub
		
	}

	public boolean isExclusive() {
		return this.exclusive;
	}
	
	
}
