package com.ecommerce.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {

	public List<State> findByCountryOrderByNameAsc(Country country);
}
