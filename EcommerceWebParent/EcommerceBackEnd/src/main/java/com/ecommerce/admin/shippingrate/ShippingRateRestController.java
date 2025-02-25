package com.ecommerce.admin.shippingrate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admin.state.StateRepository;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.State;
import com.ecommerce.common.entity.StateDTO;

@RestController
public class ShippingRateRestController {

	@Autowired private StateRepository stateRepo;
	@Autowired private ShippingRateService rateService;
	
	@GetMapping("/shipping_rates/list_by_country/{countryId}")
	public List<StateDTO> listStates(@PathVariable(name = "countryId") int countryId) {
		List<State> listByCountry = stateRepo.findByCountryOrderByNameAsc(new Country(countryId));
		List<StateDTO> result = new ArrayList<>();
		
		for (State state : listByCountry) {
			result.add(new StateDTO(state.getId(), state.getName()));
		}
		
		return result;
	}

	@PostMapping("/get_shipping_cost")
	public String getShippingCost(Integer productId, Integer countryId, String state)
			throws ShippingRateNotFoundException {
		float shippingCost = rateService.calculateShippingCost(productId, countryId, state);
		return String.valueOf(shippingCost);
	}
}
