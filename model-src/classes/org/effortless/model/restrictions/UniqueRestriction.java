package org.effortless.model.restrictions;

import org.effortless.core.FieldModelException;
import org.effortless.core.I18nManager;
import org.effortless.core.ListModelException;
import org.effortless.core.ModelException;
import org.effortless.core.PropertyUtils;
import org.effortless.model.IdEntity;
import org.effortless.model.UniqueRestrictionFilter;

//import java.lang.reflect.InvocationTargetException;
//
//import org.apache.commons.beanutils.PropertyUtils;
//
//import jejbutils.comm.FactoryDAO;
////import jejbutils.data.EntityBean;
//import jejbutils.data.Persistent;
//import jejbutils.filter.FilterManager;
//import jejbutils.filter.SimpleFilter;
//import jutils.app.exceptions.FieldModelException;
//import jutils.app.exceptions.ListModelException;
//import jutils.app.exceptions.ModelException;
//import jutils.app.interfaces.TypeEnum;
//import jutils.i18n.BundleFacade;

public class UniqueRestriction extends Object implements Restriction {

	public UniqueRestriction () {
		super();
	}
	
	protected void initiate () {
		initiatePkName();
		initiateEntityNames();
		initiatePropertyNames();
		initiatePropertyRealNames();
		initiateDeletedName();
		initiateDeletedValue();
	}
	
	protected String propertyNames;
	
	protected void initiatePropertyNames () {
		this.propertyNames = null;
	}
	
	public String getPropertyNames () {
		return this.propertyNames;
	}
	
	public void setPropertyNames (String newValue) {
		this.propertyNames = newValue;
	}
	
	protected String propertyRealNames;
	
	protected void initiatePropertyRealNames () {
		this.propertyRealNames = null;
	}
	
	public String getPropertyRealNames () {
		return this.propertyRealNames;
	}
	
	public void setPropertyRealNames (String newValue) {
		this.propertyRealNames = newValue;
	}
	
	protected String deletedName;
	
	protected void initiateDeletedName () {
		this.deletedName = null;
	}
	
	public String getDeletedName () {
		return this.deletedName;
	}
	
	public void setDeletedName (String newValue) {
		this.deletedName = newValue;
	}
	
	protected Boolean deletedValue;
	
	protected void initiateDeletedValue () {
		this.deletedValue = null;
	}
	
	public Boolean getDeletedValue () {
		return this.deletedValue;
	}
	
	public void setDeletedValue (Boolean newValue) {
		this.deletedValue = newValue;
	}

	protected String pkName;
	
	protected void initiatePkName () {
		this.pkName = null;
	}
	
	public String getPkName () {
		return this.pkName;
	}
	
	public void setPkName (String newValue) {
		this.pkName = newValue;
	}
	
	protected String entityNames;
	
	protected void initiateEntityNames () {
		this.entityNames = null;
	}
	
	public String getEntityNames () {
		return this.entityNames;
	}
	
	public void setEntityNames (String newValue) {
		this.entityNames = newValue;
	}
	
	
	
	public void check (Object value) {
		java.util.List params = new java.util.ArrayList();
		String propertyNames = getPropertyNames();
		propertyNames = (propertyNames != null ? propertyNames.trim() : "");
		String[] arrayPropertyNames = propertyNames.split(",");
		int length = (arrayPropertyNames != null ? arrayPropertyNames.length : 0);
		length = (length > 0 ? length : 0);
		for (int i = 0; i < length; i++) {
			String propertyName = arrayPropertyNames[i];
			Object property = PropertyUtils.getProperty(value, propertyName);
			params.add(property);
		}
		Object[] properties = params.toArray();
//		doCheck(dao, value, properties, arrayPropertyNames);
		
		String propertyRealNames = getPropertyRealNames();
		propertyRealNames = (propertyRealNames != null ? propertyRealNames.trim() : "");
		String[] arrayPropertyRealNames = propertyRealNames.split(",");
		doCheck(value, properties, arrayPropertyRealNames);
	}
	
	protected void throwError (Object data, Object[] properties, String error) {
		String propertyNames = getPropertyNames();
		String m_propertyNames = (propertyNames != null ? propertyNames.replace(",", "_").trim().toLowerCase() + "." : "");
		String key = m_propertyNames + error;

		int length = (properties != null ? properties.length : 0);
		length = (length > 0 ? length : 0);
		Object[] params = new Object[1 + length];
		for (int i = 0; i < length; i++) {
			params[1 + i] = properties[i];
		}
		
		String msg = I18nManager.resolve(key, new Object[] {data, properties});
		
		if (length > 0) {
			String[] arrayProperties = (propertyNames != null ? propertyNames.split(",") : null);
			ListModelException listModelException = new ListModelException();
			for (int i = 0; i < length; i++) {
				String propertyName = arrayProperties[i];
				Object propertyValue = properties[i];
				FieldModelException fieldModelException = new FieldModelException(msg, data, propertyName, propertyValue);
				listModelException.addException(fieldModelException);
			}
			int sizeExceptions = listModelException.sizeExceptions();
			if (sizeExceptions == 1) {
				throw listModelException.getException(0);
			}
			else if (sizeExceptions > 1) {
				throw listModelException;
			}
		}
		else {
			throw new ModelException(msg);
		}
	}
	
	
	public void doCheck (Object data, Object[] properties, String[] propertyNames) {
		if (properties != null && properties.length > 0) {
			long count = count(data, properties, propertyNames);
			if (count > 0) {
				throwError(data, properties, "unique");
			}
		}
	}
	
	// La restricción unique sólo comprueba los valores not null
	protected long count (Object data, Object[] properties, String[] propertyNames) {
		long result = 0L;

		int notnull = 0;
		for (Object property : properties) {
			if (property != null) {
				notnull += 1;
			}
		}

		if (notnull > 0) {
			String entityNames = getEntityNames();
			entityNames = (entityNames != null ? entityNames.trim() : "");
			String[] arrayEntityNames = entityNames.split(",");
			int length = (arrayEntityNames != null ? arrayEntityNames.length : 0);
			length = (length > 0 ? length : 0);
			
			String pkName = getPkName();
			pkName = (pkName != null ? pkName.trim() : "");
			pkName = (pkName.length() > 0 ? pkName : "id");
//			String upperPkName = pkName.substring(0, 1).toUpperCase() + pkName.substring(1);
			
			Object paramDataId = (data != null ? data : "");
			if (paramDataId instanceof IdEntity) {
				paramDataId = ((IdEntity)paramDataId).getId();
			}
			
			String deletedName = getDeletedName();
			deletedName = (deletedName != null ? deletedName.trim() : "");
			Boolean deletedValue = getDeletedValue();
			
			for (String entityName : arrayEntityNames) {
				UniqueRestrictionFilter filter = new UniqueRestrictionFilter(entityName, deletedName, deletedValue, pkName, paramDataId, propertyNames, properties);
				result = filter.size();
				if (result > 0) {
					break;
				}
			}
			
		}
		
		return result;
	}

}
