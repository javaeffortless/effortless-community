package org.effortless.server;


import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

import javax.servlet.ServletContext;

import org.effortless.core.ModelException;
 
/**
 * Example to watch a directory (or tree) for changes to files.
 */

public class WatchDir {
 
    private WatchService watcher;
    private Map<WatchKey,Path> keys;
    private boolean recursive;
    private boolean trace = false;
 
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
 
    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
 
    protected String rootCtx;
    protected ServletContext ctx;

    /**
     * Creates a WatchService and registers the given directory
     */
    public WatchDir() {
    	super();
    	
    }
 
    
    
    /**
     * Creates a WatchService and registers the given directory
     */
    protected void setup(Path dir, boolean recursive, String rootCtx, ServletContext ctx) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = recursive;
        this.rootCtx = rootCtx;
        this.ctx = ctx;
 
        if (recursive) {
            System.out.format("Scanning %s ...\n", dir);
            registerAll(dir);
            System.out.println("Done.");
        } else {
            register(dir);
        }
 
        // enable trace after initial registration
        this.trace = true;
    }
 
    
    protected void recompileDs (File file) {
//		org.effortless.model.StartupDb.startDs(fileAddr);
    }
    
    protected void recompileGy (File file) {
//		if (file.exists()) {
//			org.effortless.util.Compiler.compile(file);
//			MainTransform.process(file);
//		}
    }
    
    
    /**
     * Process all events for keys queued to the watcher
     */
    protected void processEvents() {
        for (;;) {
        	ServerContext.setServletContext(this.ctx);
            ServerContext.setRootContext(this.rootCtx);

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
 
            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
 
            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
 
                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }
 
                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);
		File file = child.toFile();

		String fileAddr = file.getAbsolutePath();
//println "fileAddr = " + fileAddr
String fileExt = org.apache.commons.io.FilenameUtils.getExtension(fileAddr);
		if ("xml".equals(fileExt)) {
			String baseName = org.apache.commons.io.FilenameUtils.getBaseName(fileAddr);
			if (baseName.endsWith("-ds")) {
				recompileDs(file);
//				org.effortless.model.StartupDb.startDs(fileAddr);
			}
		}
		else if ("gy".equals(fileExt)) {
			recompileGy(file);
//			if (file.exists()) {
//				org.effortless.util.Compiler.compile(file);
//				MainTransform.process(file);
//			}
		}
 
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);
 
                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
 
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }
 
    public void setup (final String directory, final boolean recursive, final String rootCtx, final ServletContext ctx) {
        // register directory and process its events
        Path dir = Paths.get(directory);
        try {
        	this.setup(dir, recursive, rootCtx, ctx);
		} catch (IOException e) {
			throw new ModelException(e);
		}
    }
    
    public void start () {
    	final WatchDir _this = this;
        Runnable p = new Runnable() {
    		public void run () {
   				_this.processEvents();
    		}
    	};
         new Thread(p).start();
    }

}

