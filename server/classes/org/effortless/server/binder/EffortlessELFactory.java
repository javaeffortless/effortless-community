package org.effortless.server.binder;

import org.zkoss.xel.XelContext;
import org.zkoss.xel.zel.ELFactory;
import org.zkoss.zel.ELContext;

public class EffortlessELFactory extends ELFactory {

	public EffortlessELFactory() {
		super();
	}
	
	protected ELContext newELContext(XelContext xelc) {
		return new EffortlessXelELContext(xelc);
	}
	
}
