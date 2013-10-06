package org.effortless.ui.widgets;

import org.effortless.core.ObjectUtils;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
	 
public class Tag extends AbstractComponent {
	    
	@Wire
	protected Label wgt;
	 
    public Tag() {
    	super();
//    	initiate();
//    	setupInit();
//    	initUi();
    }
    
//    protected void setupInit () {
//    	String zulName = "~." + File.separator + getClass().getName().replaceAll("\\.", File.separator) + ".zul";
//        Executions.createComponents(zulName, this, null);
//        Selectors.wireComponents(this, this, false);
//        Selectors.wireEventListeners(this, this);
//    }
    
//	protected void notifyChange (String suffix) {
//		suffix = (suffix != null ? suffix.trim() : "");
//		String evtName = "onChange" + suffix;
//		Events.postEvent(evtName, this, null);
//	}
	
	protected void initiate () {
		super.initiate();
		initiateValue();
	}
    
	protected String value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
//	@ComponentAnnotation("@ZKBIND(ACCESS=load, SAVE_EVENT=onChange)")
	public String getValue () {
		return this.value;
	}
	
	public void setValue (String newValue) {
		if (!ObjectUtils.equals(this.value, newValue)) {
			this.value = newValue;
			notifyChange(null);
			initUi();
		}
	}
	
    protected void initUi () {
    	super.initUi();
    	
    	this.wgt.setValue(this.getValue());
    }

}	
