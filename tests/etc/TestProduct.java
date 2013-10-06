package org.effortless.tests.model;

import java.io.IOException;
import java.util.List;

import org.effortless.model.StartupDb;
import org.effortless.tests.model.Product.Part;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestProduct {

	public static void main (String[] args) {
		setupDs();

		Product product = new Product();
		product.setName("123");
		
		Part part1 = new Part();
		part1.setName("parte1");

		Part part2 = new Part();
		part2.setName("parte2");
		
		product.getParts().add(part1);
		product.getParts().add(part2);
		
		product.persist();
		
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(Product.class.getName());
			StartupDb.addAppEntity(Part.class.getName());
			StartupDb.addAppEntity(org.effortless.model.LogData.class.getName());
			StartupDb.addAppEntity(org.effortless.model.FileEntity.class.getName());
			
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
