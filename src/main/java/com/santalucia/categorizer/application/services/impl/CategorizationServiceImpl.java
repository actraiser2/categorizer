package com.santalucia.categorizer.application.services.impl;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;
import com.santalucia.categorizer.application.services.CategorizationService;
import com.santalucia.categorizer.domain.model.CategorizedMovementEntity;
import com.santalucia.categorizer.domain.model.MovementEntity;
import com.santalucia.categorizer.infrastructure.repository.MovementRepository;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CategorizationServiceImpl implements CategorizationService {

	private final MovementRepository movementRepository;
	
	@Override
	@PreAuthorize("hasAuthority('SCOPE_movements:categorize')")
	public MovementCategorizedResource categorize(MovementResource movement) {
		log.info("Request categorization " + movement);
		
		var response = new MovementCategorizedResource();
		response.setCategory(10);
		response.setCategoryName("Alimentacion");
		
		var categorizedMovement = CategorizedMovementEntity.builder().
			categoryId(10l).
			categoryName("Alimentacion").
			id(UUID.randomUUID()).
			build();
		
		var entity = MovementEntity.builder().
			amount(movement.getAmount().doubleValue()).
			concept(movement.getConcept()).
			institution(movement.getInstitution()).
			operationDate(movement.getOperationDate()).
			//id(UUID.randomUUID()).
			categorizedMovement(categorizedMovement).
			build();
		
		movementRepository.save(entity);
		
		return response;
	}

}
