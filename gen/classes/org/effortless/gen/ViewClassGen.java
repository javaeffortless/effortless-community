package org.effortless.gen;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.ann.Finder;
import org.effortless.ann.NoTransform;
import org.effortless.core.Collections;
import org.effortless.core.GlobalContext;
import org.effortless.core.StringUtils;
import org.effortless.gen.fields.Restrictions;
import org.effortless.model.CriteriaFilter;
import org.effortless.model.Filter;
import org.effortless.model.SessionManager;
import org.effortless.server.WebUtils;
import org.effortless.ui.Message;
import org.effortless.ui.ScreenInfo;
import org.effortless.ui.resources.ImageResources;
import org.effortless.ui.vm.EditorVM;
import org.effortless.ui.vm.FinderFilterVM;
import org.effortless.ui.vm.FinderVM;
import org.objectweb.asm.Opcodes;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class ViewClassGen extends ClassGen {

	protected ViewClassGen () {
		super();
	}
	
	public ViewClassGen (String name, SourceUnit sourceUnit) {
		super(name, sourceUnit);
	}
	
	public ViewClassGen (ClassNode clazz) {
		super(clazz);
	}
	
	public ViewClassGen (ClassNode clazz, SourceUnit sourceUnit) {
		super(clazz, sourceUnit);
	}
	
	
	public String getFinderName () {
		String result = null;
		result = this.clazz.getName() + "FinderViewModel";
		return result;
	}
	
	public String getFinderFilterName () {
		String result = null;
		result = this.clazz.getName() + "FinderFilter";
		return result;
	}
	
	public String getEditorName () {
		String result = null;
		result = this.clazz.getName() + "EditorViewModel";
		return result;
	}

	public void addFieldsVM (ClassNode clazz) {
		List<FieldNode> fields = clazz.getFields();
		for (FieldNode field : fields) {
			ClassNode type = field.getType();
			ClassNode superClass = (type != null ? type.getSuperClass() : null);
			String superClassName = (superClass != null ? superClass.getName() : null);
			if (false && "java.lang.Enum".equals(superClassName)) {
				String pName = field.getName();
				String idListValues = "item" + StringUtils.capFirst(pName) + "Values";
				addEnumFieldVM(idListValues, field);
			}
		}
	}
	
	public ClassGen addEditorVM () {
		ClassGen result = null;
		
		if (true) {
			String cName = getEditorName();
			ViewClassGen cg = new ViewClassGen(cName, this.sourceUnit);
			cg.addAnnotation(org.zkoss.bind.annotation.Init.class, "superclass", true);
			cg.setSuperClass(EditorVM.class);

			cg.addFieldsVM(this.clazz);
			result = cg;
		}
		else {
			String cName = getEditorName();
			ViewClassGen cg = new ViewClassGen(cName, this.sourceUnit);
			cg.addSimpleProperty(this.clazz, "item", false);
			cg.addSimpleProperty(ScreenInfo.class, "screenInfo", false);
			cg.addSimpleProperty(Boolean.class, "readonly", false);
			cg.addSimpleProperty(Boolean.class, "persist", false);
			cg.addSimpleProperty(String.class, "mode", false);
			
			MethodGen mg = null;
			
			cg.addFieldsVM(this.clazz);

//		    @org.zkoss.bind.annotation.Init
//		    public void init(@org.zkoss.bind.annotation.ExecutionParam("args") java.util.Map args) {
//		        this.item = (clazz)args.get("item");
//		    }
			if (true) {
				mg = cg.addMethod("init").setPublic(true).addParameter(java.util.Map.class, "args", cg.createAnnotation(org.zkoss.bind.annotation.ExecutionArgParam.class, "args"));
				mg.addAnnotation(org.zkoss.bind.annotation.Init.class);
				mg.gPrintln("init editor mv");
				mg.add(mg.assign(mg.var("args"), mg.triple(mg.notNull(mg.var("args")), mg.var("args"), mg.callConstructor(java.util.HashMap.class))));//args = (args != null ? args : new java.util.HashMap());
				mg.add(mg.assign(mg.field("item"), mg.cast(this.clazz, mg.call("args", "get", mg.cte("item")))));//this.item = (clazz)args.get("item");
				mg.add(mg.assign(mg.field("screenInfo"), mg.cast(ScreenInfo.class, mg.call("args", "get", mg.cte("screenInfo")))));//this.item = (clazz)args.get("item");
				mg.add(mg.assign(mg.field("persist"), mg.cast(Boolean.class, mg.call("args", "get", mg.cte("persist")))));//this.item = (clazz)args.get("item");
				mg.add(mg.assign(mg.field("readonly"), mg.cast(Boolean.class, mg.call("args", "get", mg.cte("readonly")))));//this.item = (clazz)args.get("item");
				mg.add(mg.assign(mg.field("mode"), mg.cast(String.class, mg.call("args", "get", mg.cte("mode")))));//this.item = (clazz)args.get("item");
				mg.gPrintln(mg.field("item"));
			}
			
//			@org.zkoss.bind.annotation.Command
//			public void save() {
//				if (this.item.hasChanges()) {
//					this.item.persist();
//				}
//			}
			mg = cg.addMethod("save").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			MethodGen nb = mg.newBlock();
			nb.add(nb.call(nb.field("item"), "persist"));
			mg.addIf(mg.and(mg.notNull(mg.field("persist")), mg.call(mg.field("persist"), "booleanValue")), nb);
//			mg.add(mg.call(mg.field("item"), "persist"));
			mg.declVariable(java.util.HashMap.class, "args", mg.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			mg.add(mg.call("args", "put", mg.cte("screenInfo"), mg.field("screenInfo")));//args.put("screenInfo", "screenInfo");
			mg.add(mg.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", mg.cteNull(), mg.cteNull(), mg.cte("closeContent"), mg.var("args")));

			mg = cg.addMethod("cancel").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.declVariable(java.util.HashMap.class, "args", mg.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			mg.add(mg.call("args", "put", mg.cte("screenInfo"), mg.field("screenInfo")));//args.put("screenInfo", "screenInfo");
			mg.add(mg.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", mg.cteNull(), mg.cteNull(), mg.cte("closeContent"), mg.var("args")));
			
			mg = cg.addMethod("close").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.declVariable(java.util.HashMap.class, "args", mg.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			mg.add(mg.call("args", "put", mg.cte("screenInfo"), mg.field("screenInfo")));//args.put("screenInfo", "screenInfo");
			mg.add(mg.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", mg.cteNull(), mg.cteNull(), mg.cte("closeContent"), mg.var("args")));
			
			result = cg;
		}
		
		return result;
	}

	public void addFilterPropertySimple (FieldNode field) {
		if (field != null) {
			ClassNode fType = field.getType();
			String fName = field.getName();
			String capfName = StringUtils.capFirst(fName);
			
			this.addField(fType, fName);
			
			MethodGen mg = null;
			
			String initiateName = "initiate" + capfName;
			mg = addMethod(initiateName).setProtected(true);
			mg.add(mg.assign(mg.field(fName), mg.cteNull()));
			
			String getterName = "get" + capfName;
			mg = addMethod(getterName).setPublic(true).setReturnType(fType);
			mg.gPrintln("getting filter property (begin)");
			mg.gPrintln(mg.field(fName));
			mg.gPrintln("getting filter property (end)");
			mg.addReturn(mg.field(fName));
			
			String setterName = "set" + capfName;
			mg = addMethod(setterName).setPublic(true).addParameter(fType, "newValue");
			mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(fName), mg.assign(mg.field(fName), mg.var("newValue"))));
		}
	}
	
	public void addFilterPropertyBooleanDouble (FieldNode field) {
		if (field != null) {
			String fName = field.getName();
			addFilterPropertySimple(field, fName + "True");
			addFilterPropertySimple(field, fName + "False");
		}
	}
	
	protected void addFilterPropertySimple (FieldNode field, String name) {
		if (field != null && name != null) {
			removeField(field.getName());
//			field = this.addField(field.getType(), name);
			
			ClassNode fType = field.getType();
			String fName = name;
			String capfName = StringUtils.capFirst(fName);
			
			this.addField(fType, fName);
			
			MethodGen mg = null;
			
			String initiateName = "initiate" + capfName;
			mg = addMethod(initiateName).setProtected(true);
			mg.add(mg.assign(mg.field(fName), mg.cteNull()));
			
			String getterName = "get" + capfName;
			mg = addMethod(getterName).setPublic(true).setReturnType(fType);
			mg.gPrintln("getting filter property (begin)");
			mg.gPrintln(mg.field(fName));
			mg.gPrintln("getting filter property (end)");
			mg.addReturn(mg.field(fName));
			
			String setterName = "set" + capfName;
			mg = addMethod(setterName).setPublic(true).addParameter(fType, "newValue");
			mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(fName), mg.assign(mg.field(fName), mg.var("newValue"))));
		}
		
	}
	
	public static final ClassNode FILTER_ANN = ClassNodeHelper.toClassNode(org.effortless.ann.Filter.class);
	
	public ViewClassGen addFinderFilter () {
		ViewClassGen result = null;
		
		List<AnnotationNode> annotations = (this.clazz != null ? this.clazz.getAnnotations(FILTER_ANN) : null);
		if (!(annotations != null && annotations.size() > 0)) {
			String cName = getFinderFilterName();
			ViewClassGen cg = new ViewClassGen(cName, this.sourceUnit);
			cg.setSuperClass(cg.createGenericType(CriteriaFilter.class, this.clazz));
			cg.addAnnotation(FILTER_ANN);
			
			List<FieldNode> fields = this.getFinderProperties();
			if (fields != null) {
				for (FieldNode field : fields) {
					cg.addFilterProperty(field);
				}
			}
			
			MethodGen mg = null;
			MethodGen nb = null;
	
	//		protected Class<?> doGetFilterClass () {
	//			return Item.class;
	//		}
			mg = cg.addMethod("doGetFilterClass").setProtected(true).setReturnType(Class.class);
			mg.addReturn(mg.clazz(this.clazz));
			
	//		protected void setupConditions () {
	//			super.setupConditions();
	//			
	//			this.lk("name", this.name);
	//			if (this.name != null && this.name.trim().length() > 0) {
	//				this.criteria.add(Restrictions.like("name", "%" + this.name + "%"));
	//			}
	//		}
			mg = cg.addMethod("setupConditions").setProtected(true);
			if (fields != null) {
				for (FieldNode field : fields) {
	//				String fName = field.getName();
					cg.addFilterCondition(mg, field);
	//				mg.add(mg.call("ilk", mg.cte(fName), mg.field(fName)));
				}
			}
			mg.add(mg.call(mg.cteSuper(), "setupConditions"));
			
			result = cg;
		}
		return result;
	}


	public void addFilterProperty(FieldNode field) {
		if (field != null) {
			String fName = field.getName();
			
			ClassNode fieldClass = field.getType();
			if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(String.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Time.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Timestamp.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Date.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Boolean.class))) {
				addFilterPropertyBooleanDouble(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Integer.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Double.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Enum.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(File.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(org.effortless.model.FileEntity.class))) {
				addFilterPropertySimple(field);
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Collection.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(List.class))) {
				addFilterPropertySimple(field);
			}
			else {//REF
				addFilterPropertySimple(field);
			}
		}
	}
	
	
	
	public void addFilterCondition(MethodGen mg, FieldNode field) {
		if (mg != null && field != null) {
			String fName = field.getName();
			
			ClassNode fieldClass = field.getType();
			if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(String.class))) {
				mg.add(mg.call("ilk", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Time.class))) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Timestamp.class))) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Date.class))) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Boolean.class))) {
//				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
				mg.add(mg.call("eqBooleanDouble", mg.cte(fName), mg.field(fName + "True"), mg.field(fName + "False")));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Integer.class))) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Double.class))) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Enum.class))) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(File.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(org.effortless.model.FileEntity.class))) {
				mg.add(mg.call("eqFile", mg.cte(fName), mg.field(fName)));
			}
			else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Collection.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(List.class))) {
				mg.add(mg.call("in", mg.cte(fName), mg.field(fName)));
			}
			else {//REF
				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
			}
			
			
			
			
			
		}
	}
	
	
	
	public ViewClassGen addFinderFilterVM (ClassGen finderFilter) {
		ViewClassGen result = null;
		
		String cName = this.clazz.getName() + "VM";
		ViewClassGen cg = new ViewClassGen(cName, this.sourceUnit);
		MethodGen mg = null;
		if (true) {
			cg.addAnnotation(org.zkoss.bind.annotation.Init.class, "superclass", true);
			cg.setSuperClass(FinderFilterVM.class);
			
			mg = cg.addMethod("createDefaultFinderFilter").setProtected(true).setReturnType(Filter.class);
			mg.addReturn(mg.callConstructor(finderFilter.getClassNode()));
			
			cg.addFieldsVM(finderFilter.getClassNode());
		}
		else {
			cg.addSimpleProperty(this.clazz, "filter", false);

			
			mg = cg.addMethod("getFilterProperties").setPublic(true).setReturnType(java.util.Map.class);
			mg.addReturn(mg.triple(mg.notNull(mg.field("filter")), mg.call(mg.field("filter"), "toMap"), mg.cteNull()));
			
			mg = cg.addMethod("init").setPublic(true).addParameter(this.clazz, "filter", cg.createAnnotation(org.zkoss.bind.annotation.ExecutionArgParam.class, "filter"));
			mg.addAnnotation(org.zkoss.bind.annotation.Init.class);
			mg.gPrintln("init filter mv");
			mg.add(mg.assign(mg.field("filter"), mg.var("filter")));
			mg.add(mg.assign(mg.field("filter"), mg.triple(mg.notNull(mg.field("filter")), mg.field("filter"), mg.callConstructor(finderFilter.getClassNode()))));
			mg.gPrintln(mg.field("filter"));
		}
		
		result = cg;
		return result;
	}
	
	public ViewClassGen addMoreInfoVM() {
		ViewClassGen result = null;
		
		String cName = this.clazz.getName() + "MoreInfoVM";
		ViewClassGen cg = new ViewClassGen(cName, this.sourceUnit);
		
		cg.addSimpleProperty(this.clazz, "item", false);

		MethodGen mg = null;
		
		mg = cg.addMethod("init").setPublic(true).addParameter(this.clazz, "item", cg.createAnnotation(org.zkoss.bind.annotation.ExecutionArgParam.class, "item"));
		mg.addAnnotation(org.zkoss.bind.annotation.Init.class);
		mg.gPrintln("init more info mv (begin)");
		mg.add(mg.assign(mg.field("item"), mg.var("item")));
		mg.gPrintln(mg.field("item"));
		mg.gPrintln("init more info mv (end)");
		
		result = cg;
		return result;
	}
	
	public MethodGen addEnumFieldVM (String id, FieldNode field) {
		MethodGen result = null;
		ClassNode type = field.getType();
		if (type != null && type.isEnum()) {
			this.addField(java.util.List.class, id);
			
			result = this.addMethod("get" + StringUtils.capFirst(id)).setPublic(true).setReturnType(java.util.List.class);
			MethodGen ifCode = result.newBlock();
			ifCode.add(ifCode.assign(ifCode.field(id), ifCode.callConstructor(java.util.ArrayList.class)));
			List<FieldNode> values = type.getFields();
			for (FieldNode value : values) {//MIN_VALUE, MAX_VALUE, $VALUES
				String vName = value.getName();
				if (!"MIN_VALUE".equals(vName) && !"MAX_VALUE".equals(vName) && !"$VALUES".equals(vName)) {
					ifCode.add(ifCode.call(ifCode.field(id), "add", ifCode.field(value)));
				}
			}
			result.addIf(result.isNull(result.field(id)), ifCode);
			result.addReturn(result.field(id));
		}
		
		return result;
	}
	
	public MethodGen addProcessVM (ClassNode vm, String name, SourceUnit unit) {
		MethodGen result = null;
		
		MethodGen mg = null;
		
		name = (name != null ? name.trim() : "");
		String newName = vm.getName() + "$" + name;
		
		ClassGen cg = new ClassGen(newName, unit).setPublic(true).addInterface(Runnable.class);
		
		cg.addSimpleProperty(vm, "vm");
//		cg.addField(vm, "vm");
		
		mg = cg.addConstructor().setPublic(true).addParameter(vm, "vm");
		mg.add(mg.assign(mg.field("vm"), mg.var("vm")));

		mg = cg.addConstructor().setPublic(true);
		
		result = cg.addMethod("run").setPublic(true);
		
		return result;
	}
	
	public ClassGen addFinderVM (ClassNode finderFilter) {
		ClassGen result = null;

		if (true) {
			MethodGen mg = null;
			MethodGen nb = null;
			
			String entityName = this.clazz.getNameWithoutPackage();
			String cName = getFinderName();
			ClassGen cg = new ClassGen(cName, this.sourceUnit).setSuperClass(FinderVM.class);
			cg.addAnnotation(org.zkoss.bind.annotation.Init.class, "superclass", true);
			
//			protected String createDefaultFinderFilterZul() {
//				return null;
//			}
			mg = cg.addMethod("createDefaultFinderFilterZul").setProtected(true).setReturnType(String.class);
			String appId = SessionManager.getDbId(this.clazz.getName());
			String finderFilterZul = appId + File.separator + "resources" + File.separator + finderFilter.getNameWithoutPackage().toLowerCase() + ".zul";
			mg.addReturn(mg.cte(finderFilterZul));

//			protected Filter createDefaultFinderFilter () {
//				return null;
//			}
			mg = cg.addMethod("createDefaultFinderFilter").setProtected(true).setReturnType(Filter.class);
			mg.addReturn(mg.callConstructor(finderFilter));

//			protected String createDefaultEditorSrc () {
//				return null;
//			}
			mg = cg.addMethod("createDefaultEditorSrc").setProtected(true).setReturnType(String.class);
			String defaultEditor = entityName + "_editor";
			mg.addReturn(mg.cte(defaultEditor));

//			protected Object newItem () {
//				return null;
//			}
			mg = cg.addMethod("newItem").setProtected(true).setReturnType(Object.class);
			mg.addReturn(mg.callConstructor(this.clazz));
			
			if (false) {
			List<MethodNode> actions = getPublicActions();
			if (actions != null) {
				for (MethodNode action : actions) {
					String actionName = action.getName();
					String runAction = "run_" + actionName + "_OnItem";
	//				String runAction = actionName;
					
					mg = cg.addMethod(runAction).setPublic(true);
					mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
	//				mg.gPrintln(actionName);
	
					nb = mg.newBlock();
					nb.add(nb.call(nb.field("selectedItem"), actionName));
					mg.addIf(mg.notNull(mg.field("selectedItem")), nb);
				}
			}
			}

			result = cg;
		}
		else {
			MethodGen mg = null;
			MethodGen nb = null;
			
			String entityName = this.clazz.getNameWithoutPackage();
			String cName = getFinderName();
			ClassGen cg = new ClassGen(cName, this.sourceUnit);
			//cg.addSimpleProperty(String.class, "keyword", false);
			cg.addSimpleProperty(List.class, "list", true);
			cg.addSimpleProperty(this.clazz, "selectedItem", false);
			cg.addSimpleProperty(ScreenInfo.class, "screenInfo", false);
			cg.addSimpleProperty(org.effortless.model.Filter.class, "filter", false);
			cg.addSimpleProperty(String.class, "filterSrc", false);
	
			mg = cg.addSimpleProperty(this.clazz, "selectedOver", false);
			
			
			mg = cg.addSimpleProperty(Integer.class, "pageIndex", false);
	//		mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "filterProperties");
	
			mg = cg.addSimpleProperty(Integer.class, "pageSize", false);
	//		mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "filterProperties");
	
			mg = cg.addSimpleProperty(Integer.class, "totalPages", false);
	//		mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "filterProperties");
	
			mg = cg.addSimpleProperty(Integer.class, "numElements", false);
	//		mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "filterProperties");
	
			mg = cg.addSimpleProperty(Boolean.class, "paginated", false);
	//		mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "filterProperties");
			
			String op = queryAnnotation(Finder.class, "op", "lk");
			
	//	    @org.zkoss.bind.annotation.Init
	//	    public void init(@org.zkoss.bind.annotation.ExecutionParam("args") java.util.Map args) {
	//	    }
			if (true) {
				mg = cg.addMethod("init").setPublic(true).addParameter(java.util.Map.class, "args", cg.createAnnotation(org.zkoss.bind.annotation.ExecutionArgParam.class, "args"));
				mg.addAnnotation(org.zkoss.bind.annotation.Init.class);
				mg.gPrintln("init mv finder");
				mg.add(mg.assign(mg.var("args"), mg.triple(mg.notNull(mg.var("args")), mg.var("args"), mg.callConstructor(java.util.HashMap.class))));//args = (args != null ? args : new java.util.HashMap());
				mg.add(mg.assign(mg.field("screenInfo"), mg.cast(ScreenInfo.class, mg.call("args", "get", mg.cte("screenInfo")))));//this.item = (clazz)args.get("item");
				mg.add(mg.assign(mg.field("filter"), mg.cast(org.effortless.model.Filter.class, mg.call("args", "get", mg.cte("filter")))));//this.item = (clazz)args.get("item");
				mg.add(mg.assign(mg.field("filterSrc"), mg.cast(String.class, mg.call("args", "get", mg.cte("filterSrc")))));//this.item = (clazz)args.get("item");
	
				if (true) {
					String appId = SessionManager.getDbId(this.clazz.getName());
					String finderFilterZul = appId + File.separator + "resources" + File.separator + finderFilter.getNameWithoutPackage().toLowerCase() + ".zul";
				mg.add(mg.assign(mg.field("filter"), mg.triple(mg.notNull(mg.field("filter")), mg.field("filter"), mg.callConstructor(finderFilter))));
				mg.add(mg.assign(mg.field("filterSrc"), mg.triple(mg.notNull(mg.field("filterSrc")), mg.field("filterSrc"), mg.cte(finderFilterZul))));
				}
				
				mg.gPrintln(mg.cte("SHOW FILTER SRC ON FINDER"));
				mg.gPrintln(mg.field("filterSrc"));
				mg.gPrintln(mg.cte("SHOW FILTER ON FINDER"));
				mg.gPrintln(mg.field("filter"));
				
				mg.add(mg.assign(mg.field("numElements"), mg.cte(Integer.valueOf(0))));
	//			mg.add(mg.assign(mg.field("item"), mg.triple(mg.notNull(mg.var("args")), mg.cast(this.clazz, mg.call("args", "get", mg.cte("item"))), mg.cteNull())));//this.item = (args != null ? (clazz)args.get("item") : null);
			}
	
	//		mg = cg.addMethod("getMoreInfoScreen").setPublic(true).setReturnType(String.class);
	//		String moreInfoScreen = entityName + "moreInfo";
	//		mg.addReturn(mg.callStatic(WebUtils.class, "toScreenTimestamp", mg.cte(moreInfoScreen)));
			
			mg = cg.addMethod("changeOver").setPublic(true).addParameter(this.clazz, "item", cg.createAnnotation(BindingParam.class, "item"));
			mg.gPrintln("changeOver item (begin)");
			mg.gPrintln(mg.var("item"));
			mg.gPrintln("changeOver item (end)");
	//		mg.add(mg.callStatic(org.zkoss.bind.BindUtils.class, "postNotifyChange", mg.cteNull(), mg.cteNull(), mg.cteThis(), mg.cte("moreInfoScreen")));
			mg.add(mg.assign(mg.field("selectedOver"), mg.var("item")));
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, mg.cte("selectedOver"));
			
			mg = cg.addMethod("getFilterProperties").setPublic(true).setReturnType(java.util.Map.class);
			mg.addReturn(mg.triple(mg.notNull(mg.field("filter")), mg.call(mg.field("filter"), "toMap"), mg.cteNull()));
			mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "filter");
	
			mg = cg.addMethod("getListSize").setPublic(true).setReturnType(Integer.class);
			mg.addReturn(mg.triple(mg.notNull(mg.field("list")), mg.callStatic(Integer.class, "valueOf", mg.call(mg.field("list"), "size")), mg.cteNull()));
			mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "list");
	
			mg = cg.addMethod("getPageIndexStr").setPublic(true).setReturnType(Integer.class);
			mg.addReturn(mg.triple(mg.notNull(mg.field("pageIndex")), mg.plus(mg.field("pageIndex"), mg.cte(Integer.valueOf(1))), mg.cteNull()));
			mg.addAnnotation(org.zkoss.bind.annotation.DependsOn.class, "pageIndex");
			
			mg = cg.addMethod("search").setPublic(true);
	//		mg.assign("this.list", this.clazz, "listBy." + op, "#name", "this.keyword");
	//		mg.add(mg.assign(mg.field("list"), mg.call(mg.callStatic(this.clazz, "listBy"), op, mg.cte("name"), mg.field("keyword"))));
			mg.add(mg.assign(mg.field("list"), mg.call(mg.field("filter"), "listPage")));
			mg.add(mg.call("setPageIndex", mg.call(mg.field("filter"), "getPageIndex")));
			mg.add(mg.call("setPageSize", mg.call(mg.field("filter"), "getPageSize")));
			mg.add(mg.call("setTotalPages", mg.call(mg.field("filter"), "getTotalPages")));
			mg.add(mg.call("setNumElements", mg.call(mg.field("filter"), "getSize")));
			mg.add(mg.call("setPaginated", mg.call(mg.field("filter"), "getPaginated")));
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, mg.array(mg.cte("list"), mg.cte("pageIndex"), mg.cte("pageSize"), mg.cte("totalPages"), mg.cte("numElements"), mg.cte("paginated")));
	
			mg = cg.addMethod("previousPage").setPublic(true);
			mg.add(mg.call(mg.field("filter"), "previousPage"));
			mg.add(mg.call("search"));
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, mg.array(mg.cte("list"), mg.cte("filter"), mg.cte("pageIndex"), mg.cte("pageSize"), mg.cte("totalPages"), mg.cte("paginated")));
			
			mg = cg.addMethod("nextPage").setPublic(true);
			mg.add(mg.call(mg.field("filter"), "nextPage"));
			mg.add(mg.call("search"));
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, mg.array(mg.cte("list"), mg.cte("filter"), mg.cte("pageIndex"), mg.cte("pageSize"), mg.cte("totalPages"), mg.cte("paginated")));
			
			mg = cg.addMethod("createItem").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.gPrintln("creando");
			mg.declVariable(java.util.HashMap.class, "args", mg.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			mg.add(mg.call("args", "put", mg.cte("content"), mg.cte(entityName + "_editor")));//args.put("content", "item_editor");
			mg.declVariable(java.util.HashMap.class, "params", mg.callConstructor(java.util.HashMap.class));//java.util.Map params = new java.util.HashMap();
			mg.add(mg.call("params", "put", mg.cte("item"), mg.callConstructor(this.clazz)));//params.put("item", new Tag());
			mg.add(mg.call("params", "put", mg.cte("mode"), mg.cte("create")));//params.put("item", "create");
			mg.add(mg.call("params", "put", mg.cte("persist"), mg.cteTRUE()));//params.put("item", "create");
			mg.add(mg.call("params", "put", mg.cte("readonly"), mg.cteFALSE()));//params.put("item", "create");
			mg.add(mg.call("args", "put", mg.cte("args"), mg.var("params")));//args.put("args", params);
			mg.add(mg.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", mg.cteNull(), mg.cteNull(), mg.cte("addNewContent"), mg.var("args")));//org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);
	
			mg = cg.addMethod("readItem").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			nb = mg.newBlock();
			nb.gPrintln("reading");
			nb.declVariable(java.util.HashMap.class, "args", nb.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			nb.add(nb.call("args", "put", nb.cte("content"), nb.cte(entityName + "_editor")));//args.put("content", "item_editor");
			nb.declVariable(java.util.HashMap.class, "params", nb.callConstructor(java.util.HashMap.class));//java.util.Map params = new java.util.HashMap();
			nb.add(nb.call("params", "put", nb.cte("item"), nb.field("selectedItem")));//params.put("item", this.selectedItem);
			nb.add(nb.call("params", "put", nb.cte("mode"), nb.cte("read")));//params.put("item", "read");
			nb.add(nb.call("params", "put", nb.cte("persist"), nb.cteFALSE()));//params.put("item", "create");
			nb.add(nb.call("params", "put", nb.cte("readonly"), nb.cteTRUE()));//params.put("item", "create");
			nb.add(nb.call("args", "put", nb.cte("args"), nb.var("params")));//args.put("args", params);
			nb.add(nb.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", nb.cteNull(), nb.cteNull(), nb.cte("addNewContent"), nb.var("args")));//org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);
			mg.addIf(mg.notNull(mg.field("selectedItem")), nb);
			
			mg = cg.addMethod("updateItem").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			nb = mg.newBlock();
			nb.gPrintln("updating...");
			nb.declVariable(java.util.HashMap.class, "args", nb.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			nb.add(nb.call("args", "put", nb.cte("content"), nb.cte(entityName + "_editor")));//args.put("content", "item_editor");
			nb.declVariable(java.util.HashMap.class, "params", nb.callConstructor(java.util.HashMap.class));//java.util.Map params = new java.util.HashMap();
			nb.add(nb.call("params", "put", nb.cte("item"), nb.field("selectedItem")));//params.put("item", this.selectedItem);
			nb.add(nb.call("params", "put", nb.cte("mode"), nb.cte("update")));//params.put("item", "read");
			nb.add(nb.call("params", "put", nb.cte("persist"), nb.cteTRUE()));//params.put("item", "create");
			nb.add(nb.call("params", "put", nb.cte("readonly"), nb.cteFALSE()));//params.put("item", "create");
			nb.add(nb.call("args", "put", nb.cte("args"), nb.var("params")));//args.put("args", params);
			nb.add(nb.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", nb.cteNull(), nb.cteNull(), nb.cte("addNewContent"), nb.var("args")));//org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);
			mg.addIf(mg.notNull(mg.field("selectedItem")), nb);
	
			mg = cg.addMethod("toString").setPublic(true).setReturnType(String.class);
			mg.addReturn(mg.cte("toString soy vm"));
			
			mg = cg.addMethod("deleteItem").setPublic(true);
			mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
			mg.gPrintln("deleting");
	
			nb = mg.newBlock();
	
			nb.gPrintln("begin delete");
			nb.declVariable(Message.class, "msg", nb.callStatic(Message.class, "createDelete"));
			
			MethodGen processOk = this.addProcessVM(cg.getClassNode(), "DeleteItemOk", cg.getSourceUnit());
			processOk.gPrintln("run process ok from extern inner (begin)");
			processOk.gPrintln(processOk.field("vm"));
			processOk.gPrintln(processOk.property(processOk.field("vm"), "selectedItem"));
			processOk.add(processOk.call(processOk.property(processOk.field("vm"), "selectedItem"), "delete"));
			processOk.gPrintln("run process ok from extern inner (end)");
			
	//		nb.declFinalVariable(this.clazz, "_selectedItem", nb.field("selectedItem"));
			
	//		ClassGen cr = nb.addAnonymousClass(Runnable.class);
	//		cr.addConstructor();
	//		MethodGen run = cr.addMethod("run").setPublic(true);
	//		run.gPrintln("run process ok from inner");
	//		run.add(run.call(nb.var("_selectedItem"), "delete"));
	//		nb.add(nb.call(nb.var("msg"), "setProcessOk", nb.callConstructor(cr.getClassNode())));
			nb.declFinalVariable(cg.getClassNode(), "_vm", nb.cteThis());
			nb.gPrintln(nb.var("_vm"));
			nb.declFinalVariable(processOk.getClassGen().getClassNode(), "processOk", nb.callConstructor(processOk.getClassGen().getClassNode()));
			nb.add(nb.call(nb.var("processOk"), "setVm", nb.var("_vm")));
			nb.add(nb.call(nb.var("msg"), "setProcessOk", nb.var("processOk")));
			nb.declVariable(java.util.HashMap.class, "args", nb.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
			nb.add(nb.call("args", "put", nb.cte("msg"), nb.var("msg")));//args.put("content", "item_editor");
			nb.add(nb.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", nb.cteNull(), nb.cteNull(), nb.cte("openMessage"), nb.var("args")));//org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);
			nb.gPrintln("end delete");
	
	//		nb.add(nb.call(nb.field("selectedItem"), "delete"));
			mg.addIf(mg.notNull(mg.field("selectedItem")), nb);
	
			List<MethodNode> actions = getPublicActions();
			if (actions != null) {
				for (MethodNode action : actions) {
					String actionName = action.getName();
					String runAction = "run_" + actionName + "_OnItem";
	//				String runAction = actionName;
					
					mg = cg.addMethod(runAction).setPublic(true);
					mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
	//				mg.gPrintln(actionName);
	
					nb = mg.newBlock();
					nb.add(nb.call(nb.field("selectedItem"), actionName));
					mg.addIf(mg.notNull(mg.field("selectedItem")), nb);
				}
			}
			addCloneAction(cg);
	
			result = cg;
		}
		return result;
	}
	
	protected void addCloneAction(ClassGen cg) {
		MethodGen mg = null;
		MethodGen nb = null;
		
		String entityName = this.clazz.getNameWithoutPackage();

		String actionName = "clone";
		String runAction = "run_" + actionName + "_OnItem";
//		String runAction = actionName;
		
		mg = cg.addMethod(runAction).setPublic(true);
		mg.addAnnotation(org.zkoss.bind.annotation.Command.class);
		mg.gPrintln(actionName);

		nb = mg.newBlock();
		nb.declVariable(this.clazz, "itemClone", nb.call(nb.field("selectedItem"), actionName));
		
		nb.declVariable(java.util.HashMap.class, "args", nb.callConstructor(java.util.HashMap.class));//java.util.Map args = new java.util.HashMap();
		nb.add(nb.call("args", "put", nb.cte("content"), nb.cte(entityName + "_editor")));//args.put("content", "item_editor");
		nb.declVariable(java.util.HashMap.class, "params", nb.callConstructor(java.util.HashMap.class));//java.util.Map params = new java.util.HashMap();
		nb.add(nb.call("params", "put", nb.cte("item"), nb.var("itemClone")));//params.put("item", new Tag());
		nb.add(nb.call("params", "put", nb.cte("mode"), nb.cte("create")));//params.put("item", "create");
		nb.add(nb.call("params", "put", nb.cte("persist"), nb.cteTRUE()));//params.put("item", "create");
		nb.add(nb.call("params", "put", nb.cte("readonly"), nb.cteFALSE()));//params.put("item", "create");
		nb.add(nb.call("args", "put", nb.cte("args"), nb.var("params")));//args.put("args", params);
		nb.add(nb.callStatic(org.zkoss.bind.BindUtils.class, "postGlobalCommand", nb.cteNull(), nb.cteNull(), nb.cte("addNewContent"), nb.var("args")));//org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "addNewContent", args);
		
		
		mg.addIf(mg.notNull(mg.field("selectedItem")), nb);

		// TODO Auto-generated method stub
		
	}

	public List<String> getModules (String appId) {
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
	
	public ViewClassGen addMainVM (String appId) {
//		String className = appId + "." + "MainWindowVM";
//		ClassGen cg = new ClassGen(className, this.sourceUnit).addAnnotation(org.effortless.NoEntity.class);
		addSimpleProperty(String.class, "appName");
		addSimpleProperty(String.class, "selectedModule");
		addSimpleProperty(String.class, "content");
		addSimpleProperty(String.class, "footerMsg");
		
		MethodGen mg = null;
		
		List<String> modules = getModules(appId);
		for (String module : modules) {
			String cte = "MODULE_" + module.toUpperCase();
			addCte(String.class, cte, module);

			String getterName = "getChecked" + StringUtils.capFirst(module);
			mg = addMethod(getterName).setPublic(true).setReturnType(Boolean.class);
			mg.declVariable(Boolean.class, "result");
			mg.add(mg.assign("result", mg.call(mg.property(cte), "equals", mg.field("selectedModule"))));
			mg.addReturn("result");

			String setterName = "setChecked" + StringUtils.capFirst(module);
			mg = addMethod(setterName).setPublic(true).addParameter(Boolean.class, "newValue");
			mg.add(mg.call("selectModule", mg.property(cte)));
			
			List<String> options = getOpciones(appId, module);
			for (String option : options) {
				String finderName = StringUtils.capFirst(option) + "Finder";
				String finderUrl = option + "_finder.zul";
				mg = addMethod("open" + finderName).setPublic(true);
				mg.addAnnotation(org.zkoss.bind.annotation.NotifyChange.class, "content");
				mg.add(mg.call("setContent", mg.cte(finderUrl)));
			}
		}

		mg = addMethod("selectModule").setProtected(true).addParameter(Boolean.class, "value").addParameter(String.class, "module");
		mg.gPrintln(mg.var("value"));
		Expression cond = mg.and(mg.notNull("value"), mg.call("value", "booleanValue"), mg.notNull("module"), mg.call("module", "equals", mg.field("selectedModule")));//String selectCode = "if (value != null && value.booleanValue() && module != null && module.equals(this.selectedModule)) {";
		MethodGen nb = mg.newBlock();
		nb.assign(mg.field("selectedModule"), "module");//selectCode += "this.selectedModule = module";
		for (String module : modules) {
			String valueNotify = "checked" + StringUtils.capFirst(module) + "";
			nb.callStatic(org.zkoss.bind.BindUtils.class, "postNotifyChange", mg.cteNull(), mg.cteNull(), mg.cteThis(), mg.cte(valueNotify));//"org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, \"checked" + StringUtils.capFirst(module) + "\")";
		}
		mg.addIf(cond, nb);
		
		return this;
	}

	public List<MethodNode> getPublicActions() {
		List<MethodNode> result = null;
		result = new ArrayList<MethodNode>();
		
		List<MethodNode> methods = this.clazz.getMethods();
		if (methods != null) {
			for (MethodNode method : methods) {
				boolean valid = true;
				valid = valid && method.isPublic();
				valid = valid && ClassHelper.VOID_TYPE.equals(method.getReturnType());
				valid = valid && (method.getParameters() == null || method.getParameters().length <= 0);
				if (valid) {
					result.add(method);
				}
			}
		}
		return result;
	}

	public List<FieldNode> getFinderProperties () {
		List<FieldNode> result = null;
		result = Restrictions.listNotNullUnique(this.clazz);
		result = (result != null ? result : new ArrayList<FieldNode>());
		int length = (result != null ? result.size() : 0);
		if (length < 5) {
			List<FieldNode> fields = this.clazz.getFields();
			for (FieldNode field : fields) {
				if (!result.contains(field)) {
					result.add(field);
					if (result.size() >= 50) {
						break;
					}
				}
			}
		}
		return result;
	}

	public List<FieldNode> getProperties(String[] propertyNames) {
		List<FieldNode> result = null;
		result = new ArrayList<FieldNode>();
		if (propertyNames != null && propertyNames.length > 0) {
			List<FieldNode> fields = this.clazz.getFields();
			for (FieldNode field : fields) {
				String fieldName = field.getName();
				if (Collections.contains(propertyNames, fieldName)) {
					result.add(field);
				}
			}
		}
		return result;
	}

}
