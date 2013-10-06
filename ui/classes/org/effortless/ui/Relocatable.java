package org.effortless.ui;

public interface Relocatable {

	public Relocator getRelocator ();
	
	public void setRelocator (Relocator newValue);
	
	public Integer getPosition ();
	
	public void setPosition (Integer newValue);
	
}
