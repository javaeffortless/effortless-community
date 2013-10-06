package org.effortless.ui.windows;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class Windows {

	public static EditorWindow openEditor (Object item, String editor) {
		EditorWindow result = null;
		Execution exec = Executions.getCurrent();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("value", item);
		result = (EditorWindow)exec.createComponents(editor, null, args);
		return result;
	}
	
	public static InfoWindow openInfo (Object item, String editor) {
		InfoWindow result = null;
		Execution exec = Executions.getCurrent();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("value", item);
		result = (InfoWindow)exec.createComponents(editor, null, args);
		return result;
	}
	
	public static FinderWindow openFinder (String finder) {
		FinderWindow result = null;
		Execution exec = Executions.getCurrent();
		Map<String, Object> args = new HashMap<String, Object>();
//		args.put("value", item);
		result = (FinderWindow)exec.createComponents(finder, null, args);
		return result;
	}
	
	public static Window openWindow (String windowId) {
		Window result = null;
		Execution exec = Executions.getCurrent();
		Map<String, Object> args = new HashMap<String, Object>();
//		args.put("value", item);
		result = (Window)exec.createComponents(windowId, null, args);
		return result;
	}
	
}
