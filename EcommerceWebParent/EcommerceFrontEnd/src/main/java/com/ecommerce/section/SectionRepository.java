package com.ecommerce.section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.common.entity.Section;

public interface SectionRepository extends JpaRepository<Section, Integer> {

	@Query("SELECT s FROM Section s WHERE s.enabled = true ORDER BY s.position ASC")
	public List<Section> findAll();
}
