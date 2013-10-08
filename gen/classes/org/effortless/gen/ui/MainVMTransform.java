package org.effortless.gen.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.CharSet;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.ann.Finder;
import org.effortless.core.ModelException;
import org.effortless.core.StringUtils;
import org.effortless.gen.AppTransform;
import org.effortless.gen.ClassGen;
import org.effortless.gen.GenContext;
import org.effortless.gen.MethodGen;
import org.effortless.gen.ViewClassGen;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.model.AbstractEntity;
import org.effortless.server.ServerContext;
import org.effortless.ui.resources.ImageResources;
import org.effortless.ui.vm.MainWindowVM;
import org.objectweb.asm.Opcodes;
import org.zkoss.bind.BindUtils;

/**
 *
 * Implements

	
 * 
 * 
 * @author jesus
 *
 */

public class MainVMTransform {

	public static final ClassNode HASH_CODE_BUILDER = new ClassNode(org.apache.commons.lang3.builder.HashCodeBuilder.class);
	
	public static String getFinderName (ClassNode clazz, SourceUnit sourceUnit) {
		String result = null;
		result = clazz.getName() + "FinderViewModel";
		return result;
	}

	/**
	 * 
	 * 
	 * 

@org.effortless.NoEntity
class MainWindowVM {

	protected String appName;

	public String getAppName () {
		return this.appName;
	}

	public void setAppName (String newValue) {
		this.appName = newValue;
	}

	protected String selectedModule;

	public String getSelectedModule () {
		return this.selectedModule;
	}

	public void setSelectedModule (String newValue) {
		this.selectedModule = newValue;
	}

	protected static final String MODULE_SORTEOS = "sorteos";

	public Boolean getCheckedSorteos () {
		return MODULE_SORTEOS.equals(this.selectedModule);
	}

	public void setCheckedSorteos (Boolean newValue) {
		selectModule(newValue, MODULE_SORTEOS);
	}

	public void selectModule (Boolean value, String module) {
		if (value != null && value.booleanValue() && module != null && module.equals(this.selectedModule)) {
			this.selectedModule = module;
        	org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "checked" + StringUtils.capFirst(module));		
		}
	}

	protected String content;

	public String getContent () {
		return this.content;
	}

	public void setContent (String newValue) {
		this.content = newValue;
	}

	protected String footerMsg;

	public String getFooterMsg () {
		return this.footerMsg;
	}

	public void setFooterMsg (String newValue) {
		this.footerMsg = newValue;
	}

	@org.zkoss.bind.annotation.NotifyChange("content")
	public void openBasicFinder () {
		setContent("basic_finder.zul");
	}

}

	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void createMainVM (String appId, SourceUnit sourceUnit) {
		if (true && appId != null) {
			
//			if (false) {
//				ClassGen cg = new ClassGen("MainWindowVM", sourceUnit).addAnnotation(org.effortless.NoEntity.class);
//				cg.addSimpleProperty(String.class, "appName");
//				cg.addSimpleProperty(String.class, "selectedModule");
//				cg.addSimpleProperty(String.class, "content");
//				cg.addSimpleProperty(String.class, "footerMsg");
//				
//				List<String> modules = getModules(appId);
//				for (String module : modules) {
//					String cte = "MODULE_" + module.toUpperCase();
//					cg.addCte(ClassHelper.STRING_TYPE, cte, module);
//
//					String getterName = "getChecked" + StringUtils.capFirst(module);
//					MethodGen getter = cg.addMethod(getterName).setPublic(true).setReturnType(Boolean.class);
//					getter.add("Boolean result = null");
//					getter.add("result = this." + cte + ".equals(this.selectedModule)");
//					getter.add("return result");
////					getter.newVariable(Boolean.class, "result");
////					getter.assign("result", "this." + cte + ".equals", "this.selectedModule");
////					getter.addReturn("result");
//
//					String setterName = "setChecked" + StringUtils.capFirst(module);
//					MethodGen setter = cg.addMethod(setterName).setPublic(true).addParameter(Boolean.class, "newValue");
//					setter.add("this.selectModule(newValue, this." + cte);
////					setter.run("this.selectModule", "newValue", "this." + cte);
//					
//					List<String> options = getOpciones(appId, module);
//					for (String option : options) {
//						String finderName = StringUtils.capFirst(option) + "Finder";
//						String finderUrl = option + "_finder.zul";
//						MethodGen mg = cg.addMethod("open" + finderName).setPublic(true).addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, "content");
//						mg.add("this.setContent(\"" + finderUrl + "\")");
////						mg.run("this.setContent", "#" + finderUrl);
//					}
//				}
//
//				
//				MethodGen select = cg.addMethod("selectModule").setProtected(true).addParameter(Boolean.class, "value").addParameter(String.class, "module");
//				String selectCode = "if (value != null && value.booleanValue() && module != null && module.equals(this.selectedModule)) {";
//				selectCode += "this.selectedModule = module";
//				for (String module : modules) {
//					selectCode += "org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, \"checked" + StringUtils.capFirst(module) + "\")";
//				}
//				selectCode += "}";
//				select.add(selectCode);
////		        BindUtils.postNotifyChange(null, null, this, "checked" + StringUtils.capFirst(module));		
//			}
//			else {
			if (true) {
				if (true) {
				String className = getMainVM(appId);
				ViewClassGen cg = new ViewClassGen(className, sourceUnit);
				cg.addMainVM(appId);
				}
			}
			else {
				String className = appId + "." + "MainWindowVM";
				ClassNode vm = new ClassNode(className, Opcodes.ACC_PUBLIC, ClassHelper.OBJECT_TYPE);
				sourceUnit.getAST().addClass(vm);
				
				
				FinderVMTransform.addSimpleProperty(vm, null, sourceUnit, "appName", ClassHelper.STRING_TYPE, false, false);
				FinderVMTransform.addSimpleProperty(vm, null, sourceUnit, "selectedModule", ClassHelper.STRING_TYPE, false, false);
				FinderVMTransform.addSimpleProperty(vm, null, sourceUnit, "content", ClassHelper.STRING_TYPE, false, false);
				FinderVMTransform.addSimpleProperty(vm, null, sourceUnit, "footerMsg", ClassHelper.STRING_TYPE, false, false);
			}
//			}
		}
	}

	public static String getMainVM (String appId) {
//		return appId + "." + "MainWindowVM";
		return MainWindowVM.class.getName();//"org.effortless.ui.MainWindowVM";
	}
	
	/**
	 * 
	 * 
	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange("list")
	public void search(){
System.out.println("SEARCH")
		this.list = Basic.listBy().lk("name", this.keyword);
	}
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addSearchButton(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit) {
//		BlockStatement code = new BlockStatement();
		String op = "lk";
		List<AnnotationNode> annotations = clazz.getAnnotations(new ClassNode(Finder.class));
		if (annotations != null && annotations.size() > 0) {
			AnnotationNode annFinder = annotations.get(0);
			op = annFinder.getMember("op").getText();
		}
		
		FieldNode fieldKeyword = vm.getField("keyword");
		StaticMethodCallExpression callListBy = new StaticMethodCallExpression(clazz, "listBy", ArgumentListExpression.EMPTY_ARGUMENTS);
		ArgumentListExpression listExpr = new ArgumentListExpression(new Expression[] {new ConstantExpression("name"), new FieldExpression(fieldKeyword)});
		MethodCallExpression callLk = new MethodCallExpression(callListBy, op, listExpr);
		FieldNode fieldList = vm.getField("list");
		BinaryExpression assignList = new BinaryExpression(new FieldExpression(fieldList), Token.newSymbol(Types.ASSIGN, -1, -1), callLk);
		ExpressionStatement code = new ExpressionStatement(assignList);
		
		MethodNode method = new MethodNode("search", Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		vm.addMethod(method);

		//@org.zkoss.bind.annotation.Command
		AnnotationNode annCommand = new AnnotationNode(new ClassNode(org.zkoss.bind.annotation.Command.class));
		method.addAnnotation(annCommand);

		//@org.zkoss.bind.annotation.NotifyChange("list")
		AnnotationNode annNotifyChange = new AnnotationNode(new ClassNode(org.zkoss.bind.annotation.NotifyChange.class));
		annNotifyChange.setMember("value", new ConstantExpression("list"));
		method.addAnnotation(annNotifyChange);
	}

	/**
	 * 
	 * 
	 * 

<zk>
  <toolbar align="start">
    <hlayout spacing="10px">
      <label value="@load(vm.footerMsg)" />
    </hlayout>
  </toolbar>
</zk>

	 * 
	 * 
	 */
	public static void writeBottomZul (String appId) {
		List<String> zul = new ArrayList<String>();
		zul.add("<zk>");
		writeBottomZul(zul, appId);
		zul.add("</zk>");
		
		String filename = ServerContext.getRootContext() + appId + "/resources/main/main_bottom.zul";
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
		
	}

	public static void writeBottomZul (List<String> zul, String appId) {
		zul.add("  <toolbar height=\"32px\" align=\"start\" style=\"border-top: 1px #CCCCCC solid;\">");
		zul.add("    <hlayout spacing=\"10px\">");
		zul.add("      <label value=\"@load(vm." + "footerMsg" + ")\" />");
		zul.add("    </hlayout>");
		zul.add("  </toolbar>");
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 

<zk>
  <listbox visible="@load(vm.checkedSorteos)" fulFill="onSelectSorteos">
    <listitem onClick="@command('openBasicFinder')"><listcell image="/img/Centigrade-Widget-Icons/ArrowUpOrange-16x16.png" label="ZK Jet 0.8.0 is released" /></listitem>
    <listitem><listcell image="/img/Centigrade-Widget-Icons/EnvelopeOpen-16x16.png" label="Open" /></listitem>
    <listitem><listcell image="/img/Centigrade-Widget-Icons/PaperClip-16x16.png" label="Paper" /></listitem>
  </listbox>
  <listbox visible="@load(vm.checkedConfiguracion)">
    <listitem><listcell image="/img/Centigrade-Widget-Icons/ArrowUpOrange-16x16.png" label="ZK Jet 0.8.0 is released" /></listitem>
    <listitem><listcell image="/img/Centigrade-Widget-Icons/EnvelopeOpen-16x16.png" label="Open" /></listitem>
    <listitem><listcell image="/img/Centigrade-Widget-Icons/PaperClip-16x16.png" label="Paper" /></listitem>
  </listbox>
</zk>

	 * 
	 * 
	 */
	public static void writeLeftZul (String appId) {
		List<String> zul = new ArrayList<String>();
		
		zul.add("<zk>");
		writeLeftZul(zul, appId);
		zul.add("</zk>");
		
		String filename = ServerContext.getRootContext() + appId + "/resources/main/main_left.zul";
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}
	

	public static void writeLeftZul (List<String> zul, String appId) {
//		zul.add("  <listbox visible=\"@load(vm.checked" + "Sorteos" + ")\" fulFill=\"onSelect" + "Sorteos" + "\">");
//		zul.add("    <listitem onClick=\"@command('openBasicFinder')\">");
//		zul.add("      <listcell image=\"/img/Centigrade-Widget-Icons/ArrowUpOrange-16x16.png\" label=\"ZK Jet 0.8.0 is released\" />");
//		zul.add("    </listitem>");
//		zul.add("    <listitem><listcell image=\"/img/Centigrade-Widget-Icons/EnvelopeOpen-16x16.png\" label=\"Open\" /></listitem>");
//		zul.add("    <listitem><listcell image=\"/img/Centigrade-Widget-Icons/PaperClip-16x16.png\" label=\"Paper\" /></listitem>");
//		zul.add("  </listbox>");
//		
//		zul.add("  <listbox visible=\"@load(vm.checkedConfiguracion)\">");
//		zul.add("    <listitem><listcell image=\"/img/Centigrade-Widget-Icons/ArrowUpOrange-16x16.png\" label=\"ZK Jet 0.8.0 is released\" /></listitem>");
//		zul.add("    <listitem><listcell image=\"/img/Centigrade-Widget-Icons/EnvelopeOpen-16x16.png\" label=\"Open\" /></listitem>");
//		zul.add("    <listitem><listcell image=\"/img/Centigrade-Widget-Icons/PaperClip-16x16.png\" label=\"Paper\" /></listitem>");
//		zul.add("  </listbox>");

		List<String> modules = getModules(appId);
		String prefixOption = appId + File.separator + "resources" + File.separator;
		for (String module : modules) {
			module = StringUtils.uncapFirst(module);
			String capModule = StringUtils.capFirst(module);
//			zul.add("  <listbox visible=\"@load(vm.checked" + capModule + ")\" fulfill=\"onSelect" + capModule + "\">");
//			zul.add("  <listbox visible=\"@load(vm.selectedModule eq '" + module + "' ? true : false)\" fulfill=\"onSelect" + capModule + "\">");
			
			if (true) {
				zul.add("  <menu-bar visible=\"@load(vm.selectedModule eq '" + module + "' ? true : false)\" fulfill=\"mod_" + module + ".onClick\">");
				List<String> options = getOpciones(appId, module);
				for (String option : options) {
					String lowerOption = StringUtils.uncapFirst(option);
					String img = "main/option_" + lowerOption + ".png";
					ImageResources.copy(appId, lowerOption, img, 24);
					zul.add("	<menu-item link=\"" + lowerOption + "_finder\" />");
				}
				zul.add("  </menu-bar>");
			}
			else {
				zul.add("  <listbox visible=\"@load(vm.selectedModule eq '" + module + "' ? true : false)\" fulfill=\"mod_" + module + ".onClick\">");
				List<String> options = getOpciones(appId, module);
				for (String option : options) {
					String lowerOption = StringUtils.uncapFirst(option);
					String img = "main/option_" + lowerOption + ".png";
					ImageResources.copy(appId, lowerOption, img, 24);
	//				zul.add("    <listitem onClick=\"@command('open" + StringUtils.capFirst(option) + "')\">");
	//				zul.add("    <listitem onClick=\"@command('openOption', option='${view_" + lowerOption + "_finder}')\">");
	//				zul.add("    <listitem onClick=\"@command('openOption', option=${view_" + lowerOption + "_finder})\">");
	//				zul.add("    <listitem onClick=\"@command('openOption', option='" + lowerOption + "_finder', cmp=self)\">");
					zul.add("    <listitem onClick=\"@command('openOption', option='" + lowerOption + "_finder')\">");
	//				zul.add("    <listitem onClick=\"@command('openOption', option='" + prefixOption + "" + lowerOption + "_finder')\">");
					zul.add("      <listcell image=\"${images}/" + img + "\" label=\"${i18n." + option + "_optionLabel}\" tooltiptext=\"${i18n." + option + "_optionTooltip}\" />");
					zul.add("    </listitem>");
				}
				zul.add("  </listbox>");
			}
		}
	}
	
	
	
	
	public static List<String> getOpciones (String app, String module) {
		List<String> result = null;
		AppTransform appTransform = GenContext.getAppTransform(app, false);
		if (appTransform != null) {
//			resulappTransform.getClassesByUnit(module);
			result = appTransform.getEntitiesByUnit(module);
		}
//		result = new ArrayList<String>();
//		result.add("basic");
		return result;
	}
	
	
	/**
	 * 
<zk>
  <style>
.vlayout img {
  display: block;
  margin: 0 auto;
  margin-bottom: 4px;
}

.vlayout .z-toolbarbutton-cnt {
  text-align: center;
  color: #1C5178;
  font-size: 10px;
  font-family: arial, sans-serif;
  font-weight: normal;
}
  </style>
  <toolbar align="start">
    <hlayout spacing="10px">
      <image src="${resources}/main/main_logo.png" />
      <toolbarbutton checked="@bind(vm.checkedSorteos)" sclass="vlayout" label="Sorteos" tooltiptext="Descripción" mode="toggle" image="${images}/main/module_sorteos.png" />
      <toolbarbutton checked="@bind(vm.checkedConfiguracion)" sclass="vlayout" label="Configuración" tooltiptext="Descripción" mode="toggle" image="${images}/main/module_configuracion.png" />
    </hlayout>
  </toolbar>
</zk>
	 * 
	 * 
	 */
	public static void writeTopZul (String appId) {
		List<String> zul = new ArrayList<String>();
		
		zul.add("<zk>");
		writeTopZul(zul, appId);
		zul.add("</zk>");
		
		String filename = ServerContext.getRootContext() + appId + "/resources/main/main_top.zul";
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}
	
	public static List<String> getModules (String appId) {
		List<String> result = null;
		if (appId != null) {
			AppTransform appTransform = GenContext.getAppTransform(appId, false);
			if (appTransform != null) {
				result = appTransform.getUnitNames();
			}
//			else {
//				result = new ArrayList<String>();
//				result.add("sorteos");
//				result.add("configuracion");
//			}
		}
		return result;
	}

	
	public static void writeTopZul (List<String> zul, String appId) {
		if (false) {
		zul.add("  <style>");
		zul.add(".vlayout img {");
		zul.add("  display: block;");
		zul.add("  margin: 0 auto;");
		zul.add("  margin-bottom: 4px;");
		zul.add("}");

		zul.add(".vlayout .z-toolbarbutton-cnt {");
		zul.add("  text-align: center;");
		zul.add("  color: #1C5178;");
		zul.add("  font-size: 10px;");
		zul.add("  font-family: arial, sans-serif;");
		zul.add("  font-weight: normal;");
		zul.add("}");
		zul.add("  </style>");
		}
		if (true) {
		String logo = "main/main_logo.png";
		ImageResources.copy(appId, "logo", logo, 64);
		zul.add("      <image src=\"${images}/" + logo + "\" />");
		}
		if (true) {
			zul.add("  <radio-toolbar>");
		}
		else {
			zul.add("  <toolbar height=\"100%\" align=\"start\">");
			zul.add("    <hlayout spacing=\"10px\">");
		}
		if (false) {
		String logo = "main/main_logo.png";
		ImageResources.copy(appId, "logo", logo, 64);
		zul.add("      <image src=\"${images}/" + logo + "\" />");
		}
//		zul.add("      <toolbarbutton checked=\"@bind(vm.checkedSorteos)\" sclass=\"vlayout\" label=\"Sorteos\" tooltiptext=\"Descripción\" mode=\"toggle\" image=\"${images}/main/module_sorteos.png\" />");
//		zul.add("      <toolbarbutton checked=\"@bind(vm.checkedConfiguracion)\" sclass=\"vlayout\" label=\"Configuración\" tooltiptext=\"Descripción\" mode=\"toggle\" image=\"${images}/main/module_configuracion.png\" />");
		List<String> modules = getModules(appId);
		for (String module : modules) {
			module = StringUtils.uncapFirst(module);
			String img = "main/module_" + module + ".png";
			ImageResources.copy(appId, module, img, 48);
			 //onClick=\"@command('selectModule')\"
//			zul.add("      <toolbarbutton checked=\"@bind(vm.checked" + StringUtils.capFirst(module) + ")\" width=\"72px\" sclass=\"vlayout\" label=\"${i18n." + module + "_moduleLabel}\" tooltiptext=\"${i18n." + module + "_moduleTooltip}\" mode=\"toggle\" image=\"${resources}/" + img + "\" />");
			zul.add("      <toolbarbutton id=\"mod_" + module + "\" onClick=\"@command('openModule', module='" + module + "')\" checked=\"@load(vm.selectedModule eq '" + module + "' ? true : false)\" width=\"72px\" sclass=\"vlayout\" label=\"${i18n." + module + "_moduleLabel}\" tooltiptext=\"${i18n." + module + "_moduleTooltip}\" mode=\"toggle\" image=\"${images}/" + img + "\" />");
		}
		if (true) {
			zul.add("  </radio-toolbar>");
		}
		else {
			zul.add("    </hlayout>");
			zul.add("  </toolbar>");
		}
	}

	
	/**
	 * 
	 * 

<zk>
  <borderlayout vflex="1">
    <north size="80px" border="0" splittable="true" collapsible="true">
      <include src="main_top.zul" />
    </north>
    <west title="@load(vm.appName)" width="150px" border="0" splittable="true" collapsible="true" margins="0,5,0,0" autoscroll="true">
      <include src="main_left.zul" />
    </west>
    <center autoscroll="true" border="0">
      <include src="@load(vm.content)" />
    </center>
    <south id="s" size="40px" border="0" style="background: none repeat scroll 0 0 ;">
      <include src="main_bottom.zul" />
    </south>
  </borderlayout>
</zk>


	 * 
	 * 
	 */
	public static void writeMainZul (String appId) {
		List<String> zul = new ArrayList<String>();
		String vmName = getMainVM(appId);
		
		if (true) {
			String logo = "main/main_logo.png";
			ImageResources.copy(appId, "logo", logo, 64);
			
			zul.add("<zk>");
			zul.add("  <main-window fulfill=\"onLoginSuccess\">");
//			zul.add("    <menu-bar>");
			
			List<String> modules = getModules(appId);
			int index = 0;
			for (String module : modules) {
				index += 1;
				module = StringUtils.uncapFirst(module);
//				String img = "main/module_" + module + ".png";
				String img = "main/" + module + ".png";
				ImageResources.copy(appId, module, img, 48);

if (false) {
	zul.add("    <menu link=\"openModule_" + module + "\" fulfill=\"onSelect\">");
}
else {
	if (index == 1) {
		zul.add("    <menu link=\"" + StringUtils.capFirst(module) + "\" selected=\"true\">");
	}
	else {
		zul.add("    <menu link=\"" + StringUtils.capFirst(module) + "\" fulfill=\"onSelect\">");
	}
}
if (false) {
				zul.add("      <menu-bar fulfill=\"parent.onClick\">");
}
				
				List<String> options = getOpciones(appId, module);
				for (String option : options) {
					String lowerOption = StringUtils.uncapFirst(option);
//					String lowerOption = option;
//					String imgOption = "main/option_" + lowerOption + ".png";
					String imgOption = "main/" + lowerOption + ".png";
					ImageResources.copy(appId, lowerOption, imgOption, 24);
					if (true) {
//						zul.add("        <menu link=\"" + StringUtils.capFirst(lowerOption) + "\" align=\"horizontal\" />");
						zul.add("        <menu link=\"" + StringUtils.capFirst(lowerOption) + "\" />");
					}
					else {
						zul.add("        <menu link=\"" + lowerOption + "_finder\" />");
					}
				}
				
if (false) {
				zul.add("      </menu-bar>");
}
				zul.add("    </menu>");
			}
			
//			zul.add("    </menu-bar>");
			zul.add("  </main-window>");
			zul.add("</zk>");
			
		}
		else {
	//		zul.add("<zk>");
			
	//		zul.add("<window border=\"none\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('vm') @init('" + vmName + "')\">");
	//		zul.add("  <borderlayout vflex=\"1\">");
			zul.add("<?taglib uri=\"http://www.zkoss.org/dsp/web/core\" prefix=\"c\"?>");
			zul.add("<zk>");
			
	/*
	 * 
	
	<zk>
	<style>
	.msg-info { 
		background-image : url(/zksandbox/img/button.png);
	       width: 32px; height: 32px;
	}
	</style>
	
	
	<window title="Titulo" mode="highlighted" closable="true" width="200px" visible="true">
	  <vlayout spacing="15px" style="margin: 4px 4px 4px 4px;">
	        <hlayout spacing="10px" valign="middle">
	             <div sclass="msg-info" />
	             <label value="Mensaje" />
	        </hlayout>
	        <hlayout spacing="10px" valign="middle">
	            <button label="Aceptar" />
	            <button label="Cancelar" />
	        </hlayout>
	  </vlayout>
	</window>
	
	</zk>
	
	 * 
	 */
			
			zul.add("<style src=\"${resources}/styles/main.css.dsp\" />");
			
			zul.add("  <borderlayout height=\"100%\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('vm') @init('" + vmName + "')\">");
			zul.add("    <north size=\"85px\" border=\"0\" splittable=\"true\" collapsible=\"true\">");
			zul.add("<hlayout height=\"100%\">");
	//		zul.add("      <include src=\"${resources}/main/main_top.zul\" />");
			writeTopZul(zul, appId);
	//MainVMTransform.writeTopZul(appId);
			zul.add("</hlayout>");
			zul.add("    </north>");
			zul.add("    <west title=\"${i18n.app_title}\" width=\"250px\" border=\"0\" splittable=\"true\" collapsible=\"true\" margins=\"0,5,0,0\" autoscroll=\"true\">");
			zul.add("<div height=\"100%\">");
	//		zul.add("      <include src=\"${resources}/main/main_left.zul\" />");
	//MainVMTransform.writeLeftZul(appId);
			writeLeftZul(zul, appId);
			zul.add("</div>");
			zul.add("    </west>");
			zul.add("    <center autoscroll=\"true\" border=\"0\">");
	zul.add("<div width=\"100%\" height=\"100%\">");
			
	if (false) {
			zul.add("<style>");
			zul.add(".msg-info { ");
			zul.add("background-image : url(/zksandbox/img/button.png);");
			zul.add("width: 32px; height: 32px;");
			zul.add("}");
			zul.add("</style>");
	}
	
			if (true) {
				zul.add("<msg-window message=\"@load(vm.msg)\" />");
			}
			else {
	//		zul.add("<window title=\"@load(vm.msg.title)\" mode=\"highlighted\" closable=\"true\" width=\"250px\" visible=\"@load(not empty vm.msg)\">");
			zul.add("<window title=\"@load(vm.msg.title)\" mode=\"highlighted\" closable=\"true\" position=\"center\" visible=\"@load(not empty vm.msg)\">");
			zul.add("  <vlayout spacing=\"15px\" style=\"margin: 4px 4px 4px 4px;\">");
			zul.add("    <hlayout spacing=\"10px\" valign=\"middle\">");
			zul.add("      <div sclass=\"@load(vm.msg.icon)\" />");
			zul.add("      <label value=\"@load(vm.msg.message)\" tooltiptext=\"@load(vm.msg.details)\" />");
			zul.add("    </hlayout>");
			zul.add("    <hlayout sclass=\"z-hlayout-right\" spacing=\"10px\" valign=\"middle\">");
			zul.add("      <button label=\"Aceptar\" onClick=\"@command('msgConfirm')\" />");
			zul.add("      <button label=\"Cancelar\" onClick=\"@command('msgCancel')\" />");
			zul.add("    </hlayout>");
			zul.add("  </vlayout>");
			zul.add("</window>");
			}
			
			final boolean mode = true;
			
			if (!mode) {
			zul.add("		<tabbox sclass=\"main-windows\" width=\"100%\" height=\"100%\">");
			}
			else {
				zul.add("		<tabbox sclass=\"main-windows\" width=\"100%\" height=\"100%\" visible=\"@load(vm.hasContents)\">");
			}
			if (!mode) {
				zul.add("			<tabs visible=\"@load(vm.multipleContents)\" children=\"@load(vm.contents)\">");
			}
			else {
	//			zul.add("			<tabs visible=\"@load(vm.hasContents)\">");
	//			zul.add("			<tabs visible=\"true\">");
				zul.add("			<tabs visible=\"false\">");
			}
			
			
	//		zul.add("					<tab label=\"0\" closable=\"false\" selected=\"@load(vm.selectedContent eq vm.contents.0)\" onSelect=\"@command('selectContent', item=tab)\" />");
	//		zul.add("					<tab label=\"1\" closable=\"false\" selected=\"@load(vm.selectedContent eq vm.contents.1)\" onSelect=\"@command('selectContent', item=tab)\" />");
			
			final int lengthFixedTabs = 13;
			if (mode) {
			for (int i = 0; i < lengthFixedTabs; i++) {
				zul.add("<tab id=\"mainTabWndIdx" + i + "\" label=\"" + i +"\" visible=\"@load(not empty vm.wnd" + i + ")\" closable=\"false\" selected=\"@load(vm.selectedContentIdx eq " + i + ")\" onSelect=\"@command('selectContentIdx', idx=" + i + ")\" />");
			}
			}
			else {
			zul.add("				<template name=\"children\" var=\"tab\">");
	//		zul.add("					<tab id=\"${c:cat('tab', c:string(tabStatus.index))}\" label=\"@load(tabStatus.index)\" closable=\"true\" selected=\"@load(vm.selectedContent eq tab)\" onSelect=\"@command('selectContent', item=tab)\" />");
			zul.add("					<tab label=\"@load(tabStatus.index)\" closable=\"false\" selected=\"@load(vm.selectedContent eq tab)\" onSelect=\"@command('selectContent', item=tab)\" />");
	//		zul.add("					<tab id=\"tab${c:string(tabStatus.index)}\" label=\"@load(tabStatus.index)\" closable=\"true\" selected=\"@load(vm.selectedContent eq tab)\" onSelect=\"@command('selectContent', item=tab)\" />");
			zul.add("				</template>");
			}
			zul.add("			</tabs>");
			if (!mode) {
			zul.add("			<tabpanels children=\"@load(vm.contents)\">");
			}
			else {
				zul.add("			<tabpanels>");
			}
	
			if (mode) {
			for (int i = 0; i < lengthFixedTabs; i++) {
				if (i == 0) {
					zul.add("<tabpanel><include height=\"100%\" visible=\"@load(not empty vm.wnd" + i + ")\" args=\"@load(vm.wnd" + i + ".args)\" src=\"@load(vm.wnd" + i + ".src)\" /></tabpanel>");
				}
				else {
	//				zul.add("<tabpanel fulfill=\"mainTabWndIdx" + i + ".onSelect\"><include height=\"100%\" visible=\"@load(not empty vm.wnd" + i + ")\" args=\"@load(vm.wnd" + i + ".args)\" src=\"@load(vm.wnd" + i + ".src)\" /></tabpanel>");
					zul.add("<tabpanel><include height=\"100%\" visible=\"@load(not empty vm.wnd" + i + ")\" args=\"@load(vm.wnd" + i + ".args)\" src=\"@load(vm.wnd" + i + ".src)\" /></tabpanel>");
				}
			}
			} 
			else {
			zul.add("				<template name=\"children\" var=\"item\">");
	//		zul.add("					<tabpanel fulfill=\"${c:cat('tab', c:string(itemStatus.index))}.onSelect\">");
			zul.add("					<tabpanel>");
	//		zul.add("					<tabpanel fulfill=\"tab${c:string(itemStatus.index)}.onSelect\">");
	//		zul.add("						<include mode=\"defer\" args=\"@load(item.args)\" src=\"@load(item.src)\" />");
	//		zul.add("						<include mode=\"defer\" src=\"@load(item.src)\" />");
			zul.add("						<include height=\"100%\" args=\"@load(item.args)\" src=\"@load(item.src)\" />");
	
	//		zul.add("						<include args=\"@load(vm.contents.0.args)\" src=\"@load(vm.contents.0.src)\" />");
			
			
			zul.add("					</tabpanel>");
			zul.add("				</template>");
			}
			zul.add("			</tabpanels>");
			zul.add("		</tabbox>");
			
			
	//		zul.add("      <include src=\"@load(vm.content)\" />");
			
	zul.add("</div>");
			zul.add("    </center>");
			zul.add("    <south size=\"32px\" border=\"0\" style=\"background: none repeat scroll 0 0 ;\">");
			zul.add("<div height=\"100%\">");
	//		zul.add("      <include src=\"${resources}/main/main_bottom.zul\" />");
	//		MainVMTransform.writeBottomZul(appId);
			writeBottomZul(zul, appId);
			zul.add("</div>");
			zul.add("    </south>");
			zul.add("  </borderlayout>");
	//		zul.add("</window>");
			zul.add("</zk>");
		}
		String filename = ServerContext.getRootContext() + appId + "/resources/main/main.zul";
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}
	
}
