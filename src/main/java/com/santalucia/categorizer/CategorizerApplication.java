package com.santalucia.categorizer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import com.santalucia.categorizer.infrastructure.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableRetry
@Slf4j
public class CategorizerApplication {

	public static void main(String... args) {
		new SpringApplicationBuilder().
			sources(CategorizerApplication.class).
			logStartupInfo(true).build().
			run(args);
	}
	
	@Bean
	ApplicationListener<ApplicationReadyEvent> ready(UserRepository userRepository){
		return event -> {
			log.info("AppliucationRadyEvent: {}", 
					event.getSpringApplication().getMainApplicationClass());
			log.info("Users loaded: {}", userRepository.findAll());
		};
	}
}
