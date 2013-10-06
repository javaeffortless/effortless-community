package org.effortless.model;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//import org.effortless.MySession;
import org.effortless.core.ModelException;
import org.effortless.core.ObjectUtils;
import org.effortless.core.PropertyUtils;
import org.effortless.model.log.ListPropertyChanges;
import org.effortless.model.log.LogChanges;
import org.effortless.model.restrictions.Restriction;
import org.effortless.security.Resource;
import org.effortless.security.SecurityResponse;
import org.effortless.security.SecuritySeverity;
import org.effortless.security.SecuritySystem;
import org.effortless.security.resources.ResourceType;
import org.hibernate.Session;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

//import jejbutils.filter.Filter;
//import jejbutils.security.resources.PropertyResourceMode;

//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
@MappedSuperclass
public abstract class AbstractEntity<Type extends AbstractEntity<Type>> extends Object implements Entity<Type>, Referenciable {

	public static final String ERROR = "error_";
	protected static final String CREATE = "create";
	protected static final String ERROR_CREATE = ERROR + CREATE;
	
	public AbstractEntity () {
		super();
		initiate();
	}
	
//	protected String text;
//	protected void setText (String newValue) {
//		_setProperty("text", this.text, this.text = newValue);
//	}

	protected Object _setProperty (String propertyName, Object oldValue, Object newValue) {
		boolean _loaded = checkLoaded(propertyName, true);
//		String oldValue = (_loaded ? this.getText() : null);
		if (!_loaded || !_equals(oldValue, newValue)) {
//			this.text = newValue;
			if (_loaded) {
				_doChangeProperty(propertyName, oldValue, newValue);
				firePropertyChange(propertyName, oldValue, newValue);
			}
		}
		return oldValue;
	}

	protected void _doChangeProperty (String propertyName, Object oldValue, Object newValue) {
	}
	
	protected void initiate () {
		this._fcs = null;
		initiateAttributes();
	}
	
	public Serializable doGetIdentifier () {
		Serializable result = null;
		Session session = doGetSession();
		result = session.getIdentifier(this);
		return result;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this._doGetFcs().addPropertyChangeListener(listener);
	}
	
	public boolean containsPropertyChangeListener(PropertyChangeListener listener) {
		return this._doGetFcs().containsPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this._doGetFcs().removePropertyChangeListener(listener);
	}
	
	@javax.persistence.Transient
	public List<PropertyChangeListener> getPropertyChangeListeners() {
		return this._doGetFcs().getPropertyChangeListeners();
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this._doGetFcs().addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this._doGetFcs().removePropertyChangeListener(propertyName, listener);
	}
	
	public List<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
		return this._doGetFcs().getPropertyChangeListeners(propertyName);
	}
	
	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		return this._doGetFcs().containsPropertyChangeListener(propertyName, listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		_updateLogChanges(propertyName, oldValue, newValue);
		this._doGetFcs().firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public void firePropertyChange(PropertyChangeEvent evt) {
		this._doGetFcs().firePropertyChange(evt);
	}
	
	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
		this._doGetFcs().fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}
	
	public boolean hasListeners(String propertyName) {
		return this._doGetFcs().hasListeners(propertyName);
	}
	
	
	
	
	
	
	
	
	
	
	protected Restriction doGetCreateRestriction () {
		return null;
	}
	
	protected Restriction doGetUpdateRestriction () {
		return doGetCreateRestriction();
	}
	
	protected Restriction doGetEraseRestriction () {
		return null;
	}
	
	protected Restriction doGetDeleteRestriction () {
		return null;
	}
	
	protected Restriction doGetUndeleteRestriction () {
		return null;
	}
	
	protected Restriction doGetPersistRestriction () {
		return null;
	}
	
	public void checkCreate() {// throws ModelException {
		checkCreate(null);
	}

	public void checkCreate(String properties) {// throws ModelException {
		Restriction restriction = doGetCreateRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public void checkUpdate() {// throws ModelException {
		checkUpdate(null);
	}

	public void checkUpdate(String properties) {// throws ModelException {
		Restriction restriction = doGetUpdateRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public boolean checkAccessible() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkErase() {// throws ModelException {
		checkErase(null);
	}

	@Override
	public void checkErase(String properties) {// throws ModelException {
		Restriction restriction = doGetEraseRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public void checkDelete() {// throws ModelException {
		checkDelete(null);
	}

	@Override
	public void checkDelete(String properties) {// throws ModelException {
		Restriction restriction = doGetDeleteRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public void checkUndelete() {// throws ModelException {
		checkUndelete(null);
	}

	@Override
	public void checkUndelete(String properties) {// throws ModelException {
		Restriction restriction = doGetUndeleteRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	@Override
	@javax.persistence.Transient
	public Boolean isReadonly() {
		// TODO Auto-generated method stub
		return null;
	}

	public void checkPersist() {// throws ModelException {
		checkPersist(null);
	}

	@Override
	public void checkPersist(String properties) {// throws ModelException {
		Restriction restriction = doGetPersistRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	
//	public static class LogChangesEntityListener {
//
//	    @PrePersist
//	    public void prePersist(Object object) {
//	        if(object instanceof Timestamped){
//	            Timestamped timestamped = (Timestamped) object;
//	            Date now = new Date();
//	            timestamped.setCreated(now);
//	            timestamped.setUpdated(now);
//	        }
//	    }
//
//	    @PreUpdate
//	    public void preUpdate(Object object) {
//	        if(object instanceof Timestamped){
//	            Timestamped timestamped = (Timestamped) object;
//	            timestamped.setUpdated(new Date());
//	        }
//	    }
//	}	
	
	
	
	
	@Override
	public boolean doCanSaveFinalData(boolean check, boolean create,
			boolean update) {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCanLoadFinalData() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	@Override
	public boolean checkForUpdate() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkCreateCopy() {// throws ModelException {
		checkCreateCopy(null);
	}

	@Override
	public void checkCreateCopy(String properties) {// throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPropertyLoadSecurity(String propertyName) {//throws SecurityException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPropertySaveSecurity(String propertyName) {// throws SecurityException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPropertySecurity(String propertyName) {// throws SecurityException {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean checkPropertySecurity(String propertyName, PropertyResourceMode mode) {// throws SecurityException {
//		// TODO Auto-generated method stub
//		return false;
//	}

	
	
	
	@Override
	public Object onPreSaveReplace() {// throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enableNotifyChanges() {
		this._doGetFcs().enableNotifyChanges();
	}

	@Override
	public void enableDisableChangeEvents() {
		this._doGetFcs().enableDisableChangeEvents();
	}

	@Override
	public void disableDisableChangeEvents() {
		this._doGetFcs().disableDisableChangeEvents();
	}

	@Override
	public void disableNotifyChanges() {
		this._doGetFcs().disableNotifyChanges();
	}

	
	
	
	
	public void onPreCreate() {// throws ModelException {
	}

	public void onPostCreate() {// throws ModelException {
	}

	
	public void onPreRead() {// throws ModelException {
	}

	public void onPostRead() {// throws ModelException {
	}


	public void onPreUpdate() {// throws ModelException {
	}

	public void onPostUpdate() {// throws ModelException {
	}

	
	public void onPreUndelete() {// throws ModelException {
	}
	
	public void onPostUndelete() {// throws ModelException {
	}
	
	
	public void onPreDelete() {// throws ModelException {
	}

	public void onPostDelete() {// throws ModelException {
	}

	public void onPreErase() {// throws ModelException {
	}

	public void onPostErase() {// throws ModelException {
	}

	public void onPreDeleteErase() {// throws ModelException {
	}

	public void onPostDeleteErase() {// throws ModelException {
	}

	
	public void onPrePersist() {// throws ModelException {
	}

	public void onPostPersist() {// throws ModelException {
	}

	protected boolean _equals (Object value1, Object value2) {
		return ObjectUtils.equals(value1, value2);
	}
	
	@javax.persistence.Transient
	protected transient FieldChangeSupport _fcs;
	
	protected FieldChangeSupport _doGetFcs () {
		if (this._fcs == null) {
			this._fcs = new FieldChangeSupport(this);
		}
		return this._fcs;
	}
	
	protected boolean _doRunCheckLoaded () {
		return hasId();
	}
	
	public boolean checkLoaded(String property, boolean save) {
		boolean result = false;
		if (_doRunCheckLoaded()) {
			result = _doGetFcs().checkLoaded(property, save);
		}
		else {
			_doGetFcs().checkLoaded(property, save);
			result = true;
		}
		return result;
	}

	public boolean unloadProperty(String property) {
		return _doGetFcs().unloadProperty(property);
	}

	public boolean unloadProperty(String property, Object oldValue, Object newValue,
			boolean notify) {
		return _doGetFcs().unloadProperty(property, oldValue, newValue, notify);
	}

	public boolean unloadProperty(String property, boolean notify) {
		return _doGetFcs().unloadProperty(property, notify);
	}

	public void unloadProperties() {
		_doGetFcs().unloadProperties();
	}

	public String doGetLoadedProperties() {
		return _doGetFcs().doGetLoadedProperties();
	}

	public boolean checkRead(String property, boolean force) {
		return _doGetFcs().checkRead(property, force);
	}

	public void unreadProperty(String property) {
		_doGetFcs().unreadProperty(property);
	}

	public void unreadProperties() {
		_doGetFcs().unreadProperties();
	}

	public String doGetReadProperties() {
		return _doGetFcs().doGetReadProperties();
	}

	
	
	
	
	
	@Override
	public boolean doCheckLogCreate() {
		return true;
	}

	@Override
	public boolean doCheckLogRead() {
		return false;
	}

	@Override
	public boolean doCheckLogUpdate() {
		return true;
	}

	@Override
	public boolean doCheckLogUpdateChanges() {
		return true;
	}

	@Override
	public boolean doCheckLogDelete() {
		return true;
	}

	@Override
	public boolean doCheckLogUndelete() {
		return true;
	}

	@Override
	public boolean doCheckLogErase() {
		return true;
	}

	@Override
	public boolean doCheckLog() {
		return true;
	}

	protected LogData _newInstanceLogData () {
		return new LogData();
	}
	
	protected LogData _createLogData () {
		LogData result = null;
		
		Entity<?> author = SessionManager.getLogAuthor();
		String locationKeyFrom = SessionManager.getLogLocationKeyFrom();
		String locationAliasFrom = SessionManager.getLogLocationAliasFrom();
		String locationDescriptionFrom = SessionManager.getLogLocationDescriptionFrom();
		String targetType = getClass().getSimpleName();
		java.util.Date currentDate = new java.util.Date();
		
		LogData log = _newInstanceLogData();

		log.setAuthor(author);
//		log.setAuthorId(newValue);
		
		log.setDate(currentDate);
		log.setDeleted(Boolean.FALSE);

		log.setComment(null);
		log.setExecutionTime(null);
		log.setLocationAliasFrom(locationAliasFrom);
		log.setLocationDescriptionFrom(locationDescriptionFrom);
		log.setLocationKeyFrom(locationKeyFrom);
		log.setPending(Boolean.FALSE);
		
		log.setTarget(this);
//		log.setTargetId(newValue)
		
		log.setTargetType(targetType);
		log.setType(null);
		
		result = log;

		try {
			Integer authorId = (Integer)(author != null ? author.doGetIdentifier() : null);
			result.setAuthorId(authorId);
		}
		catch (ClassCastException e) {
		}
		
		try {
			Referenciable ref = (Referenciable)author;
			if (ref != null) {
				result.setAuthorCode(ref.toLabel());
			}
		}
		catch (ClassCastException e) {
		}
		
		try {
			Integer targetId = (Integer)this.doGetIdentifier();
			result.setTargetId(targetId);
		}
		catch (ClassCastException e) {
		}
		
		try {
			Referenciable ref = (Referenciable)this;
			if (ref != null) {
				result.setTargetCode(ref.toLabel());
			}
		}
		catch (ClassCastException e) {
		}
		
		
		return result;
	}
	
	@Override
	public void saveLogException(Throwable e, String actionName) {// throws ModelException {
		saveLogException(e, actionName, null);
	}

	public void saveLogException(Throwable e, String actionName, Long time) {// throws ModelException {
		if (this.doCheckLogCreate()) {
			String comment = (e != null ? e.getMessage() : null);
			_saveLog(actionName, comment, time);
		}
	}

	@Override
	public void saveActionLog(String actionName) {// throws ModelException {
		saveActionLog(actionName, null, null);
	}

	@Override
	public void saveActionLog(String actionName, String comment) {// throws ModelException {
		saveActionLog(actionName, comment, null);
	}

	@Override
	public void saveActionLog(String actionName, Long time) {// throws ModelException {
		_saveLog(actionName, null, time);
	}

	@Override
	public void saveActionLog(String actionName, String comment, Long time) {// throws ModelException {
		if (this.doCheckLogCreate()) {
			_saveLog(actionName, comment, time);
		}
	}

	public static final String ACTION_CREATE_LOG = "create";
	public static final String ACTION_READ_LOG = "read";
	public static final String ACTION_UPDATE_LOG = "update";
	public static final String ACTION_DELETE_LOG = "delete";
	public static final String ACTION_UNDELETE_LOG = "undelete";
	public static final String ACTION_ERASE_LOG = "erase";
	
//	@Override
	protected void saveCreateLog (Long time) {// throws ModelException {
		if (this.doCheckLogCreate()) {
			_saveLog(ACTION_CREATE_LOG, null, time);
		}
	}

//	@Override
	protected void saveReadLog(Long time) {// throws ModelException {
		if (this.doCheckLogRead()) {
			_saveLog(ACTION_READ_LOG, null, time);
		}
	}

//	@Override
	protected void saveUpdateLog(Long time) {// throws ModelException {
		if (this.doCheckLogUpdate()) {
			String comment = (this._changes != null ? this._changes.toText() : null);
			_saveLog(ACTION_UPDATE_LOG, comment, time);
			this._changes = null;
		}
	}

//	@Override
//	public void saveUpdateLogChanges(LogChanges changes) throws ModelException {
//		if (this.doCheckLog() && this.doCheckLogUpdate()) {
//			_saveLog(ACTION_UPDATE_LOG);
//		}
//	}
//
//	@Override
//	public void trySaveUpdateLogChanges(LogChanges changes)
//			throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
	protected void saveDeleteLog(Long time) {// throws ModelException {
		if (this.doCheckLogDelete()) {
			_saveLog(ACTION_DELETE_LOG, null, time);
		}
	}

//	@Override
	protected void saveUndeleteLog(Long time) {// throws ModelException {
		if (this.doCheckLogUndelete()) {
			_saveLog(ACTION_UNDELETE_LOG, null, time);
		}
	}

//	@Override
	protected void saveEraseLog(Long time) {// throws ModelException {
		if (this.doCheckLogErase()) {
			_saveLog(ACTION_ERASE_LOG, null, time);
		}
	}
	
	protected void _saveLog (String actionName, String comment, Long time) {// throws ModelException {
		if (this.doCheckLog()) {
			LogData log = _createLogData();
			log.setType(actionName);
			log.setComment(comment);
			log.setExecutionTime(time);
			log.persist();
		}
	}

	@javax.persistence.Transient
	public String getEntityName () {
		return getClass().getSimpleName();
	}

	protected Integer _loadDefaultPageSize () {
//		Integer pageSize = SessionManager.loadDefaultPageSize(this);
		return Integer.valueOf(25);
	}
	
	protected List<LogData> _listLogs(String type) {// throws ModelException {
		List<LogData> result = null;
		if (doCheckLog()) {
			String targetType = getEntityName();
			Serializable targetId = null;
			Integer pageSize = _loadDefaultPageSize();
	
			Filter<LogData> filter = LogData.listBy().eq("type", type).eq("targetType", targetType).eq("targetId", targetId).sortBy("date DESC");
	//		GenericLogDataFilter filter = new GenericLogDataFilter(targetType, targetId, type);
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
			filter.setPageSize(pageSize);
			
			result = filter;
		}
		return result;
	}
	
	protected LogData _loadLog(String type, boolean last) {// throws ModelException {
		LogData result = null;
		if (doCheckLog()) {
			String targetType = getEntityName();
			Serializable targetId = null;
	
			Filter<LogData> filter = LogData.listBy().eq("type", type).eq("targetType", targetType).eq("targetId", targetId).sortBy("date DESC");
	//		GenericLogDataFilter filter = new GenericLogDataFilter(targetType, targetId, type);
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
			filter.setPageSize(Integer.valueOf((last ? 1 : 2)));
			try {
				result = filter.get((last ? 0 : 1));
			}
			catch (Exception e) {
				
			}
		}
		return result;
	}
	
	
	
	
	@Override
	@javax.persistence.Transient
	public LogData getLogCreation() {// throws ModelException {
		return _loadLog(ACTION_CREATE_LOG, true);
	}

	@Override
	@javax.persistence.Transient
	public List<LogData> getLogChanges() {// throws ModelException {
		return _listLogs(ACTION_UPDATE_LOG);
	}
	
	@Override
	@javax.persistence.Transient
	public LogData getLogLastChange() {// throws ModelException {
		return _loadLog(ACTION_UPDATE_LOG, true);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogPrevLastChange() {// throws ModelException {
		return _loadLog(ACTION_UPDATE_LOG, false);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogLastAction(String actionName) {// throws ModelException {
		return _loadLog(actionName, true);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogPrevLastAction(String actionName) {// throws ModelException {
		return _loadLog(actionName, false);
	}

	@Override
	@javax.persistence.Transient
	public List<LogData> getLogActions(String actionName) {// throws ModelException {
		return _listLogs(actionName);
	}

	@Override
	@javax.persistence.Transient
	public List<LogData> getLogDeletes() {// throws ModelException {
		return _listLogs(ACTION_DELETE_LOG);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogLastDelete() {// throws ModelException {
		return _loadLog(ACTION_DELETE_LOG, true);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogPrevLastDelete() {// throws ModelException {
		return _loadLog(ACTION_DELETE_LOG, false);
	}

	@Override
	@javax.persistence.Transient
	public List<LogData> getLogReads() {// throws ModelException {
		return _listLogs(ACTION_READ_LOG);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogLastRead() {// throws ModelException {
		return _loadLog(ACTION_READ_LOG, true);
	}

	@Override
	@javax.persistence.Transient
	public LogData getLogPrevLastRead(Object data) {// throws ModelException {
		return _loadLog(ACTION_READ_LOG, false);
	}

//	@Override
//	public Number countLogBy(Filter filter) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<LogData> listLogBy(Filter filter) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public LogData findLogBy(Filter filter) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	
	
	
	
	
	
	@Override
	public void refresh() {// throws ModelException {
		this.refresh(null);
	}

	@Override
	public void refresh(String properties) {// throws ModelException {
		if (this._changes != null) {
			this._doGetFcs().enableDisableChangeEvents();
			properties = (properties != null ? properties.trim() : "");
			if (properties.length() > 0) {
				String[] arrayProperties = properties.split(",");
				for (String itemProperty : arrayProperties) {
					this._changes.restore(this, itemProperty);
				}
			}
			else {
				this._changes.restore(this);
				this._changes = null;
			}
		}
	}
	
//	@Override
//	public void refresh(boolean forceRefresh) {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void refresh(String properties, boolean forceRefresh) {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

	
	
	
	
	
	@Override
	public boolean hasChanges() {// throws ModelException {
		return this.hasChanges(null);
	}

	@Override
	public boolean hasChanges(String properties) {// throws ModelException {
		boolean result = false;
		if (this._changes != null) {
			properties = (properties != null ? properties.trim() : "");
			if (properties.length() > 0) {
				String[] arrayProperties = properties.split(",");
				for (String property : arrayProperties) {
					result = this._changes.hasChange(property);
					if (result) {
						break;
					}
				}
			}
			else {
				result = this._changes.hasChanges();
			}
		}
		return result;
	}

//	@Override
//	public long doSaveProperties(String properties, boolean check, boolean create, boolean update, LogChanges changes) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return 0;
//	}

//	@Override
//	public boolean isSameVersion() {// throws ModelException {
//		// TODO Auto-generated method stub
//		return false;
//	}

	
	
	
	@Override
	public void createIfNotExists() {// throws ModelException {
		if (!hasId()) {
			create();
		}
	}

	
	@Override
	public void create() {// throws ModelException {
		create(true, true, true);
	}

	@Override
	public void create(boolean validate) {// throws ModelException {
		create(validate, true, true);
	}

	@Override
	public void create(boolean validate, boolean enableOnPre) {// throws ModelException {
		create(validate, enableOnPre, true);
	}

	protected Long __startExecutionTime () {
		return (doCheckLog() ? SessionManager.startExecutionTime() : null);
	}
	
	protected Long __stopExecutionTime () {
		return (doCheckLog() ? SessionManager.stopExecutionTime() : null);
	}

	@Override
	public void create(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (this._checkSecurityAction(CREATE)) {
			__startExecutionTime();
			boolean __stopExecutionTime = false;
			Long _executionTime = null;
			String txId = null;
			try {
				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkCreate();
					this.checkPersist();
				}
				if (enableOnPre) {
					this.onPreCreate();
					this.onPrePersist();
				}
				this.doSavePreviousProperties(null, validate, true);
				this.doCreate();
				this.doSaveProperties(null, validate, true);
				this.doSaveFinalProperties(null, validate, true);
				this.endSaveProperties(null, validate, true);
		//		Session session = MySession.loadSession(this);
		//		session.clear();
				if (enableOnPost) {
					this.onPostCreate();
					this.onPostPersist();
				}
				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveCreateLog(_executionTime);
			}
			catch (ModelException e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
				throw e;
			}
			catch (Exception e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
				throw new ModelException(e);
			}
		}
	}

	protected boolean doCheckErrorSaveLog () {
		return false;
	}
	
	protected void trySaveLogException(Throwable e, String actionName, Long time) {
		try {
			saveLogException(e, actionName, time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}

	protected void trySaveActionLog(String actionName, String comment, Long time) {// throws ModelException {
		try {
			saveActionLog(actionName, comment, time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}
	
	protected void trySaveCreateLog(Long time) {
		try {
			saveCreateLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}

	protected void doCreate() {// throws ModelException {
		Session session = doGetSession();
		session.save(this);
	}
	
	@Override
	public Type createCopy() {// throws ModelException {
		return createCopy(null);
	}

	@Override
	public Type createCopy(String properties) {// throws ModelException {
		Type result = null;
		try {
			result = (Type) this.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new ModelException(e);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		}
		copy(result, properties);
		return result;
	}

	@Override
	public void copy(Type source, String properties) {// throws ModelException {
		if (source != null) {
			properties = (properties != null ? properties.trim() : "");
			if (properties.length() > 0) {
				String[] arrayProperties = (properties != null ? properties.split(",") : null);
				for (String property : arrayProperties) {
					PropertyUtils.setProperty(source, property, PropertyUtils.getProperty(this, property));
				}
			}
			else {
				PropertyUtils.copyProperties(source, this);
			}
		}
	}

	@Override
	public void copy(Type source) {// throws ModelException {
		copy(source, null);
	}

	
	
	@Override
	public void erase() {// throws ModelException {
		this.erase(true, true, true);
	}

	@Override
	public void erase(boolean validate) {// throws ModelException {
		this.erase(validate, true, true);
	}

	@Override
	public void erase(boolean validate, boolean enableOnPre) {// throws ModelException {
		erase(validate, enableOnPre, true);
	}

	@Override
	public void erase(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (this._checkSecurityAction("erase")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkErase();
				}
				if (enableOnPre) {
					this.onPreErase();
					this.onPreDeleteErase();
				}
				this.doEraseProperties();
				this.doErase();
				if (enableOnPost) {
					this.onPostErase();
					this.onPostDeleteErase();
				}
				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveEraseLog(_executionTime);
			}
			catch (ModelException e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new ModelException(e);
			}
		}
	}
	
	protected void trySaveEraseLog(Long time) {
		try {
			saveEraseLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}

	protected void doErase() {// throws ModelException {
		Session session = doGetSession();
		session.delete(this);
	}
	
	
	@Override
	public void doEraseProperties() {// throws ModelException {
		// TODO Auto-generated method stub
		
	}

	
	
	
	@Override
	public void delete() {// throws ModelException {
		// TODO Auto-generated method stub
		this.delete(true, true, true);
	}

	@Override
	public void delete(boolean validate) {// throws ModelException {
		this.delete(validate, true, true);
	}

	@Override
	public void delete(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.delete(validate, enableOnPre, true);
	}

	@Override
	public void delete(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (this._checkSecurityAction("delete")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkDelete();
				}
				if (enableOnPre) {
					this.onPreDelete();
					this.onPreDeleteErase();
				}
				this.doDeleteProperties();
				this.doDelete();
				if (enableOnPost) {
					this.onPostDelete();
					this.onPostDeleteErase();
				}
				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveDeleteLog(_executionTime);
			}
			catch (ModelException e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new ModelException(e);
			}
		}
	}
	
	protected void trySaveDeleteLog(Long time) {
		try {
			saveDeleteLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}

	protected void doDelete() {// throws ModelException {
		Session session = doGetSession();
		_applyDelete(session);
	}

	protected void _applyDelete (Session session) {// throws ModelException {
		session.delete(this);
	}
	
	
	@Override
	public void doDeleteProperties() {// throws ModelException {
		// TODO Auto-generated method stub
		
	}

	protected void deleteCollection (List<Entity> list) {// throws ModelException {
		if (list != null) {
			int length = list.size();
			for (int i = length - 1; i >= 0; i--) {
				Entity entity = list.get(i);
				entity.delete();
			}
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	@Override
	public void undelete() {// throws ModelException {
		this.undelete(true, true, true);
	}

	@Override
	public void undelete(boolean validate) {// throws ModelException {
		this.undelete(validate, true, true);
	}

	@Override
	public void undelete(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.undelete(validate, enableOnPre, true);
	}

	@Override
	public void undelete(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (this._checkSecurityAction("undelete")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkUndelete();
				}
				if (enableOnPre) {
					this.onPreUndelete();
				}
				this.doUndelete();
	
				if (enableOnPost) {
					this.onPostUndelete();
				}
				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveUndeleteLog(_executionTime);
			}
			catch (ModelException e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new ModelException(e);
			}
		}
	}
	
	protected void trySaveUndeleteLog(Long time) {
		try {
			saveUndeleteLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}

	protected void doUndelete() {// throws ModelException {
		Session session = doGetSession();
		_applyUndelete(session);
	}

	protected void _applyUndelete (Session session) {// throws ModelException {
		session.update(this);
	}
	
	
	
	
//	@Override
//	public void saveDelete(boolean save) {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void deleteBind() {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

	
	@Override
	public void cancelChanges () {
		if (this._changes != null) {
			this._changes.restore(this);
		}
	}
	
	@Override
	public void cancelChanges (String properties) {
		if (this._changes != null ) {
			if (properties == null) {
				this._changes.restore(this);
			}
			else {
				String[] arrayProperties = properties.split(",");
				for (String property : arrayProperties) {
					this._changes.restore(this, property);
				}
			}
		}
	}
	
	@Override
	public void restore() {// throws ModelException {
		restore((Date)null);
	}

	@Override
	public void restore(Date date) {// throws ModelException {
		LogData log = null;
		if (date != null) {
			String targetType = getEntityName();
			Serializable targetId = null;
			
			Filter<LogData> filter = LogData.listBy().eq("type", AbstractEntity.ACTION_UPDATE_LOG).eq("targetType", targetType).eq("targetId", targetId).le("date", date).sortBy("date DESC");
			
//			GenericLogDataChangeByDateFilter filter = new GenericLogDataChangeByDateFilter(targetType, targetId, date);
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
			filter.setPageSize(Integer.valueOf(1));
			try {
				log = filter.get(0);
			}
			catch (Exception e) {
				
			}
		}
		else {
			log = this.getLogLastChange();
		}
		if (log != null) {
			restore(log);
		}
	}

	@Override
	public void restore(LogData log) {// throws ModelException {
		if (log != null) {
			if (this._checkSecurityAction("restore")) {
				LogChanges changes = (LogChanges)log.getExtraData();
				changes.restore(this);
			}
		}
	}
	
	@Override
	public boolean hasId () {
		return exists();
	}
	
	@Override
	public boolean exists() {// throws ModelException {
		boolean result = false;
		Session session = doGetSession();
		Serializable id = session.getIdentifier(this);
		Object object = (id != null ? session.get(getClass(), id) : null);
		result = (object != null);
		return result;
	}

//	@Override
//	@javax.persistence.Transient
//	public Integer getDefaultPageSize() {// throws ModelException {
//		Integer result = null;
//		result = MySession.loadDefaultPageSize(this);
//		return result;
//	}

	
	
	
	@Override
	public Type reload() {// throws ModelException {
		Type result = null;
		Session session = doGetSession();
//		session.merge(this);
		Serializable id = session.getIdentifier(this);
		session.evict(this);
		result = (id != null ? (Type)session.get(getClass(), id) : null);
		session.merge(this);
		return result;
	}

//	@Override
	protected boolean doSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}

	protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}

	
//	@Override
	protected boolean doSaveFinalProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}

	protected boolean endSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		return result;
	}
	
	protected <T extends Entity<?>>boolean doSaveList (Class<T> clazz, List<T> list, boolean validate, boolean create) {
		boolean result = true;
		ListPropertyChanges lpc = null;
		boolean exclusive = false;
		try {
			lpc = (ListPropertyChanges)list;
			list = lpc.toPersist();
			exclusive = lpc.isExclusive();
		}
		catch (ClassCastException e) {
		}
		if (list != null && exclusive) {
			Iterator<T> iterator = list.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					Entity<?> item = iterator.next();
					if (item != null) {
						if (item.hasBeenCreated()) {
							ChildEntity child = null;
							try {
								child = (ChildEntity)item;
							}
							catch (ClassCastException e) {
							}
							if (child != null) {
								child.doSetOwner(this);
							}
							item.create(validate);
						}
						else if (item.hasBeenDeleted()) {
							item.delete(validate);
						}
						else if (item.hasBeenChanged()) {
							ChildEntity child = null;
							try {
								child = (ChildEntity)item;
							}
							catch (ClassCastException e) {
							}
							if (child != null) {
								child.doSetOwner(this);
							}
							item.update(validate);
						}
					}
				}
			}
		}
		if (lpc != null) {
			lpc.clearPersist();
		}
		return result;
	}
	
	
	
	@Override
	public void persist() {// throws ModelException {
		this.persist(null, true, true, true);
	}

	@Override
	public void persist(boolean validate) {// throws ModelException {
		this.persist(null, validate, true, true);
	}

	@Override
	public void persist(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.persist(null, validate, enableOnPre, true);
	}

	@Override
	public void persist(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		this.persist(null, validate, enableOnPre, enableOnPost);
	}

	@Override
	public void persist(String properties) {// throws ModelException {
		this.persist(properties, true, true, true);
	}

	@Override
	public void persist(String properties, boolean validate) {// throws ModelException {
		this.persist(properties, validate, true, true);
	}

	@Override
	public void persist(String properties, boolean validate, boolean enableOnPre) {// throws ModelException {
		this.persist(properties, validate, enableOnPre, true);
	}

	@Override
	public void persist(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (hasId()) {
			update(properties, validate, enableOnPre, enableOnPost);
		}
		else {
			create(validate, enableOnPre, enableOnPost);
		}
	}
	
	
	
	@Override
	public void update() {// throws ModelException {
		this.update(null, true, true, true);
	}

	@Override
	public void update(boolean validate) {// throws ModelException {
		this.update(null, validate, true, true);
	}

	@Override
	public void update(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.update(null, validate, enableOnPre, true);
	}

	@Override
	public void update(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		this.update(null, validate, enableOnPre, enableOnPost);
	}

	@Override
	public void update(String properties) {// throws ModelException {
		this.update(properties, true, true, true);
	}

	@Override
	public void update(String properties, boolean validate) {// throws ModelException {
		this.update(properties, validate, true, true);
	}

	@Override
	public void update(String properties, boolean validate, boolean enableOnPre) {// throws ModelException {
		this.update(properties, validate, enableOnPre, true);
	}

	@Override
	public void update(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (this._checkSecurityAction("update")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkUpdate(properties);
				}
				if (enableOnPre) {
					this.onPreUpdate();
					this.onPrePersist();
				}
				if (this.hasChanges(properties)) {
					this.doSavePreviousProperties(properties, validate, false);
					this.doUpdate();
					this.doSaveProperties(properties, validate, false);
					this.doSaveFinalProperties(null, validate, false);
					this.endSaveProperties(null, validate, false);
					if (enableOnPost) {
						this.onPostUpdate();
						this.onPostPersist();
					}
					SessionManager.endTransaction(this, txId);
					_executionTime = __stopExecutionTime();
					__stopExecutionTime = true;
					this.trySaveUpdateLog(_executionTime);
				}
				else {
					SessionManager.endTransaction(this, txId);
					__stopExecutionTime();
					__stopExecutionTime = true;
				}
			}
			catch (ModelException e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new ModelException(e);
			}
		}
	}
	
	protected void trySaveUpdateLog(Long time) {
		try {
			saveUpdateLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}

	@javax.persistence.Transient
	protected transient LogChanges _changes;
	
	protected void _initChanges () {
		this._changes = null;
	}
	
	protected void _updateLogChanges (String propertyName, Object oldValue, Object newValue) {
		this._changes = (this._changes != null ? this._changes : new LogChanges());
		this._changes.addChange(propertyName, oldValue, newValue, false);
	}

	protected Session doGetSession () {
		return SessionManager.loadSession(this);
	}
	
	protected void doUpdate() {// throws ModelException {
		Session session = doGetSession();
//		boolean flagMerge = true;
//		if (flagMerge) {
//			session.merge(this);
//		}
		session.update(this);
//		catch (org.hibernate.StaleObjectStateException e) {
			//version
	}

//	public static <Type extends Object> List<Type> listBy (Class<Type> clazz, String methodName, Object... parameters) {
//		List<Type> result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		result = (List<Type>)filter;
//		return result;
//	}

	public static <Type extends Entity<Type>> Filter<Type> listBy (Class<Type> clazz) {
		Filter<Type> result = null;
		result = new EntityFilter<Type>(clazz);
		return result;
	}
	
//	public static <Type extends Object> List<Type> listBy (Class<Type> clazz, String methodName, String orderBy, Object... parameters) {
//		List<Type> result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, orderBy, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		result = (List<Type>)filter;
//		return result;
//	}

//	public static <Type extends Object> Type findBy (Class<Type> clazz, String methodName, Object... parameters) {
//		Type result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		try {
//			result = (Type)filter.get(0);
//		}
//		catch (ModelException e) {
//		}
//
//		return result;
//	}
	
//	public static <Type extends Object> Type findBy (Class<Type> clazz, String methodName, String orderBy, Object... parameters) {
//		Type result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, orderBy, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		try {
//			result = (Type)filter.get(0);
//		}
//		catch (ModelException e) {
//		}
//
//		return result;
//	}
	
//	public static <Type extends Object> Long countBy (Class<Type> clazz, String methodName, Object... parameters) {
//		Long result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		result = Long.valueOf(filter.size());
//
//		return result;
//	}

	public boolean hasBeenChanged() {
		boolean result = true;
		result = result && !hasBeenCreated();
		result = result && !hasBeenDeleted();
		result = result && this._changes != null;
		result = result && this._changes.hasChanges();
		return result;
	}
	
	public boolean hasBeenCreated() {
		return !hasId();
	}
	
	public abstract boolean hasBeenDeleted();

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected Map<String, Object> attributes;

	protected void initiateAttributes() {
		this.attributes = null;
	}

	@javax.persistence.Transient
	protected Map<String, Object> getAttributes() {
		return this.attributes;
	}

	protected void setAttributes(Map<String, Object> newValue) {
		Map<String, Object> oldValue = getAttributes();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.attributes = newValue;
			firePropertyChange("attributes", oldValue, newValue);
		}
	}

	public boolean existsAttribute (String attribute) {
		boolean result = false;
		Map<String, Object> attributes = getAttributes();
		result = (attributes != null && attributes.containsKey(attribute));
		return result;
	}
	
	@javax.persistence.Transient
	public <T extends Object> T getAttribute (Class<T> clazz, String attribute, T defaultValue) {
		T result = defaultValue;
		Map<String, Object> attributes = getAttributes();
		if (attributes != null && attributes.containsKey(attribute)) {
			result = (T)attributes.get(attribute);
		}
		return result;
	}

	@javax.persistence.Transient
	public Object getAttribute (String attribute, Object defaultValue) {
		return getAttribute(Object.class, attribute, defaultValue);
	}
	
	@javax.persistence.Transient
	public Object getAttribute (String attribute) {
		return getAttribute(Object.class, attribute, null);
	}
	
	@javax.persistence.Transient
	public <T extends Object> T getAttribute (Class<T> clazz, String attribute) {
		return getAttribute(clazz, attribute, null);
	}
	
	public void setAttribute (String attribute, Object newValue) {
		Object oldValue = getAttribute(Object.class, attribute, null);
		if (!ObjectUtils.equals(oldValue, newValue) || (newValue != null && oldValue == null)) {
			Map<String, Object> attributes = getAttributes();
			if (attributes == null) {
				attributes = new HashMap<String, Object>();
				setAttributes(attributes);
			}
			attributes.put(attribute, newValue);
			firePropertyChange(attribute, oldValue, newValue);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void resync() {// throws ModelException {
		this.resync(null, true, true, true);
	}

	@Override
	public void resync(boolean validate) {// throws ModelException {
		this.resync(null, validate, true, true);
	}

	@Override
	public void resync(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.resync(null, validate, enableOnPre, true);
	}

	@Override
	public void resync(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		this.resync(null, validate, enableOnPre, enableOnPost);
	}

	@Override
	public void resync(String properties) {// throws ModelException {
		this.resync(properties, true, true, true);
	}

	@Override
	public void resync(String properties, boolean validate) {// throws ModelException {
		this.resync(properties, validate, true, true);
	}

	@Override
	public void resync(String properties, boolean validate, boolean enableOnPre) {// throws ModelException {
		this.resync(properties, validate, enableOnPre, true);
	}

	@Override
	public void resync(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (this._checkSecurityAction("resync")) {
			persist(properties, validate, enableOnPre, enableOnPost);
		}
	}

	
	
    protected Class<Type> doLoadRealClass() {
    	Class<Type> result = null;
    	java.lang.reflect.Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        result = (Class<Type>)(paramType.getActualTypeArguments()[0]);
        return result;
    }
	
	public Type doClone () {
		return doClone(null);
	}

	public Type doClone (String properties) {
		Type result = null;
		try {
			Class<Type> clazz = doLoadRealClass();
			result = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new ModelException(e);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		}
		this.copyTo(properties, result);
		return result;
	}
	
	public void copyTo (Type target) {
		copyTo(null, target);
	}

	public void copyTo (String properties, Type target) {
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean checkSecurityAction (Class<?> clazz, String actionName) {
		return checkSecurityAction(clazz, actionName, null, null);
	}
	
	public static boolean checkSecurityAction (Class<?> clazz, String actionName, Class<?> returnType, Class<?>[] parameters) {
		return false;
	}
	
	protected boolean _checkSecurityAction (String actionName) {
		return _checkSecurityAction(actionName, null, null, null);
	}
	
	protected boolean _checkSecurityAction (String actionName, Class<?> returnType, Class<?>[] parameters, Object[] values) {
		boolean result = true;
		SecuritySystem securitySystem = SessionManager.getSecuritySystem();
		if (securitySystem != null) {
			Resource resource = new Resource();
			resource.setObject(this);
			resource.setName(actionName);
			resource.setType(ResourceType.ACTION);
			resource.setReturnType(returnType);
			resource.setParamTypes(parameters);
			
			SecurityResponse response = securitySystem.check(resource);
			if (response != null && !response.isAllow()) {
				SecuritySeverity severity = response.getSeverity();
				if (SecuritySeverity.ERROR.equals(severity)) {
					response.throwSecurityException();
				}
				else if (SecuritySeverity.NULL.equals(severity)) {
					result = false;
				}
				else {
					response.throwSecurityException();
				}
			}
		}
		return result;
	}

	public void onRead () {
		trySaveReadLog(null);		
	}

	protected void trySaveReadLog(Long time) {
		try {
			saveReadLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new ModelException(e2);
			}
		}
	}
	

	
	public String toLabel () {
		return toLabel(null);
	}
	
	public String toDescription () {
		return toDescription(null);
	}
	
	public String toImage () {
		return toImage(null);
	}

	public String toLabel (Locale locale) {
		return null;
	}
	
	public String toDescription (Locale locale) {
		return null;
	}
	
	public String toImage (Locale locale) {
		return null;
	}

	
//	public String doAction(String text, Integer ammount) {// throws ModelException {
//		String result = null;
//		Class<?>[] paramTypes = new Class[] {String.class, Integer.class};
//		Object[] paramValues = new Object[] {text, ammount};
//		Object[] paramNamesValues = new Object[] {"text", text, "ammount", ammount};
//		if (this._checkSecurityAction("doAction", String.class, paramTypes, paramValues)) {
//			__startExecutionTime();
//			boolean __stopExecutionTime = false;
//			Long __executionTime = null;
//			boolean __saveLog = true;
//			String __commentLog = _toCommentLog(paramNamesValues);
//			try {
//				System.out.println("EJECUTANDO doAction");
//				__executionTime = __stopExecutionTime();
//				__stopExecutionTime = true;
//				if (__saveLog) {
//					this.trySaveActionLog("doAction", __commentLog, __executionTime);
//				}
//			}
//			catch (ModelException e) {
//				if (!__stopExecutionTime) {
//					__executionTime = __stopExecutionTime();
//				}
//				this.trySaveLogException(e, ERROR + "doAction", __executionTime);
//				throw e;
//			}
//			catch (Exception e) {
//				if (!__stopExecutionTime) {
//					__executionTime = __stopExecutionTime();
//				}
//				this.trySaveLogException(e, ERROR + "methodName", __executionTime);
//				throw new ModelException(e);
//			}
//		}
//		return result;
//	}

	protected String __toCommentLog(Object[] objects) {
		String result = null;
		if (objects != null) {
			result = "";
			for (int i = 0; i < objects.length; i += 2) {
				Object name = objects[i];
				Object value = objects[i + 1];

				String nameText = (name != null ? name.toString() : "");
				String valueText = (value != null ? value.toString() : "");
				
				nameText = (nameText != null ? nameText : "");
				valueText = (valueText != null ? valueText : "");
				
				String property = nameText + ":" + valueText;
				property = (property != null ? property : "");
				result += (result.length() > 0 && property.length() > 0 ? ", " : "") + property;
			}
		}
		return result;
	}

	public int hashCode () {
		int result = 0;
		Serializable identifier = this.doGetIdentifier();
		org.apache.commons.lang3.builder.HashCodeBuilder hcBuilder = new org.apache.commons.lang3.builder.HashCodeBuilder();
		if (identifier != null) {
			hcBuilder.append(identifier);
		}
		else {
			doHashCode(hcBuilder);
		}
		result = hcBuilder.toHashCode();
		return result;
	}

	protected void doHashCode(org.apache.commons.lang3.builder.HashCodeBuilder hcBuilder) {
		// TODO Auto-generated method stub
	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		if (!getClass().isInstance(obj)) {
			result = false;
		}
		else if (this == obj) {
			result = true;
		}
		else {
			Serializable id1 = this.doGetIdentifier();
			Serializable id2 = ((AbstractEntity)obj).doGetIdentifier();
			org.apache.commons.lang3.builder.EqualsBuilder eqBuilder = new org.apache.commons.lang3.builder.EqualsBuilder();
			if (id1 != null && id2 != null) {
				eqBuilder.append(id1, id2);
			}
			else {
				this.doEquals(eqBuilder, obj);
			}
			result = eqBuilder.isEquals();
		}
		return result;
	}

	protected void doEquals(EqualsBuilder eqBuilder, Object obj) {
//		super.doEquals(eqBuilder, obj);
//		eqBuilder.append(this.name, obj.name);
//        .appendSuper(super.equals(obj))
//        .append(field1, rhs.field1)
//        .append(field2, rhs.field2)
//        .append(field3, rhs.field3)
//        .isEquals();
	}
	public int compareTo(Object obj) {
		int result = -1;
		if (!getClass().isInstance(obj)) {
			result = -1;
		}
		else if (this == obj) {
			result = 0;
		}
		else {
			Serializable id1 = this.doGetIdentifier();
			Serializable id2 = ((AbstractEntity)obj).doGetIdentifier();
			org.apache.commons.lang3.builder.CompareToBuilder compareBuilder = new org.apache.commons.lang3.builder.CompareToBuilder();
			if (id1 != null && id2 != null) {
				compareBuilder.append(id1, id2);
			}
			else {
				this.doCompare(compareBuilder, obj);
			}
			result = compareBuilder.toComparison();
		}
		return result;
	}

	protected void doCompare(CompareToBuilder cpBuilder, Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString () {
		String result = null;
		
		org.apache.commons.lang3.builder.ToStringBuilder toStringBuilder = new org.apache.commons.lang3.builder.ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		doToString(toStringBuilder);

		result = toStringBuilder.toString();
		
		if (result == null) {
			result = toLabel();
			result = (result != null ? result : super.toString());
		}
		
		return result;
	}

	protected void doToString(ToStringBuilder toStringBuilder) {
		
	}

	@javax.persistence.Transient
	public Boolean getCheckHasId () {
		Boolean result = null;
		Serializable id = doGetIdentifier();
		result = Boolean.valueOf((id != null));
		return result;
	}
	
	protected Kryo _doGetKryo () {
		Kryo result = null;
//		result = MySession.get
		if (result == null) {
			result = new Kryo();
		}
		return result;
	}
	
	public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
		Kryo kryo = _doGetKryo();
		ObjectOutputStream oos = (ObjectOutputStream)output;
		Output kryoOutput = new Output(oos);
		doWrite(kryo, kryoOutput);
		output.close();		
	}
	  
	protected void doWrite(Kryo kryo, Output out) {
		kryo.writeObjectOrNull(out, this._changes, LogChanges.class);
		kryo.writeObjectOrNull(out, this._fcs, FieldChangeSupport.class);
		kryo.writeObjectOrNull(out, this.attributes, Map.class);
	}

	public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
		Kryo kryo = _doGetKryo();
	
		ObjectInputStream ois = (ObjectInputStream)input;
		Input kryoInput = new Input(ois);
		doRead(kryo, kryoInput);
		input.close();		
	}

	protected void doRead(Kryo kryo, Input in) {
		this._changes = kryo.readObjectOrNull(in, LogChanges.class);
		this._fcs = kryo.readObjectOrNull(in, FieldChangeSupport.class);
		this.attributes = kryo.readObjectOrNull(in, Map.class);
	}
	
	public byte[] toBytes () throws IOException {
		byte[] result = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(baos);
		o.writeObject(this);
		o.close();
		result = baos.toByteArray();
		return result;
	}
	
//	public byte[] fromBytes (byte[] bytes) throws IOException {
//		byte[] result = null;
//		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//		ObjectInputStream o = new ObjectInputStream(bais);
//		o.readObject();
//		o.close();
//		result = baos.toByteArray();
//		return result;
//	}
	
	public Type clone () {
		Type result = null;
		result = createClone();
		return result;
	}
	
	protected Type createClone () {
		return null;
	}
	
}
