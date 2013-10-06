package org.effortless.model;

//import jutils.app.exceptions.ModelException;

public interface RestrictionsEntity {

	public void checkCreate ();// throws ModelException;
	
	public void checkCreate (String properties);// throws ModelException;
	
	public void checkUpdate ();// throws ModelException;
	
	public void checkUpdate (String properties);// throws ModelException;
	
	public boolean checkAccessible ();// throws ModelException;

	public void checkErase ();// throws ModelException;
	
	public void checkErase (String properties);// throws ModelException; 															

	public void checkDelete ();// throws ModelException;

	public void checkDelete (String properties);// throws ModelException;

	public void checkUndelete ();// throws ModelException;

	public void checkUndelete (String properties);// throws ModelException;

	public Boolean isReadonly ();
	
	public void checkPersist ();// throws ModelException;

	public void checkPersist (String properties);// throws ModelException;
	
	public boolean doCanSaveFinalData(boolean check, boolean create, boolean update);// throws ModelException;

	public boolean doCanLoadFinalData();// throws ModelException;

	public boolean checkForUpdate ();// throws ModelException;
	
	public void checkCreateCopy ();// throws ModelException;
	
	public void checkCreateCopy (String properties);// throws ModelException; 													

	public boolean checkPropertyLoadSecurity (String propertyName);// throws SecurityException;

	public boolean checkPropertySaveSecurity (String propertyName);// throws SecurityException;

	public boolean checkPropertySecurity (String propertyName);// throws SecurityException;

//	public boolean checkPropertySecurity (String propertyName, PropertyResourceMode mode);// throws SecurityException;

}
