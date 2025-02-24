package com.ecommerce.admin.question;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.Utility;
import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.paging.PagingAndSortingParam;
import com.ecommerce.admin.user.UserService;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.User;
import com.ecommerce.common.exception.QuestionNotFoundException;

@Controller
public class QuestionController {
	private String DEFAULT_REDIRECT_URL = "redirect:/questions/page/1?sortField=askTime&sortDir=asc";
	
	@Autowired private QuestionService questionService;
	@Autowired private UserService userService;
	
	@GetMapping("/questions")
	public String listFirstPage() {
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/questions/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listQuestions", modulURL = "/questions") PagingAndSortingHelper helper,
			@PathVariable("pageNum") int pageNum) {
		questionService.listByPage(pageNum, helper);
		return "questions/questions";
	}
	
	@GetMapping("/questions/detail/{id}")
	public String viewQuestionDetail(@PathVariable("id") Integer questionId, RedirectAttributes ra, Model model) {
		try {
			Question question = questionService.get(questionId);
			model.addAttribute("question", question);
			
			return "questions/question_detail_modal";
		} catch (QuestionNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/questions/edit/{id}")
	public String editQuestion(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Question question = questionService.get(id);
			model.addAttribute("question", question);
			model.addAttribute("pageTitle", "Edit question (ID:" + id + ")");
			
			return "questions/question_form";
		} catch (QuestionNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@PostMapping("/questions/save")
	public String saveQuestion(Question question, RedirectAttributes ra, HttpServletRequest request) {
		User user = getAuthenticatedUser(request);
		question.setUser(user);
		
		questionService.save(question);
		
		ra.addFlashAttribute("message", "The question with ID " + question.getId() + " has been saved successfully");
		
		return DEFAULT_REDIRECT_URL;
	}

	private User getAuthenticatedUser(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedUser(request);
		User user = userService.getByEmail(email);
		
		return user;
	}
	
	@GetMapping("/questions/delete/{id}")
	public String deleteQuestion(@PathVariable("id") Integer id, RedirectAttributes ra) {
		try {
			questionService.delete(id);
			ra.addFlashAttribute("message", "The question with ID " + id + " has been deleted successfully");
		} catch (QuestionNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/questions/update/{id}/{status}")
	public String updateApprovalStatus(@PathVariable("id") Integer questionId, @PathVariable("status") boolean enabled,
			RedirectAttributes ra) {
		questionService.updateApprovalStatus(questionId, enabled);
		String status = enabled ? "approved" : "disapproved";
		String message = "The question ID " + questionId + " has been " + status;
		
		ra.addFlashAttribute("message", message);
		return DEFAULT_REDIRECT_URL;
	}
	
}
