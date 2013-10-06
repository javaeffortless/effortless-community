package org.effortless.security.resources;

import org.effortless.security.Resource;

public class ResourceResourceSet extends AbstractResourceSet implements ResourceSet {

	public ResourceResourceSet () {
		super();
	}

	public ResourceResourceSet (ResourceSet resourceSet) {
		this();
		setResourceSet(resourceSet);
	}

	protected ResourceSet resourceSet;

	protected void initiateResourceSet() {
		this.resourceSet = null;
	}

	public ResourceSet getResourceSet() {
		return this.resourceSet;
	}

	public void setResourceSet(ResourceSet newValue) {
		this.resourceSet = newValue;
	}
	
	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		if (this.resourceSet != null && resource != null) {
			result = this.resourceSet.contains(resource);
		}
		return result;
	}
	
}
