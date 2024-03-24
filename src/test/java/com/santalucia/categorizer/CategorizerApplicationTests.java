package com.santalucia.categorizer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.santalucia.categorizer.application.api.model.MovementCategorizedResource;
import com.santalucia.categorizer.application.api.model.MovementResource;

@SpringBootTest()
@ActiveProfiles(profiles = "test")
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class CategorizerApplicationTests {

	@Autowired JacksonTester<MovementResource> movementJacksonTester;
	@Autowired JacksonTester<List<MovementResource>> movementListJacksonTester;
	@Autowired JacksonTester<MovementCategorizedResource> movementResourceJacksonTester;
	@Autowired MockMvc mockMvc;
	@Value("${openapi.categorizer.base-path}") String basePath;
	@MockBean PasswordEncoder passwordEncoder;
	
	@Test
	void testCategorize() throws IOException, Exception {
		String path = basePath + "/movements/categorize";
		
		var movement = new MovementResource();
		movement.setConcept("Prueba");
		movement.setOperationDate(LocalDate.now());
		movement.setInstitution(2);
		movement.setAmount(new BigDecimal(-30));
	
		var response = mockMvc.perform(MockMvcRequestBuilders.post(path).
				contentType("application/json").
				content(movementJacksonTester.write(movement).getJson())).
			andReturn().getResponse();
		
		var categorizedMovement = movementResourceJacksonTester.parse(
				response.getContentAsString());
		
		categorizedMovement.assertThat().extracting("category").isNotNull();
		Assertions.assertThat(response.getStatus()).isEqualTo(200);
	}
	
	@Test
	@Sql(scripts = "/sql/insert-movements.sql")
	void testFindMovements() throws Exception {
	
		String path = basePath + "/movements";
		
		var response = mockMvc.perform(MockMvcRequestBuilders.get(path).
				accept("application/json")).
				andReturn().
				getResponse();
		
		var movementList = movementListJacksonTester.
			parse(response.getContentAsString());
		
		System.out.println(movementList);
		
		movementList.assertThat().asList().hasSizeGreaterThan(5);
	}

}
