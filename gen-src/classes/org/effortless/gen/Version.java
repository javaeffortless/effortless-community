package org.effortless.gen;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Version extends Object {

	protected Version () {
		super();
		initiate();
	}
	
	protected void initiate () {
		
	}
	
	protected static final String KEY_VERSION = "version".trim().toLowerCase();
	protected static final String KEY_BUILD_AUTHOR = "build.author".trim().toLowerCase();
	protected static final String KEY_BUILD_NUMBER = "build.number".trim().toLowerCase();
	protected static final String KEY_BUILD_DATE = "build.date".trim().toLowerCase();
	protected static final String KEY_ID = "id".trim().toLowerCase();
	
	protected static final String _FILE_ = "VERSION.MF";
	
	public static final String ID = getValue(KEY_ID);
	
	public static final String BUILD_NUMBER = getValue(KEY_BUILD_NUMBER);

	public static final String BUILD_AUTHOR = getValue(KEY_BUILD_AUTHOR);

	public static final String VERSION = getValue(KEY_VERSION);

	public static final Date BUILD_DATE = getDate(KEY_BUILD_DATE);
	
	protected static Date getDate (String key) {
		Date result = null;
		String value = getValue(key);
		if (value != null && !"".equals(value)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
			try {
				result = formatter.parse(value);
			} catch (ParseException e) {
			}
		}
		return result;
	}
	
	protected static String getValue (String key) {
		String result = null;
		try {
			if (properties == null) {
				InputStream input = Version.class.getResourceAsStream(_FILE_);
				
//				Properties properties = null;
				properties = new Properties();
				properties.load(input);
			}
			result = properties.getProperty(key);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected static Properties properties;
	
}
