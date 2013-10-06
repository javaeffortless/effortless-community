package org.effortless.tests.model;

import java.util.List;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.control.CompilePhase;

public class AstBuilderTests {

	protected static final AstBuilder BUILDER = new AstBuilder();
	
	public static void main (String[] args) {
//		String expr = "public class Container { public void metodo () { final String var = \"hola\"; java.lang.Runnable runnable = new Runnable() { public void run () {System.out.println(var);}};} }";
		String expr = "enum IconStatus {HABILITADO, DESHABILITADO}";
		List<ASTNode> enodes = toExpression(expr);
//		String expr = "";
//		expr += "java.util.Map args = new java.util.HashMap();";
//		expr += "args.put(\"content\", \"item_editor\");";
//		org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);

		
		List<ASTNode> nodes = toExpression("value = obj.property");
//		List<ASTNode> nodes = toExpression("if (newValue != null && !newValue.equals(variable) && var2 != null) { this.value = newValue }");
		for (ASTNode node : nodes) {
			System.out.println("node = " + node.toString());
		}
	}
	
	protected static List<ASTNode> toExpression (String source) {
		List<ASTNode> result = null;
		result = BUILDER.buildFromString(CompilePhase.SEMANTIC_ANALYSIS, true, source);
		return result;
	}
	
	
}
