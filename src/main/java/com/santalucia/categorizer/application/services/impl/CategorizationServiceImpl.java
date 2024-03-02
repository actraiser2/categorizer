package com.santalucia.categorizer.application.services.impl;

import org.springframework.stereotype.Service;

import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;
import com.santalucia.categorizer.application.services.CategorizationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategorizationServiceImpl implements CategorizationService {

	@Override
	public MovementCategorizedResource categorize(MovementResource movementResource) {
		// TODO Auto-generated method stub
		log.info("Request categorization " + movementResource);
		
		var response = new MovementCategorizedResource();
		response.setCategory(10);
		response.setCategoryName("Alimentacion");
		return response;
	}

}