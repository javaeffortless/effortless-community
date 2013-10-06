package org.effortless.model.restrictions;

import org.effortless.core.FieldModelException;
import org.effortless.core.I18nManager;
import org.effortless.core.PropertyUtils;

public abstract class PropertyRestriction extends AbstractRestriction {

	public PropertyRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiatePropertyName();
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
	
	public void check (Object value) {
		Object property = PropertyUtils.getProperty(value, getPropertyName());
		doCheck(value, property);
	}
	
	protected abstract void doCheck (Object data, Object property);
	
	protected void throwError (Object data, Object property, String error) {
		String propertyName = getPropertyName();
//		propertyName = (propertyName != null ? propertyName.trim().toLowerCase() + "." : "");
		String key = (propertyName != null ? propertyName.trim().toLowerCase() + "." : "") + error;
		String msg = I18nManager.resolve(key, new Object[] {data, property});
		throw new FieldModelException(msg, data, propertyName, property);
	}
	
}
