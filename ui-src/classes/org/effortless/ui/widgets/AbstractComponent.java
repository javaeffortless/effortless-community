package org.effortless.ui.widgets;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.effortless.core.I18nProperties;
import org.effortless.core.ObjectUtils;
import org.effortless.server.ServerContext;
import org.effortless.server.WebUtils;
import org.effortless.ui.Relocatable;
import org.effortless.ui.Relocator;
import org.effortless.ui.impl.Bindings;
import org.effortless.ui.impl.CteUi;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.ext.DynamicPropertied;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Div;

public class AbstractComponent extends /*org.zkoss.zul.Idspace*/Div implements IdSpace, DynamicPropertied, Relocatable, AfterCompose {

    public AbstractComponent() {
    	super();
    	initiate();
    	setupInit();
		initUi();
		this._build = true;
    }
    
    protected boolean _build;
    
    protected void initiate () {
    	this._build = false;
    	this.properties = null;
    	initiatePosition();
    	initiateRelocator();
    }
    
    protected void setupInit () {
    	String zulName = "~." + File.separator + getClass().getName().replaceAll("\\.", File.separator) + CteUi.EXT_ZUL;
        Executions.createComponents(zulName, this, null);
        Selectors.wireComponents(this, this, false);
        Selectors.wireEventListeners(this, this);
    }
	
    protected void initUi () {
    }
    
	protected void notifyChange (String suffix) {
		suffix = (suffix != null ? suffix.trim() : "");
		String evtName = "onChange" + suffix;
		Events.postEvent(evtName, this, null);
//		Events.echoEvent(evtName, this, null);
	}
	
	protected boolean _setProperty (String propertyName, String suffix, Object oldValue, Object newValue) {
		boolean result = false;
		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.label = newValue;
			notifyChange(suffix);
			_updateProperty(propertyName, oldValue, newValue);
			initUi();
			result = true;
		}
		return result;
	}

	protected void _updateProperty(String propertyName, Object oldValue,
			Object newValue) {
		// TODO Auto-generated method stub
		
	}

	protected void applyRelocatable (Component newChild) {
    	if (newChild instanceof Relocatable) {
    		Relocatable relocatable = (Relocatable)newChild;
    		Relocator relocator = getRelocator();
    		relocatable.setRelocator(relocator);
    		Integer position = getPosition();
    		if (relocator != null) {
    			Integer nextPosition = (position != null ? Integer.valueOf(position.intValue() + 1) : Integer.valueOf(1));
    			relocatable.setPosition(nextPosition);
    			relocator.relocate(newChild, nextPosition);
    		}
    	}
    	else {
    		if (this.relocator != null) {
    			Integer nextPosition = (position != null ? Integer.valueOf(position.intValue() + 1) : Integer.valueOf(1));
    			this.relocator.relocate(newChild, nextPosition);
    		}
    	}
	}
	
    public boolean insertBefore (Component newChild, Component refChild) {
    	boolean result = false;
    	if (this._build) {
    		result = doInsertBefore(newChild, refChild);
    		applyRelocatable(newChild);
    	}
    	else {
    		result = nativeInsertBefore(newChild, refChild);
    	}
    	return result;
    }
    
    protected boolean nativeInsertBefore (Component newChild, Component refChild) {
		return super.insertBefore(newChild, refChild);
    }
	
    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	return nativeInsertBefore(newChild, refChild);
    }

    
//	protected I18nProperties _i18n;
//
//    protected String _i18n(String key, Object[] params) {
//    	String result = null;
//    	_loadI18n();
//    	result = this._i18n.resolve(key, params);
//    	return result;
//    }
//	
//    protected I18nProperties _loadI18n () {
//    	if (this._i18n == null) {
//	    	Desktop desktop = Executions.getCurrent().getDesktop();
//			String appName = desktop.getRequestPath();
//	
//			if (appName != null) {
//				String appId = WebUtils.appId(appName, ServerContext.getRootContext());
//				this._i18n = I18nProperties.dev(appId);
//			}
//    	}
//    	return this._i18n;
//    }
//	
//    protected String _i18n(String key) {
//    	String result = null;
//
//    	_loadI18n();
////    	String nkey = this.i18n + "_" + key;
//    	result = (String)this._i18n.get(key);
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

	protected Integer position;
	
	protected void initiatePosition () {
		this.position = null;
	}
	
	public Integer getPosition () {
		return this.position;
	}
	
	public void setPosition (Integer newValue) {
		Integer oldValue = this.position;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.position = newValue;
			_onChangePosition();
		}
	}
	
	protected void _onChangePosition() {
		// TODO Auto-generated method stub
		
	}

	protected Relocator relocator;
	
	protected void initiateRelocator () {
		this.relocator = null;
	}
	
	public Relocator getRelocator() {
		return this.relocator;
	}

	public void setRelocator(Relocator newValue) {
		Relocator oldValue = this.relocator;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.relocator = newValue;
			_onChangeRelocator();
		}
	}

	protected void _onChangeRelocator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompose() {
	}
	
//	protected String _doGetObjExpr () {
//		return "value";
//	}

	
/*
	public String doGetFormBindingProperty () {
		String result = null;
		result = doGetFormBindingProperty(this);
		this.setAttribute(CteUi.FORM_BINDING, result);
		return result;
	}

	protected String doGetFormBindingProperty (Component comp) {
		String result = null;
		
		if (comp != null) {
			try {
				result = (String)comp.getAttribute(CteUi.FORM_BINDING);
			}
			catch (ClassCastException e) {
			}
			result = (result != null ? result : doGetFormBindingProperty(comp.getParent()));
		}
		
		return result;
	}

	protected void _notifyChange (Component comp, String property) {
		property = _getBindProperty(property);
		if (property != null) {
			org.zkoss.bind.BindComposer composer = doGetBindComposer(comp);
			if (composer != null) {
				String objExpr = doGetFormBindingProperty();
				String expr = org.effortless.core.StringUtils.concat(new String[] {objExpr, property}, ".");
				
				Object vm = composer.getViewModel();
				composer.notifyChange(vm, expr);
			}
		}
	}
	
	protected void _notifyChange (String property) {
		_notifyChange(this, property);
	}
	
	//Inspect https://github.com/zkoss/zk/blob/master/zkbind/src/org/zkoss/bind/BindComposer.java
	protected void setupBinding (Component comp, String attr, String property) {
		this.setAttribute(org.effortless.core.StringUtils.concat(new String[] {CteUi.ATTR_BIND_ID, attr}, "_"), property);

		org.zkoss.bind.BindComposer composer = doGetBindComposer(comp);
		if (composer != null) {
			org.zkoss.bind.Binder binder = (composer != null ? composer.getBinder() : null);
			
			String vmExpr = doGetVMName(comp);
			String objExpr = doGetFormBindingProperty();
			
			boolean readonly = false;
			
	//BinderImpl.init(Component, Object) first, 
	//then call BinderImpl.addCommandBinding(Component, String, String, java.util.Map), 
	//BinderImpl.addPropertyLoadBindings(Component, String, String, String[], String[], java.util.Map, String, java.util.Map) ...etc to assign the binding.
	//After all the add binding done, you have to call BinderImpl.loadComponent(Component, boolean) to trigger first loading of the binding. 
			
			
			
			String expr = org.effortless.core.StringUtils.concat(new String[] {vmExpr, objExpr, property}, ".");
			
			binder.addPropertyLoadBindings(comp, attr, expr, null, null, null, null, null);
			
			if (!readonly) {
				binder.addPropertySaveBindings(comp, attr, expr, null, null, null, null, null, null, null);
			}
			
			if (false) {
				binder.loadComponent(this, true);
			}
		}
	}
	
	protected boolean _checkBinded (String attr) {
		boolean result = false;
		String binded = _getBindProperty(attr);
		result = (binded != null && binded.length() > 0);
		return result;
	}
	
	protected String _getBindProperty (String attr) {
		String result = null;
		result = (String)this.getAttribute(org.effortless.core.StringUtils.concat(new String[] {CteUi.ATTR_BIND_ID, attr}, "_"));
		return result;
	}
	
	protected boolean _applyBinding (String attr, Object value) {
		boolean result = false;
		if (!_checkBinded(attr) && value instanceof String && _checkExprBinding((String)value)) {
			String text = (String)value;
			setupBinding(this, attr, text.substring(1));
			result = true;
		}
		return result;
	}
	
	protected boolean _checkExprBinding (String binding) {
		boolean result = false;
		result = (binding != null && binding.startsWith(CteUi.BEGIN_EXPRESSION_BINDING));
		return result;
	}

	protected org.zkoss.bind.BindComposer doGetBindComposer () {
		return doGetBindComposer(this);
	}

	protected org.zkoss.bind.BindComposer doGetBindComposer (Component comp) {
		org.zkoss.bind.BindComposer result = null;
		
		if (comp != null) {
			try {
				result = (org.zkoss.bind.BindComposer)comp.getAttribute(CteUi.BIND_COMPOSER_ID);
			}
			catch (ClassCastException e) {
			}
			result = (result != null ? result : doGetBindComposer(comp.getParent()));
		}
		
		return result;
	}


	protected String doGetVMName () {
		return doGetVMName(this);
	}

	protected String doGetVMName (Component comp) {
		String result = null;
		
		if (comp != null) {
			try {
				result = (String)comp.getAttribute(CteUi.VM_ID);
			}
			catch (ClassCastException e) {
			}
			result = (result != null ? result : doGetVMName(comp.getParent()));
		}
		
		return result;
	}
*/

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

}
