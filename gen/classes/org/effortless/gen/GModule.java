package org.effortless.gen;

import org.codehaus.groovy.control.SourceUnit;

public class GModule extends Object implements GNode {

	public GModule () {
		super();
		initiate();
	}
	
	public GModule(SourceUnit node) {
		this();
		setNode(node);
	}

	protected void initiate () {
		initiateNode();
	}
	
	protected SourceUnit node;
	
	protected void initiateNode () {
		this.node = null;
	}
	
	public SourceUnit getNode () {
		return this.node;
	}
	
	public void setNode (SourceUnit newValue) {
		this.node = newValue;
	}
	
}
