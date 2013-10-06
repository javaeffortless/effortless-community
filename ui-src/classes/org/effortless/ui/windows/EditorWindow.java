package org.effortless.ui.windows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.effortless.model.Entity;
import org.effortless.ui.ViewContext;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.layouts.LayoutGrid;
import org.effortless.ui.widgets.AbstractComponent;
import org.effortless.ui.widgets.AbstractField;
import org.effortless.ui.widgets.Field;
import org.zkoss.addon.fluidgrid.Rowchildren;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Window;

public class EditorWindow extends AbstractFieldWindow<Object> {

	public EditorWindow () {
		super();
	}

	protected void initiate () {
		super.initiate();
		
		initiatePersist();
	}
	
    protected void initiateLayoutSize () {
    	this.layoutSize = Integer.valueOf(4);
    }
    
	protected Boolean persist;
	
	protected void initiatePersist () {
		this.readonly = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangePersist)")
	public Boolean getPersist () {
		return this.persist;
	}
	
	public void setPersist (Boolean newValue) {
		_setProperty("persist", "Persist", this.persist, this.persist = newValue);
	}
	
	@Wire
	protected Hlayout toolbar;
	
	@Wire
	protected Button btnSave;
	
	@Wire
	protected Button btnCancel;
	
	@Wire
	protected Button btnClose;
	
	
	
	
	
    @Listen("onClick = #btnSave")
	public void _onSave () {
		Events.echoEvent(CteEvents.ON_EDITOR_SAVE, this, this.value);
//		if (this.persist != null && this.persist.booleanValue() == true && this.value instanceof Entity) {
//			((Entity)this.value).persist();
//		}
//		closeEditor();
    }

    @Listen("onClick = #btnCancel")
	public void _onCancel () {
		Events.echoEvent(CteEvents.ON_EDITOR_CANCEL, this, this.value);
//		closeEditor();
    }
    	
    @Listen("onClick = #btnClose")
	public void _onBack () {
		Events.echoEvent(CteEvents.ON_EDITOR_BACK, this, this.value);
//		closeEditor();
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
    	initUi_BtnSave();
    	initUi_BtnCancel();
    	initUi_BtnClose();
    }
    
    protected void initUi_Wnd () {
    	//<window id="wnd" width="100%" height="100%" contentStyle="overflow:auto" border="normal">
    	this.setWidth("100%");
    	this.setHeight("100%");
    	this.setContentStyle("overflow:auto");
    	this.setBorder("normal");
    	this.setMaximizable(false);
    	this.setMinimizable(false);
    	this.setClosable(false);
    	this.setSclass("editor-window");
    }
    
    protected void initUi_I18n () {
    	super.initUi_I18n();
    	
    	String prefix = (this.i18n != null ? this.i18n + "_" : "");
    	
    	
    	this.setTitle(ViewContext.i18n(prefix + "editorTitle", true, "editorTitle"));//title="${i18n.IconTag_editorTitle}"
		this.btnSave.setLabel(ViewContext.i18n(prefix + "save_editorButtonLabel", true, "save_editorButtonLabel"));//${i18n.IconTag_save_editorButtonLabel}
		this.btnCancel.setLabel(ViewContext.i18n(prefix + "cancel_editorButtonLabel", true, "cancel_editorButtonLabel"));//${i18n.IconTag_cancel_editorButtonLabel}
		this.btnClose.setLabel(ViewContext.i18n(prefix + "close_editorButtonLabel", true, "close_editorButtonLabel"));//${i18n.IconTag_close_editorButtonLabel}
    }
    
	protected void initUi_BtnSave() {
		this.btnSave.setImage(_images("save.png"));//${images}/save.png
		this.btnSave.setVisible(isNonRead());
	}
    
	protected void initUi_BtnCancel() {
		this.btnCancel.setImage(_images("cancel.png"));//${images}/cancel.png
		this.btnCancel.setVisible(isNonRead());
	}
    
	protected void initUi_BtnClose() {
		this.btnClose.setImage(_images("close.png"));//${images}/close.png
		this.btnClose.setVisible(isRead());
	}
	
//	protected void closeEditor () {
//		java.util.HashMap args = new java.util.HashMap();
//		args.put("screenInfo", null);//this.screenInfo);
//		org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "closeContent", args);
//	}
	
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
    		else if (sclass.contains("toolbar")) {
    			refChild = this.btnSave;
    			List<Component> children = htmlComponent.getChildren();
    			if (children != null) {
    				for (Component child : children) {
    	    			result = this.toolbar.insertBefore(child, refChild);
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
		if (value != null) {
			this.setValue(value);
		}
    }

	public Object setAttribute (String name, Object value) {
		Object result = null;
		result = super.setAttribute(name, value);
		if (CteUi.ENTITY_CLASS.equals(name)) {
			_updateTitleFromClass();
		}
		return result;
	}
	
    protected void _updateTitleFromValue() {
    	Object value = this.getValue();
		Class<?> entityClass = (value != null ? value.getClass() : null);
		_updateTitle(entityClass);
	}

    protected void _updateTitleFromClass() {
		Class<?> entityClass = (Class<?>)getAttribute(CteUi.ENTITY_CLASS);
		_updateTitle(entityClass);
		_updateI18nSave(entityClass);
		_updateI18nCancel(entityClass);
		_updateI18nClose(entityClass);
    }

    protected void _updateI18nButtonLabel(Button btn, String suffix, Class<?> entityClass) {
    	if (btn != null && entityClass != null && suffix != null) {
    		String entityName = entityClass.getSimpleName();
    		String value = ViewContext.i18n(entityName + "_" + suffix, true, suffix);
    		value = (value != null ? value.trim() : "");
    		btn.setLabel(value);
    	}
	}
    
    
    protected void _updateI18nSave(Class<?> entityClass) {
    	_updateI18nButtonLabel(this.btnSave, "editorSaveLabel", entityClass);
	}

    protected void _updateI18nCancel(Class<?> entityClass) {
    	_updateI18nButtonLabel(this.btnCancel, "editorCancelLabel", entityClass);
	}

    protected void _updateI18nClose(Class<?> entityClass) {
    	_updateI18nButtonLabel(this.btnClose, "editorCloseLabel", entityClass);
	}

	protected void _updateTitle(Class<?> entityClass) {
    	if (entityClass != null) {
    		String entityName = entityClass.getSimpleName();
//    		String title = _i18n(entityName + "_" + "editorTitle", true, "editorTitle");
    		String title = ViewContext.i18n(entityName + "_" + "editorTitle", false);
    		if (title == null) {
    			String pattern = ViewContext.i18n("editorTitle", false);
    			if (pattern != null) {
        			String entities = ViewContext.i18n(entityName + "_" + "optionLabel", false);
    				title = pattern.replaceAll("\\{0\\}", entities);
    			}
    		}
    		title = (title != null ? title.trim() : "");
    		title = (title.length() > 0 ? title : entityName);
        	this.setTitle(title);
    	}
	}

    protected void _updateValue () {
    	super._updateValue();
    	_updateTitleFromValue();    	
    }
    
}
