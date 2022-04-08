package com.jorge.xesmel.exception;

public class XesmelException extends Exception{
	
	public XesmelException(){
		
		super();
	}
	public XesmelException(String message) {
		super(message);
	}
	
	
	public XesmelException(Throwable cause) {
		super(cause);
	}
	
	
	public XesmelException(String message, Throwable cause) {
		super(message, cause);
	}

}
