package com.santalucia.categorizer.infrastructure.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(originPatterns = "*")
public class MovementsApiController implements MovementsApi {

	private final CategorizationService categorizationService;
	private final MovementRepository movementRepository;
	private final MovementMapper movementMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<MovementCategorizedResource> categorizeMovement(
			@Valid MovementResource movementResource) {

		var authentication = (JwtAuthenticationToken)SecurityContextHolder.
				getContext().getAuthentication();
		
		if (authentication != null) {
			log.info("Authentication: {}", authentication.getTokenAttributes());
			
			log.info("Authorities: {}", authentication.getAuthorities());
			log.info("Categorization request {}", movementResource);
		}
		
		return ResponseEntity.ok(categorizationService.categorize(movementResource));
	}

	@Override
	public ResponseEntity<List<MovementResource>> findAllMovements() {
		
		var movements = movementRepository.findAll();
		
		
		return ResponseEntity.ok(movementMapper.toListMovementResource(movements));
	}

	@Override
	public ResponseEntity<MovementResource> findByMovementId(String movementId) {
		
		log.info("MovementId hashed: {}", passwordEncoder.encode(movementId));
		
		var movement = movementMapper.toMovementResource(
				movementRepository.findByMovementId(UUID.fromString(movementId)));
				
		return ResponseEntity.of(Optional.ofNullable(movement));
	}

	

	

	

}
