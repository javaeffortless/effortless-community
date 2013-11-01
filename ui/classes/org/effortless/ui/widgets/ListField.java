package org.effortless.ui.widgets;

import java.util.List;

import org.effortless.core.ObjectUtils;
import org.effortless.ui.ViewContext;
//import org.effortless.ui.actions.PivotAction;
//import org.effortless.ui.impl.Bindings;
import org.effortless.ui.impl.CteEvents;
//import org.effortless.ui.impl.CteUi;
import org.effortless.ui.renderers.BaseListFieldRenderer;
//import org.effortless.ui.windows.InfoWindow;
//import org.effortless.ui.windows.Windows;
//import org.effortless.model.Entity;
//import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zk.ui.util.ForEachStatus;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.A;
//import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listhead;
//import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
//import org.zkoss.zul.ListitemRenderer;
	 
public class ListField<T extends Object> extends AbstractField<List<T>>/* implements PivotAction<T>*/ {
	    
	@Wire
	protected Listbox wgt;

	@Wire
	protected Label labelNumElementsText;
	
	@Wire
	protected Label labelNumElementsValue;
	
	@Wire
	protected Hlayout wPagination;
	
	@Wire
	protected A btnPreviousPage;
	
	@Wire
	protected Label wPageIndex;
	
	@Wire
	protected Label wPageSeparator;
	
	@Wire
	protected Label wTotalPages;
	
	@Wire
	protected A btnNextPage;
	
	    
    public ListField() {
    	super();
    }
    
	protected void initiate () {
		super.initiate();
		initiatePagination();
		initiateNumElements();
	}

	protected Boolean pagination;
	
	protected void initiatePagination () {
		this.pagination = Boolean.TRUE;
	}
	
	public Boolean getPagination () {
		return this.pagination;
	}
	
	public void setPagination (Boolean newValue) {
		Boolean oldValue = this.pagination;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.pagination = newValue;
			_refreshPagination();
		}
	}
	
	protected void _refreshPagination() {
		boolean visible = (this.pagination != null && this.pagination.booleanValue());
		this.wPagination.setVisible(visible);
	}

    @Listen("onClick = #btnPreviousPage")
	public void _onPreviousPage () {
    	Events.echoEvent(CteEvents.ON_PREVIOUS_PAGE, this, this.value);
    }
    
    protected void _refreshPageIndexStr () {
//    	this.wPageIndex.setValue(this.doGetPageIndexStr());
    }
    
    protected void _refreshTotalPages () {
//    	this.wTotalPages.setValue(this.doGetTotalPages());
    }

    @Listen("onClick = #btnNextPage")
	public void _onNextPage () {
    	Events.echoEvent(CteEvents.ON_NEXT_PAGE, this, this.value);
    }
    
    
    
	protected void initUi_LabelNumElementsText () {
		this.labelNumElementsText.setValue(ViewContext.i18n("finder_size_finderMessage"));
	}

	protected void initUi_BtnPreviousPage () {
		this.btnPreviousPage.setImage(_images("previousPage.png"));
	}
	
    protected void initUi_Separator () {
    	this.wPageSeparator.setValue(ViewContext.i18n("finder_sepPage_finderMessage"));
    }
	
    protected void initUi_BtnNextPage () {
    	this.btnNextPage.setImage(_images("nextPage.png"));
    }

//    public void setModel (List newValue) {
//    	super.setValue(newValue);
//    }
    

    protected void initUi () {
    	super.initUi();
    	initUi_LabelNumElementsText();
    	initUi_BtnPreviousPage();
    	initUi_Separator();
    	initUi_BtnNextPage();
    	
    	
////    	this.tag.setValue(this.getLabel());
//    	
//    	this.wgt.setValue(this.getValue());
//
//		Boolean readonly = getReadonly();
//		readonly = (readonly != null ? readonly : Boolean.FALSE);
////		this.wgt.setReadonly(readonly);
////		
////		Boolean disabled = readonly;
////		this.wgt.setDisabled(disabled);
//		
//		this.wgt.setButtonVisible(!readonly.booleanValue());
    }

    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	boolean result = false;
    	result = this.wgt.insertBefore(newChild, refChild);
    	return result;
    }

    public Template setTemplate(String name, Template tm) {
    	Template result = null;
    	result = super.setTemplate(name, tm);
    	initUi_Value();
    	return result;
    }
    
    public void render () {
    	initUi_Value();
    }
    
    protected void initUi_Value () {
    	super.initUi_Value();
    	int length = (this.value != null ? this.value.size() : 0);
    	if (true) {
//    		this.wgt.setSizedByContent(true);
    		BaseListFieldRenderer<T> renderer = new BaseListFieldRenderer<T>();
    		renderer.render(this);
    	} 
//    	else {
//			final Template tm = this.getTemplate("content");
//	    	if (length > 0 && tm != null) {
//	    		Components.removeAllChildren(this.wgt);
//	
//				Boolean _renderReadonly = (Boolean)this.getAttribute(CteUi.RENDER_READONLY);
//	    		boolean renderReadonly = (_renderReadonly != null && _renderReadonly.booleanValue());
//	
//				String infoWindowId = (String)this.getAttribute(CteUi.INFO_WINDOW_ID);
//	//			if (infoWindowId == null) {
//	//				infoWindowId = "/org.effortless.lotogest/resources/test.zul";
//	//			}
//				boolean info = (infoWindowId != null && infoWindowId.length() > 0);
//				if (info) {
//					System.out.println(">>>>>>>>>>>>>> INFO = " + infoWindowId);
//				}
//	    		
//		    	for (int i = 0; i < length; i++) {
//		    		T item = this.value.get(i);
//		    		Listitem newRow = new Listitem();
//		    		newRow.setValue(item);
//		    		
//		    		Component _parent = new Div();
//		    		
//					Bindings.setupFormBinding(_parent, null);
//		    		Bindings.preCompose(_parent, item);
//		    		
//					Component[] components = _buildComponents(tm, item, i, length, _parent, null);    			
//		
//					if (info && components != null) {
//						IconField iconField = new IconField();
//						_parent.appendChild(iconField);
//		//				Bindings.relocateBinding(_parent, iconField);
//		//				iconField.setValue(item);
//						iconField.setValue("$");
//						iconField.setInfoSrc(infoWindowId);
//						List<Component> list = org.effortless.core.Collections.asList(components);
//						list.add(iconField);
//						components = list.toArray(new Component[0]);
//					}
//					
//					int numColumns = (components != null ? components.length : 0);
//		
//		    		if (i == 0) {
//		    			Listhead header = new Listhead();
//		    			header.setSizable(true);
//		    			boolean anyColumn = false;
//		    			for (int j = 0; j < numColumns; j++) {
//		    				Component cmp = components[j];
//		    				if (j == 0) {
//		    					Listheader listheader = new Listheader();
//		    					listheader.setWidth("32px");
//		    					header.appendChild(listheader);
//		    				}
//		    				if (cmp instanceof Field) {
//		    					Component columnWidget = ((Field)cmp).getColumnWidget();
//		    					Listheader listheader = new Listheader();
//		    					listheader.appendChild(columnWidget);
//		    					IconField iconField = null;
//		    					try { iconField = (IconField)cmp; } catch (ClassCastException e) {}
//		    					if (iconField != null) {
//			    					listheader.setWidth("32px");
//		    					}
//		    					header.appendChild(listheader);
//		    					anyColumn = true;
//		    				}
//		    				else if (true) {
//		    					Listheader listheader = new Listheader();
//	//	    					listheader.setLabel("" + (j + 1));
//		    					listheader.setLabel(" ");
//		    					header.appendChild(listheader);
//		    					anyColumn = true;
//		    				}
//		    			}
//		    			if (anyColumn) {
//			    			if (false && info) {
//		    					Listheader listheader = new Listheader();
//		    					listheader.setWidth("32px");
//		    					header.appendChild(listheader);
//			    			}
//		    				this.wgt.appendChild(header);
//		    			}
//		    		}
//	
//					Listcell colSelect = new Listcell();
//					newRow.appendChild(colSelect);
//		    		
//					Bindings.postCompose(_parent);
//	
//					for (int j = 0; j < numColumns; j++) {
//						Component cmp = components[j];
//						
//						if (renderReadonly && cmp instanceof Field) {
//							Field field = (Field)cmp;
//	//						cmp = field.getReadonlyListWidget();
//							field.setReadonly(Boolean.TRUE);
//						}
//						
//						Listcell listcell = new Listcell();
//						listcell.appendChild(cmp);
//						newRow.appendChild(listcell);
//					}
//					
//	    			if (false && info) {
//	    				IconField iconField = new IconField();
//	//    				Bindings.relocateBinding(_parent, iconField);
//	//    				iconField.setValue(item);
//	    				iconField.setValue("$");
//	    				iconField.setInfoSrc(infoWindowId);
//	//    				InfoWindow infoWindow = (InfoWindow)Windows.openInfo(item, infoWindowId);
//	//    				infoWindow.setValue(item);
//	//    				iconField.setWindow(infoWindow);
//						Listcell iconListcell = new Listcell();
//						iconListcell.appendChild(iconField);
//						newRow.appendChild(iconListcell);
//	    			}
//					
//		    		
//					this.wgt.appendChild(newRow);
//					
//					Bindings.relocateBinding(_parent, newRow);
//		    	}
//	//    		this.wgt.setEmptyMessage(null);
//	    	}
//    	}
    	
	    if (length <= 0) {
	    	this.wgt.setEmptyMessage("No elements");
	    }
		this.wPagination.setVisible((length > 0));
    }

//    protected Component[] _buildComponents (final Template tm, final T data, final int index, final int size, Component parent, Component insertBefore) {
//    	Component[] result = null;
//		if (tm != null) {
//			final VariableResolver variableResolver = new VariableResolver() {
//				public Object resolveVariable(String name) {
//					if ("item".equals(name)) {
//						return data;
//					} 
//					else if ("forEachStatus".equals(name)) {
//						return new ForEachStatus() {
//
//							public ForEachStatus getPrevious() { return null; }
//
//							public Object getEach() { return data; }
//
//							public int getIndex() { return index; }
//
//							public Integer getBegin() { return Integer.valueOf(0); }
//
//							public Integer getEnd() { return Integer.valueOf(size); }
//							
//						};
//					} 
//					else {
//						return null;
//					}
//				}
//			};
//			result = tm.create(parent, insertBefore, variableResolver, null);
//		}
//		return result;
//    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
    public T getSelectedItem () {
		T result = null;
    	Listitem listitem = (this.wgt != null && this.wgt.getSelectedCount() == 1 ? this.wgt.getSelectedItem() : null);
    	result = (listitem != null ? (T)listitem.getValue() : null);
    	return result;
    }
    
	public void setSelectedItem (T newValue) {
		int index = 0;//newValue
		this.wgt.setSelectedIndex(index);
	}

	public void repaintValue() {
		initUi_Value();
	}

	public Component getWidgetRenderer() {
		return this.wgt;
	}

	public Long numElements;
	
	protected void initiateNumElements () {
		this.numElements = null;
	}
	
	public Long getNumElements () {
		return this.numElements;
	}
	
	public void setNumElements(Long newValue) {
		Long oldValue = this.numElements;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.numElements = newValue;
			_refreshNumElements();
		}
	}

    protected void _refreshNumElements () {
    	String value = (this.numElements != null ? "" + this.numElements.longValue() : "0");
    	this.labelNumElementsValue.setValue(value);
    }
    

    
	public Long startPage;
	
	protected void initiateStartPage () {
		this.startPage = null;
	}
	
	public Long getStartPage () {
		return this.startPage;
	}
	
	public void setStartPage(Long newValue) {
		Long oldValue = this.startPage;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.startPage = newValue;
			_refreshStartPage();
		}
	}

    protected void _refreshStartPage () {
    	_refreshStartEndPage();
    }
    
    
	public Long endPage;
	
	protected void initiateEndPage () {
		this.endPage = null;
	}
	
	public Long getEndPage () {
		return this.endPage;
	}
	
	public void setEndPage(Long newValue) {
		Long oldValue = this.endPage;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.endPage = newValue;
			_refreshEndPage();
		}
	}

    protected void _refreshEndPage () {
    	_refreshStartEndPage();
    }
    
    protected void _refreshStartEndPage () {
    	Long startPage = getStartPage();
    	Long endPage = getEndPage();
    	
    	String startPageTxt = (startPage != null ? "" + (startPage.longValue()) : "");
    	String endPageTxt = (endPage != null ? "" + (endPage.longValue()) : "");
    	
    	this.wPageIndex.setValue(startPageTxt);
    	this.wTotalPages.setValue(endPageTxt);

//    	String msg = _i18n("finder_navigation_msgPage", )
//    	this.wPageSeparator.setValue(_i18n("finder_sepPage_finderMessage"));
    	
//    	String value = (this.numElements != null ? "" + this.numElements.longValue() : "0");
//    	this.wPageIndexlabelNumElementsValue.setValue(value);
    }

//	@Override
//	public T getPivotAction() {
//		T result = null;
//		result = this.getSelectedItem();
//		return result;
//	}

}
