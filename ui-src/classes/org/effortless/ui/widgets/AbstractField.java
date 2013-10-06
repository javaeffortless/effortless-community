package org.effortless.ui.widgets;

import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.ui.ViewContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

public class AbstractField<T extends Object> extends AbstractComponent implements IdSpace, Field<T> {

//	@Wire
	protected Tag tag;
	
    public AbstractField() {
    	super();
//    	initiate();
//    	setupInit();
//		initUi();
    }
    
    protected void initiate () {
    	super.initiate();
    	initiateValue();
    	initiateReadonly();
    	initiateLabel();
    	initiateOrient();
    }
    
//    protected void setupInit () {
//    	String zulName = "~." + File.separator + getClass().getName().replaceAll("\\.", File.separator) + ".zul";
//        Executions.createComponents(zulName, this, null);
//        Selectors.wireComponents(this, this, false);
//        Selectors.wireEventListeners(this, this);
//    }
	
    protected void initUi () {
    	super.initUi();
    	_updateTag();
    }
    
    protected void _updateTag () {
    	if (this.tag != null) {
	    	String label = StringUtils.forceNotNull(getLabel());
	    	label = (label.length() > 0 ? label : doGetDefaultLabel());
	    	label = StringUtils.forceNotNull(label);
	    	label = (label.length() > 0 ? label + ":" : label);
	    	this.tag.setValue(label);
    	}
    }
    
//	protected void notifyChange (String suffix) {
//		suffix = (suffix != null ? suffix.trim() : "");
//		String evtName = "onChange" + suffix;
//		Events.postEvent(evtName, this, null);
//	}
	
	
//	protected boolean _setProperty (String propertyName, String suffix, Object oldValue, Object newValue) {
//		boolean result = false;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
////			this.label = newValue;
//			notifyChange(suffix);
//			initUi();
//			result = true;
//		}
//		return result;
//	}
	
	
	public static final String ORIENT_HORIZONTAL = "horizontal";
	public static final String ORIENT_VERTICAL = "vertical";
	public static final String ORIENT_DEFAULT = ORIENT_HORIZONTAL;
	
	protected String orient;
	
	protected void initiateOrient () {
		this.orient = ORIENT_DEFAULT;
	}
	
	//"horizontal" or "vertical".
	public String getOrient () {
		return this.orient;
	}
	
	protected String fixOrient (String orient) {
		String result = null;
		result = (result == null && ORIENT_HORIZONTAL.equals(orient) ? ORIENT_HORIZONTAL : result);
		result = (result == null && ORIENT_VERTICAL.equals(orient) ? ORIENT_VERTICAL : result);
		result = (result == null ? ORIENT_DEFAULT : result);
		return result;
	}
	
	public void setOrient (String newValue) {
		newValue = fixOrient(newValue);
		_setProperty("orient", "Orient", this.orient, newValue);
	}
	
	public boolean isHorizontal () {
		return ORIENT_HORIZONTAL.equals(this.orient);
	}
	
	public boolean isVertical () {
		return ORIENT_VERTICAL.equals(this.orient);
	}
	
	protected String label;
	
	protected void initiateLabel () {
		this.label = null;
	}
	
//	@ComponentAnnotation("@ZKBIND(ACCESS=h, SAVE_EVENT=onChange)")
	public String getLabel () {
		return this.label;
	}
	
	public void setLabel (String newValue) {
		_setProperty("label", "Label", this.label, this.label = newValue);
		boolean binded = _applyBinding("label", newValue);
		if (!binded && !ObjectUtils.equals(this.label, newValue)) {
			this.label = newValue;
			notifyChange(null);
			initUi();
		}
//		String oldValue = this.label;
//		if (!ObjectUtils.equals(this.label, newValue)) {
//			this.label = newValue;
//			notifyChange(null);
//			initUi();
//		}
	}
	
	
	
	protected T value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public T getValue () {
//		System.out.println("READING VIEW " + this.value);
		return this.value;
	}
	
	public void setValue (T newValue) {
		setValue(newValue, true);
	}

	public void setValue (T newValue, boolean notify) {
//		_setProperty("value", null, this.value, this.value = newValue);
//		if (!ObjectUtils.equals(this.value, newValue)) {
//			this.value = newValue;
//			notifyChange(null);
//			initUi();
//		}
		boolean binded = _applyBinding("value", newValue);
		if (binded) {
			_updateTag();
//			_updateReadonlyListWidget();
		}
		if (!binded && !ObjectUtils.equals(this.value, newValue)) {
//			System.out.println("WRITING VIEW " + newValue);
			this.value = newValue;
//			_notifyChange("value");
			notifyChange(null);
			_refreshValue();
			if (notify) {
				initUi_Value();
				initUi();
			}
		}
	}
	
	
	
	protected void _refreshValue () {
	}
	
	protected void initUi_Value () {
	}
	
	protected Boolean readonly;
	
	protected void initiateReadonly () {
		this.readonly = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeReadonly)")
	public Boolean getReadonly () {
		return this.readonly;
	}
	
	public void setReadonly (Boolean newValue) {
		_setProperty("readonly", "Readonly", this.readonly, this.readonly = newValue);
//		if (!ObjectUtils.equals(this.readonly, newValue)) {
//			this.readonly = newValue;
//			notifyChange("Readonly");
//			initUi();
//		}
	}

//	public Component getColumnWidget () {
//		Label result = null;
//		result = new Label();
//		result.setSclass("field-column-widget");
//		result.setValue(this.tag.getValue());
//		return result;
//	}
	
	public Component getColumnWidget () {
		Label result = null;
		result = new Label();
		result.setSclass("field-column-widget");
		String label = doGetDefaultLabel();
		result.setValue(label);
		return result;
	}
	
	protected String doGetDefaultLabel () {
		String result = null;
		String property = _getBindProperty("value");
		property = (property != null ? StringUtils.uncapFirst(property.trim()) : "");
//		result = (property != null ? StringUtils.capFirst(property.trim()) : property);
		result = (property != null ? ViewContext.i18n("property." + property, false) : property);
		result = (result != null ? StringUtils.capFirst(result) : StringUtils.capFirst(property));
		return result;
	}

	public Component getTagWidget () {
		Tag result = null;
		if (this.tag == null) {
			result = new Tag();
			this.tag = result;
		}
		else {
			result = this.tag;
		}
		_updateTag();
		return result;
//		return this.tag;
	}

//	protected LabelField readonlyListWidget;
//	
//	public Component getReadonlyListWidget () {
//		Component result = null;
//		if (true) {
//			this.readonlyListWidget = (this.readonlyListWidget != null ? this.readonlyListWidget : new LabelField());
//			result = this.readonlyListWidget;
//			_updateReadonlyListWidget();
//		}
//		else {
//			this.setReadonly(Boolean.TRUE);
//			result = this;
//		}
//		return result;
////		return this.tag;
//	}
//
//	protected void _updateReadonlyListWidget() {
//		if (this.readonlyListWidget != null) {
//			String property = _getBindProperty("value");
//			if (property != null) {
//				this.readonlyListWidget.setValue("$" + property);
//			}
//		}
//	}
	
	
	
}
