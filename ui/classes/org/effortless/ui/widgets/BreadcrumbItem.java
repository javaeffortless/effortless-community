package org.effortless.ui.widgets;

import org.effortless.core.ObjectUtils;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

public class BreadcrumbItem extends AbstractComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -894744986710734278L;

	@Wire
	protected A wgt;
	
	@Wire
	protected Div wIcon;
	
	@Wire
	protected Label wTitle;

    public BreadcrumbItem() {
    	super();
    }
    
    @Listen("onClick = #wgt")
	public void _onClick_Wgt () {
    	Events.postEvent(new Event(Events.ON_CLICK, this, this.value));
    }

    protected void initiate () {
    	super.initiate();

        initiateLabel();
        initiateDescription();
        initiateImage();
        
        initiateValue();
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
    	String label = (this.label != null ? this.label : "");
    	this.wTitle.setValue(label);
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
    	String tooltiptext = (this.description != null ? this.description : "");
    	this.wTitle.setTooltiptext(tooltiptext);
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
    	String image = (this.image != null ? this.image : "");
    	this.wIcon.setSclass(image);
    	this.wIcon.setVisible((image.length() > 0));
    }
    
    protected Object value;
    
    protected void initiateValue () {
    	this.value = null;
    }
    
    public Object getValue () {
    	return this.value;
    }
    
    public void setValue (Object newValue) {
    	this.value = newValue;
    }

}
