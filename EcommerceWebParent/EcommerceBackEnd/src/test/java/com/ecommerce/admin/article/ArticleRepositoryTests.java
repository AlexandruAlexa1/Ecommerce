package com.ecommerce.admin.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Article;
import com.ecommerce.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ArticleRepositoryTests {

	@Autowired
	private ArticleRepository repo;
	
	@Test
	public void testCreateArticle() {
		Article article = new Article();
		article.setUser(new User(1));
		article.setTitle("Payments");
		article.setContent("You can pay with a credit card or with cash. Alexandru!");
		article.setAlias("about");
		article.setPublished(true);
		article.setType("menu");
		article.setUpdatedTime(new Date());
		
		Article savedArticle = repo.save(article);
		
		assertThat(savedArticle).isNotNull();
		assertThat(savedArticle.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListArticles() {
		int pageNum = 0;
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Article> page = repo.findAll(pageable);
		
		List<Article> listArticles = page.getContent();
		
		assertThat(listArticles.size()).isGreaterThan(0);
		
		listArticles.forEach(article -> System.out.println(article));
	}
	
	@Test
	public void testGetArticle() {
		Integer articleId = 1;
		Article article = repo.findById(articleId).get();
		
		assertThat(article).isNotNull();
		
		System.out.println(article);
	}
	
	@Test
	public void testUpdateArticle() {
		Integer articleId = 2;
		String content = "You can pay with a credit card, cash or crypto.";
		
		Article article = repo.findById(articleId).get();
		article.setContent(content);
		
		Article savedArticle = repo.save(article);
		assertThat(savedArticle.getContent()).isEqualTo(content);
		
		System.out.println(article);
	}
	
	@Test
	public void testDeleteArticle() {
		Integer articleId = 2;
		repo.deleteById(articleId);
		
		Optional<Article> article = repo.findById(articleId);
		
		assertThat(!article.isPresent());
	}
	
	@Test
	public void testSearchArticle() {
		String keyword = "Alexandru";
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<Article> page = repo.findAll(keyword, pageable);
		
		List<Article> listArticles = page.getContent();
		
		assertThat(listArticles.size()).isGreaterThan(0);
		
		listArticles.forEach(article -> System.out.println(article));
	}
	
	@Test
	public void testUpdateEnableStatus() {
		Integer articleId = 1;
		boolean enabled = false;
		
		repo.updateEnableStatus(articleId, enabled);
		
		Article article = repo.findById(articleId).get();
		
		assertThat(article.isPublished()).isFalse();
	}
}















