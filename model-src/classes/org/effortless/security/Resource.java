package org.effortless.security;

import org.effortless.security.resources.ResourceType;

public class Resource extends Object {

	public Resource () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateObject();
		initiateName();
		initiateMode();
	}
	
	protected Object object;//Entity, Filter, View, Class
	
	protected void initiateObject () {
		this.object = null;
	}
	
	public Object getObject () {
		return this.object;
	}
	
	public void setObject (Object newValue) {
		this.object = newValue;
	}
	
	protected String name;//action, property
	
	protected void initiateName () {
		this.name = null;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		this.name = newValue;
	}
	
	protected PropertyResourceMode mode;
	
	protected void initiateMode () {
		this.mode = null;
	}
	
	public PropertyResourceMode getMode () {
		return this.mode;
	}
	
	public void setMode (PropertyResourceMode newValue) {
		this.mode = newValue;
	}

	protected ResourceType type;

	protected void initiateType() {
		this.type = null;
	}

	public ResourceType getType() {
		return this.type;
	}

	public void setType(ResourceType newValue) {
		this.type = newValue;
	}
	
	protected Class<?> returnType;

	protected void initiateReturnType() {
		this.returnType = null;
	}

	public Class<?> getReturnType() {
		return this.returnType;
	}

	public void setReturnType(Class<?> newValue) {
		this.returnType = newValue;
	}

	protected Class<?>[] paramTypes;

	protected void initiateParamTypes() {
		this.paramTypes = null;
	}

	public Class<?>[] getParamTypes() {
		return this.paramTypes;
	}

	public void setParamTypes(Class<?>[] newValue) {
		this.paramTypes = newValue;
	}
	
	public SecurityResponse notAllow (AccessSecurityException exception) {
		SecurityResponse result = null;
//		result = new OperationResponse();
//		result.setAllow(false);
//		result.setException(exception);
//		result.setPermission(null);
//		result.setResource(this);
//		result.setSeverity(SecuritySeverity.ERROR);
		return result;
	}

	public String getEntity () {
		String result = null;
		return result;
	}
	
	
	public String getModule () {
		String result = null;
		return result;
	}
	
	public String getUnit () {
		String result = null;
		return result;
	}
	
	public String getAction() {
		String result = null;
		return result;
	}

	public ActionType getActionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkMenuitem() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
