package com.ecommerce.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Role;
import com.ecommerce.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void testCreateUsers() {
		User user = new User("admin@yahoo.com", passwordEncoder.encode("password"), "Admin", "Admin");
		user.setEnabled(true);
		
		Role role = entityManager.find(Role.class, 1);
		
		user.addRole(role);
		
		User saveUser = repository.save(user);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUser2() {
		User user = new User("editor@yahoo.com", passwordEncoder.encode("password"), "Editor", "Editor");
		user.setEnabled(true);
		
		Role role = entityManager.find(Role.class, 3);
		
		user.addRole(role);
		
		User saveUser = repository.save(user);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUser3() {
		User user = new User("sales_person@yahoo.com", passwordEncoder.encode("password"), "Sales", "Person");
		user.setEnabled(true);
		
		Role role = entityManager.find(Role.class, 2);
		
		user.addRole(role);
		
		User saveUser = repository.save(user);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUser4() {
		User user = new User("shipper@yahoo.com", passwordEncoder.encode("password"), "Shipper", "Shipper");
		user.setEnabled(true);
		
		Role role = entityManager.find(Role.class, 4);
		
		user.addRole(role);
		
		User saveUser = repository.save(user);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUser5() {
		User user = new User("assistant@yahoo.com", passwordEncoder.encode("password"), "Assistant", "Assistant");
		user.setEnabled(true);
		
		Role role = entityManager.find(Role.class, 5);
		
		user.addRole(role);
		
		User saveUser = repository.save(user);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User user = new User("aa@yahoo.com", "0000", "Alexa", "Al");
		user.setEnabled(true);
		Role editor = new Role(3);
		Role assistant = new Role(5);
		
		user.addRole(editor);
		user.addRole(assistant);
		
		User saveUser = repository.save(user);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repository.findAll();
		
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User user = repository.findById(1).get();
		System.out.println("USER: " + user);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUser() {
		User user = repository.findById(1).get();
		user.setEnabled(true);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User user = repository.findById(2).get();
		Role salesPerson = new Role(5);
		Role shipper = new Role(4);
		
		user.getRoles().remove(salesPerson);
		user.addRole(shipper);
	
		repository.save(user);
	}
	
	@Test
	public void testDeleteUser() {
		repository.deleteById(2);
	}

	@Test
	public void testGetUserByEmail() {
		String email = "x.alx@yahoo.com";
		User user = repository.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		int id = 1;
		Long contById = repository.countById(id);
		
		assertThat(contById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id  = 9;
		repository.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id  = 9;
		repository.updateEnabledStatus(id, true);
	}
	

	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repository.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "Alexa";
		
		int pageNumber = 0;
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<User> page = repository.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
}








