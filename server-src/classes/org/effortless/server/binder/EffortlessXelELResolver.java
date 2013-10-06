package org.effortless.server.binder;

import org.zkoss.xel.XelContext;
import org.zkoss.xel.zel.XelELResolver;
import org.zkoss.zel.ArrayELResolver;
import org.zkoss.zel.BeanELResolver;
import org.zkoss.zel.CompositeELResolver;
import org.zkoss.zel.ELResolver;
import org.zkoss.zel.ListELResolver;
import org.zkoss.zel.MapELResolver;
import org.zkoss.zel.ResourceBundleELResolver;

public class EffortlessXelELResolver extends XelELResolver {

	private static final CompositeELResolver NEW_DEFAULT;
	static {
		NEW_DEFAULT = new MyCompositeELResolver();
		NEW_DEFAULT.add(new MapELResolver());
		NEW_DEFAULT.add(new ResourceBundleELResolver());
//		NEW_DEFAULT.add(new FilterELResolver());
		NEW_DEFAULT.add(new BeanListELResolver());
		NEW_DEFAULT.add(new ListELResolver());
		NEW_DEFAULT.add(new ArrayELResolver());
		NEW_DEFAULT.add(new BeanELResolver());
	}
	
	
	
	public EffortlessXelELResolver(XelContext ctx) {
		super(ctx);
	}
	
	protected ELResolver getELResolver() {
		return NEW_DEFAULT;
	}
	
}
