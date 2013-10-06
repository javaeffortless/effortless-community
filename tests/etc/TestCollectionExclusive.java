package org.effortless.tests.model;

import java.io.IOException;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestCollectionExclusive {

	public static void main (String[] args) {
		try {
			setupDs();
			
			CollectionExclusive coll = new CollectionExclusive();
			coll.setName("time" + System.currentTimeMillis());
			CollectionExclusive.Item item1 = new CollectionExclusive.Item();
			CollectionExclusive.Item item2 = new CollectionExclusive.Item();
			java.util.List items = coll.getItems();
			items.add(item1);
			items.add(item2);
			coll.persist();

//			items = coll.getItems();
			items.remove(item1);
			coll.persist();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(org.effortless.model.LogData.class.getName());
			StartupDb.addAppEntity(org.effortless.tests.model.CollectionExclusive.class.getName());
			StartupDb.addAppEntity(org.effortless.tests.model.CollectionExclusive.Item.class.getName());
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
