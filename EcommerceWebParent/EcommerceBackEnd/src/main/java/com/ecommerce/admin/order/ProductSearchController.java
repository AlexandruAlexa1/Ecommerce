package com.ecommerce.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecommerce.admin.product.ProductService;
import com.ecommerce.common.entity.product.Product;

@Controller
public class ProductSearchController {
	
	@Autowired private ProductService service;

	@GetMapping("/orders/search_product")
	public String showSearchProductPage() {
		return "orders/search_product";
	}
	
	@PostMapping("/orders/search_product")
	public String searchProducts(String keyword) {
		return "redirect:/orders/search_product/page/1?sortField=name&sortDir=asc&keyword=" + keyword;
	}
	
	@GetMapping("/orders/search_product/page/{pageNum}")
	public String searchProductsByPage(@PathVariable(name = "pageNum") int pageNum, String keyword, Model model) {
		Page<Product> page = service.searchProducts(pageNum, keyword);
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
		model.addAttribute("sortField", "name");
		model.addAttribute("sortDir", "asc");
		model.addAttribute("keyword", keyword);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("moduleURL", "/orders/search_product");
		
		return "orders/search_product";
	}
}
