package com.ecommerce.admin.question;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.User;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class QuestionRepositoryTests {

	@Autowired private QuestionRepository repo;
	
	@Test
	public void testCreateQuestion() {
		Integer customerId = 10;
		Integer productId = 34;
		Integer userId = 1;
		
		Question question = new Question();
		question.setCustomer(new Customer(customerId));
		question.setProduct(new Product(productId));
		question.setUser(new User(userId));
		question.setQuestionContent("This phone includes a charger?");
		question.setAskTime(new Date());
		question.setAnswerContent("No, you need to buy it.");
		question.setAnswerTime(new Date());
		question.setApprovalStatus(false);
		question.setVotes(1);
		
		Question savedQuestion = repo.save(question);
		
		assertThat(savedQuestion.getId()).isGreaterThan(0);
		assertThat(savedQuestion).isNotNull();
	}
	
	@Test
	public void testListAllQuestion() {
		int pageNum = 0;
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Question> page = repo.findAll(pageable);
		
		List<Question> listQuestion = page.getContent();
		
		assertThat(listQuestion.size()).isGreaterThan(0);
		
		listQuestion.forEach(System.out::println);
	}
	
	@Test
	public void testGetQuestion() {
		Integer questionId = 1;
		Question question = repo.findById(questionId).get();
		
		assertThat(question).isNotNull();
		
		System.out.println(question);
	}
	
	@Test
	public void testUpdateQuestion() {
		Integer questionId = 1;
		String answer = "YES";
		
		Question question = repo.findById(questionId).get();
		question.setAnswerContent(answer);
		
		Question updatedQuestion = repo.save(question);
		
		assertThat(updatedQuestion.getAnswerContent()).isEqualTo(answer);
		
		System.out.println(question);
	}
	
	@Test
	public void testDeleteQuestion() {
		Integer questionId = 3;
		repo.deleteById(questionId);
		
		Optional<Question> question = repo.findById(questionId);
		
		assertThat(!question.isPresent());
	}
	
	@Test
	public void testUpdateApprovalStatus() {
		Integer questionId = 6;
		boolean status = false;
		
		repo.updateApprovalStatus(questionId, status);
		
		Question question = repo.findById(questionId).get();
		
		assertThat(question.isApprovalStatus()).isEqualTo(status);
	}
}
