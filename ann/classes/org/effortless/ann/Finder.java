package org.effortless.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@java.lang.annotation.Documented
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Finder {
	
	String op();
	String properties();
	String info();
	String listActions();
	String inlineActions();
	
}
