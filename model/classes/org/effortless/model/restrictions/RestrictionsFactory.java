package org.effortless.model.restrictions;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 

				
				
				<bool name="res_bool"><notnull /><unique /><compare op="ne" property="bool2" /></bool>
				<count name="res_count"><notnull /><range min="1" max="10" /><unique /><compare op="lt" property="count2" /></count>
				<date name="res_date"><notnull /><range min="1" max="10" /><compare op="lt" property="otroDate" /><unique /><current op="lt" /></date>
				<enum name="res_enum" target="AcadPersonaAdmSexo"><notnull /><unique /><compare op="ne" property="enum2" /></enum>
				<file name="res_file" target="ResFile"><notnull /><unique /><size min="1 MB" max="10 MB" /><dimensions minWidth="100" maxWidth="200" minHeight="300" maxHeight="400" /><compare op="eq" property="file2" /></file>
				<list name="res_list" item="AcadPaisAdm"><range min="10" max="20" /><notnull /><compare op="eq" property="list2" /></list>
				<number name="res_number"><notnull /><range min="(10" max="20]" /><unique /><compare op="ge" property="number2" /></number>
				<password name="res_password"><notnull /><range min="10" max="20" /><regex pattern="+.*" /><unique /><compare op="ne" property="pass2" /></password>
				<ref name="res_ref" target="AcadPaisAdm"><notnull /><unique /><compare op="ne" property="equipoVisitante" /></ref>
				<text name="res_text"><notnull /><range min="10" max="20" /><regex pattern="+.*" /><unique /><compare op="ne" property="text2" /></text>
				<time name="res_time"><notnull /><range min="1" max="10" /><unique /><compare op="lt" property="time2" /></time>
				<entity id="ResFile" type="file" />
				
  bool: notnull, unique
  count: range, notnull, unique
  date: range, notnull, unique, compare
  enum: notnull, unique
  file: size, dimensions, notnull, unique
  list: range?, notnull
  number: range, notnull, unique
  password: range, regex, notnull, unique
  ref: notnull, unique
  text: range, regex, notnull, unique
  time: range, notnull, unique 
				<entity id="ResFile" type="file" />


 * 
 * 
 * 
 * 
 * 
  bool: notnull, unique
  count: range, notnull, unique
  date: range, notnull, unique
  enum: notnull, unique
  file: size, dimensions, notnull, unique
  list: range?, notnull
  number: range, notnull, unique
  password: range, regex, notnull, unique
  ref: notnull, unique
  text: range, regex, notnull, unique
  time: range, notnull, unique 

	factory.addCountRange(String propertyName, Integer minValue, Integer maxValue);
	
	factory.addDateRange (String propertyName, Date minValue, Date maxValue);
	
	factory.addFileSize (String propertyName, Long minSize, Long maxSize);
	
	factory.addNotNull (String propertyName);
	
	factory.addNumberRange (String propertyName, Double minValue, Double maxValue, Boolean excludeMinValue, Boolean excludeMaxValue);
	
	factory.addPhotoDimensions (String propertyName, Integer minWidth, Integer maxWidth, Integer minHeight, Integer maxHeight);
	
	factory.addRegex (String propertyName, String pattern);

	factory.addTextLength (String propertyName, Integer minLength, Integer maxLength);
	
	factory.addTimeRange (String propertyName, Time minValue, Time maxValue);
	
	factory.addUnique (String propertyName, Object options__);



 *
 */
public class RestrictionsFactory extends Object {

	public RestrictionsFactory () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateItems();
	}
	
	protected List items;
	
	protected void initiateItems () {
		this.items = null;
	}
	
	protected List getItems () {
		return this.items;
	}
	
	protected void setItems (List newValue) {
		this.items = newValue;
	}
	
	public void addItem (Restriction item) {
		if (item != null) {
			if (this.items == null) {
				this.items = new ArrayList();
			}
			this.items.add(item);
		}
	}
	
	public void addCustom (Restriction restriction) {
		addItem(restriction);
	}
	
	public void addCountRange (String propertyName, Integer minValue, Integer maxValue) {
		CountRangeRestriction restriction = new CountRangeRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinValue(minValue);
		restriction.setMaxValue(maxValue);
		addItem(restriction);
	}
	
	public void addListRange (String propertyName, Integer minValue, Integer maxValue) {
		ListRangeRestriction restriction = new ListRangeRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinValue(minValue);
		restriction.setMaxValue(maxValue);
		addItem(restriction);
	}
	
	public void addDateRange (String propertyName, Date minValue, Date maxValue) {
		DateRangeRestriction restriction = new DateRangeRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinValue(minValue);
		restriction.setMaxValue(maxValue);
		addItem(restriction);
	}
	
	public void addDateCompare (String propertyName, String pivotProperty, OpCompareType op) {
		DateCompareRestriction restriction = new DateCompareRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setPivotProperty(pivotProperty);
		restriction.setOp(op);
		addItem(restriction);
	}
	
	public void addTimeCompare (String propertyName, String pivotProperty, OpCompareType op) {
		TimeCompareRestriction restriction = new TimeCompareRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setPivotProperty(pivotProperty);
		restriction.setOp(op);
		addItem(restriction);
	}
	
	public void addObjectCompare (String propertyName, String pivotProperty, OpCompareType op) {
		ObjectCompareRestriction restriction = new ObjectCompareRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setPivotProperty(pivotProperty);
		restriction.setOp(op);
		addItem(restriction);
	}
	
	public void addDateCurrent (String propertyName, OpCompareType op) {
		DateCurrentRestriction restriction = new DateCurrentRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setOp(op);
		addItem(restriction);
	}
	
	protected Date toDate (String value, String pattern) throws ParseException {
		Date result = null;
		result = org.effortless.core.DateUtils.decodeDate(value, pattern);
		return result;
	}
	
	protected Time toTime (String value, String pattern) throws ParseException {
		Time result = null;
		Date date = toDate(value, pattern);
		result = new Time(date.getTime());
		return result;
	}
	
	public void addDateRange (String propertyName, String minValue, String maxValue) {
		addDateRange(propertyName, minValue, maxValue, "dd/MM/yyyy");
	}
	
	public void addDateRange (String propertyName, String minValue, String maxValue, String pattern) {
		Date _minValue = null;
		try {
			_minValue = toDate(minValue, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date _maxValue = null;
		try {
			_maxValue = toDate(maxValue, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addDateRange(propertyName, _minValue, _maxValue);
	}
	
	public void addFileSize (String propertyName, Long minSize, Long maxSize) {
		FileSizeRestriction restriction = new FileSizeRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinSize(minSize);
		restriction.setMaxSize(maxSize);
		addItem(restriction);
	}
	
	public void addNotNull (String propertyName) {
		NotNullRestriction restriction = new NotNullRestriction();
		restriction.setPropertyName(propertyName);
		addItem(restriction);
	}
	
	public void addCcc (String propertyName) {
		CccRestriction restriction = new CccRestriction();
		restriction.setPropertyName(propertyName);
		addItem(restriction);
	}
	
	public void addCcc (String[] propertyNames) {
		CccValuesRestriction restriction = new CccValuesRestriction();
		restriction.setPropertyNames(propertyNames);
		addItem(restriction);
	}
	
	public void addNumberRange (String propertyName, Double minValue, Double maxValue, Boolean excludeMinValue, Boolean excludeMaxValue) {
		NumberRangeRestriction restriction = new NumberRangeRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinValue(minValue);
		restriction.setMaxValue(maxValue);
		restriction.setExcludeMinValue(excludeMinValue);
		restriction.setExcludeMaxValue(excludeMaxValue);
		addItem(restriction);
	}
	
	public void addPhotoDimensions (String propertyName, /*Long minSize, Long maxSize, */Integer minWidth, Integer maxWidth, Integer minHeight, Integer maxHeight) {
		PhotoDimensionsRestriction restriction = new PhotoDimensionsRestriction();
		restriction.setPropertyName(propertyName);
//		restriction.setMinSize(minSize);
//		restriction.setMaxSize(maxSize);
		restriction.setMinWidth(minWidth);
		restriction.setMaxWidth(maxWidth);
		restriction.setMinHeight(minHeight);
		restriction.setMaxHeight(maxHeight);
		addItem(restriction);
	}
	
	public void addRegex (String propertyName, String pattern) {
		RegexRestriction restriction = new RegexRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setPattern(pattern);
		addItem(restriction);
	}
	
	public void addTextLength (String propertyName, Integer minLength, Integer maxLength) {
		TextLengthRestriction restriction = new TextLengthRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinLength(minLength);
		restriction.setMaxLength(maxLength);
		addItem(restriction);
	}

	public void addBoolTrue (String propertyName) {
		BoolTrueRestriction restriction = new BoolTrueRestriction();
		restriction.setPropertyName(propertyName);
		addItem(restriction);
	}
	
	public void addTimeRange (String propertyName, Time minValue, Time maxValue) {
		TimeRangeRestriction restriction = new TimeRangeRestriction();
		restriction.setPropertyName(propertyName);
		restriction.setMinValue(minValue);
		restriction.setMaxValue(maxValue);
		addItem(restriction);
	}

	public void addTimeRange (String propertyName, String minValue, String maxValue) {
		addTimeRange(propertyName, minValue, maxValue, "HH:mm");
	}
	
	public void addTimeRange (String propertyName, String minValue, String maxValue, String pattern) {
		Time _minValue = null;
		try {
			_minValue = toTime(minValue, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Time _maxValue = null;
		try {
			_maxValue = toTime(maxValue, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addTimeRange(propertyName, _minValue, _maxValue);
	}
	
//	public void addUnique (String propertyName, Object options__) {
//		UniqueRestriction restriction = new UniqueRestriction();
//		restriction.setPropertyName(propertyName);
//		// TODO IMPLEMENT ME!!!
//		restriction.setI18n(getI18n());
//		addItem(restriction);
//	}
	
	public void addUnique (String propertyNames, String entityNames, String pkName, String deletedName, Boolean deletedValue, String propertyRealNames) {
		UniqueRestriction restriction = new UniqueRestriction();
		restriction.setPropertyNames(propertyNames);
		restriction.setPropertyRealNames(propertyRealNames);
		restriction.setEntityNames(entityNames);
		restriction.setPkName(pkName);
		restriction.setDeletedName(deletedName);
		restriction.setDeletedValue(deletedValue);
		// TODO IMPLEMENT ME!!!
		addItem(restriction);
	}
	
	public void addUnique (String propertyNames, String entityNames, String pkName, String deletedName, Boolean deletedValue) {
		UniqueRestriction restriction = new UniqueRestriction();
		restriction.setPropertyNames(propertyNames);
		restriction.setPropertyRealNames(propertyNames);
		restriction.setEntityNames(entityNames);
		restriction.setPkName(pkName);
		restriction.setDeletedName(deletedName);
		restriction.setDeletedValue(deletedValue);
		addItem(restriction);
	}
	
	public void addUnique (String propertyNames, String entityNames, String pkName, String propertyRealNames) {
		UniqueRestriction restriction = new UniqueRestriction();
		restriction.setPropertyNames(propertyNames);
		restriction.setPropertyRealNames(propertyRealNames);
		restriction.setEntityNames(entityNames);
		restriction.setPkName(pkName);
		restriction.setDeletedName(null);
		restriction.setDeletedValue(null);
		addItem(restriction);
	}
	
	public void addUnique (String propertyNames, String entityNames, String pkName) {
		UniqueRestriction restriction = new UniqueRestriction();
		restriction.setPropertyNames(propertyNames);
		restriction.setPropertyRealNames(propertyNames);
		restriction.setEntityNames(entityNames);
		restriction.setPkName(pkName);
		restriction.setDeletedName(null);
		restriction.setDeletedValue(null);
		addItem(restriction);
	}
	
	public Restriction buildAnd () {
		AndRestriction result = null;
		result = new AndRestriction();
		result.setItems(getItems());
		return result;
	}
	
}
