package com.ecommerce.admin.brand;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.FileUploadUtil;
import com.ecommerce.admin.category.CategoryService;
import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.paging.PagingAndSortingParam;
import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;

@Controller
public class BrandController {
	private String DEFAULT_REDIRECT_URL = "redirect:/brands/page/1?sortField=name&sortDir=asc";

	@Autowired private BrandService brandService;
	@Autowired private CategoryService categoryService;
	
	@GetMapping("/brands")
	public String listFirstPage() {
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/brands/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listBrands", modulURL = "/brands") PagingAndSortingHelper helper,
			@PathVariable(name ="pageNum") Integer pageNum) {
		brandService.listByPage(pageNum, helper);
		
		return "brands/brands";
	}
	
	@GetMapping("/brands/new")
	public String newBrand(Model model) {
		List<Category> listCategories = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("brand", new Brand());
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Create new brand");
		
		return "brands/brand_form";
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
	        RedirectAttributes redirectAttributes) throws IOException {

	    if (!multipartFile.isEmpty()) {
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        brand.setLogo(fileName);

	        Brand savedBrand = brandService.save(brand);
	        String uploadDir = "../brand-logos/" + savedBrand.getId();
	        FileUploadUtil.cleanDir(uploadDir);
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	        
//	        AmazonS3Util.removeFolder(uploadDir);
//			AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
	    } else {
	        brandService.save(brand);
	    }

	    redirectAttributes.addFlashAttribute("message", "The brand has been saved successfully!");
	    return DEFAULT_REDIRECT_URL;
	}

	
	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable(name = "id") int id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Brand brand = brandService.get(id);
			List<Category> listCategories = categoryService.listCategoriesUsedInForm();
			
			model.addAttribute("brand", brand);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit Brand (ID:" + id + ")");
			
			return "brands/brand_form";
		} catch (BrandNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/brands/delete/{id}")
	public String deleteBrand(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		try {
			brandService.delete(id);
			
			String brandDir = "../brand-logos/" + id;
			FileUploadUtil.removeDir(brandDir);
//			AmazonS3Util.removeFolder(brandDir);
			
			redirectAttributes.addFlashAttribute("message", 
					"The brand ID " + id + " has been deleted successfully");
		} catch (BrandNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/brands/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<Brand> listBrands = brandService.listAll();
		
		BrandCsvExporter exporter = new BrandCsvExporter();
		exporter.export(response, listBrands);
	}
}
