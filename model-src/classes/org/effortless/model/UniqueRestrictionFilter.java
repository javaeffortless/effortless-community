package org.effortless.model;

import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public class UniqueRestrictionFilter extends CriteriaFilter {

	public UniqueRestrictionFilter (String entityName, String deletedName, Boolean deletedValue, String idName, Object idValue, String[] propertyNames, Object[] params) {
		super();
		this.entityName = entityName;
		this.deletedName = deletedName;
		this.deletedValue = deletedValue;
		this.idName = idName;
		this.idValue = idValue;
		this.propertyNames = propertyNames;
		this.params = params;
		
		this.deletedName = (this.deletedName != null ? this.deletedName.trim() : "");
		setPaginated(Boolean.TRUE);
		setPageIndex(Integer.valueOf(0));
		setPageSize(Integer.valueOf(1));
		
//		setOrderBy("date DESC");
	}
	
	protected String entityName;
	protected String deletedName;
	protected Boolean deletedValue;
	protected String idName;
	protected Object idValue;
	protected String[] propertyNames;
	protected Object[] params;

	protected void buildCriteria () {
		if (this.criteria == null) {
			Session session = SessionManager.loadSession(this.entityName);
			this.criteria = session.createCriteria(this.entityName);
		}
	}	
	
	protected void setupConditions () {
		super.setupConditions();
		
		if (this.deletedName.length() > 0) {
			if (this.deletedValue != null && this.deletedValue.booleanValue() == false) {
				Disjunction or = Restrictions.disjunction();
				or.add(Restrictions.isNull(this.deletedName));
				or.add(Restrictions.eq(this.deletedName, Boolean.FALSE));
				this.criteria.add(or);
				//query += "(o." + deletedName + " IS NULL OR o." + deletedName + " = FALSE) AND ";
			}
			else if (deletedValue != null && deletedValue.booleanValue() == true) {
				this.criteria.add(Restrictions.eq(this.deletedName, Boolean.TRUE));
				//query += "(o." + deletedName + " = TRUE) AND ";
			}
		}
		
		if (this.idValue != null) {
			this.criteria.add(Restrictions.ne(this.idName, this.idValue));
			//query += "o." + this.idName + " <> ?1 AND ";
		}

		int paramsLength = (this.params != null ? this.params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			String pName = this.propertyNames[i];
			Object pValue = this.params[i];
			if (pValue != null) {
				this.criteria.add(Restrictions.eq(pName, pValue));
				//query += "o." + pName + " = ?" + String.valueOf(idx) + " ";
			}
			else {
				this.criteria.add(Restrictions.isNull(pName));
				//query += "o." + pName + " IS NULL ";
			}
		}
	}
	
}
