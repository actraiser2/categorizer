package com.santalucia.categorizer.infrastructure.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santalucia.categorizer.application.api.MovementsApi;
import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;
import com.santalucia.categorizer.application.services.CategorizationService;
import com.santalucia.categorizer.infrastructure.mappers.MovementMapper;
import com.santalucia.categorizer.infrastructure.repository.MovementRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping(path = "${openapi.categorizer.base-path}")
@RequiredArgsConstructor
public class MovementsApiController implements MovementsApi {

	private final CategorizationService categorizationService;
	private final MovementRepository movementRepository;
	private final MovementMapper movementMapper;

	@Override
	public ResponseEntity<MovementCategorizedResource> categorizeMovement(
			@Valid MovementResource movementResource) {

		var authentication = (JwtAuthenticationToken)SecurityContextHolder.
				getContext().getAuthentication();
		log.info("Authentication: {}", authentication.getTokenAttributes());
		log.info("Categorization request {}", movementResource);
		return ResponseEntity.ok(categorizationService.categorize(movementResource));
	}

	@Override
	public ResponseEntity<List<MovementResource>> findAllMovements() {
		
		var movements = movementRepository.findAll();
		
		return ResponseEntity.ok(movementMapper.toListMovementResource(movements));
	}

	

	

	

}
