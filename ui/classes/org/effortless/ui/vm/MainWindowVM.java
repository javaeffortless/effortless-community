package org.effortless.ui.vm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.codehaus.groovy.ast.expr.Expression;
import org.effortless.server.WebUtils;
import org.effortless.ui.Message;
import org.effortless.ui.ScreenInfo;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

public class MainWindowVM extends ScreenInfo {

	public MainWindowVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateAppName();
		initiateSelectedModule();
//		initiateContent();
		initiateFooterMsg();
		initiateContents();
		initiateSelectedContent();
	}
	
	protected String appName;
	
	protected void initiateAppName () {
		this.appName = null;
	}
	
	public String getAppName () {
		return this.appName;
	}
	
	public void setAppName (String newValue) {
		this.appName = newValue;
	}
	
	protected String selectedModule;
	
	protected void initiateSelectedModule () {
		this.selectedModule = null;
	}
	
	public String getSelectedModule () {
		return this.selectedModule;
	}
	
	public void setSelectedModule (String newValue) {
		this.selectedModule = newValue;
	}
	
//	protected String content;
//	
//	protected void initiateContent () {
//		this.content = null;
//	}
//	
//	public String getContent () {
//		return this.content;
//	}
//	
//	public void setContent (String newValue) {
//		this.content = newValue;
//	}
	
	protected List<ScreenInfo> contents;
	
	protected void initiateContents () {
		this.contents = new ArrayList<ScreenInfo>();
	}
	
	public List<ScreenInfo> getContents () {
		return this.contents;
	}
	
	public void setContents (List<ScreenInfo> newValue) {
		this.contents = newValue;
	}
	
	
	
	
	protected void addContent (ScreenInfo content) {
		this.contents = (this.contents != null ? this.contents : new ArrayList<ScreenInfo>());
		this.contents.add(content);
		setSelectedContent(content);
	}

	@GlobalCommand
	@NotifyChange({"contents", "selectedContent"})
	public void addNewContent (@BindingParam("content") String content, @BindingParam("args") java.util.Map args) {
		ScreenInfo screen = new ScreenInfo();
		String url = toScreen(content);
		screen.setSrc(url);
		args = (args != null ? args : new java.util.HashMap());
		args.put("screenInfo", screen);
		screen.setArgs(args);
		addContent(screen);
	}

	@GlobalCommand
	@NotifyChange("msg")
	public void openMessage (@BindingParam("msg") Message msg) {
		this.msg = msg;
	}

	@GlobalCommand
	@NotifyChange({"contents", "selectedContent"})
	public void closeContent (@BindingParam("screenInfo") ScreenInfo screenInfo) {
		if (screenInfo != null && this.contents != null) {
			this.contents.remove(screenInfo);
			int size = this.contents.size();
			if (size > 0) {
				org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "wnd" + size);
				this.setSelectedContent(this.contents.get(size - 1));
			}
		}
	}
	
	@DependsOn("contents")
	public Boolean getMultipleContents () {
		return (this.contents != null && this.contents.size() > 1);
	}
	
	@DependsOn("contents")
	public Boolean getHasContents () {
		return (this.contents != null && this.contents.size() > 0);
	}
	
	protected ScreenInfo selectedContent;
	
	protected void initiateSelectedContent () {
		this.selectedContent = null;
	}
	
	public ScreenInfo getSelectedContent () {
		return this.selectedContent;
	}
	
	public void setSelectedContent (ScreenInfo newValue) {
		this.selectedContent = newValue;
		int idx = (this.contents != null ? this.contents.size() - 1 : -1);
		this.selectedContentIdx = Integer.valueOf(idx);
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "hasContents");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "multipleContents");
//		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "wndIdx.w" + idx);
//		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "wndIdx");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "wnd" + idx);
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "selectedContentIdx");
	}
	
	protected String footerMsg;
	
	public void initiateFooterMsg () {
		this.footerMsg = null;
	}
	
	public String getFooterMsg () {
		return this.footerMsg;
	}
	
	public void setFooterMsg (String newValue) {
		this.footerMsg = newValue;
	}
	
	protected Message msg;
	
	protected void initiateMsg () {
		this.msg = null;
	}
	
	public Message getMsg () {
		return this.msg;
	}
	
	public void setMsg (Message newValue) {
		this.msg = newValue;
	}

	@Command
	@NotifyChange("msg")
	public void msgConfirm () {
		Message msg = this.msg;
		this.msg = null;
		if (msg != null && msg.getProcessOk() != null) {
//			msg.getProcessOk().run();
		}
		this.msg = null;
	}

	@Command
	@NotifyChange("msg")
	public void msgCancel () {
		Message msg = this.msg;
		this.msg = null;
		if (msg != null && msg.getProcessCancel() != null) {
//			msg.getProcessCancel().run();
		}
	}
	

	@Command
	@NotifyChange("selectedModule")
	public void openModule (@BindingParam("module") String module) {
		setSelectedModule(module);
	}
	
    @Command
    @NotifyChange("selectedContent")
    public void selectContent(@BindingParam("item") ScreenInfo content) {
    	setSelectedContent(content);
    }	
	
//	@Command
//	@NotifyChange("content")
//	public void openOption (@BindingParam("option") String option) {
////		String root = MySession.getResourcesContext() + "/";
//		String root = "";
//		option = option.toLowerCase();
//		String newOption = root + option + ".zul";
//		setContent(newOption);
//	}
	
////	@NotifyChange("content")
//    @Command
//	@NotifyChange({"contents", "selectedContent"})
//	public void openOption (@BindingParam("option") String option, @BindingParam("cmp") Component cmp) {
//		Desktop desktop = Executions.getCurrent().getDesktop();//(cmp != null ? cmp.getDesktop() : null);
//		String appName = (desktop != null ? desktop.getRequestPath() : null);
//		String appId = (appName != null ? WebUtils.appId(appName, MySession.getRootContext()) : null);
//		String newOption = appId + File.separator + "resources" + File.separator + option.toLowerCase() + ".zul";
////		setContent(newOption);
//		addContent(newOption);
//	}
	
    @Command
	@NotifyChange({"contents", "selectedContent"})
	public void openOption (@BindingParam("option") String option) {
		String url = toScreen(option);
//		setContent(newOption);
		if (this.contents != null) {
			this.contents.clear();
		}
		ScreenInfo screen = new ScreenInfo();
		screen.setSrc(url);
		java.util.HashMap args = new java.util.HashMap();
		screen.setArgs(args);
		addContent(screen);
	}
    
    protected String toScreen (String screenId) {
    	String result = null;
    	result = WebUtils.toScreen(screenId);
//		Desktop desktop = Executions.getCurrent().getDesktop();//(cmp != null ? cmp.getDesktop() : null);
//		String appName = (desktop != null ? desktop.getRequestPath() : null);
//		String appId = (appName != null ? WebUtils.appId(appName, MySession.getRootContext()) : null);
//		result = appId + File.separator + "resources" + File.separator + screenId.toLowerCase() + ".zul";
    	return result;
    }

//	public Boolean checkSelectedModule (String moduleId) {
//		
//	}
	
//	List<String> modules = getModules(appId);
//	for (String module : modules) {
//		String cte = "MODULE_" + module.toUpperCase();
//		addCte(String.class, cte, module);
//
//		String getterName = "getChecked" + StringUtils.capFirst(module);
//		mg = addMethod(getterName).setPublic(true).setReturnType(Boolean.class);
//		mg.declVariable(Boolean.class, "result");
//		mg.add(mg.assign("result", mg.call(mg.property(cte), "equals", mg.field("selectedModule"))));
//		mg.addReturn("result");
//
//		String setterName = "setChecked" + StringUtils.capFirst(module);
//		mg = addMethod(setterName).setPublic(true).addParameter(Boolean.class, "newValue");
//		mg.add(mg.call("selectModule", mg.property(cte)));
//		
//		List<String> options = getOpciones(appId, module);
//		for (String option : options) {
//			String finderName = StringUtils.capFirst(option) + "Finder";
//			String finderUrl = option + "_finder.zul";
//			mg = addMethod("open" + finderName).setPublic(true);
//			mg.addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, "content");
//			mg.add(mg.call("setContent", mg.cte(finderUrl)));
//		}
//	}
//
//	mg = addMethod("selectModule").setProtected(true).addParameter(Boolean.class, "value").addParameter(String.class, "module");
//	mg.gPrintln(mg.var("value"));
//	Expression cond = mg.and(mg.notNull("value"), mg.call("value", "booleanValue"), mg.notNull("module"), mg.call("module", "equals", mg.field("selectedModule")));//String selectCode = "if (value != null && value.booleanValue() && module != null && module.equals(this.selectedModule)) {";
//	MethodGen nb = mg.newBlock();
//	nb.assign(mg.field("selectedModule"), "module");//selectCode += "this.selectedModule = module";
//	for (String module : modules) {
//		String valueNotify = "checked" + StringUtils.capFirst(module) + "";
//		nb.callStatic(org.zkoss.bind.BindUtils.class, "postNotifyChange", mg.cteNull(), mg.cteNull(), mg.cteThis(), mg.cte(valueNotify));//"org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, \"checked" + StringUtils.capFirst(module) + "\")";
//	}
//	mg.addIf(cond, nb);
	

    protected Integer selectedContentIdx;
    
    protected void initiateSelectedContentIdx () {
    	this.selectedContentIdx = null;
    }
    
    public Integer getSelectedContentIdx () {
    	return this.selectedContentIdx;
    }
    
    public void setSelectedContentIdx (Integer newValue) {
    	this.selectedContentIdx = newValue;
    }
    

    @Command
	@NotifyChange("selectedContentIdx")
	public void selectContentIdx (@BindingParam("idx") Integer idx) {
    	this.selectedContentIdx = idx;
    }
    
    public Map getWndIdx () {
    	java.util.Map result = null;
    	result = new HashMap();
    	int length = (this.contents != null ? this.contents.size() : 0);
    	for (int i = 0; i < length; i++) {
    		result.put("w" + i, this.contents.get(i));
    	}
    	return result;
    }
    
    protected ScreenInfo _getWndIdx (int idx) {
    	return (this.contents != null && this.contents.size() > idx && idx > -1 ? this.contents.get(idx) : null);
    }
    
    public ScreenInfo getWnd0 () {
    	return _getWndIdx(0);
    }
    
    public ScreenInfo getWnd1 () {
    	return _getWndIdx(1);
    }
    
    public ScreenInfo getWnd2 () {
    	return _getWndIdx(2);
    }
    
    public ScreenInfo getWnd3 () {
    	return _getWndIdx(3);
    }
    
    public ScreenInfo getWnd4 () {
    	return _getWndIdx(4);
    }
    
    public ScreenInfo getWnd5 () {
    	return _getWndIdx(5);
    }
    
    public ScreenInfo getWnd6 () {
    	return _getWndIdx(6);
    }
    
    public ScreenInfo getWnd7 () {
    	return _getWndIdx(7);
    }
    
    public ScreenInfo getWnd8 () {
    	return _getWndIdx(8);
    }
    
    public ScreenInfo getWnd9 () {
    	return _getWndIdx(9);
    }
    
    public ScreenInfo getWnd10 () {
    	return _getWndIdx(10);
    }
    
    public ScreenInfo getWnd11 () {
    	return _getWndIdx(11);
    }
    
    public ScreenInfo getWnd12 () {
    	return _getWndIdx(12);
    }
    
}
