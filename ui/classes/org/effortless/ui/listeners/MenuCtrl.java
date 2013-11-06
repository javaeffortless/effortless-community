package org.effortless.ui.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.effortless.ann.InfoFacade;
import org.effortless.model.Filter;
import org.effortless.model.MapFilter;
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

public class MenuCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public MenuCtrl () {
		super();
		initiate();
	}
	
	public MenuCtrl (org.zkoss.zk.ui.event.EventListener mainCtrl) {
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
			boolean check = _onEventMenu(event);
			if (check) {
				event.stopPropagation();
			}
		}
	}
	
	protected boolean _onEventMenu (Event event) throws Exception {
		boolean result = false;
		if (event != null) {
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
//					Object item = UiApplication.newItem(clazz);
					Object item = UiApplication.loadCurrentSingleton(clazz);
					item = (item != null ? item : UiApplication.newItem(clazz));
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
					
					boolean autoStartSearch = true;
					
					if (filter != null) {
						filter.setPaginated(Boolean.TRUE);
						filter.setPageIndex(Integer.valueOf(0));
//						filter.setPageSize(Integer.valueOf(3));
					}
					
					if (filter != null && autoStartSearch) {
						Long numElements = Long.valueOf(filter.size());
						java.util.List list = filter.listPage();
						finderWindow.setStartPage(Long.valueOf(filter.getPageIndex() != null ? filter.getPageIndex().intValue() + 1 : 0));
						finderWindow.setEndPage(Long.valueOf(filter.getTotalPages()));
						finderWindow.setValue(list);
						finderWindow.setNumElements(numElements);
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
					
					boolean autoSearch = true;
					
					if (autoSearch) {//autoSearch
						filter.addPropertyChangeListener(new AutoSearchFilterListener(finderWindow));
//						filter.addPropertyChangeListener(new PropertyChangeListener () {
//	
//							public void propertyChange(PropertyChangeEvent evt) {
//								String propertyName = (evt != null ? evt.getPropertyName() : null);
//								boolean flag = false;
////								flag = flag || "paginated".equals(propertyName);
//								flag = flag || "pageIndex".equals(propertyName);
////								flag = flag || "pageSize".equals(propertyName);
//								if (!flag) {
//									filter.setPageIndex(Integer.valueOf(0));
//								}
//								finderWindow.repaintValue();
//							}
//							
//						});
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
			window.addEventListener(CteEvents.ON_SEARCH, this.mainCtrl);
			window.addEventListener(CteEvents.ON_UPDATE_ITEM, this.mainCtrl);
			window.addEventListener(CteEvents.ON_CREATE_ITEM, this.mainCtrl);
			window.addEventListener(CteEvents.ON_READ_ITEM, this.mainCtrl);
			window.addEventListener(CteEvents.ON_DELETE_ITEM, this.mainCtrl);
			window.addEventListener(CteEvents.ON_CLONE_ITEM, this.mainCtrl);
			window.addEventListener(CteEvents.ON_CUSTOM_ACTION, this.mainCtrl);
			window.addEventListener(CteEvents.ON_FINDER_PREVIOUS_PAGE, this.mainCtrl);
			window.addEventListener(CteEvents.ON_FINDER_NEXT_PAGE, this.mainCtrl);
		}
	}

	protected void _listenEditorWindow (EditorWindow window) {
		if (window != null) {
			window.addEventListener(CteEvents.ON_EDITOR_SAVE, this.mainCtrl);
			window.addEventListener(CteEvents.ON_EDITOR_CANCEL, this.mainCtrl);
			window.addEventListener(CteEvents.ON_EDITOR_BACK, this.mainCtrl);
		}
	}

}
