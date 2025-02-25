package com.ecommerce.section;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Section;

@Service
public class SectionService {
	@Autowired private SectionRepository repo;
	
	public List<Section> listAll() {
		return repo.findAll();
	}
}
