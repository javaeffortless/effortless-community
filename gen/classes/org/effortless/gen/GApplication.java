package org.effortless.gen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.core.ClassNodeHelper;
import org.effortless.model.Entity;

public class GApplication implements GNode {

	public GApplication () {
		super();
		initiate();
	}
	
	public GApplication (String name) {
		this();
		setName(name);
	}
	
	protected void initiate () {
		initiateClasses();
		initiateName();
		initiateLogClass();
		initiateFileClass();
	}
	
	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		this.name = newValue;
	}
	
	protected List<GClass> classes;
	
	protected void initiateClasses () {
		this.classes = null;
	}
	
	public List<GClass> getClasses () {
		return this.classes;
	}
	
	protected void setClasses (List<GClass> newValue) {
		this.classes = newValue;
	}
	
	public void addClass (GClass clazz) {
		if (clazz != null) {
			this.classes = (this.classes != null ? this.classes : new ArrayList<GClass>());
			if (!this.classes.contains(clazz)) {
				this.classes.add(clazz);
				clazz.setApplication(this);
			}
		}
	}

	public List<String> getModules () {
		List<String> result = null;
		result = getUnitNames();
		return result;
	}

	public List<String> getOptions (String module) {
		List<String> result = null;
		result = getEntitiesByUnit(module);
		return result;
	}
	
	protected GClass logClass;
	
	protected void initiateLogClass () {
		this.logClass = null;
	}
	
	public GClass getLogClass () {
		return this.logClass;
	}
	
	public void setLogClass (GClass newValue) {
		this.logClass = newValue;
	}

	protected GClass fileClass;
	
	protected void initiateFileClass () {
		this.fileClass = null;
	}
	
	public GClass getFileClass () {
		return this.fileClass;
	}
	
	public void setFileClass (GClass newValue) {
		this.fileClass = newValue;
	}

	public SourceUnit getSourceUnit () {
		SourceUnit result = null;
		if (this.classes != null && this.classes.size() > 0) {
			result = this.classes.get(0).getSourceUnit();
		}
		return result;
	}
	
	public List<SourceUnit> getUnits () {
		List<SourceUnit> result = null;
		result = new ArrayList<SourceUnit>();
		if (this.classes != null && this.classes.size() > 0) {
			for (GClass item : this.classes) {
				SourceUnit unit = item.getSourceUnit();
				if (unit != null && !result.contains(unit)) {
					result.add(unit);
				}
			}
		}
		return result;
	}

	public List<String> getUnitNames () {
		List<String> result = null;
		result = new ArrayList<String>();
		List<SourceUnit> units = getUnits();
		if (units != null) {
			for (SourceUnit unit : units) {
				String unitName = (unit != null ? unit.getName() : null);
				String baseUnitName = (unitName != null ? FilenameUtils.getBaseName(unitName) : null);
				if (baseUnitName != null) {
					result.add(baseUnitName);
				}
			}
		}
		return result;
	}

	public SourceUnit getUnit (String unitName) {
		SourceUnit result = null;
		unitName = (unitName != null ? unitName.trim() : "");
		if (unitName.length() > 0) {
			List<SourceUnit> units = getUnits();
			if (units != null) {
				for (SourceUnit unit : units) {
					String baseUnitName = (unit != null ? unit.getName() : null);
					baseUnitName = (baseUnitName != null ? FilenameUtils.getBaseName(baseUnitName) : null);
					if (baseUnitName != null && baseUnitName.equals(unitName)) {
						result = unit;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public List<ClassNode> getClassesByUnit (String unitName) {
		List<ClassNode> result = null;
		SourceUnit unit = getUnit(unitName);
		result = (unit != null ? unit.getAST().getClasses() : null);
		return result;
	}
	
	protected static final ClassNode ENTITY_CLASS = ClassNodeHelper.toClassNode(Entity.class);
	
	public List<String> getEntitiesByUnit (String unitName) {
		List<String> result = null;
		result = new ArrayList<String>();
		List<ClassNode> classes = getClassesByUnit(unitName);
		for (ClassNode clazz : classes) {
			if (clazz != null && clazz.implementsInterface(ENTITY_CLASS)) {
				String className = clazz.getNameWithoutPackage();
				result.add(className);
			}
		}
		return result;
	}
	
	public boolean containsUnit (SourceUnit unit) {
		boolean result = false;
		if (unit != null) {
			List<SourceUnit> units = getUnits();
			if (units != null) {
				for (SourceUnit item : units) {
					if (unit == item) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	public GClass loadClass(ClassNode fieldType) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
