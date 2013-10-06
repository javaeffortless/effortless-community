package org.effortless.ui.widgets;

import org.effortless.core.ObjectUtils;
import org.effortless.util.CvtrUtils;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

public class LabelField extends AbstractField<Object> {
    
	@Wire
	protected Label wgt;

	public LabelField() {
		super();
	}

	protected void initUi () {
		super.initUi();
    }
	
	protected String toLabel () {
		String result = null;
    	Object value = this.getValue();
		result = CvtrUtils.getInstance().toString(value);
		return result;
	}
	
//	protected void initUi_Value () {
////    	this.tag.setValue(this.getLabel());
//		String value = toLabel();
//    	this.wgt.setValue(value);
//	}
    
	protected void _refreshValue () {
		String value = toLabel();
    	this.wgt.setValue(value);
	}

	
//	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
//	public Object getValue () {
//		return super.getValue();
//	}
//	
//	public void setValue (Object newValue) {
//		super.setValue(newValue);
//	}
	
}
