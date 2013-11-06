package org.effortless.security;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.effortless.core.GlobalContext;
import org.effortless.core.I18nManager;
import org.effortless.core.SeverityException;
import org.effortless.model.SessionManager;
import org.effortless.security.conditions.*;
import org.effortless.security.conflicts.SecurityConflict;
import org.effortless.security.params.*;
import org.effortless.security.resources.*;
import org.effortless.security.validators.SecurityValidator;

public abstract class AbstractSecuritySystem extends Object implements SecuritySystem {

	public AbstractSecuritySystem() {
		super();
		initiate();
		build();
	}

	protected void initiate() {
		initiateParams();
		initiateValidators();
		initiateEnabled();
	}
	
	protected void build () {
		buildParams();
		buildValidators();
		buildConflicts();
	}
	
	protected boolean enabled;
	
	protected void initiateEnabled () {
		this.enabled = true;
	}
	
	public boolean isEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (boolean newValue) {
		this.enabled = newValue;
	}
	
	public SecurityResponse check (Resource resource) {
		SecurityResponse result = null;
		if (this.enabled) {
			if (resource != null) {
				List<Permission> permissions = doGetPermissions();
				int length = (permissions != null ? permissions.size() : 0);
				List responses = new ArrayList();
				for (Permission permission : permissions) {
					if (permission != null) {
						SecurityResponse response = permission.check(resource);
						if (response != null) {
							responses.add(response);
						}
					}
				}
				result = mergeResponses(resource, responses);
				result = transformResponse(resource, result);
				if (!result.isAllow()) {
					forbidden(resource, result, responses);
				}
			}
		}
		else {
			result = NoneSecuritySystem.getInstance().check(resource);
		}
		return result;
	}
	
	protected void forbidden (Resource resource, SecurityResponse response, List responses) {
		String resourceId = resource.toString();
		String permission = response.getPermission();
		resourceId = (resourceId != null ? resourceId : "");
		permission = (permission != null ? permission : "");
		System.out.println("forbidden security id = " + resourceId + " permission = " + permission);
	}

	protected boolean transformFilter (Resource resource) {
		boolean result = false;
		if (resource != null) {
			List<SecurityCondition> conditions = doGetConditions();
			for (SecurityCondition condition : conditions) {
				condition.transform(resource);
			}
			result = true;
		}
		return result;
	}
	
	protected SecurityResponse transformResponse(Resource resource, SecurityResponse response) {
		SecurityResponse result = response;
		if (ResourceType.FILTER.equals(resource.getType())) {
			boolean trasnformQuery = transformFilter(resource);
			boolean valid = response.isAllow();
			if (!valid || !trasnformQuery) {
				response.setAllow(false);

				String appId = GlobalContext.get(GlobalContext.APP_ID, String.class);
				AccessSecurityException exception = new AccessSecurityException(GlobalContext.i18n(appId, DEFAULT_EXCEPTION_KEY_SUFFIX));
				if (exception != null) {
					exception.setResponse(response);
				}
				SecuritySeverity severity = SecuritySeverity.ERROR;
//				if (SecuritySeverity.NULL.equals(severity)) {
//					response.setQuery(null);
//					response.setParams(null);
//				}
				response.setException(exception);
				response.setSeverity(severity);
			}
			else {
				response.setAllow(true);
			}
			result = response;
		}
		else if (ResourceType.ACTION.equals(resource.getType())) {
			String operation = resource.getAction();
			boolean flag = ("delete".equals(operation) || "erase".equals(operation));
			if (flag) {//Debe tener también permiso de modificar para poder eliminar
				Resource newResource = new Resource();
				newResource.setType(ResourceType.ACTION);
				newResource.setObject(resource.getObject());
				newResource.setName("update");
				result = check(newResource);
			}
		}
		else if (response == null && resource != null) {
			AccessSecurityException notRsException = buildInaccesibleException();
			result = resource.notAllow(notRsException);
		}
		return result;
	}
	
	protected AccessSecurityException buildInaccesibleException () {
		AccessSecurityException result = null;
		
		Locale lang = getUserLocale();
		String exLabel = I18nManager.resolve(I18nSecurity.class, lang, "error.security.inacesible.label");
		String exDescription = I18nManager.resolve(I18nSecurity.class, lang, "error.security.inacesible.description");
		result = new AccessSecurityException(exLabel);
		result.setDescription(exDescription);
		result.setSeverity(SeverityException.ERROR);
		result.setSolution(null);
		return result;
	}
	
	public static final String DEFAULT_EXCEPTION_KEY_SUFFIX = "exception.msg";

	protected String doGetBaseI18n () {
		return "i18n.security";
	}
	
	protected String getUserLocaleText () {
		String result = null;
		Locale locale = getUserLocale();
		result = (locale != null ? locale.toString() : null);
		return result;
	}
	
	protected SecurityResponse mergeResponses(Resource resource, List responses) {
		SecurityResponse result = null;
		int length = (responses != null ? responses.size() : 0);
		if (length <= 1) {
			result = (length == 1 ? (SecurityResponse)responses.get(0) : null);
		}
		else {
			result = doMergeMultipleResponses(resource, responses);
		}
		return result;
	}

	protected SecurityResponse doMergeMultipleResponses (Resource resource, List responses) {
		SecurityResponse result = null;
		int length = (responses != null ? responses.size() : 0);
		if (length > 0) {
			result = resolveConflicts(resource, responses, length);
			if (result == null) {
				for (int i = 0; i < length; i++) {
					SecurityResponse response = (SecurityResponse)responses.get(i);
					if (result != null && response != null && response.getPriority() > result.getPriority()) {
						result = response;
					}
					else if (result == null) {
						result = response;
					}
				}
			}
//			result = (SecurityResponse)responses.get(0);
		}
		return result;
	}
	
	

	protected SecurityResponse resolveConflicts(Resource resource, List responses, int length) {
		SecurityResponse result = null;
		SecurityConflict conflict = selectConflict(resource);
		if (conflict != null) {
			result = conflict.resolve(responses);
		}
		return result;
	}

	protected SecurityConflict selectConflict(Resource resource) {
		SecurityConflict result = null;
		List conflicts = getConflicts();
		int length = (conflicts != null ? conflicts.size() : 0);
		for (int i = 0; i < length; i++) {
			SecurityConflict conflict = (SecurityConflict)conflicts.get(i);
			if (conflict != null && conflict.check(resource)) {
				result = conflict;
				break;
			}
		}
		return result;
	}
	
	protected void buildConflicts () {
		setupConflicts();
	}
	
	protected void setupConflicts() {
		//addValidator(validator);
	}

	protected void addConflict (SecurityConflict conflict) {
		if (conflict != null) {
			if (this.conflicts == null) {
				this.conflicts = new ArrayList();
			}
			this.conflicts.add(conflict);
		}
	}
	
	protected List conflicts;
	
	protected void initiateConflicts () {
		this.conflicts = null;
	}
	
	public List getConflicts () {
		return this.conflicts;
	}
	
	public void setConflicts (List newValue) {
		this.conflicts = newValue;
	}

	protected Map params;
	
	protected void initiateParams () {
		this.params = null;
	}
	
	protected Map getParams () {
		return this.params;
	}
	
	protected void setParams (Map newValue) {
		this.params = newValue;
	}
	
	protected void addParam (SecurityParam param) {
		if (param != null) {
			String paramId = param.getId();
			if (paramId != null) {
				if (this.params == null) {
					this.params = new HashMap();
				}
				this.params.put(paramId, param);
			}
		}
	}
	
	public SecurityParam getParam (String id) {
		SecurityParam result = null;
		if (id != null) {
			if (this.params != null) {
				result = (SecurityParam)this.params.get(id);
			}
		}
		return result;
	}
	
	protected void buildParams () {
		if (this.params == null) {
			this.params = new HashMap();
			setupDefaultParams();
			setupParams();
		}
	}
	
	public static final String PARAM_CURRENTUSER = "#currentUser";
	
	protected void setupDefaultParams () {
		addParam(new CurrentUserSecurityParam(PARAM_CURRENTUSER));
	}
	
	protected void setupParams() {
		// TODO Auto-generated method stub
	}

	protected Map validators;
	
	protected void initiateValidators () {
		this.validators = null;
	}
	
	protected Map getValidators () {
		return this.validators;
	}
	
	protected void setValidators (Map newValue) {
		this.validators = newValue;
	}
	
	protected void addValidator (SecurityValidator validator) {
		if (validator != null) {
			String id = validator.getId();
			if (id != null) {
				if (this.validators == null) {
					this.validators = new HashMap();
				}
				this.validators.put(id, validator);
			}
		}
	}
	
	public SecurityValidator getValidator (String id) {
		SecurityValidator result = null;
		if (id != null) {
			if (this.validators != null) {
				result = (SecurityValidator)this.validators.get(id);
			}
		}
		return result;
	}
	
	protected void buildValidators () {
		if (this.validators == null) {
			this.validators = new HashMap();
			setupValidators();
		}
	}
	
	protected void setupValidators() {
		//addValidator(validator);
	}

	protected Permission newPermission () {
		Permission result = null;
		result = new Permission();
		return result;
	}
	
	protected Locale getUserLocale () {
		Locale result = null;
		result = SessionManager.getUserLocale();
		return result;
	}
	
	protected Permission createPermission (ResourceSet resource, Enum<?> rol) {
		Permission result = null;
		result = newPermission();
		result.setResource(resource);
		result.setRoles(new Enum[] {rol});
		result.setAnyRole(true);
		return result;
	}

	protected Permission createPermission (ResourceSet resource, Enum<?>[] roles) {
		Permission result = null;
		result = newPermission();
		result.setResource(resource);
		result.setRoles(roles);
		result.setAnyRole(true);
		return result;
	}
	
	protected Permission createPermission (ResourceSet resource, Enum<?>[] roles, boolean anyRole) {
		Permission result = null;
		result = newPermission();
		result.setResource(resource);
		result.setRoles(roles);
		result.setAnyRole(anyRole);
		return result;
	}
	
	protected List<Permission> doGetPermissions () {
		return null;
	}

	protected List<SecurityCondition> doGetConditions () {
		return null;
	}
	
	public abstract Object login (String loginName, String loginPassword);

	public void setupSession (Object user) {
	}
	
}
