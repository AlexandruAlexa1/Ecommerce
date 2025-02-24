package com.ecommerce.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Menu;
import com.ecommerce.common.exception.MenuNotFoundException;

@Service
public class MenuService {

	@Autowired private MenuRepository repo;
	
	public List<Menu> listAll() {
		Sort sort = Sort.by("position").ascending();
		return repo.findAll(sort);
	}
	
	public Menu findByAlias(String alias) throws MenuNotFoundException {
		Menu menu = repo.findByAlias(alias);
		
		if (menu == null) {
			throw new MenuNotFoundException("Could not find menu with alias " + alias);
		}
		
		return menu;
	}
}
