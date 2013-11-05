package org.effortless.security;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.effortless.core.GlobalContext;
import org.effortless.core.Hashes;

public class PropertiesSecuritySystem extends AbstractSecuritySystem {

	public PropertiesSecuritySystem() {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.securityFile = null;
	}
	
	public Object login (String loginName, String loginPassword) {
		Object result = null;
		result = loginFromProperties(loginName, loginPassword);
		return result;
	}
	
	protected PropertiesConfiguration securityFile;
	
	protected String loginFromProperties (String loginName, String loginPassword) {
		String result = null;
		
		if (loginName != null && loginPassword != null) {
			if (this.securityFile == null) {
				String appId = GlobalContext.get(GlobalContext.APP_ID, String.class);
				String rootCtx = GlobalContext.get(GlobalContext.ROOT_CONTEXT, String.class);
				String appUrl = rootCtx + File.separator + appId;
				String securityAddr = appUrl + File.separator + ".security.properties";
				
				try {
					this.securityFile = new PropertiesConfiguration(securityAddr);
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.securityFile.setEncoding("UTF-8");
				FileChangedReloadingStrategy reload = new FileChangedReloadingStrategy();
				this.securityFile.setReloadingStrategy(reload);
			}
			
			if (this.securityFile != null) {
				String defaultHash = this.securityFile.getString("cfg.default.hash");
				defaultHash = (defaultHash != null ? defaultHash.trim() : "");
				defaultHash = (defaultHash.length() > 0 ? defaultHash : "MD5");
				String hashPassword = Hashes.getInstance().digest(defaultHash, loginPassword);
				
				String storePassword = this.securityFile.getString("user." + loginName + ".pass");
				if (hashPassword != null && hashPassword.equals(storePassword)) {
					result = loginName;
				}
			}			
//			
//			if ("root".equals(loginName) && "pass".equals(loginPassword)) {
//				result = loginName;
//				
//				
//				
//			}
		}
		
		return result;
	}
	
}
