package com.ecommerce.admin.menu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.common.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	
	@Query("SELECT m FROM Menu m ORDER BY m.type DESC, m.position ASC")
	public List<Menu> findAll();
	
	Long countById(Integer id);
	
	@Query("UPDATE Menu m SET m.enabled = ?2 WHERE m.id = ?1")
	@Modifying
	public void updateEnableStatus(Integer id, boolean enabled);
	
	@Query("UPDATE Menu m SET m.position = ?2 WHERE m.id = ?1")
	@Modifying
	public void updatePosition(Integer id, Integer position);
}
