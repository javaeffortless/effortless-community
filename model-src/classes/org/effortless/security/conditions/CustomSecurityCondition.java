package org.effortless.security.conditions;

import java.io.Serializable;

import org.effortless.model.Filter;
import org.effortless.security.Resource;
import org.effortless.security.resources.ResourceType;

public abstract class CustomSecurityCondition extends Object implements SecurityCondition, Serializable {

	public CustomSecurityCondition () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateId();
	}
	
	public CustomSecurityCondition (String id) {
		this();
		setId(id);
	}
	
	public void transform (Resource resource/*String query, Object[] params*/) {
		if (ResourceType.FILTER.equals(resource.getType())) {
			Filter<?> filter = (Filter<?>)resource.getObject();
			if (filter != null) {
				transform(filter);
			}
		}
	}
	
	protected abstract void transform(Filter<?> filter);

	protected String id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	public String getId () {
		return this.id;
	}
	
	protected void setId (String newValue) {
		this.id = newValue;
	}
	
}
