package com.ecommerce.admin.section;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.admin.category.CategoryRepository;
import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;
import com.ecommerce.common.entity.Section;
import com.ecommerce.common.entity.SectionType;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SectionRepositoryTests {
	@Autowired private SectionRepository sectionRepo;
	@Autowired private CategoryRepository categoryRepo;

	@Test
	public void textCreateProductSection() {
		Section productSection = new Section();
		List<Product> listProducts = new ArrayList<>();
		listProducts.add(new Product(1));
		listProducts.add(new Product(16));
		listProducts.add(new Product(24));
		listProducts.add(new Product(4));
		listProducts.add(new Product(35));
		productSection.setProducts(listProducts);
		productSection.setHeading("Products");
		productSection.setDescription("New Products");
		productSection.setType(SectionType.PRODUCT);
		productSection.setPosition(1);
		productSection.setEnabled(true);
		
		Section savedSection = sectionRepo.save(productSection);
		
		assertThat(savedSection.getId()).isGreaterThan(0);
		assertThat(savedSection).isNotNull();
	}
	
	@Test
	public void testCreateCategorySection() {
		Section categorySection = new Section();
		List<Category> listCategories = new ArrayList<>();
		listCategories.add(new Category(10));
		listCategories.add(new Category(11));
		listCategories.add(new Category(12));
		listCategories.add(new Category(16));
		categorySection.setCategories(listCategories);
		categorySection.setHeading("Categories");
		categorySection.setDescription("New Categories");
		categorySection.setType(SectionType.CATEGORY);
		categorySection.setPosition(2);
		categorySection.setEnabled(false);
		
		Section savedSection = sectionRepo.save(categorySection);
		
		assertThat(savedSection.getId()).isGreaterThan(0);
		assertThat(savedSection).isNotNull();
	}
	
	@Test
	public void testCreateAllCategoriesSection() {
		Section categorySection = new Section();
		List<Category> listCategories = (List<Category>) categoryRepo.findAll();
		categorySection.setCategories(listCategories);
		categorySection.setHeading("All Categories");
		categorySection.setType(SectionType.CATEGORY);
		categorySection.setPosition(3);
		categorySection.setEnabled(true);
		
		Section savedSection = sectionRepo.save(categorySection);
		
		assertThat(savedSection.getId()).isGreaterThan(0);
		assertThat(savedSection).isNotNull();
	}
	
	@Test
	public void testCreateArticleSection() {
		Section section = new Section();
		List<Article> listArticles = new ArrayList<>();
		listArticles.add(new Article(1));
		listArticles.add(new Article(10));
		listArticles.add(new Article(5));
		listArticles.add(new Article(3));
		listArticles.add(new Article(4));
		section.setArticles(listArticles);
		section.setHeading("Articles");
		section.setDescription("New Articles");
		section.setType(SectionType.ARTICLE);
		section.setPosition(4);
		section.setEnabled(true);
		
		Section savedSection = sectionRepo.save(section);
		
		assertThat(savedSection.getId()).isGreaterThan(0);
		assertThat(savedSection).isNotNull();
	}
	
	@Test
	public void testCreateBrandSection() {
		Section section = new Section();
		List<Brand> listBrands = new ArrayList<>();
		listBrands.add(new Brand(10));
		listBrands.add(new Brand(11));
		listBrands.add(new Brand(12));
		section.setHeading("Brands");
		section.setBrands(listBrands);
		section.setType(SectionType.BRAND);
		section.setPosition(5);
		section.setEnabled(true);
		
		Section savedSection = sectionRepo.save(section);
		
		assertThat(savedSection.getId()).isGreaterThan(0);
		assertThat(savedSection).isNotNull();
	}

	@Test
	public void testListSections() {
		List<Section> listSections = sectionRepo.findAll();

		assertThat(listSections.size()).isGreaterThan(0);

		listSections.forEach(section -> System.out.println(section));
	}

	@Test
	public void testGetSection() {
		Integer sectionId = 18;

		Section section = sectionRepo.findById(sectionId).get();

		assertThat(section).isNotNull();

		System.out.println(section);
		System.out.println(section.getProducts());
	}

	@Test
	public void testUpdateSection() {
		Integer sectionId = 2;

		Section section = sectionRepo.findById(sectionId).get();
		section.setPosition(1);

		Section savedSection = sectionRepo.save(section);
		assertThat(savedSection.getPosition()).isEqualTo(1);
	}

	@Test
	public void testDeleteSection() {
		Integer sectionId = 12;

		sectionRepo.deleteById(sectionId);

		Optional<Section> section = sectionRepo.findById(sectionId);
		assertThat(!section.isPresent());
	}
	
	@Test
	public void testUpdateEnabledStatus() {
		Integer sectionId = 1;
		boolean status = false;
		
		sectionRepo.updateEnabledStatus(sectionId, status);
		
		Section section = sectionRepo.findById(sectionId).get();
		
		assertThat(section.isEnabled()).isFalse();
	}
}
