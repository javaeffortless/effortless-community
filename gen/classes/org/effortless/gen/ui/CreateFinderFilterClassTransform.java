package org.effortless.gen.ui;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.GMethod;
import org.effortless.gen.InfoModel;
import org.effortless.model.CriteriaFilter;

public class CreateFinderFilterClassTransform extends Object implements Transform<GClass> {

	public CreateFinderFilterClassTransform () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateResult();
	}
	
	protected GClass result;
	
	protected void initiateResult () {
		this.result = null;
	}
	
	public GClass getResult () {
		return this.result;
	}
	
	protected void setResult (GClass newValue) {
		this.result = newValue;
	}
	
	@Override
	public void process(GClass clazz) {
		if (!clazz.hasAnnotation(FILTER_ANN)) {
			String cName = getFinderFilterName(clazz);
			GClass cg = new GClass(cName, clazz.getSourceUnit());
			cg.setSuperClass(cg.createGenericType(CriteriaFilter.class, clazz.getClassNode()));
			cg.addAnnotation(FILTER_ANN);
			
			List<GField> fields = this.getFinderProperties(clazz);
			if (fields != null) {
				for (GField field : fields) {
					addFilterProperty(cg, field);
				}
			}
			
			GMethod mg = null;
	
	//		protected Class<?> doGetFilterClass () {
	//			return Item.class;
	//		}
			mg = cg.addMethod("doGetFilterClass").setProtected(true).setReturnType(Class.class);
			mg.addReturn(mg.clazz(clazz.getClassNode()));
			
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
				for (GField field : fields) {
	//				String fName = field.getName();
					addFilterCondition(mg, field);
	//				mg.add(mg.call("ilk", mg.cte(fName), mg.field(fName)));
				}
			}
			mg.add(mg.call(mg.cteSuper(), "setupConditions"));
			
			setResult(cg);
		}
	}
	
	public static final ClassNode FILTER_ANN = ClassNodeHelper.toClassNode(org.effortless.ann.Filter.class);
	
	public String getFinderFilterName (GClass clazz) {
		String result = null;
		result = clazz.getName() + "FinderFilter";
		return result;
	}
	
	public List<GField> getFinderProperties (GClass clazz) {
		List<GField> result = null;
		result = InfoModel.listNotNullUnique(clazz);
		result = (result != null ? result : new ArrayList<GField>());
		int length = (result != null ? result.size() : 0);
		if (length < 5) {
			List<GField> fields = clazz.getFields();
			for (GField field : fields) {
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
	
	public void addFilterProperty(GClass filter, GField field) {
		if (field != null) {
			if (field.isString()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isTime()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isTimestamp()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isDate()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isBoolean()) {
				addFilterPropertyBooleanDouble(filter, field);
			}
			else if (field.isInteger()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isDouble()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isEnum()) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isFile() || field.isType(org.effortless.model.FileEntity.class)) {
				addFilterPropertySimple(filter, field);
			}
			else if (field.isCollection()|| field.isList()) {
				addFilterPropertySimple(filter, field);
			}
			else {//REF
				addFilterPropertySimple(filter, field);
			}
		}
	}
	
	public void addFilterPropertySimple (GClass filter, GField field) {
		addFilterPropertySimple(filter, field, field.getName());
	}
	
	public void addFilterPropertyBooleanDouble (GClass filter, GField field) {
		if (field != null) {
			String fName = field.getName();
			addFilterPropertySimple(filter, field, fName + "True");
			addFilterPropertySimple(filter, field, fName + "False");
		}
	}
	
	protected void addFilterPropertySimple (GClass filter, GField field, String name) {
		if (field != null && name != null) {
			filter.removeField(field.getName());
//			field = this.addField(field.getType(), name);
			
			ClassNode fType = field.getType();
			String fName = name;
//			String capfName = StringUtils.capFirst(fName);
			
			filter.addField(fType, fName);
			
			GMethod mg = null;
			
			String initiateName = field.getInitiateName();//"initiate" + capfName;
			mg = filter.addMethod(initiateName).setProtected(true);
			mg.add(mg.assign(mg.field(fName), mg.cteNull()));
			
			String getterName = field.getGetterName();//"get" + capfName;
			mg = filter.addMethod(getterName).setPublic(true).setReturnType(fType);
//			mg.gPrintln("getting filter property (begin)");
//			mg.gPrintln(mg.field(fName));
//			mg.gPrintln("getting filter property (end)");
			mg.addReturn(mg.field(fName));
			
			String setterName = field.getSetterName();//"set" + capfName;
			mg = filter.addMethod(setterName).setPublic(true).addParameter(fType, "newValue");
			mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(fName), mg.assign(mg.field(fName), mg.var("newValue"))));
		}
		
	}

	public void addFilterCondition(GMethod mg, GField field) {
		if (mg != null && field != null) {
			String fName = field.getName();
			
			if (field.isString()) {
				mg.add(mg.call("ilk", mg.cte(fName), mg.field(field)));
			}
			else if (field.isTime()) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
			else if (field.isTimestamp()) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
			else if (field.isDate()) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
			else if (field.isBoolean()) {
//				mg.add(mg.call("eq", mg.cte(fName), mg.field(fName)));
				mg.add(mg.call("eqBooleanDouble", mg.cte(fName), mg.field(fName + "True"), mg.field(fName + "False")));
			}
			else if (field.isInteger()) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
			else if (field.isDouble()) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
			else if (field.isEnum()) {
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
			else if (field.isFile() || field.isType(org.effortless.model.FileEntity.class)) {
				mg.add(mg.call("eqFile", mg.cte(fName), mg.field(field)));
			}
			else if (field.isCollection() || field.isList()) {
				mg.add(mg.call("in", mg.cte(fName), mg.field(field)));
			}
			else {//REF
				mg.add(mg.call("eq", mg.cte(fName), mg.field(field)));
			}
		}
	}
	
}
