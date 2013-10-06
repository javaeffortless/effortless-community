package org.effortless.ui.vm.fields;

import java.util.ArrayList;
import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.model.Filter;
import org.effortless.server.WebUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class RefFieldVM extends Object {

	public RefFieldVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateFilter();
		
		initiateType();
		initiateFinderSrc();
		initiateFilterClass();
		initiateFilterSrc();
		initiateEditorSrc();
	}
	
//	public void init (@org.zkoss.bind.annotation.ExecutionArgParam("args") java.util.Map args, 
	//@init('org.effortless.ui.RefFieldVM', type='org.effortless.icondb.icons.Icon', filter='', )
	@Init
	public void init(@BindingParam("filter") Filter<?> filter, @BindingParam("type") String type, @BindingParam("filterClass") String filterClass, @BindingParam("finderSrc") String finderSrc, @BindingParam("filterSrc") String filterSrc, @BindingParam("editorSrc") String editorSrc) {
		setType(type);
		
		filter = (filter != null ? filter : (Filter<?>)ClassUtils.newInstanceRE(type + "FinderFilter"));
		
		setFilter(filter);

		setFilterClass(filterClass);
		
		
		String className = type;
		finderSrc = (finderSrc != null ? finderSrc : WebUtils.toScreen(className + "_finder"));
		filterSrc = (filterSrc != null ? filterSrc : WebUtils.toScreen(className + "finderfilter"));
		editorSrc = (editorSrc != null ? editorSrc : WebUtils.toScreen(className + "_editor"));
		
		setFinderSrc(finderSrc);
		setFilterSrc(filterSrc);
		setEditorSrc(editorSrc);
		
		
//		Class typeClass = ClassUtils.loadClassRE(type);
//		Object[] values = type.getEnumConstants();
//		List list = new ArrayList();
//		for (Object value : values) {
//			list.add(value);
//		}
//		this.values = list;
	}
	
	protected Filter<?> filter;
	
	protected void initiateFilter () {
		this.filter = null;
	}
	
	public Filter<?> getFilter () {
		return this.filter;
	}
	
	public void setFilter (Filter<?> newValue) {
		this.filter = newValue;
	}
	
	protected String type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	public String getType () {
		return this.type;
	}
	
	public void setType (String newValue) {
		this.type = newValue;
	}
	
	protected String finderSrc;
	
	protected void initiateFinderSrc () {
		this.finderSrc = null;
	}
	
	public String getFinderSrc () {
		return this.finderSrc;
	}
	
	public void setFinderSrc (String newValue) {
		this.finderSrc = newValue;
	}
	
	protected String filterClass;
	
	protected void initiateFilterClass () {
		this.filterClass = null;
	}
	
	public String getFilterClass () {
		return this.filterClass;
	}
	
	public void setFilterClass (String newValue) {
		this.filterClass = newValue;
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
	
	protected String editorSrc;
	
	protected void initiateEditorSrc () {
		this.editorSrc = null;
	}
	
	public String getEditorSrc () {
		return this.editorSrc;
	}
	
	public void setEditorSrc (String newValue) {
		this.editorSrc = newValue;
	}
	
	public List getValues () {
		List result = null;
		result = (this.filter != null ? this.filter.listPage() : null);
		return result;
	}
	
	@Command
	public void selectMore () {
		System.out.println("SELECT MORE");
	}

	
	
}
