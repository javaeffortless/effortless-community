package org.effortless.server.binder;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import org.zkoss.zel.CompositeELResolver;
import org.zkoss.zel.ELContext;
import org.zkoss.zel.ELException;
import org.zkoss.zel.PropertyNotFoundException;
import org.zkoss.zel.PropertyNotWritableException;

public class MyCompositeELResolver extends CompositeELResolver {

	public MyCompositeELResolver () {
		super();
	}
	
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return super.getCommonPropertyType(context, base);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return super.getFeatureDescriptors(context, base);
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return super.getType(context, base, property);
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return super.getValue(context, base, property);
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return super.isReadOnly(context, base, property);
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value)
			throws NullPointerException, PropertyNotFoundException,
			PropertyNotWritableException, ELException {
		super.setValue(context, base, property, value);
	}
	
}
