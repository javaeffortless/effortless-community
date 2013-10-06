package org.effortless.model.restrictions;

import java.util.regex.Pattern;

import org.effortless.core.ObjectUtils;

public class CccValidator extends Object {

	protected CccValidator () {
		super();
	}

	public static String removeSomeChars (String ccc) {
		String result = null;
		result = (ccc != null ? ccc.replaceAll("-", "").replaceAll(" ", "") : result);
		return result;
	}
	
	public static boolean check (String ccc) {
		boolean result = false;
		String[] arrayCcc = split(ccc);
		result = (arrayCcc != null && arrayCcc.length == 4 ? check(arrayCcc[0], arrayCcc[1], arrayCcc[2], arrayCcc[3]) : false);
		return result;
	}
	
	public static String[] split (String ccc) {
		String[] result = null;
		if (check(ccc, REGEX_CCC)) {
			result = new String[4];
			result[0] = ccc.substring(0, 4);
			result[1] = ccc.substring(4, 8);
			result[2] = ccc.substring(8, 10);
			result[3] = ccc.substring(10, 20);
		}
		return result;
	}
	
	public static boolean check (String entidad, String oficina, String dc, String cuenta) {
		boolean result = false;
		String ccc = merge(entidad, oficina, dc, cuenta);
		if (check(ccc, REGEX_CCC)) {
			String dcCalculado = calcularDc(entidad, oficina, cuenta);
			result = ObjectUtils.equals(dc, dcCalculado);
		}
		return result;
	}
	
	public static String merge (String entidad, String oficina, String dc, String cuenta) {
		String result = "";
		result += (entidad != null ? entidad : "");
		result += (oficina != null ? oficina : "");
		result += (dc != null ? dc : "");
		result += (cuenta != null ? cuenta : "");
		return result;
	}
	
	protected static boolean check (String value, Pattern pattern) {
		return pattern.matcher(value).matches();
	}
	
//	protected static final Pattern REGEX_ENTIDAD_OFICINA = Pattern.compile("[0-9]{4,4}");
//	protected static final Pattern REGEX_CUENTA = Pattern.compile("[0-9]{10,10}");
	protected static final Pattern REGEX_CCC = Pattern.compile("[0-9]{20,20}");
	protected static final Pattern REGEX_ENTIDAD_OFICINA_CUENTA = Pattern.compile("[0-9]{18,18}");
	
	public static String calcularDc (String entidad, String oficina, String cuenta) {
		String result = null;
		String eoc = merge(entidad, oficina, null, cuenta);
		if (check(eoc, REGEX_ENTIDAD_OFICINA_CUENTA)) {
			String eo = merge(entidad, oficina, null, null);
			int dc1 = calcularDc(eo);
			int dc2 = calcularDc(cuenta);
			result = ("" + dc1) + ("" + dc2);
		}
		return result;
	}
	
	protected static final int[] FACTORS = {6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
	protected static final int CTE = 11;
	
	protected static int calcularDc (String value) {
		int result = 0;
		int length = (value != null ? value.length() : 0);
		int factorIdx = 0;
		int suma = 0;
		for (int i = length - 1; i >= 0; i--) {
			int dato = Integer.valueOf(value.substring(i, i + 1)).intValue();
			suma += FACTORS[factorIdx++] * dato;
		}
		int diff = CTE - (suma % CTE);
		result = (diff == 11 ? 0 : diff);
		result = (diff == 10 ? 1 : diff);
		return result;
	}
	
}
