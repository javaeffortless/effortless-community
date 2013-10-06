package org.effortless.ui.resolvers;

import org.effortless.core.I18nProperties;
import org.effortless.server.ServerContext;
import org.effortless.server.WebUtils;
import org.zkoss.xel.XelContext;
import org.zkoss.xel.XelException;
import org.zkoss.xel.util.SimpleXelContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.xel.impl.ExecutionResolver;

public class I18nVariableResolver extends Object implements org.zkoss.xel.VariableResolverX {

	public static final String I18N = "i18n";
	
	/**
	 * null -> not a recognized variable
	 */
	@Override
	public Object resolveVariable(XelContext ctx, Object base, Object name)	throws XelException {
		Object result = null;
		
		if (base == null && I18N.equals(name)) {
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
				appName = (desktop != null ? desktop.getRequestPath() : null);
			}
			if (appName != null) {
				String appId = WebUtils.appId(appName, ServerContext.getRootContext());
				result = I18nProperties.dev(appId);
			}
		}
		
		return result;
	}

	@Override
	public Object resolveVariable(String name) throws XelException {
		return null;
	}

}
