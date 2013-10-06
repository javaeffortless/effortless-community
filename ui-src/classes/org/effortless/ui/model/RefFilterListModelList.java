package org.effortless.ui.model;

import java.util.List;

import org.effortless.model.Filter;
import org.effortless.ui.ViewContext;
import org.effortless.ui.impl.CteEvents;
import org.effortless.ui.widgets.RefField;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.AbstractListModel;

public class RefFilterListModelList extends AbstractListModel<Object> implements RefFieldCtrl {

	protected RefFilterListModelList() {
		super();
	}

	public RefFilterListModelList (Filter<Object> filter) {
		super();
		this.filter = filter;
	}
	
	
	protected java.util.List<Object> page;
	protected Filter<Object> filter;

	public Object getElementAt(int idx) {
		Object result = null;
		int size = getSize();
		boolean paginated = isPaginated();
		if (false) {
			if (idx == 0 && paginated && !isFirstPage()) {
				result = PREVIOUS;
			}
			else if (idx == (size - 2) && paginated && !isLastPage()) {
				result = NEXT;
			}
			else if (idx == size - 1) {
				result = MORE;
			}
			else {
				int dec = 0;
				dec += (paginated && !isFirstPage() ? 1 : 0);
				int newIdx = idx - dec;
				result = this.page.get(newIdx);
			}
		}
		else {
			int pageSize = (this.page != null ? this.page.size() : 0);
			if (idx < pageSize) {
				result = this.page.get(idx);
			}
			else {
				boolean isFirstPage = isFirstPage();
				boolean isLastPage = isLastPage();

				if (!paginated || (isFirstPage && isLastPage) || (idx == (pageSize + 2)) || pageSize <= 0) {
					result = MORE;
				}
				else {
					if (idx == pageSize) {
						result = (isLastPage ? PREVIOUS : NEXT);
					}
					else if (idx == (pageSize + 1)) {
						result = (isLastPage || isFirstPage ? MORE : PREVIOUS);
					}
				}
			}
		}
		return result;
	}

	public int getSize() {
		int result = 0;
		this.page = (this.page != null ? this.page : this.filter.listPage());
		int currentPageSize = (this.page != null ? this.page.size() : 0);
		result = currentPageSize;
		boolean paginated = isPaginated();
		result += (paginated && !isFirstPage() && currentPageSize > 0 ? 1 : 0);
		result += (paginated && !isLastPage() && currentPageSize > 0 ? 1 : 0);
		result += 1;
		return result;
	}
	
	public boolean isPaginated () {
		boolean result = false;
		Boolean paginated = (this.filter != null ? this.filter.getPaginated() : null);
		result = (paginated != null && paginated.booleanValue());
		return result;
	}

	public int getTotalPages () {
		int result = 0;
		Integer totalPages = (this.filter != null ? this.filter.getTotalPages() : null);
		result = (totalPages != null ? totalPages.intValue() : result);
		return result;
	}

	public int getPageIndex () {
		int result = -1;
		Integer pageIndex = (this.filter != null ? this.filter.getPageIndex() : null);
		result = (pageIndex != null ? pageIndex.intValue() : result);
		return result;
	}
	
	public boolean isFirstPage () {
		boolean result = false;
		int pageIndex = getPageIndex();
		result = (pageIndex == 0);
		return result;
	}
	
	public boolean isLastPage () {
		boolean result = false;
		int pageIndex = getPageIndex();
		int totalPages = getTotalPages();
		result = (pageIndex == (totalPages - 1));
		return result;
	}

	protected static final Object MORE = "...";
	
	protected static final Object PREVIOUS = "<-";

	protected static final Object NEXT = "->";

	public void process (RefField field, Object value) {
		if (field != null) {
			if (value == MORE) {
				findMoreValues(field, value);
			}
			else if (value == PREVIOUS) {
				previousValue(field, value);
			}
			else if (value == NEXT) {
				nextValue(field, value);
			}
			else {
				normalValue(field, value);
			}
		}
	}

	protected void findMoreValues(RefField field, Object value) {
		Event event = new Event(CteEvents.ON_SELECT_REF, field, field.getValue());
		org.zkoss.zk.ui.event.EventListener mainCtrl = ViewContext.getMainCtrl();
		try {
			mainCtrl.onEvent(event);
		} catch (Exception e) {
			throw new UiException(e);
		}
//		System.out.println("MORE");
	}

	protected void normalValue(RefField field, Object value) {
		field.setValue(value, false);
	}

	protected void nextValue(RefField field, Object value) {
		List values = field.getValues();
		Filter filter = null;
		try { filter = (Filter)values; } catch (ClassCastException e) {}
		if (filter != null) {
			filter.nextPage();
			field.resetValues();
		}
	}

	protected void previousValue(RefField field, Object value) {
		List values = field.getValues();
		Filter filter = null;
		try { filter = (Filter)values; } catch (ClassCastException e) {}
		if (filter != null) {
			filter.previousPage();
			field.resetValues();
		}
//		System.out.println("PREVIOUS");
	}
	
}
