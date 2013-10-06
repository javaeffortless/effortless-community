package org.effortless.model;

public interface EntityEvents {

	public Object onPreSaveReplace();// throws ModelException;

	public void enableNotifyChanges ();
	
	public void enableDisableChangeEvents ();

	public void disableDisableChangeEvents ();

	public void disableNotifyChanges ();
													
	public void onPostUpdate ();// throws ModelException;

	public void onPostRead();// throws ModelException;

	public void onPostErase();// throws ModelException;

	public void onPostDeleteErase();// throws ModelException;

	public void onPostDelete();// throws ModelException;

	public void onPostPersist();// throws ModelException;

	public void onPostCreate ();// throws ModelException;

	public void onPreUpdate ();// throws ModelException;

	public void onPreRead();// throws ModelException;

	public void onPreErase();// throws ModelException;

	public void onPreDeleteErase();// throws ModelException;

	
	public void onPreUndelete();// throws ModelException;
	
	public void onPostUndelete();// throws ModelException;
	
	
	public void onPreDelete();// throws ModelException;

	public void onPrePersist();// throws ModelException;

	public void onPreCreate ();// throws ModelException;

	public boolean checkLoaded(String property, boolean save);
	
	public boolean unloadProperty (String property);
	
	public boolean unloadProperty (String property, Object oldValue, Object newValue, boolean notify);
	
	public boolean unloadProperty (String property, boolean notify);
	
	public void unloadProperties ();
	
	public String doGetLoadedProperties ();

	public boolean checkRead(String property, boolean force);

	public void unreadProperty (String property);

	public void unreadProperties ();

	public String doGetReadProperties ();

}
