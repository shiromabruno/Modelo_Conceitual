package com.shiromabruno.modelconceitual.services.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Essa notacao deve ficar em 'standby' esperando acontecer erro no CONTROLLER
@ControllerAdvice
public class ResourceExceptionHandler {
	
	// padrao do controle advice. Tem q ter essa assinatura
	//essa notacao significa que eh um tratador de exceções do tipo ObjectNotFoundException.class
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	// 
	@ExceptionHandler(DataIntegrityExceptionYuji.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityExceptionYuji e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	// essa excecao eh espeifica do Controller (?) as 2 de cima sao do Services
	// Qualuer erro
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		// esse erro de Validacao aparece no retorno
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validacao ResourceExceptionHandler.class", System.currentTimeMillis());
		// com esse comando, acessa todos os erros de campos que ocorreram nessa excecao MethodArgumentNotValidException
		// cada tipo FieldError X, nessa lista 'e.getBindingResult().getFieldErrors()' , vai fazer: corpo do FOR
		// essa Lista de Errors esta na classe do FieldError e nao no StandardError, mas sem problemas pois FieldError eh filha do StandardError
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	

}
