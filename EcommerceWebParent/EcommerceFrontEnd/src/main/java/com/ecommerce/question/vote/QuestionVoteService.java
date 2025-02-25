package com.ecommerce.question.vote;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.QuestionVote;
import com.ecommerce.question.QuestionRepository;
import com.ecommerce.review.vote.VoteResult;
import com.ecommerce.review.vote.VoteType;

@Service
@Transactional
public class QuestionVoteService {

	@Autowired private QuestionVoteRepository voteRepo;
	@Autowired private QuestionRepository questionRepo;
	
	
	public VoteResult undoVote(QuestionVote questionVote, Integer questionId, VoteType voteType) {
		voteRepo.delete(questionVote);
		questionRepo.updateVoteCount(questionId);
		Integer voteCount = questionRepo.getVoteCount(questionId);
		
		return VoteResult.success("You have unvoted " + voteType + " that review.", voteCount);
	}
	
	public VoteResult doVote(Integer questionId, Customer customer, VoteType voteType) {
		Question question = null;
		
		try {
			question = questionRepo.findById(questionId).get();
		} catch (NoSuchElementException e) {
			return VoteResult.fail("The question ID " + questionId + " not longer exists!");
		}
		
		QuestionVote vote = voteRepo.findByQuestionAndCustomer(questionId, customer.getId());
		
		if (vote != null) {
			if (vote.isUpvoted() && voteType.equals(VoteType.UP) || 
					vote.isDownvoted() && voteType.equals(VoteType.DOWN)) {
				return undoVote(vote, questionId, voteType);
			} else if (vote.isUpvoted() && voteType.equals(VoteType.DOWN)) {
				vote.voteDown();
			} else if (vote.isDownvoted() && voteType.equals(VoteType.UP)) {
				vote.voteUp();
			}
		} else {
			vote = new QuestionVote();
			vote.setCustomer(customer);
			vote.setQuestion(question);
			
			if (voteType.equals(VoteType.UP)) {
				vote.voteUp();
			} else {
				vote.voteDown();
			}
		}
		
		voteRepo.save(vote);
		questionRepo.updateVoteCount(questionId);
		Integer voteCount = questionRepo.getVoteCount(questionId);
		
		return VoteResult.success("You have successfully voted " + voteType + " that review.", voteCount);
	}
	
	public void markQuestionsVotedForProductByCustomer(List<Question> listQuestions, Integer productId,
			Integer customerId) {
		List<QuestionVote> listVotes = voteRepo.findByProductAndCustomer(productId, customerId);
		
		for (QuestionVote vote : listVotes) {
			Question votedQuestion = vote.getQuestion();
			
			if (listQuestions.contains(votedQuestion)) {
				int index = listQuestions.indexOf(votedQuestion);
				Question question = listQuestions.get(index);
				
				if (vote.isUpvoted()) {
					question.setUpvotedByCurrentCustomer(true);
				} else if (vote.isDownvoted()) {
					question.setDownvotedByCurrentCustomer(true);
				}
			}
		}
	}

}
