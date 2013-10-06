package org.effortless.security.resources;

import org.effortless.security.ActionType;
import org.effortless.security.Resource;

public abstract class ScopeResourceSet extends AbstractResourceSet {

	public ScopeResourceSet () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateModule();
		initiateUnit();
		initiateEntity();
		initiateExcludeEntities();
		initiateExcludeAllViews();
		initiateExcludeActions();
	}
	
	protected String excludeActions;

	protected void initiateExcludeActions() {
		this.excludeActions = null;
	}

	public String getExcludeActions() {
		return this.excludeActions;
	}

	public void setExcludeActions(String newValue) {
		this.excludeActions = newValue;
	}
	
	protected boolean excludeAllViews;

	protected void initiateExcludeAllViews() {
		this.excludeAllViews = false;
	}

	public boolean getExcludeAllViews() {
		return this.excludeAllViews;
	}

	public void setExcludeAllViews(boolean newValue) {
		this.excludeAllViews = newValue;
	}
	
	protected String excludeEntities;

	protected void initiateExcludeEntities() {
		this.excludeEntities = null;
	}

	public String getExcludeEntities() {
		return this.excludeEntities;
	}

	public void setExcludeEntities(String newValue) {
		this.excludeEntities = newValue;
	}

	protected String module;

	protected void initiateModule() {
		this.module = null;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String newValue) {
		this.module = newValue;
	}

	protected String unit;

	protected void initiateUnit() {
		this.unit = null;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String newValue) {
		this.unit = newValue;
	}
	
	protected String entity;

	protected void initiateEntity() {
		this.entity = null;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String newValue) {
		this.entity = newValue;
	}
	
	protected boolean _belongsScope(Resource resource) {
		boolean result = false;
		
		String entity = (this.entity != null ? this.entity.trim() : "");
		String unit = (this.unit != null ? this.unit.trim() : "");
		String module = (this.module != null ? this.module.trim() : "");
		
		result = false;
		result = result || entity.equals(resource.getEntity());
		result = result || unit.equals(resource.getUnit());
		result = result || module.equals(resource.getUnit());
		result = result || (module.length() <= 0 && unit.length() <= 0 && entity.length() <= 0);
			
		if (result && this.excludeEntities != null && this.excludeEntities.trim().length() > 0) {
			if (_belongs(resource.getEntity(), this.excludeEntities.trim())) {
				result = false;
			}
		}
		
		if (result && this.excludeActions != null && this.excludeActions.trim().length() > 0) {
			result = !_belongs(resource.getAction(), this.excludeActions);
		}
		
		if (result && this.excludeAllViews) {
			result = !ResourceType.VIEW.equals(resource.getType());
		}
					
		return result;
	}

	
	
}
