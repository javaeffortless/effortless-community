package org.effortless.model;


public class EntityFilter<Type extends Entity<Type>> extends CriteriaFilter<Type> {

	protected EntityFilter () {
		super();
	}
	
	public EntityFilter (Class<?> entity) {
		this();
		this.entity = entity;
	}
	
	protected Class<?> entity;
	
	protected Class<?> doGetFilterClass () {
		return this.entity;
	}
	
}
