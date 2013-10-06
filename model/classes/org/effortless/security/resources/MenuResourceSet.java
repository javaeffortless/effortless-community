package org.effortless.security.resources;

import org.effortless.security.Resource;

public class MenuResourceSet extends ScopeResourceSet implements ResourceSet {

	public MenuResourceSet() {
		super();
	}
	
	
	public MenuResourceSet(String module) {
		this();
		setModule(module);
	}

	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		if (ResourceType.VIEW.equals(resource.getType())) {
			boolean checkMenu = resource.checkMenuitem();
			result = (checkMenu && _belongsScope(resource));
		}
		
		return result;
	}
	
}
