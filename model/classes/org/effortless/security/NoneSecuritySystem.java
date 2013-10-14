package org.effortless.security;

public class NoneSecuritySystem extends Object implements SecuritySystem {

	public NoneSecuritySystem() {
		super();
		initiate();
	}

	protected void initiate() {
	}
	
	protected static NoneSecuritySystem instance;
	
	public static NoneSecuritySystem getInstance () {
		if (instance == null) {
			instance = new NoneSecuritySystem();
		}
		return instance;
	}

	public SecurityResponse check (Resource resource) {
		SecurityResponse result = null;
		if (resource != null) {
			result = sendResponse(resource);
			if (result == null) {
				SecurityResponse response = new SecurityResponse();
				response.setAllow(true);
				result = response;
			}
		}
		return result;
	}
	
	protected SecurityResponse sendResponse(Resource resource) {
		SecurityResponse result = null;
		result = new SecurityResponse();
		result.setAllow(true);
		result.setResource(resource);
		return result;
	}

	@Override
	public Object login(String loginName, String loginPassword) {
		return loginName;
	}

}
