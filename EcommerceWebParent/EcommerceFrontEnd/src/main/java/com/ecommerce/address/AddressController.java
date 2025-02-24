package com.ecommerce.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.ControllerHelper;
import com.ecommerce.common.entity.Address;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.customer.CustomerService;

@Controller
public class AddressController {

	@Autowired private AddressService addressService;
	@Autowired private CustomerService customerService;
	@Autowired private ControllerHelper controllerHelper;
	
	@GetMapping("/address_book")
	public String showAddressBook(Model model, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		List<Address> listAddresses = addressService.listAddressBook(customer);
		
		boolean usePrimaryAddressAsDefault = true;
		for (Address address : listAddresses) {
			if (address.isDefaultForShipping()) {
				usePrimaryAddressAsDefault = false;
				break;
			}
		}
		
		model.addAttribute("listAddresses", listAddresses);
		model.addAttribute("customer", customer);
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		
		return "address_book/addresses";
	}
	
	@GetMapping("/address_book/new")
	public String newAddress(Model model) {
		List<Country> listCountries = customerService.listAllCountries();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("address", new Address());
		model.addAttribute("pageTitle", "Add New Address");
		
		return "address_book/address_form";
	}
	
	@PostMapping("/address_book/save")
	public String saveAddress(Address address, HttpServletRequest request, RedirectAttributes ra) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		address.setCustomer(customer);
		addressService.save(address);
		
		String redirectOption = request.getParameter("redirect");
		String redirectUrl = "redirect:/address_book";
		
		if ("checkout".equals(redirectOption)) {
			redirectUrl += "?redirect=checkout";
		}
		
		ra.addFlashAttribute("message", "The address has been saved successfully.");
		
		return redirectUrl;
	}
	
	@GetMapping("/address_book/edit/{id}")
	public String editAddress(@PathVariable(name = "id") Integer addressId, Model model,
			HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		Address address = addressService.get(addressId, customer.getId());
		List<Country> listCountries = customerService.listAllCountries();
		
		model.addAttribute("address", address);
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Edit Address (ID: " + addressId + ")");
		
		return "address_book/address_form";
	}
	
	@GetMapping("/address_book/delete/{id}")
	public String deleteAddress(@PathVariable(name = "id") Integer addressId, HttpServletRequest request,
			RedirectAttributes ra) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		addressService.delete(addressId, customer.getId());
		
		ra.addFlashAttribute("message", "The address ID " + addressId + " has been deleted.");
		
		return "redirect:/address_book";
	}
	
	@GetMapping("/address_book/default/{id}")
	public String setDefaultAddress(@PathVariable(name = "id") Integer addressId, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		addressService.setDefaultAddress(addressId, customer.getId());
		
		String redirectOption = request.getParameter("redirect");
		String redirectUrl = "redirect:/address_book";
		
		if ("cart".equals(redirectOption)) {
			redirectUrl = "redirect:/cart";
		} else if ("checkout".equals(redirectOption)) {
			redirectUrl = "redirect:/checkout";
		}
		
		return redirectUrl;
	}
}















