package com.ecommerce.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.Menu;
import com.ecommerce.common.exception.MenuNotFoundException;

@Controller
public class MenuController {
	@Autowired private MenuService service;
	
	@GetMapping("/m/{menu_alias}")
	public String findByAlias(@PathVariable("menu_alias") String alias, Model model) {
		try {
			Menu menu = service.findByAlias(alias);
			Article article = menu.getArticle();
			model.addAttribute("article", article);
			model.addAttribute("pageTitle", article.getTitle());
			
			return "article/article_detail";
		} catch (MenuNotFoundException e) {
			return "error/404";
		}
	}
}
