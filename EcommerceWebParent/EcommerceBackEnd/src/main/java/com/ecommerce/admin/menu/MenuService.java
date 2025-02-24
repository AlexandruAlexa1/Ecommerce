package com.ecommerce.admin.menu;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Menu;
import com.ecommerce.common.exception.MenuNotFoundException;

@Service
@Transactional
public class MenuService {
	
	@Autowired private MenuRepository repo;
	
	public List<Menu> listAll() {
		List<Menu> listMenus = repo.findAll();
		return listMenus;
	}

	public void save(Menu menu) {
		if (menu.getAlias() == null || menu.getAlias().isEmpty()) {
			String alias = menu.getTitle().replace(" ", "-").toLowerCase();
			menu.setAlias(alias );
		} else {
			String alias = menu.getAlias().replace(" ", "-").toLowerCase();
			menu.setAlias(alias );
		}
		
		menu.setPosition(1);
		
		repo.save(menu);
	}

	public Menu get(Integer id) throws MenuNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new MenuNotFoundException("Could not find any Menu with ID: " + id);
		}
	}

	public void delete(Integer id) throws MenuNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById <= 0) {
			throw new MenuNotFoundException("Could not find any Menu with ID: " + id);
		}
		
		repo.deleteById(id);
	}
	
	public void updateEnableStatus(Integer id, boolean enabled) {
		repo.updateEnableStatus(id, enabled);
	}
	
	public void updatePosition(Integer id, String status) {
		Menu menu = repo.findById(id).get();
		Integer position = menu.getPosition();
		
		if (status.equals("up")) {
			position += 1;
		} else if (status.equals("down")){
			if (position == 1) {
				position = 1;
			} else {
				position -= 1;
			}
		}
		
		repo.updatePosition(id, position);
	}
}
