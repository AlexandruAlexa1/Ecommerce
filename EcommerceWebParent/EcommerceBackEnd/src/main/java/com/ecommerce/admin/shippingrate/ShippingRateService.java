package com.ecommerce.admin.shippingrate;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.country.CountryRepository;
import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.product.ProductRepository;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.ShippingRate;
import com.ecommerce.common.entity.product.Product;

@Service
@Transactional
public class ShippingRateService {
	public static final int RATES_PER_PAGE = 5;
	private static final int DIM_DIVISOR = 139;
	
	@Autowired private ShippingRateRepository shippingRepo;
	@Autowired private CountryRepository countryRepo;
	@Autowired private ProductRepository productRepo;

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, RATES_PER_PAGE, shippingRepo);
	}
	
	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	
	public void save(ShippingRate rateInForm) throws ShippingRateAlreadyExistsException {
		ShippingRate rateInDB = shippingRepo.findByCountryAndState(
				rateInForm.getCountry().getId(), rateInForm.getState());
		boolean foundExistingRateInNewMode = rateInForm.getId() == null && rateInDB != null;
		boolean foundDifferentExistingRateInEditMode = rateInForm.getId() != null && rateInDB != null;
		
		if (foundExistingRateInNewMode || foundDifferentExistingRateInEditMode) {
			throw new ShippingRateAlreadyExistsException("There's already a rate for the destination "
					+ rateInForm.getCountry().getName() + ", " + rateInForm.getState());
		}
		
		shippingRepo.save(rateInForm);
	}
	
	public ShippingRate get(int id) throws ShippingRateNotFoundException {
		try {
			return shippingRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
		}
	}
	
	public void updateCODSupport(int id, boolean enabled) throws ShippingRateNotFoundException {
		Long countById = shippingRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
		}
		
		shippingRepo.updateCODSupport(id, enabled);
	}
	
	public void delete(int id) throws ShippingRateNotFoundException {
		Long countById = shippingRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
		}
		
		shippingRepo.deleteById(id);
	}
	
	public float calculateShippingCost(Integer productId, Integer countryId, String state)
			throws ShippingRateNotFoundException {
		ShippingRate shippingRate = shippingRepo.findByCountryAndState(countryId, state);
		
		if (shippingRate == null) {
			throw new ShippingRateNotFoundException("No shipping rate found for the given"
					+ "destination. You have to enter shipping cost manually.");
		}
		
		Product product = productRepo.findById(productId).get();
		
		float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
		float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;
		
		return finalWeight * shippingRate.getRate();
	}
}
