package com.ecommerce.admin.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.State;
import com.ecommerce.common.entity.StateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class StateRestControllerTests {

	@Autowired MockMvc mockMvc;
	
	@Autowired ObjectMapper objectMapper;
	
	@Autowired StateRepository repo;
	
	@Test
	@WithMockUser(username = "alx@yahoo.com", password = "sjkj2J2a", roles = "ADMIN")
	public void testListByCountry() throws Exception {
		Integer countryId = 1;
		String url = "/states/list_by_country/" + countryId;
		
		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		StateDTO[] states = objectMapper.readValue(jsonResponse, StateDTO[].class);
		
		assertThat(states).hasSizeGreaterThan(0);
	}
	
	@Test
	@WithMockUser(username = "alx@yahoo.com", password = "sjkj2J2a", roles = "ADMIN")
	public void testCreateState() throws JsonProcessingException, Exception {
		String url = "/states/save";
		String stateName = "Constanta";
		State state = new State(stateName , new Country(1));
		
		MvcResult result = mockMvc.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(state)).with(csrf()))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		String response = result.getResponse().getContentAsString();
		Integer stateId = Integer.parseInt(response);
		
		Optional<State> findById = repo.findById(stateId);
		State savedState = findById.get();
		
		assertThat(findById.isPresent());
		assertThat(savedState.getName()).isEqualTo(stateName);
	}
	
	@Test
	@WithMockUser(username = "alx@yahoo.com", password = "sjkj2J2a", roles = "ADMIN")
	public void testUpdateState() throws JsonProcessingException, Exception {
		String url = "/states/save";
		Integer stateId = 2;
		String stateName = "Timisoara";
		State state = new State(stateId, stateName , new Country(1));
		
		mockMvc.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(state)).with(csrf()))
				.andExpect(status().isOk())
				.andDo(print());
		
		Optional<State> findById = repo.findById(stateId);
		State savedState = findById.get();
		
		assertThat(findById.isPresent());
		assertThat(savedState.getName()).isEqualTo(stateName);
	}
	
	@Test
	@WithMockUser(username = "alx@yahoo.com", password = "sjkj2J2a", roles = "ADMIN")
	public void testDeleteState() throws Exception {
		Integer stateId = 1;
		String url = "/states/delete/" + stateId;
		
		mockMvc.perform(get(url)).andExpect(status().isOk());
		
		Optional<State> country = repo.findById(stateId);
		
		assertThat(country).isNotPresent();
	}
}











