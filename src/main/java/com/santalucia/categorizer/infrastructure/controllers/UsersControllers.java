package com.santalucia.categorizer.infrastructure.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.santalucia.categorizer.domain.model.User;
import com.santalucia.categorizer.infrastructure.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
@Slf4j
public class UsersControllers {

	private final MongoTemplate mongoTemplate;
	private final UserRepository userRepository;
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<User	> createUser(@RequestBody @Valid User user) {
		log.info("Insert user {}", user);
		var userCreated = userRepository.insert(user);
		
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/" + userCreated.getId()).build();
		
		log.info("Uri: {}", uri);
		
		return ResponseEntity.created(uri.toUri()).body(userCreated);
		
		
	}
	
	@DeleteMapping("/{email}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteUser(@PathVariable String email) {
		var user = userRepository.findUserByEmail(email);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
		
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<User> getUsers(@RequestParam(defaultValue = "true")
		boolean active, @RequestParam String email){
		return userRepository.findUsersByActive(active, email);
	}
	
	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public List<User> searchUsers(@RequestParam String address){
		log.info("searchUsers for address {}", address);
		return userRepository.searchUsersByAddress(address);
	}
	
}
