package org.effortless.model;

import org.effortless.core.ModelException;

public interface IdEntity extends MarkDeleted {

	public Integer getId();
	
//	public void setId (Integer newValue);
	
	public Integer getVersion();
	
//	public void setVersion (Integer newValue);
	
	public Boolean getDeleted();
	
	public void setDeleted (Boolean newValue);

	public boolean isSameVersion() throws ModelException;
	
	
}
