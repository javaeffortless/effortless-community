package org.effortless.server.binder;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import org.zkoss.zel.BeanELResolver;
import org.zkoss.zel.ELContext;
import org.zkoss.zel.ELException;
import org.zkoss.zel.ELResolver;
import org.zkoss.zel.PropertyNotFoundException;
import org.zkoss.zel.PropertyNotWritableException;

public class BeanListELResolver extends ELResolver {

	public BeanListELResolver () {
		super();
		this.resolver = new BeanELResolver();
	}
	
	protected BeanELResolver resolver;
	
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return this.resolver.getCommonPropertyType(context, base);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return this.resolver.getFeatureDescriptors(context, base);
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return this.resolver.getType(context, base, property);
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return this.resolver.getValue(context, base, property);
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return this.resolver.isReadOnly(context, base, property);
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value)
			throws NullPointerException, PropertyNotFoundException,
			PropertyNotWritableException, ELException {
		this.resolver.setValue(context, base, property, value);
	}

}
