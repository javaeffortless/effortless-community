package org.effortless.ui.widgets;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.impl.InputElement;

public class AbstractInputField<W extends InputElement, T extends Object> extends AbstractField<T> {

	@Wire
	protected W wgt;
	
	public AbstractInputField () {
		super();
	}
	
    protected void initUi () {
    	super.initUi();
    	
//    	this.tag.setValue(this.getLabel());
    	
//    	this.wgt.setValue(this.getValue());

		Boolean readonly = getReadonly();
		readonly = (readonly != null ? readonly : Boolean.FALSE);
		this.wgt.setReadonly(readonly);
		
		Boolean disabled = readonly;
		this.wgt.setDisabled(disabled);
    }

}
