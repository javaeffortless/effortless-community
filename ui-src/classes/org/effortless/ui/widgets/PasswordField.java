package org.effortless.ui.widgets;

//import org.effortless.core.ObjectUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.helper.PasswordFieldEditExtension;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
	 
public class PasswordField extends TextField {
	    
    public PasswordField() {
    	super();
    }
    
	protected void initiate () {
		super.initiate();
		initiateType();
		this.extension = null;
		
		this._onChange = null;
		this._onClick = null;
	}
	
    protected void initUi () {
    	super.initUi();
    	initUi_Type();
    }

    protected void initUi_Type () {
    	this.wgt.setType("password");
    	_updateType();
    }

    protected String type;
    
	protected void initiateType () {
		this.type = CteUi.PASSWORD_TYPE_EDIT;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getType () {
		return this.type;
	}
	
	public void setType (String newValue) {
		boolean binded = _applyBinding("type", newValue);
		if (!binded && !ObjectUtils.equals(this.type, newValue)) {
			this.type = newValue;
			notifyChange(null);
			_updateType();
		}
	}

	protected void _updateType() {
		if (CteUi.PASSWORD_TYPE_EDIT.equals(this.type)) {
			this.wgt.addEventListener(Events.ON_CLICK, _doGetOnClick());
			this.wgt.addEventListener(Events.ON_CHANGE, _doGetOnChange());
		}
		else {
			if (this._onClick != null) {
				this.wgt.removeEventListener(Events.ON_CLICK, this._onClick);
				this._onClick = null;
			}
			if (this._onChange != null) {
				this.wgt.removeEventListener(Events.ON_CHANGE, this._onChange);
				this._onChange = null;
			}
			this.extension = null;
		}
	}
	
	protected void _buildExtension () {
		if (this.extension == null) {
			this.extension = new PasswordFieldEditExtension();
			this.extension.link(this);
			this.appendChild(this.extension);
		}
	}
	
	protected EventListener _onChange;
	
	protected EventListener _doGetOnChange() {
		if (this._onChange == null) {
			final PasswordField _this = this;
			this._onChange = new EventListener () {

				public void onEvent(Event event) throws Exception {
					_this._onChangePassword();
				}
				
			};
		}
		return this._onChange;
	}

	protected void _onChangePassword() {
		_buildExtension();
		this.extension.startConfirm();
	}

	protected EventListener _onClick;

	protected EventListener _doGetOnClick() {
		final PasswordField _this = this;
		this._onClick = new EventListener () {

			public void onEvent(Event event) throws Exception {
				_this._onClickPassword();
			}
			
		};
		return this._onClick;
	}

	protected void _onClickPassword() {
		_buildExtension();
		this.extension.startPopup();
	}

	protected PasswordFieldEditExtension extension;

	public boolean isHide() {
		boolean result = false;
		String type = this.wgt.getType();
    	result = "password".equals(type);
    	return result;
	}

	public void show() {
		this.wgt.setType("text");
	}

	public void hide() {
		this.wgt.setType("password");
	}
	
}	
