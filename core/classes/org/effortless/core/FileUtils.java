package org.effortless.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


//import org.apache.commons.io.FileUtils;

public class FileUtils {

	public static void copyInputStreamToFile(InputStream is, File file) {
		try {
			org.apache.commons.io.FileUtils.copyInputStreamToFile(is, file);
		} catch (IOException e) {
			throw new ModelException(e);
		}
	}

	public static File renameToTempDir (File file, String newName) {
		return renameToTempDir(file, newName, null, null, 1);
	}
	
	public static File renameToTempDir (File file, String newName, String prefix) {
		return renameToTempDir(file, newName, prefix, null, 1);
	}
	
	public static File renameToTempDir (File file, String newName, String prefix, String suffix, int attempts) {
		File result = null;
		if (file != null && file.exists()) {
			File folder = createTempFolder(prefix, suffix, attempts);
			result = new File(folder, newName);
			file.renameTo(result);
		}
		return result;
	}

	public static File createTempFolder() {
		return createTempFolder(null, null, 1);
	}
	
	public static File createTempFolder(String prefix) {
		return createTempFolder(prefix, null, 1);
	}
	
	public static File createTempFolder(String prefix, String suffix) {
		return createTempFolder(prefix, suffix, 1);
	}
	
	public static File createTempFolder(String prefix, String suffix, int attempts) {
		File result = null;

		prefix = (prefix != null ? prefix : "");
		suffix = (suffix != null ? suffix : "");

		String tmpDir = System.getProperty("java.io.tmpdir");

		File baseDir = new File(tmpDir);
		String baseName = "" + System.currentTimeMillis();

		for (int counter = 0; counter < attempts; counter++) {
			String counterStr = (counter > 0 ? "-" + counter : "");
			String fileName = prefix + baseName + suffix + counterStr;
			File tempDir = new File(baseDir, fileName);
			if (tempDir.mkdir()) {
				result = tempDir;
				break;
			}
		}
		if (result == null) {
			throw new IllegalStateException("Failed to create directory");
		}

		return result;
	}

}
