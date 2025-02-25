package com.ecommerce.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repository;
	
	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Electronics");
		Category savedCategory = repository.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(7);
		Category subCategory = new Category("Samsung", parent);
		Category savedCategory = repository.save(subCategory);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testGetCategory() {
		Category category = repository.findById(2).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		for (Category subCategory : children) {
			System.out.println("--" + subCategory.getName());
		}
		
		assertThat(children.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = repository.findAll();
		
		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());
			
			
			Set<Category> children = category.getChildren();
			for (Category subCategory : children) {
				System.out.println("--" + subCategory.getName());
				printChildren(subCategory);
			}
			}
		}
	}
	
	private void printChildren(Category parent) {
		Set<Category> children = parent.getChildren();
		
		for (Category subCategory : children) {
			System.out.println("----" + subCategory.getName());
		}
	}
	
	@Test
	public void testListRootCategories() {
		List<Category> listCategories = repository.findRootCategories(Sort.by("name").ascending());
		for (Category category : listCategories) {
			System.out.println(category.getName());
		}
		
		assertThat(listCategories.size()).isGreaterThan(0);
	}
	
	@Test
	public void testCountById() {
		int id = 5;
		Long countById = repository.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testFindByName() {
		String name = "Samsung";
		Category category = repository.findByName(name);
		
		assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(name);
	}
	
	@Test
	public void testFindByAlias() {
		String alias = "Samsung";
		Category category = repository.findByAlias(alias);
		
		assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(alias);
	}
	
	@Test
	public void testUpdateEnableStatus() {
		int id = 12;
		boolean enabled = true;
		
		repository.updateEnabledStatus(id, enabled);
	}
	
	@Test
	public void testSearchCategories() {
		String keyword = "computers";
		
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Category> page = repository.search(keyword, pageable);
		
		List<Category> categories = page.getContent();
		categories.forEach(category -> System.out.println(category.getName()));
		
		assertThat(categories.size()).isGreaterThan(0);
	}
}
