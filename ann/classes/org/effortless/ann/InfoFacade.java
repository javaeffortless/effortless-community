package org.effortless.ann;

import org.effortless.core.StringUtils;

public class InfoFacade {

	public static boolean isSingleton (Class<?> clazz) {
		boolean result = false;
		if (clazz != null) {
			String name = StringUtils.forceNotNull(clazz.getSimpleName());
			result = false;
			result = result || "settings".equals(name.toLowerCase()); 
			result = result || "configuration".equals(name.toLowerCase()); 
		}
		return result;
	}
	
}
