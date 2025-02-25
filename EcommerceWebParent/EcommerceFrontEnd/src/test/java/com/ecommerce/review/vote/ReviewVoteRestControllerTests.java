package com.ecommerce.review.vote;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ecommerce.common.entity.Review;
import com.ecommerce.review.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewVoteRestControllerTests {
	
	@Autowired private ReviewRepository reviewRepo;
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	
	@Test
	public void testVoteNotLogin() throws Exception {
		String requestURL = "/vote_review/1/up";
		
		MvcResult mvcResult = mockMvc.perform(post(requestURL).with(csrf()))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		VoteResult voteResult = objectMapper.readValue(json, VoteResult.class);
		
		assertThat(voteResult.isSuccessful()).isFalse();
		assertThat(voteResult.getMessage()).contains("You must login");
	}
	
	@Test
	@WithMockUser(username = "username", password = "password")
	public void testVoteNonExistReview() throws Exception {
		String requestURL = "/vote_review/100/up";
		
		MvcResult mvcResult = mockMvc.perform(post(requestURL).with(csrf()))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		VoteResult voteResult = objectMapper.readValue(json, VoteResult.class);
		
		assertThat(voteResult.isSuccessful()).isFalse();
		assertThat(voteResult.getMessage()).contains("no longer exists");
	}
	
	@Test
	@WithMockUser(username = "username", password = "password")
	public void testVoteUp() throws Exception {
		Integer reviewId = 10;
		String requestURL = "/vote_review/" + reviewId +"/up";
		
		Review review = reviewRepo.findById(reviewId).get();
		int voteCountBefore = review.getVotes();
		
		MvcResult mvcResult = mockMvc.perform(post(requestURL).with(csrf()))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		VoteResult voteResult = objectMapper.readValue(json, VoteResult.class);
		
		assertThat(voteResult.isSuccessful()).isTrue();
		assertThat(voteResult.getMessage()).contains("You have successfuly voted ");
		
		int voteCountAfter = voteResult.getVoteCount();
		assertThat(voteCountBefore + 1).isEqualTo(voteCountAfter);
	}
	
	@Test
	@WithMockUser(username = "username", password = "password")
	public void testUndoVoteUp() throws Exception {
		Integer reviewId = 10;
		String requestURL = "/vote_review/" + reviewId +"/up";
		
		Review review = reviewRepo.findById(reviewId).get();
		int voteCountBefore = review.getVotes();
		
		MvcResult mvcResult = mockMvc.perform(post(requestURL).with(csrf()))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		VoteResult voteResult = objectMapper.readValue(json, VoteResult.class);
		
		assertThat(voteResult.isSuccessful()).isTrue();
		assertThat(voteResult.getMessage()).contains("You have unvoted");
		
		int voteCountAfter = voteResult.getVoteCount();
		assertThat(voteCountBefore - 1).isEqualTo(voteCountAfter);
	}
}
