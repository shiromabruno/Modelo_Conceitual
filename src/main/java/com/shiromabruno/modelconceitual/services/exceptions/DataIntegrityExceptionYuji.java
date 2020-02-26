package com.shiromabruno.modelconceitual.services.exceptions;

public class DataIntegrityExceptionYuji extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityExceptionYuji(String msg) {
		super(msg);
	}
	
	public DataIntegrityExceptionYuji(String msg, Throwable cause) {
		super(msg, cause);
	}

}
