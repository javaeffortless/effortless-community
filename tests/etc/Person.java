package org.effortless.tests.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.ObjectUtils;
import org.effortless.core.GlobalContext;
import org.effortless.model.AutoEntity;
import org.effortless.model.LogData;

@Entity
public class Person implements org.effortless.model.MarkDeleted, org.effortless.model.Entity {

	public Person () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateId();
		initiateVersion();
		initiateDeleted();
		initiateName();
	}

	protected Integer id;

	protected void initiateId() {
		this.id = null;
	}

	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	@javax.persistence.Column(name="id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer newValue) {
		Integer oldValue = this.id;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.id = newValue;
			refreshId(oldValue, newValue);
		}
	}

	protected void refreshId(Integer oldValue, Integer newValue) {
		
	}
	
	

	protected Integer version;

	protected void initiateVersion() {
		this.version = null;
	}

	@javax.persistence.Version
	@javax.persistence.Column(name = "VERSION")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer newValue) {
		Integer oldValue = this.version;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.version = newValue;
			refreshVersion(oldValue, newValue);
		}
	}

	protected void refreshVersion(Integer oldValue, Integer newValue) {
		
	}
	
	

	protected Boolean deleted;

	protected void initiateDeleted() {
		this.deleted = null;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean newValue) {
		Boolean oldValue = this.deleted;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.deleted = newValue;
			refreshDeleted(oldValue, newValue);
		}
	}

	protected void refreshDeleted(Boolean oldValue, Boolean newValue) {
		
	}
	
	

	protected String name;

	protected void initiateName() {
		this.name = null;
	}

	@javax.persistence.Column(name="NAME")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getName() {
		return this.name;
	}

	public void setName(String newValue) {
		String oldValue = this.name;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.name = newValue;
			refreshName(oldValue, newValue);
		}
	}

	protected void refreshName(String oldValue, String newValue) {
		
	}
	
	
//	public void persist () throws Exception {
////		GlobalContext.persist(this);
//	}
//	
//	public void delete () throws Exception {
////		GlobalContext.delete(this);
//	}
//	
//	public void erase () throws Exception {
////		GlobalContext.erase(this);
//	}
//
//	public void insert () throws Exception {
////		GlobalContext.insert(this);
//	}
//
//	public void create () throws Exception {
////		GlobalContext.create(this);
//	}
//
//	public void update () throws Exception {
////		GlobalContext.update(this);
//	}
//
//	public void refresh () throws Exception {
////		GlobalContext.refresh(this);
//	}
	
	public void merge () throws Exception {
//		GlobalContext.merge(this);
	}
	
	public static List<Person> listByName (String name) {
		List<Person> result = null;
		
		PersonFinderFilter filter = new PersonFinderFilter();
		filter.setPaginated(Boolean.TRUE);
		filter.setPageIndex(Integer.valueOf(0));
		filter.setPageSize(Integer.valueOf(25));
		filter.setName(name);
		result = filter;
		
		return result;
	}
	
	public String toString () {
		return this.name;
	}

	@Override
	public void checkCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkCreate(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkUpdate(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkAccessible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkErase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkErase(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkDelete(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkUndelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkUndelete(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isReadonly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkPersist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkPersist(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean doCanSaveFinalData(boolean check, boolean create,
			boolean update) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCanLoadFinalData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkForUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkCreateCopy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkCreateCopy(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPropertyLoadSecurity(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPropertySaveSecurity(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPropertySecurity(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object onPreSaveReplace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enableNotifyChanges() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableDisableChangeEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableDisableChangeEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableNotifyChanges() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostRead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostErase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostDeleteErase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostPersist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreRead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreErase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDeleteErase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreUndelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostUndelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrePersist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkLoaded(String property, boolean save) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadProperty(String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadProperty(String property, Object oldValue,
			Object newValue, boolean notify) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadProperty(String property, boolean notify) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unloadProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String doGetLoadedProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkRead(String property, boolean force) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unreadProperty(String property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unreadProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String doGetReadProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doCheckLogCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLogRead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLogUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLogUpdateChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLogDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLogUndelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLogErase() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCheckLog() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveLogException(Throwable e, String actionName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveLogException(Throwable e, String actionName, Long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveActionLog(String actionName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveActionLog(String actionName, String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveActionLog(String actionName, Long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveActionLog(String actionName, String comment, Long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LogData getLogCreation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getLogChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogLastChange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogPrevLastChange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogLastAction(String actionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogPrevLastAction(String actionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getLogActions(String actionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getLogDeletes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogLastDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogPrevLastDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getLogReads() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogLastRead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogPrevLastRead(Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChanges(String properties) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createIfNotExists() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AutoEntity createCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutoEntity createCopy(String properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void copy(AutoEntity source, String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(AutoEntity source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void erase(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doEraseProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDeleteProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undelete(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undelete(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undelete(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelChanges() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelChanges(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(LogData log) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasId() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AutoEntity reload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(String properties, boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(String properties, boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(String properties, boolean validate,
			boolean enableOnPre, boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String properties, boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String properties, boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String properties, boolean validate,
			boolean enableOnPre, boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void erase(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void erase(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(boolean validate, boolean enableOnPre,
			boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(String properties, boolean validate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(String properties, boolean validate, boolean enableOnPre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resync(String properties, boolean validate,
			boolean enableOnPre, boolean enableOnPost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AutoEntity doClone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutoEntity doClone(String properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void copyTo(AutoEntity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyTo(String properties, AutoEntity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable doGetIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsPropertyChangeListener(
			PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transient
	public List getPropertyChangeListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getPropertyChangeListeners(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void firePropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireIndexedPropertyChange(String propertyName, int index,
			Object oldValue, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasListeners(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBeenChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBeenCreated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBeenDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsAttribute(String attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getAttribute(Class clazz, String attribute,
			Object defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String attribute, Object defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(Class clazz, String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String attribute, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeExternal(ObjectOutput output) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readExternal(ObjectInput input) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getCheckHasId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void erase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
