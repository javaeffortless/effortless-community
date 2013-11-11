package org.effortless.model;

import java.io.File;

import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.effortless.core.FilenameUtils;
import org.effortless.core.ModelException;
//import org.effortless.core.GlobalContext;
import org.effortless.server.ServerContext;
import org.hibernate.SessionFactory;
//import org.hibernate.event.service.spi.EventListenerRegistry;
//import org.hibernate.event.spi.EventType;
//import org.hibernate.internal.SessionFactoryImpl;

public class StartupDb {

//	public static String FILE_APPS = "apps.xml";
//
//	public static String fileApps () {
//		String result = null;
//		String rootCtx = org.effortless.MySession.getRootContext();
//		result = FilenameUtils.concat(rootCtx, FILE_APPS);
//		return result;
//	}
//
//	public static void addAppEntity (String entity) throws ParsingException, IOException {
//		String file = fileApps();
//		String app = org.effortless.MySession.getDbId(entity);
//		org.effortless.util.XmlAppsUtil.addEntity(file, app, entity);
//	}
//
//	public static void addAppExternEntity (String entity, String externEntity) throws ParsingException, IOException {
//		String file = fileApps();
//		String app = org.effortless.MySession.getDbId(entity);
//		org.effortless.util.XmlAppsUtil.addEntity(file, app, externEntity);
//	}

	
	
	public static String app2DsFile (String app) {
		String result = null;
		String rootCtx = org.effortless.server.ServerContext.getRootContext();
		String dbId = SessionManager.getDbId(app) + "-ds.xml";
		result = FilenameUtils.concat(rootCtx, dbId);
		return result;
	}

	public static void persistDb (String app, List<String> entities) throws ParsingException, IOException {
		String file = app2DsFile(app);
		File fichero = new File(file);
		if (!fichero.exists()) {
			org.effortless.core.XmlHibernateUtil.createDs(fichero, app);
		}
		org.effortless.core.XmlHibernateUtil.addEntities(fichero, entities);
	}

	public static void updateDb (String entity) throws ParsingException, IOException {
		List<String> entities = new ArrayList<String>();
		entities.add(entity);
		String app = SessionManager.getDbId(entity);
		persistDb(app, entities);
	}

	public static void updateDbExternEntity (String entity, String externEntity) throws ParsingException, IOException {
		List<String> entities = new ArrayList<String>();
		entities.add(externEntity);
		String app = SessionManager.getDbId(entity);
		persistDb(app, entities);
	}

	public static void runDb (String app) throws ParsingException, IOException {
		String fileName = app2DsFile(app);
		org.effortless.core.XmlHibernateUtil.removeDeletedEntities(new java.io.File(fileName));
		startDs(fileName);
	}

	public static void startDs (String fileName) {
//		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		String baseName = FilenameUtils.getBaseName(fileName);
		int lastIndexOf = baseName.lastIndexOf("-ds");
		baseName = (lastIndexOf != -1 ? baseName.substring(0, lastIndexOf) : baseName);
		
//		String prefix = "java:jboss/hibernate/"
//		String prefix = "java:/hibernate/"
		String prefix = "gy:/hibernate/";
		String suffix = "/SessionFactory";
		String jndi = prefix + baseName + suffix;

//		try {
//		javax.naming.InitialContext ictx = new javax.naming.InitialContext()
//		ictx.unbind(jndi)
//		} catch (Throwable t) {}

		java.io.File newFile = new java.io.File(fileName);

		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		try {
		configuration.configure(newFile);
		}
		catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		configuration.registerTypeOverride(new FileUserType(), new String[] {"java.io.File"});

		//configuration.getProperties()
		org.hibernate.service.ServiceRegistryBuilder serviceRegistryBuilder = new org.hibernate.service.ServiceRegistryBuilder().applySettings(configuration.getProperties());
		
		
		org.effortless.core.XmlHibernateUtil.decryptCfg(configuration);
		
		
//		try {
//			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("org.effortless.lotogest.Loteria");
//			Annotation[] annotations = clazz.getAnnotations();
//			for (Annotation ann : annotations) {
//				System.out.println(">>>>> type = " + ann.annotationType());
//			}
//			{
//				Field[] fields = clazz.getFields();
//				for (Field field : fields) {
//					System.out.println("############ field Name = " + field.getName() + " (BEGIN)");
//					Annotation[] fieldAnnotations = field.getAnnotations();
//					for (Annotation ann : fieldAnnotations) {
//						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> type = " + ann.annotationType());
//					}
//					System.out.println("############ field Name = " + field.getName() + " (END)");
//				}
//			}
//			System.out.println("-------------DECLARED FIELDS");
//			{
//				Field[] fields = clazz.getDeclaredFields();
//				for (Field field : fields) {
//					System.out.println("############ field Name = " + field.getName() + " (BEGIN)");
//					{
//						Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
//						for (Annotation ann : fieldAnnotations) {
//							System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> type = " + ann.annotationType());
//						}
//					}
//					{
//						Annotation[] fieldAnnotations = field.getAnnotations();
//						for (Annotation ann : fieldAnnotations) {
//							System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> type = " + ann.annotationType());
//						}
//					}
//					System.out.println("############ field Name = " + field.getName() + " (END)");
//				}
//			}
//			System.out.println("AAAAAAAAA");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		enableJndiWritable()
		try {
			throw new NullPointerException();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());

		registerListeners(sessionFactory);
		
		if (sessionFactory != null) {
			SessionManager.setMapJndi(jndi, sessionFactory);
		}
//		disableJndiWritable()
	}
	
	
	protected static void registerListeners(SessionFactory sessionFactory) {
//		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
//	    registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(null);
//	    registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(null);
	}

/*	
	static void enableJndiWritable () {
		org.jboss.msc.service.ServiceName[] array = [createGhostName()];
		org.jboss.as.naming.WritableServiceBasedNamingStore.pushOwner(createGhostService(), array);
	}

	public static org.jboss.msc.service.ServiceName createGhostName () {
		org.jboss.msc.service.ServiceName result = null;
		String[] array = ["jboss", "hibernate"];
		result = org.jboss.msc.service.ServiceName.of(array);
		return result;
	}
	
	public static org.jboss.msc.service.ServiceTarget createGhostService () {
		org.jboss.msc.service.ServiceTarget result = null;
		result = new org.jboss.msc.service.ServiceTarget() {

			public ServiceTarget addDependency(ServiceName name) {
				return null;
			}

			public ServiceTarget addDependency(ServiceName... names) {
				return null;
			}

			public ServiceTarget addDependency(Collection<ServiceName> names) {
				return null;
			}

			public ServiceTarget addListener(ServiceListener<Object> listener) {
				return null;
			}

			public ServiceTarget addListener(ServiceListener<Object>... listener) {
				return null;
			}

			public ServiceTarget addListener(
					Collection<ServiceListener<Object>> listeners) {
				return null;
			}

			public ServiceTarget addListener(Inheritance arg0,
					ServiceListener<Object> arg1) {
				return null;
			}

			public ServiceTarget addListener(Inheritance arg0,
					ServiceListener<Object>... arg1) {
				return null;
			}

			public ServiceTarget addListener(Inheritance arg0,
					Collection<ServiceListener<Object>> arg1) {
				return null;
			}

			public <T> ServiceBuilder<T> addService(ServiceName arg0,
					Service<T> arg1) {
				return null;
			}

			public <T> ServiceBuilder<T> addServiceValue(ServiceName arg0,
					Value<? extends Service<T>> arg1) {
				return null;
			}

			public BatchServiceTarget batchTarget() {
				return null;
			}

			public Set<ServiceName> getDependencies() {
				return null;
			}

			public Set<ServiceListener<Object>> getListeners() {
				return null;
			}

			public ServiceTarget removeDependency(ServiceName arg0) {
				return null;
			}

			public ServiceTarget removeListener(ServiceListener<Object> arg0) {
				return null;
			}

			public ServiceTarget subTarget() {
				return null;
			}
			
		};
		return result;
	}
	
	static void disableJndiWritable () {
		org.jboss.as.naming.WritableServiceBasedNamingStore.popOwner();
	}
*/

	public static void runAllDb () {
		try {
			
//			Map<String, List<String>> apps = org.effortless.util.XmlAppsUtil.discoverAppEntities(fileApps());
			Map<String, List<String>> apps = org.effortless.util.XmlAppsUtil.autoDiscoverAppEntities(ServerContext.getRootContext());
			Set<String> keys = apps.keySet();
			for (String app : keys) {
//				List<String> entities = apps.get(app);
//				persistDb(app, entities);
				runDb(app);
			}
		}
		catch (ValidityException e) {
			throw new ModelException(e);
		}
		catch (ParsingException e) {
			throw new ModelException(e);
		}
		catch (IOException e) {
			throw new ModelException(e);
		}
	}

}
