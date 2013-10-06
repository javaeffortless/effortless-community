package org.effortless.tests.model;

import java.io.IOException;
import java.util.List;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestCustomer {

	public static void main (String[] args) {
		setupDs();

		Customer tlf = new Customer();
		tlf.setName("123");
		tlf.setFile("/home/jesus/Descargas/Ubuntu_Crear_ramdisk.pdf");
		tlf.getFile().persist();
		tlf.persist();
		
		List<Customer> list = Customer.listBy(Customer.class, "Name", null, "2");
		
		for (Customer item : list) {
			System.out.println(item.toString());
		}
		
		
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(Customer.class.getName());
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
