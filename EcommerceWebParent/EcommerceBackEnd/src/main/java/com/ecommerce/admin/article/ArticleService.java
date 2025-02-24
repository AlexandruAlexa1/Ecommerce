package com.ecommerce.admin.article;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.common.entity.Article;

@Service
@Transactional
public class ArticleService {
	public static final int ARTICLES_PER_PAGE = 5;
	@Autowired private ArticleRepository repo;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, ARTICLES_PER_PAGE, repo);
	}
	
	public List<Article> listAll() {
		return (List<Article>) repo.findAll();
	}
	
	public void updateEnableStatus(Integer id, boolean enabled) {
		repo.updateEnableStatus(id, enabled);
	}

	public void save(Article article) {
		if (article.getAlias() == null || article.getAlias().isEmpty()) {
			String alias = article.getTitle().replace(" ", "-").toLowerCase();
			article.setAlias(alias);
		} else {
			String alias = article.getAlias().replace(" ", "-").toLowerCase();
			article.setAlias(alias);
		}
		
		article.setUpdatedTime(new Date());
		
		repo.save(article);
	}

	public Article get(Integer id) throws ArticleNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ArticleNotFoundException("Could not find any Article with ID " + id);
		}
	}

	public void delete(Integer id) throws ArticleNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new ArticleNotFoundException("Could not find any Article with ID " + id);
		}
		
		repo.deleteById(id);
	}
}
