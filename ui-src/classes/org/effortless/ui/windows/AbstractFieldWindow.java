package org.effortless.ui.windows;

import org.effortless.core.ObjectUtils;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.AbstractField;
import org.effortless.ui.widgets.Field;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zul.Label;

public class AbstractFieldWindow<T extends Object> extends AbstractLayoutWindow implements Field<T>, FieldWindow {

	public AbstractFieldWindow () {
		super();
	}

	protected void initiate () {
		super.initiate();
		setAttribute(CteUi.FORM_BINDING, "value");
		initiateOrient();
		initiateLabel();
		initiateValue();
		initiateReadonly();
		initiateCtrlMode();
		
	}

//	protected String _doGetObjExpr () {
//		return "value";
//	}

	protected String orient;
	
	protected void initiateOrient () {
		this.orient = AbstractField.ORIENT_DEFAULT;
	}
	
	//"horizontal" or "vertical".
	public String getOrient () {
		return this.orient;
	}
	
	protected String fixOrient (String orient) {
		String result = null;
		result = (result == null && AbstractField.ORIENT_HORIZONTAL.equals(orient) ? AbstractField.ORIENT_HORIZONTAL : result);
		result = (result == null && AbstractField.ORIENT_VERTICAL.equals(orient) ? AbstractField.ORIENT_VERTICAL : result);
		result = (result == null ? AbstractField.ORIENT_DEFAULT : result);
		return result;
	}
	
	public void setOrient (String newValue) {
		newValue = fixOrient(newValue);
		_setProperty("orient", "Orient", this.orient, newValue);
	}
	
	public boolean isHorizontal () {
		return AbstractField.ORIENT_HORIZONTAL.equals(this.orient);
	}
	
	public boolean isVertical () {
		return AbstractField.ORIENT_VERTICAL.equals(this.orient);
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
	}
	
	
	
	protected T value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public T getValue () {
		return this.value;
	}
	
	public void setValue (T newValue) {
//		_setProperty("value", null, this.value, this.value = newValue);
		boolean binded = _applyBinding("value", newValue);
		if (!binded && !ObjectUtils.equals(this.value, newValue)) {
			this.value = newValue;
			notifyChange(null);
			initUi_Value();
			_updateValue();
		}
	}

	protected void _updateValue() {
	}

	public void setValue (String newValue) {
		boolean binded = _applyBinding("value", newValue);
		_updateValue(newValue);
		initUi_Value();
	}
	
	
	protected void _updateValue(String newValue) {
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
	}
	
	protected String ctrlMode;
	
	protected void initiateCtrlMode () {
		this.ctrlMode = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeCtrlMode)")
	public String getCtrlMode () {
		return this.ctrlMode;
	}
	
	public void setCtrlMode (String newValue) {
		_setProperty("ctrlMode", "CtrlMode", this.ctrlMode, this.ctrlMode = newValue);
	}

	public Component getColumnWidget () {
		Label result = null;
		result = new Label();
		result.setSclass("field-column-widget");
		result.setValue(this.getLabel());
		return result;
	}

	@Override
	public Component getTagWidget() {
		return null;
	}

//	@Override
//	public Component getReadonlyListWidget() {
//		return null;
//	}

}
