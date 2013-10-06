package org.effortless.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.persistence.MappedSuperclass;

import org.effortless.core.ObjectUtils;
import org.effortless.util.ToLabel;
import org.hibernate.Session;

//@Entity
//@Table(name="APP_LOGS")
//@javax.persistence.SequenceGenerator(name="sequence_id", sequenceName="app_logs")
@MappedSuperclass
public class LogData extends AbstractIdEntity<LogData> {

	public LogData () {
		super();
	}
	
	public static Filter<LogData> listBy () {
		return AbstractEntity.listBy(LogData.class);
	}
	
	public static final String KEY_CLASS_NEEDS = "needs_" + LogData.class.getName() + "_class";
	public static final String KEY_APP_NEEDS = "needs_" + LogData.class.getName() + "_app";
	
	protected void initiate () {
		super.initiate();
		initiateType();
		initiateTargetCode();
		initiateAuthorCode();
		initiateTargetType();
		initiateTargetId();
		initiateAuthorId();
		initiateTarget();
		initiateAuthor();
		initiateDate();

		initiateLocationKeyFrom();
		initiateLocationDescriptionFrom();
		initiateLocationAliasFrom();
		initiateComment();
		initiateExecutionTime();
		initiatePending();
		
		initiateExtraData();
	}
	
	public boolean doCheckLog() {
		return false;
	}
	
	protected String type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	@javax.persistence.Column(name="LOG_TYPE", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getType() {
		return this.type;
	}
	
	public void setType(String newValue) {
		String oldValue = this.type;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.type = newValue;
			firePropertyChange("type", oldValue, newValue);
		}
	}

	protected String targetCode;
	
	protected void initiateTargetCode () {
		this.targetCode = null;
	}
	
	@javax.persistence.Column(name="LOG_TARGET_CODE", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getTargetCode() {
		return this.targetCode;
	}

	public void setTargetCode(String newValue) {
		String oldValue = this.targetCode;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.targetCode = newValue;
			firePropertyChange("targetCode", oldValue, newValue);
		}
	}

	protected String authorCode;
	
	protected void initiateAuthorCode () {
		this.authorCode = null;
	}
	
	@javax.persistence.Column(name="LOG_AUTHOR_CODE", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getAuthorCode() {
		return this.authorCode;
	}

	public void setAuthorCode(String newValue) {
		String oldValue = this.authorCode;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.authorCode = newValue;
			firePropertyChange("authorCode", oldValue, newValue);
		}
	}

	protected String targetType;
	
	protected void initiateTargetType () {
		this.targetType = null;
	}
	
	@javax.persistence.Column(name="LOG_TARGET_TYPE", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String newValue) {
		String oldValue = this.targetType;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.targetType = newValue;
			firePropertyChange("targetType", oldValue, newValue);
		}
	}

	protected Integer targetId;
	
	protected void initiateTargetId () {
		this.targetId = null;
	}
	
	@javax.persistence.Column(name="LOG_TARGET")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Integer getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Integer newValue) {
		Integer oldValue = this.targetId;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.targetId = newValue;
			firePropertyChange("targetId", oldValue, newValue);
		}
	}

	protected Integer authorId;
	
	protected void initiateAuthorId () {
		this.authorId = null;
	}
	
	@javax.persistence.Column(name="LOG_AUTHOR")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Integer getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Integer newValue) {
		Integer oldValue = this.authorId;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.authorId = newValue;
			firePropertyChange("authorId", oldValue, newValue);
		}
	}

	@javax.persistence.Transient
	protected org.effortless.model.Entity<?> target;
	
	protected void initiateTarget () {
		this.target = null;
	}
	
	@javax.persistence.Transient
	public org.effortless.model.Entity<?> getTarget() {
		this.target = (this.target != null ? this.target : loadTarget());
		return this.target;
	}

	public void setTarget(org.effortless.model.Entity<?> newValue) {
		org.effortless.model.Entity<?> oldValue = this.target;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.target = newValue;
			firePropertyChange("target", oldValue, newValue);
		}
	}

	@javax.persistence.Transient
	protected org.effortless.model.Entity<?> author;
	
	protected void initiateAuthor () {
		this.author = null;
	}
	
	@javax.persistence.Transient
	public org.effortless.model.Entity<?> getAuthor() {
		this.author = (this.author != null ? this.author : loadAuthor());
		return this.author;
	}

	public void setAuthor(org.effortless.model.Entity<?> newValue) {
		org.effortless.model.Entity<?> oldValue = this.author;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.author = newValue;
			firePropertyChange("author", oldValue, newValue);
		}
	}

	
	protected Date date;
	
	protected void initiateDate () {
		this.date = null;
	}
	
	@javax.persistence.Column(name="LOG_DATE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date newValue) {
		Date oldValue = this.date;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.date = newValue;
			firePropertyChange("date", oldValue, newValue);
		}
	}
	
	protected String locationKeyFrom;
	
	protected void initiateLocationKeyFrom () {
		this.locationKeyFrom = null;
	}
	
	@javax.persistence.Column(name="LOG_LOCATION_KEY_FROM", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getLocationKeyFrom() {
		return this.locationKeyFrom;
	}
	
	public void setLocationKeyFrom (String newValue) {
		String oldValue = this.locationKeyFrom;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.locationKeyFrom = newValue;
			firePropertyChange("locationKeyFrom", oldValue, newValue);
		}
	}

	protected String locationDescriptionFrom;
	
	protected void initiateLocationDescriptionFrom () {
		this.locationDescriptionFrom = null;
	}
	
	@javax.persistence.Column(name="LOG_LOCATION_DESCRIPTION_FROM", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getLocationDescriptionFrom() {
		return this.locationDescriptionFrom;
	}
	
	public void setLocationDescriptionFrom (String newValue) {
		String oldValue = this.locationDescriptionFrom;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.locationDescriptionFrom = newValue;
			firePropertyChange("locationDescriptionFrom", oldValue, newValue);
		}
	}

	protected String locationAliasFrom;
	
	protected void initiateLocationAliasFrom () {
		this.locationAliasFrom = null;
	}
	
	@javax.persistence.Column(name="LOG_LOCATION_ALIAS_FROM", length=255)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getLocationAliasFrom() {
		return this.locationAliasFrom;
	}
	
	public void setLocationAliasFrom (String newValue) {
		String oldValue = this.locationAliasFrom;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.locationAliasFrom = newValue;
			firePropertyChange("locationAliasFrom", oldValue, newValue);
		}
	}

	protected Long __startExecutionTime () {
		return null;
	}
	
	protected Long __stopExecutionTime () {
		return null;
	}
	
	protected String comment;
	
	protected void initiateComment () {
		this.comment = null;
	}
	
	@javax.persistence.Column(name="LOG_COMMENT", length=3072)
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getComment() {
		return this.comment;
	}
	
	public void setComment (String newValue) {
		String oldValue = this.comment;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.comment = newValue;
			firePropertyChange("comment", oldValue, newValue);
		}
	}

	protected Long executionTime;
	
	protected void initiateExecutionTime () {
		this.executionTime = null;
	}
	
	@javax.persistence.Column(name="LOG_EXECUTION_TIME")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Long getExecutionTime () {
		return this.executionTime;
	}
	
	public void setExecutionTime (Long newValue) {
		Long oldValue = this.executionTime;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.executionTime = newValue;
			firePropertyChange("executionTime", oldValue, newValue);
		}
	}

	protected Boolean pending;
	
	protected void initiatePending () {
		this.pending = null;
	}
	
	@javax.persistence.Column(name="LOG_PENDING")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Boolean getPending () {
		return this.pending;
	}
	
	public void setPending (Boolean newValue) {
		Boolean oldValue = this.pending;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.pending = newValue;
			firePropertyChange("pending", oldValue, newValue);
		}
	}

	
	
	protected org.effortless.model.Entity<?> loadObject(Object id, String entityName) {
		org.effortless.model.Entity<?> result = null;
		if (id != null && entityName != null) {
			Session session = SessionManager.loadSession(this);
			result = (session != null ? (org.effortless.model.Entity<?>)session.get(entityName, (Serializable)id) : null);
		}
		return result;
	}
	
	protected org.effortless.model.Entity<?> loadTarget() {
		return loadObject(this.targetId, this.targetType);
	}

	protected org.effortless.model.Entity<?> loadAuthor() {
		return null;
//		return loadObject(this.authorId, this.authorType);
	}

	protected transient Object extraData;

	protected void initiateExtraData() {
		this.extraData = null;
	}

//	@javax.persistence.Column(name="LOG_EXTRA_DATA")
//	@javax.persistence.Basic(fetch=javax.persistence.FetchType.LAZY)
//	@org.hibernate.annotations.Type(type="org.effortless.model.ObjectUserType")
	@javax.persistence.Transient
	public Object getExtraData() {
		return this.extraData;
	}

	public void setExtraData(Object newValue) {
//		_setProperty("extraData", this.extraData, this.extraData = newValue);
		Object oldValue = this.extraData;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.extraData = newValue;
			firePropertyChange("extraData", oldValue, newValue);
		}
	}

	public String toLabel (Locale locale) {
		String result = null;
		ToLabel tl = new ToLabel(null);
		tl.add(getDate());
		tl.add(getType());
		tl.add(getAuthorCode());
		tl.add(getTargetType());
		tl.add(getTargetCode());
		result = tl.getText();
		return result;
	}

}
