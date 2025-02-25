package com.ecommerce.admin.section;

import java.util.ArrayList;
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

import com.ecommerce.admin.article.ArticleService;
import com.ecommerce.admin.brand.BrandService;
import com.ecommerce.admin.category.CategoryService;
import com.ecommerce.admin.product.ProductService;
import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;
import com.ecommerce.common.entity.Section;
import com.ecommerce.common.entity.SectionType;
import com.ecommerce.common.entity.product.Product;

@Controller
public class SectionController {
	private static final String DEFAULT_REDIRECT_URL = "redirect:/sections";
	
	@Autowired private SectionService sectionService;
	@Autowired private CategoryService categoryService;
	@Autowired private BrandService brandService;
	@Autowired private ArticleService articleService;
	@Autowired private ProductService productService;
	
	@GetMapping("/sections")
	public String listSections(Model model) {
		List<Section> listSections = sectionService.listSections();
		model.addAttribute("listSections", listSections);
		model.addAttribute("title", "Home Page Customization");
		
		return "sections/sections";
	}
	
	@GetMapping("/sections/{type}/new")
	public String newSection(@PathVariable(name = "type") String type, Model model) {
		Section section = new Section();
		model.addAttribute("section", section);
		
		SectionType sectionType = SectionType.valueOf(type.toUpperCase());
		
		switch (sectionType) {
		case ARTICLE:
			return newArticleSection(model);
		case ALL_CATEGORIES:
			return newCategoriesSection(model);
		case BRAND:
			return newBrandSection(model);
		case CATEGORY:
			return newCategorySection(model);
		case PRODUCT:
			return newProductSection(model);
		case TEXT:
			return newTextSection(model);
		default:
			return null;
		}
	}
	
	private String newArticleSection(Model model) {
		List<Article> listArticles = articleService.listAll();
		model.addAttribute("listArticles", listArticles);
		model.addAttribute("pageTitle", "Add Article Section");
		
		return "sections/section_form_article";
	}
	
	@PostMapping("/sections/save/article")
	private String saveArticleSection(Section section, RedirectAttributes redirectAttributes) {
		section.setType(SectionType.ARTICLE);
		sectionService.saveSection(section);
		
		redirectAttributes.addFlashAttribute("message", "The Article Section has been saved successfully");
		return DEFAULT_REDIRECT_URL;
	}
	
	private String newCategoriesSection(Model model) {
		model.addAttribute("pageTitle", "Add All Categories Section");

		return "sections/section_form_all_categories";
	}
	
	@PostMapping("/sections/save/categories")
	private String saveCategoriesSection(Section section, RedirectAttributes redirectAttributes) {
		List<Category> listCategories = categoryService.listAll();
		section.setCategories(listCategories);
		section.setType(SectionType.ALL_CATEGORIES);
		
		sectionService.saveSection(section);
		
		redirectAttributes.addFlashAttribute("message", "Category Section has been saved successfully");
		return DEFAULT_REDIRECT_URL;
	}
	
	private String newCategorySection(Model model) {
		List<Category> listCategories = categoryService.listAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Add Category Section");
		
		return "sections/section_form_category";
	}
	
	@PostMapping("/sections/save/category")
	private String saveCategorySection(Section section, RedirectAttributes redirectAttributes) {
		section.setType(SectionType.CATEGORY);
		sectionService.saveSection(section);
		
		redirectAttributes.addFlashAttribute("message", "The Category Section has been saved successfully");
		return DEFAULT_REDIRECT_URL;
	}
	
	private String newBrandSection(Model model) {
		List<Brand> listBrands = brandService.listAll();
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Add Brand Section");
		
		return "sections/section_form_brand";
	}

	@PostMapping("/sections/save/brand")
	private String saveBrandSection(Section section, RedirectAttributes redirectAttributes) {
		section.setType(SectionType.BRAND);
		sectionService.saveSection(section);
		
		redirectAttributes.addFlashAttribute("message", "The Brand Section has been saved successfully");
		return DEFAULT_REDIRECT_URL;
	}
	
	private String newProductSection(Model model) {
		model.addAttribute("pageTitle", "Add Product Section");
		
		return "sections/section_form_product";
	}
	
	@PostMapping("/sections/save/product")
	private String saveProductSection(Section section, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String[] productIds = request.getParameterValues("productId");
		List<Product> listProducts = new ArrayList<>();
		
		for (int i = 0; i < productIds.length; i++) {
			listProducts.add(new Product(Integer.parseInt(productIds[i])));
		}
		
		section.setProducts(listProducts);
		section.setType(SectionType.PRODUCT);
		
		sectionService.saveSection(section);
		
		redirectAttributes.addFlashAttribute("message", "The Product Section has been saved successfully");
		return DEFAULT_REDIRECT_URL;
	}
	
	private String newTextSection(Model model) {
		model.addAttribute("pageTitle", "Add Text Section");
		
		return "sections/section_form_text";
	}
	
	@PostMapping("/sections/save/text")
	private String saveTextSection(Section section, RedirectAttributes redirectAttributes) { 
		 section.setType(SectionType.TEXT);
		 sectionService.saveSection(section);
		 
		 redirectAttributes.addFlashAttribute("message", "The Text Section has been saved successfully"); 
		 return DEFAULT_REDIRECT_URL;
	}

	@GetMapping("/sections/search_product")
	public String searchProducts() {
		return "sections/search_product";
	}
	
	@PostMapping("/sections/search_product")
	public String searchProduct(String keyword) {
		return "redirect:/sections/search_product/page/1?sortField=name&sortDir=asc&keyword=" + keyword;
	}
	
	@GetMapping("/sections/search_product/page/{pageNum}")
	public String searchProductsByPage(@PathVariable(name = "pageNum") int pageNum, String keyword, Model model) {
		Page<Product> page = productService.searchProducts(pageNum, keyword);
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
		model.addAttribute("moduleURL", "/sections/search_product");
		
		return "sections/search_product";
	}
	
	@GetMapping("/sections/{id}/enabled/{status}")
	public String updateEnabledStatus(@PathVariable(name = "id") Integer id, 
			@PathVariable(name = "status") boolean enabled, RedirectAttributes redirectAttributes) {
		
		sectionService.updateEnabledStatus(id, enabled);
		
		String status = enabled ? "Enabled" : "Disabled";
		String message = "The Section ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/sections/edit/{id}")
	public String editSection(@PathVariable(name = "id") Integer sectionId,
			Model model, RedirectAttributes redirectAttributes) {
		
		try {
			Section section = sectionService.get(sectionId);
			SectionType sectionType = section.getType();
			
			model.addAttribute("section", section);
			
			switch (sectionType) {
			case ARTICLE:
				return editArticle(sectionId, model);
			case ALL_CATEGORIES:
				return editCategories(sectionId, model);
			case BRAND:
				return editBrandSection(sectionId, model);
			case CATEGORY:
				return editCategorySection(sectionId, model);
			case PRODUCT:
				return editProductSection(sectionId, model);
			case TEXT:
				return editTextSection(sectionId, model);
			default:
				return null;
			}
		} catch (SectionNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	private String editProductSection(Integer sectionId, Model model) {
		model.addAttribute("pageTitle", "Edit Product Section (ID:" + sectionId + ")");
		
		return "sections/section_form_product";
	}

	private String editCategories(Integer sectionId, Model model) {
		List<Category> listCategories = categoryService.listAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Edit Categories Section (ID:" + sectionId + ")");
		
		return "sections/section_form_all_categories";	
	}

	private String editTextSection(Integer sectionId, Model model) {
		model.addAttribute("pageTitle", "Edit Text Section (ID:" + sectionId + ")");
		
		return "sections/section_form_text";
	}

	private String editCategorySection(Integer sectionId, Model model) {
		List<Category> listCategories = categoryService.listAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Edit Category Section (ID:" + sectionId + ")");
		
		return "sections/section_form_category";
	}

	private String editBrandSection(Integer sectionId, Model model) {
		List<Brand> listBrands = brandService.listAll();
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Edit Brand Section (ID:" + sectionId + ")");
		
		return "sections/section_form_brand";
	}

	private String editArticle(Integer sectionId, Model model) {
		List<Article> listArticles = articleService.listAll();
		model.addAttribute("listArticles", listArticles);
		model.addAttribute("pageTitle", "Edit Article Section (ID:" + sectionId + ")");
		
		return "sections/section_form_article";
	}

	@GetMapping("/sections/delete/{id}")
	public String deleteSection(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			sectionService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The Section with ID " + id + " has been deleted successfuly");
		} catch (SectionNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/sections/{status}/{id}")
	public String updatePosition(@PathVariable(name = "status") String status, 
			@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		sectionService.updatePosition(id, status);
		String message = "The section ID " + id + " has been moved " + status + " by one position.";
		redirectAttributes.addFlashAttribute("message", message);

		return DEFAULT_REDIRECT_URL;
	}
}
