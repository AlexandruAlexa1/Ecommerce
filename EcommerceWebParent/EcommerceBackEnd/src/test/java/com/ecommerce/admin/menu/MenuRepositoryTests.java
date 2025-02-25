package com.ecommerce.admin.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.Menu;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class MenuRepositoryTests {

	@Autowired private MenuRepository repo;
	
	@Test
	public void testCreateMenu() {
		Integer articleId = 7;
		
		Menu menu = new Menu();
		menu.setArticle(new Article(articleId));
		menu.setTitle("About Us");
		menu.setAlias("about-us");
		menu.setType("Footer Menu");
		menu.setEnabled(true);
		menu.setPosition(1);
		
		Menu savedMenu = repo.save(menu);
		
		assertThat(savedMenu).isNotNull();
	}
	
	@Test
	public void testListMenus() {
		List<Menu> listMenus = repo.findAll();
		
		assertThat(listMenus.size()).isGreaterThan(0);
		
		listMenus.forEach(menu -> System.out.println(menu));
	}
	
	@Test
	public void testGetMenu() {
		Integer menuId = 2;
		Menu menu = repo.findById(menuId).get();
		
		assertThat(menu).isNotNull();
		assertThat(menu.getId()).isEqualTo(menuId);
		
		System.out.println(menu);
	}
	
	@Test
	public void testUpdateMenu() {
		Integer menuId = 4;
		String title = "About";
		
		Menu menu = repo.findById(menuId).get();
		menu.setTitle(title);
		
		Menu savedMenu = repo.save(menu);
		
		assertThat(savedMenu.getTitle()).isEqualTo(title);
	}
	
	@Test
	public void testDeleteMenu() {
		Integer menuId = 4;
		repo.deleteById(menuId);
		
		Optional<Menu> menu = repo.findById(menuId);
		
		assertThat(!menu.isPresent());
	}
	
	@Test
	public void testUpdateEnableStatus() {
		Integer menuId = 5;
		boolean enabled = false;
		repo.updateEnableStatus(menuId, enabled);
		
		Menu menu = repo.findById(menuId).get();
		
		assertThat(menu.isEnabled()).isFalse();
	}
	
	@Test
	public void testUpdatePosition() {
		Integer menuId = 6;
		Integer position = 2;
		repo.updatePosition(menuId, position);
		
		Menu menu = repo.findById(menuId).get();
		
		assertThat(menu.getPosition()).isEqualTo(position);
	}
}
