package com.ecommerce.admin.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.article.ArticleService;
import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.Menu;
import com.ecommerce.common.exception.MenuNotFoundException;

@Controller
public class MenuController {
	private static final String DEFAULT_REDIRECT_URL = "redirect:/menus";
	
	@Autowired private MenuService menuService;
	@Autowired private ArticleService articleService;
	
	@GetMapping("/menus")
	public String listMenus(Model model) {
		List<Menu> listMenus = menuService.listAll();
		model.addAttribute("listMenus", listMenus);
		
		return "menus/menus";
	}
	
	@GetMapping("/menus/new")
	public String newMenu(Model model) {
		List<Article> listArticles = articleService.listAll();
		
		model.addAttribute("menu", new Menu());
		model.addAttribute("listArticles", listArticles);
		model.addAttribute("pageTitle", "Create new Menu");
		
		return "menus/menu_form";
	}
	
	@PostMapping("/menus/save")
	public String saveMenu(Menu menu, RedirectAttributes redirectAttributes) {
		menuService.save(menu);
		if (menu.getId() == null) {
			redirectAttributes.addFlashAttribute("message", "The Menu has been saved successfully.");
		} else {
			redirectAttributes.addFlashAttribute("message", "The Menu with ID " + menu.getId() + " has been updated successfully.");
		}
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/menus/{id}/enabled/{status}")
	public String updateEnableStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		menuService.updateEnableStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Menu with ID " + id + " has been " + status;
		
		redirectAttributes.addFlashAttribute("message", message);
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/menus/edit/{id}")
	public String editMenu(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			Model model) {
		try {
			Menu menu = menuService.get(id);
			List<Article> listArticles = articleService.listAll();

			model.addAttribute("menu", menu);
			model.addAttribute("listArticles", listArticles);
			model.addAttribute("pageTitle", "Edit Menu ID(" + id + ")");
			
			return "menus/menu_form";
		} catch (MenuNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/menus/delete/{id}")
	public String deleteMenu(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			menuService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The Menu with ID(" + id + ") has been deleted successfully.");
		} catch (MenuNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/menus/{status}/{id}")
	public String updatePosition(@PathVariable("id") Integer id, @PathVariable("status") String status,
			RedirectAttributes redirectAttributes) {
		menuService.updatePosition(id, status);
		String message = "The menu ID " + id + " has been moved " + status + " by one position.";
		redirectAttributes.addFlashAttribute("message", message);
		
		return DEFAULT_REDIRECT_URL;
	}
}
