package org.effortless.security.resources;

import org.effortless.security.ActionType;
import org.effortless.security.Resource;

public class ReadOperationsResourceSet extends ScopeResourceSet implements ResourceSet {

	public ReadOperationsResourceSet(String module) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		if (ResourceType.ACTION.equals(resource.getType())) {
			result = _belongsScope(resource);
			if (result && ActionType.PROCEDURE.equals(resource.getActionType())) {
				result = false;
			}
		}

		return result;
	}

}
