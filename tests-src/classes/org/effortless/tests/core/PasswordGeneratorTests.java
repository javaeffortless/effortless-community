package org.effortless.tests.core;

import org.effortless.core.PasswordGenerator;

public class PasswordGeneratorTests {

	public static void main (String[] args) {
		for (int i = 0; i < 10; i++) {
			String pass = PasswordGenerator.generateStrong();
			System.out.println("pass = " + pass);
		}
	}
	
}
