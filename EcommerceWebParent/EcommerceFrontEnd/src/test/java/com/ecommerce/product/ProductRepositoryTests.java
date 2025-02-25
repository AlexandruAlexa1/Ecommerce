package com.ecommerce.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {

	@Autowired 
	ProductRepository repo;
	
	@Test
	public void testFindByAlias() {
		String alias = "Manfrotto-PIXI-Mini-Tripod,-Black-(MTPIXI-B)";
		Product product = repo.findByAlias(alias);
		
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testSearch() {
		String keyword = "iphone";
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Product> page = repo.search(keyword, pageable);
		List<Product> listResult = page.getContent();
		
		listResult.forEach(product -> System.out.println(product.getName()));
	}
}
