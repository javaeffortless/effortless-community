package org.effortless.security.resources;

import org.effortless.security.Resource;

public class OperationResourceSet extends ScopeResourceSet implements ResourceSet {

	public OperationResourceSet() {
		super();
	}

	public OperationResourceSet(String operation) {
		this();
		setOperations(operation);
	}

	public OperationResourceSet(String operation, String module) {
		this();
		setOperations(operation);
		setModule(module);
	}

	public OperationResourceSet(String operation, String module,
			boolean excludeViews) {
		this();
		setOperations(operation);
		setModule(module);
		setExcludeAllViews(excludeViews);
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
		if (_belongs(resource.getAction(), this.operations)) {
			result = _belongsScope(resource);
		}
		return result;
	}

}
