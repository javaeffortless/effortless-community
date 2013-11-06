package org.effortless.ui.listeners;

import org.effortless.model.Filter;
import org.effortless.model.MapFilter;
import org.effortless.ui.UiApplication;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.Field;
import org.effortless.ui.widgets.RefField;
import org.effortless.ui.windows.EmbeddedFilterWindow;
import org.effortless.ui.windows.FinderWindow;
import org.effortless.ui.windows.MainWindow;
import org.effortless.ui.windows.Windows;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Window;

public class FieldCtrl extends Object implements org.zkoss.zk.ui.event.EventListener {

	public FieldCtrl () {
		super();
		initiate();
	}
	
	public FieldCtrl (org.zkoss.zk.ui.event.EventListener mainCtrl) {
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
			
			boolean check = _onEventField(event);
			if (check) {
				event.stopPropagation();
			}
		}
	}
	
	protected boolean _onEventField (Event event) throws Exception {
		boolean result = false;
		if (event != null) {
			Field field = null;
			try { Window window = (Window)event.getTarget(); field = (Field)window.getAttribute(CteUi.CMP_CALLER); } catch (ClassCastException e) {}
			try { field = (field != null ? field : (Field)event.getTarget()); } catch (ClassCastException e) {}
			if (field != null) {
				String eventName = event.getName();
				if (CteEvents.ON_SELECT_REF.equals(eventName)) {
					onSelectRef(event);
				}
				else if (CteEvents.ON_SELECT_ITEMS.equals(eventName)) {
					onSelectItems(event);
				}
				else if (CteEvents.ON_CANCEL_ITEMS.equals(eventName)) {
					onCancelItems(event);
				}
				else {
					onFieldDefaultEvent(event);
				}
			}
			result = (field != null);
		}
		return result;
	}
	
	protected void onCancelItems(Event event) {
		Component cmp = event.getTarget();
		RefField field = (RefField)cmp.getAttribute(CteUi.CMP_CALLER);
//		field.setValue(field.getValue());
		field.refreshValue();
		_closeWindow(event);
//		System.out.println("onCancelItems");
	}

	protected void _closeWindow(Event event) {
		Window wnd = (Window)event.getTarget();
		this.mainWindow.removeWindow(wnd);
	}

	protected void onSelectItems(Event event) {
		Object selectedItem = event.getData();
		if (selectedItem != null) {
			Component cmp = event.getTarget();
			Field field = (Field)cmp.getAttribute(CteUi.CMP_CALLER);
			field.setValue(selectedItem);
		}
		_closeWindow(event);
//		System.out.println("onSelectItems");
	}

	protected void onSelectRef(Event event) {
		Field field = (Field)event.getTarget();
		Class clazz = UiApplication.getFieldClass(field);
		String cName = clazz.getSimpleName();

		String zul = UiApplication.getFinderZul(clazz);
		String filterZul = UiApplication.getFinderFilterZul(clazz);
		
		EmbeddedFilterWindow filterWindow = (EmbeddedFilterWindow)Windows.openWindow(filterZul);
		Class<?> filterClass = UiApplication.getFinderFilterClass(cName);
		
		Filter filterFromField = null;
		try {
			RefField fieldValues = (RefField)field;
			filterFromField = (Filter)fieldValues.getValues();
		} catch (ClassCastException e) {}
		
		final Filter filter = (filterFromField != null ? filterFromField : (Filter)UiApplication.newItem(filterClass));
		filterWindow.setAttribute(CteUi.ENTITY_CLASS, filterClass);
		filterWindow.setValue(new MapFilter(filter));
//		this.mainWindow.addWindow(filterWindow);
		
		final FinderWindow finderWindow = Windows.openFinder(zul);
		finderWindow.setCtrlMode("select");
		finderWindow.setAttribute(CteUi.ENTITY_CLASS, clazz);
		String infoZul = UiApplication.getMoreInfoZul(clazz);
		finderWindow.setAttribute(CteUi.INFO_WINDOW_ID, infoZul);
		
		boolean autoStartSearch = true;
		
		if (filter != null) {
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
//			filter.setPageSize(Integer.valueOf(3));
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
//		finderWindow.setFilterSrc(filterZul);
		finderWindow.setFilterEditor(filterWindow);
		
		_listenFinderWindow(finderWindow, field);
		
		boolean autoSearch = true;
		
		if (autoSearch) {//autoSearch
			filter.addPropertyChangeListener(new AutoSearchFilterListener(finderWindow));
//			filter.addPropertyChangeListener(new PropertyChangeListener () {
//
//				public void propertyChange(PropertyChangeEvent evt) {
//					String propertyName = (evt != null ? evt.getPropertyName() : null);
//					boolean flag = false;
////					flag = flag || "paginated".equals(propertyName);
//					flag = flag || "pageIndex".equals(propertyName);
////					flag = flag || "pageSize".equals(propertyName);
//					if (!flag) {
//						filter.setPageIndex(Integer.valueOf(0));
//					}
//					finderWindow.repaintValue();
//				}
//				
//			});
		}
		this.mainWindow.addWindow(finderWindow);
	}

	protected void _listenFinderWindow (FinderWindow window, Component field) {
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
			
			window.addEventListener(CteEvents.ON_SELECT_ITEMS, this);
			window.addEventListener(CteEvents.ON_CANCEL_ITEMS, this);
			
			window.setAttribute(CteUi.CMP_CALLER, field);
		}
	}
	
	
	protected void onFieldDefaultEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
	
}
