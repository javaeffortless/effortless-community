package org.effortless.ui.layouts;

import org.effortless.core.ObjectUtils;

import org.effortless.ui.widgets.AbstractComponent;
import org.effortless.ui.widgets.Field;
import org.zkoss.addon.fluidgrid.Rowchildren;
import org.zkoss.addon.fluidgrid.Rowlayout;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
	 
public class LayoutGrid extends AbstractComponent {
	    
	@Wire
	protected Rowlayout wgt;
	 
    public LayoutGrid() {
    	super();
    }
    
	protected void initiate () {
		super.initiate();
		
		initiateSize();
		initiateSpans();
	}
	
	protected Integer size;
	
	protected void initiateSize () {
		this.size = Integer.valueOf(12);
	}
	
	public Integer getSize () {
		return this.size;
	}
	
	public void setSize (Integer newValue) {
		if (!ObjectUtils.equals(this.size, newValue)) {
			this.size = newValue;
			notifyChange(null);
			initUi();
		}
	}
	
	protected String spans;
	
	protected void initiateSpans () {
		this.spans = null;
	}
	
	public String getSpans () {
		return this.spans;
	}
	
	public void setSpans (String newValue) {
		if (!ObjectUtils.equals(this.spans, newValue)) {
			this.spans = newValue;
			notifyChange(null);
			initUi();
		}
	}
	
    protected void initUi () {
    	super.initUi();
    	
    	int ncols = (this.size != null ? this.size.intValue() : -1);
    	ncols = (ncols > -1 ? ncols : 12);
    	this.wgt.setNcols(ncols);
    	
//    	this.wgt.setSpacing("1/3");
    	this.wgt.setSpacing("0");
    	
    	_updateSpans();
    }
    
    protected void _updateSpans () {
    	if (this.spans != null && this.spans.trim().length() > 0) {
    		String[] spans = this.spans.trim().split(",");
    		java.util.List<Component> children = this.wgt.getChildren();
    		int childrenSize = (children != null ? children.size() : 0);
    		int min = Math.min(childrenSize, spans.length);
    		for (int i = 0; i < min; i++) {
    			Integer colspan = Integer.valueOf(1);
    			try {
    				colspan = Integer.valueOf(spans[i]);
    			}
    			catch (Throwable t) {
    			}
    			Rowchildren rowchildren = (Rowchildren) children.get(i);
    			if (rowchildren != null) {
    				rowchildren.setColspan(colspan.intValue());
    			}
    		}
    		for (int i = min; i < childrenSize; i++) {
    			Integer colspan = Integer.valueOf(1);
    			Rowchildren rowchildren = (Rowchildren) children.get(i);
    			if (rowchildren != null) {
    				rowchildren.setColspan(colspan.intValue());
    			}
    		}
    	}
    }

    public boolean insertBefore (Component newChild, Component refChild) {
//    	  <rowchildren colspan="3" offset="2">
//    	  </rowchildren>
    	boolean result = false;
    	if (this._build) {
	    	Component[] components = _applyTransform(newChild);
	    	
	    	if (components == null || components.length <= 1) {
		    	Rowchildren rowChildren = new Rowchildren();
		    	//    	rowChildren.setColspan(1);
		    	//    	rowChildren.setOffset(0);
		    	    	
    	    	rowChildren.insertBefore(newChild, null);
		    	    	  
    	    	result = this.wgt.insertBefore(rowChildren, refChild);
	    	}
	    	else {
	    		for (Component comp : components) {
			    	Rowchildren rowChildren = new Rowchildren();
			    	//    	rowChildren.setColspan(1);
			    	//    	rowChildren.setOffset(0);
			    	    	
	    	    	rowChildren.insertBefore(comp, null);
			    	    	  
	    	    	result = this.wgt.insertBefore(rowChildren, refChild);
	    		}
	    	}
    	}
    	else {
        	result = super.insertBefore(newChild, refChild);
    	}
    	return result;
    }
    
    protected Component[] _applyTransform(Component newChild) {
    	Component[] result = null;
    	if (newChild != null) {
    		Field field = null;
    		try {
    			field = (Field)newChild;
    		}
    		catch (ClassCastException e) {
    			
    		}
    		if (field != null) {
    			Component tag = field.getTagWidget();
    			if (tag != null) {
    				result = new Component[] {tag, field};
    			}
    		}
    		else {
    			result = new Component[] {newChild};
    		}
			result = (result != null ? result : new Component[] {newChild});
    	}
		return result;
	}

	protected void processChild (Component newChild, Component refChild) {
    	
    }
    
}	
