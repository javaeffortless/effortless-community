package org.effortless.tests.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.FileEntity;

@Entity
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class Customer extends AbstractIdEntity<Customer> {

	protected String name;

	protected void initiateName() {
		this.name = null;
	}

	@javax.persistence.Column(name="CNAME")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getName() {
		return this.name;
	}

	public void setName(String newValue) {
		String oldValue = this.name;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.name = newValue;
			firePropertyChange("name", oldValue, newValue);
		}
	}
	
	protected FileEntity file;

	protected void initiateFile() {
		this.file = null;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="C_FILE")
	public FileEntity getFile() {
		return this.file;
	}

	public void setFile(FileEntity newValue) {
		FileEntity oldValue = this.file;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.file = newValue;
			firePropertyChange("file", oldValue, newValue);
		}
	}
	
	public void setFile (java.io.File newValue) {
		FileEntity entity = getFile();
		entity = (entity != null ? entity : new FileEntity());
		if (entity != null) {
			entity.setContent(newValue);
		}
		setFile(entity);
	}

	public void setFile (String newValue) {
		setFile(new java.io.File(newValue));
	}

	public String toString () {
		return getName();
	}
	
}
