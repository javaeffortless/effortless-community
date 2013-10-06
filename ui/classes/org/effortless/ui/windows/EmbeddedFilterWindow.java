package org.effortless.ui.windows;

import java.util.List;
import java.util.Map;

import org.effortless.model.MapFilter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

public class EmbeddedFilterWindow extends AbstractFieldWindow<MapFilter> {

	public EmbeddedFilterWindow () {
		super();
	}

	protected void initiate () {
		super.initiate();
	}
	
    protected void initiateLayoutSize () {
    	this.layoutSize = Integer.valueOf(4);
    }
    
    protected void initUi () {
    	super.initUi();
    	initUi_Wnd();
    }
    
    protected void initUi_Wnd () {
    	//<window id="wnd" width="100%" height="100%" contentStyle="overflow:auto" border="normal">
    	this.setWidth("100%");
    	this.setHeight("100%");
//    	this.setContentStyle("overflow:auto");
    	this.setContentStyle("overflow:hidden");
    	this.setBorder("none");
    	this.setSclass("embedded-filter-window");
    }
    
    public boolean insertBefore (Component newChild, Component refChild) {
    	boolean result = false;
    	if (this._build) {
    		org.zkoss.zk.ui.HtmlBasedComponent htmlComponent = null;
    		try { htmlComponent = (org.zkoss.zk.ui.HtmlBasedComponent)newChild; } catch (Throwable t) {}
    		String sclass = (htmlComponent != null ? htmlComponent.getSclass() : null);
    		sclass = (sclass != null ? sclass.trim() : "");
    		if (sclass.contains("content")) {
    			List<Component> children = htmlComponent.getChildren();
    			if (children != null) {
    				for (Component child : children) {
    	    			result = this.layoutContent.insertBefore(child, refChild);
    				}
    			}
    		}
    		else {
    			result = this.layoutContent.insertBefore(newChild, refChild);
    		}
    	}
    	else {
    		result = super.insertBefore(newChild, refChild);
    	}
    	return result;
    }

    public void beforeCompose() {
    	super.beforeCompose();
		Execution exec = Executions.getCurrent();
		Map<?, ?> args = (exec != null ? exec.getArg() : null);
		Object value = (args != null ? args.get("value") : null);
		MapFilter mapFilter = null;
		try {
			mapFilter = (MapFilter)value;
		}
		catch (ClassCastException e) {
		}
		if (mapFilter != null) {
			this.setValue(mapFilter);
		}
    }
    
}
