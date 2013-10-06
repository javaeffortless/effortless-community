package org.effortless.ui;

public class Message extends Object {

	public Message () {
		super();
		initiate();
	}
	
	public static Message createDelete () {
		Message result = null;
		result = new Message();
		result.setTitle("Confirmación");
		result.setMessage("Are you sure to remove?");
		result.setDetails("(Eliminar)");
		result.setType("delete");
		return result;
	}
	
	public static Message createInfo () {
		Message result = null;
		result = new Message();
		result.setTitle("Confirmación");
		result.setMessage("Are you sure to execute?");
		result.setDetails("(Eliminar)");
		result.setType("info");
		return result;
	}
	
	public String getIcon () {
		return "msg-" + this.type;
	}
	
	protected void initiate () {
		initiateTitle();
		initiateMessage();
		initiateDetails();
		initiateType();
		initiateProcessOk();
		initiateProcessCancel();
	}
	
	protected String title;
	
	protected void initiateTitle () {
		this.title = null;
	}
	
	public String getTitle () {
		return this.title;
	}
	
	public void setTitle (String newValue) {
		this.title = newValue;
	}
	
	protected String message;
	
	protected void initiateMessage () {
		this.message = null;
	}
	
	public String getMessage () {
		return this.message;
	}
	
	public void setMessage (String newValue) {
		this.message = newValue;
	}
	
	protected String details;
	
	protected void initiateDetails () {
		this.details = null;
	}
	
	public String getDetails () {
		return this.details;
	}
	
	public void setDetails (String newValue) {
		this.details = newValue;
	}
	
	protected String type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	public String getType () {
		return this.type;
	}
	
	public void setType (String newValue) {
		this.type = newValue;
	}
	
	protected MsgProcess processOk;
	
	protected void initiateProcessOk () {
		this.processOk = null;
	}
	
	public MsgProcess getProcessOk () {
		return this.processOk;
	}
	
	public void setProcessOk (MsgProcess newValue) {
		this.processOk = newValue;
	}
	
	protected MsgProcess processCancel;
	
	protected void initiateProcessCancel () {
		this.processCancel = null;
	}
	
	public MsgProcess getProcessCancel () {
		return this.processCancel;
	}
	
	public void setProcessCancel (MsgProcess newValue) {
		this.processCancel = newValue;
	}
	
}
