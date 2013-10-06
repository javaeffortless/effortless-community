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
import org.effortless.model.log.ListPropertyChanges;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

//@org.hibernate.annotations.Tuplizer(impl=CustomEntityTuplizer.class)

@Entity
@Table(name="ItemNonExclusive")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class ItemNonExclusive extends AbstractIdEntity<ItemNonExclusive> {

	public ItemNonExclusive () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
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
