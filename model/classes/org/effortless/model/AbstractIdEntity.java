package org.effortless.model;

import java.io.Serializable;

import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import org.effortless.core.ObjectUtils;
import org.hibernate.Session;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@MappedSuperclass
//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
public class AbstractIdEntity<Type extends AbstractIdEntity<Type>> extends AbstractEntity<Type> implements IdEntity {

	public AbstractIdEntity () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateId();
		initiateVersion();
		initiateDeleted();
	}
	
	@Override
	@javax.persistence.Transient
	public boolean isSameVersion() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void _updateLogChanges (String propertyName, Object oldValue, Object newValue) {
		if (true || this.id != null) {
			super._updateLogChanges(propertyName, oldValue, newValue);
		}
	}
	
	public boolean hasBeenCreated() {
		return (this.id == null);
	}
	
	public boolean hasBeenDeleted() {
		boolean result = true;
		result = result && this._changes != null;
		result = result && this._changes.hasChange("deleted");
		return result;
	}
	
	@Override
	public boolean hasId () {
		boolean result = false;
		Integer id = this.getId();
		result = (id != null);
		return result;
	}
	
	@Override
	public boolean exists() {// throws ModelException {
		boolean result = false;
		Integer id = this.getId();
		if (id != null) {
			Session session = doGetSession();
			Object object = session.get(getClass(), id);
			result = (object != null);
		}
		return result;
	}
	
	protected boolean _doRunCheckLoaded () {
		return getId() != null;
	}
	
	
	
	protected Integer id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	@Override
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(generator="sequence_id", strategy=GenerationType.SEQUENCE)
	@javax.persistence.Column(name="ID")
	public Integer getId() {
		return this.id;
	}

//	@Override
	protected void setId(Integer newValue) {
		if (true) {
			_setProperty("id", this.id, this.id = newValue);
		}
		else {
			Integer oldValue = this.id;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.id = newValue;
				firePropertyChange("id", oldValue, newValue);
			}
		}
	}

	protected Integer version;
	
	protected void initiateVersion () {
		this.version = null;
	}
	
	@Override
	@javax.persistence.Version
	@javax.persistence.Column(name="VERSION")
	public Integer getVersion() {
		return this.version;
	}

//	@Override
	protected void setVersion(Integer newValue) {
		if (true) {
			_setProperty("version", this.version, this.version = newValue);
		}
		else {
			Integer oldValue = this.version;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.version = newValue;
				firePropertyChange("version", oldValue, newValue);
			}
		}
	}

	protected Boolean deleted;
	
	protected void initiateDeleted () {
		this.deleted = Boolean.FALSE;
	}
	
	@Override
	@javax.persistence.Column(name="DELETED")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Boolean getDeleted() {
		return this.deleted;
	}

//	@Override
	protected void setDeleted(Boolean newValue) {
		if (true) {
			_setProperty("deleted", this.deleted, this.deleted = newValue);
		}
		else {
			Boolean oldValue = this.deleted;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.deleted = newValue;
				firePropertyChange("deleted", oldValue, newValue);
			}
		}
	}

	protected void _applyDelete (Session session) {// throws ModelException {
		this.setDeleted(Boolean.TRUE);
		session.update(this);
	}
	
	protected void _applyUndelete (Session session) {// throws ModelException {
		this.setDeleted(Boolean.FALSE);
		session.update(this);
	}
	
	public Serializable doGetIdentifier () {
		return getId();
	}
	
	
	protected LogData _createLogData () {
		LogData result = null;
		result = super._createLogData();

		Object author = result.getAuthor();
		try {
			AbstractIdEntity idEntity = (AbstractIdEntity)author;
			if (idEntity != null) {
				result.setAuthorId(idEntity.getId());
			}
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
			try {
				String text = (String)author;
				if (text != null) {
					result.setAuthorCode(text);
				}
			}
			catch (ClassCastException e2) {
			}
		}
		
		
		Object target = result.getTarget();
		try {
			AbstractIdEntity idEntity = (AbstractIdEntity)target;
			if (idEntity != null) {
				Integer id = idEntity.getId();
				result.setTargetId(id);
			}
		}
		catch (ClassCastException e) {
		}
		try {
			Referenciable ref = (Referenciable)target;
			if (ref != null) {
				result.setTargetCode(ref.toLabel());
			}
		}
		catch (ClassCastException e) {
			try {
				String text = (String)target;
				if (text != null) {
					result.setTargetCode(text);
				}
			}
			catch (ClassCastException e2) {
			}
		}
		
		return result;
	}

	protected void doWrite(Kryo kryo, Output out) {
		super.doWrite(kryo, out);
		kryo.writeObjectOrNull(out, this.id, Integer.class);
		kryo.writeObjectOrNull(out, this.version, Integer.class);
		kryo.writeObjectOrNull(out, this.deleted, Boolean.class);
	}
	
	protected void doRead(Kryo kryo, Input in) {
		super.doRead(kryo, in);
		this.id = kryo.readObjectOrNull(in, Integer.class);
		this.version = kryo.readObjectOrNull(in, Integer.class);
		this.deleted = kryo.readObjectOrNull(in, Boolean.class);
	}
	
	public Type clone () {
		Type result = null;
		result = super.clone();
//		result._changes = this._changes;
//		result._fcs = this._fcs;
//		result.attributes = this.attributes;
		result.deleted = this.deleted;
//		result.id = this.id;
//		result.version = this.version;
		return result;
	}
	
}
