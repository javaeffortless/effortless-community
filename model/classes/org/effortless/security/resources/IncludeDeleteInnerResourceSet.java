package org.effortless.security.resources;

import org.effortless.security.Resource;

public class IncludeDeleteInnerResourceSet extends ScopeResourceSet implements ResourceSet {

	public IncludeDeleteInnerResourceSet(String module) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		if (ResourceType.ACTION.equals(resource.getType())) {
			String action = resource.getAction();
			action = (action != null ? action.trim().toLowerCase() : "");
			if ("delete".equals(action)) {
				result = _belongsScope(resource);
				result = (result && _checkInner(resource));
			}
		}
		
		return result;
	}

	protected boolean _checkInner (Resource resource) {
		boolean result = false;
		Class<?> clazz = resource.getEntityClass();
		result = (clazz != null && clazz.getEnclosingClass() != null);
		return result;
	}
	
}
