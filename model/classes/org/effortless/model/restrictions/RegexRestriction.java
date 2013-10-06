package org.effortless.model.restrictions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRestriction extends PropertyRestriction {

	public RegexRestriction () {
		super();
	}
	
	protected void initiate () {
		initiatePattern();
		init$Pattern$();
	}
	
	protected String pattern;
	
	protected void initiatePattern () {
		this.pattern = null;
	}
	
	public String getPattern () {
		return this.pattern;
	}
	
	public void setPattern (String newValue) {
		this.pattern = newValue;
		set$Pattern$(null);
	}

	protected transient Pattern $pattern$;
	
	protected void init$Pattern$ () {
		this.$pattern$ = null;
	}
	
	protected Pattern get$Pattern$ () {
		return this.$pattern$;
	}
	
	protected void set$Pattern$ (Pattern newValue) {
		this.$pattern$ = newValue;
	}
	
	protected Pattern doGet$Pattern$ () {
		if (this.$pattern$ == null) {
			this.$pattern$ = Pattern.compile(getPattern());
		}
		return this.$pattern$;
	}
	
	protected void doCheck (Object data, Object property) {
		if (!(property instanceof String)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			String value = (String)property;
			if (value != null) {
				String patternRegex = getPattern();
				if (patternRegex != null) {
					Pattern pattern = doGet$Pattern$();
					Matcher matcher = pattern.matcher(value);
					if (!matcher.matches()) {
						throwError(data, property, "regex");
					}
				}
			}
		}
	}
	
}
