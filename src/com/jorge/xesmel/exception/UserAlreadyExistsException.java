package com.jorge.xesmel.exception;

public class UserAlreadyExistsException extends ServiceException{
	
		public UserAlreadyExistsException(String email) {
			super(email);
		}
	
}
