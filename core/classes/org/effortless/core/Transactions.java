package org.effortless.core;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Transactions extends Object {

	public Transactions (Session session) {
		super();
		this.list = null;
		this.session = session;
	}
	
	protected Map<String, Transaction> list;
	
	protected Session session;
	
	protected String generateId () {
		String result = null;
		int size = (this.list != null ? list.size() : 0);
		result = String.valueOf(size);
		return result;
		//return java.util.UUID.randomUUID().toString();
	}
	
	public String newTransaction () {
		String result = null;
		
		Transaction tx = this.session.beginTransaction();
		result = generateId();
		this.list = (this.list != null ? this.list : new HashMap<String, Transaction>());
		this.list.put(result, tx);
		
		return result;
	}
	
	public String begin () {
		String result = null;
		String id = getCurrent();
		result = (id == null ? newTransaction() : null);
		return result;
	}
	
	public void end (String id) {
		Transaction tx = (id != null && this.list != null ? this.list.get(id) : null);
		if (tx != null) {
			tx.commit();
			this.session.flush();
//			this.session.close();
			this.list.remove(id);
		}
	}
	
	public void rollback (String id) {
		Transaction tx = (id != null && this.list != null ? this.list.get(id) : null);
		if (tx != null) {
			tx.rollback();
			this.session.flush();
//			this.session.close();
			this.list.remove(id);
		}
	}
	
	public void endAll () {
		int size = (this.list != null ? this.list.size() : 0);
		for (int i = size - 1; i >= 0; i--) {
			String id = String.valueOf(i);
			Transaction tx = this.list.get(id);
			tx.commit();
			this.list.remove(id);
		}
		this.list = null;
	}
	
	public String getCurrent () {
		String result = null;
		int size = (this.list != null ? this.list.size() : 0);
		result = (size > 0 ? String.valueOf(size) : null);
		return result;
	}
	
}
