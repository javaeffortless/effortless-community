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

public class FinderCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public FinderCtrl () {
		super();
		initiate();
	}
	
	public FinderCtrl (org.zkoss.zk.ui.event.EventListener mainCtrl) {
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
			
			boolean check = _onEventFinder(event);
			if (check) {
				event.stopPropagation();
			}
		}
	}
	
	protected boolean _onEventFinder (Event event) throws Exception {
		boolean result = false;
		if (event != null) {
			FinderWindow finderWindow = null;
			try {
				finderWindow = (FinderWindow)event.getTarget();
			}
			catch (ClassCastException e) {
			}
			if (finderWindow != null) {
				String eventName = event.getName();
				if (CteEvents.ON_SEARCH.equals(eventName)) {
					onFinderSearch(event);
				}
				else if (CteEvents.ON_UPDATE_ITEM.equals(eventName)) {
					onFinderUpdateItem(event);
				}
				else if (CteEvents.ON_CREATE_ITEM.equals(eventName)) {
					onFinderCreateItem(event);
				}
				else if (CteEvents.ON_READ_ITEM.equals(eventName)) {
					onFinderReadItem(event);
				}
				else if (CteEvents.ON_DELETE_ITEM.equals(eventName)) {
					onFinderDeleteItem(event);
				}
				else if (CteEvents.ON_CLONE_ITEM.equals(eventName)) {
					onFinderCloneItem(event);
				}
				else if (CteEvents.ON_CUSTOM_ACTION.equals(eventName)) {
					onFinderCustomAction(event);
				}
				else if (CteEvents.ON_FINDER_PREVIOUS_PAGE.equals(eventName)) {
					onFinderPreviousPage(event);
				}
				else if (CteEvents.ON_FINDER_NEXT_PAGE.equals(eventName)) {
					onFinderNextPage(event);
				}
				else {
					onFinderDefaultEvent(event);
				}
			}
			result = (finderWindow != null);
		}
		return result;
	}
	
	protected void onFinderSearch(Event event) {
		FinderWindow finderWindow = (FinderWindow)event.getTarget();
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
	}
	
	protected void onFinderUpdateItem(Event event) {
		String editorSrc = _doGetEditorSrcFromFinder(event);
		Object selectedItem = event.getData();
		if (selectedItem != null) {
			EditorWindow editorWindow = openEditor(selectedItem, editorSrc);
			setupEditor(event, editorWindow);
		}
		else {
			_openMsgWindow(buildMessageInfoSelect(event));				
		}
	}

	protected void onFinderCreateItem(Event event) {
		String editorSrc = _doGetEditorSrcFromFinder(event);
    	Object newItem = _doCreateNewItem(event);
    	if (newItem != null) {
    		EditorWindow editorWindow = openEditor(newItem, editorSrc);
    		
    		setupEditor(event, editorWindow);
    	}
	}
	
	protected EditorWindow openEditor (Object item, String editorSrc) {
		EditorWindow result = null;
		result = Windows.openEditor(item, editorSrc);
		if (item != null) {
			result.setAttribute(CteUi.ENTITY_CLASS, item.getClass());
		}
		return result;
	}

	protected void onFinderReadItem(Event event) {
		String editorSrc = _doGetEditorSrcFromFinder(event);
		if (editorSrc != null) {
			Object selectedItem = event.getData();
			if (selectedItem != null) {
	    		EditorWindow editorWindow = openEditor(selectedItem, editorSrc);
	    		editorWindow.setReadonly(Boolean.TRUE);
	    		setupEditor(event, editorWindow);
			}
			else {
				_openMsgWindow(buildMessageInfoSelect(event));				
			}
		}
	}

	protected void onFinderDeleteItem(Event event) {
		Object value = event.getData();
		if (value instanceof Entity) {
			if (true) {
				Message msg = buildMessageDelete(value, event);
				_openMsgWindow(msg);
			}
			else {
				((Entity)value).delete();
				onFinderSearch(event);
			}
		}
	}
	
	protected void _openMsgWindow (Message msg) {
		MsgWindow wnd = new MsgWindow();
		wnd.setMessage(msg);
		wnd.setParent(this.mainWindow);
		wnd.doHighlighted();
	}
	
	protected Message buildMessageDelete (final Object value, final Event event) {
		Message result = null;
		result = new Message();
		result.setTitle("Confirmación");
		String msg = "¿Está seguro que desea eliminar?";
		Referenciable ref = null;
		try { ref = (Referenciable)value; } catch (ClassCastException e) {}
		if (ref != null) {
			String label = ref.toLabel();
			msg = "¿Seguro que desea eliminar '" + label + "'?";
		}
		result.setMessage(msg);
		result.setProcessOk(new org.effortless.ui.MsgProcess() {

			public void run(MsgWindow wnd) {
				((Entity)value).delete();
				onFinderSearch(event);
				wnd.detach();
			}
			
		});
		result.setProcessCancel(new org.effortless.ui.MsgProcess() {

			public void run(MsgWindow wnd) {
				wnd.detach();
			}
			
		});
		result.setType("delete");
		return result;
	}

	protected Message buildMessageInfoSelect (final Event event) {
		Message result = null;
		result = new Message();
		result.setTitle("Confirmación");
		String msg = "Para realizar la operación debe primero seleccionar";
		result.setMessage(msg);
		result.setProcessOk(new org.effortless.ui.MsgProcess() {

			public void run(MsgWindow wnd) {
				wnd.detach();
			}
			
		});
		result.setType("info");
		return result;
	}

	protected void onFinderPreviousPage(Event event) {
    	System.out.println("ON PREVIOUS PAGE ON MAIN CTRL");
    	
		FinderWindow finderWindow = (FinderWindow)event.getTarget();
		EmbeddedFilterWindow filterWindow = (finderWindow != null ? finderWindow.getFilterEditor() : null);
		MapFilter mapFilter = (filterWindow != null ? filterWindow.getValue() : null);
		Filter filter = (mapFilter != null ? mapFilter.getFilter() : null);
		if (filter != null) {
			Integer pageIndex = filter.getPageIndex();
			int _pageIndex = (pageIndex != null ? pageIndex.intValue() : 0);
			_pageIndex = (_pageIndex > 0 ? _pageIndex - 1 : 0);
			filter.setPageIndex(Integer.valueOf(_pageIndex));
			filter.reset();
			Long numElements = Long.valueOf(filter.size());
			java.util.List list = filter.listPage();
			finderWindow.setStartPage(Long.valueOf(filter.getPageIndex() != null ? filter.getPageIndex().intValue() + 1 : 0));
			finderWindow.setEndPage(Long.valueOf(filter.getTotalPages()));
			finderWindow.setValue(list);
			finderWindow.setNumElements(numElements);
		}
		finderWindow.repaintValue();
	}
		
	protected void onFinderNextPage(Event event) {
//    	System.out.println("ON NEXT PAGE ON MAIN CTRL");
		FinderWindow finderWindow = (FinderWindow)event.getTarget();
		EmbeddedFilterWindow filterWindow = (finderWindow != null ? finderWindow.getFilterEditor() : null);
		MapFilter mapFilter = (filterWindow != null ? filterWindow.getValue() : null);
		Filter filter = (mapFilter != null ? mapFilter.getFilter() : null);
		if (filter != null) {
			Integer totalPages = filter.getTotalPages();
			int _totalPages = (totalPages != null ? totalPages.intValue() : 0);
			Integer pageIndex = filter.getPageIndex();
			int _pageIndex = (pageIndex != null ? pageIndex.intValue() : 0);
			_pageIndex = (_pageIndex >= 0 && (pageIndex + 1) < _totalPages ? _pageIndex + 1 : _pageIndex);
			filter.setPageIndex(Integer.valueOf(_pageIndex));
			filter.reset();
			Long numElements = Long.valueOf(filter.size());
			java.util.List list = filter.listPage();
			finderWindow.setStartPage(Long.valueOf(filter.getPageIndex() != null ? filter.getPageIndex().intValue() + 1 : 0));
			finderWindow.setEndPage(Long.valueOf(totalPages));
			finderWindow.setValue(list);
			finderWindow.setNumElements(numElements);
		}
		finderWindow.repaintValue();
	}
		
	protected void onFinderCustomAction(Event event) {
		Object[] data = (Object[])event.getData();
    	if (data != null && data.length > 1) {
    		String methodName = (String)data[0];
    		Object selectedItem = data[1];
    		if (selectedItem != null && methodName != null) {
    			MethodUtils.run(selectedItem, methodName);
    		}
    	}
	}

	
	protected void onFinderCloneItem(Event event) {
		Object selectedItem = event.getData();
    	if (selectedItem != null) {
    		if (selectedItem instanceof Entity) {
       			Entity entity = (Entity)selectedItem;
       			Entity cloneEntity = entity.clone();
       			String editorSrc = _doGetEditorSrcFromFinder(event);
       	    	if (cloneEntity != null && editorSrc != null) {
       	    		EditorWindow editorWindow = openEditor(cloneEntity, editorSrc);
       	    		setupEditor(event, editorWindow);
        		}
    		}
    	}
	}

	protected void onFinderDefaultEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
	
	protected void setupEditor (Event event, EditorWindow editor) {
		if (editor != null) {
			boolean flag = false;
			editor.setMaximizable(false);
			editor.setSizable(false);
			editor.setMinimizable(false);
//			if (flag) {
//				editor.doHighlighted();
//			}
//			else {
//				editor.doPopup();
//			}
			if (editor != null) {
				editor.setAttribute(CteUi.CMP_CALLER, event.getTarget());
				_listenEditorWindow(editor);
				this.mainWindow.addWindow(editor);
			}
		}
	}

	protected void _listenEditorWindow (EditorWindow window) {
		if (window != null) {
			window.addEventListener(CteEvents.ON_EDITOR_SAVE, this.mainCtrl);
			window.addEventListener(CteEvents.ON_EDITOR_CANCEL, this.mainCtrl);
			window.addEventListener(CteEvents.ON_EDITOR_BACK, this.mainCtrl);
		}
	}

	protected Object _doCreateNewItem(Event event) {
		Object result = null;
		Component cmp = event.getTarget();
		Class<?> clazz = (cmp != null ? (Class<?>)cmp.getAttribute(CteUi.ENTITY_CLASS) : null);
		result = (clazz != null ? UiApplication.newItem(clazz) : null);
		if (false && result == null) {
			result = new Entidad("HOLA");
		}
		return result;
	}

	protected String _doGetEditorSrcFromFinder (Event event) {
		String result = null;
		
		Component cmp = event.getTarget();
		Class<?> clazz = (cmp != null ? (Class<?>)cmp.getAttribute(CteUi.ENTITY_CLASS) : null);
		
		result = (clazz != null ? UiApplication.getEditorZul(clazz) : null);
		if (false && result == null) {
			result = "/org.effortless.icondb/resources/main/editor.zul";
		}
		return result;
	}

}
