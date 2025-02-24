package com.ecommerce.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.ControllerHelper;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Review;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.common.exception.ReviewNotFoundException;
import com.ecommerce.product.ProductService;
import com.ecommerce.review.vote.ReviewVoteService;

@Controller
public class ReviewController {
	private String defaultRedirectURL = "redirect:/reviews/page/1?sortField=reviewTime&sortDir=desc";
	
	@Autowired private ReviewService reviewService;
	@Autowired private ProductService productService;
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private ReviewVoteService voteService;
	
	@GetMapping("/reviews")
	public String listFirstPage() {
		return defaultRedirectURL;
	}
	
	@GetMapping("/reviews/page/{pageNum}")
	public String listReviewsByCustomerByPage(@PathVariable("pageNum") int pageNum, String sortField, String sortDir,
			String keyword, HttpServletRequest request, Model model) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		if (sortDir == null || sortDir.isEmpty()) sortDir = "desc";
		
		Page<Review> page = reviewService.listByCustomerByPage(customer, keyword, pageNum, sortField, sortDir);
		List<Review> listReviews = page.getContent();
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		long startCount = (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("endCount", endCount);
		
		model.addAttribute("listReviews", listReviews);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		
		model.addAttribute("moduleURL", "/reviews");
		
		return "reviews/reviews_customer";
	}
	
	@GetMapping("/ratings/{product_alias}/page/{pageNum}")
	public String listByProductByPage(Model model,
			@PathVariable("product_alias") String productAlias,
			@PathVariable("pageNum") int pageNum,
			String sortField, String sortDir,
			HttpServletRequest request) {
		Product product = null;
		
		try {
			product = productService.getProduct(productAlias);
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
		
		if (sortDir == null || sortDir.isEmpty()) sortDir = "desc";
		
		Page<Review> page = reviewService.listByProduct(product, pageNum, sortField, sortDir);
		List<Review> listReviews = page.getContent();
		
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		if (customer != null) {
			voteService.markReviewsVotedForProductByCustomer(listReviews, product.getId(), customer.getId());
		}
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		
		model.addAttribute("listReviews", listReviews);
		model.addAttribute("product", product);
		
		long startCount = (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("endCount", endCount);
		model.addAttribute("pageTitle", "Reviews for " + product.getShortName());
		
		return "reviews/reviews_product";
	}
	
	@GetMapping("/ratings/{product_alias}")
	public String listByProductFirstPage(@PathVariable("product_alias") String productAlias,
			Model model, HttpServletRequest request) {
		return listByProductByPage(model, productAlias, 1, "reviewTime", "desc", request);
	}
	
	
	@GetMapping("/reviews/detail/{id}")
	public String viewReview(@PathVariable("id") Integer id, HttpServletRequest request, Model model,
			RedirectAttributes ra) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		try {
			Review review = reviewService.getByCustomerAndId(customer, id);
			model.addAttribute("review", review);
			
			return "reviews/review_detail_modal";
		} catch (ReviewNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return defaultRedirectURL;
		}
	}
	
	@GetMapping("/write_review/product/{productId}")
	public String showReviewForm(@PathVariable("productId") Integer productId, HttpServletRequest request,
			Model model) {
		Review review = new Review();
		Product product = null;
		
		try {
			product = productService.getProduct(productId);
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
		
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
		
		if (customerReviewed) {
			model.addAttribute("customerReviewed", customerReviewed);
		} else {
			boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
			
			if (customerCanReview) {
				model.addAttribute("customerCanReview", customerCanReview);
			} else {
				model.addAttribute("NoReviewPermission", true);
			}
		}
		
		model.addAttribute("product", product);
		model.addAttribute("review", review);
		
		return "reviews/review_form";
	}
	
	@PostMapping("/post_review")
	public String saveReview(Review review, Integer productId, HttpServletRequest request, Model model) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		Product product = null;
		
		try {
			product = productService.getProduct(productId);
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
		
		review.setCustomer(customer);
		review.setProduct(product);
		
		Review savedReview = reviewService.saveReview(review);
		
		model.addAttribute("review", savedReview);
		
		return "reviews/review_done";
	}
	
}
