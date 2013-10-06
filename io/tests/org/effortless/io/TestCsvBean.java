package org.effortless.io;

import java.util.ArrayList;
import java.util.List;

import org.effortless.io.decoders.StandardDecoders;

public class TestCsvBean {

	public TestCsvBean () {
		super();
	}
	
	protected Integer id;
	
	public Integer getId () {
		return this.id;
	}
	
	public void setId (Integer newValue) {
		this.id = newValue;
	}
	
	protected String name;
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		this.name = newValue;
	}
	
	protected java.util.Date date;
	
	public java.util.Date getDate () {
		return this.date;
	}
	
	public void setDate (java.util.Date newValue) {
		this.date = newValue;
	}
	
	protected Boolean enabled;
	
	public Boolean getEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (Boolean newValue) {
		this.enabled = newValue;
	}
	
	protected Integer amount;
	
	public Integer getAmount () {
		return this.amount;
	}
	
	public void setAmount (Integer newValue) {
		this.amount = newValue;
	}
	
	protected Double price;
	
	public Double getPrice () {
		return this.price;
	}
	
	public void setPrice (Double newValue) {
		this.price = newValue;
	}

	public static List<TestCsvBean> importCsv (String csv) {
		List<TestCsvBean> result = null;
		result = new ArrayList<TestCsvBean>();
		
		String splitLine = "\n";
		String[] linesCsv = (csv != null ? csv.split(splitLine) : null);
		int numLines = (linesCsv != null ? linesCsv.length : 0);
		if (numLines > 0) {
			String separator = null;
			separator = (separator != null ? separator : ",");
			String[] fields = null;
			fields = (fields != null ? fields : new String[]{"id","name","date","enabled","amount","price"});
			for (int i = 0; i < numLines; i++) {
				String lineCsv = linesCsv[i];
				TestCsvBean item = fromCsvLine(lineCsv, separator, fields);
				if (item != null) {
					result.add(item);
				}
			}
		}
		
		return result;
	}
	
	protected static TestCsvBean fromCsvLine (String lineCsv, String separator, String[] fields) {
		TestCsvBean result = null;
		result = new TestCsvBean();
		String[] variables = lineCsv.split(separator);
		int variablesLength = (variables != null ? variables.length : 0);
		int fieldsLength = (fields != null ? fields.length : 0);
		int length = Math.min(variablesLength, fieldsLength);
		if (length > 0) {
			result = new TestCsvBean();
			for (int i = 0; i < length; i++) {
				String field = fields[i];
				if (field != null) {
					String value = variables[i];
					if ("amount".equals(field)) {
						result.amount = StandardDecoders.INTEGER.decode(value);
					}
					else if ("date".equals(field)) {
						result.date = StandardDecoders.DATE.decode(value);
					}
					else if ("enabled".equals(field)) {
						result.enabled = StandardDecoders.BOOLEAN.decode(value);
					}
					else if ("id".equals(field)) {
						result.id = StandardDecoders.INTEGER.decode(value);
					}
					else if ("name".equals(field)) {
						result.name = StandardDecoders.STRING.decode(value);
					}
					else if ("price".equals(field)) {
						result.price = StandardDecoders.DOUBLE.decode(value);
					}
				}
			}
		}
		return result;
	}
	
}
