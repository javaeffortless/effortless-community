package org.effortless.server;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.effortless.core.FilenameUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateSessionFactoryBuilder extends org.effortless.server.WatchDir {

	public HibernateSessionFactoryBuilder (Path dir, boolean recursive, String root) throws IOException {
		super();
		setup(dir, recursive, root, null);
	}
	
    protected void processFile(String kind, String fileName) {
    	String name = FilenameUtils.getName(fileName);
    	if (name != null && name.trim().toLowerCase().endsWith("-ds.xml")) {
    		Configuration configuration = new Configuration();
            configuration.configure(fileName);
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());    		
    		
    		
    		System.out.println(" ES UN sessionFactory? " + (sessionFactory != null));
    	}
	}
    
    public synchronized void start () {
        final HibernateSessionFactoryBuilder builder = this;
        Runnable runnable = new Runnable () {

			public void run() {
		        builder.processEvents();
			}
        	
        };
        Thread thread = new Thread(runnable);
        thread.setContextClassLoader(Thread.currentThread().getContextClassLoader());
        thread.start();
    }
    
    public static synchronized void runBuilder (String path) {
        final Path dir = Paths.get(path);
        HibernateSessionFactoryBuilder builder = null;
		try {
			builder = new HibernateSessionFactoryBuilder(dir, true, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (builder != null) {
			builder.start();
		}
    }
	
}
