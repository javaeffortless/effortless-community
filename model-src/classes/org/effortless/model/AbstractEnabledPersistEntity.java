package org.effortless.model;

import org.effortless.core.ObjectUtils;

public class AbstractEnabledPersistEntity<Type extends AbstractEnabledPersistEntity<Type>> extends AbstractIdEntity<Type> implements EnabledPersistEntity {

	public AbstractEnabledPersistEntity () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateEnabled();
	}
	
	@Override
	public void create(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		Boolean enabled = isEnabled();
		if (enabled != null && enabled.booleanValue()) {
			super.create(validate, enableOnPre, enableOnPost);
		}
	}

	@Override
	public void update(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		Boolean enabled = isEnabled();
		if (enabled != null && enabled.booleanValue()) {
			setDeleted(Boolean.FALSE);
			super.update(properties, validate, enableOnPre, enableOnPost);
		}
		else {
			super.delete(validate, enableOnPre, enableOnPost);
		}
	}
	

	@javax.persistence.Transient
	protected transient Boolean enabled;
	
	protected void initiateEnabled () {
		this.enabled = null;
	}
	
	@javax.persistence.Transient
	public Boolean isEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (Boolean newValue) {
		Boolean oldValue = this.enabled;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.enabled = newValue;
			firePropertyChange("enabled", oldValue, newValue);
		}
	}
	
}
