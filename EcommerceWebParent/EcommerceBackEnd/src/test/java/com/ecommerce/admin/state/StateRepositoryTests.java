package com.ecommerce.admin.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.State;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {
	
	@Autowired
	private StateRepository repo;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void testCreateState() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		
		State state = new State("Brasov", country);
		
		State savedState = repo.save(state);
		
		assertThat(savedState).isNotNull();
		assertThat(savedState.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListStatesByCountry() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		
		List<State> listCountry = repo.findByCountryOrderByNameAsc(country);
		listCountry.forEach(System.out::println);
		
		assertThat(listCountry.size()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdateState() {
		Integer stateId = 3;
		String name = "Botosani";
		
		State state = repo.findById(stateId).get();
		state.setName(name);
		
		State savedState = repo.save(state);
		
		assertThat(savedState.getName()).isEqualTo(name);
	}
	
	@Test
	public void testDeleteState() {
		Integer stateId = 3;
		repo.deleteById(stateId);
		
		Optional<State> state = repo.findById(stateId);
		
		assertThat(!state.isPresent());
	}
}








