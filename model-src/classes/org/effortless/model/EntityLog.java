package org.effortless.model;

public interface EntityLog {

	public boolean doCheckLogCreate ();
	
	public boolean doCheckLogRead ();
	
	public boolean doCheckLogUpdate ();
	
	public boolean doCheckLogUpdateChanges ();
	
	public boolean doCheckLogDelete ();
	
	public boolean doCheckLogUndelete ();
	
	public boolean doCheckLogErase ();
	
	public boolean doCheckLog ();

	public void saveLogException (Throwable e, String actionName);// throws ModelException;
	
	public void saveLogException (Throwable e, String actionName, Long time);// throws ModelException;
	
	public void saveActionLog (String actionName);// throws ModelException;

	public void saveActionLog (String actionName, String comment);// throws ModelException;

	public void saveActionLog (String actionName, Long time);// throws ModelException;

	public void saveActionLog (String actionName, String comment, Long time);// throws ModelException;

//	public void trySaveActionLog (String actionName);// throws ModelException;
//
//	public void trySaveActionLog (String actionName, String comment);// throws ModelException;
//
//	public void trySaveActionLog (String actionName, Long time);// throws ModelException;
//
//	public void trySaveActionLog (String actionName, String comment, Long time);// throws ModelException;

//	public void saveCreateLog ();// throws ModelException;

//	public void trySaveCreateLog ();// throws ModelException;

//	public void saveReadLog ();// throws ModelException;

//	public void trySaveReadLog ();// throws ModelException;

//	public void saveUpdateLog ();// throws ModelException;

//	public void trySaveUpdateLog ();// throws ModelException;

//	public void saveUpdateLogChanges (LogChanges changes);// throws ModelException;

//	public void trySaveUpdateLogChanges (LogChanges changes);// throws ModelException;

//	public void saveDeleteLog ();// throws ModelException;

//	public void trySaveDeleteLog ();// throws ModelException;

//	public void saveUndeleteLog ();// throws ModelException;
	
//	public void trySaveUndeleteLog ();// throws ModelException;
	
//	public void saveEraseLog ();// throws ModelException;

//	public void trySaveEraseLog ();// throws ModelException;

	public LogData getLogCreation ();// throws ModelException;

	public java.util.List getLogChanges ();// throws ModelException;

	public LogData getLogLastChange ();// throws ModelException;

	public LogData getLogPrevLastChange ();// throws ModelException;

	public LogData getLogLastAction (String actionName);// throws ModelException;

	public LogData getLogPrevLastAction (String actionName);// throws ModelException;

	public java.util.List getLogActions (String actionName);// throws ModelException;

	public java.util.List getLogDeletes ();// throws ModelException;

	public LogData getLogLastDelete ();// throws ModelException;

	public LogData getLogPrevLastDelete ();// throws ModelException;

	public java.util.List getLogReads ();// throws ModelException;

	public LogData getLogLastRead ();// throws ModelException;

	public LogData getLogPrevLastRead (Object data);// throws ModelException;

//	public java.lang.Number countLogBy (jejbutils.filter.Filter filter);// throws ModelException; 
//
//	public java.util.List listLogBy (jejbutils.filter.Filter filter);// throws ModelException;
//
//	public LogData findLogBy (jejbutils.filter.Filter filter);// throws ModelException;

}
