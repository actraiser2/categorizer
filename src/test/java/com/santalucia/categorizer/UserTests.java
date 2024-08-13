package com.santalucia.categorizer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.santalucia.categorizer.domain.model.User;
import com.santalucia.categorizer.infrastructure.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testing users")
public class UserTests {

	@Autowired MockMvc mockMvc;
	@MockBean UserRepository userRepository;
	@Autowired JacksonTester<User> userJacksonTester;
	
	private String basePath = "/users";
	
	@Test
	@DisplayName("Search Users")
	 void testSearchUsers() throws Exception {
		var users = List.of(
				User.builder().active(true).
					address("Avenida La Leala").
					address("Calle Barrilero").
					email("jmbesada.juez@gmail.es").
					name("Jose").build());

		
		Mockito.when(userRepository.searchUsersByAddress(Mockito.any())).
			thenReturn(users);
		
		mockMvc.perform(
				MockMvcRequestBuilders.
				get(basePath + "/search").
				param("address", "barrilero").
				contentType(MediaType.APPLICATION_JSON)).
		andExpect(MockMvcResultMatchers.header().
				string("Content-Type", MediaType.APPLICATION_JSON.toString())).
		andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
		andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))).
		andExpect(MockMvcResultMatchers.jsonPath("$[0].addresses", Matchers.hasSize(2)));
	}
	
	@Test
	@DisplayName("Create User")
	void createUserTest() throws IOException, Exception {
		var user = User.builder().
			active(true).
			address("Avenida La Leala").
			address("Calle Barrilero").
			email("jmbesada.juez@gmail.es").
			name("Jose").build();
		
		Mockito.when(userRepository.insert(Mockito.any(User.class))).thenReturn(user);
		user.setId("1");
		
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/").
				contentType(MediaType.APPLICATION_JSON).
				content(userJacksonTester.write(user).getJson())).
				andExpect(MockMvcResultMatchers.status().isCreated()).
				andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/users/**")).
				andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		
		Mockito.verify(userRepository,Mockito.times(1)).
			insert(Mockito.any(User.class))	;
	}
}
