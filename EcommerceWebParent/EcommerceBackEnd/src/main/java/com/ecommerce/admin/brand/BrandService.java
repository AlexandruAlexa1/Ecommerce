package com.ecommerce.admin.brand;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.common.entity.Brand;

@Service
public class BrandService {
	public static final int BRANDS_PER_PAGE = 5;
	
	@Autowired private BrandRepository repo;
	
	public List<Brand> listAll() {
		return repo.findAll();
	}
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, BRANDS_PER_PAGE, repo);
	}

	public Brand save(Brand brand) {
		return repo.save(brand);
	}
	
	public Brand get(int id) throws BrandNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new BrandNotFoundException("Could not find any brand with id: " + id);
		}
	}
	
	public void delete(int id) throws BrandNotFoundException {
		Long countById = repo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new BrandNotFoundException("Could not find any brand with id: " + id);
		}
		
		repo.deleteById(id);
	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Brand brandByName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (brandByName != null) return "Duplicate";
		} else {
			if (brandByName != null && brandByName.getId() != id) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}

	
}
