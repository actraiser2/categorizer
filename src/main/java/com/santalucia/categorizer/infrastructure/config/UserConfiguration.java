package com.santalucia.categorizer.infrastructure.config;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import com.santalucia.categorizer.domain.model.User;

@Component
public class UserConfiguration implements BeforeConvertCallback<User> {

	@Override
	public User onBeforeConvert(User entity, String collection) {
		entity.setTimestamp(LocalDateTime.now());
		return entity;
	}

}
