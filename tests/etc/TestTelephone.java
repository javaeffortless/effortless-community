package org.effortless.tests.model;

import java.io.IOException;
import java.util.List;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestTelephone {

	protected static final String NUMBER = "124";
	
	public static void main (String[] args) {
		setupDs();

		Telephone tlf = new Telephone();
		tlf.setNumber(NUMBER);
		tlf.persist();
		
//		List<Telephone> telephones = Telephone.listBy(Telephone.class, "Number", null, "2");
//		
//		for (Telephone telephone : telephones) {
//			System.out.println(telephone.toString());
//		}
		
		List<Telephone> list = Telephone.listBy().eq("number", NUMBER);
		for (Telephone telephone : list) {
			System.out.println("1 -> " + telephone.toString());
		}
		System.out.println("size = " + list.size());
		for (Telephone telephone : list) {
			System.out.println("2 -> " + telephone.toString());
		}
		
		
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(Telephone.class.getName());
			StartupDb.addAppEntity(org.effortless.model.LogData.class.getName());
			
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StartupDb.runAllDb();
		} catch (ValidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String jndi = "gy:/hibernate/model/SessionFactory";
//		javax.naming.InitialContext ictx = new javax.naming.InitialContext();
//		result = (SessionFactory)ictx.lookup(jndi);
	}
	
	
}
