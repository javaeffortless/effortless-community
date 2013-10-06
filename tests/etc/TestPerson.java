package org.effortless.tests.model;

import java.io.IOException;
import java.util.List;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestPerson {

	public static void main (String[] args) {
		try {
			setupDs();
			for (String name : NAMES) {
				createPerson(name);
			}
			List<Person> persons = Person.listByName(null);
			System.out.println("size = " + persons.size());
			for (Person person : persons) {
				System.out.println(person);
			}
			int idx = 0;
			for (Person person : persons) {
				if (idx % 2 == 1) {
					person.delete();
				}
				idx += 1;
			}
			persons = Person.listByName(null);
			System.out.println("size = " + persons.size());

			persons = Person.listByName("Juan");
			System.out.println("size Juan = " + persons.size());
		
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	protected static void setupDs() throws ValidityException, ParsingException, IOException {
		StartupDb.addAppEntity(Person.class.getName());
		StartupDb.runAllDb();
//		String jndi = "gy:/hibernate/model/SessionFactory";
//		javax.naming.InitialContext ictx = new javax.naming.InitialContext();
//		result = (SessionFactory)ictx.lookup(jndi);
	}

	protected static String[] NAMES = {"Juan", "María", "Pepe", "Jesús", "Rodolfo", "Yessica", "Estefanía"};
	
	protected static void createPerson (String name) throws Exception {
		Person person = new Person();
		person.setName(name);
		person.persist();
	}
	
}
