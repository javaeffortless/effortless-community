package org.effortless.ui.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.effortless.ann.InfoFacade;
import org.effortless.core.MethodUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.model.Entity;
import org.effortless.model.Filter;
import org.effortless.model.MapFilter;
import org.effortless.tests.examples.mvvm.Entidad;
import org.effortless.ui.UiApplication;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.Menu;
import org.effortless.ui.windows.EditorWindow;
import org.effortless.ui.windows.EmbeddedFilterWindow;
import org.effortless.ui.windows.FinderWindow;
import org.effortless.ui.windows.MainWindow;
import org.effortless.ui.windows.Windows;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class CopyOfMainCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public CopyOfMainCtrl () {
		super();
		initiate();
	}
	
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
		event.stopPropagation();
		
		System.out.println("APP Alias = " + UiApplication.getAppAlias());
		System.out.println("APP Package Name = " + UiApplication.getAppPackageName());
		
		boolean check = true;
		check = !_onEventMenu(event, check);
		check = !_onEventFinder(event, check);
		check = !_onEventEditor(event, check);
	}
	
	protected boolean _onEventMenu (Event event, boolean check) throws Exception {
		boolean result = false;
		if (event != null && check) {
			Menu menu = null;
			try {
				menu = (Menu)event.getTarget();
			}
			catch (ClassCastException e) {
			}
			if (menu != null) {
				this.mainWindow.removeAllWindows();
				if (true || menu.isSelected()) {
					_openMenuWindow(menu, event);
				}
			}
			result = (menu != null);
		}
		return result;
	}
	
	protected void _openMenuWindow(Menu menu, Event event) {
		String link = menu.getLink();
		Component window = null;
		if (true || link.contains(CteUi.SUFFIX_FINDER_ZUL)) {
			int idx = link.indexOf(CteUi.SUFFIX_FINDER_ZUL);
			String cName = (idx > -1 ? link.substring(0, idx) : link);
			Class clazz = UiApplication.getEntityClass(cName);
			if (clazz != null) {
				if (InfoFacade.isSingleton(clazz)) {
					Object item = UiApplication.newItem(clazz);
					String editorSrc = UiApplication.getEditorZul(clazz);
			    	if (item != null) {
			    		EditorWindow editorWindow = Windows.openEditor(item, editorSrc);
			    		setupEditor(event, editorWindow);
			    	}
				}
				else {
					String zul = UiApplication.getFinderZul(clazz);
					String filterZul = UiApplication.getFinderFilterZul(clazz);
					
					EmbeddedFilterWindow filterWindow = (EmbeddedFilterWindow)Windows.openWindow(filterZul);
					Class<?> filterClass = UiApplication.getFinderFilterClass(cName);
					final Filter filter = (Filter)UiApplication.newItem(filterClass);
					filterWindow.setAttribute(CteUi.ENTITY_CLASS, filterClass);
					filterWindow.setValue(new MapFilter(filter));
//					this.mainWindow.addWindow(filterWindow);
					
					final FinderWindow finderWindow = Windows.openFinder(zul);
					finderWindow.setAttribute(CteUi.ENTITY_CLASS, clazz);
					String infoZul = UiApplication.getMoreInfoZul(clazz);
					finderWindow.setAttribute(CteUi.INFO_WINDOW_ID, infoZul);
					
					boolean autoStartSearch = false;
					
					if (filter != null) {
						filter.setPaginated(Boolean.TRUE);
						filter.setPageIndex(Integer.valueOf(0));
						filter.setPageSize(Integer.valueOf(1));
					}
					
					if (filter != null && autoStartSearch) {
						java.util.List list = filter.listPage();
						finderWindow.setValue(list);
					}
					else if (false) {
						java.util.List list = new java.util.ArrayList();
						list.add(UiApplication.newItem(clazz));
						finderWindow.setValue(list);
					}
//					finderWindow.setFilterSrc(filterZul);
					finderWindow.setFilterEditor(filterWindow);
					
					window = finderWindow;
					_listenFinderWindow(finderWindow);
					
					if (true) {//autoSearch
						filter.addPropertyChangeListener(new PropertyChangeListener () {
	
							public void propertyChange(PropertyChangeEvent evt) {
								filter.setPageIndex(Integer.valueOf(0));
								finderWindow.setValue(filter);
								finderWindow.repaintValue();
							}
							
						});
					}
					
				}
			}
			else {
				String zul = "/org.effortless.icondb/resources/main/finder.zul";
				FinderWindow finderWindow = Windows.openFinder(zul);
				window = finderWindow;
				_listenFinderWindow(finderWindow);
			}
		}
		if (window != null) {
			this.mainWindow.addWindow(window);
		}
//		System.out.println("MENU link = " + link);
//		Windows.openEditor(item, editor)
		
//		Windows.openFinder(finder);
		// TODO Auto-generated method stub
		
	}

	protected boolean _onEventFinder (Event event, boolean check) throws Exception {
		boolean result = false;
		if (event != null && check) {
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
	
	protected boolean _onEventEditor (Event event, boolean check) throws Exception {
		boolean result = false;
		if (event != null && check) {
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
	

	

	protected void onFinderSearch(Event event) {
		FinderWindow finderWindow = (FinderWindow)event.getTarget();
		EmbeddedFilterWindow filterWindow = (finderWindow != null ? finderWindow.getFilterEditor() : null);
		MapFilter mapFilter = (filterWindow != null ? filterWindow.getValue() : null);
		Filter filter = (mapFilter != null ? mapFilter.getFilter() : null);
		if (filter != null) {
			filter.setPageIndex(Integer.valueOf(0));
			java.util.List list = filter.listPage();
			finderWindow.setValue(list);
		}
		finderWindow.repaintValue();
	}
	
	protected void onFinderUpdateItem(Event event) {
		String editorSrc = _doGetEditorSrcFromFinder(event);
		Object selectedItem = event.getData();
   		EditorWindow editorWindow = Windows.openEditor(selectedItem, editorSrc);
   		setupEditor(event, editorWindow);
	}

	protected void onFinderCreateItem(Event event) {
		String editorSrc = _doGetEditorSrcFromFinder(event);
    	Object newItem = _doCreateNewItem(event);
    	if (newItem != null) {
    		EditorWindow editorWindow = Windows.openEditor(newItem, editorSrc);
    		setupEditor(event, editorWindow);
    	}
	}

	protected void onFinderReadItem(Event event) {
		String editorSrc = _doGetEditorSrcFromFinder(event);
		if (editorSrc != null) {
			Object selectedItem = event.getData();
    		EditorWindow editorWindow = Windows.openEditor(selectedItem, editorSrc);
    		editorWindow.setReadonly(Boolean.TRUE);
    		setupEditor(event, editorWindow);
		}
	}

	protected void onFinderDeleteItem(Event event) {
		Object value = event.getData();
		if (value instanceof Entity) {
			((Entity)value).delete();
			onFinderSearch(event);
		}
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
			java.util.List list = filter.listPage();
			finderWindow.setValue(list);
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
			Integer pageIndex = filter.getPageIndex();
			int _pageIndex = (pageIndex != null ? pageIndex.intValue() : 0);
			_pageIndex = (_pageIndex >= 0 ? _pageIndex + 1 : 0);
			filter.setPageIndex(Integer.valueOf(_pageIndex));
			java.util.List list = filter.listPage();
			finderWindow.setValue(list);
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
       	    		EditorWindow editorWindow = Windows.openEditor(cloneEntity, editorSrc);
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
			editor.setMaximizable(true);
			editor.setSizable(true);
			editor.setMinimizable(true);
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

	protected void _listenFinderWindow (FinderWindow window) {
		if (window != null) {
			window.addEventListener(CteEvents.ON_SEARCH, this);
			window.addEventListener(CteEvents.ON_UPDATE_ITEM, this);
			window.addEventListener(CteEvents.ON_CREATE_ITEM, this);
			window.addEventListener(CteEvents.ON_READ_ITEM, this);
			window.addEventListener(CteEvents.ON_DELETE_ITEM, this);
			window.addEventListener(CteEvents.ON_CLONE_ITEM, this);
			window.addEventListener(CteEvents.ON_CUSTOM_ACTION, this);
			window.addEventListener(CteEvents.ON_FINDER_PREVIOUS_PAGE, this);
			window.addEventListener(CteEvents.ON_FINDER_NEXT_PAGE, this);
		}
	}

	protected void _listenEditorWindow (EditorWindow window) {
		if (window != null) {
			window.addEventListener(CteEvents.ON_EDITOR_SAVE, this);
			window.addEventListener(CteEvents.ON_EDITOR_CANCEL, this);
			window.addEventListener(CteEvents.ON_EDITOR_BACK, this);
		}
	}

	
	
	
	protected void onEditorSave(Event event) {
		System.out.println("ON EDITOR SAVE");
		
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
			java.util.List list = filter.listPage();
			finderWindow.setValue(list);
		}
		finderWindow.repaintValue();
		
		_closeEditor(event);
	}

	protected void onEditorCancel(Event event) {
//		System.out.println("ON EDITOR CANCEL");
		_closeEditor(event);
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
