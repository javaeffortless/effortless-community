package org.effortless.security.conditions;

import org.effortless.security.Resource;

public abstract class BaseCustomSecurityCondition extends CustomSecurityCondition {

	public BaseCustomSecurityCondition () {
		super();
	}
	
	public BaseCustomSecurityCondition (String id) {
		super(id);
	}

	protected void initiate () {
		super.initiate();
		initiateConditions();
	}
	
	protected Object[] readParams (Resource resource) {
		Object[] result = null;
		String condition = toCondition(resource);
		if (condition != null && condition.length() > 0) {
			String query = null;
			Object[] params = null;
			result = doReadParams(resource, condition);
			result = new Object[] {query, params};
		}
		else {
			result = new Object[] {null, null};
		}
		return result;
	}

	protected abstract Object[] doReadParams(Resource resource, String condition);

	protected java.util.Map conditions;
	
	protected void initiateConditions () {
		this.conditions = null;
	}
	
	protected java.util.Map getConditions () {
		return this.conditions;
	}
	
	protected void setConditions (java.util.Map newValue) {
		this.conditions = newValue;
	}
	
	protected void buildConditions (java.util.Map map) {
	}
	
	protected String toCondition(Resource resource) {
		String result = null;
		String entityName = resource.getEntity();
		if (entityName != null) {
			if (this.conditions == null) {
				this.conditions = new java.util.HashMap();
				buildConditions(this.conditions);
			}
			if (this.conditions != null) {
				result = (String)this.conditions.get(entityName);
			}
		}
		return result;
	}
	
}
