package com.santalucia.categorizer.infrastructure.controllers;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santalucia.categorizer.application.api.MovementsApi;
import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;
import com.santalucia.categorizer.application.exceptions.CategorizerException;
import com.santalucia.categorizer.application.services.CategorizationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping(path = "${openapi.categorizer.base-path}")
@RequiredArgsConstructor
public class MovementsApiController implements MovementsApi {

	private final CategorizationService categorizationService;

	@Override
	@CircuitBreaker(maxAttempts = 3, openTimeout = 10000, resetTimeout= 60000)
	public ResponseEntity<MovementCategorizedResource> categorizeMovement(@Valid MovementResource movement) {
		log.info("Request categorizeMovement");
		if (new Random().nextBoolean()) {
			return ResponseEntity.ok(categorizationService.categorize(movement));
		}
		else {
			throw new CategorizerException("Error categorizing");
		}
	}
	
	@Recover
	public ResponseEntity<MovementCategorizedResource> categorizeMovementRecover(
			Exception ex, @Valid MovementResource movement) {
		log.info("Executing the recover:" + ex.getMessage());
		return ResponseEntity.ok().build();

	}

	

}
