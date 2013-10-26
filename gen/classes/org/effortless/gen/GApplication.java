package org.effortless.gen;

import java.util.List;

public class GApplication {

	public GApplication () {
		super();
	}
	
	protected void initiate () {
		initiateClasses();
	}
	
	protected List<GClass> classes;
	
	protected void initiateClasses () {
		this.classes = null;
	}
	
	public List<GClass> getClasses () {
		return this.classes;
	}
	
	protected void setClasses (List<GClass> newValue) {
		this.classes = newValue;
	}
	
	public void addClass (GClass clazz) {
		if (clazz != null) {
			this.classes = (this.classes != null ? this.classes : new ArrayList<GClass>());
			if (!this.classes.contains(clazz)) {
				this.classes.add(clazz);
			}
		}
	}
	
}
