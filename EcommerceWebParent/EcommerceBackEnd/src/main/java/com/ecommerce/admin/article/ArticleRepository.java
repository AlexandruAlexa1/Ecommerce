package com.ecommerce.admin.article;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.admin.paging.SearchRepository;
import com.ecommerce.common.entity.Article;

public interface ArticleRepository extends SearchRepository<Article, Integer> {
	
	@Query("SELECT a FROM Article a WHERE a.published = true")
	public List<Article> findAll();
	
	@Query("SELECT a FROM Article a WHERE a.title LIKE %?1% OR a.content LIKE %?1%")
	public Page<Article> findAll(String keyword, Pageable pageable);
	
	@Query("Update Article a SET a.published = ?2 WHERE a.id = ?1")
	@Modifying
	public void updateEnableStatus(Integer id, boolean enabled);

	public Long countById(Integer id);
}
