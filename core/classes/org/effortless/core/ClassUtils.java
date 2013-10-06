package org.effortless.core;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassUtils extends Object {

	protected ClassUtils () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}

	public static Class<?> loadClass (String className) throws ClassNotFoundException {
		Class<?> result = null;
		if (className != null) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			result = (cl != null ? cl.loadClass(className) : null);
		}
		return result;
	}
	
	public static Class<?> tryLoadClass (String className) {
		Class<?> result = null;
		try {
			result = loadClass(className);
		} catch (ClassNotFoundException e) {
		}
		return result;
	}
	
	public static Class<?> loadClassRE (String className) {
		Class<?> result = null;
		try {
			result = loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new ModelException(e);
		}
		return result;
	}
	
	public static Object newInstance (String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object result = null;
		Class<?> type = loadClass(className);
		result = (type != null ? type.newInstance() : null);
		return result;
	}
	
	public static Object tryNewInstance (String className) {
		Object result = null;
		try {
			result = newInstance(className);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return result;
	}
	
	public static Object newInstanceRE (String className) {
		Object result = null;
		try {
			result = newInstance(className);
		} catch (ClassNotFoundException e) {
			throw new ModelException(e);
		} catch (InstantiationException e) {
			throw new ModelException(e);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		}
		return result;
	}
	
	public static Object newInstance (Class<?> type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object result = null;
		result = (type != null ? type.newInstance() : null);
		return result;
	}
	
	public static Object tryNewInstance (Class<?> type) {
		Object result = null;
		try {
			result = newInstance(type);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return result;
	}
	
	public static Object newInstanceRE (Class<?> type) {
		Object result = null;
		try {
			result = newInstance(type);
		} catch (ClassNotFoundException e) {
			throw new ModelException(e);
		} catch (InstantiationException e) {
			throw new ModelException(e);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public static Class<?> getClass(Type type) {
    	Class<?> result = null;
        if (type instanceof Class) {
            result = (Class) type;
        } 
        else if (type instanceof ParameterizedType) {
            result = getClass(((ParameterizedType) type).getRawType());
        } 
        else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                result = Array.newInstance(componentClass, 0).getClass();
            } 
            else {
                result = null;
            }
        } 
        else {
            result = null;
        }
        return result;
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic
     * base class.
     *
     * @param baseClass the base class
     * @param childClass the child class
     * @return a list of the raw classes for the actual type arguments.
     */
    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

}
