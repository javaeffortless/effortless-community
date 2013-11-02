package org.effortless.core;

import nu.xom.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlHibernateUtil {

	private static Builder builder = new Builder(true);

	public static void addEntities (String file, List<String> entities) throws ParsingException, IOException {
		addEntities(new File(file), entities);
	}

	
	public static void removeDeletedEntities (File file) throws ParsingException, IOException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl != null) {
			Document doc = loadDs(file);
			Element root = doc.getRootElement();
			Element sf = (root != null ? root.getFirstChildElement("session-factory") : null);
			Elements mappings = (sf != null ? sf.getChildElements("mapping") : null);
			int size = (mappings != null ? mappings.size() : 0);
			List<Element> toRemove = new ArrayList<Element>();
			Class<?> clazz = null;
			for (int i = 0; i < size; i++) {
				Element mapping = mappings.get(i);
				String entity = mapping.getAttributeValue("class");
				if (entity != null) {
					try {
						clazz = cl.loadClass(entity);
					}
					catch (Throwable t) {
						toRemove.add(mapping);
						mapping.detach();
					}
				}
			}
			if (toRemove != null && toRemove.size() > 0) {
				saveDs(file, doc);
			}
		}
	}
	

	public static void addEntities (File file, List<String> entities) throws ParsingException, IOException {
		Document doc = loadDs(file);
		Element sf = loadSf(doc);

		for (String entity : entities) {
//		entities.each { entity ->
			if (!containsMapping(sf, entity)) {
				addMapping(sf, entity);
			}
		}
		//addMapping(sf, "org.effortless.sandbox.Book1")	
		//addMapping(sf, "org.effortless.sandbox.Book2")	
		//addMapping(sf, "org.effortless.sandbox.Book3")	

		saveDs(file, doc);
	}


	public static Document loadDs (String file) throws ParsingException, IOException {
		return loadDs(new File(file));
	}

	public static Document loadDs (File file) throws ParsingException, IOException {
		Document result = null;
		result = XmlHibernateUtil.builder.build(file);
		return result;
	}

	public static Document createDs (String file, String dbId) throws IOException {
		return createDs(new File(file), dbId);
	}

	public static Document createDs (File file, String dbId) throws IOException {
		Document result = null;
		Document doc = create(dbId);
		result = saveDs(file, doc);
		return result;
        }

	public static Document saveDs (String file, Document doc) throws IOException {
		return saveDs(new File(file), doc);
	}

	public static Document saveDs (File file, Document doc) throws IOException {
		Document result = null;
		FileOutputStream fos = new FileOutputStream(file);
		Serializer out = new Serializer(fos, "UTF-8");
		out.setIndent(2);

		out.write(doc);
		out.flush();
		result = doc;
		return result;
        }

	public static Element loadSf (Document doc) {
		Element result = null;
		Element cfg = doc.getRootElement();
		Elements children = cfg.getChildElements();
		result = children.get(0);
		return result;
	}
	
	public static List<String> loadMapping (String file) throws ParsingException, IOException {
		List<String> result = null;
		result = new ArrayList<String>();
		Document doc = loadDs(file);
		Element root = doc.getRootElement();
		Element sf = (root != null ? root.getFirstChildElement("session-factory") : null);
		Elements mappings = (sf != null ? sf.getChildElements("mapping") : null);
		int size = (mappings != null ? mappings.size() : 0);
		for (int i = 0; i < size; i++) {
			Element mapping = mappings.get(i);
			String entity = mapping.getAttributeValue("class");
			result.add(entity);
		}
		return result;
	}

	public static Document create (String dbId) {
		Document result = null;
		

//		Text docType = new Text("<!DOCTYPE hibernate-configuration PUBLIC \"-//Hibernate/Hibernate Configuration DTD//EN\" \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">")
//<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD//EN" "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

//		DocType docType = new DocType("hibernate-configuration", "-//Hibernate/Hibernate Configuration DTD//EN",  "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd");
		DocType docType = new DocType("hibernate-configuration", "-//Hibernate/Hibernate Configuration DTD//EN",  "hibernate-configuration-3.0.dtd");


		Element root = new Element("hibernate-configuration");
		result = new Document(root);
		result.setDocType(docType);
		Element sf = new Element("session-factory");
		root.appendChild(sf);

		addProperty(sf, "id", dbId);
		
//		addProperty(sf, "hibernate.session_factory_name", "java:jboss/hibernate/" + dbId + "/SessionFactory")
//		addProperty(sf, "hibernate.session_factory_name", "java:/hibernate/" + dbId + "/SessionFactory")
		addProperty(sf, "hibernate.session_factory_name", "gy:/hibernate/" + dbId + "/SessionFactory");
		
//		addProperty(sf, "hibernate.transaction.manager_lookup_class", "org.hibernate.transaction.JBossTransactionManagerLookup")
//		addProperty(sf, "hibernate.transaction.factory_class", "org.hibernate.transaction.CMTTransactionFactory")
		addProperty(sf, "hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
//		addProperty(sf, "hibernate.jndi.class", "org.jboss.as.naming.InitialContextFactory")
//		addProperty(sf, "hibernate.jndi.url", "jnp://localhost:1099")

//		addProperty(sf, "jta.UserTransaction", "gy:/comp/" + dbId + "/UserTransaction")

		addProperty(sf, "dialect", "org.hibernate.dialect.H2Dialect");
		addProperty(sf, "show_sql", "true");
		addProperty(sf, "format_sql", "true");
		addProperty(sf, "use_sql_comments", "true");
		
		addProperty(sf, "connection.driver_class", "org.h2.Driver");
		addProperty(sf, "connection.url", "jdbc:h2:~/" + dbId + ";AUTO_SERVER=TRUE");
		
		String username = encrypt(dbId, "user" + dbId + "");
		String pass = encrypt(dbId, "pass" + dbId + "");
		
		addProperty(sf, "connection.username", username);
		addProperty(sf, "connection.password", pass);

		addProperty(sf, "hbm2ddl.auto", "update");
//		addProperty(sf, "hbm2ddl.auto", "validate");
		
		//<!-- Enable Hibernate's automatic session context management -->
		addProperty(sf, "current_session_context_class", "thread");
		//<!-- Disable the second-level cache -->
		addProperty(sf, "cache.provider_class", "org.hibernate.cache.NoCacheProvider");
	

//		addMapping(sf, "org.effortless.sandbox.Book2")	

//		createXmlDeclaration()
//		createDocType()
		
		return result;
	}

	public static Node createXmlDeclaration () {
		return new Text("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}


	public static Text addDocType (ParentNode parent) {
		Text result = null;
		result = new Text("<!DOCTYPE hibernate-configuration PUBLIC \"-//Hibernate/Hibernate Configuration DTD//EN\" \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">");
		parent.appendChild(result);
		return result;
	}

	public static Element addProperty (ParentNode parent, String name, String value) {
		Element result = null;
		result = new Element("property");
		result.addAttribute(new Attribute("name", name));
		result.appendChild(value);
		parent.appendChild(result);
		return result;
	}

	public static Element addMapping (ParentNode parent, String clazz) {
		Element result = null;
		result = new Element("mapping");
		result.addAttribute(new Attribute("class", clazz));
		parent.appendChild(result);
		return result;
	}

	public static boolean containsMapping (Element parent, String clazz) {
		boolean result = false;
		if (parent != null && clazz != null) {
			Elements mappings = parent.getChildElements("mapping");
			int size = mappings.size();
			for (int i = 0; i < size; i++) {
				Element mapping = mappings.get(i);
				String classValue = mapping.getAttributeValue("class");
				if (clazz.equals(classValue)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public static String encrypt (String key, String value) {
		String result = null;
		//result = value
		String str = key + value;
		result = new String(encodeBase64(str));//str.bytes.encodeBase64().toString();
		return result;
	}
	
	protected static byte[] encodeBase64 (String value) {
		byte[] result = null;
		byte[] binaryData = value.getBytes();
		result = org.apache.commons.codec.binary.Base64.encodeBase64(binaryData);
//		result = str.bytes.encodeBase64().toString();
		return result;
	}

	protected static byte[] decodeBase64 (String value) {
		byte[] result = null;
		byte[] binaryData = value.getBytes();
		result = org.apache.commons.codec.binary.Base64.decodeBase64(binaryData);
//		return value.decodeBase64();
		return result;
	}
	
	public static String decrypt (String key, String value) {
		String result = null;
		//result = value
		
		result = new String(decodeBase64(value));
		result = (key != null && result.startsWith(key) ? result.substring(key.length()) : result);
		
		return result;
	}
	
	public static void decryptCfg (org.hibernate.cfg.Configuration cfg) {
		String dbId = cfg.getProperty("id");
	
		String user = cfg.getProperty("connection.username");
		String pass = cfg.getProperty("connection.password");
		
		String decryptedUser = decrypt(dbId, user);
		String decryptedPwd = decrypt(dbId, pass);
		
//		println ">>>>>>>>>>>>>>>>>>>>>dbId = $dbId"
//		println ">>>>>>>>>>>>>>>>>>>>>decryptedUser = $decryptedUser"
//		println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>decryptedPwd = $decryptedPwd"
		
//		cfg.setProperty("connection.username", decryptedUser);
//		cfg.setProperty("connection.password", decryptedPwd);
	}

//http://old.nabble.com/Configure-Hibernate.cfg.xml-with-Encrypted-password-td16319026s21332.html
//http://stackoverflow.com/questions/5747136/hibernate-authentication-without-passwords-stored-in-plain-text
//http://www.jasypt.org/api/jasypt/1.8/org/jasypt/hibernate/connectionprovider/EncryptedPasswordDriverManagerConnectionProvider.html
//http://docs.jboss.org/hibernate/orm/3.6/javadocs/org/hibernate/connection/ConnectionProvider.html

}

