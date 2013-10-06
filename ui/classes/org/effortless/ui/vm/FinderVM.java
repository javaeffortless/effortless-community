package org.effortless.ui.vm;

import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.core.MethodUtils;
import org.effortless.model.Entity;
import org.effortless.model.Filter;
import org.effortless.server.WebUtils;
import org.effortless.ui.Message;
import org.effortless.ui.ScreenInfo;
import org.zkoss.bind.annotation.BindingParam;


/*
  http://books.zkoss.org/wiki/ZK_Developer's_Reference/MVVM/Data_Binding/Binder
  
<window apply="org.zkoss.bind.BindComposer" viewModel="@id('vm') @init('foo.MyViewModel')"
    binder="@init(queueName='myqueue')">
 
 
<window apply="org.zkoss.bind.BindComposer" viewModel="@id('vm') @init('foo.MyViewModel')"
    binder="@init(value='xxx.MyBinder', queueScope='session')">  
  
   * 
 */
public class FinderVM extends ScreenInfo {

	public FinderVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateList();
		initiateSelectedItem();
		initiateScreenInfo();
		initiateFilter();
		initiateFilterSrc();
		initiateSelectedOver();
		initiatePageIndex();
		initiatePageSize();
		initiateNumElements();
		initiatePaginated();
		initiateEditorSrc();
		initiateItemClass();
	}

	protected List list;
	
	protected void initiateList () {
		this.list = null;
	}
	
	public List getList () {
		return this.list;
	}
	
	protected void setList (List newValue) {
		this.list = newValue;
	}

	protected Entity selectedItem;
	
	protected void initiateSelectedItem () {
		this.selectedItem = null;
	}
	
	public Entity getSelectedItem () {
		return this.selectedItem;
	}
	
	public void setSelectedItem (Entity newValue) {
		this.selectedItem = newValue;
	}
	
	protected ScreenInfo screenInfo;
	
	protected void initiateScreenInfo () {
		this.screenInfo = null;
	}
	
	public ScreenInfo getScreenInfo () {
		return this.screenInfo;
	}
	
	public void setScreenInfo (ScreenInfo newValue) {
		this.screenInfo = newValue;
	}
	
	protected Filter filter;
	
	protected void initiateFilter () {
		this.filter = null;
	}
	
	public Filter getFilter () {
		return this.filter;
	}
	
	public void setFilter (Filter newValue) {
		this.filter = newValue;
	}

	protected String filterSrc;
	
	protected void initiateFilterSrc () {
		this.filterSrc = null;
	}
	
	public String getFilterSrc () {
		return this.filterSrc;
	}
	
	public void setFilterSrc (String newValue) {
		this.filterSrc = newValue;
	}
	
	protected Object selectedOver;
	
	protected void initiateSelectedOver () {
		this.selectedOver = null;
	}
	
	public Object getSelectedOver () {
		return this.selectedOver;
	}
	
	public void setSelectedOver (Object newValue) {
		this.selectedOver = newValue;
	}
	
	protected Integer pageIndex;
	
	protected void initiatePageIndex () {
		this.pageIndex = null;
	}
	
	public Integer getPageIndex () {
		return this.pageIndex;
	}
	
	public void setPageIndex (Integer newValue) {
		this.pageIndex = newValue;
	}
	
	protected Integer pageSize;
	
	protected void initiatePageSize () {
		this.pageSize = null;
	}
	
	public Integer getPageSize () {
		return this.pageSize;
	}
	
	public void setPageSize (Integer newValue) {
		this.pageSize = newValue;
	}
	
	protected Integer totalPages;
	
	protected void initiateTotalPages () {
		this.totalPages = null;
	}
	
	public Integer getTotalPages () {
		return this.totalPages;
	}
	
	public void setTotalPages (Integer newValue) {
		this.totalPages = newValue;
	}
	
	protected Integer numElements;
	
	protected void initiateNumElements () {
		this.numElements = null;
	}
	
	public Integer getNumElements () {
		return this.numElements;
	}
	
	public void setNumElements (Integer newValue) {
		this.numElements = newValue;
	}
	
	protected Boolean paginated;

	protected void initiatePaginated () {
		this.paginated = null;
	}
	
	public Boolean getPaginated () {
		return this.paginated;
	}
	
	public void setPaginated (Boolean newValue) {
		this.paginated = newValue;
	}

	public Boolean getPagination () {
		Boolean result = Boolean.TRUE;
		if (this.paginated == null || this.paginated.booleanValue() == false) {
			result = Boolean.FALSE;
		}
		else {
			result = (this.numElements != null && this.numElements.intValue() > 0);
		}
		return result;
	}
	
	protected String editorSrc;
	
	public void initiateEditorSrc () {
		this.editorSrc = null;
	}
	
	public String getEditorSrc () {
		return this.editorSrc;
	}
	
	public void setEditorSrc (String newValue) {
		this.editorSrc = newValue;
	}
	
	protected Class<?> itemClass;
	
	protected void initiateItemClass () {
		this.itemClass = null;
	}
	
	public Class<?> getItemClass () {
		return this.itemClass;
	}
	
	public void setItemClass (Class<?> newValue) {
		this.itemClass = newValue;
	}
	
	@org.zkoss.bind.annotation.Init
	public void init (@org.zkoss.bind.annotation.ExecutionArgParam("args") java.util.Map args, @BindingParam("filterClass") String defaultFilterClass, @BindingParam("filterSrc") String defaultFilterSrc, @BindingParam("editorSrc") String defaultEditorSrc, @BindingParam("itemClass") String itemClass) {
		args = (args != null ? args : new java.util.HashMap());
		this.screenInfo = (ScreenInfo)args.get("screenInfo");

		this.filterSrc = (String)args.get("filterSrc");
		defaultFilterSrc = (defaultFilterSrc != null ? WebUtils.toScreen(defaultFilterSrc) : null);
		this.filterSrc = (this.filterSrc != null ? this.filterSrc : defaultFilterSrc);
		this.filterSrc = (this.filterSrc != null ? this.filterSrc : createDefaultFinderFilterZul());

		this.filter = (Filter)args.get("filter");
		this.filter = (this.filter == null && defaultFilterClass != null ? (Filter)ClassUtils.newInstanceRE(defaultFilterClass) : this.filter);
		this.filter = (this.filter != null ? this.filter : createDefaultFinderFilter());
		
		this.editorSrc = (String)args.get("editorSrc");
//		defaultEditorSrc = (defaultEditorSrc != null ? WebUtils.toScreen(defaultEditorSrc) : null);
		this.editorSrc = (this.editorSrc != null ? this.editorSrc : defaultEditorSrc);
		this.editorSrc = (this.editorSrc != null ? this.editorSrc : createDefaultEditorSrc());

		this.itemClass = (itemClass != null ? ClassUtils.loadClassRE(itemClass) : null);
		
		this.numElements = Integer.valueOf(0);
	}
	
	protected String createDefaultFinderFilterZul() {
		return null;
	}

	protected Filter createDefaultFinderFilter () {
		return null;
	}
	
	protected String createDefaultEditorSrc () {
		return null;
	}

	protected Object newItem () {
		return (this.itemClass != null ? ClassUtils.newInstanceRE(this.itemClass) : null);
	}
	
//	String appId = MySession.getDbId(get.getName());
//	String finderFilterZul = appId + File.separator + "resources" + File.separator + finderFilter.getNameWithoutPackage().toLowerCase() + ".zul";
	
	
//	mg = cg.addMethod("getMoreInfoScreen").setPublic(true).setReturnType(String.class);
//	String moreInfoScreen = entityName + "moreInfo";
//	mg.addReturn(mg.callStatic(WebUtils.class, "toScreenTimestamp", mg.cte(moreInfoScreen)));

	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange("selectedOver")
	public void changeOver (@BindingParam("item") Object item) {
		this.selectedOver = item;
	}
	
	@org.zkoss.bind.annotation.DependsOn("filter")
	public java.util.Map getFilterProperties () {
		return (this.filter != null ? this.filter.toMap() : null);
	}
	
	@org.zkoss.bind.annotation.DependsOn("list")
	public Integer getListSize () {
		return (this.list != null ? Integer.valueOf(this.list.size()) : null);
	}
	
	@org.zkoss.bind.annotation.DependsOn("pageIndex")
	public Integer getPageIndexStr () {
		return (this.pageIndex != null ? Integer.valueOf(this.pageIndex.intValue() + 1) : null);
	}
	
	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange({"list", "pageIndex", "pageSize", "totalPages", "numElements", "paginated", "pagination"})
	public void search () {
		this.filter.reset();
		this.list = this.filter.listPage();
		this.setPageIndex(this.filter.getPageIndex());
		this.setPageSize(this.filter.getPageSize());
		this.setTotalPages(this.filter.getTotalPages());
		this.setNumElements(this.filter.getSize());
		this.setPaginated(this.filter.getPaginated());
	}
	
	protected void _notifySearchChanges () {
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "list");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "pageIndex");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "pageSize");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "totalPages");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "numElements");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "paginated");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "pagination");
	}
	
	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange({"list", "filter", "pageIndex", "pageSize", "totalPages", "paginated", "pagination"})
	public void previousPage () {
		this.filter.previousPage();
		this.search();
	}

	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange({"list", "filter", "pageIndex", "pageSize", "totalPages", "paginated", "pagination"})
	public void nextPage () {
		this.filter.nextPage();
		this.search();
	}
	
	@org.zkoss.bind.annotation.Command
	public void createItem () {
		Object newItem = newItem();
		openEditor(newItem, "create", true, false);
	}
	
	@org.zkoss.bind.annotation.Command
	public void readItem () {
		if (this.selectedItem != null) {
			openEditor(this.selectedItem, "read", false, true);
		}
	}
	
	@org.zkoss.bind.annotation.Command
	public void updateItem () {
		if (this.selectedItem != null) {
//			Entity item = (Entity)this.selectedItem.reload();
//			openEditor(item, "update", true, false);
			openEditor(this.selectedItem, "update", true, false);
		}
	}
	
//	public String toString () {
//		return "toString soy vm";
//	}

	@org.zkoss.bind.annotation.Command
	public void deleteItem () {
		if (this.selectedItem != null) {
			Message msg = Message.createDelete();

			final FinderVM _this = this;
			Runnable processOk = new Runnable () {

				public void run() {
					_this.selectedItem.delete();
					_this.search();
					_this._notifySearchChanges();
				}
				
			};
//			msg.setProcessOk(processOk);
			openConfirm(msg);
		}
	}
	
	@org.zkoss.bind.annotation.Command
	public void runCustomActionItem (@BindingParam("action") String action) {
		if (this.selectedItem != null && action != null) {
			Message msg = Message.createInfo();

			final FinderVM _this = this;
			final String _action = action;
			Runnable processOk = new Runnable () {

				public void run() {
					MethodUtils.run(_this.selectedItem, _action);
					_this.search();
					_this._notifySearchChanges();
				}
				
			};
//			msg.setProcessOk(processOk);
			openConfirm(msg);
		}
	}
	
	
	protected void openConfirm (Message msg) {
		java.util.HashMap args = new java.util.HashMap();
		args.put("msg", msg);
		org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "openMessage", args);
	}
	
	@org.zkoss.bind.annotation.Command
	public void run_clone_OnItem () {
		if (this.selectedItem != null) {
			Object clone = this.selectedItem.clone();
			
			openEditor(clone, "create", true, false);
		}
	}
	
	protected void openEditor (Object item, String mode, boolean persist, boolean readonly) {
		java.util.Map args = new java.util.HashMap();
		args.put("content", this.editorSrc);
		
		java.util.HashMap params = new java.util.HashMap();
		params.put("item", item);
		params.put("mode", mode);
		params.put("persist", Boolean.valueOf(persist));
		params.put("readonly", Boolean.valueOf(readonly));
		args.put("args", params);
		org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);
	}
	
}
