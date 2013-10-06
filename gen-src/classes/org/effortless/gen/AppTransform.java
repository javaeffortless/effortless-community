package org.effortless.gen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.model.Entity;

public class AppTransform {

	public AppTransform (String appId) {
		this.appId = appId;
	}
	
	protected String appId;
	
	public String getAppId () {
		return this.appId;
	}
	
	protected List<ItemTransform> items;
	
	public List<ItemTransform> getItems () {
		return this.items;
	}

	public void addItem (ASTNode[] nodes, SourceUnit sourceUnit) {
		addItem(new ItemTransform(nodes, sourceUnit));
	}
	
	public void addItem (ItemTransform item) {
		if (item != null) {
			this.items = (this.items != null ? this.items : new ArrayList<ItemTransform>());
			int indexOf = this.items.indexOf(item);
			if (indexOf >= 0) {
				ItemTransform oldItem = this.items.get(indexOf);
				oldItem.addNodes(item.getNodes());
			}
			else {
				this.items.add(item);
			}
		}
	}
	
	public SourceUnit getSourceUnit () {
		SourceUnit result = null;
		if (this.items != null && this.items.size() > 0) {
			result = this.items.get(0).getSourceUnit();
		}
		return result;
	}
	
	public List<SourceUnit> getUnits () {
		List<SourceUnit> result = null;
		result = new ArrayList<SourceUnit>();
		if (this.items != null && this.items.size() > 0) {
			for (ItemTransform item : this.items) {
				SourceUnit unit = item.getSourceUnit();
				if (unit != null) {
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
	
	protected static final ClassNode ENTITY_CLASS = new ClassNode(Entity.class);
	
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

}
