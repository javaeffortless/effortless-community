package org.effortless.ui.renderers;

import java.util.List;

import org.effortless.ui.impl.Bindings;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.Field;
import org.effortless.ui.widgets.IconField;
import org.effortless.ui.widgets.ListField;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.util.ForEachStatus;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

public class BaseListFieldRenderer<T extends Object> {

    public void render (ListField<T> listField) {
    	Component wgt = listField.getWidgetRenderer();
		Components.removeAllChildren(wgt);
    	java.util.List<T> value = listField.getValue();
    	int length = (value != null ? value.size() : 0);
		final Template tm = listField.getTemplate("content");
    	if (length > 0 && tm != null) {

			Boolean _renderReadonly = (Boolean)listField.getAttribute(CteUi.RENDER_READONLY);
    		boolean renderReadonly = (_renderReadonly != null && _renderReadonly.booleanValue());

			String infoWindowId = (String)listField.getAttribute(CteUi.INFO_WINDOW_ID);
//			if (infoWindowId == null) {
//				infoWindowId = "/org.effortless.lotogest/resources/test.zul";
//			}
			boolean info = (infoWindowId != null && infoWindowId.length() > 0);
//			if (info) {
//				System.out.println(">>>>>>>>>>>>>> INFO = " + infoWindowId);
//			}
    		
	    	for (int i = 0; i < length; i++) {
	    		T item = value.get(i);
	    		Listitem newRow = new Listitem();
	    		newRow.setValue(item);
	    		newRow.setAttribute(CteUi.PIVOT_ACTION_OBJECT, item);
	    		
	    		Component _parent = new Div();
	    		
				Bindings.setupFormBinding(_parent, null);
	    		Bindings.preCompose(_parent, item);
	    		
				Component[] components = _buildComponents(tm, item, i, length, _parent, null);    			
	
				if (false && info && components != null) {
					IconField iconField = new IconField();
					_parent.appendChild(iconField);
	//				Bindings.relocateBinding(_parent, iconField);
	//				iconField.setValue(item);
					iconField.setValue("$");
					iconField.setInfoSrc(infoWindowId);
					List<Component> list = org.effortless.core.Collections.asList(components);
					list.add(iconField);
					components = list.toArray(new Component[0]);
				}
				
				int numColumns = (components != null ? components.length : 0);
	
	    		if (i == 0) {
	    			Listhead header = new Listhead();
	    			header.setColumnshide(false);
	    			header.setSizable(false);
	    			boolean anyColumn = false;
	    			for (int j = 0; j < numColumns; j++) {
	    				Component cmp = components[j];
	    				if (j == 0) {
	    					Listheader listheader = new Listheader();
	    					listheader.setWidth("24px");
	    					header.appendChild(listheader);
	    				}
    					Listheader listheader = null;
	    				if (cmp instanceof Field) {
	    					Component columnWidget = ((Field)cmp).getColumnWidget();
	    					listheader = new Listheader();
	    					listheader.appendChild(columnWidget);
	    					IconField iconField = null;
	    					try { iconField = (IconField)cmp; } catch (ClassCastException e) {}
	    					if (iconField != null) {
		    					listheader.setWidth("24px");
	    					}
	    					else {
		    					listheader.setWidth("auto");
	    					}
	    					header.appendChild(listheader);
	    					anyColumn = true;
	    				}
	    				else if (true) {
	    					listheader = new Listheader();
//	    					listheader.setLabel("" + (j + 1));
	    					listheader.setLabel(" ");
	    					header.appendChild(listheader);
	    					anyColumn = true;
	    				}
//	    				if (listheader != null && j < numColumns - 1) {
//	    					listheader.setHflex("min");
//	    				}
	    			}
	    			if (anyColumn) {
		    			if (false && info) {
	    					Listheader listheader = new Listheader();
	    					listheader.setWidth("32px");
	    					header.appendChild(listheader);
		    			}
	    				wgt.appendChild(header);
	    			}
	    		}

				Listcell colSelect = new Listcell();
				newRow.appendChild(colSelect);
	    		
				Bindings.postCompose(_parent);

				for (int j = 0; j < numColumns; j++) {
					Component cmp = components[j];
					
					if (renderReadonly && cmp instanceof Field) {
						Field field = (Field)cmp;
//						cmp = field.getReadonlyListWidget();
						field.setReadonly(Boolean.TRUE);
					}
					if (false && info && cmp instanceof IconField) {
						((IconField)cmp).setInfoSrc(infoWindowId);						
					}
					
					Listcell listcell = new Listcell();
					listcell.appendChild(cmp);
					newRow.appendChild(listcell);
				}
				
    			if (false && info) {
    				IconField iconField = new IconField();
//    				Bindings.relocateBinding(_parent, iconField);
//    				iconField.setValue(item);
    				iconField.setValue("$");
    				iconField.setInfoSrc(infoWindowId);
//    				InfoWindow infoWindow = (InfoWindow)Windows.openInfo(item, infoWindowId);
//    				infoWindow.setValue(item);
//    				iconField.setWindow(infoWindow);
					Listcell iconListcell = new Listcell();
					iconListcell.appendChild(iconField);
					newRow.appendChild(iconListcell);
    			}
				
	    		
				wgt.appendChild(newRow);
				
				Bindings.relocateBinding(_parent, newRow);
	    	}
//    		this.wgt.setEmptyMessage(null);
    	}
    }
	
    protected Component[] _buildComponents (final Template tm, final T data, final int index, final int size, Component parent, Component insertBefore) {
    	Component[] result = null;
		if (tm != null) {
			final VariableResolver variableResolver = new VariableResolver() {
				public Object resolveVariable(String name) {
					if ("item".equals(name)) {
						return data;
					} 
					else if ("forEachStatus".equals(name)) {
						return new ForEachStatus() {

							public ForEachStatus getPrevious() { return null; }

							public Object getEach() { return data; }

							public int getIndex() { return index; }

							public Integer getBegin() { return Integer.valueOf(0); }

							public Integer getEnd() { return Integer.valueOf(size); }
							
						};
					} 
					else {
						return null;
					}
				}
			};
			result = tm.create(parent, insertBefore, variableResolver, null);
		}
		return result;
    }
    
}
