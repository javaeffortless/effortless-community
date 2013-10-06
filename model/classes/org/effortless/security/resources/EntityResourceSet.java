package org.effortless.security.resources;

import org.effortless.security.Resource;

public class EntityResourceSet extends ScopeResourceSet implements ResourceSet {

	public EntityResourceSet () {
		super();
	}

	protected String operations;

	protected void initiateOperations() {
		this.operations = null;
	}

	public String getOperations() {
		return this.operations;
	}

	public void setOperations(String newValue) {
		this.operations = newValue;
	}
	
	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		String entity = (this.entity != null ? this.entity.trim() : "");
		String resEntity = resource.getEntity();
		if (entity.equals(resEntity)) {
			result = super._belongsScope(resource);
			if (result && this.operations != null && this.operations.trim().length() > 0) {
				result = _belongs(resource.getAction(), this.operations);
			}
		}
		
		return result;
	}
	
}
