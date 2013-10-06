package org.effortless.tests.model;

import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.CriteriaFilter;
import org.hibernate.criterion.Restrictions;

public class PersonFinderFilter extends CriteriaFilter<Person> {

	public PersonFinderFilter () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateName();
	}
	
//	public java.util.Map<?, ?> toMap () {
//		return new java.util.AbstractMap() {
//
//			@Override
//			public Set entrySet() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		};
//	}
	
	protected String name;

	protected void initiateName() {
		this.name = null;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newValue) {
		this._setProperty("name", this.name, this.name = newValue);
//		String oldValue = this.name;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.name = newValue;
//			refreshName(oldValue, newValue);
//		}
	}

	protected void refreshName(String oldValue, String newValue) {
		updateFilter();
	}
	
	protected void setupConditions () {
		super.setupConditions();
		
		this.lk("name", this.name);
		if (this.name != null && this.name.trim().length() > 0) {
			this.criteria.add(Restrictions.like("name", "%" + this.name + "%"));
		}
	}
	
}
