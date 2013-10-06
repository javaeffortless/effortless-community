package org.effortless.core;

//import org.apache.commons.io.FilenameUtils;

public class FilenameUtils {

	public static String getExtension (String path) {
		String result = null;
		result = (path != null ? org.apache.commons.io.FilenameUtils.getExtension(path) : null);
		return result;
	}
	
	public static String getName (String path) {
		String result = null;
		result = (path != null ? org.apache.commons.io.FilenameUtils.getName(path) : null);
		return result;
	}

	public static String concat(String prefix, String suffix) {
		String result = null;
		result = org.apache.commons.io.FilenameUtils.concat(prefix, suffix);
		return result;
	}

	public static String getBaseName(String path) {
		String result = null;
		result = org.apache.commons.io.FilenameUtils.getBaseName(path);
		return result;
	}
	
	
}
