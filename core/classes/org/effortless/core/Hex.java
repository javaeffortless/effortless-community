package org.effortless.core;

//import org.apache.commons.codec.binary.Hex;

public class Hex {

	public static String toHex (byte[] bytes) {
		String result = null;
		result = String.valueOf(org.apache.commons.codec.binary.Hex.encodeHex(bytes));
		return result;
	}
	
}
