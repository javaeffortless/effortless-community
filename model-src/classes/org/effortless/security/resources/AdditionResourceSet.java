package org.effortless.security.resources;

import org.effortless.security.Resource;

public class AdditionResourceSet extends ListResourceSet<AdditionResourceSet> implements ResourceSet {

	public AdditionResourceSet () {
		super();
	}
	
	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		int size = (this.items != null ? this.items.size() : 0);
		if (resource != null && size > 0) {
			result = false;
			for (int i = 0; i < (size - 1); i++) {
				ResourceSet rs = this.items.get(i);
				if (rs.contains(resource)) {
					result = true;
					break;
				}
			}
		}
		
		return result;
	}


}
