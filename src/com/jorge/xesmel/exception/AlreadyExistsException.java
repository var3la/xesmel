package com.jorge.xesmel.exception;

public class AlreadyExistsException extends ServiceException{
	
	public AlreadyExistsException(String codEnApiario) {
		
			super(codEnApiario);
	}

}
