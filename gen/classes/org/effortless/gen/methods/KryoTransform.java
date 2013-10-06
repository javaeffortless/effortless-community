package org.effortless.gen.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.core.ModelException;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.model.AbstractEntity;
import org.objectweb.asm.Opcodes;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

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
public class KryoTransform {

	public static final ClassNode INPUT = new ClassNode(com.esotericsoftware.kryo.io.Input.class);
	public static final ClassNode OUTPUT = new ClassNode(com.esotericsoftware.kryo.io.Output.class);
	public static final ClassNode KRYO = new ClassNode(com.esotericsoftware.kryo.Kryo.class);
	
	public static void processClass (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
			addWrite(clazz, sourceUnit);
			addRead(clazz, sourceUnit);
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
	public static void addWrite(ClassNode clazz, SourceUnit sourceUnit) {
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
	public static void addRead(ClassNode clazz, SourceUnit sourceUnit) {
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
