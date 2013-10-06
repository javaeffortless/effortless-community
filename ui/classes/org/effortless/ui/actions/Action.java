package org.effortless.ui.actions;

import java.beans.PropertyChangeEvent;

import org.effortless.core.MethodUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.model.Entity;
import org.effortless.ui.ViewContext;
import org.effortless.ui.impl.Bindings;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;

public class Action<T extends Object> extends AbstractComponent {

	@Wire
	protected Button wgt;
	
	protected void setupInit () {
		super.setupInit();
	}
	
    public Action() {
    	super();
    }
    
    protected String name;
    
    protected void initiateName () {
    	this.name = null;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
    public String getName () {
    	return this.name;
    }
    
    public void setName (String newValue) {
    	String oldValue = this.name;
    	
		boolean binded = _applyBinding("name", newValue);
		if (binded) {
			_updateButton();
		}
		if (!binded && !ObjectUtils.equals(oldValue, newValue)) {
			this.name = newValue;
			notifyChange(null);
			_updateName();
		}
    }
    
    protected void _updateButton() {
    	if (this.wgt != null && this.name != null) {
    		String capName = doGetDefaultLabel();
    		this.wgt.setLabel(capName);
    	}
	}
    
	protected String doGetDefaultLabel () {
		String result = null;
		result = ViewContext.i18n("action." + this.name, true);
		result = (result != null ? StringUtils.capFirst(result) : StringUtils.capFirst(this.name));
		return result;
	}
    

	protected void _updateName() {
		_updateButton();
	}

	@Listen("onClick = #wgt")
	public void _onClick_Wgt () {
		if (this.value == null) {
			this.value = loadDefaultValue();
		}
		if (this.value != null) {
			execute();
		}
		if (this.name != null) {
			Events.postEvent(Events.ON_CLICK, this, this.name);
		}
    }
	
	public void execute() {
		if (this.value != null && this.name != null) {
			Entity entity = null;
			try { entity = (Entity)this.value; } catch (ClassCastException e) {}
			if (true && entity != null) {
				final Action _this = this;
				entity.addPropertyChangeListener(new java.beans.PropertyChangeListener () {

					@Override
					public void propertyChange(java.beans.PropertyChangeEvent event) {
						String propertyName = (event != null ? event.getPropertyName() : null);
						Object bean = (event != null ? event.getSource() : null);
						Bindings.notifyChange(_this, bean, propertyName);
					}
					
				});
			}
			try {
				MethodUtils.run(this.value, this.name);
			}
			catch (Throwable t) {
				if (entity != null) {
					entity.cancelChanges();
				}
				throw new UiException(t);
			}
		}
	}

	public T loadDefaultValue() {
		T result = null;
		PivotAction pivot = null;
		Component cmp = this;
		do {
			cmp = cmp.getParent();
			try { pivot = (PivotAction)cmp; } catch (ClassCastException e) { pivot = null; }
			if (pivot == null) {
				Object obj = cmp.getAttribute(CteUi.PIVOT_ACTION_OBJECT);
				try { result = (T)obj; } catch (ClassCastException e) {}
			}
			result = (pivot != null ? (T)pivot.getPivotAction() : result);
		} while (result == null && pivot == null && cmp != null);
		return result;
	}

	protected T value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public T getValue () {
//		System.out.println("READING VIEW " + this.value);
		return this.value;
	}
	
	public void setValue (T newValue) {
		setValue(newValue, true);
	}

	public void setValue (T newValue, boolean notify) {
		boolean binded = _applyBinding("value", newValue);
		if (!binded && !ObjectUtils.equals(this.value, newValue)) {
			this.value = newValue;
			notifyChange(null);
		}
	}

    protected void initiate () {
    	super.initiate();

        initiateName();
    }

    protected void initUi () {
    	super.initUi();
    }
    
}
