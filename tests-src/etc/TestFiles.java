package org.effortless.tests.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.effortless.model.FileEntity;
import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestFiles {

	protected static final String[] FILES = {
		"/home/jesus/Descargas/SangaHolidayScheduleupdated.odt",
		"/home/jesus/dwhelper/ABC Song.flv", 
		"/home/jesus/Escritorio/safeplay_button-249x300.jpg",
		"/home/jesus/Escritorio/PNG_transparency_demonstration_1.png", 
		"/home/jesus/Escritorio/banano.gif", 
		"/home/jesus/Descargas/darknet5.doc", 
		"/home/jesus/Descargas/VAPProjectTemplate_New.xls", 
		"/home/jesus/Descargas/Linkedin_Presentation.odp", 
		"/home/jesus/Descargas/meta-extractor-developers-guide-v3.pdf", 
		"/home/jesus/Descargas/tika-app-1.2.jar", 
		"/home/jesus/programs/eclipse-indigo/about.html", 
		"/home/jesus/Escritorio/Windows XP SSD_files/widgets.js", 
	};
	
	public static void main (String[] args) {
		setupDs();

		for (String strFile : FILES) {
			FileEntity file = new FileEntity();
//		file.setContent("/home/jesus/Escritorio/test.txt");
//		file.setContent("/home/jesus/Descargas/apache-tomcat-7.0.30.7z");
			file.setContent(strFile);
			file.persist();
		}
		
		
		FileEntity file = new FileEntity();
//		file.setContent("/home/jesus/Escritorio/test.txt");
//		file.setContent("/home/jesus/Descargas/apache-tomcat-7.0.30.7z");
		file.setContent("/home/jesus/Escritorio/LA GRACIOSA.jpg");
		List<FileEntity> list = FileEntity.listBy();
		file.persist();
		
		file.delete();
		
//		file.erase();
		
//		List<FileEntity> files = file.listBy("Path", null, "test");
		List<FileEntity> files = FileEntity.listBy();//"home");
		
		for (FileEntity ifile : files) {
			System.out.println(ifile.toString());
		}
		
		
	}
	
	protected static void setupDs() {
		try {
//			StartupDb.addAppEntity(Telephone.class.getName());
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
