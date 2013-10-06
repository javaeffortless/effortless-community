package org.effortless.model.log;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.effortless.util.CvtrUtils;

public abstract class AbstractLogChangeItem extends Object implements LogChangeItem {

	public AbstractLogChangeItem () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiatePropertyName();
		initiateText();
	}
	
	protected String propertyName;
	
	protected void initiatePropertyName () {
		this.propertyName = null;
	}
	
	public String getPropertyName () {
		return this.propertyName;
	}
	
	public void setPropertyName (String newValue) {
		this.propertyName = newValue;
		this.text = null;
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.propertyName = (String)in.readObject();
		this.text = (String)in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.propertyName);
		out.writeObject(this.text);
	}
	
	public String toString () {
		return toText();
	}
	
	protected String text;
	
	protected void initiateText () {
		this.text = null;
	}
	
	protected String getText () {
		return this.text;
	}
	
	protected void setText (String newValue) {
		this.text = newValue;
	}
	
	public String toText () {
		if (this.text == null) {
			this.text = toText(getPropertyName());
		}
		return this.text;
	}
	
	protected String toText (Object value) {
		String result = null;
		result = CvtrUtils.getInstance().toText(value);
//		result = (value != null ? value.toString() : null);
		result = (result == null ? "(null)" : result);
		return result;
	}

	public abstract void restore(Object target);
	
}
