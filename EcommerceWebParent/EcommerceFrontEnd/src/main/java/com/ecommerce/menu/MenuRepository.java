package com.ecommerce.menu;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.common.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	
	@Query("SELECT m FROM Menu m WHERE m.enabled = true")
	public List<Menu> findAll(Sort sort);
	
	public Menu findByAlias(String alias);
}
