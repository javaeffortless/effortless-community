package org.effortless.security.resources;

public class ResourceSetFactory {

	
	public static ResourceSet depends (String depends) {
		return depends(depends, false);
	}
	
	public static ResourceSet depends (String depends, boolean excludeViews) {
		AdditionResourceSet result = null;
		result = new AdditionResourceSet();
		String[] array = (depends != null ? depends.trim().split(",") : null);
		if (array != null) {
			for (String item : array) {
				result.operation("read", item, excludeViews);
				result.readOperations(item);
			}
		}
		return result;
	}
	
	public static ResourceSet build_Module_Administrator (String module, String exceptions, String depends) {
		AdditionResourceSet result = null;
		result = new AdditionResourceSet();
		
		result.subtract();
			result.addition();
				result.includeDeleteInner(module);
				result.module(module, "delete,erase,deleteBind");
			result.end();
			String[] arrayExceptions = (exceptions != null ? exceptions.trim().split(",") : null);
			if (arrayExceptions != null) {
				result.addition();
				for (String exception : arrayExceptions) {
					result.operation(exception, module);
				}
				result.end();
			}
		result.end();
		
		result.menu(module);
		
		String[] arrayDepends = (depends != null ? depends.trim().split(",") : null);
		if (arrayDepends != null) {
			for (String depend : arrayDepends) {
				result.operation("read", depend);
				result.readOperations(depend);
			}
		}
		
		return result;
	}
	
	
	public static ResourceSet build_RES_ADMINISTRADOR_DE_USUARIOS_ACADEMICOS () {
//		return build_Module_Administrator("control_acceso", "imprimir.*,abrir,initLogin,forceLogout,getCurrent.*", "depends_profesorado,depends_personalnodocente,depends_cursos,depends_usuarios");
		AdditionResourceSet result = null;
		result = new AdditionResourceSet();
		
		result.subtract();
			result.addition();
				result.includeDeleteInner("control_acceso");
				result.module("control_acceso", "delete,erase,deleteBind");
			result.end();
			result.addition();
				result.operation("imprimir.*");
				result.operation("abrir");
				result.operation("initLogin", "control_acceso");
				result.operation("forceLogout", "control_acceso");
				result.operation("getCurrent.*", "control_acceso");
			result.end();
		result.end();
		
		result.menu("control_acceso");
		
		result.operation("read", "depends_profesorado");
		result.readOperations("depends_profesorado");

		result.operation("read", "depends_personalnodocente");
		result.readOperations("depends_personalnodocente");

		result.operation("read", "depends_cursos");
		result.readOperations("depends_cursos");

		result.operation("read", "depends_usuarios");
		result.readOperations("depends_usuarios");
		
		return result;
	}
	
/*
 * 
		<resource id="RES_ADMINISTRADOR_DE_USUARIOS_ACADEMICOS">
			<subtract>
				<addition>
					<includeDeleteInner module="control_acceso" />
					<module id="control_acceso" excludeOperations="delete,erase,deleteBind" />
				</addition>
				<addition>
					<operation id="imprimir.*" />
					<operation id="abrir" />
					<operation id="initLogin" module="control_acceso" />
					<operation id="forceLogout" module="control_acceso" />
					<operation id="getCurrent.*" module="control_acceso" />
				</addition>
 			</subtract>
		
			<menu module="control_acceso" />
			
			<operation id="read" module="depends_profesorado" />
			<readOperations module="depends_profesorado" />

			<operation id="read" module="depends_personalnodocente" />
			<readOperations module="depends_personalnodocente" />

			<operation id="read" module="depends_cursos" />
			<readOperations module="depends_cursos" />

			<operation id="read" module="depends_usuarios" />
			<readOperations module="depends_usuarios" />
<!-- 
			<operation id="read" module="autorun_cfg" />
-->
		</resource>
 * 
 * 	
 */
	
}
