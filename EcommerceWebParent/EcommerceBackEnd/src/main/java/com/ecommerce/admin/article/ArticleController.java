package com.ecommerce.admin.article;

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
import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.User;

@Controller
public class ArticleController {
	private static final String DEFAULT_REDIRECT_URL = "redirect:/articles/page/1?sortField=title&sortDir=asc";
	@Autowired private ArticleService articleService;
	@Autowired private UserService userService;
	
	@GetMapping("/articles")
	public String listFirstPage() {
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/articles/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listArticles", modulURL = "/articles") PagingAndSortingHelper helper,
			@PathVariable("pageNum") Integer pageNum) {
		articleService.listByPage(pageNum, helper);
		
		return "articles/articles";
	}
	
	@GetMapping("/articles/{id}/enabled/{status}")
	public String updateEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		articleService.updateEnableStatus(id, enabled);
		
		String status = enabled ? "enabled" : "disabled";
		String message = "The Article with ID " + id + " has been " + status;
		
		redirectAttributes.addFlashAttribute("message", message);
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/articles/new")
	public String newArticle(Model model) {
		model.addAttribute("article", new Article());
		model.addAttribute("pageTitle", "Create new Article");
		
		return "articles/article_form";
	}
	
	@PostMapping("/articles/save") 
	public String saveArticle(Article article, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String userEmail = Utility.getEmailOfAuthenticatedUser(request);
		User user = userService.getByEmail(userEmail);
		article.setUser(user);
		
		articleService.save(article);
		
		redirectAttributes.addFlashAttribute("message", "The Article has beed saved successfully.");
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/articles/edit/{id}")
	public String editArticle(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Article article = articleService.get(id);
			model.addAttribute("article", article);
			model.addAttribute("pageTitle", "Edit Article ID(" + id + ")");
			
			return "articles/article_form";
		} catch (ArticleNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/articles/delete/{id}")
	public String deleteArticle(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			articleService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The Article with ID " + id + " has been deleted successfully!");
		} catch (ArticleNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/articles/modal/detail/{id}")
	public String viewArticleDetail(@PathVariable("id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Article article = articleService.get(id);
			model.addAttribute("article", article);
			
			return "articles/article_detail_modal";
		} catch (ArticleNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/articles/detail/{id}")
	public String viewDetail(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Article article = articleService.get(id);
			model.addAttribute("article", article);
			
			return "articles/article";
		} catch (ArticleNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
}
