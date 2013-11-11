package org.effortless.gen;

import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.Expression;

public class GAnnotation extends Object implements GNode {

	public GAnnotation () {
		super();
		initiate();
	}
	
//	public GAnnotation (AnnotationNode node) {
//		this();
//		setNode(node);
//	}
	
	public GAnnotation (AnnotationNode node, AnnotatedNode parent) {
		this();
//		String annName = node.getClassNode().getName();
//		if ("org.effortless.ann.Person".equals(annName)) {
//			System.out.println("");
//		}
//		parent.addAnnotation(node);
		setNode(node);
		setParent(parent);
	}
	
	protected void initiate () {
		initiateNode();
		initiateParent();
	}

	protected AnnotatedNode parent;
	
	protected void initiateParent () {
		this.parent = null;
	}
	
	public AnnotatedNode getParent () {
		return this.parent;
	}
	
	public void setParent (AnnotatedNode newValue) {
		this.parent = newValue;
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

	public String getValue() {
		return getMemberString("value");
	}

	public GAnnotation addMember(String attr, Expression value) {
		if (this.node != null) {
			this.node.addMember(attr, value);
		}
		return this;
	}

}
