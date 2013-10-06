package org.effortless.ui.windows;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.effortless.core.I18nProperties;
import org.effortless.core.ObjectUtils;
import org.effortless.server.ServerContext;
import org.effortless.server.WebUtils;
import org.effortless.ui.impl.Bindings;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.layouts.LayoutGrid;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.ext.BeforeCompose;
import org.zkoss.zk.ui.ext.DynamicPropertied;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.ForEachStatus;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Window;

public class AbstractWindow extends Window implements IdSpace, DynamicPropertied, BeforeCompose, AfterCompose {

    public AbstractWindow() {
    	super();
    	initiate();
    	setupInit();
		initUi();
    }
    
    protected boolean _build;
    
    protected void initiate () {
    	this._build = false;
    	initiateI18n();
    	this.properties = null;
    }
    
	protected String i18n;
	
	protected void initiateI18n () {
		this.i18n = null;
	}
	
	public String getI18n () {
		return this.i18n;
	}
	
	public void setI18n (String newValue) {
		boolean change = this._setProperty("i18n", "I18n", this.i18n, this.i18n = newValue);
		if (change && this.i18n != null) {
			initUi_I18n();
		}
	}
	
    protected void initUi_I18n () {
    }
    
    protected void setupInit () {
    	String zulName = "~." + File.separator + getClass().getName().replaceAll("\\.", File.separator) + CteUi.EXT_ZUL;
        Executions.createComponents(zulName, this, null);
        Selectors.wireComponents(this, this, false);
        Selectors.wireEventListeners(this, this);
    	this._build = true;
    	_endBuild();
    }
	
    protected void _endBuild() {
	}

	protected void initUi () {
    }
    
	protected void notifyChange (String suffix) {
		suffix = (suffix != null ? suffix.trim() : "");
		String evtName = "onChange" + suffix;
		Events.postEvent(evtName, this, null);
	}
	
	protected boolean _setProperty (String propertyName, String suffix, Object oldValue, Object newValue) {
		boolean result = false;
		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.label = newValue;
			notifyChange(suffix);
			initUi();
			result = true;
		}
		return result;
	}
	
//	protected I18nProperties _i18n;
//
//	protected void _loadI18n () {
//    	if (this._i18n == null) {
//	    	Desktop desktop = Executions.getCurrent().getDesktop();
//			String appName = desktop.getRequestPath();
//	
//			if (appName != null) {
//				String appId = WebUtils.appId(appName, ServerContext.getRootContext());
//				this._i18n = I18nProperties.dev(appId);
//			}
//    	}
//	}
//	
//    protected String _i18n(String key) {
//    	String result = null;
//
//    	_loadI18n();
//    	String prefix = (this.i18n != null ? this.i18n + "_" : "");
//    	String nkey = prefix + key;
//    	result = (String)this._i18n.get(nkey);
//    	
//    	return result;
//    }
//    
//    protected String _i18n(String key, boolean save, String defaultKey) {
//    	String result = null;
//    	_loadI18n();
////    	String nkey = this.i18n + "_" + key;
//    	boolean firstSave = (defaultKey == null);
//    	result = (String)this._i18n.getKey(key, firstSave);
//    	if (result == null && defaultKey != null) {
//        	result = (String)this._i18n.getKey(defaultKey, save);
//    	}
//    	
//    	return result;
//    }
//
//    protected String _i18n(String key, boolean save) {
//    	String result = null;
//    	_loadI18n();
////    	String nkey = this.i18n + "_" + key;
//    	result = (String)this._i18n.getKey(key, save);
//    	return result;
//    }

    
    
    protected String imagesFolder;
    
    protected String _images (String key) {
    	String result = null;
    	
    	if (this.imagesFolder == null) {
        	Desktop desktop = Executions.getCurrent().getDesktop();
    		String appName = desktop.getRequestPath();
			String appId = WebUtils.appId(appName, ServerContext.getRootContext());
			this.imagesFolder = File.separator + appId + File.separator + "resources" + File.separator + "img" + File.separator;
    	}
		result = this.imagesFolder + key;
    	
    	return result;
    }

    protected String resourcesFolder;
    
    protected String _res (String key) {
    	String result = null;
    	
    	if (this.resourcesFolder == null) {
        	Desktop desktop = Executions.getCurrent().getDesktop();
    		String appName = desktop.getRequestPath();
			String appId = WebUtils.appId(appName, ServerContext.getRootContext());
			this.resourcesFolder = File.separator + appId + File.separator + "resources" + File.separator;
    	}
		result = this.resourcesFolder + key;
    	
    	return result;
    }

    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	return nativeInsertBefore(newChild, refChild);
    }
    
    public boolean insertBefore (Component newChild, Component refChild) {
    	boolean result = false;
    	if (this._build) {
    		result = doInsertBefore(newChild, refChild);
    	}
    	else {
    		result = nativeInsertBefore(newChild, refChild);
    	}
    	return result;
    }
    
    protected boolean nativeInsertBefore (Component newChild, Component refChild) {
		return super.insertBefore(newChild, refChild);
    }

    protected Map<String, Object> properties;
    
	public boolean hasDynamicProperty(String name) {
		return (this.properties != null ? this.properties.containsKey(name) : false);
	}

	public Object getDynamicProperty(String name) {
		return (this.properties != null ? this.properties.get(name) : null);
	}

	public void setDynamicProperty(String name, Object value)
			throws WrongValueException {
		this.properties = (this.properties != null ? this.properties : new HashMap<String, Object>());
		this.properties.put(name, value);
	}

	@Override
	public void beforeCompose() {
		preCompose();
	}
	
	protected Object doGetViewModel () {
		return this;
	}

	@Override
	public void afterCompose() {
//		preCompose();
		postCompose();
	}
	
	protected void preCompose () {
		Object vm = doGetViewModel();
		if (true) {
		Bindings.preCompose(this, vm);
		}
		else {
		if (vm != null) {
			org.zkoss.bind.BindComposer bindComposer = new org.zkoss.bind.BindComposer();
			bindComposer.setViewModel(vm);
			
			try {
				bindComposer.doBeforeComposeChildren(this);
			} catch (Exception e) {
				try {
					bindComposer.doCatch(e);
				} catch (Exception e1) {
					throw new UiException(e1);
				}
				try {
					bindComposer.doFinally();
				} catch (Exception e1) {
					throw new UiException(e1);
				}
			}

//			bindComposer.setViewModel(vm);
			
			this.setAttribute(CteUi.BIND_COMPOSER_ID, bindComposer);
			
			String vmName = (String)this.getAttribute(CteUi.VM_ID);
			if (org.effortless.core.StringUtils.isEmpty(vmName)) {
				vmName = CteUi.DEFAULT_VM_NAME;
				this.setAttribute(vmName, vm);
				this.setAttribute(CteUi.VM_ID, vmName);
			}
		}
		}
	}
	
	protected void postCompose () {
		if (true) {
		Bindings.postCompose(this);
		}
		else {
		org.zkoss.bind.BindComposer bindComposer = (org.zkoss.bind.BindComposer)this.getAttribute(CteUi.BIND_COMPOSER_ID);
		if (bindComposer != null) {
			try {
				bindComposer.doAfterCompose(this);
			} catch (Exception e) {
				try {
					bindComposer.doCatch(e);
				} catch (Exception e1) {
					throw new UiException(e1);
				}
				try {
					bindComposer.doFinally();
				} catch (Exception e1) {
					throw new UiException(e1);
				}
			}
		}
		}
	}

	
	
	protected String doGetFormBindingProperty () {
		return Bindings.doGetFormBindingPropertyThis(this);
	}

	protected String doGetFormBindingProperty (Component comp) {
		return Bindings.doGetFormBindingProperty(comp);
	}

	protected void _notifyChange (Component comp, String property) {
		Bindings._notifyChange(comp, property);
	}
	
	protected void _notifyChange (String property) {
		Bindings._notifyChange(this, property);
	}
	
	//Inspect https://github.com/zkoss/zk/blob/master/zkbind/src/org/zkoss/bind/BindComposer.java
	protected void setupBinding (Component comp, String attr, String property) {
		Bindings.setupBinding(comp, attr, property);
	}
	
	protected boolean _checkBinded (String attr) {
		return Bindings._checkBinded(this, attr);
	}
	
	protected String _getBindProperty (String attr) {
		return Bindings._getBindProperty(this, attr);
	}
	
	protected boolean _applyBinding (String attr, Object value) {
		return Bindings._applyBinding(this, attr, value);
	}
	
	protected boolean _checkExprBinding (String binding) {
		return Bindings._checkExprBinding(binding);
	}

	protected org.zkoss.bind.BindComposer doGetBindComposer () {
		return Bindings.doGetBindComposer(this);
	}

	protected org.zkoss.bind.BindComposer doGetBindComposer (Component comp) {
		return Bindings.doGetBindComposer(comp);
	}

	protected String doGetVMName () {
		return Bindings.doGetVMName(this);
	}

	protected String doGetVMName (Component comp) {
		return Bindings.doGetVMName(comp);
	}

    protected Component[] _buildComponents (final Template tm, final Object data, final int index, final int size, Component parent, Component insertBefore) {
    	Component[] result = null;
		if (tm != null) {
			final VariableResolver variableResolver = new VariableResolver() {
				public Object resolveVariable(String name) {
					if ("item".equals(name)) {
						return data;
					} 
					else if ("forEachStatus".equals(name)) {
						return new ForEachStatus() {

							public ForEachStatus getPrevious() { return null; }

							public Object getEach() { return data; }

							public int getIndex() { return index; }

							public Integer getBegin() { return Integer.valueOf(0); }

							public Integer getEnd() { return Integer.valueOf(size); }
							
						};
					} 
					else {
						return null;
					}
				}
			};
			result = tm.create(parent, insertBefore, variableResolver, null);
		}
		return result;
    }

    public void close () {
    	this.detach();
    }
    
}
