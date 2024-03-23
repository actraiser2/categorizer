package com.santalucia.categorizer.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORIZED_MOVEMENTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorizedMovementEntity {

	@Id
	private UUID id;
	
	private String categoryName;
	private Long categoryId;
}
