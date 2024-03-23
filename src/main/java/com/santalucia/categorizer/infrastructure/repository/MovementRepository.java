package com.santalucia.categorizer.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santalucia.categorizer.domain.model.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {

}
