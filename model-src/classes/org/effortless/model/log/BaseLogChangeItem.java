package org.effortless.model.log;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationTargetException;

import org.effortless.core.PropertyUtils;

public class BaseLogChangeItem extends AbstractLogChangeItem {

	public BaseLogChangeItem () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateOldValue();
		initiateNewValue();
	}
	
	protected Object oldValue;
	
	protected void initiateOldValue () {
		this.oldValue = null;
	}
	
	public Object getOldValue () {
		return this.oldValue;
	}
	
	public void setOldValue (Object newValue) {
		this.oldValue = newValue;
		this.text = null;
	}
	
	protected Object newValue;
	
	protected void initiateNewValue () {
		this.newValue = null;
	}
	
	public Object getNewValue () {
		return this.newValue;
	}
	
	public void setNewValue (Object newValue) {
		this.newValue = newValue;
		this.text = null;
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);
		this.oldValue = (Object)in.readObject();
		this.newValue = (Object)in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeObject(this.oldValue);
		out.writeObject(this.newValue);
	}
	
	public String toText () {
		if (this.text == null) {
			String result = null;
			String propertyName = toText(getPropertyName());
			String oldValue = toText(getOldValue());
			String newValue = toText(getNewValue());
			result = "";
			result += propertyName + "_old=" + oldValue;
			result += "\n";
			result += propertyName + "_new=" + newValue;
			this.text = result;
		}
		return this.text;
	}

	@Override
	public void restore(Object target) {
		if (target != null) {
			PropertyUtils.setProperty(target, this.propertyName, this.oldValue);
		}
	}
	
}
