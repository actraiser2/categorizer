package com.santalucia.categorizer.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.santalucia.categorizer.domain.model.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {

	@Query("from MovementEntity where id=:movementId")
	public MovementEntity findByMovementId(UUID movementId);
}
