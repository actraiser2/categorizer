package com.santalucia.categorizer.infrastructure.errors;


import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorsHandler {

	@ExceptionHandler(Exception.class)
	ResponseEntity<Error> handlerException(HttpServletRequest request, 
			Exception ex, Locale local){
		//log.error("Error: {}", ex);
		var error = Error.builder().url(request.getServletPath()).
			errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).
			status(HttpStatus.INTERNAL_SERVER_ERROR.value()).
			message(ex.getMessage()).
			reqMethod(request.getMethod()).build();
		
		return ResponseEntity.internalServerError().body(error);
		
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	ResponseEntity<Error> handlerException(HttpServletRequest request, 
			AccessDeniedException ex, Locale local){
		
		var error = Error.builder().url(request.getServletPath()).
			errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).
			status(HttpStatus.FORBIDDEN.value()).
			message(ex.getMessage()).
			reqMethod(request.getMethod()).build();
		
		return ResponseEntity.status(403).body(error);
		
	}
}
