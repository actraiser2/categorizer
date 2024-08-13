package com.santalucia.categorizer.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.santalucia.categorizer.domain.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findUserByEmail(String email);
	
	@Query("{'active':?0, 'email':?1}")
	public List<User> findUsersByActive(Boolean active, String email);
	
	@Query("{'addresses':{$in:[/?0/]}}")
	public List<User> searchUsersByAddress(String address);
	
	
}
