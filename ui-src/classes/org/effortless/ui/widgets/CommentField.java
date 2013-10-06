package org.effortless.ui.widgets;

//import org.effortless.core.ObjectUtils;
import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
	 
public class CommentField extends TextField {
	    
    public CommentField() {
    	super();
    }
    
	protected void initiate () {
		super.initiate();
	}

    protected void initUi () {
    	super.initUi();
    	
//    	this.tag.setValue(this.getLabel());
    	
    	this.wgt.setMultiline(true);
    	this.wgt.setRows(4);
    }

}	
