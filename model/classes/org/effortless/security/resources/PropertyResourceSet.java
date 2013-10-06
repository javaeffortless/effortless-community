package org.effortless.security.resources;

import org.effortless.security.Resource;

public class PropertyResourceSet extends AbstractResourceSet implements ResourceSet {

	public PropertyResourceSet () {
		super();
	}
	
	@Override
	public boolean contains(Resource resource) {
//		<#if (data.@id[0]?? && data.@id?trim?length > 0)>
//		<#local resPropertyName = data.@id?trim?lower_case>
//		<#if (libSecurityResources.fCheckProperty(infoSecurity, data, extra, cfg))>
//			<#local propertyName = libSecurityResources.fGetPropertyName(infoSecurity, data, extra, cfg)?trim?lower_case>
//			<#if (resPropertyName = propertyName)>
//				<#if (data.@entity[0]?? && data.@entity?trim?length > 0)>
//					<#local entityName = libSecurityResources.fGetEntityName(infoSecurity, data, extra, cfg)?trim?lower_case>
//					<#local resEntityName = data.@entity?trim?lower_case>
//					<#local result = (entityName = resEntityName)>
//				<#elseif (data.@unit[0]?? && data.@unit?trim?length > 0)>
//					<#local unitName = libSecurityResources.fGetUnitName(infoSecurity, data, extra, cfg)?trim?lower_case>
//					<#local resUnitName = data.@unit?trim?lower_case>
//					<#local result = (unitName = resUnitName)>
//				<#elseif (data.@module[0]?? && data.@module?trim?length > 0)>
//					<#local moduleName = libSecurityResources.fGetModuleName(infoSecurity, data, extra, cfg)?trim?lower_case>
//					<#local resModuleName = data.@module?trim?lower_case>
//					<#local result = (moduleName = resModuleName)>
//				<#else>
//					<#local result = true>
//				</#if>
//				<#if (result && data.@operation[0]?? && data.@operation?trim?length > 0)>
//					<#local operationName = "">
//					<#if (infoSecurity["op"]?? && infoSecurity["op"]?trim?length > 0)>
//						<#local operationName = infoSecurity["op"]?trim>
//					</#if>
//					<#if (operationName?trim?length > 0)>
//						<#local array = data.@operation?trim?lower_case?split(",")>
//						<#if (operationName?trim?lower_case = "save")>
//							<#local contains = false>
//							<#if (!contains && array?seq_contains("create"?trim))><#local contains = true></#if>
//							<#if (!contains && array?seq_contains("update"?trim))><#local contains = true></#if>
//							<#if (!contains && array?seq_contains("persist"?trim))><#local contains = true></#if>
//							<#local result = contains>
//						<#elseif (operationName?trim?lower_case = "load")>
//							<#local contains = false>
//							<#if (!contains && array?seq_contains("read"?trim))><#local contains = true></#if>
//							<#local result = contains>
//						</#if>
//					</#if>
//				</#if>
//			</#if>
//		</#if>
//	</#if>
		return false;
	}

}
