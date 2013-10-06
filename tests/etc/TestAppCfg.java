package org.effortless.tests.model;

import java.io.IOException;
import java.util.List;

import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestAppCfg {

	public static void main (String[] args) {
		setupDs();

		MyAppCfg cfg = MyAppCfg.getCurrent(MyAppCfg.class);
		if (!cfg.exists()) {
			cfg.setDefaultPageSize(Integer.valueOf(25));
			cfg.setProperty("perico", Boolean.TRUE);
			cfg.setNumDays(Integer.valueOf(31));
			cfg.persist();
		}
		else {
			Integer defaultPageSize = cfg.getDefaultPageSize();
			Boolean perico = cfg.getProperty(Boolean.class, "perico", null);
			Integer numDays = cfg.getNumDays();
			System.out.println("pageSize = " + defaultPageSize);
			System.out.println("perico = " + perico);
			System.out.println("numDays = " + numDays);
			cfg.setNumDays((numDays != null ? Integer.valueOf(numDays.intValue() + 1) : Integer.valueOf(0)));
			cfg.setDefaultPageSize((defaultPageSize != null ? Integer.valueOf(defaultPageSize.intValue() + 1) : Integer.valueOf(0)));
			cfg.persist();
		}
//		cfg.delete();
//		
////		file.erase();
//		
////		List<FileEntity> files = file.listBy("Path", null, "test");
		List<MyAppCfg> cfgs = MyAppCfg.listBy(MyAppCfg.class, "Enable", Boolean.TRUE);
		
		for (MyAppCfg item : cfgs) {
			System.out.println(item.toString());
		}
		
		
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(org.effortless.model.LogData.class.getName());
			StartupDb.addAppEntity(org.effortless.tests.model.MyAppCfg.class.getName());
			
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
