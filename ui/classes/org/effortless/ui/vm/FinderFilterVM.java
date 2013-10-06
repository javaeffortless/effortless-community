package org.effortless.ui.vm;

import java.util.Map;

import org.effortless.model.Filter;
import org.effortless.ui.ScreenInfo;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

public class FinderFilterVM extends ScreenInfo {

	public FinderFilterVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateFilter();
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
	
	public Map<String, Object> getFilterProperties () {
		return (this.filter != null ? this.filter.toMap() : null);
	}
	
	@Init
	public void init (@ExecutionArgParam("filter") Filter filter) {
		this.filter = (filter != null ? filter : createDefaultFinderFilter());
	}

	protected Filter createDefaultFinderFilter() {
		return null;
	}
	
//	mg.add(mg.assign(mg.field("filter"), mg.triple(mg.notNull(mg.field("filter")), mg.field("filter"), mg.callConstructor(finderFilter.getClassNode()))));
	
}
