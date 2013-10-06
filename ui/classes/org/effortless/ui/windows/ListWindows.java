package org.effortless.ui.windows;

import java.util.ArrayList;
import java.util.List;

import org.effortless.core.ObjectUtils;
import org.effortless.ui.widgets.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;

public class ListWindows extends AbstractComponent {

//    <hlayout height="100%" id="wHeader">
	
//	@Wire
//	protected Tabbox wgt;
	
	
	public ListWindows () {
		super();
	}

	protected void initiate () {
		super.initiate();
	}
	
    protected void initUi () {
    	super.initUi();
    	initUi_Wnd();
    }

    protected void initUi_Wnd () {
    	this.setWidth("100%");
    	this.setHeight("100%");
    	
    	this.setSclass("main-windows");
    }
    
	protected List<Component> list;
	
	protected void initiateList () {
		this.list = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeList)")
	public List<Component> getList () {
		return this.list;
	}
	
	public void setList (List<Component> newValue) {
		List<Component> oldValue = this.list;
		if (oldValue != newValue) {
			this.list = newValue;
			notifyChange("List");
			_onChangeList();
		}
	}
	
	protected void _onChangeList () {
		Components.removeAllChildren(this);
		if (this.list != null) {
			for (Component screen : this.list) {
				addScreen(screen);
			}
		}
		this.setVisible((this.list != null && list.size() > 0));
	}
	
	protected Component selected;
	
	protected void initiateSelected () {
		this.selected = null;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeSelected)")
	public Component getSelected () {
		return this.selected;
	}
	
	public void setSelected (Component newValue) {
		Component oldValue = this.selected;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.selected = newValue;
			if (oldValue != null) {
				oldValue.setVisible(false);
			}
			if (newValue != null) {
				newValue.setVisible(true);
			}
			notifyChange("Selected");
			_onChangeSelected();
		}
	}
	
	protected void _onChangeSelected() {
		// TODO Auto-generated method stub
		
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChangeSelectedIndex)")
	public Integer getSelectedIndex () {
		Integer result = null;
		if (this.list != null && this.selected != null) {
			int idx = 0;
			for (Component item : this.list) {
				if (this.selected == item) {
					break;
				}
				idx += 1;
			}
			result = Integer.valueOf(idx);
		}
		return result;
	}

	public void setSelectedIndex (Integer newValue) {
		Integer oldValue = getSelectedIndex();
		int _idx = (newValue != null ? newValue.intValue() : -1);
		if (!ObjectUtils.equals(oldValue, newValue) && _idx >= 0 && this.list != null && _idx < this.list.size()) {
			Component newSelected = this.list.get(_idx);
			setSelected(newSelected);
			notifyChange("SelectedIndex");
		}
	}

	public void addScreen (Component screen) {
		if (screen != null) {
			this.list = (this.list != null ? this.list : new ArrayList<Component>());
			this.list.add(screen);

			this.appendChild(screen);
			setSelected(screen);
			notifyChange("List");
			notifyChange("SelectedIndex");
		}
		this.setVisible((this.list != null && list.size() > 0));
	}

	public void removeScreen (Component screen) {
		if (screen != null && this.list != null) {
			boolean removed = this.removeChild(screen);
			if (removed) {
				this.list.remove(screen);
				int size = this.list.size();
				Component selected = (size > 0 ? this.list.get(size - 1) : null);
				setSelected(selected);
				notifyChange("List");
			}
		}
		this.setVisible((this.list != null && list.size() > 0));
	}

	public void removeAllScreens () {
		if (this.list != null) {
			Components.removeAllChildren(this);

			this.list.clear();
			int size = this.list.size();
			notifyChange("List");
		}
		this.setVisible((this.list != null && list.size() > 0));
	}
	
	
	
	public void reset () {
		Components.removeAllChildren(this);
		this.list = null;
		notifyChange("List");
		notifyChange("Selected");
		notifyChange("SelectedIndex");
		this.setVisible((this.list != null && list.size() > 0));
	}
	
}
