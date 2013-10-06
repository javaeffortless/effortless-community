package org.effortless.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

//import org.apache.commons.io.IOUtils;

public class IOUtils {

	public static void copy(Reader reader, OutputStream os) throws IOException {
		org.apache.commons.io.IOUtils.copy(reader, os);
	}

	public static void closeQuietly(Reader reader) {
		org.apache.commons.io.IOUtils.closeQuietly(reader);
	}

	public static void closeQuietly(OutputStream os) {
		org.apache.commons.io.IOUtils.closeQuietly(os);
	}

	public static void copy(InputStream input, OutputStream os) throws IOException {
		org.apache.commons.io.IOUtils.copy(input, os);
	}

	public static void closeQuietly(InputStream input) {
		org.apache.commons.io.IOUtils.closeQuietly(input);
	}

}
