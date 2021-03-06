package org.effortless.gen;

import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.effortless.core.ModelException;
import org.effortless.gen.ui.MainVMTransform;
import org.effortless.server.ServerContext;
import org.effortless.util.XmlAppsUtil;

public class MainTransform {

	public static void process() {
		Map<String, List<String>> apps = null;
		try {
			apps = XmlAppsUtil.autoDiscoverAppEntities(ServerContext.getRootContext());
		} catch (ValidityException e) {
			throw new ModelException(e);
		} catch (ParsingException e) {
			throw new ModelException(e);
		} catch (IOException e) {
			throw new ModelException(e);
		}
		Set<String> keys = apps.keySet();
		for (String appId : keys) {
			generateMainWindow(appId);
		}
	}
	
	public static void process(File file) {
		if (file != null && file.exists()) {
			String appId = GenContext.toAppId(file);
			generateMainWindow(appId);
		}
	}

	public static void generateMainWindow(String appId) {
		GApplication app = GenContext.getApplication(appId);
		MainVMTransform.writeMainZul(app);
	}

}
