package org.effortless.server.binder;

import org.zkoss.zel.ELResolver;

import org.zkoss.xel.XelContext;
import org.zkoss.xel.zel.XelELContext;
import org.zkoss.xel.zel.XelELResolver;

public class EffortlessXelELContext extends XelELContext {

	public EffortlessXelELContext(XelContext xelc) {
		super(xelc);
	}
	
	protected ELResolver newELResolver(XelContext xelc) {
//		return new XelELResolver(xelc);
		return new EffortlessXelELResolver(xelc);
	}
	
}
