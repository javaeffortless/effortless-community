package org.effortless.gen.fields;

import javax.persistence.ManyToOne;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.effortless.gen.GAnnotation;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.GMethod;
import org.effortless.gen.InfoModel;
import org.objectweb.asm.Opcodes;

public class MappingPropertiesTransform extends AbstractPropertiesTransform {

	public MappingPropertiesTransform () {
		super();
	}
	
	protected void textProcessField (GField field) {
		GMethod m = field.getGetterMethod();
		addSimpleMapping(field, m);
	}
	
	protected void dateProcessField (GField field) {
		GMethod getter = field.getGetterMethod();
		addSimpleMapping(field, getter);
		//@Temporal(TemporalType.TIMESTAMP)
//		@Type(type="timestamp")
//		@org.hibernate.annotations.Type(type="org.effortless.model.FileUserType")
		String type = null;
		String typeName = field.getType().getName();
		if ("java.sql.Date".equals(typeName)) {
//			type = "DATE";//javax.persistence.TemporalType.DATE;
//			type = "org.effortless.model.DateSqlUserType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		else if ("java.sql.Time".equals(typeName)) {
			type = "TIME";//javax.persistence.TemporalType.TIME;
			type = "org.effortless.model.TimeUserType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		else if ("java.sql.Timestamp".equals(typeName)) {
//			type = "TIMESTAMP";//javax.persistence.TemporalType.TIMESTAMP;
			type = "timestamp";
//			type = "org.effortless.model.TimestampType";
//			field.setType(new ClassNode(java.util.Date.class));
		}

		if (type != null) {
			getter.addAnnotation(org.hibernate.annotations.Type.class, "type", getter.cte(type));
		}
	}
	
	protected void boolProcessField (GField field) {
		GMethod m = field.getGetterMethod();
		addSimpleMapping(field, m);
	}
	
	protected void countProcessField (GField field) {
		GMethod m = field.getGetterMethod();
		addSimpleMapping(field, m);
	}
	
	protected void numberProcessField (GField field) {
		GMethod m = field.getGetterMethod();
		addSimpleMapping(field, m);
	}
	
	protected void enumProcessField (GField field) {
		GMethod getter = field.getGetterMethod();
		addSimpleMapping(field, getter);

		//@javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
		getter.addAnnotation(javax.persistence.Enumerated.class, "value", getter.enumValue(javax.persistence.EnumType.class, "STRING"));
	}

	protected void fileProcessField (GField field) {
		GMethod getter = field.getGetterMethod();
//		addSimpleMapping(field, m);

		String columnName = field.getName().toUpperCase() + "_ID";
		GAnnotation ann = getter.addAnnotation(javax.persistence.ManyToOne.class);//@javax.persistence.ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
		ann.addMember("cascade", getter.enumValue(javax.persistence.CascadeType.class, "ALL"));
		ann.addMember("targetEntity", getter.cteClass(field.getType()));
		ann.addMember("fetch", getter.enumValue(javax.persistence.FetchType.class, "LAZY"));
//		getter.addAnnotation(ann);
		getter.addAnnotation(javax.persistence.JoinColumn.class, "name", columnName);//@javax.persistence.JoinColumn(name="FICHERO")

		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
//		getter.addAnnotation(javax.persistence.Basic.class, "fetch", getter.enumValue(javax.persistence.FetchType.class, "EAGER"));
	}
	
	protected void listProcessField (GField field) {
		GMethod getter = field.getGetterMethod();
//		@OneToMany(mappedBy="owner")
		
		boolean inner = false;

		ClassNode cType = field.getType();
		GenericsType[] types = cType.getGenericsTypes();
		cType = types[0].getType();
//		cType.isPrimaryClassNode();
//		ClassNode outerClass = (cType != null ? cType.getOuterClass() : null);
		GClass targetClass = (cType != null ? new GClass(cType) : null);
		if (targetClass != null) {
			targetClass.setApplication(field.getApplication());
		}
		if (cType != null) {
			if (targetClass != null && field.getClazz().checkInner(targetClass)) {
				GAnnotation ann = getter.addAnnotation(javax.persistence.OneToMany.class);
				ann.addMember("mappedBy", field.cte("owner"));
				
	//			@ManyToOne
				ClassNode plainClass = field.getClazz().getPlainClassForGenerics();
				targetClass.addField(plainClass, "owner").addAnnotation(ManyToOne.class);
				inner = true;
			}
		}

		
//	    @ManyToMany(
//	            targetEntity=org.hibernate.test.metadata.manytomany.Employee.class,
//	            cascade={CascadeType.PERSIST, CascadeType.MERGE}
//	        )
//	        @JoinTable(
//	            name="EMPLOYER_EMPLOYEE",
//	            joinColumns=@JoinColumn(name="EMPER_ID"),
//	            inverseJoinColumns=@JoinColumn(name="EMPEE_ID")
//	        )
//	        public Collection getEmployees() {
//	            return employees;
//	        }		
		if (!inner && targetClass != null) {
			GAnnotation ann = null;
			ann = getter.addAnnotation(javax.persistence.ManyToMany.class);
			ann.addMember("targetEntity", getter.cteClass(cType));
			ann.addMember("cascade", getter.cte(new javax.persistence.CascadeType[]{javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}));
			
			String tableName = getter.getClassGen().getSimpleName() + "_" + field.getName() + "_" + targetClass.getSimpleName();
			String ownerColumnName = "owner_id";
			String itemColumnName = "item_id";
			
			ann = getter.addAnnotation(javax.persistence.JoinTable.class);
			ann.addMember("name", getter.cte(tableName));
//			javax.persistence.JoinColumn a = new javax.persistence.JoinColumn();
			ArrayExpression exprOwner = getter.cteArray(javax.persistence.JoinColumn.class);
			exprOwner.addExpression(getter.cteAnnotation(getter.createAnnotation(javax.persistence.JoinColumn.class).addMember("name", getter.cte(ownerColumnName))));
			ann.addMember("joinColumns", exprOwner);

			ArrayExpression exprItem = getter.cteArray(javax.persistence.JoinColumn.class);
			exprItem.addExpression(getter.cteAnnotation(getter.createAnnotation(javax.persistence.JoinColumn.class).addMember("name", getter.cte(itemColumnName))));
			ann.addMember("inverseJoinColumns", exprItem);
		}
	}
	
	protected void refProcessField (GField field) {
		GMethod getter = field.getGetterMethod();
//		addSimpleMapping(field, m);
		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		getter.addAnnotation(javax.persistence.Basic.class, "fetch", getter.enumValue(javax.persistence.FetchType.class, "EAGER"));
		
		//@ManyToOne() 
		boolean refInner = (field.getAnnotation(ManyToOne.class) != null);
		GAnnotation ann = getter.addAnnotation(javax.persistence.ManyToOne.class);
		if (!refInner) {
			ann.addMember("cascade", getter.enumValue(javax.persistence.CascadeType.class, "ALL"));
			ann.addMember("targetEntity", getter.cteClass(field.getType()));
			ann.addMember("fetch", getter.enumValue(javax.persistence.FetchType.class, "LAZY"));
		}
		
		
		String columnName = field.getName().toUpperCase() + "_ID";
		GAnnotation column = getter.addAnnotation(javax.persistence.JoinColumn.class, "name", getter.cte(columnName));//@JoinColumn(name="CUST_ID")
		
		//@javax.persistence.Column(name="column", unique=true, nullable=false)
		if (InfoModel.isSingleUnique(field)) {
			column.addMember("unique", getter.cteTrue());
		}
		if (InfoModel.isNotNull(field)) {
			column.addMember("nullable", getter.cteFalse());
		}
	}
	
	protected void protectField (GField field) {
		field.setModifiers(Opcodes.ACC_PROTECTED);
	}

	protected void addInitiate (GField field) {
		String methodName = field.getInitiateName();
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		mg.add(mg.assign(mg.field(field), mg.cteNull()));
	}
	
	public void addSimpleMapping (GField field, GMethod mg) {
		String columnName = field.getName().toUpperCase();
		//@javax.persistence.Column(name="column", unique=true, nullable=false)
		GAnnotation column = mg.addAnnotation(javax.persistence.Column.class, "name", mg.cte(columnName));

		if (InfoModel.isSingleUnique(field)) {
			column.addMember("unique", mg.cteTrue());
		}
		if (InfoModel.isNotNull(field)) {
			column.addMember("nullable", mg.cteFalse());
		}
		if (field.isString()) {
			String fieldName = field.getName();
			int length = (InfoModel.checkCommentField(fieldName) ? 3072 : 255);
			column.addMember("length", mg.cte(Integer.valueOf(length)));
		}
			
		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		mg.addAnnotation(javax.persistence.Basic.class, "fetch", mg.enumValue(javax.persistence.FetchType.class, "EAGER"));
	}
	
}
