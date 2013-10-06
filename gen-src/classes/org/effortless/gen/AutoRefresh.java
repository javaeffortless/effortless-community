package org.effortless.gen;

import java.io.File;

import org.effortless.server.WatchDir;

public class AutoRefresh extends WatchDir {

    protected void recompileDs (File file) {
//		org.effortless.model.StartupDb.startDs(fileAddr);
    }
    
    protected void recompileGy (File file) {
		if (file.exists()) {
			org.effortless.server.Compiler.compile(file);
			MainTransform.process(file);
		}
    }
    
}
