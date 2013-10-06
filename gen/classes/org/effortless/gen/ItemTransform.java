package org.effortless.gen;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.SourceUnit;

public class ItemTransform {

	protected List<ASTNode> nodes;
	protected SourceUnit sourceUnit;	
	
	public ItemTransform (ASTNode[] nodes, SourceUnit sourceUnit) {
		this.nodes = (this.nodes != null ? this.nodes : new ArrayList<ASTNode>());
		if (nodes != null) {
			for (ASTNode node : nodes) {
				this.nodes.add(node);
			}
		}
		this.sourceUnit = sourceUnit;
	}
	
	public List<ASTNode> getNodes () {
		return this.nodes;
	}

	public void addNodes (List<ASTNode> nodes) {
		if (nodes != null) {
			this.nodes = (this.nodes != null ? this.nodes : new ArrayList<ASTNode>());
			this.nodes.addAll(nodes);
		}
	}
	
	public SourceUnit getSourceUnit () {
		return this.sourceUnit;
	}
	
	public boolean equals (Object o) {
		boolean result = false;
		if (this == o) {
			result = true;
		}
		else if (o instanceof ItemTransform) {
			ItemTransform obj = (ItemTransform)o;
			String oName = (obj.sourceUnit != null ? obj.sourceUnit.getName() : null);
			String tName = (this.sourceUnit != null ? this.sourceUnit.getName() : null);
			result = (tName != null && tName.equals(oName));
		}
		return result;
	}
	
}
