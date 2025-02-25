package com.ecommerce.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

	@Autowired
	private BrandRepository repository;
	
	@Test
	public void testCreateBrand1() {
		Category leptops = new Category(6);
		Brand acer = new Brand("Acer", "brand-logo.png");
		acer.getCategories().add(leptops);
		
		Brand savedBrand = repository.save(acer);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateBrand2() {
		Brand apple = new Brand("Apple", "brand-logo.png");
		apple.getCategories().add(new Category(4));
		apple.getCategories().add(new Category(7));
		
		Brand savedBrand = repository.save(apple);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateBrand3() {
		Category memory = new Category(24);
		Category hardDriver = new Category(29);
		
		Set<Category> categories = new HashSet<>();
		categories.add(memory);
		categories.add(hardDriver);
		
		Brand samsung =  new Brand("Samsung", "brand-logo.png");
		samsung.getCategories().addAll(categories);
		
		Brand savedBrand = repository.save(samsung);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindAll() {
		List<Brand> brands = (List<Brand>) repository.findAll();
		brands.forEach(brand -> System.out.println(brand));
		
		assertThat(brands.size()).isGreaterThan(0);
	}
	
	@Test
	public void testFindById() {
		int id = 3;
		Brand brand = repository.findById(id).get();
		
		System.out.println(brand);
		
		assertThat(brand).isNotNull();
	}
	
	@Test
	public void testUpdateName() {
		String name = "Samsung Electronics";
		Brand samsung = repository.findById(3).get();
		samsung.setName(name);
		
		Brand savedBrand = repository.save(samsung);
		assertThat(savedBrand.getName()).isEqualTo(name);
	}
	
	@Test
	public void testDeleteBrand() {
		int id = 6;
		repository.deleteById(id);
	}
	
	@Test
	public void testFindByName() {
		String name = "Acer";
		
		Brand brand = repository.findByName(name);
		
		assertThat(brand).isNotNull();
		assertThat(brand.getName()).isEqualTo(name);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 2;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Brand> page = repository.findAll(pageable);
		
		List<Brand> listBrands = page.getContent();
		listBrands.forEach(brand -> System.out.println(brand));
		
		assertThat(listBrands.size()).isGreaterThan(0);
	}
}










