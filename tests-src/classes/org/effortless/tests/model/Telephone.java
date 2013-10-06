package org.effortless.tests.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.Filter;

@Entity
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class Telephone extends AbstractIdEntity<Telephone> {

	public static Filter<Telephone> listBy () {
		return AbstractIdEntity.listBy(Telephone.class);
	}
	
	protected String number;

	protected void initiateNumber() {
		this.number = null;
	}

	@javax.persistence.Column(name="CNUMBER")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String newValue) {
		String oldValue = this.number;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.number = newValue;
			firePropertyChange("number", oldValue, newValue);
		}
	}

	public String toString () {
		return getNumber();
	}
	
}
