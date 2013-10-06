package org.effortless.core;

public class ViewException extends BaseException {

	public ViewException () {
		super();
	}
	
	public ViewException (String msg) {
		super(msg);
	}
	
	public ViewException (Throwable cause) {
		super(cause);
	}
	
	public ViewException (String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
