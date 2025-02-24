package com.ecommerce.admin.review;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.product.ProductRepository;
import com.ecommerce.common.entity.Review;
import com.ecommerce.common.exception.ReviewNotFoundException;

@Service
@Transactional
public class ReviewService {
	public static final int REVIEWS_PER_PAGE = 5;
	
	@Autowired private ReviewRepository reviewRepo;
	@Autowired private ProductRepository productRepo;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, REVIEWS_PER_PAGE, reviewRepo);
	}
	
	public Review get(Integer id) throws ReviewNotFoundException {
		try {
			return reviewRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ReviewNotFoundException("Could not find any reviews with ID " + id);
		}
	}

	public void save(Review reviewInForm) {
		Review reviewInDB = reviewRepo.findById(reviewInForm.getId()).get();
		reviewInDB.setHeadline(reviewInForm.getHeadline());
		reviewInDB.setComment(reviewInForm.getComment());
		
		reviewRepo.save(reviewInDB);
		productRepo.updateReviewCountAndAverageRating(reviewInDB.getProduct().getId());
	}
	
	public void delete(Integer id) throws ReviewNotFoundException {
		Long countById = reviewRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new ReviewNotFoundException("Could not find any review with ID " + id);
		}
		
		reviewRepo.deleteById(id);
	}
}
