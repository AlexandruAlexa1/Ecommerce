package com.ecommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.common.entity.product.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY p.name ASC")
	public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);
	
	public Product findByAlias(String alias);
	
	@Query("SELECT p FROM Product p WHERE " +
		       "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(p.fullDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	Page<Product> search(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("UPDATE Product p SET p.averageRating = COALESCE((SELECT AVG(r.rating) FROM Review r WHERE r.product.id = ?1), 0), "
			+ "p.reviewCount = (SELECT COUNT(r.id) FROM Review r WHERE r.product.id = ?1) "
			+ "WHERE p.id = ?1")
	@Modifying
	public void updateReviewCountAndAverageRating(Integer productId);
	
	@Query("UPDATE Product p SET p.questionCount = (SELECT COUNT(q.id) FROM Question q WHERE q.product.id = ?1) "
			+ "WHERE p.id = ?1")
	@Modifying
	public void updateQuestionCount(Integer productId);
}
