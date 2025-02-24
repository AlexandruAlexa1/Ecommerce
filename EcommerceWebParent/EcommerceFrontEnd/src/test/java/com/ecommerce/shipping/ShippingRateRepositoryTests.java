package com.ecommerce.shipping;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.ShippingRate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ShippingRateRepositoryTests {

	@Autowired
	private ShippingRateRepository repo;
	
	@Test
	public void testFindByCountryAndState() {
		Country country = new Country(234);
		String state = "Hawaii";
		ShippingRate shippingRate = repo.findByCountryAndState(country, state);
		
		assertThat(shippingRate).isNotNull();
		System.out.println(shippingRate);
	}
}
