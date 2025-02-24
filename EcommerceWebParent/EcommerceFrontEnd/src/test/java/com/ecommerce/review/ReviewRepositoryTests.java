package com.ecommerce.review;

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

import com.ecommerce.common.entity.Review;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewRepositoryTests {

	@Autowired private ReviewRepository repo;
	
	@Test
	public void testFindByCustomerNotKeyword() {
		Integer customerId = 10;
		Integer pageNum = 0;
		Integer pageSize = 5;

		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Review> page = repo.findByCustomer(customerId, pageable);
		
		List<Review> listReviews = page.getContent();
		
		assertThat(listReviews.size()).isGreaterThan(0);
		
		listReviews.forEach(System.out::println);
	}
	
	@Test
	public void testFindByCustomerWithKeyword() {
		Integer customerId = 10;
		String keyword = "product";
		Integer pageNum = 0;
		Integer pageSize = 5;

		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Review> page = repo.findByCustomer(customerId, keyword, pageable);
		
		List<Review> listReviews = page.getContent();
		
		assertThat(listReviews.size()).isGreaterThan(0);
		
		listReviews.forEach(System.out::println);
	}
	
	@Test
	public void testFindByCustomerAndId() {
		Integer customerId = 10;
		Integer reviewId = 5;
		Review review = repo.findByCustomerAndId(customerId, reviewId);
		
		assertThat(review).isNotNull();
		
		System.out.println(review);
	}
	
	@Test
	public void testCountByCustomerAndProduct() {
		Integer customerId = 3;
		Integer productId = 1;
		Long count = repo.countByCustomerAndProduct(customerId, productId);
		
		assertThat(count).isEqualTo(1);
	}
	
	@Test
	public void testUpdateVoteCount() {
		Integer reviewId = 5;
		repo.updateVoteCount(reviewId);
		
		Review review = repo.findById(reviewId).get();
		
		assertThat(review.getVotes()).isEqualTo(1);
	}
	
	@Test 
	public void testGetVoteCount() {
		Integer reviewId = 5;
		Integer voteCount = repo.getVoteCount(reviewId);
		
		assertThat(voteCount).isEqualTo(1);
	}
}
