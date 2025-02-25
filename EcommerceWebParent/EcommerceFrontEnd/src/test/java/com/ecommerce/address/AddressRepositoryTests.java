package com.ecommerce.address;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Address;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTests {

	@Autowired
	private AddressRepository repo;
	
	@Test
	public void testAddNew() {
		Integer customerId = 10;
		Integer countryId = 1;
		
		Address address = new Address();
		address.setFirstName("First Name");
		address.setLastName("Last Name");
		address.setPhoneNumber("20102401");
		address.setAddressLine1("Address 1");
		address.setCity("City");
		address.setState("State");
		address.setPostalCode("00001521");
		address.setCustomer(new Customer(customerId));
		address.setCountry(new Country(countryId));
		address.setDefaultForShipping(true);
		
		Address savedAddress = repo.save(address);
		
		assertThat(savedAddress.getId()).isGreaterThan(0);
		assertThat(savedAddress).isNotNull();
	}
	
	@Test
	public void testFindByCustomer() {
		Integer customerId = 3;
		List<Address> listAddresses = repo.findByCustomer(new Customer(customerId));
		assertThat(listAddresses.size()).isGreaterThan(0);
		
		listAddresses.forEach(address -> System.out.println(address));
	}
	
	@Test
	public void testFindByIdAndCustomer() {
		Integer addressId = 1;
		Integer customerId = 3;
		Address address = repo.findByIdAndCustomer(addressId, customerId);
		
		assertThat(address).isNotNull();
		System.out.println(address);
	}
	
	@Test
	public void testUpdate() {
		Integer addressId = 1;
		String firstName = "Al";
		
		Address address = repo.findById(addressId).get();
		address.setFirstName(firstName);
		
		Address updatedAddress = repo.save(address);
		
		assertThat(updatedAddress.getFirstName()).isEqualTo(firstName);
	}
	
	@Test
	public void testDeleteByIdAndCustomer() {
		Integer addressId = 1;
		Integer customerId = 3;
		repo.deleteByIdAndCustomer(addressId, customerId);
		
		Optional<Address> address = repo.findById(addressId);
		
		assertThat(!address.isPresent());
	}
	
	@Test
	public void testSetDefaultAddress() {
		Integer	addressId = 6;
		repo.setDefaultAddress(addressId);
		
		Address address = repo.findById(addressId).get();
		
		assertThat(address.isDefaultForShipping()).isTrue();
	}
	
	@Test
	public void testSetNonDefaultAddresses() {
		Integer addressId = 6;
		Integer	customerId = 10;
		
		repo.setNonDefaultForOthers(addressId, customerId);
	}
	
	@Test
	public void testGetDefault() {
		Integer customerId = 10;
		Address address = repo.findDefaultByCustomer(customerId);
		
		assertThat(address).isNotNull();
		
		System.out.println(address);
	}
}
