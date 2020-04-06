package com.shiromabruno.modelconceitual.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.shiromabruno.modelconceitual.resources.exception.StandardError;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	// Essa list de errors nao tem no StandardError (classe Pai), mas sera retornada no Body
	private List<FieldMessage> errors = new ArrayList<>();
	
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}


	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
	
	
	
	
}
