package com.santalucia.categorizer.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	private String id;
	
	@NotBlank
	private String email;
	
	@NotBlank(message = "The name can not b empty")
	private String name;
	
	
	private Boolean active;
	
	@CreatedDate
	private LocalDateTime timestamp;
	
	@Singular
	private List<String> addresses;
	
}
