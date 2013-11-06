package org.effortless.gen.classes;

import org.codehaus.groovy.ast.ClassHelper;
import org.effortless.ann.HashPassword;
import org.effortless.core.GlobalContext;
import org.effortless.core.StringUtils;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.GMethod;
import org.effortless.gen.Transform;

/*
 * 
 * 
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
	}
	
	public Object login (String loginName, String loginPassword) {
		Object result = null;
		result = loginFromProperties(loginName, loginPassword);
		return result;
	}
	
 * 
 */
public class CreateSecuritySystemTransform extends Object implements Transform<GApplication> {

	public CreateSecuritySystemTransform () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	protected String getDefaultSecuritySystemClassName (GApplication app) {
		String result = null;
		String appId = app.getName();
		String appAlias = app.getAlias();
		result = appId + "." + StringUtils.capFirst(appAlias) + "SecuritySystem";
		return result;
	}
	
	@Override
	public void process(GApplication node) {
		if (node != null && node.getUserClass() != null) {
			GClass userClass = node.getUserClass();
			String className = getDefaultSecuritySystemClassName(node);
			GClass cg = node.newClass(className).setPublic(true).setSuperClass(org.effortless.security.AbstractSecuritySystem.class);
//			node.setUserClass(cg);
			
			String hashAlgorithm = null;
			try {
				hashAlgorithm = userClass.getField("password").getAnnotation(HashPassword.class).getValue();
			}
			catch (Throwable t) {
			}
			
			GMethod mg = null;

			mg = cg.addConstructor();
//			mg.add(mg.callConstructor(type)mg.callConstructor(cg), mg.cteSuper())

			//public Object login (String loginName, String loginPassword) {
			mg = cg.addMethod("login").setPublic(true).setReturnType(Object.class).addParameter(String.class, "loginName").addParameter(String.class, "loginPassword");
				//Object result = null;
				mg.declVariable(Object.class, "result");
				//result = loginFromProperties(loginName, loginPassword);
								
//				org.effortless.model.SimpleFilter;public SimpleFilter (Class<Type> type) {
				//org.effortless.model.SimpleFilter filter = new org.effortless.model.SimpleFilter(User.class);
				mg.declVariable(org.effortless.model.SimpleFilter.class, "filter", mg.callConstructor(org.effortless.model.SimpleFilter.class, mg.cteClass(userClass.getClassNode())));
//				filter.eq("login", loginName);
				mg.add(mg.call(mg.var("filter"), "eq", mg.cte("login"), mg.var("loginName")));
//				filter.eq("password", loginName);
				if (hashAlgorithm != null) {
//					filter.eq("password", org.effortless.core.Hashes.getInstance().digest(hashAlgorithm, loginPassword));			
					mg.add(mg.call(mg.var("filter"), "eq", mg.cte("password"), mg.call(mg.callStatic(org.effortless.core.Hashes.class, "getInstance"), "digest", mg.cte(hashAlgorithm), mg.var("loginPassword"))));
				}
				else {
					mg.add(mg.call(mg.var("filter"), "eq", mg.cte("password"), mg.var("loginPassword")));
				}
//				filter.eq("activo", Boolean.TRUE);
				mg.add(mg.call(mg.var("filter"), "eq", mg.cte("activo"), mg.cteTRUE()));
if (false) {
//				filter.eq("deleted", Boolean.FALSE);
				mg.add(mg.call(mg.var("filter"), "eq", mg.cte("deleted"), mg.cteFALSE()));
}
//				result = (filter.size() > 0 ? filter.get(0) : null);
				mg.add(mg.assign(mg.var("result"), mg.triple(mg.eq(mg.call(mg.var("filter"), "size"), mg.cte(Integer.valueOf(1))), mg.call(mg.var("filter"), "get", mg.cte(Integer.valueOf(0))), mg.cteNull())));
				
				//return result;
				mg.addReturn("result");

			if (node.getSettingsClass() != null) {
				//public void setupSession (Object user) {
				//}
				mg = cg.addMethod("setupSession").setPublic(true).addParameter(Object.class, "user").setReturnType(ClassHelper.VOID_TYPE);

if (false) {
				//org.effortless.model.SimpleFilter filter = new org.effortless.model.SimpleFilter(Settings.class);
				mg.declVariable(org.effortless.model.SimpleFilter.class, "filter", mg.callConstructor(org.effortless.model.SimpleFilter.class, mg.cteClass(node.getSettingsClass().getClassNode())));
if (false) {
//				filter.eq("deleted", Boolean.FALSE);
				mg.add(mg.call(mg.var("filter"), "eq", mg.cte("deleted"), mg.cteFALSE()));
}
//				Settings cfg = (Settings)(filter.size() > 0 ? filter.get(0) : null);
				mg.declVariable(node.getSettingsClass(), "cfg", mg.cast(node.getSettingsClass().getClassNode(), mg.triple(mg.gt(mg.call(mg.var("filter"), "size"), mg.cte(Integer.valueOf(0))), mg.call(mg.var("filter"), "get", mg.cte(Integer.valueOf(0))), mg.cteNull())));
}
else {
				//Settings cfg = Settings.getCurrent();
				mg.declVariable(node.getSettingsClass(), "cfg", mg.callStatic(node.getSettingsClass().getClassNode(), "getCurrent"));
}
//				Integer defaultPageSize = (cfg != null ? cfg.getDefaultPageSize() : null);
				String defaultPageSizePropertyName = "defaultPageSize";
				String defaultPageSizeGetterName = "get" + StringUtils.capFirst(defaultPageSizePropertyName);
				mg.declVariable(Integer.class, defaultPageSizePropertyName, mg.triple(mg.notNull(mg.var("cfg")), mg.call(mg.var("cfg"), defaultPageSizeGetterName), mg.cteNull()));
				
				//GlobalContext.set(GlobalContext.DEFAULT_PAGE_SIZE, Integer.valueOf(4));
				mg.add(mg.callStatic(GlobalContext.class, "set", mg.cte(GlobalContext.DEFAULT_PAGE_SIZE), mg.var(defaultPageSizePropertyName)));
			}
		}
	}

}
