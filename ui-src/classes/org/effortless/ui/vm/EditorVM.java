package org.effortless.ui.vm;

import org.effortless.model.Entity;
import org.effortless.ui.ScreenInfo;

public class EditorVM extends ScreenInfo {

	public EditorVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateItem();
		initiateScreenInfo();
		initiateReadonly();
		initiatePersist();
		initiateMode();
	}
	
	protected Entity item;
	
	protected void initiateItem () {
		this.item = null;
	}
	
	public Entity getItem () {
		return this.item;
	}
	
	public void setItem (Entity newValue) {
		this.item = newValue;
	}
	
	protected ScreenInfo screenInfo;
	
	protected void initiateScreenInfo () {
		this.screenInfo = null;
	}
	
	public ScreenInfo getScreenInfo () {
		return this.screenInfo;
	}
	
	public void setScreenInfo (ScreenInfo newValue) {
		this.screenInfo = newValue;
	}

	protected Boolean readonly;
	
	protected void initiateReadonly () {
		this.readonly = null;
	}
	
	public Boolean getReadonly () {
		return this.readonly;
	}
	
	public void setReadonly (Boolean newValue) {
		this.readonly = newValue;
	}

	protected Boolean persist;
	
	protected void initiatePersist () {
		this.persist = null;
	}
	
	public Boolean getPersist () {
		return this.persist;
	}
	
	public void setPersist (Boolean newValue) {
		this.persist = newValue;
	}
	
	protected String mode;
	
	protected void initiateMode () {
		this.mode = null;
	}
	
	public String getMode () {
		return this.mode;
	}
	
	public void setMode (String newValue) {
		this.mode = newValue;
	}
	
    @org.zkoss.bind.annotation.Init
    public void init(@org.zkoss.bind.annotation.ExecutionParam("args") java.util.Map args) {
    	args = (args != null ? args : new java.util.HashMap());
    	
        this.item = (Entity)args.get("item");
        this.screenInfo = (ScreenInfo)args.get("screenInfo");
        this.persist = (Boolean)args.get("persist");
        this.readonly = (Boolean)args.get("readonly");
        this.mode = (String)args.get("mode");
    }
    
	@org.zkoss.bind.annotation.Command
	public void save() {
		if (this.persist != null && this.persist.booleanValue() == true) {
			this.item.persist();
		}
		closeEditor();
	}
	
	@org.zkoss.bind.annotation.Command
	public void cancel() {
		closeEditor();
	}

	@org.zkoss.bind.annotation.Command
	public void close() {
		closeEditor();
	}
	
	protected void closeEditor () {
		java.util.HashMap args = new java.util.HashMap();
		args.put("screenInfo", this.screenInfo);
		org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "closeContent", args);
	}
	
}
