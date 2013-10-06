package org.effortless.core;

import java.util.ArrayList;
import java.util.List;

public class ListModelException extends ModelException {

	public void addException(FieldModelException fieldModelException) {
		// TODO Auto-generated method stub
		
	}

	public ListModelException () {
		super();
	}
	
	public ListModelException (String msg) {
		super(msg);
	}
	
	public ListModelException (Throwable cause) {
		super(cause);
	}
	
	public ListModelException (String msg, Throwable cause) {
		super(msg, cause);
	}

	protected void initiate () {
		super.initiate();
		initiateExceptions();
	}
	
	protected List exceptions;
	
	protected void initiateExceptions () {
		this.exceptions = null;
	}
	
	public List getExceptions () {
		return this.exceptions;
	}
	
	public void setExceptions (List newValue) {
		this.exceptions = newValue;
	}
	
	public void addException (ModelException e) {
		if (e != null) {
			if (this.exceptions == null) {
				this.exceptions = new ArrayList();
			}
			this.exceptions.add(e);
		}
	}
	
	public int sizeExceptions () {
		return (this.exceptions != null ? this.exceptions.size() : 0);
	}

	public ModelException getException (int index) {
		return (ModelException)(this.exceptions.get(index));
	}
	
	public String getMessage () {
		String result = null;
		result = super.getMessage();
		if (result == null || "".equals(result.trim())) {
			result = "";
			int length = (this.exceptions != null ? this.exceptions.size() : 0);
			for (int i = 0; i < length; i++) {
				Exception e = (Exception)this.exceptions.get(i);
				String msg = (e != null ? e.getMessage() : null);
				msg = (msg != null ? msg.trim() : "");
				result += (result.length() > 0 && msg.length() > 0 ? "\n" : "") + msg;
			}
			result = (result.length() > 0 ? result : null);
		}
		return result;
	}
	
	
}
