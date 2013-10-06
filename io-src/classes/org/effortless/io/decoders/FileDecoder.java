package org.effortless.io.decoders;

import java.io.File;

public class FileDecoder extends AbstractDecoder<File> {

	public FileDecoder () {
		super();
	}

	public File decode(String value) {
		File result = null;
		if (value != null) {
			File file = new File(value);
			if (file.exists()) {
				result = file;
			}
		}
		return result;
	}
	
}
