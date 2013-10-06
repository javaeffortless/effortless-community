package org.effortless.util;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.effortless.model.Referenciable;

public class ToLabel extends Object {

	public ToLabel () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateSeparator();
		initiateText();
	}
	
	public ToLabel (String separator) {
		this();
		setSeparator((separator != null ? separator : DEFAULT_SEPARATOR));
	}
	
	protected String separator;
	
	protected void initiateSeparator () {
		this.separator = DEFAULT_SEPARATOR;
	}
	
	public String getSeparator () {
		return this.separator;
	}
	
	public void setSeparator (String newValue) {
		this.separator = newValue;
	}
	
	protected String text;
	
	protected void initiateText () {
		this.text = null;
	}
	
	public String getText () {
		return this.text;
	}
	
	protected void setText (String newValue) {
		this.text = newValue;
	}
	
	protected String doGetSeparator () {
		return (this.separator != null ? this.separator : "");
	}
	
	public static final String DEFAULT_SEPARATOR = " - ";
	
	public String toLabel (String value) {
		return value;
	}

	public String toLabel (Date value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Integer value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Boolean value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Double value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Time value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Timestamp value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Enum<?> value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (File value) {
		String result = null;
		result = (value != null ? value.toString() : null);
		return result;
	}

	public String toLabel (Referenciable value) {
		String result = null;
		result = (value != null ? value.toLabel() : null);
		return result;
	}
	
	public String add (Date value) {
		return add(toLabel(value));
	}
	
	public String add (Integer value) {
		return add(toLabel(value));
	}
	
	public String add (Boolean value) {
		return add(toLabel(value));
	}
	
	public String add (Double value) {
		return add(toLabel(value));
	}
	
	public String add (Time value) {
		return add(toLabel(value));
	}
	
	public String add (Timestamp value) {
		return add(toLabel(value));
	}
	
	public String add (Enum<?> value) {
		return add(toLabel(value));
	}
	
	public String add (File value) {
		return add(toLabel(value));
	}
	
	public String add (Referenciable value) {
		return add(toLabel(value));
	}
	
	public String add (String value) {
		String result = null;
		this.text = (this.text != null ? this.text : "");
		value = (value != null ? value : "");
		this.text = this.text + (this.text.length() > 0 && value.length() > 0 ? doGetSeparator() : "") + value;
		result = text;
		return result;
	}

}
