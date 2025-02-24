package com.ecommerce.question;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class QuestionRepositoryTests {

	@Autowired private QuestionRepository repo;
	
	@Test
	public void testFindByCustomer() {
		Integer customerId = 10;	
		Integer pageNum = 0;
		Integer pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Question> page = repo.findByCustomer(customerId, pageable);
		
		List<Question> listQuestions = page.getContent();
		
		assertThat(listQuestions.size()).isGreaterThan(0);
		
		listQuestions.forEach(System.out::println);
	}
	
	@Test
	public void testFindByCustomerWithKeyword() {
		Integer customerId = 10;	
		String keyword = "This";
		Integer pageNum = 0;
		Integer pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Question> page = repo.findByCustomer(customerId, keyword, pageable);
		
		List<Question> listQuestions = page.getContent();
		
		assertThat(listQuestions.size()).isGreaterThan(0);
		
		listQuestions.forEach(System.out::println);
	}
	
	@Test
	public void testFindByProduct() {
		Integer productId = 34;
		Integer pageNum = 0;
		Integer pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Question> page = repo.findByProduct(new Product(productId), pageable);
		
		List<Question> listQuestions = page.getContent();
		
		assertThat(listQuestions.size()).isGreaterThan(0);
		
		listQuestions.forEach(System.out::println);
	}
	
	@Test
	public void testGetQuestionCount() {
		Integer productId = 1;
		
		Integer countByProduct = repo.getQuestionCount(productId);
		
		System.out.println(countByProduct);
	}
	
	@Test
	public void testGetAnswerCount() {
		Integer productId = 1;
		Integer answerCount = repo.getAnswerCount(productId);
		
		System.out.println(answerCount);
	}
}
