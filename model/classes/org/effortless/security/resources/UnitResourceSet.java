package org.effortless.security.resources;

import org.effortless.security.Resource;

public class UnitResourceSet extends ScopeResourceSet implements ResourceSet {

	public UnitResourceSet () {
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
		result = _belongsScope(resource);
		if (result && this.operations != null && this.operations.trim().length() > 0 && ResourceType.ACTION.equals(resource.getType())) {
			result = _belongs(resource.getAction(), this.operations);
		}
		return result;
	}
	
	
	
}
