package com.ecommerce.article;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.common.entity.Article;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ArticleRepositoryTests {

	@Autowired
	private ArticleRepository repo;
	
	@Test
	public void testFindByAlias() {
		String alias = "spring-boot";
		Article article = repo.findByAlias(alias);
		
		assertThat(article).isNotNull();
		
		System.out.println(article);
	}
}
