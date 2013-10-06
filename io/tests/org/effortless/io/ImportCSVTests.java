package org.effortless.io;

import java.util.List;

public class ImportCSVTests {

	public static final String CSV = "\"id\",\"name\",\"date\",\"enabled\",\"amount\",\"price\"" + "\n" + "1,\"Juan, Dos\",22/08/2012,yes,-32,-43.24" + "\n" + "2,\"Mario, Tres\",23/08/2012,no,+32,+3.24";
	
	public static void main (String[] args) {
		List<TestCsvBean> list = TestCsvBean.importCsv(CSV);
		System.out.println(">>");
		
	}
	
}
