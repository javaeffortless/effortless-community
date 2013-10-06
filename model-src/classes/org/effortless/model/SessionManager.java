package org.effortless.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

import org.effortless.core.ExecutionTime;
import org.effortless.core.ModelException;
import org.effortless.core.GlobalContext;
import org.effortless.core.StringUtils;
import org.effortless.core.Transactions;
import org.effortless.security.SecuritySystem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionManager {
	
	protected static final String KEY_SESSIONS = "sessions";
	protected static final String KEY_TRANSACTIONS = "transactions";
	protected static final String KEY_SESSION_FACTORIES = "sessionFactories";
	
	public static Session closeSession (String dbId) {
		Session result = null;
		Map<String, Session> cache = GlobalContext.getCache(Session.class, KEY_SESSIONS);
		result = cache.remove(dbId);
		if (result != null) {
			result.clear();
			result.close();
		}
		return result;
	}
	
	
	public static Session closeSession (Class<?> clazz) {
		Session result = null;
		String dbId = getDbId(clazz);
		result = closeSession(dbId);
		return result;
	}
	
	
	public static Session loadSession (Class<?> clazz) {
		Session result = null;
		
		Map<String, Session> cache = GlobalContext.getCache(Session.class, KEY_SESSIONS);
		String dbId = getDbId(clazz);
		result = cache.get(dbId);
		
		if (result == null) {
			SessionFactory sessionFactory = loadSessionFactory(clazz);
			result = sessionFactory.openSession();
			cache.put(dbId, result);
		}
		
		return result;
	}
	
	//prefix.company_author.app_name
	public static String getDbId (Class clazz) {
		String result = null;
		
		String className = clazz.getName();
		result = getDbId(className);
		
		return result;
	}

	//prefix.company_author.app_name
	//org.effortless.samples.Entity length>3
	//org.samples.Entity length=3
	//samples.Entity length=2
	public static String getDbId (String className) {
		String result = null;
		
		if (false) {
			String[] parts = className.split("\\.");
			String dbId = parts[parts.length - 1];
			dbId = (parts.length > 1 ? parts[parts.length - 2] : dbId);
			dbId = (parts.length >= 3 ? parts[2] : dbId);
			
			result = dbId;
		}
		else if (className != null) {
			String[] parts = className.split("\\.");
			int size = (parts.length > 3 ? Math.min(3, parts.length - 1) : parts.length);
			if (size > 0) {
				result = "";
				for (int i = 0; i < size; i++) {
					String part = parts[i];
					if (part != null && part.equals(StringUtils.uncapFirst(part))) {
						result += (result.length() > 0 ? "." : "") + parts[i];
					}
					else {
						break;
					}
				}
			}
		}
		
		return result;
	}

	protected static String loadJndiSessionFactory (Class clazz) {
		String result = null;

		String dbId = getDbId(clazz);
		
//		String prefix = "java:jboss/hibernate/"
//		String prefix = "java:/hibernate/"
		String prefix = "gy:/hibernate/";
		String suffix = "/SessionFactory";
		result = prefix + dbId + suffix;
		//println "&&&&&&&&&&&&&&&& "+ result
		return result;
	}

	protected static Map<String, Object> jndi;
		
	public static SessionFactory loadSessionFactory (Class clazz) {
		SessionFactory result = null;
		String jndi = loadJndiSessionFactory(clazz);
		//println "jndi =" +jndi
		javax.naming.InitialContext ictx = null;
		try {
			ictx = new javax.naming.InitialContext();
			result = (SessionFactory)ictx.lookup(jndi);
		}
		catch (NoInitialContextException e) {
			SessionManager.jndi = (SessionManager.jndi != null ? SessionManager.jndi : new HashMap<String, Object>());
			result = (SessionFactory)SessionManager.jndi.get(jndi);
//			System.setProperty("java.naming.provider.url", "localhost:1099");
//			System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
//			System.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
			
//			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
//			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
//			System.setProperty(Context.PROVIDER_URL, "file:///tmp");
//			ictx = new javax.naming.InitialContext();
//			result = (SessionFactory)ictx.lookup(jndi);
		} catch (NamingException e) {
			throw new ModelException(e);
		}
		return result;
	}
	
	public static void setMapJndi (String key, Object value) {
		SessionManager.jndi = (SessionManager.jndi != null ? SessionManager.jndi : new HashMap<String, Object>());
		SessionManager.jndi.put(key, value);
	}
	
	public static Session loadSession (Object data) {
		Session result = null;
		result = (data != null ? loadSession(data.getClass()) : null);
		return result;
	}

//	public static Object batch (Session session, CodeRunnable code) {
//		Object result = null;
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//			session.setFlushMode(FlushMode.COMMIT);
//			//do some work
//			result = code.run();
////			session.persist(data)
//
//			tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//			throw new ModelException(e);
//		}
////		finally {
////			session.close();
////		}
//		return result;
//	}
//	
//	
//	public static void persist (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.saveOrUpdate(data);
//				return null;
//			}
//			
//		});
//	}
//	
//	public static void delete (final Object data) {
//		if (data instanceof org.effortless.model.MarkDeleted) {
//			org.effortless.model.MarkDeleted markDeleted = (org.effortless.model.MarkDeleted)data;
//			Boolean deleted = markDeleted.getDeleted();
//			if (deleted == null || deleted.booleanValue() == false) {
//				markDeleted.setDeleted(Boolean.TRUE);
//				final Session session = loadSession(data);
//				batch(session, new CodeRunnable () {
//
//					public Object run() throws Exception {
//						session.saveOrUpdate(data);
//						return null;
//					}
//
//				});
//			}
//		}
//		else {
//			erase(data);
//		}
//	}
//	
//	public static void erase (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.delete(data);
//				return null;
//			}
//			
//		});
//	}
//	
//	public static void insert (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.save(data);
//				return null;
//			}
//			
//		});
//	}
//	
//	public static void create (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.save(data);
//				return null;
//			}
//			
//		});
//	}
//	
//	public static void update (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.update(data);
//				return null;
//			}
//
//		});
//	}
//
//	public static void refresh (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.refresh(data);
//				return null;
//			}
//			
//		});
//	}
//	
////	public void refreshDetached(Object data, Serializable id)
////	{
////		T attached = (T) session.load(getPersistentClass(), id);
////		if (attached != data)
////		{
////			session.evict(attached);
////			session.lock(data, LockMode.NONE);
////		}
////		session.refresh(data);
////	}
//	
//	
//	public static void merge (final Object data) {
//		final Session session = loadSession(data);
//		batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				session.merge(data);
//				return null;
//			}
//			
//		});
//	}
//	
//	public static Object load (final Class clazz, final Serializable id) {
//		Object result = null;
//		final Session session = loadSession(clazz);
//		result = batch(session, new CodeRunnable () {
//
//			public Object run() throws Exception {
//				return session.load(clazz, id);
//			}
//			
//		});
//		return result;
//	}
//	
//	//List<Type> list = MySession.listBy(Type.class, ['name':'John','age':10])
//	public static <Type> List<Type> listBy (Class<Type> clazz, Map params) {
//		List<Type> result = null;
//
////		Session session = loadSession(clazz)
////		Query queryHql = session.createQuery(query)
////		queryHql.setProperties(params)
//		
//		return result;
//	}
//	
//	public static Number countBy (Class clazz, Map params) {
//		return null;
////		return (Number) session.createCriteria("Book").setProjection(Projections.rowCount()).uniqueResult();
//	}
//
//	public static <Type> Type findBy (Class<Type> clazz, Map params) {
//		return null;
//	}
//
//	public static String i18n(String key, Object[] objects) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//		
//	public static String getLanguage () {
//		return null;
//	}
//	
//	public static Integer loadDefaultPageSize(Object data) {// throws ModelException {
//		Integer result = null;
//		result = Integer.valueOf(25);
//		return result;
//	}

//	public static org.effortless.model.Entity<?> getLogAuthor() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static String getLogLocationKeyFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getLogLocationAliasFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getLogLocationDescriptionFrom() {
		// TODO Auto-generated method stub
		return null;
	}

//	public static <T extends User<T>> T getCurrentUser (Class<T> clazz) {
//		T result = null;
//
//		return result;
//	}

	

	public static String newTransaction (Object data) {
		String result = null;
		Transactions transactions = getTransactions(data);
		result = transactions.newTransaction();
		return result;
	}
	
	public static Transactions getTransactions (Object data) {
		Transactions result = null;
		Class<?> clazz = data.getClass();
		result = getTransactions(clazz);
		return result;
	}

	public static final String EXECUTION_TIME = "_execution_time";
	
	public static ExecutionTime getExecutionTime () {
		ExecutionTime result = null;
		result = GlobalContext.get(EXECUTION_TIME, ExecutionTime.class, null);
		if (result == null) {
			result = new ExecutionTime();
			GlobalContext.set(EXECUTION_TIME, result);
		}
		return result;
	}

	
	
	public static Transactions getTransactions (Class<?> clazz) {
		Transactions result = null;
		String dbId = getDbId(clazz);
		result = GlobalContext.get(dbId, Transactions.class, null);
		if (result == null) {
			Session session = SessionManager.loadSession(clazz);
			result = new Transactions(session);
			GlobalContext.set(dbId, result);
		}
		return result;
	}

	public static String beginTransaction (Object data) {
		String result = null;
		Transactions transactions = getTransactions(data);
		result = transactions.begin();
		return result;
	}
	
	public static void endTransaction (Object data, String id) {
		Transactions transactions = getTransactions(data);
		transactions.end(id);
		if (id != null) {
			closeTransaction(data, transactions);
		}
	}
	
	protected static void closeTransaction (Object data, Transactions transactions) {
		if (false && transactions != null && data != null) {
			Class<?> clazz = data.getClass();
			String dbId = getDbId(clazz);
			GlobalContext.remove(dbId);
			closeSession(clazz);
		}
	}
	
	public static void rollbackTransaction (Object data, String id) {
		Transactions transactions = getTransactions(data);
		transactions.rollback(id);
		closeTransaction(data, transactions);
	}

	public static Long startExecutionTime () {
		Long result = null;
		ExecutionTime execTime = getExecutionTime();
		result = (execTime != null ? execTime.start() : result);
		return result;
	}
	
	public static Long stopExecutionTime () {
		Long result = null;
		ExecutionTime execTime = getExecutionTime();
		result = (execTime != null ? execTime.stop() : result);
		return result;
	}
	
	
	
	
	public static String newTransaction (Class<?> clazz) {
		String result = null;
		Transactions transactions = getTransactions(clazz);
		result = transactions.newTransaction();
		return result;
	}
	
	public static String beginTransaction (Class<?> clazz) {
		String result = null;
		Transactions transactions = getTransactions(clazz);
		result = transactions.begin();
		return result;
	}
	
	public static void endTransaction (Class<?> clazz, String id) {
		Transactions transactions = getTransactions(clazz);
		transactions.end(id);
	}
	
	public static void rollbackTransaction (Class<?> clazz, String id) {
		Transactions transactions = getTransactions(clazz);
		transactions.rollback(id);
	}

	
	
	
//	public static SecuritySystem getSecuritySystem() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static Object getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Map getSecurityRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Locale getUserLocale() {
		// TODO Auto-generated method stub
		return null;
	}


	public static SecuritySystem getSecuritySystem() {
		// TODO Auto-generated method stub
		return null;
	}


	public static Entity<?> getLogAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
