package com.ecommerce.admin.customer;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.country.CountryRepository;
import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.exception.CustomerNotFoundException;

@Service
@Transactional
public class CustomerService {
	public static final int CUSTOMERS_PER_PAGE = 5;
	
	@Autowired private CustomerRepository customerRepository;
	@Autowired private CountryRepository countryRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<Customer> listCustomers() {
		return (List<Customer>) customerRepository.findAll();
	}
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepository);
	}
	
	public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
		customerRepository.updateEnabledStatus(id, enabled);
	}
	
	public Customer get(Integer id) throws CustomerNotFoundException {
		try {
			return customerRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CustomerNotFoundException("Could not find any Customer with ID " + id);
		}
	}
	
	public List<Country> listAllCountries() {
		return countryRepository.findAllByOrderByNameAsc();
	}

	public void save(Customer customerInForm) {
		Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
		
		if (!customerInForm.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
			customerInForm.setPassword(encodedPassword);
		} else {
			customerInForm.setPassword(customerInDB.getPassword());
		}
		
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
		customerInForm.setEnabled(customerInDB.isEnabled());
		customerInForm.setCreatedTime(customerInDB.getCreatedTime());
		customerInForm.setVerificationCode(customerInDB.getVerificationCode());
		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		customerRepository.save(customerInForm);
	}
	
	public void delete(Integer id) throws CustomerNotFoundException {
		Long countById = customerRepository.countById(id);
		
		if (countById == null || countById == 0) {
			throw new CustomerNotFoundException("Could not find any Customer with ID " + id);
		}
		
		customerRepository.deleteById(id);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		Customer customer = customerRepository.findByEmail(email);
		
		if (customer != null && customer.getId() != id) return false;
		
		return true;
	}
}
