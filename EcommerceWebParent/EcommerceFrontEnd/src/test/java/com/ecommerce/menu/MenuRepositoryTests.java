package com.ecommerce.menu;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.common.entity.Menu;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MenuRepositoryTests {

	@Autowired private MenuRepository repo;
	
	@Test
	public void testFindByAlias() {
		String alias = "new-articles";
		Menu menu = repo.findByAlias(alias);
		
		assertThat(menu).isNotNull();
		
		System.out.println(menu);
	}
}
