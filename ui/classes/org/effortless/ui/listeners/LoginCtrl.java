package org.effortless.ui.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.effortless.ann.InfoFacade;
import org.effortless.core.GlobalContext;
import org.effortless.model.Filter;
import org.effortless.model.MapFilter;
import org.effortless.security.SecuritySystem;
import org.effortless.ui.UiApplication;
import org.effortless.ui.ViewContext;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.Menu;
import org.effortless.ui.windows.EditorWindow;
import org.effortless.ui.windows.EmbeddedFilterWindow;
import org.effortless.ui.windows.FinderWindow;
import org.effortless.ui.windows.LoginWindow;
import org.effortless.ui.windows.MainWindow;
import org.effortless.ui.windows.Windows;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class LoginCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public LoginCtrl () {
		super();
		initiate();
	}
	
	public LoginCtrl (org.zkoss.zk.ui.event.EventListener mainCtrl) {
		this();
		this.mainCtrl = mainCtrl;
	}
	
	protected org.zkoss.zk.ui.event.EventListener mainCtrl;

	protected void initiate () {
		initiateMainWindow();
	}
	
	protected MainWindow mainWindow;
	
	protected void initiateMainWindow () {
		this.mainWindow = null; 
	}
	
	public MainWindow getMainWindow () {
		return this.mainWindow;
	}
	
	public void setMainWindow (MainWindow newValue) {
		this.mainWindow = newValue;
	}
	
	public void onEvent(Event event) throws Exception {
		if (event.isPropagatable()) {
			boolean check = _onEventLogin(event);
			if (check) {
				event.stopPropagation();
			}
		}
	}
	
	protected boolean _onEventLogin (Event event) throws Exception {
		boolean result = false;
		if (event != null) {
			LoginWindow wnd = null;
			try {
				wnd = (LoginWindow)event.getTarget();
			}
			catch (ClassCastException e) {
			}
			if (wnd != null) {
				String eventName = event.getName();
				if (CteEvents.ON_LOGIN.equals(eventName)) {
					boolean validLogin = _onLogin(wnd, event);
					if (validLogin) {
//				    	Events.postEvent(new Event(eventName, ViewContext.getMainWindow(), event.getData()));
						Events.postEvent(CteEvents.ON_LOGIN_SUCCESS, ViewContext.getMainWindow(), event.getData());
						Events.postEvent(CteEvents.ON_LOGIN_SUCCESS, wnd, event.getData());
						wnd.setVisible(false);
					}
					result = true;
				}
			}
		}
		return result;
	}
	
	protected boolean _onLogin(LoginWindow wnd, Event event) {
		boolean result = true;
		java.util.Map data = (java.util.Map)event.getData();
    	String loginName = (String)data.get(CteUi.LOGIN_NAME);
    	String loginPassword = (String)data.get(CteUi.LOGIN_PASSWORD);
		Session session = Sessions.getCurrent();
		SecuritySystem securitySystem = (session != null ? (SecuritySystem)session.getAttribute(GlobalContext.SECURITY_CONTEXT) : null);
		if (securitySystem != null) {
			Object currentUser = (loginName != null && loginPassword != null ? securitySystem.login(loginName, loginPassword) : null);
			result = (currentUser != null);
			if (result) {
				session.setAttribute(GlobalContext.CURRENT_USER, currentUser);
			}
			securitySystem.setupSession(currentUser);
		}
//		result = "root".equals(loginName) && "pass".equals(loginPassword);
//		if (result) {
//			session.setAttribute(GlobalContext.CURRENT_USER, loginName);
//		}
		return result;
	}
	
}
