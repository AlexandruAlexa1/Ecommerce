package com.ecommerce.admin.brand;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.admin.paging.SearchRepository;
import com.ecommerce.common.entity.Brand;

public interface BrandRepository extends SearchRepository<Brand, Integer> {
	
	public Long countById(int id);
	
	public Brand findByName(String name);
	
	@Query("SELECT brand FROM Brand brand WHERE brand.name LIKE %?1%")
	public Page<Brand> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT NEW Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
	public List<Brand> findAll();
}
