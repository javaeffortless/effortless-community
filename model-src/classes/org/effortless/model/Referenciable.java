package org.effortless.model;

import java.util.Locale;

public interface Referenciable {

	public String toLabel ();
	
	public String toDescription ();
	
	public String toImage ();

	public String toLabel (Locale locale);
	
	public String toDescription (Locale locale);
	
	public String toImage (Locale locale);
	
	
}
