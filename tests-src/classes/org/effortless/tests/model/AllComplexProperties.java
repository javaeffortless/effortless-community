package org.effortless.tests.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.FileEntity;
import org.effortless.model.Filter;
import org.effortless.model.log.ListPropertyChanges;

@Entity
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class AllComplexProperties extends AbstractIdEntity<AllComplexProperties> {

	public static Filter<AllComplexProperties> listBy () {
		return AbstractIdEntity.listBy(AllComplexProperties.class);
	}
	
	protected AllBasicProperties reference;

	protected void initiateReference() {
		this.reference = null;
	}

	@ManyToOne(cascade = {})
	@JoinColumn(name="REFERENCE")
	public AllBasicProperties getReference() {
		return this.reference;
	}

	public void setReference(AllBasicProperties newValue) {
		AllBasicProperties oldValue = this.getReference();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.reference = newValue;
			firePropertyChange("reference", oldValue, newValue);
		}
	}
	
	
	protected FileEntity fichero;

	protected void initiateFichero() {
		this.fichero = null;
	}

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="FICHERO")
	public FileEntity getFichero() {
		return this.fichero;
	}
	
	@javax.persistence.Transient
	public String getFicheroPath () {
		FileEntity fichero = getFichero();
		return (fichero != null ? fichero.getPath() : null);
	}

	public void setFichero(FileEntity newValue) {
		FileEntity oldValue = this.getFichero();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fichero = newValue;
			firePropertyChange("fichero", oldValue, newValue);
		}
	}
	
	public void setFichero (java.io.File newValue) {
		FileEntity entity = getFichero();
		entity = (entity != null ? entity : new FileEntity());
		if (entity != null) {
			entity.setContent(newValue);
		}
		setFichero(entity);
	}

	public void setFichero (String newValue) {
		setFichero(new java.io.File(newValue));
	}

	protected List<AllBasicProperties> items__;

	protected void initiateItems__() {
		this.items__ = null;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = {}, targetEntity=AllBasicProperties.class)
	@JoinTable(name = "allcomplexproperties_listado", joinColumns = {	@JoinColumn(name = "OWNER_ID", nullable = false, updatable = false, insertable = false) }, inverseJoinColumns = { @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false, insertable = false) })    
	protected List<AllBasicProperties> getItems__() {
    	this.items__ = (this.items__ != null ? this.items__ : new ArrayList<AllBasicProperties>());
		return this.items__;
	}
    
    protected List<AllBasicProperties> items;
    
    @javax.persistence.Transient
    public List<AllBasicProperties> getItems () {
    	if (this.items == null) {
    		this.items = new ListPropertyChanges<AllBasicProperties>(this, "items", getItems__(), false);
    	}
    	return this.items;
    }
    
	protected void setItems__(List<AllBasicProperties> newValue) {
		List<AllBasicProperties> oldValue = this.getItems__();
		if (!ObjectUtils.equals(oldValue, newValue)) {
//	    	this.items = _adaptItems(newValue);
	    	this.items__ = newValue;
			firePropertyChange("items", oldValue, newValue);
		}
	}
	
	protected boolean doSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		FileEntity fichero = this.getFichero();
		if (fichero != null) {
			fichero.persist(validate);
		}
		result = doSaveList(AllBasicProperties.class, getItems(), validate, create);
		return result;
	}
	
	
	
	
	protected String text;

	protected void initiateText() {
		this.text = null;
	}

	@javax.persistence.Column(name = "TEXT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getText() {
		return this.text;
	}

	public void setText(String newValue) {
		String oldValue = this.getText();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.text = newValue;
			firePropertyChange("text", oldValue, newValue);
		}
	}
	
}
