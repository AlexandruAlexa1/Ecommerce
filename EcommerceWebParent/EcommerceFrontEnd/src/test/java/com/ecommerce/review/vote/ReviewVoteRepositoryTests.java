package com.ecommerce.review.vote;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Review;
import com.ecommerce.common.entity.ReviewVote;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewVoteRepositoryTests {

	@Autowired private ReviewVoteRepository repo;
	
	@Test
	public void testSaveVote() {
		Customer customer = new Customer(10);
		Review review = new Review(5);
		
		ReviewVote vote = new ReviewVote();
		vote.setCustomer(customer);
		vote.setReview(review);
		vote.voteDown();
		
		ReviewVote savedVote = repo.save(vote);
		
		assertThat(savedVote).isNotNull();
	}
	
	@Test
	public void testFindByReviewAndCustomer() {
		Integer reviewId = 5;
		Integer customerId = 10;
		
		ReviewVote vote = repo.findByReviewAndCustomer(reviewId, customerId);
		
		assertThat(vote).isNotNull();
		
		System.out.println(vote);
	}
	
	@Test
	public void testFindByProductAndCustomer() {
		Integer productId = 70;
		Integer customerId = 10;
		
		List<ReviewVote> listVotes = repo.findByProductAndCustomer(productId, customerId);
		
		assertThat(listVotes.size()).isGreaterThan(0);
		
		listVotes.forEach(System.out::println);
	}
}
