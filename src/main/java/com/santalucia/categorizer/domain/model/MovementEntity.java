package com.santalucia.categorizer.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MOVEMENTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {

	@Id
	private UUID id;
	
	private String concept;
	private Double amount;
	private Integer institution;
	private LocalDate operationDate;
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "CATEGORIZED_MOVEMENT")
	private CategorizedMovementEntity categorizedMovement;
}
