package org.effortless.server.binder;

import org.zkoss.zel.BeanELResolver;
import org.zkoss.zel.CompositeELResolver;
import org.zkoss.zel.ListELResolver;

public class FilterELResolver extends CompositeELResolver {

	public FilterELResolver () {
		super();
		add(new BeanListELResolver());
		add(new ListELResolver());
	}
	
}
