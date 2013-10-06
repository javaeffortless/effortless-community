package org.effortless.model;

public interface ChildEntity<T extends Entity<T>> {

	public void doSetOwner(T parent);
	
}
