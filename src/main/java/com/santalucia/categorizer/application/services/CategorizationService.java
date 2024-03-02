package com.santalucia.categorizer.application.services;

import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;

public interface CategorizationService {

	public MovementCategorizedResource categorize(MovementResource movementResource);
}
