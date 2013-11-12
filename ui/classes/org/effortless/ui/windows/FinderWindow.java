package org.effortless.ui.windows;

import java.util.List;

import org.effortless.core.ObjectUtils;
import org.effortless.model.Filter;
import org.effortless.ui.Message;
import org.effortless.ui.ViewContext;
import org.effortless.ui.actions.Action;
import org.effortless.ui.actions.PivotAction;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.ListField;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;

public class FinderWindow<T extends Object> extends AbstractFieldWindow<List<T>> implements PivotAction<T> {

//    @Wire
//    protected Include wFilter;

    @Wire
    protected Button btnOkSelectItem;

	@Wire
    protected Button btnCancelSelectItem;
	
    @Wire
    protected Div wFilterContainer;
    
    @Wire
    protected Hlayout wBottomActions;
    
    @Wire
    protected Button btnSearch;
    
    @Wire
    protected ListField<T> wList;
    
    @Wire
    protected Hlayout wListActions;
    
    @Wire
    protected Button btnCloneItem;
    
	@Wire
	protected Button btnCreateItem;
	
	@Wire
	protected Button btnReadItem;
	
	@Wire
	protected Button btnUpdateItem;
	
	@Wire
	protected Button btnDeleteItem;
	
	
	public FinderWindow () {
		super();
	}

	protected String ctrlMode;
	
	public void initiateCtrlMode () {
		this.ctrlMode = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeCtrlMode)")
	public String getCtrlMode () {
		return this.ctrlMode;
	}
	
	public void setCtrlMode (String newValue) {
		boolean binded = _applyBinding("ctrlMode", newValue);
//		if (binded) {
//			_updateTag();
//		}
		if (!binded && !ObjectUtils.equals(this.ctrlMode, newValue)) {
			this.ctrlMode = newValue;
//			_notifyChange("value");
			notifyChange("CtrlMode");
			_updateCtrlMode();
		}
	}
	
	
//    protected void _endBuild() {
//    	super._endBuild();
////    	if (this.wList != null) {
////    		final FinderWindow _this = this;
////    		final EventListener DEFAULT_LIST_FIELD_LISTENER = new EventListener () {
////
////    			public void onEvent(Event event) throws Exception {
////    				// TODO Auto-generated method stub
////    				System.out.println("QUE PASA " + event.getName());
////    				Events.postEvent(event.getName(), _this, _this.value);
////    			}
////    			
////    		};
////    		this.wList.addEventListener(CteEvents.ON_PREVIOUS_PAGE, DEFAULT_LIST_FIELD_LISTENER);
////    		this.wList.addEventListener(CteEvents.ON_NEXT_PAGE, DEFAULT_LIST_FIELD_LISTENER);
////    	}
//	}
	
	protected void _updateCtrlMode() {
		boolean selectMode = "select".equals(this.ctrlMode);
		
		boolean visibleSelect = selectMode;
		boolean visibleCrud = !selectMode;
		
		this.btnOkSelectItem.setVisible(visibleSelect);		
		this.btnCancelSelectItem.setVisible(visibleSelect);
		
		this.btnOkSelectItem.setLabel("Aceptar selección");		
		this.btnCancelSelectItem.setLabel("Cancelar selección");
		
		this.btnCreateItem.setVisible(visibleCrud);
		this.btnReadItem.setVisible(visibleCrud);
		this.btnUpdateItem.setVisible(visibleCrud);
		this.btnDeleteItem.setVisible(visibleCrud);
	}

	protected void initiate () {
		super.initiate();
//		initiateFilterSrc();
		initiateFilterEditor();
	}

    protected void initUi () {
    	super.initUi();
    	initUi_Wnd();
    	initUi_Buttons();
    	_updateCtrlMode();
    }
    
    protected void initUi_Buttons () {
    	this.btnOkSelectItem.setLabel(ViewContext.i18n("finder_button_select_item_label"));
    	this.btnOkSelectItem.setImage(_images("ok_select.png"));
    	
    	this.btnCancelSelectItem.setLabel(ViewContext.i18n("finder_button_cancel_select_item_label"));
    	this.btnCancelSelectItem.setImage(_images("cancel_select.png"));
    }
    
    protected void initUi_Wnd () {
    	//<window id="wnd" width="100%" height="100%" contentStyle="overflow:auto" border="normal">
    	this.setWidth("100%");
    	this.setHeight("100%");
    	this.setContentStyle("overflow:auto");
    	this.setBorder("normal");
    	this.setMaximizable(false);
    	this.setMinimizable(false);
    	this.setClosable(false);
    	this.setSclass("finder-window");
    }
	
    protected void initiateLayoutSize () {
    	this.layoutSize = Integer.valueOf(1);
    }
    
    
    protected EmbeddedFilterWindow filterEditor;
    
    protected void initiateFilterEditor () {
    	this.filterEditor = null;
    }
    
    public EmbeddedFilterWindow getFilterEditor () {
    	return this.filterEditor;
    }
    
    public void setFilterEditor (EmbeddedFilterWindow newValue) {
    	EmbeddedFilterWindow oldValue = this.filterEditor;
    	if (oldValue != newValue) {
    		this.filterEditor = newValue;
   			Components.removeAllChildren(this.wFilterContainer);
   			if (newValue != null) {
   				this.wFilterContainer.appendChild(newValue);
   			}
    	}
    }
    
//    protected String filterSrc;
//    
//    protected void initiateFilterSrc () {
//    	this.filterSrc = null;
//    }
//    
//    public String getFilterSrc () {
//    	return this.filterSrc;
//    }
//    
//    public void setFilterSrc (String newValue) {
//    	String oldValue = this.filterSrc;
//    	if (!ObjectUtils.equals(oldValue, newValue)) {
//        	this.filterSrc = newValue;
//        	_updateFilterSrc();
//    	}
//    }
//    
//	protected void _updateFilterSrc() {
//		if (this.filterSrc != null && this.wFilter != null) {
//			this.wFilter.setSrc(this.filterSrc);
//		}
//	}
	
    public void repaintValue () {
    	if (this.wList != null) {
    		_updateValue();
    		this.wList.repaintValue();
    	}
    }
    
    protected void _updateNumElements () {
    	if (this.wList != null && false) {
    		int numElements = (this.value != null ? this.value.size() : 0);
    		this.wList.setNumElements(Long.valueOf(numElements));
		}
    }
    
    protected void _updatePagination () {
    	if (this.wList != null && false) {
    		Boolean pagination = Boolean.FALSE;
    		Filter filter = null;
    		try { filter = (Filter)this.value; } catch (ClassCastException e) {}
    		pagination = (filter != null ? filter.getPaginated() : pagination);
    		pagination = (pagination != null ? pagination : Boolean.FALSE);
    		this.wList.setPagination(pagination);
		}
    }
    
	protected void _updateValue() {
		_updateNumElements();
		_updatePagination();
		this.wList.setValue(this.value);
//		if (this.filterEditor != null && this.value instanceof Filter) {
//			this.filterEditor.setValue(this.value);
//		}
//		if (this.wFilter != null && this.value instanceof Filter) {
//			this.wFilter.setDynamicProperty("value", this.value);
//		}
		if (this.wList != null) {
			Template tm = this.getTemplate("content");
			if (tm != null) {
				this.wList.setAttribute(CteUi.RENDER_READONLY, Boolean.TRUE);

				this.wList.setAttribute(CteUi.INFO_WINDOW_ID, this.getAttribute(CteUi.INFO_WINDOW_ID));
				
				this.wList.setTemplate("content", tm);
			}
		}
	}
    
    public Template setTemplate(String name, Template tm) {
    	Template result = null;
    	result = super.setTemplate(name, tm);
    	_updateTemplates(name, tm);
    	return result;
    }
    
    protected EventListener customAction;
    
    protected EventListener doGetCustomAction () {
    	if (this.customAction == null) {
			final FinderWindow _this = this;
			this.customAction = new EventListener() {

				public void onEvent(Event event) throws Exception {
					if (event != null) {
						Action action = (Action)event.getTarget();
						String actionName = (action != null ? action.getName() : null);
						
						Events.echoEvent(CteEvents.ON_CUSTOM_ACTION, _this, new Object[] {actionName, _this.getSelectedItem()});
					}
				}
				
			};
    	}
    	return this.customAction;
    }
    
    protected void _updateTemplates(String name, Template tm) {
		if (name != null && tm != null && "bottom-actions".equals(name.trim().toLowerCase())) {
			Components.removeAllChildren(this.wBottomActions);
			Component[] array = _buildComponents(tm, null, 0, 0, this.wBottomActions, null);
			int arrayLength = (array != null ? array.length : 0);
			this.wBottomActions.setVisible((arrayLength > 0));
			
			for (int i = 0; i < arrayLength; i++) {
				Component itemArray = array[i];
				Action action = null;
				try { action = (Action)itemArray; }	catch (ClassCastException e) {}
				if (action != null) {
					action.addEventListener(Events.ON_CLICK, doGetCustomAction());
				}
			}
		}
	}

//	protected void _updateValue(String newValue) {
//	}
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public T getSelectedItem () {
		return (this.wList != null ? this.wList.getSelectedItem() : null);
	}
	
	public void setSelectedItem (T newValue) {
		if (this.wList != null) {
			this.wList.setSelectedItem(newValue);
		}
	}
	
    @Listen("onClick = #btnOkSelectItem")
	public void _onClick_btnOkSelectItem () {
    	T selectedItem = getSelectedItem();
    	Events.postEvent(CteEvents.ON_SELECT_ITEMS, this, selectedItem);
    }
	
    @Listen("onClick = #btnCancelSelectItem")
	public void _onClick_btnCancelSelectItem () {
    	T selectedItem = getSelectedItem();
    	Events.postEvent(CteEvents.ON_CANCEL_ITEMS, this, selectedItem);
    }
	
    @Listen("onPreviousPage = #wList")
	public void _onPreviousPage (Event event) {
    	event.stopPropagation();
//    	System.out.println("ON PREVIOUS PAGE ON FINDER");
		Events.echoEvent(CteEvents.ON_FINDER_PREVIOUS_PAGE, this, this.value);
    }
	
    @Listen("onNextPage = #wList")
	public void _onNextPage (Event event) {
    	event.stopPropagation();
//    	System.out.println("ON NEXT PAGE ON FINDER");
		Events.echoEvent(CteEvents.ON_FINDER_NEXT_PAGE, this, this.value);
    }
	
    @Listen("onClick = #btnSearch")
	public void _search () {
		Events.echoEvent(CteEvents.ON_SEARCH, this, this.value);
    }
	
    @Listen("onClick = #btnCloneItem")
	public void _cloneSelectedItem () {
    	T selectedItem = getSelectedItem();
    	if (selectedItem != null) {
   			Events.echoEvent(CteEvents.ON_CLONE_ITEM, this, selectedItem);
    	}
    	else {
			_openMsgWindow(buildMessageInfoSelect());				
    	}
	}
	
    @Listen("onClick = #btnCreateItem")
	public void _createNewItem () {
    	T selectedItem = this.getSelectedItem();
		Events.echoEvent(CteEvents.ON_CREATE_ITEM, this, selectedItem);
	}
    
	@Listen("onClick = #btnReadItem")
	public void _readSelectedItem () {
    	T selectedItem = getSelectedItem();
    	if (selectedItem != null) {
   			Events.echoEvent(CteEvents.ON_READ_ITEM, this, selectedItem);
    	}
    	else {
			_openMsgWindow(buildMessageInfoSelect());				
    	}
	}

    @Listen("onClick = #btnUpdateItem")
	public void _updateSelectedItem () {
    	T selectedItem = getSelectedItem();
    	if (selectedItem != null) {
   			Events.echoEvent(CteEvents.ON_UPDATE_ITEM, this, selectedItem);
    	}
    	else {
			_openMsgWindow(buildMessageInfoSelect());				
    	}
	}

	protected void _openMsgWindow (Message msg) {
		MsgWindow wnd = new MsgWindow();
		wnd.setMessage(msg);
		wnd.setParent(ViewContext.getMainWindow());
		wnd.doHighlighted();
	}
	
	protected Message buildMessageInfoSelect () {
		Message result = null;
		result = new Message();
		result.setTitle("Información");
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

    
    
    @Listen("onClick = #btnDeleteItem")
	public void _deleteSelectedItem () {
    	T selectedItem = getSelectedItem();
    	if (selectedItem != null) {
   			Events.echoEvent(CteEvents.ON_DELETE_ITEM, this, selectedItem);
    	}
    	else {
			_openMsgWindow(buildMessageInfoSelect());				
    	}
	}

    public void afterCompose () {
    	super.afterCompose();
    	_updateValue();
    }

	public Long getNumElements () {
    	return (this.wList != null ? this.wList.getNumElements() : null);
    }

	public void setNumElements(Long newValue) {
		if (wList != null) {
			this.wList.setNumElements(newValue);
		}
	}

	public Long getStartPage () {
    	return (this.wList != null ? this.wList.getStartPage() : null);
	}
	
	public void setStartPage(Long newValue) {
		if (this.wList != null) {
			this.wList.setStartPage(newValue);
		}
	}
	
	public Long getEndPage () {
    	return (this.wList != null ? this.wList.getEndPage() : null);
	}
	
	public void setEndPage(Long newValue) {
		if (this.wList != null) {
			this.wList.setEndPage(newValue);
		}
	}
	
	public Object setAttribute (String name, Object value) {
		Object result = null;
		result = super.setAttribute(name, value);
		if (CteUi.ENTITY_CLASS.equals(name)) {
			_updateTitle();
		}
		return result;
	}
	
    protected void _updateTitle() {
		Class<?> entityClass = (Class<?>)getAttribute(CteUi.ENTITY_CLASS);
    	if (entityClass != null) {
    		String entityName = entityClass.getSimpleName();
//    		String title = _i18n(entityName + "_" + "finderTitle", true, "finderTitle");
    		String title = ViewContext.i18n(entityName + "_" + "finderTitle", false);
    		if (title == null) {
    			String pattern = ViewContext.i18n("finderTitle", false);
    			if (pattern != null) {
        			String entities = ViewContext.i18n(entityName + "_" + "optionLabel", false);
    				title = (entities != null ? pattern.replaceAll("\\{0\\}", entities) : null);
    			}
    		}
    		title = (title != null ? title.trim() : "");
    		title = (title.length() > 0 ? title : entityName);
        	this.setTitle(title);
    	}
	}

	@Override
	public T getPivotAction() {
		T result = null;
		result = this.getSelectedItem();
		return result;
	}
	
}
