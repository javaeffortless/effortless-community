package org.effortless.tests.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.ChildEntity;
import org.effortless.model.log.ListPropertyChanges;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

//@org.hibernate.annotations.Tuplizer(impl=CustomEntityTuplizer.class)

@Entity
@Table(name="CollectionExclusive")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class CollectionExclusive extends AbstractIdEntity<CollectionExclusive> {

	public CollectionExclusive () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateItems__();
	}
	
	protected List<Item> items__;

	protected void initiateItems__() {
		this.items__ = null;
	}

//  @OrderBy("ID ASC")
//@org.hibernate.annotations.Persister(impl=org.effortless.model.ExclusiveCollectionPersister.class)
//  @LazyCollection(LazyCollectionOption.EXTRA) 
	
    @OneToMany(cascade={})
    @JoinColumn(name="PARENT_ID", updatable=false, insertable=false, nullable=true)
	protected List<Item> getItems__() {
    	this.items__ = (this.items__ != null ? this.items__ : new ArrayList<Item>());
//    	this.items = _adaptItems(this.items);
		return this.items__;
	}
    
    protected List<Item> items;
    
    @javax.persistence.Transient
    public List<Item> getItems () {
    	if (this.items == null) {
    		this.items = new ListPropertyChanges<Item>(this, "items", getItems__(), true);
    	}
    	return this.items;
    }
    
//    protected List<Item> _adaptItems (List<Item> list) {
//    	List<Item> result = null;
//    	try {
//    		ListPropertyChanges lpc = (ListPropertyChanges)list;
//    		result = list;
//    	}
//    	catch (ClassCastException e) {
//        	try {
//        		ListPropertyChanges lpc = (ListPropertyChanges)this.items;
//        		lpc.setList(list);
//        		result = this.items;
//        	}
//        	catch (ClassCastException e1) {
//        		result = new ListPropertyChanges<Item>(this, "items", list);
//        	}
//    	}
//    	return result;
//    }
    
	protected void setItems__(List<Item> newValue) {
		List<Item> oldValue = this.getItems__();
		if (!ObjectUtils.equals(oldValue, newValue)) {
//	    	this.items = _adaptItems(newValue);
	    	this.items__ = newValue;
			firePropertyChange("items", oldValue, newValue);
		}
	}
	
	protected boolean doSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		result = doSaveList(CollectionExclusive.Item.class, getItems(), validate, create);
		return result;
	}
	

	protected String name;

	protected void initiateName() {
		this.name = null;
	}

	@javax.persistence.Column(name = "NAME")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getName() {
		return this.name;
	}

	public void setName(String newValue) {
		String oldValue = this.getName();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.name = newValue;
			firePropertyChange("name", oldValue, newValue);
		}
	}
	
	@Entity
	@Table(name="CollectionExclusiveItem")
	@org.hibernate.annotations.DynamicInsert(value = true)
	@org.hibernate.annotations.DynamicUpdate(value = true)
	public static class Item extends AbstractIdEntity<Item> implements ChildEntity<CollectionExclusive> {
		
		public Item () {
			super();
		}
		
		public void doSetOwner(CollectionExclusive parent) {
			setParent(parent);
		}
		
		protected void initiate () {
			super.initiate();
			initiateParent();
		}
		
		protected CollectionExclusive parent;

		protected void initiateParent() {
			this.parent = null;
		}

	    @ManyToOne
	    @JoinColumn(name="PARENT_ID", nullable=true)	    
		public CollectionExclusive getParent() {
			return this.parent;
		}

		public void setParent(CollectionExclusive newValue) {
			CollectionExclusive oldValue = this.getParent();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.parent = newValue;
				firePropertyChange("parent", oldValue, newValue);
				changeParent(oldValue, newValue);
			}
		}
		
		protected void changeParent(CollectionExclusive oldValue,
				CollectionExclusive newValue) {
			setFdParentName((newValue != null ? newValue.getName() : null));
		}

		protected String fdParentName;

		protected void initiateFdParentName() {
			this.fdParentName = null;
		}

		@javax.persistence.Column(name = "FD_PARENTNAME")
		@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		public String getFdParentName() {
			return this.fdParentName;
		}

		public void setFdParentName(String newValue) {
			String oldValue = this.getFdParentName();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.fdParentName = newValue;
				firePropertyChange("fdParentName", oldValue, newValue);
			}
		}
		
	}
	
}
