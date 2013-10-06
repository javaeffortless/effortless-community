package org.effortless.tests.model;

import java.io.Serializable;
import java.util.Iterator;

import org.effortless.model.Entity;
import org.hibernate.MappingException;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.mapping.Collection;
import org.hibernate.persister.collection.OneToManyPersister;

public class ExclusiveCollectionPersister extends OneToManyPersister {

	public ExclusiveCollectionPersister(Collection collection,
			CollectionRegionAccessStrategy cacheAccessStrategy,
			Configuration cfg, SessionFactoryImplementor factory)
			throws MappingException, CacheException {
		super(collection, cacheAccessStrategy, cfg, factory);
	}

	
	public void insertRows(PersistentCollection collection, Serializable key, SessionImplementor session) {
		Iterator<Entity> iterator = (Iterator<Entity>) collection.entries(this);
		if (iterator != null) {
			while (iterator.hasNext()) {
				Entity entity = (Entity)iterator.next();
				entity.persist();
			}
		}
		super.insertRows(collection, key, session);
	}

	public void updateRows(PersistentCollection collection, Serializable key, SessionImplementor session) {
		Iterator<Entity> iterator = (Iterator<Entity>) collection.entries(this);
		if (iterator != null) {
			while (iterator.hasNext()) {
				Entity entity = (Entity)iterator.next();
				entity.persist();
			}
		}
		super.updateRows(collection, key, session);
	}
	
	public void postInstantiate() {
		super.postInstantiate();
	}

	protected int	doUpdateRows(Serializable key, PersistentCollection collection, SessionImplementor session) {
		System.out.println("HOLA");
		return super.doUpdateRows(key, collection, session);
	}
	
	protected String generateInsertRowString() {
		return null;
	}
	
}
