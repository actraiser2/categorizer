package com.santalucia.categorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CategorizerApplication {

	public static void main(String... args) {
		new SpringApplicationBuilder().
			sources(CategorizerApplication.class).
			logStartupInfo(true).build().
			run(args);
	}
}
