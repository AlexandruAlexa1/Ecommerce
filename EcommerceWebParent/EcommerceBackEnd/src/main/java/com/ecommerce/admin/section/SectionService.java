package com.ecommerce.admin.section;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Section;

@Service
@Transactional
public class SectionService {
	@Autowired private SectionRepository sectionRepo;
	
	public List<Section> listSections() {
		return sectionRepo.findAll();
	}

	public void saveSection(Section section) {
		setPosition(section);
		sectionRepo.save(section);
	}
	
	public void setPosition(Section section) {
		boolean isEditMode = section.getId() != null;
		int position = 0;
		
		if (isEditMode) {
			Section sectionInDB = sectionRepo.findById(section.getId()).get();
			position = sectionInDB.getPosition();
		} else {
			List<Section> listSections = sectionRepo.findAll();

			if (listSections.size() > 0) {
				Section maxPosition = listSections.stream().max(Comparator.comparingInt(Section::getPosition)).get();
				position = maxPosition.getPosition() + 1;
			} else {
				position = 1;
			}
		}
		
		section.setPosition(position);
	}
	
	public void updateEnabledStatus(Integer id, boolean enabled) {
		sectionRepo.updateEnabledStatus(id, enabled);
	}
	
	public Section get(Integer id) throws SectionNotFoundException {
		try {
			return sectionRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new SectionNotFoundException("Could not find any Section with ID " + id);
		}
	}
	
	public void delete(Integer id) throws SectionNotFoundException {
		Long countById = sectionRepo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new SectionNotFoundException("Could not find any Section with ID " + id);
		}
		
		sectionRepo.deleteById(id);
	}
	
	public void updatePosition(Integer id, String status) {
		Section section = sectionRepo.findById(id).get();
		int position = section.getPosition();
		
		if (status.equals("up")) {
			position++;
		} else {
			if (position != 1) {
				position--;
			}
		}
		
		sectionRepo.updatePosition(id, position);
	}
}
