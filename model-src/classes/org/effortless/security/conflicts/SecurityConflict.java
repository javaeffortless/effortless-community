package org.effortless.security.conflicts;

import java.util.List;

import org.effortless.security.Resource;
import org.effortless.security.SecurityResponse;

public interface SecurityConflict {

	public SecurityResponse resolve (List<SecurityResponse> responses);

	public boolean check(Resource resource);

}
