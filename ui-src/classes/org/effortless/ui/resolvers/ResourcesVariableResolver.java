package org.effortless.ui.resolvers;

import java.io.File;

import org.effortless.server.ServerContext;
import org.effortless.server.WebUtils;
import org.zkoss.xel.XelContext;
import org.zkoss.xel.XelException;
import org.zkoss.xel.util.SimpleXelContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.xel.impl.ExecutionResolver;

public class ResourcesVariableResolver extends Object implements org.zkoss.xel.VariableResolverX {

	public static final String RESOURCES = "resources";
	public static final String IMAGES = "images";
	
	/**
	 * null -> not a recognized variable
	 */
	@Override
	public Object resolveVariable(XelContext ctx, Object base, Object name)	throws XelException {
		Object result = null;

		if (base == null && RESOURCES.equals(name)) {
			String appId = resolveAppId(ctx, base, name);
			result = File.separator + appId + File.separator + "resources";
		}
		else if (base == null && IMAGES.equals(name)) {
			String appId = resolveAppId(ctx, base, name);
			result = File.separator + appId + File.separator + "resources" + File.separator + "img";
		}
//		else {
//			String strName = null;
//			try {
//				strName = (String)name;
//			}
//			catch (ClassCastException e) {
//				
//			}
//			if (base == null && strName != null && strName.startsWith(VIEW_STR)) {
//				String appId = resolveAppId(ctx, base, name);
//				String option = strName.substring(VIEW_STR.length());
//				String prefixOption = appId + File.separator + "resources" + File.separator;
//				option = option.toLowerCase();
//				result = prefixOption + option.toLowerCase() + ".zul";
//			}
//		}
		
		return result;
	}

	protected static final String VIEW_STR = "view_";
	
	@Override
	public Object resolveVariable(String name) throws XelException {
		return null;
	}
	
	protected String resolveAppId (XelContext ctx, Object base, Object name)	throws XelException {
		String result = null;
		Component cmp = null;
		try {
			SimpleXelContext sctx = (SimpleXelContext)ctx;
			ExecutionResolver execResolver = (ExecutionResolver)sctx.getVariableResolver();
			cmp = (Component)execResolver.getSelf();
		}
		catch (ClassCastException e) {
		}
		String appName = null;
		if (cmp != null) {
			Desktop desktop = cmp.getDesktop();
			desktop = (desktop != null ? desktop : Executions.getCurrent().getDesktop());
			appName = desktop.getRequestPath();
		}
		if (appName != null) {
			result = WebUtils.appId(appName, ServerContext.getRootContext());
		}
		return result;
	}

}
