package org.effortless.core;

public class ModelException extends BaseException {

	public ModelException () {
		super();
	}
	
	public ModelException (String msg) {
		super(msg);
	}
	
	public ModelException (Throwable cause) {
		super(cause);
	}
	
	public ModelException (String msg, Throwable cause) {
		super(msg, cause);
	}

}
