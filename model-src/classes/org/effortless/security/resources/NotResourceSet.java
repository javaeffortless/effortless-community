package org.effortless.security.resources;

import org.effortless.security.Resource;

public class NotResourceSet extends AbstractResourceSet implements ResourceSet {

	public NotResourceSet () {
		super();
	}
	
	@Override
	public boolean contains(Resource resource) {
		// TODO Auto-generated method stub
//		<#if (data?has_content && data?children?size > 0)>
//		<#list data?children as element>
//			<#if (element?node_type = "element")>
//				<#import element?node_name?trim?lower_case + ".ftl" as lib>
//				<#local result = !lib.fCheckBelongs2Resource(infoSecurity, element, extra, cfg)>
//				<#break>
//			</#if>
//		</#list>
//	</#if>
		return false;
	}

}
