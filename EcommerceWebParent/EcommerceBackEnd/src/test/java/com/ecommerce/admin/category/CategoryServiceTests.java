package com.ecommerce.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecommerce.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@MockBean
	private CategoryRepository repository;
	
	@InjectMocks
	private CategoryService service;
	
	@Test
	public void testCheckUniqueInNewModeRetrunDuplicateName() {
		Integer id = null;
		String name = "Computers";
		String alias = "abc";
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(repository.findByName(name)).thenReturn(category);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		
		String result = service.checkUnique(id, name, alias);
		
		assertThat(result).isEqualTo("DuplicateName");
	}
	
	@Test
	public void testCheckUniqueInNewModeRetrunDuplicateAlias() {
		Integer id = null;
		String name = "Computer";
		String alias = "Computers";
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(category);
		
		String result = service.checkUnique(id, name, alias);
		
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testCheckUniqueInNewModeRetrunOK() {
		Integer id = null;
		String name = "Computers";
		String alias = "Computers";
		
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		
		String result = service.checkUnique(id, name, alias);
		
		assertThat(result).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeRetrunDuplicateName() {
		Integer id = 1;
		String name = "Computers";
		String alias = "abc";
		
		Category category = new Category(2, name, alias);
		
		Mockito.when(repository.findByName(name)).thenReturn(category);
		Mockito.when(repository.findByName(alias)).thenReturn(null);
		
		String result = service.checkUnique(id, name, alias);
		
		assertThat(result).isEqualTo("DuplicateName");
	}
	
	@Test
	public void testCheckUniqueInEditModeRetrunDuplicateAlias() {
		Integer id = 1;
		String name = "aaa";
		String alias = "Computers";
		
		Category category = new Category(2, name, alias);
		
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(category);
		
		String result = service.checkUnique(id, name, alias);
		
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testCheckUniqueInEditModeRetrunOK() {
		Integer id = 1;
		String name = "abc";
		String alias = "computers";
		
		Category category = new Category(1, name, alias);
		
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(category);
		
		String result = service.checkUnique(id, name, alias);
		
		assertThat(result).isEqualTo("OK");
	}
}
