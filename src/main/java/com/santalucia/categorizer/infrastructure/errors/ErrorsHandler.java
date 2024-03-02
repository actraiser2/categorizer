package com.santalucia.categorizer.infrastructure.errors;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorsHandler {

	@ExceptionHandler(Exception.class)
	ResponseEntity<Error> handlerException(HttpServletRequest request, 
			Exception ex, Locale local){
		
		var error = Error.builder().url(request.getRequestURI()).
			errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).
			status(HttpStatus.INTERNAL_SERVER_ERROR.value()).
			message(ex.getMessage()).
			reqMethod(request.getMethod()).build();
		
		return ResponseEntity.internalServerError().body(error);
		
	}
}
