package org.effortless.model.log;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public interface LogChangeItem extends Externalizable {

	public String getPropertyName ();
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
	
	public void writeExternal(ObjectOutput out) throws IOException;
	
	public String toText ();
	
	public void restore(Object target);
	
}
