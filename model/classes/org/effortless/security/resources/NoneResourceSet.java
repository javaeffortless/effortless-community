package org.effortless.security.resources;

import org.effortless.security.Resource;

public class NoneResourceSet extends AbstractResourceSet implements ResourceSet {

	public NoneResourceSet () {
		super();
	}
	
	@Override
	public boolean contains(Resource resource) {
		return false;
	}

}
