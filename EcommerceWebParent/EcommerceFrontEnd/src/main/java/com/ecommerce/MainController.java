package com.ecommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.common.entity.Section;
import com.ecommerce.section.SectionService;

@Controller
public class MainController {
	@Autowired private SectionService sectionService;
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Section> listSections = sectionService.listAll();
		model.addAttribute("listSections", listSections);
		
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/";
	}

}
