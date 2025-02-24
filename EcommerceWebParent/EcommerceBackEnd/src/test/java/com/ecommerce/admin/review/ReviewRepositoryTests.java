package com.ecommerce.admin.review;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Review;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewRepositoryTests {

	@Autowired
	private ReviewRepository repo;
	
	@Test
	public void testCreateReview() {
		Review review = new Review();
		review.setCustomer(new Customer(3));
		review.setProduct(new Product(66));
		review.setHeadline("Headline");
		review.setComment("...");
		review.setRating(5);
		review.setReviewTime(new Date());
		
		Review savedReview = repo.save(review);
		
		assertThat(savedReview).isNotNull();
		assertThat(savedReview.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListReviews() {
		List<Review> listReviews = repo.findAll();
		
		assertThat(listReviews.size()).isGreaterThan(0);
		
		listReviews.forEach(System.out::println);
	}
	
	@Test
	public void testGetReview() {
		Integer reviewId = 1;
		Review review = repo.findById(reviewId).get();
		
		assertThat(review).isNotNull();
		
		System.out.println(review);
	}
	
	@Test
	public void testUpdateReview() {
		Integer reviewId = 2;
		String headline = "Samsung 22 Ultra is the best phone";
		
		Review review = repo.findById(reviewId).get();
		review.setHeadline(headline);
		
		assertThat(review.getHeadline()).isEqualTo(headline);
	}
	
	@Test
	public void testDeleteReview() {
		Integer reviewId = 1;
		repo.deleteById(reviewId);
		
		Optional<Review> review = repo.findById(reviewId);
		
		assertThat(!review.isPresent());
	}
}










