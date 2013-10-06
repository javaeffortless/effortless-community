package org.effortless.ui.windows;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

public class InfoWindow<T extends Object> extends AbstractFieldWindow<T> {

	public InfoWindow () {
		super();
	}

	protected void initiate () {
		super.initiate();
	}
	
    public boolean isRead () {
    	return "read".equals(this.ctrlMode);
    }

    public boolean isNonRead () {
    	return !isRead();
    }
    
    protected void initUi () {
    	super.initUi();
    	initUi_Wnd();
    }

    protected void initiateLayoutSize () {
    	this.layoutSize = Integer.valueOf(2);
    }
    
	public void setValue (T newValue) {
		super.setValue(newValue);
	}
    
    
    protected void initUi_Wnd () {
    	//<window id="wnd" width="100%" height="100%" contentStyle="overflow:auto" border="normal">
    	this.setWidth("100%");
    	this.setHeight("100%");
    	this.setContentStyle("overflow:auto");
    	this.setBorder("none");
    	this.setSclass("info-window");
    }
    
//	protected void closeEditor () {
//		java.util.HashMap args = new java.util.HashMap();
//		args.put("screenInfo", null);//this.screenInfo);
//		org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "closeContent", args);
//	}

	@Override
	public void beforeCompose() {
		super.beforeCompose();
		Execution exec = Executions.getCurrent();
		Map<?, ?> args = (exec != null ? exec.getArg() : null);
		T value = (args != null ? (T)args.get("value") : null);
		if (value != null) {
			this.setValue(value);
		}
	}
	
//	protected Object doGetViewModel () {
//		return this;
//	}

//	@Override
//	public void afterCompose() {
//		super.afterCompose();
//	}
	

    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	boolean result = false;

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
   		
    	return result;
    }
	
    
}
