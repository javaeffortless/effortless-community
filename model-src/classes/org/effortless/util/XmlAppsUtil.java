package org.effortless.util;

import nu.xom.*;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.effortless.core.FilenameUtils;
import org.effortless.core.XmlHibernateUtil;
import org.effortless.model.SessionManager;

//import org.effortless.MySession;

public class XmlAppsUtil {

	private static Builder builder = new Builder(false);

	public static Map<String, List<String>> autoDiscoverAppEntities (String file) throws ValidityException, ParsingException, IOException {
		Map<String, List<String>> result = null;
		File folder = new File(file);
		if (folder != null && folder.isDirectory()) {
			result = new HashMap<String, List<String>>();
			File[] items = folder.listFiles();
			for (File item : items) {
				if (item != null && item.isFile()) {
					String path = item.getAbsolutePath();
					String extension = FilenameUtils.getExtension(path);
					if ("xml".equals(extension)) {
						List<String> entities = XmlHibernateUtil.loadMapping(path);
						if (entities != null && entities.size() > 0) {
							String firstEntity = entities.get(0);
							String appId = SessionManager.getDbId(firstEntity);
							result.put(appId, entities);
						}
					}
				}
			}
		}
		else {
			result = discoverAppEntities(folder);
		}
		return result;
	}

	
	
	public static Map<String, List<String>> discoverAppEntities (String file) throws ValidityException, ParsingException, IOException {
		return discoverAppEntities(new File(file));
	}

	public static Map<String, List<String>> discoverAppEntities (File file) throws ValidityException, ParsingException, IOException {
		Map<String, List<String>> result = null;
		result = new HashMap<String, List<String>>();

		Document doc = XmlAppsUtil.builder.build(file);
		Element root = doc.getRootElement();
		Elements apps = root.getChildElements("app");
		int sizeApps = apps.size();
		for (int i = 0; i < sizeApps; i++) {
			Element app = apps.get(i);
			String appId = app.getAttributeValue("id");
			List<String> listEntities = new ArrayList<String>();
			Elements entities = app.getChildElements("entity");
			int sizeEntities = entities.size();
			for (int j = 0; j < sizeEntities; j++) {
				Element entity = entities.get(j);
				String entityId = entity.getAttributeValue("id");
				listEntities.add(entityId);
			}
			result.put(appId, listEntities);
		}

		return result;
	}

	public static void addEntity (String file, String app, String entity) throws ParsingException, IOException {
		addFileEntity(new File(file), app, entity);
	}

	public static void addFileEntity (File file, String app, String entity) throws ParsingException, IOException {
		Document doc = (!file.exists() ? create() : XmlAppsUtil.builder.build(file));

		Element nodeApp = addApp(doc, app);
		addEntity(nodeApp, entity);
		saveDoc(file, doc);
	}

	public static Document create () {
		Document result = null;
		
		Element root = new Element("apps");
		result = new Document(root);
		return result;
	}

	public static Document saveDoc (String file, Document doc) throws IOException {
		return saveDoc(new File(file), doc);
	}

	public static Document saveDoc (File file, Document doc) throws IOException {
		Document result = null;
		FileOutputStream fos = new FileOutputStream(file);
		Serializer out = new Serializer(fos, "UTF-8");
		out.setIndent(2);

		out.write(doc);
		out.flush();
		
		result = doc;
		return result;
        }

	public static Element addApp (Document doc, String app) {
		Element result = null;
		result = loadApp(doc, app);
		if (result == null) {
			Element root = doc.getRootElement();

			Element nodeApp = new Element("app");
			nodeApp.addAttribute(new Attribute("id", app));
			root.appendChild(nodeApp);

			result = nodeApp;
		}
		return result;
	}

	public static Element addEntity (Element app, String entity) {
		Element result = null;
		result = loadEntity(app, entity);
		if (result == null) {
			Element nodeEntity = new Element("entity");
			nodeEntity.addAttribute(new Attribute("id", entity));
			app.appendChild(nodeEntity);

			result = nodeEntity;
		}
		return result;
	}


	public static boolean containsApp (Document doc, String app) {
		return (loadApp(doc, app) != null);
	}

	public static Element loadApp (Document doc, String app) {
		Element result = null;
		Element root = doc.getRootElement();
		Elements apps = root.getChildElements();
		int size = apps.size();
		for (int i = 0; i < size; i++) {
			Element nodeApp = apps.get(i);
			if (app.equals(nodeApp.getAttributeValue("id"))) {
				result = nodeApp;
				break;
			}
		}
		return result;
	}

	public static boolean containsEntity (Element app, String entity) {
		return (loadEntity(app, entity) != null);
	}

	public static Element loadEntity (Element app, String entity) {
		Element result = null;
		Elements entities = app.getChildElements();
		int size = entities.size();
		for (int i = 0; i < size; i++) {
			Element nodeEntity = entities.get(i);
			if (entity.equals(nodeEntity.getAttributeValue("id"))) {
				result = nodeEntity;
				break;
			}
		}
		return result;
	}

}
