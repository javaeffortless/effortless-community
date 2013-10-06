package org.effortless.security.params;

public class CurrentUserSecurityParam extends SimpleSecurityParam {

	public CurrentUserSecurityParam () {
		super();
	}
	
	public CurrentUserSecurityParam (String id) {
		super(id);
	}
	
	public Object getValue() {
		Object result = null;
		result = super.getCurrentUser();
		return result;
	}

}
