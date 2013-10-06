package org.effortless.tests.model;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestAllBasicProperties {

	public static void main (String[] args) {
		setupDs();

		AllBasicProperties e = new AllBasicProperties();
		e.setComentario("Comentario");
		e.setCount(Integer.valueOf(22));
		e.setFecha(new java.util.Date());
		e.setFechaHora(new Timestamp((new java.util.Date()).getTime()));
		e.setFlag(Boolean.TRUE);
		e.setHora(new Time((new java.util.Date()).getTime()));
		e.setMode(StatusTest.DISABLED);
		e.setNumber(Double.valueOf(123.45));
		e.setText("text");
		
		e.persist();
		
		List<AllBasicProperties> list = AllBasicProperties.listBy().sortBy("fecha DESC");
		for (AllBasicProperties item : list) {
			System.out.println("1 -> " + item.getFecha());
		}
		System.out.println(" date = " + e.getLogCreation().getDate());
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(AllBasicProperties.class.getName());
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
