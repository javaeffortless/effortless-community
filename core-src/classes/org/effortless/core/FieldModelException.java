package org.effortless.core;

public class FieldModelException extends ModelException {

//	public FieldModelException(String msg, Object data, String propertyName,
//			Object propertyValue) {
//		// TODO Auto-generated constructor stub
//	}
	
	public FieldModelException () {
		super();
	}
	
	public FieldModelException (String msg) {
		super(msg);
	}
	
	public FieldModelException (Throwable cause) {
		super(cause);
	}
	
	public FieldModelException (String msg, Throwable cause) {
		super(msg, cause);
	}

	public FieldModelException (String msg, Object data, String propertyName, Object propertyValue) {
		super(msg);
		setupFieldProperties(data, propertyName, propertyValue);
	}
	
	public FieldModelException (Throwable cause, Object data, String propertyName, Object propertyValue) {
		super(cause);
		setupFieldProperties(data, propertyName, propertyValue);
	}
	
	public FieldModelException (String msg, Throwable cause, Object data, String propertyName, Object propertyValue) {
		super(msg, cause);
		setupFieldProperties(data, propertyName, propertyValue);
	}
	
	protected void setupFieldProperties (Object data, String propertyName, Object propertyValue) {
		setData(data);
		setPropertyName(propertyName);
		setPropertyValue(propertyValue);
	}

	protected void initiate () {
		super.initiate();
		initiateData();
		initiatePropertyName();
		initiatePropertyValue();
	}
	
	protected Object data;
	
	protected void initiateData () {
		this.data = null;
	}
	
	public Object getData () {
		return this.data;
	}
	
	public void setData (Object newValue) {
		this.data = newValue;
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
	}
	
	protected Object propertyValue;
	
	protected void initiatePropertyValue () {
		this.propertyValue = null;
	}
	
	public Object getPropertyValue () {
		return this.propertyValue;
	}
	
	public void setPropertyValue (Object newValue) {
		this.propertyValue = newValue;
	}

}
