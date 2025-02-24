package com.ecommerce.admin.country;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Country;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {

	@Autowired
	private CountryRepository repo;
	
	@Test
	public void testCreateCountry() {
		Country country = new Country("America", "AMR");
		
		Country savedCountry = repo.save(country);
		
		assertThat(savedCountry).isNotNull();
	}
	
	@Test
	public void testListCountries() {
		List<Country> listCountires = repo.findAllByOrderByNameAsc();
		
		listCountires.forEach(System.out::println);
		
		assertThat(listCountires.size()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdateCountry() {
		String name = "Romania";
		
		Country country = repo.findById(1).get();
		country.setName(name);
		
		Country savedCountry = repo.save(country);
		
		assertThat(savedCountry.getName()).isEqualTo(name);
	}
	
	@Test
	public void testGetCountry() {
		Country country = repo.findById(1).get();
		
		System.out.println(country);
		
		assertThat(country).isNotNull();
	}
	
	@Test
	public void testDeleteCountry() {
		int id = 3;
		repo.deleteById(id);
		
		Optional<Country> country = repo.findById(id);
		
		assertThat(!country.isPresent());
	}
}
