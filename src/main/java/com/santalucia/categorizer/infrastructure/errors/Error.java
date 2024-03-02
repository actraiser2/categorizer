package com.santalucia.categorizer.infrastructure.errors;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Error {

	private Integer errorCode;
	private String message;
	private Integer status;
	private String url;
	private String reqMethod;
}
