package com.jorge.xesmel.exception;

public class DataException extends ServiceException{
	
	
		public DataException(String message) {
			super(message);
		}
		

		public DataException(String message, Throwable cause) {
			super(message, cause);
		}

		public DataException(Throwable e) {
			super(e);
		}

		 
}
