package org.effortless.tests.model;

import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.log.ListPropertyChanges;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

//@org.hibernate.annotations.Tuplizer(impl=CustomEntityTuplizer.class)

@Entity
@Table(name="CollectionNonExclusive")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class CollectionNonExclusive extends AbstractIdEntity<CollectionNonExclusive> {

	public CollectionNonExclusive () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateItems__();
	}
	
	protected List<ItemNonExclusive> items__;

	protected void initiateItems__() {
		this.items__ = null;
	}

//  @OrderBy("ID ASC")
//@org.hibernate.annotations.Persister(impl=org.effortless.model.ExclusiveCollectionPersister.class)
//  @LazyCollection(LazyCollectionOption.EXTRA) 
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {}, targetEntity=ItemNonExclusive.class)
//    @OneToMany(cascade={}, targetEntity=ItemNonExclusive.class)
	@JoinTable(name = "collectionnonexclusive_items", joinColumns = {	@JoinColumn(name = "OWNER_ID", nullable = false, updatable = false, insertable = false) }, inverseJoinColumns = { @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false, insertable = false) })    
	protected List<ItemNonExclusive> getItems__() {
    	this.items__ = (this.items__ != null ? this.items__ : new ArrayList<ItemNonExclusive>());
//    	this.items = _adaptItems(this.items);
		return this.items__;
	}
    
    protected List<ItemNonExclusive> items;
    
    @javax.persistence.Transient
    public List<ItemNonExclusive> getItems () {
    	if (this.items == null) {
    		this.items = new ListPropertyChanges<ItemNonExclusive>(this, "items", getItems__(), false);
    	}
    	return this.items;
    }
    
	protected void setItems__(List<ItemNonExclusive> newValue) {
		List<ItemNonExclusive> oldValue = this.getItems__();
		if (!ObjectUtils.equals(oldValue, newValue)) {
//	    	this.items = _adaptItems(newValue);
	    	this.items__ = newValue;
			firePropertyChange("items", oldValue, newValue);
		}
	}
	
	protected boolean doSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		result = doSaveList(ItemNonExclusive.class, getItems(), validate, create);
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
	
}
