package com.ecommerce.admin.user.controller;

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
import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.paging.PagingAndSortingParam;
import com.ecommerce.admin.user.UserNotFoundException;
import com.ecommerce.admin.user.UserService;
import com.ecommerce.admin.user.export.UserCsvExporter;
import com.ecommerce.admin.user.export.UserExcelExporter;
import com.ecommerce.admin.user.export.UserPdfExporter;
import com.ecommerce.common.entity.Role;
import com.ecommerce.common.entity.User;

@Controller
public class UserController {
	private String DEFAULT_REDIRECT_URL = "redirect:/users/page/1?sortField=firstName&sortDir=asc";
	
	@Autowired private UserService service;

	@GetMapping("/users")
	public String listFirstPage() {
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listUsers", modulURL = "/users") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		service.listByPage(pageNum, helper);
		
		return "users/users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();

		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create new user");

		return "users/user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		List<Role> listRoles = service.listRoles();
		model.addAttribute("listRoles", listRoles);
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = service.save(user);

			String uploadDir = "../user-photos/" + savedUser.getId();
//			AmazonS3Util.removeFolder(uploadDir);
//			AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
			
			FileUploadUtil.removeDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			service.save(user);
		}
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return getRedirectSavedUser(user);
	}

	private String getRedirectSavedUser(User user) {
		String firstPathOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPathOfEmail;
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			List<Role> listRoles = service.listRoles();
			User user = service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("listRoles", listRoles);

			model.addAttribute("pageTitle", "Edit user (ID: " + id + ")");

			return "users/user_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

		try {
			service.delete(id);
			String userPhotosDir = "user-photos/" + id;
//			AmazonS3Util.removeFolder(userPhotosDir);
			FileUploadUtil.removeDir(userPhotosDir);
			
			redirectAttributes.addFlashAttribute("message", "The user ID: " + id + " has been deleted successfully!");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return DEFAULT_REDIRECT_URL;
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateUserEnableStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/users/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<User> listUsers = service.listUsers();
		
		UserCsvExporter exporter = new UserCsvExporter();
		exporter.export(listUsers, response);
	}
	
	@GetMapping("/users/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<User> listUsers = service.listUsers();
		
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(listUsers, response);
	}
	
	@GetMapping("/users/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> listUsers = service.listUsers();
		
		UserPdfExporter exporter = new UserPdfExporter();
		exporter.export(listUsers, response);
	}
	
	
}
