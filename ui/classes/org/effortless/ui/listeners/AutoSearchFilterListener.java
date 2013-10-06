package org.effortless.ui.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.effortless.model.Filter;
import org.effortless.ui.windows.FinderWindow;

public class AutoSearchFilterListener extends Object implements PropertyChangeListener {

	protected AutoSearchFilterListener () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public AutoSearchFilterListener (FinderWindow finderWindow) {
		this();
		this.finderWindow = finderWindow;
	}
	
	protected FinderWindow finderWindow;
		
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = (evt != null ? evt.getPropertyName() : null);
		Filter filter = (evt != null ? (Filter)evt.getSource() : null);
		boolean flag = false;
//		flag = flag || "paginated".equals(propertyName);
		flag = flag || "pageIndex".equals(propertyName);
//		flag = flag || "pageSize".equals(propertyName);
		if (!flag) {
			filter.setPageIndex(Integer.valueOf(0));
		}
		
		Long numElements = Long.valueOf(filter.size());
		java.util.List list = filter.listPage();
		this.finderWindow.setNumElements(numElements);
		
		Integer totalPages = filter.getTotalPages();
		this.finderWindow.setStartPage(Long.valueOf(filter.getPageIndex() != null ? filter.getPageIndex().intValue() + 1 : 0));
		this.finderWindow.setEndPage(Long.valueOf(totalPages));
		
		
		this.finderWindow.setValue(list);
		
		this.finderWindow.repaintValue();
	}
	
}
