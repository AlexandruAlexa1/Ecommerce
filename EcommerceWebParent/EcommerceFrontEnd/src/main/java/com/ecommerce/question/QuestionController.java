package com.ecommerce.question;

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
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.common.exception.QuestionNotFoundException;
import com.ecommerce.product.ProductService;
import com.ecommerce.question.vote.QuestionVoteService;

@Controller
public class QuestionController {
	String defaultRedirectUrl = "redirect:/questions/page/1?sortField=askTime&sortDir=asc";
	
	@Autowired private QuestionService questionService;
	@Autowired private ProductService productService;
	@Autowired private ControllerHelper helper;
	@Autowired private QuestionVoteService voteService;
	
	@GetMapping("/questions")
	public String listFirstPage() {
		return defaultRedirectUrl;
	}
	
	@GetMapping("/questions/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, String sortField, String sortDir,
			String keyword, Model model, HttpServletRequest request) {
		
		Customer customer = helper.getAuthenticatedCustomer(request);
		
		Page<Question> page = questionService.listByPage(customer, pageNum, sortField, sortDir, keyword);
		List<Question> listQuestions = page.getContent();
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		long startCount = (pageNum - 1) * QuestionService.QUESTIONS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + QuestionService.QUESTIONS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) endCount = page.getTotalElements();
		model.addAttribute("endCount", endCount);
		
		model.addAttribute("listQuestions", listQuestions);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("moduleURL", "/questions");
		
		return "questions/questions_customer";
	}
	
	@GetMapping("/questions/{product_alias}/page/{pageNum}")
	public String listByProductByPage(@PathVariable("product_alias") String productAlias,
			@PathVariable("pageNum") int pageNum, String sortField, String sortDir, Model model, 
			HttpServletRequest request) {
		Product product = null;
		
		try {
			product = productService.getProduct(productAlias);
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
		
		
		Page<Question> page = questionService.listByProduct(product, pageNum, sortField, sortDir);
		List<Question> listQuestions = page.getContent();
		
		Customer customer = helper.getAuthenticatedCustomer(request);
		
		if (customer != null) {
			voteService.markQuestionsVotedForProductByCustomer(listQuestions, product.getId(), customer.getId());
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		long startCount = (pageNum - 1) * QuestionService.QUESTIONS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + QuestionService.QUESTIONS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) endCount = page.getTotalElements();
		model.addAttribute("endCount", endCount);
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		
		model.addAttribute("listQuestions", listQuestions);
		model.addAttribute("product", product);
		
		model.addAttribute("moduleURL", "/questions");
		
		return "questions/questions_product";
	}
	
	@GetMapping("/questions/{product_alias}")
	public String listByProductFirstPage(@PathVariable("product_alias") String productAlias) {
		return "redirect:/questions/" + productAlias + "/page/1?sortField=askTime&sortDir=asc";
	}
	
	@GetMapping("/questions/detail/{id}")
	public String viewQuestion(@PathVariable("id") Integer id, RedirectAttributes ra, Model model) {
		try {
			Question question = questionService.get(id);
			model.addAttribute("question", question);
			
			return "questions/question_detail_modal";
		} catch (QuestionNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return defaultRedirectUrl;
		}
	}
	
	@PostMapping("/post_question")
	public String saveQuestion(Question question) {
		return "product/product_detail";
	}
}
