package com.ecommerce.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.ControllerHelper;
import com.ecommerce.category.CategoryService;
import com.ecommerce.common.entity.Category;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.Review;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.CategoryNotFoundException;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.question.QuestionService;
import com.ecommerce.question.vote.QuestionVoteService;
import com.ecommerce.review.ReviewService;
import com.ecommerce.review.vote.ReviewVoteService;

@Controller
public class ProductController {
	@Autowired private ProductService productService;
	@Autowired private CategoryService categoryService;
	@Autowired private ReviewService reviewService;
	@Autowired private ReviewVoteService voteService;
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private QuestionService questionService;
	@Autowired private QuestionVoteService questionVoteService;
	
	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
		return viewCategoryByPage(alias, 1, model);
	}
	
	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum, Model model) {
		try {
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);
			
			Page<Product> page = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = page.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > page.getTotalElements()) {
				endCount = page.getTotalElements();
			}
			
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);
			
			return "product/products_by_category";
		} catch (CategoryNotFoundException e) {
			return "error/404";
		}
	}
	
	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias,
			HttpServletRequest request, Model model) {
		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			List<Category> listCategories = categoryService.listNoChildrenCategories();
			Page<Review> listReviews = reviewService.list3MostRecentReviewsByProduct(product);
			Page<Question> listQuestions = questionService.list3MostRecentQuestionsByProduct(product);
			
			Customer customer = controllerHelper.getAuthenticatedCustomer(request);
			if (customer != null) {
				boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
				voteService.markReviewsVotedForProductByCustomer(listReviews.getContent(), product.getId(), customer.getId());
				questionVoteService.markQuestionsVotedForProductByCustomer(listQuestions.getContent(), product.getId(), customer.getId());
				
				if (customerReviewed) {
					model.addAttribute("customerReviewed", customerReviewed);
				} else {
					boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
					model.addAttribute("customerCanReview", customerCanReview);
				}
			}
			
			Integer answerCount = questionService.getAnswerCount(product);
			model.addAttribute("answerCount", answerCount);
			
			Integer questionCount = questionService.getQuestionCount(product);
			model.addAttribute("questionCount", questionCount);
			
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("product", product);
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listReviews", listReviews);
			model.addAttribute("listQuestions", listQuestions);
			model.addAttribute("question", new Question());
			
			model.addAttribute("pageTitle", product.getShortName());
			
			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}
	
	@GetMapping("/search")
	public String searchFirstPage(@Param("keyword") String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}
	
	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@Param("keyword") String keyword,
			@PathVariable("pageNum") int pageNum, Model model) {
		Page<Product> page = productService.search(keyword, pageNum);
		List<Product> listResult = page.getContent();
		
		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE+ 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");
		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);
		
		return "product/search_result";
	}
}
