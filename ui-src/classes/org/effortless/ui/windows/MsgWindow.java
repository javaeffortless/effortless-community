package org.effortless.ui.windows;

import org.effortless.core.ObjectUtils;
import org.effortless.ui.Message;
import org.effortless.ui.MsgProcess;
import org.effortless.ui.ViewContext;
import org.effortless.ui.actions.Action;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

public class MsgWindow extends AbstractWindow {

	public MsgWindow () {
		super();
	}

	protected void initiate () {
		super.initiate();
	}
	
    protected void initUi () {
    	super.initUi();
    	initUi_Wnd();
    	initUi_Buttons();
    }
    
    protected void initUi_Buttons () {
    	this.wBtnOk.setVisible(false);
    	this.wBtnCancel.setVisible(false);
    }

    @Wire
    protected Div wIcon;
    
    @Wire
    protected Label wMsg;
    
    @Wire
    protected Button wBtnOk;
    
    @Wire
    protected Button wBtnCancel;
    
    protected Message message;
    
    protected void initiateMessage () {
    	this.message = null;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
    public Message getMessage () {
    	return this.message;
    }
    
    public void setMessage (Message newValue) {
    	Message oldValue = this.message;
    	if (!ObjectUtils.equals(oldValue, newValue)) {
    		this.message = newValue;
    		notifyChange(null);
    		_onChangeMessage();
    	}
    }
    
    protected void _onChangeMessage () {
    	Message msg = this.message;
    	this.setVisible(msg != null);
    	if (msg != null) {
    		this.wIcon.setSclass(msg.getIcon());
    		this.wMsg.setValue(msg.getMessage());
    		setTitle(msg.getTitle());
    		this.wMsg.setTooltiptext(msg.getDetails());
    		
    		this.wBtnOk.setLabel("Aceptar");
    		this.wBtnCancel.setLabel("Cancelar");
    		
        	this.wBtnOk.setVisible((msg.getProcessOk() != null));
        	this.wBtnCancel.setVisible((msg.getProcessCancel() != null));
    	}
    	else {
    		initUi_Buttons();
    	}
    }
    
    @Listen("onClick = #wBtnOk")
	public void _onClick_wBtnOk () {
//        <button id="wBtnOk" label="Aceptar" onClick="@command('msgConfirm')" />
		MsgProcess process = (this.message != null ? this.message.getProcessOk() : null);
		if (process != null) {
			process.run(this);
		}
//		setMessage(null);
    }
    
    @Listen("onClick = #wBtnCancel")
	public void _onClick_wBtnCancel () {
//        <button id="wBtnCancel" label="Cancelar" onClick="@command('msgCancel')" />
    	MsgProcess process = (this.message != null ? this.message.getProcessCancel() : null);
		if (process != null) {
			process.run(this);
		}
//		setMessage(null);
    }
    
    
    
    
    
    protected void initUi_Wnd () {
    	//<window title="@load(vm.msg.title)" mode="highlighted" closable="true" position="center" visible="@load(not empty vm.msg)">
    	
    	setTitle(ViewContext.i18n("msg.title"));
    	setMode("highlighted");
    	
    	setClosable(true);
    	setPosition("center");
//    	setVisible(true);//="@load(not empty vm.msg)">    	

    	_onChangeMessage();
    }

    
    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	boolean result = false;
    	Action action = null;
    	try { action = (Action)newChild; } catch (ClassCastException e) {}
    	if (action != null) {
    		Component pivot = this.getFirstChild().getFellow("buttons");
    		Component newRefChild = pivot.getFirstChild();
    		result = pivot.insertBefore(action, newRefChild);
    	}
    	else {
    		Component pivot = this.getFirstChild();
    		Component newRefChild = pivot.getFellow("phrase");
    		result = pivot.insertBefore(newChild, newRefChild);
    	}
    	return result;
    }

    
    
}
