package org.effortless.security.conflicts;

import java.util.List;

import org.effortless.security.Resource;
import org.effortless.security.SecurityResponse;

public abstract class BaseAllowConflict extends Object implements SecurityConflict {

	public BaseAllowConflict () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public abstract boolean check(Resource resource);

	public SecurityResponse resolve(List<SecurityResponse> responses) {
		SecurityResponse result = null;
		int length = (responses != null ? responses.size() : 0);
		for (int i = 0; i < length; i++) {
			SecurityResponse response = (SecurityResponse)responses.get(i);
			if (response != null && response.isAllow()) {
				result = response;
				break;
			}
		}
		return result;
	}

}
