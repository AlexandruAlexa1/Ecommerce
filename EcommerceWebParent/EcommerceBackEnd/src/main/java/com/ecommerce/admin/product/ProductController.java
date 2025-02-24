package com.ecommerce.admin.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.FileUploadUtil;
import com.ecommerce.admin.brand.BrandService;
import com.ecommerce.admin.category.CategoryService;
import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.paging.PagingAndSortingParam;
import com.ecommerce.admin.security.EcommerceUserDetails;
import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.ProductNotFoundException;

@Controller
public class ProductController {
	String DEFAULT_REDIRECT_URL = "redirect:/products/page/1?sortField=name&sortDir=asc";
	
	@Autowired private ProductService productService;
	@Autowired private BrandService brandService;
	@Autowired private CategoryService categoryService;
	
	@GetMapping("/products")
	public String listFirstPage(Model model) {
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/products/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listProducts", modulURL = "/products") PagingAndSortingHelper helper,
			@PathVariable(name ="pageNum") int pageNum, Integer categoryId, Model model) {
		
		productService.listByPage(pageNum, helper, categoryId);
		
		List<Category> listCategories = categoryService.listCategoriesUsedInForm();
		
		if(categoryId != null) model.addAttribute("categoryId", categoryId);
		model.addAttribute("listCategories", listCategories);
		
		return "products/products";
	}
	
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		List<Brand> listBrands = brandService.listAll();
		
		model.addAttribute("product", new Product());
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create new product");
		
		return "products/product_form";
	}
	
	
	@PostMapping("/products/save")
	public String saveProduct(Product product, RedirectAttributes ra,
			@RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
			@RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues,
			@RequestParam(name = "imageIDs", required = false) String[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames,
			@AuthenticationPrincipal EcommerceUserDetails loggedUser
			) throws IOException {
		
		if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Editor")) {
			if (loggedUser.hasRole("Salesperson")) {
				productService.saveProductPrice(product);
				ra.addFlashAttribute("message", "The product has been saved successfully.");
				return DEFAULT_REDIRECT_URL;
			}
		}
		
		ProductSaveHelper.setMainImageName(mainImageMultipart, product);
		ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
		ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);
		ProductSaveHelper.setProductDetails(detailIDs, detailNames, detailValues, product);
		
		Product savedProduct = productService.save(product);
		
		ProductSaveHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);
		
		ProductSaveHelper.deleteExtraImagesWheredRemovedOnForm(product);
		
		ra.addFlashAttribute("message", "The product has been saved successfully.");
		return DEFAULT_REDIRECT_URL;
	}

	@GetMapping("/products/{id}/enabled/{status}")
	public String updateProductEnabledStatus(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "status") boolean enabled, RedirectAttributes redirectAttributes) {
		
		productService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The product ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		
		try {
			productService.delete(id);
			String productExtraImagesDir = "../product-images/" + id + "/extras";
			String productImagesDir = "../product-images/" + id;
			
			FileUploadUtil.removeDir(productExtraImagesDir);
			FileUploadUtil.removeDir(productImagesDir);
			
			redirectAttributes.addFlashAttribute("message", "The product ID " + id + " has been deleted successfully");
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal EcommerceUserDetails loggedUser) {
		try {
			Product product = productService.get(id);
			List<Brand> listBrands = brandService.listAll();
			Integer numberOfExistingExtraImages = product.getImages().size();
			
			boolean isReadOnlyForSalesperson = false;
			if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Editor")) {
				if (loggedUser.hasRole("SalesPerson")) {
					isReadOnlyForSalesperson = true;
				}
			}
			
			model.addAttribute("isReadOnlyForSalesperson", isReadOnlyForSalesperson);
			model.addAttribute("product", product);
			model.addAttribute("listBrands", listBrands);
			model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
			model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);
			
			return "products/product_form";
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/products/detail/{id}")
	public String viewProductDetails(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Product product = productService.get(id);
			model.addAttribute("product", product);
			
			return "products/product_detail_modal";
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/products/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<Product> listProducts = productService.listAll();
		
		ProductCsvExporter exporter = new ProductCsvExporter();
		exporter.export(listProducts, response);
	}
}
