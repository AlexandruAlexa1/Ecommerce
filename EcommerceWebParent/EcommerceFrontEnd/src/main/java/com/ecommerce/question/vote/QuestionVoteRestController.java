package com.ecommerce.question.vote;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ControllerHelper;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.review.vote.VoteResult;
import com.ecommerce.review.vote.VoteType;

@RestController
public class QuestionVoteRestController {

	@Autowired private QuestionVoteService service;
	@Autowired private ControllerHelper helper;
	
	@PostMapping("/vote_question/{id}/{type}")
	public VoteResult voteQuestion(@PathVariable("id") Integer questionId,
			@PathVariable("type") String type, HttpServletRequest request) {
		Customer customer = helper.getAuthenticatedCustomer(request);
		
		if (customer == null) {
			return VoteResult.fail("You must login to vote this question.");
		}
		
		VoteType voteType = VoteType.valueOf(type.toUpperCase());
		return service.doVote(questionId, customer, voteType);
	}
}
