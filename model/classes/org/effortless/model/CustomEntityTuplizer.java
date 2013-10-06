package org.effortless.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.metamodel.binding.AttributeBinding;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

public class CustomEntityTuplizer extends PojoEntityTuplizer {

	public CustomEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
		super( entityMetamodel, mappedEntity );
	}

	public CustomEntityTuplizer(EntityMetamodel entityMetamodel, EntityBinding mappedEntity) {
		super( entityMetamodel, mappedEntity );
	}
	
	
    protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity) {
		final String propertyName = mappedProperty.getName();
		final Class propertyClass = mappedProperty.getType().getReturnedClass();
		final Class clazz = mappedEntity.getMappedClass();
		return buildGetter(propertyName, propertyClass, clazz);
	}

	protected Getter buildPropertyGetter(AttributeBinding mappedProperty) {
		final String propertyName = mappedProperty.getAttribute().getName();
		final Class propertyClass = mappedProperty.getContainer().getClassReference();
		final Class clazz = mappedProperty.getAttribute().getAttributeContainer().getClassReference();
		return buildGetter(propertyName, propertyClass, clazz);
	}
	
    protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity) {
		final String propertyName = mappedProperty.getName();
		final Class propertyClass = mappedProperty.getType().getReturnedClass();
		final Class clazz = mappedEntity.getMappedClass();
		return buildSetter(propertyName, propertyClass, clazz);
	}

	protected Setter buildPropertySetter(AttributeBinding mappedProperty) {
		final String propertyName = mappedProperty.getAttribute().getName();
		final Class propertyClass = mappedProperty.getContainer().getClassReference();
		final Class clazz = mappedProperty.getAttribute().getAttributeContainer().getClassReference();
		return buildSetter(propertyName, propertyClass, clazz);
	}
	
	protected Getter buildGetter (final String propertyName, final Class propertyClass, final Class clazz) {

		final String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
//		println "%%%%%%%%%%%%%%%% " + methodName
		return new Getter () {
		
			public Object get(Object data) throws HibernateException {
				Object result = null;
				Method method = getMethod();
				try {
					result = method.invoke(data, (Object[])null);
				} catch (IllegalAccessException e) {
					throw new HibernateException(e);
				} catch (IllegalArgumentException e) {
					throw new HibernateException(e);
				} catch (InvocationTargetException e) {
					throw new HibernateException(e);
				}
//				return data."$methodName"();
				return result;
			}
	
			public Object getForInsert(Object data, Map map,
					SessionImplementor session) throws HibernateException {
				return get(data);
			}
	
			public Member getMember() {
				return getMethod();
			}
	
			public Method getMethod() {
				try {
					return clazz.getMethod(methodName);
				}
				catch (SecurityException e) {
					throw new RuntimeException(e);
				}
				catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}
	
			public String getMethodName() {
				Method method = getMethod();
				return method.getName();
			}
	
			public Class getReturnType() {
				Method method = getMethod();
				return method.getReturnType();
			}
			
		};
	}

    protected Setter buildSetter(final String propertyName, final Class propertyClass, final Class clazz) {
		final String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
//		println "%%%%%%%%%%%%%%%% " + methodName
		return new Setter () {

			public Method getMethod() {
				Method result = null;
				try {
					result = clazz.getMethod(methodName, new Class[] {propertyClass});
				} catch (NoSuchMethodException e) {
					
					throw new RuntimeException(e);
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				}
//				return clazz.getMethod(methodName, [propertyClass] as Class[]);
				return result;
			}

			public String getMethodName() {
				Method method = getMethod();
				return method.getName();
			}

			public void set(Object target, Object value,
					SessionFactoryImplementor factory) throws HibernateException {
				// TODO Auto-generated method stub
				Method method = getMethod();
				try {
					method.invoke(target, new Object[] {value});
				} catch (IllegalAccessException e) {
					throw new HibernateException(e);
				} catch (IllegalArgumentException e) {
					throw new HibernateException(e);
				} catch (InvocationTargetException e) {
					throw new HibernateException(e);
				}
//				target."$methodName"(value);
			}
			
		};
	}
	
}
