package org.effortless.security;

import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import org.effortless.security.conditions.SecurityCondition;
import org.effortless.core.I18nManager;
import org.effortless.core.ObjectUtils;
import org.effortless.core.PropertyUtils;
import org.effortless.model.Entity;
import org.effortless.model.Referenciable;
import org.effortless.model.SessionManager;
import org.effortless.security.resources.*;
import org.effortless.security.validators.SecurityValidator;

public class Permission extends Object implements SecuritySystem {

	public Permission () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateResource();
		initiateRoles();
		initiateAnyRole();
//		initiateConditions();
		initiateAccessibleValidator();
		initiateId();
	}
	
	protected String id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String newValue) {
		this.id = newValue;
	}
	
	protected boolean anyRole;
	
	protected void initiateAnyRole () {
		this.anyRole = true;
	}
	
	public boolean getAnyRole () {
		return this.anyRole;
	}
	
	public void setAnyRole (boolean newValue) {
		this.anyRole = newValue;
	}

	protected boolean equals (Object value1, Object value2) {
		return ObjectUtils.equals(value1, value2);
	}
	
	protected Object getUser (Resource resource) {
		Object result = null;
		result = SessionManager.getCurrentUser();
		return result;
	}
	
	public static final String PROPERTY_SECURITYROLES = "securityRoles";
	public static final String METHOD_SECURITYROLES = "checkSecurityRol";
	
	protected List getRoles (Resource resource) {
		List result = null;
		Object user = getUser(resource);
		result = (user != null ? (List)PropertyUtils.getProperty(user, PROPERTY_SECURITYROLES, null) : null);
		return result;
	}
	
	protected boolean containsRol (Enum<?> rol, Resource resource) {
		boolean result = false;
		java.util.Map securityRoles = SessionManager.getSecurityRoles();
//		java.util.Map securityRoles = (this.dao != null ? (java.util.Map)this.dao.getAttribute(FactoryDAO.SECURITY_ROLES) : null);
		if (securityRoles != null) {
			result = securityRoles.containsKey(rol);
		}
		else {
			Object user = getUser(resource);
			if (user != null) {
				Boolean returnValue = null;
				try {
					returnValue = (Boolean)org.apache.commons.beanutils.MethodUtils.invokeExactMethod(user, METHOD_SECURITYROLES, new Object[] {rol});
				} catch (NoSuchMethodException e) {
					throw new AccessSecurityException(e);
				} catch (IllegalAccessException e) {
					throw new AccessSecurityException(e);
				} catch (InvocationTargetException e) {
					throw new AccessSecurityException(e);
				}
				result = (returnValue != null && returnValue.booleanValue());
			}
		}
    	return result;
	}
	
    protected boolean hasRole (Enum<?> rol, Resource resource) {
    	boolean result = false;
    	if (rol != null) {
    		result = containsRol(rol, resource);
    	}
    	return result;
    }
    
    protected boolean hasAllRoles (Enum<?>[] roles, Resource resource) {
    	boolean result = false;
    	int rolesLength = (roles != null ? roles.length : 0);
    	if (rolesLength > 0) {
    		result = true;
    		for (int i = 0; i < rolesLength; i++) {
    			Enum<?> rol = roles[i];
    			if (rol != null && !hasRole(rol, resource)) {
    				result = false;
    				break;
    			}
    		}
    	}
    	return result;
    }
    
    protected boolean hasSomeAnyRole (Enum<?>[] roles, Resource resource) {
    	boolean result = false;
    	int rolesLength = (roles != null ? roles.length : 0);
    	if (rolesLength > 0) {
    		for (int i = 0; i < rolesLength; i++) {
    			Enum<?> rol = roles[i];
    			if (rol != null && hasRole(rol, resource)) {
    				result = true;
    				break;
    			}
    		}
    	}
    	return result;
    }

    protected boolean hasRoles (Resource resource) {
    	boolean result = false;
    	if (resource != null) {
    		boolean any = true;
    		if (any) {
    			result = hasSomeAnyRole(getRoles(), resource);
    		}
    		else {
    			result = hasAllRoles(getRoles(), resource);
    		}
    	}
    	return result;
    }
    
	protected ResourceSet resource;
	
	protected void initiateResource () {
		this.resource = null;
	}
	
	public ResourceSet getResource () {
		return this.resource;
	}
	
	public void setResource (ResourceSet newValue) {
		this.resource = newValue;
	}
	
	public boolean applyToResource (Resource resource) {
		boolean result = false;
		if (resource != null) {
			ResourceSet masterResource = getResource();
			result = (masterResource != null ? masterResource.contains(resource) : false);
		}
		return result;
	}
	
	protected Enum<?>[] roles;
	
	protected void initiateRoles () {
		this.roles = null;
	}
	
	public Enum<?>[] getRoles () {
		return this.roles;
	}
	
	public void setRoles (Enum<?>[] newValue) {
		this.roles = newValue;
	}

	public SecurityResponse check (Resource resource) {
		SecurityResponse result = null;
		if (applyToResource(resource)) {//¿Este recurso es aplicable a este permiso?
			boolean valid = hasRoles(resource);//¿El permiso da acceso al recurso?
			result = sendResponse(resource, valid);//Devolver respuesta de acceso al recurso
		}
		return result;
//		RESPUESTA NORMAL DEL SISTEMA DE SEGURIDAD
//		//ERROR en caso de ENTIDAD
//		   readByPk
//		//ERROR en caso de PROPIEDAD DE ENTIDAD
//		   loadPropertyX
//		   
//		//ERROR en caso de OPERACIÓN DE ENTIDAD
//		   Action, Sessio
//		   exclude operationDelete
//		   
//		//NULL (lista vacía) en caso de FILTRO
//		//NADA en caso de PANTALLA
//		  //EN CASO DE ENLACE A PANTALLA => visible = false
//		  //EN CASO DE ACCESO DIRECTO => NO MOVERSE DE LA PANTALLA ACTUAL
	}

	protected SecurityResponse sendResponse(Resource resource, boolean valid) {
		SecurityResponse result = null;
		result = new SecurityResponse();
		result.setPermission(getId());
		if (valid) {
			valid = validate(resource);
		}
		result.setAllow(valid);
		result.setResource(resource);
		if (!valid) {
			AccessSecurityException exception = buildAccessibleException(resource, valid, result);
			exception.setResponse(result);
			SecuritySeverity severity = SecuritySeverity.ERROR;
			ResourceType type = resource.getType();
			if (ResourceType.VIEW.equals(type) || ResourceType.PROPERTY.equals(type)) {
				severity = SecuritySeverity.NULL;
			}
			else {
				Object object = (resource != null ? resource.getObject() : null);
				if (object instanceof Entity) {
					Object id = ((Entity)object).doGetIdentifier();
					severity = (id != null ? SecuritySeverity.ERROR : SecuritySeverity.NULL);
				}
				else {
					severity = SecuritySeverity.ERROR;
				}
			}
			result.setException(exception);
			result.setSeverity(severity);
		}

		return result;
	}

	protected AccessSecurityException buildAccessibleException(
			Resource resource, boolean valid, SecurityResponse response) {
		AccessSecurityException result = null;
		result = new AccessSecurityException();
		return result;
	}

	protected boolean validate (Resource resource) {
		boolean result = false;
		SecurityValidator validator = getAccessibleValidator();
		if (validator != null && resource != null) {
			try {
				result = validator.validate(resource.getObject());
			} catch (org.effortless.core.ModelException e) {
				throw new AccessSecurityException(e);
			}
		}
		else {
			result = true;
		}
		return result;
	}
	
	protected SecurityValidator accessibleValidator;
	
	protected void initiateAccessibleValidator () {
		this.accessibleValidator = null;
	}
	
	public SecurityValidator getAccessibleValidator () {
		return this.accessibleValidator;
	}

	public void setAccessibleValidator (SecurityValidator newValue) {
		this.accessibleValidator = newValue;
	}
	
	protected AccessSecurityException buildSecurityException (Resource resource, boolean valid, SecurityResponse response) {
		AccessSecurityException result = null;
		String msg = null;
		try {
			msg = loadI18nMsg(resource, valid, response);
		} catch (Exception e) {
		}
		String desc = null;
		try {
			desc = loadI18nDescription(resource, valid, response);
		} catch (Exception e) {
		}
		result = new AccessSecurityException(msg);
		result.setDescription(desc);
		return result;
	}
	
	protected String loadI18nMsg (Resource resource, boolean valid, SecurityResponse response) throws Exception {
		String result = null;
		String permission = response.getPermission();
		if (permission != null) {
			Locale lang = getUserLocale();
			result = I18nManager.resolve(I18nSecurity.class, lang, "error.security.label");
		}
		return result;
	}
	
	protected String loadI18nDescription (Resource resource, boolean valid, SecurityResponse response) {
		String result = null;
		Enum<?> firstRol = (this.roles.length > 0 ? this.roles[0] : null);
		if (firstRol instanceof Referenciable) {
			Locale lang = getUserLocale();
			String permissionLabel = ((Referenciable)firstRol).toLabel(lang);
			if (permissionLabel != null) {
				result = I18nManager.resolve(I18nSecurity.class, lang, "error.security.description", new Object[] {permissionLabel});
			}
		}
		return result;
	}
	
	public static final String DEFAULT_EXCEPTION_KEY_SUFFIX = "exception.msg";

	protected String doGetBaseI18n () {
		return "i18n.security";
	}
	
	protected Locale getUserLocale () {
		Locale result = null;
		result = SessionManager.getUserLocale();
		return result;
	}
	
	protected String getUserLocaleText () {
		String result = null;
		Locale locale = getUserLocale();
		result = (locale != null ? locale.toString() : null);
		return result;
	}
	
	public String toString () {
		return this.id;
	}

	@Override
	public Object login(String loginName, String loginPassword) {
		throw new UnsupportedOperationException("login not supported.");
	}

	@Override
	public void setupSession(Object user) {
	}
	
}
