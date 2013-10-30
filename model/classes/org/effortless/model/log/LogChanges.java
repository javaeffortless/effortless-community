package org.effortless.model.log;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import org.effortless.core.ObjectUtils;
import org.effortless.model.ListPropertyChanges;

public class LogChanges extends Object implements Externalizable {

	public LogChanges () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateChanges();
	}
	
	protected List<LogChangeItem> changes;
	
	protected void initiateChanges () {
		this.changes = null;
	}
	
	public List<LogChangeItem> getChanges () {
		return this.changes;
	}
	
	public void setChanges (List<LogChangeItem> newValue) {
		this.changes = newValue;
	}
	
	public boolean hasChanges () {
		return (this.changes != null ? this.changes.size() > 0 : false);
	}
	
	public void addChange (LogChangeItem change) {
		if (change != null) {
			this.changes = (this.changes == null ? new ArrayList<LogChangeItem>() : this.changes);
			this.changes.add(change);
		}
	}
	
	public void addChange (String propertyName, Object oldValue, Object newValue) {
		addChange(propertyName, oldValue, newValue, true);
	}
	
	public void addChange (String propertyName, Object oldValue, Object newValue, boolean check) {
		boolean add = (!check || !ObjectUtils.equals(oldValue, newValue));
		if (add) {
			LogChangeItem change = loadProperty(propertyName);
			if (change == null) {
				BaseLogChangeItem bchange = new BaseLogChangeItem();
				bchange.setPropertyName(propertyName);
				bchange.setOldValue(oldValue);
				bchange.setNewValue(newValue);
				addChange(bchange);
			}
			else {
				try {
					BaseLogChangeItem bchange = (BaseLogChangeItem)change;
					bchange.setNewValue(newValue);
				}
				catch (ClassCastException e) {
					
				}
			}
		}
	}
	
	protected LogChangeItem loadProperty(String propertyName) {
		LogChangeItem result = null;
		if (this.changes != null) {
			propertyName = (propertyName != null ? propertyName.trim() : "");
			if (propertyName.length() > 0) {
				LogChangeItem toResult = null;
				for (LogChangeItem item : this.changes) {
					String itemProperty = item.getPropertyName();
					itemProperty = (itemProperty != null ? itemProperty.trim() : "");
					if (propertyName.equals(itemProperty)) {
						toResult = item;
						break;
					}
				}
				try {
					result = (BaseLogChangeItem)toResult;
				}
				catch (ClassCastException e) {
					result = null;
				}
			}
		}
		return result;
	}

	public void addChange (String propertyName, List<?> value) {
		addChange(propertyName, value, true);
	}

	public void addChange (String propertyName, List<?> value, boolean check) {
		boolean add = (!check || checkChange(value));
		if (add) {
			removeProperty(propertyName);
			LogChangeItem change = null;
			try {
				change = (ListPropertyChanges)value;
			}
			catch (ClassCastException e) {
			}
			if (change != null) {
				addChange(change);
				change.toText();
			}
		}
	}
	
	protected void removeProperty(String propertyName) {
		if (this.changes != null) {
			propertyName = (propertyName != null ? propertyName.trim() : "");
			if (propertyName.length() > 0) {
				LogChangeItem toRemove = null;
				for (LogChangeItem item : this.changes) {
					String itemProperty = item.getPropertyName();
					itemProperty = (itemProperty != null ? itemProperty.trim() : "");
					if (propertyName.equals(itemProperty)) {
						toRemove = item;
						break;
					}
				}
				if (toRemove != null) {
					this.changes.remove(toRemove);
				}
			}
		}
	}

	protected boolean checkChange (List<?> value) {
		boolean result = false;
		result = (value != null ? true/*value.hasChanges()*/ : false);
		return result;
	}
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.changes = (List<LogChangeItem>)in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.changes);
	}
	
	public String toString () {
		return toText();
	}
	
	public String toText () {
		String result = null;
		int length = (this.changes != null ? this.changes.size() : 0);
		boolean flag = false;
		for (int i = 0; i < length; i++) {
			AbstractLogChangeItem item = (AbstractLogChangeItem)this.changes.get(i);
			if (item != null) {
				String text = item.toText();
				if (text != null) {
					result = (flag ? result + "\n" : "");
					result += text;
					flag = (!flag ? result.length() > 0 : flag);
				}
//				System.out.println(">>>>>>> log text = " + text);
			}
		}
		result = (result != null ? result : "(null)");
		return result;
	}

	public boolean hasChange(String property) {
		boolean result = false;
		if (this.changes != null && property != null) {
			for (LogChangeItem item : this.changes) {
				if (property.equals(item.getPropertyName())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public void restore (Object target) {
		if (this.changes != null && target != null) {
			for (LogChangeItem item : this.changes) {
				item.restore(target);
			}
			this.changes = null;
		}
	}
	
	public void restore (Object target, String property) {
		if (target != null && this.changes != null && property != null) {
			property = property.trim();
			if (property.length() > 0) {
				LogChangeItem toRemove = null;
				for (LogChangeItem item : this.changes) {
					String itemProperty = item.getPropertyName();
					itemProperty = (itemProperty != null ? itemProperty.trim() : "");
					if (property.equals(itemProperty)) {
						item.restore(target);
						toRemove = item;
						break;
					}
				}
				if (toRemove != null) {
					this.changes.remove(toRemove);
				}
			}
		}
	}
	
}
