package org.effortless.ui.windows;

import org.effortless.ui.ViewContext;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class LoginWindow extends AbstractWindow {

	public LoginWindow () {
		super();
	}

	protected void initiate () {
		super.initiate();
		
	}
	
    @Wire
    protected Div wLoginLogo;
    
    @Wire
    protected Label wLoginNameTag;
    
    @Wire
    protected Textbox wLoginName;
    
    @Wire
    protected Label wLoginPasswordTag;
    
    @Wire
    protected Textbox wLoginPassword;
    
	@Wire
	protected Button btnLogin;

	
    @Listen("onClick = #btnLogin")
	public void _onLogin () {
    	String loginName = this.wLoginName.getValue();
    	String loginPassword = this.wLoginPassword.getValue();
    	java.util.Map data = new java.util.HashMap();
    	data.put(CteUi.LOGIN_NAME, loginName);
    	data.put(CteUi.LOGIN_PASSWORD, loginPassword);
		Events.echoEvent(CteEvents.ON_LOGIN, this, data);
    }

    protected void initUi () {
    	super.initUi();
    	initUi_Wnd();
    	initUi_BtnLogin();
    }
    
    protected void initUi_Wnd () {
    	//<window id="wnd" width="100%" height="100%" contentStyle="overflow:auto" border="normal">
    	this.setWidth("auto");
    	this.setHeight("auto");
    	this.setTitle("Inicio sesión");
    	this.setContentStyle("overflow:auto");
    	this.setBorder("normal");
    	this.setMaximizable(false);
    	this.setMinimizable(false);
    	this.setClosable(false);
    	this.setSclass("login-window");
    }
    
    protected void initUi_I18n () {
    	super.initUi_I18n();
    	
    	String prefix = (this.i18n != null ? this.i18n + "_" : "");
    	
    	this.setTitle(ViewContext.i18n(prefix + "loginTitle", true, "loginTitle"));
		this.btnLogin.setLabel(ViewContext.i18n(prefix + "login_loginButtonLabel", true, "login_loginButtonLabel"));
    }
    
	protected void initUi_BtnLogin() {
		this.btnLogin.setImage(_images("login.png"));//${images}/close.png
		this.btnLogin.setVisible(true);
	}
	
}
