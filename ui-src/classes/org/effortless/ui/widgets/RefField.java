package org.effortless.ui.widgets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.core.FileUtils;
import org.effortless.core.ModelException;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.model.Entity;
import org.effortless.model.FileEntity;
import org.effortless.model.Filter;
import org.effortless.model.Referenciable;
import org.effortless.server.WebUtils;
import org.effortless.ui.UiApplication;
import org.effortless.ui.model.RefFieldCtrl;
import org.effortless.ui.model.RefFilterListModelList;
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
	 
public class RefField<T extends Object> extends AbstractField<T> {
	    
	@Wire
	protected Combobox wgt;
	 
    public RefField() {
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
		initiateFinderSrc();
		initiateFilterClass();
		initiateFilterSrc();
		initiateEditorSrc();
	}
	
    
//	protected Entity value;
//	
//	protected void initiateValue () {
//		this.value = null;
//	}
//	
//	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
//	public Entity getValue () {
//		return this.value;
//	}
//	
//	public void setValue (Entity newValue) {
//		if (!ObjectUtils.equals(this.value, newValue)) {
//			this.value = newValue;
//			notifyChange();
//			initUi();
//		}
//	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=load, SAVE_EVENT=onChange)")
	public Boolean isEmpty () {
		return Boolean.valueOf((this.value != null));
	}

    @Listen("onOpen = #wgt")
    public void _onOpen () {
    	setupDefaults();
    }

    protected void setupValueLabel (Object value) {
    	Referenciable referenciable = null;
    	try { referenciable = (Referenciable)value;	} catch (ClassCastException e) {}
    	String label = (referenciable != null ? referenciable.toLabel() : (value != null ? value.toString() : ""));
    	this.wgt.setValue(label);
    }

	public void refreshValue () {
		setupValueLabel(this.getValue());
	}
    
    
//    @Listen("onSelect = #wgt")
    @Listen("onChange = #wgt")
	public void _onChangeSelect () {
    	Comboitem selectedItem = this.wgt.getSelectedItem();
    	Object value = (selectedItem != null ? selectedItem.getValue() : null);
    	setupValueLabel(value);
    	ListModel listModel = this.wgt.getModel();
    	RefFieldCtrl ctrl = null;
    	try { ctrl = (RefFieldCtrl)listModel; } catch (ClassCastException e) {}
    	if (ctrl != null) {
        	ctrl.process(this, value);
    	}
    	else if (true) {
    		setValue((T)value, false);
    	}
//    	if (process) {//RefFilterListModelList.FINDER != value) {
//    		setValue((T)value);
//    	}
//    	else {
//    		System.out.println(">>>>>>>>>> FINDER");
//    	}

//		if (this.value != null) {
//			try {
//				File fileContent = this.value.getContent();
//				AMedia media = new AMedia(this.value.getName(), this.value.getFormat(), this.value.getContentType(), fileContent, true);
////				String prefix = (this.type != null ? StringUtils.lastPart(this.type, ".") : null);
////				File newFile = FileUtils.renameToTempDir(fileContent, this.value.getName(), prefix);
////				Filedownload.save(newFile, this.value.getContentType());
//				Filedownload.save(media);
//			} catch (FileNotFoundException e) {
//				throw new ModelException(e);
//			}
//		}
	}

//	public void setValue (T newValue, boolean notify) {
//		super.setValue(newValue, notify);
//	}
    
//	protected Boolean readonly;
//	
//	protected void initiateReadonly () {
//		this.readonly = null;
//	}
//	
//	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
//	public Boolean isReadonly () {
//		return this.readonly;
//	}
//	
//	public void setReadonly (Boolean newValue) {
//		if (!ObjectUtils.equals(this.readonly, newValue)) {
//			this.readonly = newValue;
//			notifyChange();
//			initUi();
//		}
//	}
	
//    protected static void _render(Comboitem item, Object value, int index) throws Exception {
//		Referenciable entity = null;
//		try {
//			entity = (Referenciable)value;
//		}
//		catch (ClassCastException e) {
//		}
//		if (entity != null) {
//			item.setLabel(entity.toLabel());
//			item.setImage(entity.toImage());
//			item.setTooltiptext(entity.toDescription());
//			item.setValue(value);
//		}
//		else if (value != null) {
//			String label = value.toString();
//			item.setLabel(label);
//			item.setImage(null);
//			item.setTooltiptext(null);
//			item.setValue(value);
//		}
//    }
    
	public static class RefComboitemRenderer extends Object implements ComboitemRenderer<Object> {

		@Override
		public void render(Comboitem item, Object value, int index)
				throws Exception {
			Referenciable entity = null;
			try {
				entity = (Referenciable)value;
			}
			catch (ClassCastException e) {
			}
			if (entity != null) {
				item.setLabel(entity.toLabel());
				item.setImage(entity.toImage());
				item.setTooltiptext(entity.toDescription());
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
	
	public static final ComboitemRenderer DEFAULT = new RefComboitemRenderer();

//	protected Comboitem loadSelectedItem () {
//		Comboitem result = null;
//		List<Component> items = this.wgt.getChildren();
//		for (Component item : items) {
//			Object value = ((Comboitem)item).getValue();
//			if (this.value != null && this.value == value) {
//				result = (Comboitem)item;
//				break;
//			}
//		}
//		return result;
//	}
	
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
    
//    @Listen("onUpload = #btn")
//	public void upload(UploadEvent event) {
//		org.zkoss.util.media.Media media = event.getMedia();
//		if (media != null) {
//			this.value = (this.value != null ? this.value : newFile());
////			this.value.setContentType(media.getContentType());
////			this.value.setCreationDate(new java.util.Date());
////			this.value.setFrameRate(newValue);
////			this.value
//			this.value.setContent(media.getStreamData(), media.getName());
//		}
//		else {
//			this.value = null;
//		}
//		Events.postEvent("onChange", this, null);
//		setValue(this.value);
//		initUi();
////		if (media instanceof org.zkoss.image.Image) {
////			org.zkoss.image.Image img = (org.zkoss.image.Image) media;
////			if (img.getWidth() > img.getHeight()){
////				if (img.getHeight() > 300) {
//////					pics.setHeight("300px");
//////					pics.setWidth(img.getWidth() * 300 / img.getHeight() + "px");
////				}
////			}
////			if (img.getHeight() > img.getWidth()){
////				if (img.getWidth() > 400) {
//////					pics.setWidth("400px");
//////					pics.setHeight(img.getHeight() * 400 / img.getWidth() + "px");
////				}
////			}
//////			pics.setContent(img);
////		} else {
////			Messagebox.show("Not an image: "+media, "Error", Messagebox.OK, Messagebox.ERROR);
//////			break; //not to show too many errors
////		}
//	}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	protected List<T> values;
	
	protected void initiateValues () {
		this.values = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public List<T> getValues () {
		return this.values;
	}

	public void resetValues () {
		_updateModel();
    	setupValueLabel(getValue());
		this.wgt.open();
	}
	
	protected void _updateModel () {
		ListModel model = createListModel();
		this.wgt.setModel(model);
	}
	
	protected ListModel createListModel () {
		ListModel result = null;
		Filter filter = null;
		try { filter = (Filter)this.values; } catch (ClassCastException e) {}
		if (filter != null) {
			result = new RefFilterListModelList(filter);
		}
		else {
			if (this.values != null) {
				result = new ListModelList(this.values);
			}
			else {
				result = new ListModelList(new java.util.ArrayList());
			}
		}
		return result;
	}
	
	
//	@ComponentAnnotation("@ZKBIND(ACCESS=load, SAVE_EVENT=onChange)")
//	public ListModel getValues () {
//		ListModelList result = null;
//		List list = (this.filter != null ? this.filter.listPage() : null);
//		result = (list != null ? new ListModelList(list, true) : null);
//		return result;
//	}
	
	
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

		setFilterClass(filterClass);
		
		
		String className = type;
		finderSrc = (finderSrc != null ? finderSrc : WebUtils.toScreen(className + "_finder"));
		filterSrc = (filterSrc != null ? filterSrc : WebUtils.toScreen(className + "finderfilter"));
		editorSrc = (editorSrc != null ? editorSrc : WebUtils.toScreen(className + "_editor"));
		
		setFinderSrc(finderSrc);
		setFilterSrc(filterSrc);
		setEditorSrc(editorSrc);
	}
	
	protected String finderSrc;
	
	protected void initiateFinderSrc () {
		this.finderSrc = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getFinderSrc () {
		return this.finderSrc;
	}
	
	public void setFinderSrc (String newValue) {
		if (!ObjectUtils.equals(this.finderSrc, newValue)) {
			this.finderSrc = newValue;
			_notifyChange("finderSrc");
			initUi();
		}
	}
	
	protected String filterClass;
	
	protected void initiateFilterClass () {
		this.filterClass = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getFilterClass () {
		return this.filterClass;
	}
	
	public void setFilterClass (String newValue) {
		if (!ObjectUtils.equals(this.filterClass, newValue)) {
			this.filterClass = newValue;
			_notifyChange("filterClass");
			initUi();
		}
	}
	
	protected String filterSrc;
	
	protected void initiateFilterSrc () {
		this.filterSrc = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getFilterSrc () {
		return this.filterSrc;
	}
	
	public void setFilterSrc (String newValue) {
		if (!ObjectUtils.equals(this.filterSrc, newValue)) {
			this.filterSrc = newValue;
			_notifyChange("filterSrc");
			initUi();
		}
	}
	
	protected String editorSrc;
	
	protected void initiateEditorSrc () {
		this.editorSrc = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getEditorSrc () {
		return this.editorSrc;
	}
	
	public void setEditorSrc (String newValue) {
		if (!ObjectUtils.equals(this.editorSrc, newValue)) {
			this.editorSrc = newValue;
			_notifyChange("editorSrc");
			initUi();
		}
	}
	
//	protected void notifyChange () {
//		Events.postEvent("onChange", this, null);
//	}
	
//	@Command
//	public void selectMore () {
//		System.out.println("SELECT MORE");
//	}

	
	
    
    
	
	
	protected void _refreshValue () {
	}
	
	protected void initUi_Value () {
//		ListModel model = this.wgt.getModel();
//		model.
//		List<?> values = this.getValues();
		if (/*values != null && */this.value != null) {
			this.setupValueLabel(this.value);
//			
//			
////			int index = values.indexOf(this.value);
//			this.wgt.setValue(this.value.toString());
////			Comboitem selectedItem = this.wgt.getItemAtIndex(index);
////			this.wgt.setSelectedItem(selectedItem);
		}
		else {
			this.wgt.setValue(null);
		}
//		this.wgt.setSelectedItem(item)
	}
	
	
    
    
	protected void setupDefaults() {
		setupDefaultFinderSrc();
		setupDefaultFilterClass();
		setupDefaultFilterSrc();
		setupDefaultEditorSrc();
		setupDefaultValues();
	}
    
	protected void setupDefaultFinderSrc() {
		String value = getFinderSrc();
		if (value == null) {
			String propertyName = _getBindProperty("value");
			value = UiApplication.loadFinderZul(this, propertyName);
			setFinderSrc(value);
		}
	}

	protected void setupDefaultFilterClass() {
		String value = getFilterClass();
		if (value == null) {
			String propertyName = _getBindProperty("value");
			Class<?> clazz = UiApplication.getFinderFilterClass(this, propertyName);
			value = (clazz != null ? clazz.getName() : null);
			setFilterClass(value);
		}
	}

	protected void setupDefaultFilterSrc() {
		String value = getFilterSrc();
		if (value == null) {
			String propertyName = _getBindProperty("value");
			value = UiApplication.loadFinderFilterZul(this, propertyName);
			setFilterSrc(value);
		}
	}

	protected void setupDefaultEditorSrc() {
		String value = getEditorSrc();
		if (value == null) {
			String propertyName = _getBindProperty("value");
			value = UiApplication.loadEditorZul(this, propertyName);
			setEditorSrc(value);
		}
	}

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
		result = (List<T>)UiApplication.loadRefDefaultValues(this, propertyName);
		return result;
	}

    
    
    
}	
