package org.effortless.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.effortless.core.ModelException;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;

public class Compiler {

	public static final String EXTENSION = "gy";//"groovy"

	public static void precompileApplication(GroovyClassLoader gcl, File folder, List<File> errors) {
		try {
			File[] list = folder.listFiles();
			
			int index = 0;
			for ( File f : list ) {
			    if (f.isDirectory()) {
//			    	gcl.addClasspath(f.getAbsolutePath());
if (false) {
			    	precompileApplication(gcl, f, errors);
}
//			        ServerContext.addImportPackage("org.effortless.lotogest");
			        //System.out.println( "Dir:" + f.getAbsoluteFile())
			    }
			    else {
			    	if (index == 0) {
				        ServerContext.addMainPackageFile(f);
			    	}
			    	index += 1;
		    		precompileFile(gcl, f, errors);
			    }
			}

			resolveConflicts(gcl, errors);
			if (errors.size() > 0) {
				for (File error : errors) {
					try {
						precompileFile(gcl, error, null);
					}
					catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
			}
		}
		catch (IOException e) {
			throw new ModelException(e);
		}
	}
	
	
	protected static void resolveConflicts (GroovyClassLoader gcl, List<File> errors) throws IOException {
		int errorsSize = (errors != null ? errors.size() : 0);
		if (errorsSize > 0) {
			List<File> newErrors = new ArrayList<File>();
			if (errors != null) {
				newErrors.addAll(errors);
			}
			for (File error : newErrors) {
				try {
					precompileFile(gcl, error, null);
					errors.remove(error);
				}
				catch (RuntimeException e) {
					
				}
			}
			int newErrorSize = errors.size();
			if (newErrorSize < errorsSize && newErrorSize > 0) {
				resolveConflicts(gcl, errors);
			}
		}
	}


	public static void precompileAllApplications(GroovyClassLoader gcl, String path) {
		try {
			File root = new File( path );
			File[] list = root.listFiles();

			List<File> errors = new ArrayList<File>();
			for ( File f : list ) {
			    if ( f.isDirectory() ) {
			    	if (checkValidApp(f)) {
			    		precompileApplication(gcl, f, errors);
			    	}
			    }
			}
			resolveConflicts(gcl, errors);
		}
		catch (IOException e) {
			throw new ModelException(e);
		}
	}
	
	
	public static boolean checkValidApp(File f) {
		boolean result = false;
		if (f != null && f.isDirectory()) {
			String fName = f.getName();
			result = true;
			result = result && !fName.startsWith(".");
			result = result && !fName.equals("META-INF");
			result = result && !fName.equals("WEB-INF");
		}
		return result;
	}


	public static void precompile(GroovyClassLoader gcl, String path) {
		if (true) {
			precompileAllApplications(gcl, path);
		}
		else {
			try {
				File root = new File( path );
				File[] list = root.listFiles();

				List<File> errors = new ArrayList<File>();
				for ( File f : list ) {
				    if ( f.isDirectory() ) {
				    	gcl.addClasspath(f.getAbsolutePath());
				        precompile(gcl, f.getAbsolutePath());
//				        ServerContext.addImportPackage("org.effortless.lotogest");
				        //System.out.println( "Dir:" + f.getAbsoluteFile())
				    }
				    else {
				    	if (true) {
				    		precompileFile(gcl, f, errors);
				    	}
				    	else {
						    String fName = f.getName();
							if (EXTENSION.equals(org.apache.commons.io.FilenameUtils.getExtension(fName)) && !checkRootCtx(f)) {
								//System.out.println( "File:" + f.getAbsoluteFile())
								GroovyCodeSource codeSource = new GroovyCodeSource(f);
								try {
									gcl.parseClass(codeSource, true);
								}
								catch (RuntimeException e) {
									
									throw e;
								}
						        ServerContext.addMainPackageFile(f);
								if (false) {
									Class[] classes = gcl.getLoadedClasses();
									for (int i = 0; i < classes.length; i++) {
										Class clazz = classes[i];
										ClassLoader cloader = clazz.getClassLoader();
										System.out.println("clazz compiled = " + clazz.getName());
										System.out.println("clazz loader compiled = " + cloader);
									}
								}
							}
				    	}
				    }
				}

	    		for (File error : errors) {
	    			precompileFile(gcl, error, null);
	    		}
			}
			catch (IOException e) {
				throw new ModelException(e);
			}
		}
	}
	
	public static void precompileFile (GroovyClassLoader gcl, File f, List<File> errors) throws IOException {
	    String fName = f.getName();
		if (EXTENSION.equals(org.apache.commons.io.FilenameUtils.getExtension(fName)) && !checkRootCtx(f)) {
			//System.out.println( "File:" + f.getAbsoluteFile())
			GroovyCodeSource codeSource = new GroovyCodeSource(f);
			try {
				gcl.parseClass(codeSource, true);
			}
			catch (RuntimeException e) {
				if (errors != null) {
					errors.add(f);
				}
				else {
					throw e;
				}
			}
//	        ServerContext.addMainPackageFile(f);
			if (false) {
				Class[] classes = gcl.getLoadedClasses();
				for (int i = 0; i < classes.length; i++) {
					Class clazz = classes[i];
					ClassLoader cloader = clazz.getClassLoader();
					System.out.println("clazz compiled = " + clazz.getName());
					System.out.println("clazz loader compiled = " + cloader);
				}
			}
		}
	}
	
	public static void compile (File file) {
		try {
			GroovyClassLoader gcl = (GroovyClassLoader)Thread.currentThread().getContextClassLoader();
			GroovyCodeSource codeSource = new GroovyCodeSource(file);
			gcl.parseClass(codeSource, true);
		}
		catch (IOException e) {
			throw new ModelException(e);
		}
	}
	
	protected static boolean checkRootCtx (File file) {
		boolean result = false;
		if (file != null && file.isFile()) {
			String rootCtx = ServerContext.getRootContext();
			String fileParent = file.getParent() + File.separator;
			result = rootCtx.equals(fileParent);
		}
		return result;
	}


	public static void precompile(String path) {
		GroovyClassLoader gcl = (GroovyClassLoader)Thread.currentThread().getContextClassLoader();
//		gcl.setShouldRecompile(false);
		gcl.setShouldRecompile(true);
		precompile(gcl, path);
		gcl.setShouldRecompile(true);
	}

	public static void precompile() {
		precompile(ServerContext.getRootContext());
	}

/*

if (true) {

GroovyClassLoader gcl = Thread.currentThread().getContextClassLoader()
gcl.setShouldRecompile(false)

precompile(gcl, effortless.MySession.getRootContext() + "/org/effortless/sandbox")

if (false) {
GroovyCodeSource codeSource = new GroovyCodeSource(new java.io.File(effortless.MySession.getRootContext() + "/org/effortless/sandbox/Book2.groovy"))
gcl.parseClass(codeSource, true)

GroovyCodeSource codeSource2 = new GroovyCodeSource(new java.io.File(effortless.MySession.getRootContext() + "/org/effortless/sandbox/Book.groovy"))
gcl.parseClass(codeSource2, true)
}
}

*/


}

