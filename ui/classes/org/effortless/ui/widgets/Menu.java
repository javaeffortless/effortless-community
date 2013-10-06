package org.effortless.ui.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.server.ServerContext;
import org.effortless.ui.Relocatable;
import org.effortless.ui.Relocator;
import org.effortless.ui.ViewContext;
import org.effortless.ui.impl.Components;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.layouts.LayoutGrid;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Style;
import org.zkoss.zul.Vlayout;

public class Menu extends AbstractComponent {

	@Wire
	protected A wgt;
	
	@Wire
	protected Div wIcon;
	
	@Wire
	protected Label wTitle;

	@Wire
	protected Div wIconSubmenu;
	
//	@Wire
//	protected LayoutGrid wSubmenu;
	protected Vlayout wSubmenu;
	
	
	protected void setupInit () {
		super.setupInit();
//		this.wIcon = (Div)this.wgt.getFellowIfAny("wIcon");
//		this.wTitle = (Label)this.wgt.getFellowIfAny("wTitle");
//		this.wIconSubmenu = (Div)this.wgt.getFellowIfAny("wIconSubmenu");
//		this.wSubmenu = new LayoutGrid();
		this.wSubmenu = new Vlayout();
		this.appendChild(this.wSubmenu);
	}
	
    public Menu() {
    	super();
    }
    
    @Listen("onClick = #wgt")
	public void _onClick_Wgt () {
    	open();
    }

	public void open () {
    	Events.postEvent(Events.ON_CLICK, this, Boolean.valueOf(this.selected));
    	setSelected(true);
    }
    
    @Listen("onClick = #wIconSubmenu")
	public void _onOpenSubmenu () {
    	
    }
    
    protected void initiate () {
    	super.initiate();

        initiateLink();
        initiateLabel();
        initiateDescription();
        initiateImage();
        
        initiateSelected();
        
        initiateSubitems();
        initiateAlign();
    }
    
    protected String align;
    
    protected void initiateAlign () {
    	this.align = null;
    }
    
    public String getAlign () {
    	return this.align;
    }
    
    public void setAlign (String newValue) {
    	String oldValue = this.align;
    	if (!ObjectUtils.equals(oldValue, newValue)) {
    		this.align = newValue;
    		_updateAlign();
    	}
    }
    
    protected Hlayout _cmpAlign;
    
    protected void _updateAlign () {
    	if ("horizontal".equals(this.align)) {
    		if (this._cmpAlign == null) {
    			this._cmpAlign = new Hlayout();
    			this._cmpAlign.setSclass("horizontal-align");
    			this._cmpAlign.appendChild(this.wIcon);
    			this._cmpAlign.appendChild(this.wTitle);
    			this._cmpAlign.appendChild(this.wIconSubmenu);
    			this.wgt.appendChild(this._cmpAlign);
    		}
    	}
    	else {
    		this.wgt.appendChild(this.wIcon);
    		this.wgt.appendChild(this.wTitle);
    		this.wgt.appendChild(this.wIconSubmenu);
    		if (this._cmpAlign != null) {
    			this._cmpAlign.detach();
    			this._cmpAlign = null;
    		}
    	}
    }

    protected void initUi () {
    	super.initUi();
//    	this.wSubmenu.setVisible(false);
    	this.wSubmenu.setVisible(true);
    }
    
    protected boolean selected;
    
    protected void initiateSelected () {
    	this.selected = false;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeSelected)")
    public boolean isSelected () {
    	return this.selected;
    }
    
    public void setSelected (boolean newValue) {
    	boolean oldValue = this.selected;
    	if (oldValue != newValue) {
    		this.selected= newValue;
    		notifyChange("Selected");
    		_onChangeSelected();
    		if (newValue) {
    			_openItem();
    		}
    	}
    }

    
    protected void selectSubitems () {
    	if (this.subitems != null) {
//    		int idx = 0;
    		for (Component subitem : this.subitems) {
    			if (subitem != null) {
    				subitem.setVisible(this.selected);
//    				if (this.selected && idx == 0) {
//        				Menu menu = null;
//        				try { menu = (Menu)subitem; } catch (ClassCastException e) {}
//        				if (menu != null) {
//        					idx += 1;
//        					menu.open();
////           					menu.setSelected(true);
//        				}
//    				}
    			}
    		}
    	}
    }
    
    
    protected void _openItem() {
    	List<Menu> menus = Components.getSameSiblings(this);
    	boolean newSelected = !this.selected;
    	for (Menu menu : menus) {
    		menu.setSelected(newSelected);
    	}
    	selectSubitems();
    	String rootCtx = ServerContext.getRootContext();
    	if (false) {
	    	String uri = "org.effortless.icondb/resources/test.zul";
	    	
	    	Component parent = this;//this.wSubmenu;
	    	
	    	Executions.createComponents(uri, parent, null);
			// TODO Auto-generated method stub
    	}
	}
    
	protected void _onChangeSelected () {
    	String eventName = (this.selected ? Events.ON_SELECT : "onUnselect");
    	Events.postEvent(new Event(eventName, this, this.link));
    	selectSubitems();
    }
    
//    public List<Component> getSubitems () {
//    	return (this.wSubmenu != null ? this.wSubmenu.getChildren() : null);
//    }
    
    protected String link;
    
    protected void initiateLink () {
    	this.link = null;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeLink)")
    public String getLink () {
    	return this.link;
    }
    
    public void setLink (String newValue) {
    	String oldValue = this.link;
    	if (!ObjectUtils.equals(oldValue, newValue)) {
    		this.link = newValue;
    		notifyChange("Link");
    		_onChangeLink();
    	}
    }
    
    protected void _onChangeLink () {
    	if (this.link != null) {
//    	    <listitem onClick="@command('openOption', option='licenseType_finder')">
        	if (this.label == null) {
        		String key = this.link + "_optionLabel";
        		String label = ViewContext.i18n(key);
        		setLabel(label);
//        	      label="${i18n.LicenseType_optionLabel}"
        		
        	}
        	if (this.description == null) {
        		String key = this.link + "_optionTooltip";
        		String defaultKey = this.link + "_optionLabel";
        		String description = ViewContext.i18n(key, false, defaultKey);
        		setDescription(description);
//        	      ${i18n.LicenseType_optionTooltip}" />
        		
        	}
        	if (this.image == null) {
        		String prefix = "";
//        		prefix = (this.subitems != null && this.subitems.size() > 0 ? "module_" : "option_");
        		String suffix = StringUtils.uncapFirst(this.link);
        		String key = "main/" + prefix + suffix + ".png";
        		String image = _images(key);
        		setImage(image);
//        	   "${images}/main/option_licenseType.png"
        	}
    	}
    }
    
    protected String label;
    
    protected void initiateLabel () {
    	this.label = null;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeLabel)")
    public String getLabel () {
    	return this.label;
    }
    
    public void setLabel (String newValue) {
    	String oldValue = this.label;
    	if (!ObjectUtils.equals(oldValue, newValue)) {
    		this.label = newValue;
    		notifyChange("Label");
    		_onChangeLabel();
    	}
    }
    
    protected void _onChangeLabel () {
    	String value = this.label;
    	value = (value != null ? value : "");
    	this.wTitle.setValue(value);
    }
    
    protected String description;
    
    protected void initiateDescription () {
    	this.description = null;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeDescription)")
    public String getDescription () {
    	return this.description;
    }
    
    public void setDescription (String newValue) {
    	String oldValue = this.description;
    	if (!ObjectUtils.equals(oldValue, newValue)) {
    		this.description = newValue;
    		notifyChange("Description");
    		_onChangeDescription();
    	}
    }
    
    protected void _onChangeDescription () {
    	String value = this.description;
    	value = (value != null ? value : "");
    	this.wTitle.setTooltiptext(value);
    }
    
    protected String image;
    
    protected void initiateImage () {
    	this.image = null;
    }
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeImage)")
    public String getImage () {
    	return this.image;
    }
    
    public void setImage (String newValue) {
    	String oldValue = this.image;
    	if (!ObjectUtils.equals(oldValue, newValue)) {
    		this.image = newValue;
    		notifyChange("Image");
    		_onChangeImage();
    	}
    }
    
    protected void _onChangeImage () {
    	if (this.wIcon != null) {
    		boolean visible = (this.image != null);
			this.wIcon.setVisible(visible);
    		if (visible) {
    			String baseName = FilenameUtils.getBaseName(this.image);
    			String url = this.image;
    			url = (url.startsWith(File.separator) ? url.substring(1) : url);
    			Style style = new Style();
    			this.wIcon.appendChild(style);
    			String sclass = "menuclass_" + baseName;
    			String content = "." + sclass + " { ";
    			content += "background-image: url('" + url + "');";
    			Boolean flag = (Boolean)this.getAttribute(CteUi.INNER_MENU);
    			String imageSize = (Boolean.TRUE.equals(flag) ? "24px" : "48px");
    			content += "width: " + imageSize + ";";
    			content += "height: " + imageSize + ";";
    			content += "margin: auto;";
    			content += "}";
    			style.setContent(content);
        		this.wIcon.setSclass(sclass);
    		}
    	}
//    	String value = this.description;
//    	value = (value != null ? value : "");
//    	this.wLabel.setTooltiptext(value);
    }

    protected String src;
    
    protected void initiateSrc () {
    	this.src = null;
    }
    
	public String getSrc() {
		return this.src;
	}
	
	public void setSrc (String newValue) {
		
	}

	public Map<String, Object> getArgs() {
		return this.properties;
	}

	
	
	public String getResumeLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResumeDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResumeImage() {
		// TODO Auto-generated method stub
		return null;
	}
    

	protected List<Component> subitems;
	
	protected void initiateSubitems () {
		this.subitems = null;
	}
	
	public List<Component> getSubitems () {
		return this.subitems;
	}
	
    protected boolean doInsertBefore (Component newChild, Component refChild) {
    	boolean result = false;
		if (false && this.relocator != null) {
//			Integer newPosition = (this.position != null ? Integer.valueOf(this.position.intValue() + 1) : this.position);
//			this.relocator.relocate(this.wSubmenu, newPosition);
			this.relocator.relocate(this.wSubmenu, this.position);
		}
		Menu _menu = null;
		try { _menu = (Menu)newChild; } catch (ClassCastException e) {}
		if (_menu != null) {
			_menu.setAttribute(CteUi.INNER_MENU, Boolean.TRUE);
//			if (_menu.getAlign() == null) {
//				_menu.setAlign("horizontal");
//			}
		}
   		result = this.wSubmenu.insertBefore(newChild, refChild);
   		this.subitems = (this.subitems != null ? this.subitems : new ArrayList<Component>());
//		boolean anyMenu = checkAnyMenu();
   		this.subitems.add(newChild);
   		if (newChild != null) {
   			newChild.setVisible(this.selected);
//			if (this.selected && !anyMenu) {
//				Menu menu = null;
//				try { menu = (Menu)newChild; } catch (ClassCastException e) {}
//				if (menu != null) {
//					menu.open();
//				}
//			}
   		}
    	return result;
    }
    
    protected boolean checkAnyMenu () {
    	boolean result = false;
    	int length = (this.subitems != null ? this.subitems.size() : 0);
    	for (int i = 0; i < length; i++) {
    		Component item = this.subitems.get(i);
			Menu menu = null;
			try { menu = (Menu)item; } catch (ClassCastException e) {}
			if (menu != null) {
				result = true;
				break;
			}
    	}
    	return result;
    }

	@Override
	public void afterCompose() {
		if (false && this.relocator != null) {
			this.relocator.relocate(this.wSubmenu, this.position);
		}
		if (this.align == null) {
			Boolean flag = (Boolean)this.getAttribute(CteUi.INNER_MENU);
			if (Boolean.TRUE.equals(flag)) {
				this.setAlign("horizontal");
			}
		}
	}
    
}
