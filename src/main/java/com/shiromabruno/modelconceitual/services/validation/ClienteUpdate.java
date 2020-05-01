package com.shiromabruno.modelconceitual.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ClienteUpdateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)

//Esse ClienteInsert passara a ser a nossa nova notacao
//Tambem tera que criar o Validator para programar as caracteristicas da notacao
public @interface ClienteUpdate {
	String message() default "Erro de validação - ClienteUpdate.class";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
