package org.effortless.model;

public interface EntityProcess<Type extends Object, Result extends Object> {

	public Result run (Type entity);
	
}
