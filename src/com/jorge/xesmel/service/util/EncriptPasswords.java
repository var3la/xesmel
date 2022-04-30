package com.jorge.xesmel.service.util;

public class EncriptPasswords {
	
	public static void main(String[]args) {
		
		String password = "chantada";
		
		password = PasswordEncryptionUtil.encryptPassword(password);
		
		System.out.println(password);
	}
	
}
