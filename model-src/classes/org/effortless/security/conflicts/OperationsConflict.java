package org.effortless.security.conflicts;

import org.effortless.security.Resource;
import org.effortless.security.resources.ResourceType;

public class OperationsConflict extends BaseAllowConflict {

	public OperationsConflict () {
		super();
	}
	
	public OperationsConflict (String[] names, String module) {
		this();
		setNames(names);
		setModule(module);
	}
	
	public OperationsConflict (String[] names, String module, String entity) {
		this(names, module);
		setEntity(entity);
	}
	
	protected void initiate () {
		super.initiate();
		initiateNames();
		initiateModule();
	}
	
	protected String[] names;
	
	protected void initiateNames () {
		this.names = null;
	}
	
	public String[] getNames () {
		return this.names;
	}
	
	public void setNames (String[] newValue) {
		this.names = newValue;
	}
	
	protected String module;
	
	protected void initiateModule () {
		this.module = null;
	}
	
	public String getModule () {
		return this.module;
	}
	
	public void setModule (String newValue) {
		this.module = newValue;
	}
	
	protected String entity;
	
	protected void initiateEntity () {
		this.entity = null;
	}
	
	public String getEntity () {
		return this.entity;
	}
	
	public void setEntity (String newValue) {
		this.entity = newValue;
	}
	
	public boolean check(Resource resource) {
		boolean result = false;
		ResourceType type = resource.getType();
		if (ResourceType.ACTION.equals(type)) {
			String entityResource = resource.getEntity();
			String moduleResource = resource.getModule();
			String moduleConflict = getModule();
			String entityConflict = getEntity();
			boolean flag = false;
			flag = flag || (moduleConflict == null);
			flag = flag || (moduleConflict != null && moduleConflict.equals(moduleResource));
			flag = flag && (entityConflict == null || (entityConflict != null && entityConflict.equals(entityResource)));
			if (flag) {
				String operation = resource.getAction();
				if (operation != null) {
					String[] names = getNames();
					int length = (names != null ? names.length : 0);
					for (int i = 0; i < length; i++) {
						if (operation.equals(names[i])) {
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}

}
