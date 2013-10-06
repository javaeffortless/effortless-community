package org.effortless.security.conditions;

import java.io.Serializable;

import org.effortless.model.Filter;
import org.effortless.security.Resource;
import org.effortless.security.params.SecurityParam;
import org.effortless.security.resources.ResourceType;

public abstract class SimpleSecurityCondition extends Object implements SecurityCondition, Serializable {

	public SimpleSecurityCondition () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateId();
		initiatePrecondition();
	}
	
	public void transform (Resource resource/*String query, Object[] params*/) {
		if (ResourceType.FILTER.equals(resource.getType())) {
			Filter<?> filter = (Filter<?>)resource.getObject();
			if (filter != null) {
				boolean precondition = readPrecondition();
				if (precondition) {
					transform(filter);
				}
			}
		}
	}
	
	protected abstract void transform(Filter<?> filter);

	protected boolean readPrecondition () {
		boolean result = false;
		
		SecurityParam param = getPrecondition();
		if (param != null) {
			Boolean value = (Boolean)param.getValue();
			result = (value != null && value.booleanValue());
		}
		else {
			result = true;
		}
		return result;
	}

	protected String id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String newValue) {
		this.id = newValue;
	}
	
	protected SecurityParam precondition;
	
	protected void initiatePrecondition () {
		this.precondition = null;
	}
	
	public SecurityParam getPrecondition () {
		return this.precondition;
	}
	
	public void setPrecondition (SecurityParam newValue) {
		this.precondition = newValue;
	}
	
}
