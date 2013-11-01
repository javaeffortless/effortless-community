package org.effortless.ui.widgets;

import org.effortless.core.ObjectUtils;
import org.effortless.ui.UiApplication;
import org.effortless.ui.windows.InfoWindow;
import org.effortless.ui.windows.Windows;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Popup;
	 
public class IconField<T extends Object> extends AbstractField<T> {
	    
	@Wire
	protected Div wgt;
	
//	@Wire
	protected Popup wMoreInfo;
	
//	@Wire
	protected InfoWindow wWindow;

    public IconField() {
    	super();
    }
    
	protected void initiate () {
		super.initiate();
//		initiateValue();
//		initiateReadonly();
//		initiateTagValue();
		this._onOpenListener = null;
	}

	protected void initUi_Value () {
		if (this.wWindow != null) {
			this.wWindow.setValue(this.getValue());
		}
	}
	
	
	
	public InfoWindow getWindow () {
		return this.wWindow;
	}
	
	public void setWindow (InfoWindow newValue) {
		if (this.wMoreInfo != null) {
			Components.removeAllChildren(this.wMoreInfo);
		}
		this.wWindow = newValue;
		initUi_Value();
		if (this.wWindow != null) {
			this.wMoreInfo.appendChild(this.wWindow);
		}
	}

	protected String infoSrc;
	
	protected void initiateInfoSrc () {
		this.infoSrc = null;
	}
	
	public String getInfoSrc () {
		return this.infoSrc;
	}
	
	public void setInfoSrc (String newValue) {
		String oldValue = this.infoSrc;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.infoSrc = newValue;
			_updateInfoSrc();
		}
	}
	
	
//    @Listen("onOpen = #wMoreInfo")
	public void _onOpenMoreInfo () {
		setupDefaultInfoSrc();
    	if (this.value != null && this.infoSrc != null && this.wWindow == null) {
			InfoWindow infoWindow = (InfoWindow)Windows.openInfo(this.value, this.infoSrc);
//			infoWindow.setValue(this.value);
			setWindow(infoWindow);
			if (this._onOpenListener != null) {
				this.wMoreInfo.removeEventListener(Events.ON_OPEN, this._onOpenListener);
				this._onOpenListener = null;
			}
    	}
	}
    
	
	
    protected void setupDefaultInfoSrc() {
    	if (this.infoSrc == null && this.value != null) {
    		Class clazz = this.value.getClass();
    		this.infoSrc = UiApplication.getMoreInfoZul(clazz);
    	}
    }

	protected void _updateInfoSrc() {
	}

	protected void initUi () {
    	super.initUi();
    	initTooltip();
//    	initUi_Value();
////    	this.tag.setValue(this.getLabel());
//    	
//    	this.wgt.setValue(this.getValue());
//
//		Boolean readonly = getReadonly();
//		readonly = (readonly != null ? readonly : Boolean.FALSE);
////		this.wgt.setReadonly(readonly);
////		
////		Boolean disabled = readonly;
////		this.wgt.setDisabled(disabled);
//		
//		this.wgt.setButtonVisible(!readonly.booleanValue());
    }
    
	protected EventListener _onOpenListener;
	
	protected EventListener doGetOnOpenListener () {
		if (this._onOpenListener == null) {
			final IconField _this = this;
			this._onOpenListener = new EventListener() {

				public void onEvent(Event event) throws Exception {
					_this._onOpenMoreInfo();
				}
				
			};
		}
		return this._onOpenListener;
	}
	
	protected void initTooltip() {
		this.wMoreInfo = (Popup)this.getFirstChild().getNextSibling();
		if (this.wMoreInfo != null) {
			this.wMoreInfo.addEventListener(Events.ON_OPEN, doGetOnOpenListener());
			String tooltipId = this.wMoreInfo.getUuid();
			this.wMoreInfo.setId(tooltipId);
			String tooltipCmd = tooltipId + ", position=before_start, delay=500";
			this.wgt.setTooltip(tooltipCmd);
		}
	}

//    protected boolean doInsertBefore (Component newChild, Component refChild) {
//		return this.wWindow.insertBefore(newChild, refChild);
//    }

	public T getValue () {
		return super.getValue();
	}
	
	public void setValue (T newValue) {
		super.setValue(newValue);
	}

	public Boolean getReadonly () {
		return Boolean.TRUE;
	}
	
	public void setReadonly (Boolean newValue) {
	}
	
	
}	
