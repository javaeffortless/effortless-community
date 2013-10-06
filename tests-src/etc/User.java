package org.effortless.tests.model;

import java.util.Date;

import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
//import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.core.GlobalContext;
import org.effortless.model.AbstractEnabledPersistEntity;
import org.effortless.model.AbstractIdEntity;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="APP_USER")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
//@MappedSuperclass
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class User extends AbstractIdEntity<User> {

	public User () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateLogin();
		initiatePassword();
		initiateName();
		initiateSurname();
		initiateCode();
		initiateAlias();
		initiateDescription();
		initiateComment();
		initiateLanguage();
		initiateProfile();
		initiatePermissions();
		initiateEnable();
		
		initiateAllowSyncFromProfile();
		initiateDisableCache();
		initiateDisableCacheStartDate();
		initiateDisableCacheEndDate();

		initiateCreationDate();
		initiateLastModification();
		
		
		initiateFdProfileName();
		initiateFdProfileCode();
		initiateFdProfileAlias();
		initiateFdProfileDescription();
	}

	protected String login;

	protected void initiateLogin() {
		this.login = null;
	}

	@javax.persistence.Column(name = "USER_LOGIN")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String newValue) {
		String oldValue = this.getLogin();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.login = newValue;
			firePropertyChange("login", oldValue, newValue);
		}
	}
	
	protected String password;

	protected void initiatePassword() {
		this.password = null;
	}

	@javax.persistence.Column(name = "USER_PASSWORD")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String newValue) {
		String oldValue = this.getPassword();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.password = newValue;
			firePropertyChange("password", oldValue, newValue);
		}
	}
	
	protected String name;

	protected void initiateName() {
		this.name = null;
	}

	@javax.persistence.Column(name = "USER_NAME")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getName() {
		return this.name;
	}

	public void setName(String newValue) {
		String oldValue = this.getName();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.name = newValue;
			firePropertyChange("name", oldValue, newValue);
		}
	}
	
	protected String surname;

	protected void initiateSurname() {
		this.surname = null;
	}

	@javax.persistence.Column(name = "USER_SURNAME")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String newValue) {
		String oldValue = this.getSurname();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.surname = newValue;
			firePropertyChange("surname", oldValue, newValue);
		}
	}
	
	protected String code;

	protected void initiateCode() {
		this.code = null;
	}

	@javax.persistence.Column(name = "USER_CODE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getCode() {
		return this.code;
	}

	public void setCode(String newValue) {
		String oldValue = this.getCode();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.code = newValue;
			firePropertyChange("code", oldValue, newValue);
		}
	}
	
	protected String alias;

	protected void initiateAlias() {
		this.alias = null;
	}

	@javax.persistence.Column(name = "USER_ALIAS")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String newValue) {
		String oldValue = this.getAlias();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.alias = newValue;
			firePropertyChange("alias", oldValue, newValue);
		}
	}
	
	protected String description;

	protected void initiateDescription() {
		this.description = null;
	}

	@javax.persistence.Column(name = "USER_DESCRIPTION")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String newValue) {
		String oldValue = this.getDescription();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.description = newValue;
			firePropertyChange("description", oldValue, newValue);
		}
	}
	
	protected String comment;

	protected void initiateComment() {
		this.comment = null;
	}

	@javax.persistence.Column(name = "USER_COMMENT", length=3072)
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String newValue) {
		String oldValue = this.getComment();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.comment = newValue;
			firePropertyChange("comment", oldValue, newValue);
		}
	}
	
	protected Locale language;

	protected void initiateLanguage() {
		this.language = null;
	}

	@javax.persistence.Column(name = "USER_LANGUAGE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Locale getLanguage() {
		return this.language;
	}

	public void setLanguage(Locale newValue) {
		Locale oldValue = this.getLanguage();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.language = newValue;
			firePropertyChange("language", oldValue, newValue);
		}
	}
	
	protected UserProfile profile;

	protected void initiateProfile() {
		this.profile = null;
	}

    @ManyToOne
    @ForeignKey(name="PROFILE_ID")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public UserProfile getProfile() {
		return this.profile;
	}

	public void setProfile(UserProfile newValue) {
		UserProfile oldValue = this.getProfile();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.profile = newValue;
			firePropertyChange("profile", oldValue, newValue);
			changeProfile(oldValue, newValue);
		}
	}
	
	protected void changeProfile(UserProfile oldValue, UserProfile newValue) {
		setFdProfileName((newValue != null ? newValue.getName() : null));
		setFdProfileCode((newValue != null ? newValue.getCode() : null));
		setFdProfileAlias((newValue != null ? newValue.getAlias() : null));
		setFdProfileDescription((newValue != null ? newValue.getDescription() : null));
	}

	protected String fdProfileName;

	protected void initiateFdProfileName() {
		this.fdProfileName = null;
	}

	@javax.persistence.Column(name = "FD_PROFILENAME")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getFdProfileName() {
		return this.fdProfileName;
	}

	public void setFdProfileName(String newValue) {
		String oldValue = this.getFdProfileName();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fdProfileName = newValue;
			firePropertyChange("fdProfileName", oldValue, newValue);
		}
	}
	
	protected String fdProfileCode;

	protected void initiateFdProfileCode() {
		this.fdProfileCode = null;
	}

	@javax.persistence.Column(name = "FD_PROFILECODE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getFdProfileCode() {
		return this.fdProfileCode;
	}

	public void setFdProfileCode(String newValue) {
		String oldValue = this.getFdProfileCode();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fdProfileCode = newValue;
			firePropertyChange("fdProfileCode", oldValue, newValue);
		}
	}
	
	protected String fdProfileAlias;

	protected void initiateFdProfileAlias() {
		this.fdProfileAlias = null;
	}

	@javax.persistence.Column(name = "FD_PROFILEALIAS")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getFdProfileAlias() {
		return this.fdProfileAlias;
	}

	public void setFdProfileAlias(String newValue) {
		String oldValue = this.getFdProfileAlias();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fdProfileAlias = newValue;
			firePropertyChange("fdProfileAlias", oldValue, newValue);
		}
	}
	
	protected String fdProfileDescription;

	protected void initiateFdProfileDescription() {
		this.fdProfileDescription = null;
	}

	@javax.persistence.Column(name = "FD_PROFILEDESCRIPTION")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getFdProfileDescription() {
		return this.fdProfileDescription;
	}

	public void setFdProfileDescription(String newValue) {
		String oldValue = this.getFdProfileDescription();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.fdProfileDescription = newValue;
			firePropertyChange("fdProfileDescription", oldValue, newValue);
		}
	}
	
	protected List<UserPermission> permissions;

	protected void initiatePermissions() {
		this.permissions = null;
	}

    
    @OneToMany(cascade=CascadeType.ALL)
    @OrderBy("ID ASC")
//    @OrderColumn(name="id")
    @JoinColumn(name="USER_ID", nullable=false)
	public List<UserPermission> getPermissions() {
    	this.permissions = (this.permissions != null ? this.permissions : new java.util.ArrayList<UserPermission>());
		return this.permissions;
	}

	public void setPermissions(List<UserPermission> newValue) {
		List<UserPermission> oldValue = this.getPermissions();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.permissions = newValue;
			firePropertyChange("permissions", oldValue, newValue);
		}
	}
	
	protected Boolean enable;

	protected void initiateEnable() {
		this.enable = Boolean.TRUE;
	}

	@javax.persistence.Column(name = "USER_ENABLE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean newValue) {
		Boolean oldValue = this.getEnable();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.enable = newValue;
			firePropertyChange("enable", oldValue, newValue);
		}
	}
	
	protected Boolean allowSyncFromProfile;

	protected void initiateAllowSyncFromProfile() {
		this.allowSyncFromProfile = null;
	}

	@javax.persistence.Column(name = "USER_ALLOWSYNCFROMPROFILE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getAllowSyncFromProfile() {
		return this.allowSyncFromProfile;
	}

	public void setAllowSyncFromProfile(Boolean newValue) {
		Boolean oldValue = this.getAllowSyncFromProfile();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.allowSyncFromProfile = newValue;
			firePropertyChange("allowSyncFromProfile", oldValue, newValue);
		}
	}

	protected Boolean disableCache;

	protected void initiateDisableCache() {
		this.disableCache = null;
	}

	@javax.persistence.Column(name = "USER_DISABLECACHE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getDisableCache() {
		return this.disableCache;
	}

	public void setDisableCache(Boolean newValue) {
		Boolean oldValue = this.getDisableCache();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.disableCache = newValue;
			firePropertyChange("disableCache", oldValue, newValue);
		}
	}

	protected Date disableCacheStartDate;

	protected void initiateDisableCacheStartDate() {
		this.disableCacheStartDate = null;
	}

	@javax.persistence.Column(name = "USER_DISABLECACHESTARTDATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getDisableCacheStartDate() {
		return this.disableCacheStartDate;
	}

	public void setDisableCacheStartDate(Date newValue) {
		Date oldValue = this.getDisableCacheStartDate();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.disableCacheStartDate = newValue;
			firePropertyChange("disableCacheStartDate", oldValue, newValue);
		}
	}

	protected Date disableCacheEndDate;

	protected void initiateDisableCacheEndDate() {
		this.disableCacheEndDate = null;
	}

	@javax.persistence.Column(name = "USER_DISABLECACHEENDDATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getDisableCacheEndDate() {
		return this.disableCacheEndDate;
	}

	public void setDisableCacheEndDate(Date newValue) {
		Date oldValue = this.getDisableCacheEndDate();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.disableCacheEndDate = newValue;
			firePropertyChange("disableCacheEndDate", oldValue, newValue);
		}
	}

	protected Date creationDate;

	protected void initiateCreationDate() {
		this.creationDate = null;
	}

	@javax.persistence.Column(name = "USER_CREATIONDATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date newValue) {
		Date oldValue = this.getCreationDate();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.creationDate = newValue;
			firePropertyChange("creationDate", oldValue, newValue);
		}
	}

	protected Date lastModification;

	protected void initiateLastModification() {
		this.lastModification = null;
	}

	@javax.persistence.Column(name = "USER_LASTMODIFICATION")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getLastModification() {
		return this.lastModification;
	}

	public void setLastModification(Date newValue) {
		Date oldValue = this.getLastModification();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.lastModification = newValue;
			firePropertyChange("lastModification", oldValue, newValue);
		}
	}

	
	
	public void onPreCreate () {
		super.onPreCreate();
		Date creationDate = getCreationDate();
		if (creationDate == null) {
			creationDate = new Date();
			setCreationDate(creationDate);
		}
		Date lastModification = getLastModification();
		if (lastModification == null) {
			setLastModification(creationDate);
		}
		Boolean enable = getEnable();
		if (enable == null) {
			enable = Boolean.TRUE;
			setEnable(enable);
		}
	}
	
	public void onPreUpdate () {
		super.onPreUpdate();
		Date lastModification = new Date();
		setLastModification(lastModification);
	}
	
	public static User getCurrent () {
		return getCurrent(User.class);
	}
	
	public static <T extends User> T getCurrent (Class<T> clazz) {
		T result = null;
		result = GlobalContext.getCurrentUser(clazz);
		return result;
	}

	@Entity
	@Table(name="APP_USERPERMISSION")
	@org.hibernate.annotations.DynamicInsert(value = true)
	@org.hibernate.annotations.DynamicUpdate(value = true)
	//@MappedSuperclass
	//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
	public static class UserPermission extends AbstractEnabledPersistEntity<UserPermission> {
		
		public UserPermission () {
			super();
		}
		
		protected void initiate () {
			super.initiate();
			
			initiatePermission();
			initiateUser();
		}

		protected Permission permission;

		protected void initiatePermission() {
			this.permission = null;
		}

		@javax.persistence.Column(name = "PERMISSION")
		@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		@Enumerated(EnumType.STRING)
		public Permission getPermission() {
			return this.permission;
		}

		public void setPermission(Permission newValue) {
			Permission oldValue = this.getPermission();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.permission = newValue;
				firePropertyChange("permission", oldValue, newValue);
			}
		}
		
		protected User user;

		protected void initiateUser() {
			this.user = null;
		}

	    @ManyToOne
	    @JoinColumn(name="USER_ID", insertable=false, updatable=false, nullable=false)	    
		public User getUser() {
			return this.user;
		}

		public void setUser(User newValue) {
			User oldValue = this.getUser();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.user = newValue;
				firePropertyChange("user", oldValue, newValue);
				changeUser(oldValue, newValue);
			}
		}
		
		protected void changeUser(User oldValue, User newValue) {
			setFdUserName((newValue != null ? newValue.getName() : null));
			setFdUserCode((newValue != null ? newValue.getCode() : null));
			setFdUserAlias((newValue != null ? newValue.getAlias() : null));
			setFdUserDescription((newValue != null ? newValue.getDescription() : null));
		}

		protected String fdUserName;

		protected void initiateFdUserName() {
			this.fdUserName = null;
		}

		@javax.persistence.Column(name = "FD_USERNAME")
		@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		public String getFdUserName() {
			return this.fdUserName;
		}

		public void setFdUserName(String newValue) {
			String oldValue = this.getFdUserName();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.fdUserName = newValue;
				firePropertyChange("fdUserName", oldValue, newValue);
			}
		}
		
		protected String fdUserCode;

		protected void initiateFdUserCode() {
			this.fdUserCode = null;
		}

		@javax.persistence.Column(name = "FD_USERCODE")
		@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		public String getFdUserCode() {
			return this.fdUserCode;
		}

		public void setFdUserCode(String newValue) {
			String oldValue = this.getFdUserCode();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.fdUserCode = newValue;
				firePropertyChange("fdUserCode", oldValue, newValue);
			}
		}
		
		protected String fdUserAlias;

		protected void initiateFdUserAlias() {
			this.fdUserAlias = null;
		}

		@javax.persistence.Column(name = "FD_USERALIAS")
		@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		public String getFdUserAlias() {
			return this.fdUserAlias;
		}

		public void setFdUserAlias(String newValue) {
			String oldValue = this.getFdUserAlias();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.fdUserAlias = newValue;
				firePropertyChange("fdUserAlias", oldValue, newValue);
			}
		}
		
		protected String fdUserDescription;

		protected void initiateFdUserDescription() {
			this.fdUserDescription = null;
		}

		@javax.persistence.Column(name = "FD_USERDESCRIPTION")
		@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		public String getFdUserDescription() {
			return this.fdUserDescription;
		}

		public void setFdUserDescription(String newValue) {
			String oldValue = this.getFdUserDescription();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.fdUserDescription = newValue;
				firePropertyChange("fdUserDescription", oldValue, newValue);
			}
		}
		
	}
	
}
