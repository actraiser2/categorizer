package com.santalucia.categorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CategorizerApplication {

	public static void main(String... args) {
		SpringApplication.run(CategorizerApplication.class, args);
	}
}
