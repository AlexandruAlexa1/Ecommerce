package com.ecommerce.admin.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Category;
import com.ecommerce.common.exception.CategoryNotFoundException;

@Service
@Transactional
public class CategoryService {
	public static final int ROOT_CATEGORIES_PER_PAGE = 1;

	@Autowired
	private CategoryRepository repository;
	
	public List<Category> listAll() {
		return (List<Category>) repository.findAll();
	}
	
	public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNumber, String sortDir,
			String keyword) {
		Sort sort = Sort.by("name");
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, ROOT_CATEGORIES_PER_PAGE, sort);
		
		Page<Category> pageCategories = null;
		
		if (keyword != null && !keyword.isEmpty()) {
			pageCategories = repository.search(keyword, pageable);
		} else {
			pageCategories = repository.findRootCategories(pageable);
		}
		
		List<Category> rootCategories = pageCategories.getContent();
		
		pageInfo.setTotalElements(pageCategories.getTotalElements());
		pageInfo.setTotalPages(pageCategories.getTotalPages());
		
		if (keyword != null && !keyword.isEmpty()) {
			List<Category> searchResult = pageCategories.getContent();
			for (Category category : searchResult) {
				category.setHasChildren(category.getChildren().size() > 0);
			}
			
			return searchResult;
		} else {
			return listHerarchicalCategories(rootCategories, sortDir);
		}
	}
	
	private List<Category> listHerarchicalCategories(List<Category> rootCategories, String sortDir) {
		List<Category> herarchicalCategories= new ArrayList<>();
		
		for (Category rootCategory : rootCategories) {
			herarchicalCategories.add(Category.copyFull(rootCategory));
			
			Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);
			for (Category subCategory : children) {
				String name = "--" + subCategory.getName();
				herarchicalCategories.add(Category.copyFull(subCategory, name));
				
				listSubHierarchicalCategories(herarchicalCategories, subCategory, sortDir);
			}
		}
		
		return herarchicalCategories;
	}

	private void listSubHierarchicalCategories(List<Category> herarchicalCategories, Category category, String sortDir) {
		Set<Category> children = sortSubCategories(category.getChildren(), sortDir);
		for(Category subCategory : children) {
			String name = "----" + subCategory.getName();
			herarchicalCategories.add(Category.copyFull(subCategory, name));
		}
	}

	public List<Category> listCategoriesUsedInForm() {
		List<Category> categoriesUsedInForm = new ArrayList<>();
		
		Iterable<Category> categoriesInDB = repository.findRootCategories(Sort.by("name").ascending());
		
		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUsedInForm.add(Category.copyIdAndName(category));
			
				Set<Category> children = sortSubCategories(category.getChildren());
				
				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
					listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory);
				}	
			}
		}
		
		return categoriesUsedInForm;
	}
	
	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent) {
		Set<Category> children = sortSubCategories(parent.getChildren());
		
		for (Category subCategory : children) {
			String name = "----" + subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
		}
	}
	
	public Category save(Category category) {
		Category parent = category.getParent();
		if (parent != null) {
			String allParentIDs = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
			allParentIDs += String.valueOf(parent.getId() + "-");
			category.setAllParentIDs(allParentIDs);
		}
		
		return repository.save(category);
	}
	
	public Category get(Integer id) throws CategoryNotFoundException {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("Could not find any category with id: " + id);
		}
	}
	
	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);
		
		Category categoryByName = repository.findByName(name);
		
		if (isCreatingNew) {
			if (categoryByName != null) {
				return "DuplicateName";
			} else {
				Category caregoryByAlias = repository.findByAlias(alias);
				if (caregoryByAlias != null) {
					return "DuplicateAlias";
				}
			}
		} else {
			if (categoryByName != null && categoryByName.getId() != id) {
				return "DuplicateName";
			}
			
			Category caregoryByAlias = repository.findByAlias(alias);
			if (caregoryByAlias != null && caregoryByAlias.getId() != id) {
				return "DuplicateAlias";
			}
		}
		
		return "OK";
	}
	
	private SortedSet<Category> sortSubCategories(Set<Category> children) {
		return sortSubCategories(children, "asc");
	}
	
	private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
		SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category cat1, Category cat2) {
				if (sortDir.equals("asc")) {
					return cat1.getName().compareTo(cat2.getName());
				} else {
					return cat1.getName().compareTo(cat2.getName());
				}
			}
		});
		
		sortedChildren.addAll(children);
		
		return sortedChildren;
	}
	
	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		repository.updateEnabledStatus(id, enabled);
	}
	
	public void delete(Integer id) throws CategoryNotFoundException {
		Long countById = repository.countById(id);
		if (countById == null || countById == 0) {
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}
		
		repository.deleteById(id);
	}
}
