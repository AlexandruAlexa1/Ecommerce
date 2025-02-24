package com.ecommerce.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	RoleRepository repository;

	@Test
	public void testCreateFirstRole() {
		Role role = new Role("Admin", "Manage everything");
		
		Role savedRole =  repository.save(role);
		
		assertThat(savedRole).isNotNull();
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role salesPerson = new Role("SalesPerson", "Manage product price, customers, shipping, orders and sales report");
		Role editor = new Role("Editor", "Manage categories, brands, products, articles and menus");
		Role shipper = new Role("Shipper", "View products, view orders and update order status");
		Role assistant = new Role("Assistant", "Manage questions and reviews");
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(salesPerson);
		roles.add(editor);
		roles.add(shipper);
		roles.add(assistant);
		
		repository.saveAll(roles);
	}
}
