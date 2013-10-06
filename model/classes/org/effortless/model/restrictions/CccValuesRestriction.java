package org.effortless.model.restrictions;

import org.effortless.core.I18nManager;
import org.effortless.core.ModelException;
import org.effortless.core.PropertyUtils;

public class CccValuesRestriction extends AbstractRestriction {

	public CccValuesRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiatePropertyNames();
	}
	
	protected String[] propertyNames;
	
	protected void initiatePropertyNames () {
		this.propertyNames = null;
	}
	
	public String[] getPropertyNames () {
		return this.propertyNames;
	}
	
	public void setPropertyNames (String[] newValue) {
		this.propertyNames = newValue;
	}
	
	public void check (Object value) {
		String[] properties = getPropertyNames();
		int length = (properties != null ? properties.length : 0);
		String ccc = "";
		for (int i = 0; i < length; i++) {
			String propertyName = properties[i];
			String propertyValue = (String)PropertyUtils.getProperty(value, propertyName);
			ccc += (propertyValue != null ? propertyValue : "");
		}
		ccc = CccValidator.removeSomeChars(ccc);
		if (ccc.length() > 0) {
			if (!CccValidator.check(ccc)) {
				String msg = I18nManager.resolve("ccc", new Object[] {value, ccc});
				throw new ModelException(msg);
			}
		}
	}
	
}
