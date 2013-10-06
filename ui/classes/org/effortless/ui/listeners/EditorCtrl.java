package org.effortless.ui.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.effortless.ann.InfoFacade;
import org.effortless.core.MethodUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.model.Entity;
import org.effortless.model.Filter;
import org.effortless.model.MapFilter;
import org.effortless.model.Referenciable;
import org.effortless.tests.examples.mvvm.Entidad;
import org.effortless.ui.Message;
import org.effortless.ui.UiApplication;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.Menu;
import org.effortless.ui.windows.EditorWindow;
import org.effortless.ui.windows.EmbeddedFilterWindow;
import org.effortless.ui.windows.FinderWindow;
import org.effortless.ui.windows.MainWindow;
import org.effortless.ui.windows.MsgWindow;
import org.effortless.ui.windows.Windows;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class EditorCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public EditorCtrl () {
		super();
		initiate();
	}
	
	public EditorCtrl (org.zkoss.zk.ui.event.EventListener mainCtrl) {
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
		if (event != null && event.isPropagatable()) {
			boolean check = _onEventEditor(event);
			if (check) {
				event.stopPropagation();
			}
		}
	}
	
	protected boolean _onEventEditor (Event event) throws Exception {
		boolean result = false;
		if (event != null) {
			EditorWindow window = null;
			try {
				window = (EditorWindow)event.getTarget();
			}
			catch (ClassCastException e) {
			}
			if (window != null) {
				String eventName = event.getName();
				if (CteEvents.ON_EDITOR_SAVE.equals(eventName)) {
					onEditorSave(event);
				}
				else if (CteEvents.ON_EDITOR_CANCEL.equals(eventName)) {
					onEditorCancel(event);
				}
				else if (CteEvents.ON_EDITOR_BACK.equals(eventName)) {
					onEditorBack(event);
				}
				else {
					onEditorDefaultEvent(event);
				}
			}
			result = (window != null);
		}
		return result;
	}
	
	protected void onEditorSave(Event event) {
//		System.out.println("ON EDITOR SAVE");
		
		Object value = event.getData();
		boolean persist = true;
		if (persist && value instanceof Entity) {
			((Entity)value).persist();
		}
		Component editor = event.getTarget();
		
		
		FinderWindow finderWindow = (FinderWindow)editor.getAttribute(CteUi.CMP_CALLER);
		EmbeddedFilterWindow filterWindow = (finderWindow != null ? finderWindow.getFilterEditor() : null);
		MapFilter mapFilter = (filterWindow != null ? filterWindow.getValue() : null);
		Filter filter = (mapFilter != null ? mapFilter.getFilter() : null);
		if (filter != null) {
			filter.setPageIndex(Integer.valueOf(0));
			filter.reset();
			Long numElements = Long.valueOf(filter.size());
			java.util.List list = filter.listPage();
			finderWindow.setStartPage(Long.valueOf(filter.getPageIndex() != null ? filter.getPageIndex().intValue() + 1 : 0));
			finderWindow.setEndPage(Long.valueOf(filter.getTotalPages()));
			finderWindow.setValue(list);
			finderWindow.setNumElements(numElements);
		}
		finderWindow.repaintValue();
		
		_closeEditor(event);
	}

	protected void onEditorCancel(Event event) {
//		System.out.println("ON EDITOR CANCEL");
		if (true) {
			Object value = event.getData();
			Entity entity = null;
			try { entity = (Entity)value; } catch (ClassCastException e) {}
			if (entity != null && ((true || entity.exists()) && entity.hasChanges())) {
				_openMsgWindow(buildMessageCancel(entity, event));
			}
			else {
				_closeEditor(event);
			}
		}
		else {
			_closeEditor(event);
		}
	}
	
	protected void _openMsgWindow (Message msg) {
		MsgWindow wnd = new MsgWindow();
		wnd.setMessage(msg);
		wnd.setParent(this.mainWindow);
		wnd.doHighlighted();
	}
	
	protected Message buildMessageCancel (final Object value, final Event event) {
		Message result = null;
		result = new Message();
		result.setTitle("Confirmación");
		String msg = "¿Está seguro que desea cancelar?";
		Referenciable ref = null;
		try { ref = (Referenciable)value; } catch (ClassCastException e) {}
		if (ref != null) {
			String label = ref.toLabel();
			msg = "'" + label + "' ha cambiado, ¿seguro que desea cancelar?";
		}
		result.setMessage(msg);
		final EditorCtrl _this = this;
		result.setProcessOk(new org.effortless.ui.MsgProcess() {

			public void run(MsgWindow wnd) {
				_this._closeEditor(event);
				wnd.detach();
			}
			
		});
		result.setProcessCancel(new org.effortless.ui.MsgProcess() {

			public void run(MsgWindow wnd) {
				wnd.detach();
			}
			
		});
		result.setType("warning");
		return result;
	}

	protected void onEditorBack(Event event) {
//		System.out.println("ON EDITOR BACK");
		_closeEditor(event);
	}
	
	protected void _closeEditor (Event event) {
		EditorWindow window = (EditorWindow)event.getTarget();
		this.mainWindow.removeWindow(window);
	}

	protected void onEditorDefaultEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
	
}
