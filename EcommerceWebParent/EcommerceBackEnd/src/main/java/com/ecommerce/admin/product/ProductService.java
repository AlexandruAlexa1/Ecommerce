package com.ecommerce.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.ProductNotFoundException;

@Service
@Transactional
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 5;

	@Autowired private ProductRepository repo;
	
	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {
		Pageable pageable = helper.createPageable(pageNum, PRODUCTS_PER_PAGE);
		String keyword = helper.getKeyword();
		Page<Product> page = null;
		
		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			} else {
				page = repo.findAll(keyword, pageable);
			}
		} else {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
			} else {
				page = repo.findAll(pageable);
			}
		}
		
		helper.updateModelAttributes(pageNum, page);
	}
	
	public Page<Product> searchProducts(int pageNum, String keyword) {
		Pageable pageable = PageRequest.of(pageNum, PRODUCTS_PER_PAGE);
		Page<Product> page = repo.searchProductsByName(keyword, pageable);
		
		return page;
	}
	
	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		
		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replace(" ", "-");
			product.setAlias(defaultAlias);
		} else {
			product.setAlias(product.getAlias().replace(" ", "-"));
		}
		
		product.setUpdatedTime(new Date());
		
		Product updatedProduct = repo.save(product);
		repo.updateReviewCountAndAverageRating(updatedProduct.getId());
		
		return updatedProduct;
	}
	
	public void saveProductPrice(Product productInForm) {
		Product product = repo.findById(productInForm.getId()).get();
		product.setCost(productInForm.getCost());
		product.setPrice(productInForm.getPrice());
		product.setDiscountPercent(productInForm.getDiscountPercent());
		
		repo.save(product);
	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (productByName != null) {
				return "Duplicate";
			}
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}
	
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = repo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
		
		repo.deleteById(id);
	}
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
	}
}
