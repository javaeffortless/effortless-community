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
//import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.model.AbstractEnabledPersistEntity;
import org.effortless.model.AbstractIdEntity;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="APP_USERPROFILE")
@org.hibernate.annotations.DynamicInsert(value = true)
@org.hibernate.annotations.DynamicUpdate(value = true)
//@MappedSuperclass
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class UserProfile extends AbstractIdEntity<UserProfile> {

	public UserProfile () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateName();
		initiateCode();
		initiateAlias();
		initiateDescription();
		initiateComment();
		initiateLanguage();
		initiatePermissions();
		
		initiateCreationDate();
		initiateLastModification();
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

	@javax.persistence.Column(name = "USER_LANGUAGE", columnDefinition="")
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
	
	protected List<UserProfilePermission> permissions;

	protected void initiatePermissions() {
		this.permissions = null;
	}

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="PROFILE_ID")
	public List<UserProfilePermission> getPermissions() {
		this.permissions = (this.permissions != null ? this.permissions : new java.util.ArrayList<UserProfilePermission>());
		return this.permissions;
	}

	public void setPermissions(List<UserProfilePermission> newValue) {
		List<UserProfilePermission> oldValue = this.getPermissions();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.permissions = newValue;
			firePropertyChange("permissions", oldValue, newValue);
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
	}
	
	public void onPreUpdate () {
		super.onPreUpdate();
		Date lastModification = new Date();
		setLastModification(lastModification);
	}

	@Entity
	@Table(name="APP_USERPROFILEPERMISSION")
	@org.hibernate.annotations.DynamicInsert(value = true)
	@org.hibernate.annotations.DynamicUpdate(value = true)
	//@MappedSuperclass
	//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
	public static class UserProfilePermission extends AbstractEnabledPersistEntity<UserProfilePermission> {
		
		public UserProfilePermission () {
			super();
		}
		
		protected void initiate () {
			super.initiate();
			
			initiatePermission();
			initiateProfile();
			
			initiateFdProfileName();
			initiateFdProfileCode();
			initiateFdProfileAlias();
			initiateFdProfileDescription();
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
		
	}

}
