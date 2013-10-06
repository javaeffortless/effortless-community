package org.effortless.model.restrictions;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeRestriction extends Object implements Restriction {

	public CompositeRestriction () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateItems();
	}
	
	protected List items;
	
	protected void initiateItems () {
		this.items = null;
	}
	
	public List getItems () {
		return this.items;
	}
	
	public void setItems (List newValue) {
		this.items = newValue;
	}
	
	public void addItem (Restriction item) {
		if (item != null) {
			if (this.items != null) {
				this.items = new ArrayList();
			}
			this.items.add(item);
		}
	}
	
}
