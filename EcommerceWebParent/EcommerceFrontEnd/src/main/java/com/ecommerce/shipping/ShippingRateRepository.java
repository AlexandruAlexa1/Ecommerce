package com.ecommerce.shipping;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

	public ShippingRate findByCountryAndState(Country country, String state);
}
