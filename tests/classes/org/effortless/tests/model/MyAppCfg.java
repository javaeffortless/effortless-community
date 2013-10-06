package org.effortless.tests.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.effortless.model.AbstractCfg;

@Entity
@Table(name="APP_CFG")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
@org.hibernate.annotations.SelectBeforeUpdate(value = true)
public class MyAppCfg extends AbstractCfg<MyAppCfg> {

	public MyAppCfg () {
		super();
	}
	
	@javax.persistence.Transient
	public Integer getNumDays() {
		return getProperty(Integer.class, "numDays", null);
	}

	public void setNumDays(Integer newValue) {
		setProperty("numDays", newValue);
	}
	
}
