package org.effortless.tests.model;

import java.io.IOException;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestCollectionNonExclusive {

	public static void main (String[] args) {
		try {
			setupDs();
System.out.println("BEGIN item persist");
			ItemNonExclusive item1 = new ItemNonExclusive();
			item1.setName("item1");
			item1.persist();
			System.out.println("END item persist");
			
			System.out.println("BEGIN item persist");
			ItemNonExclusive item2 = new ItemNonExclusive();
			item2.setName("item2");
			item2.persist();
			System.out.println("END item persist");
			
			System.out.println("BEGIN item persist");
			ItemNonExclusive item3 = new ItemNonExclusive();
			item3.setName("item3");
			item3.persist();
			System.out.println("END item persist");
			
			
			System.out.println("BEGIN collection persist");
			CollectionNonExclusive coll = new CollectionNonExclusive();
			coll.setName("time" + System.currentTimeMillis());
			java.util.List items = coll.getItems();
			items.add(item1);
			items.add(item2);
			items.add(item3);
			coll.persist();
			System.out.println("END collection persist");

			coll.refresh();
			items = coll.getItems();
			System.out.println("BEGIN collection remove");
			items.remove(item1);
			coll.persist();
			System.out.println("END collection remove");
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(org.effortless.model.LogData.class.getName());
			StartupDb.addAppEntity(org.effortless.tests.model.CollectionNonExclusive.class.getName());
			StartupDb.addAppEntity(org.effortless.tests.model.ItemNonExclusive.class.getName());
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
