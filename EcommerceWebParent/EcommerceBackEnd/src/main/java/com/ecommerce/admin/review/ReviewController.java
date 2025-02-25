package com.ecommerce.admin.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.paging.PagingAndSortingParam;
import com.ecommerce.common.entity.Review;
import com.ecommerce.common.exception.ReviewNotFoundException;

@Controller
public class ReviewController {
	private String DEFAULT_REDIRECT_URL = "redirect:/reviews/page/1?sortField=reviewTime&sortDir=desc";
	
	@Autowired private ReviewService service;
	
	@GetMapping("/reviews")
	public String listFirstPage() {
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/reviews/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listReviews", modulURL = "/reviews") PagingAndSortingHelper helper,
			@PathVariable("pageNum") int pageNum) {
		service.listByPage(pageNum, helper);
		
		return "reviews/reviews";
	}
	
	@GetMapping("/reviews/detail/{id}")
	public String viewReview(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Review review = service.get(id);
			model.addAttribute("review", review);
			
			return "reviews/review_detail_modal";
		} catch (ReviewNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	
	@GetMapping("/reviews/edit/{id}")
	public String editReview(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Review review = service.get(id);
			
			model.addAttribute("review", review);
			model.addAttribute("pageTitle", "Edit Review (ID:" + id + ")");
			
			return "reviews/review_form";
		} catch (ReviewNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@PostMapping("/reviews/save")
	public String saveDetail(Review review, RedirectAttributes ra) {
		service.save(review);
		ra.addFlashAttribute("message", "The review with ID " + review.getId() + " has been updating successfully.");
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/reviews/delete/{id}")
	public String deleteDetail(@PathVariable("id") Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message", "The review with ID " + id + " has been deleted succesfully.");
		} catch (ReviewNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
}
