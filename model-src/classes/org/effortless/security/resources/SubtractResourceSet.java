package org.effortless.security.resources;

import org.effortless.security.Resource;

public class SubtractResourceSet extends ListResourceSet<SubtractResourceSet> implements ResourceSet {

	public SubtractResourceSet () {
		super();
	}
	
	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		int size = (this.items != null ? this.items.size() : 0);
		if (resource != null && size > 1) {
			for (int i = 0; i < (size - 1); i++) {
				ResourceSet rs = this.items.get(i);
				if (rs.contains(resource)) {
					result = true;
					break;
				}
			}
			result = (result && !(this.items.get(size - 1).contains(resource)));
		}
		
		return result;
	}

}
