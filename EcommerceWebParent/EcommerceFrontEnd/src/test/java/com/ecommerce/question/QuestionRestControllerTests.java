package com.ecommerce.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ecommerce.common.entity.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionRestControllerTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	
	@Test
	@WithMockUser(username = "usernmae", password = "password")
	public void testPostQuestion() throws JsonProcessingException, Exception {
		String requestURL = "/questions/post_question";
		Question question = new Question();
		question.setQuestionContent("Question Content");
		question.setAskTime(new Date());
		
		MvcResult result = mockMvc.perform(post(requestURL).contentType("application/json")
				.content(objectMapper.writeValueAsString(question)).with(csrf()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		
		assertThat(jsonResponse).isEqualTo("Your question has been posted and awaiting for approval.");
	}
}
