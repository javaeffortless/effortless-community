package org.effortless.ui.windows;

import org.effortless.core.ObjectUtils;
import org.effortless.ui.layouts.LayoutGrid;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;

public class AbstractLayoutWindow extends AbstractWindow {

	@Wire
	protected LayoutGrid layoutContent;
	
	protected void initiate () {
		super.initiate();
    	initiateLayoutSize();
	}

    protected Integer layoutSize;
    
    protected void initiateLayoutSize () {
    	this.layoutSize = Integer.valueOf(12);
    }
    
    public Integer getLayoutSize () {
    	return this.layoutSize;
    }
    
    public void setLayoutSize (Integer newValue) {
    	this.layoutSize = newValue;
    	this.layoutContent.setSize(newValue);
    }
    
    public void setLayoutSize (String newValue) {
		boolean binded = _applyBinding("layoutSize", newValue);
    }
    
    protected String layoutSpans;
    
    protected void initiateLayoutSpans () {
    	this.layoutSpans = null;
    }
    
    public String getLayoutSpans () {
    	return this.layoutSpans;
    }
    
    public void setLayoutSpans (String newValue) {
		boolean binded = _applyBinding("layoutSpans", newValue);
		if (!binded && !ObjectUtils.equals(this.layoutSpans, newValue)) {
			this.layoutSpans = newValue;
			this.layoutContent.setSpans(newValue);
		}
    }
    
    protected void initUi () {
    	super.initUi();
    	if (this.layoutContent != null) {
    		this.layoutContent.setSize(this.layoutSize);
    		this.layoutContent.setSpans(this.layoutSpans);
    	}
    }

    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	return this.layoutContent.insertBefore(newChild, refChild);
    }
    
    
}
