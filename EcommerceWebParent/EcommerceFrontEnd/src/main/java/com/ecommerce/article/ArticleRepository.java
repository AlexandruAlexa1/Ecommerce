package com.ecommerce.article;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.common.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	public Article findByAlias(String alias);
}
