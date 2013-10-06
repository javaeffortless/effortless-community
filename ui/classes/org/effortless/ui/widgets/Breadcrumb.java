package org.effortless.ui.widgets;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;

public class Breadcrumb extends AbstractComponent {

	@Wire
	protected Hlayout wgt;
	
    public Breadcrumb() {
    	super();
    }
    
    protected void initiate () {
    	super.initiate();

    }

    protected static final String SEPARATOR = ">";
    
    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	boolean result = false;
    	if (newChild instanceof BreadcrumbItem) {
    		addListener((BreadcrumbItem)newChild);
    		List<Component> children = this.wgt.getChildren();
    		int size = (children != null ? children.size() : 0);
    		if (size > 0) {
    			Label separator = new Label();
    			separator.setValue(" " + SEPARATOR + " ");
    			this.wgt.appendChild(separator);
    		}
    		result = this.wgt.insertBefore(newChild, refChild);
    	}
    	else {
    		result = super.doInsertBefore(newChild, refChild);
    	}
    	return result;
    }
    
	protected void addListener(BreadcrumbItem newChild) {
		if (newChild != null && false) {
			newChild.addEventListener(Events.ON_CLICK, _doGetOnClickListener());
		}
	}
	
	protected EventListener _onClickListener;
	
	protected EventListener _doGetOnClickListener () {
		if (this._onClickListener == null) {
			final Breadcrumb _this = this;
			this._onClickListener = new EventListener () {

				public void onEvent(Event event) throws Exception {
					BreadcrumbItem item = (BreadcrumbItem)event.getTarget();
					_this._onClickBreadcrumb(item);
				}
				
			};
		}
		return this._onClickListener;
	}
	
	protected void _onClickBreadcrumb (BreadcrumbItem item) {
		if (item != null) {
			Hlayout wgt = this.wgt;
			List<Component> children = wgt.getChildren();
			if (children != null) {
				int idx = 0;
				for (Component child : children) {
					if (item == child) {
						break;
					}
					idx += 1;
				}
				if (idx >= 0) {
					int size = children.size();
					for (int i = size - 1; i > idx; i--) {
						Component child = children.get(i);
						wgt.removeChild(child);
					}
				}
			}
		}
	}
    
}
