package com.ecommerce.admin.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.paging.PagingAndSortingParam;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.exception.CustomerNotFoundException;

@Controller
@Transactional
public class CustomerController {
	private String DEFAULT_REDIRECT_URL = "redirect:/customers/page/1?sortField=firstName&sortDir=asc";
	
	@Autowired private CustomerService service;
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return DEFAULT_REDIRECT_URL;
	}

	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listCustomers", modulURL = "/customers") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		service.listByPage(pageNum, helper);
		return "customers/customers";
	}
	
	
	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateCustomerEnabledStatus(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCustomerEnabledStatus(id, enabled);
		
		String status = enabled ? "enabled" : "disabled";
		String message = "The customer ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/customers/detail/{id}")
	public String viewCustomerDetail(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		try {
			Customer customer = service.get(id);
			model.addAttribute("customer", customer);
			
			return "customers/customer_detail_modal";
		} catch (CustomerNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}
	
	@GetMapping("/customers/edit/{id}")
	public String editCustomer(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		try {
			Customer customer = service.get(id);
			List<Country> listCountries = service.listAllCountries();
			
			model.addAttribute("customer", customer);
			model.addAttribute("listCountries", listCountries);
			model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");

			return "customers/customer_form";
		} catch (CustomerNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return DEFAULT_REDIRECT_URL;
		}
	}

	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes) {
		service.save(customer);
		redirectAttributes.addFlashAttribute("message", "The customer ID " + customer.getId() + " has been updated successfuly.");
		
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			
			redirectAttributes.addFlashAttribute("message", "The customer ID " + id + " has been deleted successfully!");
		} catch (CustomerNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return DEFAULT_REDIRECT_URL;
	}
	
	@GetMapping("/customers/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<Customer> listCustomers = service.listCustomers();
		
		CustomerCsvExporter exporter = new CustomerCsvExporter();
		exporter.export(listCustomers, response);
	}
}









