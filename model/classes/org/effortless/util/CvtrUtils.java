package org.effortless.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.effortless.model.IFile;
import org.effortless.model.Referenciable;

public class CvtrUtils extends Object {

	public CvtrUtils () {
		super();
		initiate();
	}
	
	public CvtrUtils (String language) {
		this();
		setLanguage(language);
	}
	
	protected void initiate () {
		initiateLanguage();
	}
	
	protected String language;
	
	protected void initiateLanguage () {
		this.language = null;
	}
	
	public String getLanguage () {
		return this.language;
	}
	
	public void setLanguage (String newValue) {
		this.language = newValue;
	}
	
	protected static CvtrUtils instance;
	
	public static CvtrUtils getInstance () {
		if (CvtrUtils.instance == null) {
			CvtrUtils.instance = new CvtrUtils();
		}
		return CvtrUtils.instance;
	}
	
	public String toStringUi(Object value) {
		String result = null;
		result = toText(value);
		result = (result != null ? result : "");
		return result;
	}

	public String toText(Object value) {
		String result = null;
		if (value instanceof String) {
			result = toString((String)value);
		}
		else if (value instanceof Boolean) {
			result = toString((Boolean)value);
		}
		else if (value instanceof Integer) {
			result = toString((Integer)value);
		}
		else if (value instanceof Date) {
			if (value instanceof Timestamp) {
				result = toString((Timestamp)value);
			}
			else if (value instanceof Time) {
				result = toString((Time)value);
			}
			else {
				result = toString((Date)value);
			}
		}
		else if (value instanceof Enum) {
			result = toString((Enum)value);
		}
		else if (value instanceof Double) {
			result = toString((Double)value);
		}
		else if (value instanceof Referenciable) {
			result = toString((Referenciable)value);
		}
		else if (value instanceof IFile) {
			result = toString((IFile)value);
		}
		else {
			result = toString(value);
		}
		return result;
	}
	
	
	public String toString (String value) {
		String result = null;
		result = value;
		return result;
	}
	
	public String toString (Boolean value) {
		String result = null;
		result = (value != null ? (value.booleanValue() ? "Sí" : "No" ) : null);
		return result;
	}
	
	public String toString (Integer value) {
		String result = null;
		if (value != null) {
			NumberFormat nf = NumberFormat.getInstance();
		    nf.setMaximumFractionDigits(0); 
		    nf.setMinimumFractionDigits(0);
		    nf.setParseIntegerOnly(true);
		    nf.setGroupingUsed(false);
		    result = nf.format(value);
		}
		return result;
	}
	
	public String toString (Date value) {
		String result = null;
		if (value != null) {
//			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			result = formatter.format(value);
		}
		return result;
	}
	
	public String toString (Timestamp value) {
		String result = null;
		if (value != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			result = formatter.format(value);
		}
		return result;
	}

	public String date2String (Date value) {
		String result = null;
		if (value != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			result = formatter.format(value);
		}
		return result;
	}
	
	public String toString (Time value) {
		String result = null;
		if (value != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			result = formatter.format(value);
		}
		return result;
	}
	
	public Time toTime (String value) {
		Time result = null;
		if (value != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			Date date;
			try {
				date = formatter.parse(value);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			result = (date != null ? new Time(date.getTime()) : null);
		}
		return result;
	}
	
	public String toString (Enum value) {
		String result = null;
		if (value != null) {
			String language = doGetLanguage();
			result = value.name();
//			result = (language != null ? value.toLabel(language) : value.toLabel());
		}
		return result;
	}

	protected String doGetLanguage () {
		return this.language;
	}
	
	public String toString (Double value) {
		String result = null;
		if (value != null) {
			NumberFormat nf = NumberFormat.getInstance();
		    nf.setMaximumFractionDigits(6); 
		    nf.setMinimumFractionDigits(2);
		    result = nf.format(value);
		}
		return result;
	}
	
	public String toString (Referenciable value) {
		String result = null;
		result = (value != null ? value.toLabel() : null);
		return result;
	}
	
	public String toString (IFile value) {
		String result = null;
		result = (value != null ? value.getName() : null);
		return result;
	}
	
	public String toString (Object value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}
	
}
