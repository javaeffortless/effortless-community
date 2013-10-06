package org.effortless.ui.widgets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.core.FileUtils;
import org.effortless.core.MethodUtils;
import org.effortless.core.ModelException;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.model.Entity;
import org.effortless.model.FileEntity;
import org.effortless.model.Filter;
import org.effortless.model.Referenciable;
import org.effortless.server.WebUtils;
import org.effortless.ui.UiApplication;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.windows.AbstractFieldWindow;
import org.effortless.ui.windows.FieldWindow;
//import org.effortless.widgets.RefComboitemRenderer;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
	 
public class EnumField<T extends Enum<T>> extends AbstractField<T> {
	    
	@Wire
	protected Combobox wgt;
	 
    public EnumField() {
    	super();
//    	initiate();
//    	String zulName = "~." + File.separator + getClass().getName().replaceAll("\\.", File.separator) + ".zul";
//        Executions.createComponents(zulName, this, null);
//        Selectors.wireComponents(this, this, false);
//        Selectors.wireEventListeners(this, this);
//		initUi();
    }

	protected void initiate () {
		super.initiate();
		initiateValues();
		
		initiateType();
	}
	
    
	@ComponentAnnotation("@ZKBIND(ACCESS=load, SAVE_EVENT=onChange)")
	public Boolean isEmpty () {
		return Boolean.valueOf((this.value != null));
	}
	
//    @Listen("onSelect = #wgt")
    @Listen("onChange = #wgt")
	public void _onChangeSelect () {
    	Comboitem selectedItem = this.wgt.getSelectedItem();
    	T value = (selectedItem != null ? (T)selectedItem.getValue() : null);
    	setupValueLabel(value);
    	setValue(value, false);
	}
    
    protected void setupValueLabel (Object value) {
    	Referenciable referenciable = null;
    	try { referenciable = (Referenciable)value;	} catch (ClassCastException e) {}
    	String label = (referenciable != null ? referenciable.toLabel() : (value != null ? value.toString() : ""));
    	this.wgt.setValue(label);
    }

    

    @Listen("onOpen = #wgt")
	public void _onOpen () {
    	setupDefaultValues();
	}
    
//    public void afterCompose () {
//    	super.afterCompose();
//    	setupDefaultValues();
//    }
	
	protected void setupDefaultValues() {
    	List<T> values = this.getValues();
    	if (values == null || values.size() <= 0) {
    		values = loadDefaultValues();
    		setValues(values);
    		if (values != null && values.size() > 0) {
    			this.wgt.open();
    		}
    	}
	}


	protected List<T> loadDefaultValues() {
		List<T> result = null;
		String propertyName = _getBindProperty("value");
		result = (List<T>)UiApplication.loadEnumDefaultValues(this, propertyName);
		return result;
	}


	public static class EnumComboitemRenderer extends Object implements ComboitemRenderer<Object> {

		@Override
		public void render(Comboitem item, Object value, int index)
				throws Exception {
			Referenciable entity = null;
			try { entity = (Referenciable)value; } catch (ClassCastException e) {}
			if (entity != null) {
				Referenciable referenciable = (Referenciable)entity;
				item.setLabel(referenciable.toLabel());
				item.setImage(referenciable.toImage());
				item.setTooltiptext(referenciable.toDescription());
				item.setValue(value);
			}
			else if (value != null) {
				String label = value.toString();
				item.setLabel(label);
				item.setImage(null);
				item.setTooltiptext(null);
				item.setValue(value);
			}
		}
		
	}
	
	public static final ComboitemRenderer DEFAULT = new EnumComboitemRenderer();

    protected void initUi () {
    	_updateModel();
//		this.wgt.
		
		this.wgt.setItemRenderer(DEFAULT);
		
		
//		this.wgt.setRawValue(this.value);
//		Comboitem selectedItem = loadSelectedItem();
//		this.wgt.setSelectedItem(selectedItem);
//		this.wgt.setSelectedItem(item)
//				 selectedItem="@bind(editor.item.license)" 

		Boolean readonly = getReadonly();
		readonly = (readonly != null ? readonly : Boolean.FALSE);
		this.wgt.setReadonly(readonly);
		
		Boolean buttonVisible = Boolean.valueOf(readonly == null || readonly.booleanValue() == false);
		this.wgt.setButtonVisible(buttonVisible);
		
		Boolean disabled = readonly;
		this.wgt.setDisabled(disabled);
    }
    
	protected List<T> values;
	
	protected void initiateValues () {
		this.values = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public List<T> getValues () {
		return this.values;
	}
	
	protected void _updateModel () {
		ListModelList model = null;
//		List list = (this.filter != null ? this.filter.listPage() : null);
		List<T> values = this.values;
		model = (values != null ? new ListModelList(values, true) : null);
		this.wgt.setModel(model);
	}
	
	public void setValues (List<T> newValue) {
		if (!ObjectUtils.equals(this.values, newValue)) {
			this.values = newValue;
			_notifyChange("values");
			_updateModel();
			initUi();
		}
	}
	
	public void setValues (String newValue) {
		boolean binded = _applyBinding("values", newValue);
	}
	
	protected String type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getType () {
		return this.type;
	}
	
	public void setType (String newValue) {
		if (!ObjectUtils.equals(this.type, newValue)) {
			this.type = newValue;
			setupFromType();
			_notifyChange("type");
//			initUi();
		}
	}
	
	protected void setupFromType () {
//		setType(type);
		
//		filter = (filter != null ? filter : (Filter<?>)ClassUtils.newInstanceRE(type + "FinderFilter"));
		
//		setFilter(filter);
	}
	
	protected void _refreshValue () {
	}
	
	protected void initUi_Value () {
//		ListModel model = this.wgt.getModel();
//		model.
//		List<?> values = this.getValues();
		if (/*values != null && */this.value != null) {
			this.setupValueLabel(this.value);
//			int index = values.indexOf(this.value);
//			this.wgt.setValue(this.value.toString());
//			Comboitem selectedItem = this.wgt.getItemAtIndex(index);
//			this.wgt.setSelectedItem(selectedItem);
		}
		else {
			this.wgt.setValue(null);
		}
//		this.wgt.setSelectedItem(item)
	}
	
}	
