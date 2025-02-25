package com.ecommerce.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 3);
		Category category = entityManager.find(Category.class, 32);
		
		Product product = new Product();
		product.setName("Samsung s22");
		product.setAlias("samsung_s22");
		product.setShortDescription("This will be my phone");
		product.setFullDescription("This will be my phone");
		
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setPrice(3000);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		product.setEnabled(true);
		product.setInStock(true);
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllProducts() {
		Iterable<Product> products = repo.findAll();
		
		products.forEach(System.out::println);
	}
	
	@Test
	public void testGetProduct() {
		Product product = repo.findById(3).get();
		
		System.out.println(product);
		
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testUpdateProduct() {
		int id = 1;
		float price = 750;
		Product product = repo.findById(id).get();
		product.setPrice(price);
		
		Product savedProduct = repo.save(product);
		
		// Product savedProduct = entityManager.find(Product.class, id);
		
		assertThat(savedProduct.getPrice()).isEqualTo(price);
	}
	
	@Test
	public void testDeleteProduct() {
		int id = 3;
		repo.deleteById(id);
		
		Optional<Product> result = repo.findById(id);
		
		assertThat(!result.isPresent());
	}
	
	@Test
	public void testFindByName() {
		String name = "Samsung A52";
		Product product = repo.findByName(name);
		
		System.out.println(product);
		
		assertThat(product).isNotNull();
		assertThat(product.getName()).isEqualTo(name);
	}
	
	@Test
	public void testEnableProduct() {
		int id = 1;
		boolean enabled = true;
		
		repo.updateEnabledStatus(id, enabled);
	}
	
	@Test
	public void testCountById() {
		Long countById = repo.countById(7);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testSaveProductWithImages() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();

		product.setMainImage("main-image.jpg");
		product.addExtraImage("extra-image-1.jpg");
		product.addExtraImage("extra-image-2.jpg");
		product.addExtraImage("extra-image-3.jpg");
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}
	
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();
		
		product.addDetail("Camera", "64MB");
		product.addDetail("Memory", "120GB");
		product.addDetail("RAM", "8GB");
		
		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();
	}
	
	@Test
	public void testUpdateReviewCountAndAverageRating() {
		Integer productId = 66;
		repo.updateReviewCountAndAverageRating(productId);
	}
	
	@Test
	public void testUpdateAnswerCount() {
		Integer productId = 6;
		repo.updateAnswerCount(productId);
	}
}


