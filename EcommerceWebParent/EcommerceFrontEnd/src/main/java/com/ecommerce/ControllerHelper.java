package com.ecommerce;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.customer.CustomerService;

@Component
public class ControllerHelper {
	@Autowired private CustomerService customerService;

	public Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		
		return customerService.getCustomerByEmail(email);
	}
}
