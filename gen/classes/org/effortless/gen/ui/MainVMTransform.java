package org.effortless.gen.ui;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.effortless.core.ModelException;
import org.effortless.core.StringUtils;
import org.effortless.gen.AppTransform;
import org.effortless.gen.GenContext;
import org.effortless.server.ServerContext;
import org.effortless.ui.resources.ImageResources;

/**
 *
 * Implements

	
 * 
 * 
 * @author jesus
 *
 */

public class MainVMTransform {

	
	/**
	 * 
	 * 

<zk>
  <main-window fulfill="onLoginSuccess">
    <menu link="Cuentas" selected="true">
        <menu link="Cuenta" />
        <menu link="MovimientoCuenta" />
        <menu link="LotogestFileEntity" />
    </menu>
    <menu link="Loterias" fulfill="onSelect">
        <menu link="Loteria" />
        <menu link="Sorteo" />
        <menu link="Subscripcion" />
        <menu link="Apuesta" />
    </menu>
    <menu link="Usuarios" fulfill="onSelect">
        <menu link="Perfil" />
        <menu link="Usuario" />
    </menu>
  </main-window>
</zk>

	 * 
	 * 
	 */
	public static void writeMainZul (String appId) {
		List<String> zul = new ArrayList<String>();
		
		String logo = "main/main_logo.png";
		ImageResources.copy(appId, "logo", logo, 64);
			
		zul.add("<zk>");
		zul.add("  <main-window fulfill=\"onLoginSuccess\">");
			
		List<String> modules = getModules(appId);
		int index = 0;
		for (String module : modules) {
			index += 1;
			module = StringUtils.uncapFirst(module);
			String img = "main/" + module + ".png";
			ImageResources.copy(appId, module, img, 48);
			String moduleAttr = (index == 1 ? "selected=\"true\"" : "fulfill=\"onSelect\"");
			zul.add("    <menu link=\"" + StringUtils.capFirst(module) + "\" " + moduleAttr + ">");
				
			List<String> options = getOpciones(appId, module);
			for (String option : options) {
				String lowerOption = StringUtils.uncapFirst(option);
				String imgOption = "main/" + lowerOption + ".png";
				ImageResources.copy(appId, lowerOption, imgOption, 24);
				zul.add("        <menu link=\"" + StringUtils.capFirst(lowerOption) + "\" />");
			}
			
			zul.add("    </menu>");
		}
		zul.add("  </main-window>");
		zul.add("</zk>");
			
		String filename = ServerContext.getRootContext() + appId + "/resources/main/main.zul";
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}

	public static List<String> getModules (String appId) {
		List<String> result = null;
		if (appId != null) {
			AppTransform appTransform = GenContext.getAppTransform(appId, false);
			if (appTransform != null) {
				result = appTransform.getUnitNames();
			}
//			else {
//				result = new ArrayList<String>();
//				result.add("sorteos");
//				result.add("configuracion");
//			}
		}
		return result;
	}

	public static List<String> getOpciones (String app, String module) {
		List<String> result = null;
		AppTransform appTransform = GenContext.getAppTransform(app, false);
		if (appTransform != null) {
//			resulappTransform.getClassesByUnit(module);
			result = appTransform.getEntitiesByUnit(module);
		}
//		result = new ArrayList<String>();
//		result.add("basic");
		return result;
	}
	
	
}
