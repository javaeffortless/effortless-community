package org.effortless.core;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.effortless.server.ServerContext;

public class I18nProperties extends Object implements Map<String, Object> {

	protected I18nProperties () {
		super();
		initiate();
	}

	protected void initiate () {
		this.composite = null;
		this.i18n = null;
	}

	public static I18nProperties dev (String root) {
		return I18nProperties.dev(root, null);
	}
	
	public static I18nProperties dev (String root, Locale locale) {
		return I18nProperties.create(root, locale, true, 5000L);
	}
	
	public static I18nProperties production (String root) {
		return I18nProperties.production(root, null);
	}
	
	public static I18nProperties production (String root, Locale locale) {
		return I18nProperties.create(root, locale, false, 0L);
	}
	
	
	public static I18nProperties create (String root, Locale locale, boolean autoSave, long refreshDelay) {
		I18nProperties result = null;
		result = new I18nProperties();
		result.build(root, locale, autoSave, refreshDelay);
		return result;
	}
	
	protected CompositeConfiguration composite;
	protected PropertiesConfiguration i18n;
	
	protected void build (String root, Locale locale, boolean autoSave, long refreshDelay) {
		String lang = (locale != null ? locale.getLanguage() : null);
		build(root, lang, autoSave, refreshDelay);
	}
	
	protected void build (String root, String lang, boolean autoSave, long refreshDelay) {
		lang = (lang != null ? lang.trim() : "");
		String suffix = (lang.length() > 0 ? "_" + lang : "");
		
		String appContext = ServerContext.getRootContext() + File.separator + root;//MySession.getRootContext();//AppContext();
		
		
		String i18nAddr = appContext + File.separator + "i18n" + suffix + ".properties";
		File i18nFile = new File(i18nAddr);
		if (!i18nFile.exists()) {
			try {
				i18nFile.createNewFile();
			} catch (IOException e) {
				throw new ModelException(e);
			}
		}

		this.composite = new CompositeConfiguration();
		try {
			this.i18n = new PropertiesConfiguration(i18nAddr);
			this.i18n.setEncoding("UTF-8");
		} catch (ConfigurationException e) {
			throw new ModelException(e);
		}
		this.i18n.setAutoSave(autoSave);
//		this.i18n.setFile(i18nFile);
		if (refreshDelay > 0L) {
			FileChangedReloadingStrategy reloading = new FileChangedReloadingStrategy();
			reloading.setRefreshDelay(refreshDelay);
			this.i18n.setReloadingStrategy(reloading);
		}
		this.composite.addConfiguration(this.i18n);
		
		try {
			String vocabularyName = "vocabulary" + suffix + ".properties";
			URL urlVocabulary = I18nProperties.class.getResource(vocabularyName);
			if (urlVocabulary != null) {
				PropertiesConfiguration vocabulary = new PropertiesConfiguration(urlVocabulary);
				vocabulary.setEncoding("UTF-8");
				this.composite.addConfiguration(vocabulary);
			}
		}
		catch (Throwable t) {
//			throw new ModelException(t);
		}
	}
	
	@Override
	public void clear() {
	}

	@Override
	public boolean containsKey(Object key) {
		return this.composite.containsKey((String)key);
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return null;
	}

	@Override
	public Object get(Object key) {
		return getKey((String)key, true);
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
	}

	@Override
	public Object remove(Object key) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<Object> values() {
		return null;
	}
	
	public String resolve (String key, Object[] params) {
		String result = null;
		if (key != null) {
			result = this.composite.getString(key);
			if (params != null && result != null) {
				for (int i = 0; i < params.length; i++) {
					Object param = params[i];
					String paramText = (param != null ? param.toString() : "");
					result = result.replaceAll("{" + i + "}", paramText);
				}
			}
		}
		return result;
	}
	
	public String getKey (String key, boolean save) {
		String result = null;
		result = this.composite.getString(key, null);
		if (result == null && save) {
			String value = translate(key);
			this.i18n.addProperty(key, value);
			result = value;
		}
//		if (result != null) {
//			try {
//				result = new String(result.getBytes("UTF-8"));//
//			} catch (UnsupportedEncodingException e) {
//				throw new ModelException(e);
//			}
//		}
		return result;
	}

	protected String translate(String key) {
		return I18nTranslator.translate(key);
	}
	
}
