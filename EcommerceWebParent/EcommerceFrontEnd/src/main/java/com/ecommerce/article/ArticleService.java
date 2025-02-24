package com.ecommerce.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository repo;
	
	public Article findByAlias(String alias) throws ArticleNotFoundException {
		Article article = repo.findByAlias(alias);
		
		if (article == null) {
			throw new ArticleNotFoundException("Could not find article with alias: " + alias);
		}
		
		return article;
	}
}
