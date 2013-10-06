package org.effortless.model;

import java.util.Date;

public interface AutoEntity<Type extends AutoEntity<Type>> {

	public void refresh ();// throws ModelException;
	
	public void refresh (String properties);// throws ModelException;

//	public void refresh (boolean forceRefresh);// throws ModelException;
//	
//	public void refresh (String properties, boolean forceRefresh);// throws ModelException;

	
	
	public boolean hasChanges ();// throws ModelException;
	
	public boolean hasChanges (String properties);// throws ModelException;

//	public long doSaveProperties(String properties, boolean check, boolean create, boolean update, LogChanges changes);// throws ModelException;
	
//	public boolean isSameVersion ();// throws ModelException;


	
	public void createIfNotExists ();// throws ModelException;
	
	
	
	public void create ();// throws ModelException;
			
	public void create (boolean validate);// throws ModelException;
					
	public void create (boolean validate, boolean enableOnPre);// throws ModelException;

	public void create (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
						
					
									
	public Type createCopy ();// throws ModelException;
	
	public Type createCopy (String properties);// throws ModelException;

	public void copy (Type source, String properties);// throws ModelException;
	
	public void copy (Type source);// throws ModelException;

	
	
	
	public void erase ();// throws ModelException;

	public void erase (boolean validate);// throws ModelException;														
	
	public void doEraseProperties ();// throws ModelException;

	

												
	public void delete ();// throws ModelException;

	public void doDeleteProperties ();// throws ModelException;

//	public void saveDelete (boolean save);// throws ModelException;
																
//	public void deleteBind ();// throws ModelException;

	
	
	
	public void undelete();// throws ModelException;

	public void undelete(boolean validate);// throws ModelException;

	public void undelete(boolean validate, boolean enableOnPre);// throws ModelException;

	public void undelete(boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
	
	
	
	public void cancelChanges ();

	public void cancelChanges (String properties);
	
	//Usa el log para volver al estado anterior
	public void restore ();// throws ModelException;

	//Usa el log para volver al estado del día indicado
	public void restore (Date date);// throws ModelException;

	public void restore(LogData log);// throws ModelException;
	
	public boolean hasId ();// throws ModelException;
	
	public boolean exists ();// throws ModelException;

//	public java.lang.Integer getDefaultPageSize ();// throws ModelException;

	public Type reload ();// throws ModelException;

//	public boolean saveProperties (String properties, boolean validate, boolean create);// throws ModelException;




	
	public void persist ();// throws ModelException;

	public void persist (boolean validate);// throws ModelException;

	public void persist (boolean validate, boolean enableOnPre);// throws ModelException;

	public void persist (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void persist (String properties);// throws ModelException;

	public void persist (String properties, boolean validate);// throws ModelException;

	public void persist (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void persist (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
		
	
	
	
	
	public void update ();// throws ModelException;

	public void update (boolean validate);// throws ModelException;
	
	public void update (boolean validate, boolean enableOnPre);// throws ModelException;

	public void update (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void update (String properties);// throws ModelException;

	public void update (String properties, boolean validate);// throws ModelException;

	public void update (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void update (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	
	
//	public boolean doCheckPropertyPersist ();// throws ModelException;
//
//	public Boolean doGetPropertyPersist ();// throws ModelException;
//
//	public void doSetPropertyPersist (Boolean newValue);// throws ModelException;

	public void erase(boolean validate, boolean enableOnPre);// throws ModelException;

	public void erase(boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void delete(boolean validate);// throws ModelException;

	public void delete(boolean validate, boolean enableOnPre);// throws ModelException;

	public void delete(boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	
	

	
	
	public void resync ();// throws ModelException;

	public void resync (boolean validate);// throws ModelException;

	public void resync (boolean validate, boolean enableOnPre);// throws ModelException;

	public void resync (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void resync (String properties);// throws ModelException;

	public void resync (String properties, boolean validate);// throws ModelException;

	public void resync (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void resync (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
		
	

	public Type doClone ();

	public Type doClone (String properties);
	
	public void copyTo (Type target);

	public void copyTo (String properties, Type target);
	
	
}
