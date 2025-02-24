package com.ecommerce.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.common.entity.Article;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@GetMapping("/a/{alias}")
	public String findByAlias(@PathVariable(name = "alias") String alias, Model model) {
		try {
			Article article = service.findByAlias(alias);
			model.addAttribute("article", article);
			model.addAttribute("pageTitle", article.getTitle());
			
			return "article/article_detail";
		} catch (ArticleNotFoundException e) {
			return "error/404";
		}
	}
}
