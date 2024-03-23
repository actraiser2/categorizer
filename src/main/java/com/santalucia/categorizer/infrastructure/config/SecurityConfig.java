package com.santalucia.categorizer.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity  httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(requests -> 
			requests.
				anyRequest().
				hasAuthority("SCOPE_movements:categorize")
				
		   ).
			oauth2ResourceServer(o -> o.jwt()).
			csrf(c -> c.disable()).
			cors(c -> c.disable()).
			build();
		
	
	}
	
	
}
