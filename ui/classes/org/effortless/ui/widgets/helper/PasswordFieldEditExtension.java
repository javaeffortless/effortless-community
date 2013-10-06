package org.effortless.ui.widgets.helper;

import org.effortless.core.PasswordGenerator;
import org.effortless.ui.Message;
import org.effortless.ui.MsgProcess;
import org.effortless.ui.ViewContext;
import org.effortless.ui.actions.Action;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.AbstractComponent;
import org.effortless.ui.widgets.PasswordField;
import org.effortless.ui.windows.MsgWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

public class PasswordFieldEditExtension extends AbstractComponent {

	public PasswordFieldEditExtension () {
		super();
	}

	public void startPopup () {
		Boolean openPopup = (Boolean)this.wPopup.getAttribute(CteUi.OPEN_POPUP);
		if (openPopup == null || openPopup.booleanValue() == false) {
			this.wPopup.open(this.field, doGetPopupPosition());
			this.wPopup.setAttribute(CteUi.OPEN_POPUP, Boolean.TRUE);
		}
		else {
			this.wPopup.removeAttribute(CteUi.OPEN_POPUP);
			this.wPopup.close();
		}
	}
	
	protected String doGetPopupPosition () {
		return "overlap_after";
	}
	
	public void link (PasswordField field) {
		this.field = field;
	}
	
	public void startConfirm () {
		openConfirm();
	}
	
	protected void openConfirm () {
		String value = this.field.getValue();
		
		this.wConfirm.setVisible(true);
		this.wConfirm.doHighlighted();
		this.vConfirm1.setValue(value);
		this.vConfirm2.setValue("");
		if (value == null || value.length() <= 0) {
			this.vConfirm1.focus();
		}
		else {
			this.vConfirm2.focus();
		}
	}
	
	protected PasswordField field;
	
	@Wire
	protected Menupopup wPopup;
	
	@Wire
	protected Menuitem mEdit;

	@Wire
	protected Menuitem mShow;
	
	@Wire
	protected Menuitem mHide;
	
	@Wire
	protected Menuitem mClear;

	@Wire
	protected MsgWindow wConfirm;
	
//	@Wire
	protected PasswordField vConfirm1;
	
//	@Wire
	protected PasswordField vConfirm2;
	
//	@Wire
	protected Action bActionShow;
	
//	@Wire
	protected Action bActionSuggest;

	protected void initUi () {
		super.initUi();
		initUi_Popup();
		initUi_Confirm();
	}

	protected void initUi_Popup() {
		this.mEdit.setLabel("Editar");
		
		this.mShow.setLabel("Mostrar");
		
		this.mHide.setLabel("Ocultar");
		
		this.mClear.setLabel("Limpiar");
		
	}

	protected void initUi_Confirm() {
		final PasswordFieldEditExtension _this = this;
		
		Message msg = new Message();
		msg.setTitle("Confirmación");
		msg.setMessage("Introduzca la contraseña y su confirmación");
		msg.setProcessOk(new MsgProcess() {

			public void run(MsgWindow wnd) {
				if (_this.vConfirm1.isHide()) {
					String value1 = _this.vConfirm1.getValue();
					String value2 = _this.vConfirm2.getValue();
					boolean flag = true;
					flag = flag && value1 != null && value2 != null;
					flag = flag && value1.length() > 0 && value2.length() > 0;
					flag = flag && value1.equals(value2);
					if (flag) {
						_this.field.setValue(value1);
						wnd.setVisible(false);
					}
					else {
						_this.vConfirm2.focus();
					}
				}
				else {
					String value1 = _this.vConfirm1.getValue();
					_this.field.setValue(value1);
					wnd.setVisible(false);
				}
			}
			
		});
		msg.setProcessCancel(new MsgProcess() {

			public void run(MsgWindow wnd) {
				wnd.setVisible(false);
			}
			
		});
		msg.setType("info");
		
		this.wConfirm.setMessage(msg);
		this.wConfirm.setVisible(false);
		
		
		if (this.vConfirm1 == null) {
			this.vConfirm1 = (PasswordField)this.wConfirm.getFellowIfAny("vConfirm1", true);
		}
		this.vConfirm1.setValue(null);
		this.vConfirm1.setLabel("Contraseña");
		
		if (this.vConfirm2 == null) {
			this.vConfirm2 = (PasswordField)this.wConfirm.getFellowIfAny("vConfirm2", true);
		}
		this.vConfirm2.setValue(null);
		this.vConfirm2.setLabel("Confirmar");
		
		if (this.bActionShow == null) {
			this.bActionShow = (Action)this.wConfirm.getFellowIfAny("bActionShow", true);
		}
		this.bActionShow.setName("Mostrar");
		this.bActionShow.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event event) throws Exception {
				_this._onClick_bActionShow();
			}
			
		});
		
		if (this.bActionSuggest == null) {
			this.bActionSuggest = (Action)this.wConfirm.getFellowIfAny("bActionSuggest", true);
		}
		this.bActionSuggest.setName("Sugerir");
		this.bActionSuggest.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event event) throws Exception {
				_this._onClick_bActionSuggest();
			}
			
		});
	}

    @Listen("onClick = #mEdit")
	public void _onClick_mEdit () {
    	openConfirm();
	}
    
    @Listen("onClick = #mShow")
	public void _onClick_mShow () {
    	this.field.show();
	}
    
    @Listen("onClick = #mHide")
	public void _onClick_mHide () {
    	this.field.hide();
	}
    
    @Listen("onClick = #mClear")
	public void _onClick_mClear () {
		final PasswordFieldEditExtension _this = this;
		
		Message msg = new Message();
		msg.setTitle("Confirmación");
		msg.setMessage("¿Seguro que desea dejar en blanco la contraseña?");
		msg.setProcessOk(new MsgProcess() {

			public void run(MsgWindow wnd) {
				_this.field.setValue(null);
				wnd.close();
			}
			
		});
		msg.setProcessCancel(new MsgProcess() {

			public void run(MsgWindow wnd) {
				wnd.close();
			}
			
		});
		msg.setType("info");
		
		MsgWindow wnd = new MsgWindow();
		wnd.setParent(ViewContext.getMainWindow());
		wnd.setMessage(msg);
		wnd.doHighlighted();
	}
    
//    @Listen("onClick = #bActionShow")
	public void _onClick_bActionShow () {
    	if (this.vConfirm1.isHide()) {
        	this.vConfirm1.show();
        	this.vConfirm2.setVisible(false);
        	this.bActionShow.setName("Ocultar");
    	}
    	else {
        	this.vConfirm1.hide();
        	this.vConfirm2.setVisible(true);
        	this.bActionShow.setName("Mostrar");
    	}
    	this.vConfirm2.setValue(null);
	}
    
//    @Listen("onClick = #bActionSuggest")
	public void _onClick_bActionSuggest () {
    	String newPassword = PasswordGenerator.generateStrong();
    	this.vConfirm1.setValue(newPassword);
    	this.vConfirm1.show();
    	this.vConfirm2.setVisible(false);
	}
	
}
