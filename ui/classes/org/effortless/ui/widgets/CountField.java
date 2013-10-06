package org.effortless.ui.widgets;

//import org.effortless.core.ObjectUtils;
import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
	 
public class CountField extends AbstractInputField<Intbox, Integer> {
	    
//	@Wire
//	protected Intbox wgt;

//	@Wire
//	protected Tag tag;
	
    public CountField() {
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
//		initiateValue();
//		initiateReadonly();
//		initiateTagValue();
	}

//	protected String tagValue;
//	
//	protected void initiateTagValue () {
//		this.tagValue = null;
//	}
//	
////	@ComponentAnnotation("@ZKBIND(ACCESS=h, SAVE_EVENT=onChange)")
//	public String getTagValue () {
//		return this.tagValue;
//	}
//	
//	public void setTagValue (String newValue) {
//		if (!ObjectUtils.equals(this.tagValue, newValue)) {
//			this.tagValue = newValue;
//			notifyChange(null);
//			initUi();
//		}
//	}
	
	
	
//	protected Integer value;
//	
//	protected void initiateValue () {
//		this.value = null;
//	}
//	
//	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
//	public Integer getValue () {
//		return this.value;
//	}
//	
//	public void setValue (Integer newValue) {
//		if (!ObjectUtils.equals(this.value, newValue)) {
//			this.value = newValue;
//			notifyChange(null);
//			initUi();
//		}
//	}
	
//	protected Boolean readonly;
//	
//	protected void initiateReadonly () {
//		this.readonly = null;
//	}
//	
//	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeReadonly)")
//	public Boolean getReadonly () {
//		return this.readonly;
//	}
//	
//	public void setReadonly (Boolean newValue) {
//		if (!ObjectUtils.equals(this.readonly, newValue)) {
//			this.readonly = newValue;
//			notifyChange("Readonly");
//			initUi();
//		}
//	}
	
    protected void initUi () {
    	super.initUi();
    	
//    	this.tag.setValue(this.getLabel());
    	
    	this.wgt.setValue(this.getValue());

//		Boolean readonly = getReadonly();
//		readonly = (readonly != null ? readonly : Boolean.FALSE);
//		this.wgt.setReadonly(readonly);
//		
//		Boolean disabled = readonly;
//		this.wgt.setDisabled(disabled);
    }

    @Listen("onChange = #wgt")
	public void _onChange () {
    	Integer value = this.wgt.getValue();
    	setValue(value);
	}
    
	protected void _refreshValue () {
    	this.wgt.setValue(this.value);
	}
    
}	
