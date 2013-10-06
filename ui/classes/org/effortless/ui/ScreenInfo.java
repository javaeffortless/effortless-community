package org.effortless.ui;

import java.util.Map;

public class ScreenInfo extends Object {

	public ScreenInfo () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateArgs();
		initiateSrc();
//		initiateId();
	}
	
	protected Map<String, Object> args;
	
	protected void initiateArgs () {
		this.args = null;
	}
	
	public Map<String, Object> getArgs () {
		return this.args;
	}
	
	public void setArgs (Map<String, Object> newValue) {
		this.args = newValue;
	}
	
	protected String src;
	
	protected void initiateSrc () {
		this.src = null;
	}
	
	public String getSrc () {
		return this.src;
	}
	
	public void setSrc (String newValue) {
		this.src = newValue;
	}
	
	public String getSrcTimestamp () {
		return (this.src != null ? this.src + "?tms=" + System.currentTimeMillis() : this.src);
	}
	
//	protected String id;
//	
//	protected void initiateId () {
//		this.id = null;
//	}
//	
//	public String getId () {
//		if (this.id == null) {
//			this.id = loadId();
//		}
//		return this.id;
//	}
//	
//	public void setId (String newValue) {
//		this.id = newValue;
//	}
//	
//	protected String loadId() {
//		String result = null;
//		String timeId = String.valueOf(System.currentTimeMillis());
//		String srcId = (this.src != null ? String.valueOf(this.src.hashCode()) : "");
//		result = timeId + srcId;
//		return result;
//	}
	
	public boolean equals (Object o) {
		boolean result = false;
		result = (o == this);
		return result;
	}

}
