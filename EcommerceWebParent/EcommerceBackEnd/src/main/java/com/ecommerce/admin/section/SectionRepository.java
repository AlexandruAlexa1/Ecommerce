package com.ecommerce.admin.section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.common.entity.Section;

public interface SectionRepository extends JpaRepository<Section, Integer> {
	
	@Query("SELECT s FROM Section s ORDER BY s.position ASC")
	public List<Section> findAll();

	@Query("UPDATE Section s SET s.enabled = ?2 WHERE s.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);

	@Query("UPDATE Section s SET s.position = ?2 WHERE s.id = ?1")
	@Modifying
	public void updatePosition(Integer id, int position);
}
