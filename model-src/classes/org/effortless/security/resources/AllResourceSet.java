package org.effortless.security.resources;

import org.effortless.security.Resource;

public class AllResourceSet extends AbstractResourceSet implements ResourceSet {

	public AllResourceSet () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public boolean contains (Resource resource) {
		return (resource != null);
	}
	
}
