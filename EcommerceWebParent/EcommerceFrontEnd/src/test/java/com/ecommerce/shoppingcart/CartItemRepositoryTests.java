package com.ecommerce.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.CartItem;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {

	@Autowired private CartItemRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testSaveItem() {
		Integer customerId = 10;
		Integer productId = 5;
		
		Customer customer = entityManager.find(Customer.class, customerId);
		Product product = entityManager.find(Product.class, productId);
		
		CartItem item = new CartItem();
		item.setCustomer(customer);
		item.setProduct(product);
		item.setQuantity(1);
		
		CartItem savedItem = repo.save(item);
		
		assertThat(savedItem.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindByCustomer() {
		Integer customerId = 10;
		List<CartItem> listItems = repo.findByCustomer(new Customer(customerId));
		
		listItems.forEach(System.out::println);
		
		assertThat(listItems.size()).isEqualTo(5);
	}
	
	@Test
	public void findByCustomerAndProduct() {
		Integer customerId = 10;
		Integer productId = 80;

		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		
		assertThat(item).isNotNull();
		
		System.out.println(item);
	}
	
	@Test
	public void testUpdateQuantity() {
		Integer quantity = 100;
		Integer customerId = 10;
		Integer productId = 80;
		
		repo.updateQuantity(quantity, customerId, productId);
		
		CartItem cartItem = repo.findById(3).get();
		
		assertThat(cartItem.getQuantity()).isEqualTo(quantity);
	}
	
	@Test
	public void testDeleteByCustomerAndProduct() {
		Integer customerId = 10;
		Integer productId = 80;
		
		repo.deleteByCustomerAndProduct(customerId, productId);
		
		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		
		assertThat(item).isNull();
	}
}













