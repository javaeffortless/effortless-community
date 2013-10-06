package org.effortless.tests.model;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToMany;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;

@Entity
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class Product extends AbstractIdEntity<Product> {

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

	protected List<Part> parts = new ArrayList<Part>();
	
    @OneToMany
    @JoinTable(name="PRODUCT_PARTS", joinColumns = @JoinColumn( name="PRODUCT_ID"), inverseJoinColumns = @JoinColumn( name="PART_ID"))
    public List<Part> getParts() { return parts; }
    void setParts(List<Part> parts) { this.parts = parts; }	

	public String toString () {
		return getName();
	}

	
	@Entity
	//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
	public static class Part extends AbstractIdEntity<Part> {

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

		public String toString () {
			return getName();
		}
		
	}
	
	
	
}
