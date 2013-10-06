package org.effortless.security.resources;

import org.effortless.security.Resource;

public class ModuleResourceSet extends ScopeResourceSet implements ResourceSet {

	public ModuleResourceSet() {
		super();
	}
	
	public ModuleResourceSet(String module, String excludeActions) {
		this();
		setModule(module);
		setExcludeActions(excludeActions);
	}

	public boolean contains(Resource resource) {
		boolean result = false;
		result = _belongsScope(resource);
		return result;
	}
	
}
