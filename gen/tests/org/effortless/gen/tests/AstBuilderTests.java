package org.effortless.gen.tests;

import groovy.lang.Closure;

import java.util.List;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.control.CompilePhase;

public class AstBuilderTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String code = "Hello";
		String code = "";

        code += "@javax.persistence.JoinTable(";
        code += "name=\"EMPLOYER_EMPLOYEE\",";
        code += "joinColumns=@javax.persistence.JoinColumn(name=\"EMPER_ID\"))";
		code += "\n";
		code += "public java.util.Collection getEmployees() {return null;}";
		
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
		
		List<ASTNode> nodes = new AstBuilder().buildFromString(CompilePhase.CLASS_GENERATION, true, code);
		System.out.println("AAA");
	}

}
