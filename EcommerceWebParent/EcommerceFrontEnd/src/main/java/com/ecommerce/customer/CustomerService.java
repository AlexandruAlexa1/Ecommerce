package com.ecommerce.customer;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.AuthenticationType;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.exception.CustomerNotFoundException;
import com.ecommerce.setting.CountryRepository;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class CustomerService {
	
	@Autowired private CountryRepository countryRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<Country> listAllCountries() {
		return countryRepository.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(String email) {
		Customer customer = customerRepository.findByEmail(email);
		return customer == null;
	}
	
	public void registerCustomer(Customer customer) {
		encodePassword(customer);
//		customer.setEnabled(false);
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		customer.setAuthenticationType(AuthenticationType.DATABASE);
		
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		
		customerRepository.save(customer);
	}

	private void encodePassword(Customer customer) {
		String encodePassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodePassword);
	}
	
	public boolean verify(String verificationCode) {
		Customer customer = customerRepository.findByVerificationCode(verificationCode);
		
		if (customer == null || customer.isEnabled()) {
			return false;
		} else {
			customerRepository.enable(customer.getId());
			return true;
		}
	}
	
	public void updateAuthenticationType(Customer customer, AuthenticationType type) {
		if (!customer.getAuthenticationType().equals(type)) {
			customerRepository.updateAuthenticationType(customer.getId(), type);
		}
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	public void addNewCustomerUponAuthLogin(String name, String email, String countryCode,
			AuthenticationType authenticationType ) {
		Customer customer = new Customer();
		customer.setEmail(email);
		setName(name, customer);
		
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		customer.setAuthenticationType(authenticationType);
		customer.setPassword("");
		customer.setAddressLine1("");
		customer.setCity("");
		customer.setState("");
		customer.setPhoneNumber("");
		customer.setPostalCode("");
		customer.setCountry(countryRepository.findByCode(countryCode));
		
		customerRepository.save(customer);
	}

	private void setName(String name, Customer customer) {
		String[] nameArray = name.split(" ");
		if (nameArray.length < 2) {
			customer.setFirstName(name);
			customer.setLastName("");
		} else {
			String firstName = nameArray[0];
			customer.setFirstName(firstName);
			
			String lastName = name.replace(firstName + " ", "");
			customer.setLastName(lastName);
		}
	}
	
	public void update(Customer customerInForm) {
		Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
		
		if (customerInDB.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
			if (!customerInForm.getPassword().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
				customerInForm.setPassword(encodedPassword);
			} else {
				customerInForm.setPassword(customerInDB.getPassword());
			}
		} else {
			customerInForm.setPassword(customerInDB.getPassword());
		}
		
		customerInForm.setEnabled(customerInDB.isEnabled());
		customerInForm.setCreatedTime(customerInDB.getCreatedTime());
		customerInForm.setVerificationCode(customerInDB.getVerificationCode());
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		customerRepository.save(customerInForm);
	}

	public String updateRequestPasswordToken(String email) throws CustomerNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		if (customer != null) {
			String token = RandomString.make(30);
			customer.setResetPasswordToken(token);
			customerRepository.save(customer);
			
			return token;
		} else {
			throw new CustomerNotFoundException("Could not find any customer with the email " + email);
		}
	}
	
	public Customer getByResetPasswordToken(String token) {
		return customerRepository.findByResetPasswordToken(token);
	}
	
	public void updatePassword(String token, String newPassword) throws CustomerNotFoundException {
		Customer customer = customerRepository.findByResetPasswordToken(token);
		if (customer == null) {
			throw new CustomerNotFoundException("No customer found: invalid token");
		}
		
		customer.setPassword(newPassword);
		customer.setResetPasswordToken(null);
		encodePassword(customer);
		
		customerRepository.save(customer);
	}
}
