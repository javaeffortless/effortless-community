package org.effortless.gen;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.Expression;

public class GAnnotation extends Object {

	public GAnnotation () {
		super();
		initiate();
	}
	
	public GAnnotation (AnnotationNode node) {
		this();
		setNode(node);
	}
	
	protected void initiate () {
		initiateNode();
	}
	
	protected AnnotationNode node;
	
	protected void initiateNode () {
		this.node = null;
	}
	
	public AnnotationNode getNode () {
		return this.node;
	}
	
	protected void setNode (AnnotationNode newValue) {
		this.node = newValue;
	}
	
	public String getMemberString(String name) {
		String result = null;
		Expression expr = (this.node != null ? this.node.getMember(name) : null);
		result = (expr != null ? expr.getText() : null);
		return result;
	}

}
