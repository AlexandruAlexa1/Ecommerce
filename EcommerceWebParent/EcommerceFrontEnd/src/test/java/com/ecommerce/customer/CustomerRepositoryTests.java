package com.ecommerce.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.AuthenticationType;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

	@Autowired private CustomerRepository repo;
	
	@Autowired private EntityManager entityManager;
	
	@Test
	public void testCreateCustomer1() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		
		Customer customer = new Customer();
		customer.setEmail("michael.jordan@testmail.com");
		customer.setPhoneNumber("777-111-999");
		customer.setPassword("Jump23@secure");
		customer.setFirstName("Michael");
		customer.setLastName("Jordan");
		customer.setAddressLine1("Strada Stadionului 23");
		customer.setAddressLine2("Bloc A1, Ap. 12");
		customer.setCity("Timișoara");
		customer.setState("TM");
		customer.setPostalCode("300012");
		customer.setEnabled(true);
		customer.setVerificationCode("MJ23TEST");
		customer.setCreatedTime(new Date());
		customer.setCountry(country);
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateCustomer2() {
		Integer countryId = 14;
		Country country = entityManager.find(Country.class, countryId);
		
		Customer customer = new Customer();
	    customer.setEmail("alice.smith@demo.com");
	    customer.setPhoneNumber("555-789-123");
	    customer.setPassword("MySecretPass456");
	    customer.setFirstName("Alice");
	    customer.setLastName("Smith");
	    customer.setAddressLine1("Bulevardul Eroilor 25");
	    customer.setAddressLine2("Bloc C, Etaj 3");
	    customer.setCity("Brașov");
	    customer.setState("BV");
	    customer.setPostalCode("500001");
	    customer.setEnabled(false);
	    customer.setVerificationCode("XYZ9876");
	    customer.setCreatedTime(new Date());
	    customer.setCountry(country);
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListCustomers() {
		Iterable<Customer> listCustomers = repo.findAll();
		listCustomers.forEach(System.out::println);
		
		assertThat(listCustomers).size().isGreaterThan(0);
	}
	
	@Test
	public void testUpdateCustomer() {
		Integer customerId = 2;
		
		Customer customer = repo.findById(customerId).get();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setVerificationCode("AA23");
		customer.setEnabled(false);
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isEqualTo(customerId);
	}
	
	@Test
	public void testGetCustomer() {
		Integer customerId = 2;
		
		Customer customer = repo.findById(customerId).get();
		
		assertThat(customer).isNotNull();
	}
	
	@Test
	public void testDeleteCustomer() {
		Integer customerId = 1;
		
		repo.deleteById(customerId);
		
		Optional<Customer> findById = repo.findById(customerId);
		
		assertThat(!findById.isPresent());
	}
	
	@Test
	public void testFindByEmail() {
		String email = "michael.jordan@testmail.com";
		Customer customer = repo.findByEmail(email);
		
		assertThat(customer.getId()).isGreaterThan(0);
		assertThat(customer).isNotNull();
		
		System.out.println(customer);
	}
	
	@Test
	public void testFindByVerificationCode() {
		String code = "AA23";
		Customer customer = repo.findByVerificationCode(code);
		
		assertThat(customer).isNotNull();
		assertThat(customer.getId()).isGreaterThan(0);
		
		System.out.println(customer);
	}
	
	@Test
	public void testEnableCustomer() {
		Integer customerId = 3;
		repo.enable(customerId);
		
		Customer customer = repo.findById(customerId).get();
		assertThat(customer.isEnabled()).isTrue();
	}
	
	@Test
	public void testUpdateAuthenticationType() {
		Integer id = 10;
		repo.updateAuthenticationType(id, AuthenticationType.DATABASE);
		
		Customer customer = repo.findById(id).get();
		
		assertThat(customer.getAuthenticationType()).isEqualTo(AuthenticationType.DATABASE);
	}
}













