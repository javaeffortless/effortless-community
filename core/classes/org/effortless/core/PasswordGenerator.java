package org.effortless.core;

import java.util.Random;

import org.effortless.core.gpw.Gpw;

public class PasswordGenerator {

	public static String generate () {
		return generate(8);
	}
	
	public static String generate (int length) {
		String result = null;
		result = Gpw.generate(length);
		return result;
	}

	public static String generateStrong () {
		return generateStrong(11);
	}
	
	protected static final Random RANDOM = new Random(System.currentTimeMillis());
	
	public static String generateStrong (int length) {
		String result = null;
		if (length > 3) {
			String number = ("" + (RANDOM.nextInt(999) + 100));
			number = (number.substring(number.length() - 2));
			int textLength = length - 3;
			String text = Gpw.generate(textLength);
			text = text.toLowerCase();
			int numUpper = Math.min(textLength, 2);
			for (int i = 0; i < numUpper; i++) {
				int idxText = RANDOM.nextInt(textLength);
				text = text.substring(0, idxText) + text.substring(idxText, idxText + 1).toUpperCase() + text.substring(idxText + 1);
			}
			int idxSymbol = RANDOM.nextInt(BASIC_SYMBOLS.length());
			String symbol = "" + BASIC_SYMBOLS.charAt(idxSymbol);
			result = text + number + symbol;
		}
		else {
			result = generate(length);
		}
		return result;
	}
	
	protected static final String SYMBOLS = "!\"·$%&/()=?¿ªº\\|@#~½¬{[]}^*`+´ç;,:._-";

	protected static final String BASIC_SYMBOLS = "!$%=?@#*+.-";
	
	
}
