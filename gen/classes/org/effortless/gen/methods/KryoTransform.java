package org.effortless.gen.methods;

import java.util.List;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GClass;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements

	protected void doWrite(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Output out) {
		super.doWrite(kryo, out);
		kryo.writeObjectOrNull(out, this.id, Integer.class);
		kryo.writeObjectOrNull(out, this.version, Integer.class);
		kryo.writeObjectOrNull(out, this.deleted, Boolean.class);
	}
	
	protected void doRead(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Input in) {
		super.doRead(kryo, in);
		this.id = kryo.readObjectOrNull(in, Integer.class);
		this.version = kryo.readObjectOrNull(in, Integer.class);
		this.deleted = kryo.readObjectOrNull(in, Boolean.class);
	}
	
 * 
 * 
 * @author jesus
 *
 */
public class KryoTransform extends Object implements ClassTransform {

	public static final ClassNode INPUT = ClassNodeHelper.toClassNode(com.esotericsoftware.kryo.io.Input.class);
	public static final ClassNode OUTPUT = ClassNodeHelper.toClassNode(com.esotericsoftware.kryo.io.Output.class);
	public static final ClassNode KRYO = ClassNodeHelper.toClassNode(com.esotericsoftware.kryo.Kryo.class);
	
	public void process (GClass cg) {
		if (cg != null) {
			addWrite(cg);
			addRead(cg);
		}
	}

	/**
	 * 
	 * 
	protected void doWrite(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Output out) {
		super.doWrite(kryo, out);
		kryo.writeObjectOrNull(out, this.id, Integer.class);
		kryo.writeObjectOrNull(out, this.version, Integer.class);
		kryo.writeObjectOrNull(out, this.deleted, Boolean.class);
	}
	 * 
	 * 
	 * 
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addWrite(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		if (clazz != null) {
			List<FieldNode> fields = clazz.getFields();
			if (fields != null && fields.size() > 0) {
				BlockStatement code = new BlockStatement();
				
				VariableExpression varKryo = new VariableExpression("kryo", KRYO);
				VariableExpression varOut = new VariableExpression("out", OUTPUT);
		
				//super.doWrite(kryo, out);
				MethodCallExpression addSuper = new MethodCallExpression(VariableExpression.SUPER_EXPRESSION, "doWrite", new ArgumentListExpression(new Expression[] {varKryo, varOut}));
				code.addStatement(new ExpressionStatement(addSuper));
				
				for (FieldNode field : fields) {
					//kryo.writeObjectOrNull(out, this.id, Integer.class);
					MethodCallExpression writeField = new MethodCallExpression(varKryo, "writeObjectOrNull", new ArgumentListExpression(new Expression[] {varOut, new FieldExpression(field), new ClassExpression(field.getType())}));
					code.addStatement(new ExpressionStatement(writeField));
				}
		
				Parameter[] parameterTypes = new Parameter [] {new Parameter(KRYO, "kryo"), new Parameter(OUTPUT, "out")};
				MethodNode method = new MethodNode("doWrite", Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
				clazz.addMethod(method);
			}
		}
	}

	/**
	 * 

	protected void doRead(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Input in) {
		super.doRead(kryo, in);
		this.id = kryo.readObjectOrNull(in, Integer.class);
		this.version = kryo.readObjectOrNull(in, Integer.class);
		this.deleted = kryo.readObjectOrNull(in, Boolean.class);
	}

	 * 
	 * 
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addRead(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		if (clazz != null) {
			List<FieldNode> fields = clazz.getFields();
			if (fields != null && fields.size() > 0) {
				BlockStatement code = new BlockStatement();
				
				VariableExpression varKryo = new VariableExpression("kryo", KRYO);
				VariableExpression varIn = new VariableExpression("in", INPUT);
		
				//super.doRead(kryo, in);
				MethodCallExpression addSuper = new MethodCallExpression(VariableExpression.SUPER_EXPRESSION, "doRead", new ArgumentListExpression(new Expression[] {varKryo, varIn}));
				code.addStatement(new ExpressionStatement(addSuper));

				for (FieldNode field : fields) {
					//this.id = kryo.readObjectOrNull(in, Integer.class);
					MethodCallExpression readField = new MethodCallExpression(varKryo, "readObjectOrNull", new ArgumentListExpression(new Expression[] {varIn, new ClassExpression(field.getType())}));
					BinaryExpression assignField = new BinaryExpression(new FieldExpression(field), Token.newSymbol(Types.ASSIGN, -1, -1), readField);
					code.addStatement(new ExpressionStatement(assignField));
				}

				Parameter[] parameterTypes = new Parameter [] {new Parameter(KRYO, "kryo"), new Parameter(INPUT, "in")};
				MethodNode method = new MethodNode("doRead", Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
				clazz.addMethod(method);
			}
		}
	}
	
}
