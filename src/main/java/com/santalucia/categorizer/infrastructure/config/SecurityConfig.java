package com.santalucia.categorizer.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Profile("!test")
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity  httpSecurity) throws Exception {
		return httpSecurity.
			httpBasic(c -> c.disable()).
			formLogin(c -> c.disable()).
			headers(c -> c.frameOptions(c2 -> c2.sameOrigin())).
			authorizeHttpRequests(requests -> 
				requests.
				    requestMatchers(new AntPathRequestMatcher("/management/**")).
				    	permitAll().
					anyRequest().
						hasAuthority("ROLE_movements:categorize")
				
		   ).
			oauth2ResourceServer(o -> 
				o.jwt(c -> c.jwtAuthenticationConverter(getJwtAuthenticationConverter()))).
			csrf(c -> c.ignoringRequestMatchers(new AntPathRequestMatcher("/ggategorizer/v1/**"))).
			cors(c -> c.disable()).
			build();
		
	
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {

	  JwtGrantedAuthoritiesConverter rolesConverter = new JwtGrantedAuthoritiesConverter();
	  rolesConverter.setAuthorityPrefix("ROLE_");
	  rolesConverter.setAuthoritiesClaimName("realm_access.roles");

	  
	  JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
	  scopesConverter.setAuthorityPrefix("ROLE_");
	  scopesConverter.setAuthoritiesClaimName("scope");

	  JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
	  //converter.setJwtGrantedAuthoritiesConverter(rolesConverter);

	  converter.setJwtGrantedAuthoritiesConverter(scopesConverter);

	  return converter;

	}
	
	
}
