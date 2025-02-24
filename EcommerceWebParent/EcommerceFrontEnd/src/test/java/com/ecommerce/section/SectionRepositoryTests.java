package com.ecommerce.section;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.common.entity.Section;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SectionRepositoryTests {

	@Autowired
	private SectionRepository repo;
	
	@Test
	public void testListAllSections() {
		List<Section> listSections = repo.findAll();
		
		assertThat(listSections.size()).isGreaterThan(0);
		
		listSections.forEach(section -> System.out.println(section));
	}
}
