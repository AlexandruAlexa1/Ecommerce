package com.ecommerce.admin.shippingrate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.ShippingRate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShippingRateRepositoryTests {

	@Autowired private ShippingRateRepository repo;
	
	@Test
	public void testCreateNew() {
		ShippingRate rate = new ShippingRate();
		rate.setRate(12.5F);
		rate.setDays(2);
		rate.setCountry(new Country(1));
		rate.setState("ABC");
		rate.setCodSupported(true);
		
		ShippingRate savedRate = repo.save(rate);
		
		assertThat(savedRate).isNotNull();
		assertThat(savedRate.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindAll() {
		List<ShippingRate> listRates = (List<ShippingRate>) repo.findAll();
		listRates.forEach(rate -> System.out.println(rate));
		
		assertThat(listRates.size()).isEqualTo(2);
	}
	
	@Test
	public void testUpdate() {
		ShippingRate shippingRate = repo.findById(2).get();
		shippingRate.setCountry(new Country(1));
		shippingRate.setState("ABC");
		shippingRate.setRate(9.5F);
		shippingRate.setDays(1);
		shippingRate.setCodSupported(false);
		
		ShippingRate savedRate = repo.save(shippingRate);
		
		assertThat(savedRate.getDays()).isEqualTo(1);
	}
	
	@Test
	public void testUpdateCODSupport() {
		int id = 1;
		repo.updateCODSupport(id, true);
	}
}
