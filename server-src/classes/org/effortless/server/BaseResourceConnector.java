package org.effortless.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;

import org.effortless.core.FilenameUtils;

import groovy.util.ResourceConnector;
import groovy.util.ResourceException;

public class BaseResourceConnector extends Object implements ResourceConnector {

	protected BaseResourceConnector () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateBase();
	}
	
	protected String base;
	
	protected void initiateBase () {
		this.base = null;
	}
	
	public String getBase () {
		return this.base;
	}
	
	public void setBase (String newValue) {
		this.base = newValue;
	}
	
	public URLConnection getResourceConnection(String name)	throws ResourceException {
		URLConnection result = null;
		
		try {
			String fullName = FilenameUtils.concat(this.base, name);
			File file = new File(fullName);
			result = file.toURI().toURL().openConnection();
		} catch (MalformedURLException e) {
			throw new ResourceException(e);
		} catch (IOException e) {
			throw new ResourceException(e);
		} catch (Throwable e) {
			throw new ResourceException(e);
		}
		
		return result;
	}

}
