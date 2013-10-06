package org.effortless.ui.widgets;

import org.zkoss.zk.ui.Component;

public interface Field<T extends Object> extends Component {

	public T getValue();
	
	public void setValue (T newValue);
	
	public Boolean getReadonly ();
	
	public void setReadonly (Boolean newValue);
	
	//"horizontal" or "vertical".
	public String getOrient ();
	
	public void setOrient (String newValue);
	
	public boolean isHorizontal ();
	
	public boolean isVertical ();
	
	public String getLabel ();
	
	public void setLabel (String newValue);	

	public Component getColumnWidget ();

//	public Component getReadonlyListWidget ();
	
	public Component getTagWidget ();
}
