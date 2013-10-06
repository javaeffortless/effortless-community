package org.effortless.ui.impl;

import org.effortless.ui.widgets.Field;
import org.effortless.ui.windows.EditorWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zul.Listitem;

public class Bindings {

	public static void relocateBinding (Component src, Component target) {
		if (src != null && target != null) {
			org.zkoss.bind.BindComposer bindComposer = (org.zkoss.bind.BindComposer)src.getAttribute(CteUi.BIND_COMPOSER_ID);
			target.setAttribute(CteUi.BIND_COMPOSER_ID, bindComposer);
			
			String vmName = (String)src.getAttribute(CteUi.VM_ID);
			if (org.effortless.core.StringUtils.isEmpty(vmName)) {
				vmName = CteUi.DEFAULT_VM_NAME;
			}
	
			Object vm = src.getAttribute(vmName);
			target.setAttribute(vmName, vm);
			target.setAttribute(CteUi.VM_ID, vmName);
			
			java.util.Map<String, Object> attr = src.getAttributes();
			if (attr != null && attr.containsKey(CteUi.FORM_BINDING)) {
				target.setAttribute(CteUi.FORM_BINDING, attr.get(CteUi.FORM_BINDING));
			}
		}
	}
	
	public static void preCompose (Component comp, Object vm) {
//		Object vm = doGetViewModel();
		if (vm != null) {
			org.zkoss.bind.BindComposer bindComposer = new org.zkoss.bind.BindComposer();
			bindComposer.setViewModel(vm);
			
			try {
				bindComposer.doBeforeComposeChildren(comp);
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
			
			comp.setAttribute(CteUi.BIND_COMPOSER_ID, bindComposer);
			
			String vmName = (String)comp.getAttribute(CteUi.VM_ID);
			if (org.effortless.core.StringUtils.isEmpty(vmName)) {
				vmName = CteUi.DEFAULT_VM_NAME;
				comp.setAttribute(vmName, vm);
				comp.setAttribute(CteUi.VM_ID, vmName);
			}
		}
	}
	
	public static void postCompose (Component comp) {
		org.zkoss.bind.BindComposer bindComposer = (org.zkoss.bind.BindComposer)comp.getAttribute(CteUi.BIND_COMPOSER_ID);
		if (bindComposer != null) {
			try {
				bindComposer.doAfterCompose(comp);
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

	public static void setupFormBinding(Component comp, String formBinding) {
		if (comp != null) {
			comp.setAttribute(CteUi.FORM_BINDING, formBinding);
		}
	}
    

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String doGetFormBindingPropertyThis (Component comp) {
		String result = null;
		result = doGetFormBindingProperty(comp);
		comp.setAttribute(CteUi.FORM_BINDING, result);
		return result;
	}

	public static String doGetFormBindingProperty (Component comp) {
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

	public static void _notifyChange (Component comp, String property) {
		property = _getBindProperty(comp, property);
		if (property != null) {
			org.zkoss.bind.BindComposer composer = doGetBindComposer(comp);
			if (composer != null) {
				String objExpr = doGetFormBindingProperty(comp);
				String expr = org.effortless.core.StringUtils.concat(new String[] {objExpr, property}, ".");
				
				Object vm = composer.getViewModel();
				composer.notifyChange(vm, expr);
			}
		}
	}

	public static void notifyChange (Component comp, Object bean, String property) {
		org.zkoss.bind.BindComposer composer = doGetBindComposer(comp);
		if (composer != null) {
			composer.notifyChange(bean, property);
		}
	}
	
	//Inspect https://github.com/zkoss/zk/blob/master/zkbind/src/org/zkoss/bind/BindComposer.java
	public static void setupBinding (Component comp, String attr, String property) {
		comp.setAttribute(org.effortless.core.StringUtils.concat(new String[] {CteUi.ATTR_BIND_ID, attr}, "_"), property);

		org.zkoss.bind.BindComposer composer = doGetBindComposer(comp);
		if (composer != null) {
			org.zkoss.bind.Binder binder = (composer != null ? composer.getBinder() : null);
			
			String vmExpr = doGetVMName(comp);
			String objExpr = doGetFormBindingProperty(comp);

			String expr = org.effortless.core.StringUtils.concat(new String[] {vmExpr, objExpr, property}, ".");

			boolean readonly = false;
			Field field = null;
			try { field = (Field)comp; } catch (ClassCastException e) {}
			Boolean fieldReadonly = (field != null ? field.getReadonly() : null);
			readonly = (fieldReadonly != null && fieldReadonly.booleanValue());
			readonly = (!readonly && expr != null && expr.indexOf(".") <= -1 ? true : readonly);
			if (!readonly) {
				EditorWindow editorWindow = null;
				Object vm = composer.getViewModel();
				try { editorWindow = (EditorWindow)vm; } catch (ClassCastException e) {}
				Boolean editorReadonly = (editorWindow != null ? editorWindow.getReadonly() : null);
				readonly = (editorReadonly != null && editorReadonly.booleanValue());
			}
			
			
	//BinderImpl.init(Component, Object) first, 
	//then call BinderImpl.addCommandBinding(Component, String, String, java.util.Map), 
	//BinderImpl.addPropertyLoadBindings(Component, String, String, String[], String[], java.util.Map, String, java.util.Map) ...etc to assign the binding.
	//After all the add binding done, you have to call BinderImpl.loadComponent(Component, boolean) to trigger first loading of the binding. 
			
			
			
			
			binder.addPropertyLoadBindings(comp, attr, expr, null, null, null, null, null);
			
			if (!readonly) {
				binder.addPropertySaveBindings(comp, attr, expr, null, null, null, null, null, null, null);
			}
			
			if (false) {
				binder.loadComponent(comp, true);
			}
		}
	}
	
	public static boolean _checkBinded (Component comp, String attr) {
		boolean result = false;
		String binded = _getBindProperty(comp, attr);
		result = (binded != null && binded.length() > 0);
		return result;
	}
	
	public static String _getBindProperty (Component comp, String attr) {
		String result = null;
		result = (String)comp.getAttribute(org.effortless.core.StringUtils.concat(new String[] {CteUi.ATTR_BIND_ID, attr}, "_"));
		return result;
	}
	
	public static boolean _applyBinding (Component comp, String attr, Object value) {
		boolean result = false;
		if (!_checkBinded(comp, attr) && value instanceof String && _checkExprBinding((String)value)) {
			String text = (String)value;
			String property = text.substring(1);
			setupBinding(comp, attr, property);
			result = true;
		}
		return result;
	}
	
	public static boolean _checkExprBinding (String binding) {
		boolean result = false;
		result = (binding != null && binding.startsWith(CteUi.BEGIN_EXPRESSION_BINDING));
		return result;
	}

	public static org.zkoss.bind.BindComposer doGetBindComposer (Component comp) {
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


	public static String doGetVMName (Component comp) {
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
	
}
