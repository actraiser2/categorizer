package com.santalucia.categorizer.infrastructure.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.santalucia.categorizer.application.api.model.MovementResource;
import com.santalucia.categorizer.domain.model.MovementEntity;

@Component
@Mapper(componentModel = "spring")
public interface MovementMapper {

	List<MovementResource> toListMovementResource(List<MovementEntity> movementList);
}
