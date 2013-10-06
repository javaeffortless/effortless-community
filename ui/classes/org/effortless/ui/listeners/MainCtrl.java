package org.effortless.ui.listeners;

import org.effortless.ui.UiApplication;
import org.effortless.ui.windows.MainWindow;
import org.zkoss.zk.ui.event.Event;

public class MainCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public MainCtrl () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateMainWindow();
		this.menuCtrl = new MenuCtrl(this);
		this.finderCtrl = new FinderCtrl(this);
		this.editorCtrl = new EditorCtrl(this);
		this.fieldCtrl = new FieldCtrl(this);
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
		this.menuCtrl.setMainWindow(newValue);
		this.finderCtrl.setMainWindow(newValue);
		this.editorCtrl.setMainWindow(newValue);
		this.fieldCtrl.setMainWindow(newValue);
	}

	protected MenuCtrl menuCtrl;
	protected FinderCtrl finderCtrl;
	protected EditorCtrl editorCtrl;
	protected FieldCtrl fieldCtrl;
	
	public void onEvent(Event event) throws Exception {
//		System.out.println("APP Alias = " + UiApplication.getAppAlias());
//		System.out.println("APP Package Name = " + UiApplication.getAppPackageName());
		
		this.menuCtrl.onEvent(event);
		this.finderCtrl.onEvent(event);
		this.editorCtrl.onEvent(event);
		this.fieldCtrl.onEvent(event);
	}
	
}
