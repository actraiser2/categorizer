package com.santalucia.categorizer.application.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;
import com.santalucia.categorizer.application.services.CategorizationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategorizationServiceImpl implements CategorizationService {

	@Autowired Environment env;
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
