package com.santalucia.categorizer.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(requests -> 
			requests.
				anyRequest().
				hasAuthority("SCOPE_movements:categorize")
				
		   ).
			oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt).
			csrf(c -> c.disable()).
			cors(c -> c.disable()).
			build();
		
	
	}
	
	
}
