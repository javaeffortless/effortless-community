package org.effortless.core;

public class BaseException extends RuntimeException {

	public BaseException () {
		this(null, null);
	}
	
	public BaseException (String msg) {
		this(msg, null);
	}
	
	public BaseException (Throwable cause) {
		this(null, cause);
	}
	
	public BaseException (String msg, Throwable cause) {
		super(msg, cause);
		initiate();
	}
	
	protected void initiate () {
		initiateSeverity();
		initiateId();
		initiateDescription();
		initiateSolution();
	}
	
	protected SeverityException severity;

	protected void initiateSeverity() {
		this.severity = SeverityException.ERROR;
	}

	public SeverityException getSeverity() {
		return this.severity;
	}

	public void setSeverity(SeverityException newValue) {
		this.severity = newValue;
	}
	
	protected String id;

	protected void initiateId() {
		this.id = null;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String newValue) {
		this.id = newValue;
	}
	
	protected String description;

	protected void initiateDescription() {
		this.description = null;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String newValue) {
		this.description = newValue;
	}
	
	protected String solution;

	protected void initiateSolution() {
		this.solution = null;
	}

	public String getSolution() {
		return this.solution;
	}

	public void setSolution(String newValue) {
		this.solution = newValue;
	}
	
}
