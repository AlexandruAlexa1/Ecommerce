package com.ecommerce.question.vote;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.QuestionVote;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class QuestionVoteRepositoryTests {

	@Autowired private QuestionVoteRepository repo;
	
	@Test
	public void testSaveVote() {
		Integer customerId = 3;
		Integer questionId = 8;
		
		QuestionVote vote = new QuestionVote();
		vote.setCustomer(new Customer(customerId));
		vote.setQuestion(new Question(questionId));
		vote.voteUp();
		
		QuestionVote savedVote = repo.save(vote);
		
		assertThat(savedVote.getId()).isGreaterThan(0);
		assertThat(savedVote).isNotNull();
	}
	
	@Test
	public void testFindByQuestionAndCustomer() {
		Integer questionId = 8;
		Integer customerId = 3;
		
		QuestionVote vote = repo.findByQuestionAndCustomer(questionId, customerId);
		
		assertThat(vote).isNotNull();
		
		System.out.println(vote);
	}
	
	@Test
	public void testFindByProductAndCustomer() {
		Integer productId = 6;
		Integer customerId = 10;
		
		List<QuestionVote> listQuestions = repo.findByProductAndCustomer(productId, customerId);
		
		assertThat(listQuestions.size()).isGreaterThan(0);
		
		listQuestions.forEach(System.out::println);
	}
}
