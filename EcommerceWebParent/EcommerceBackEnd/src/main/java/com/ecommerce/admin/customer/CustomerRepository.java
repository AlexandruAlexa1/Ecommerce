package com.ecommerce.admin.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.admin.paging.SearchRepository;
import com.ecommerce.common.entity.Customer;

public interface CustomerRepository extends SearchRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.firstName LIKE %?1% "
			+ "OR c.lastName LIKE %?1% "
			+ "OR c.email LIKE %?1% "
			+ "OR c.addressLine1 LIKE %?1% "
			+ "OR c.addressLine2 LIKE %?1% "
			+ "OR c.city LIKE %?1% "
			+ "OR c.state LIKE %?1% "
			+ "OR c.country.name LIKE %?1% "
			+ "OR c.postalCode LIKE %?1%")
	public Page<Customer> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT c FROM Customer c WHERE c.email = ?1")
	public Customer findByEmail(String email);
	
	@Query("UPDATE Customer c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);
}
