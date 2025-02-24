package com.ecommerce.menu;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.category.CategoryService;
import com.ecommerce.common.entity.Category;
import com.ecommerce.common.entity.Menu;

@Component
public class MenuFilter implements Filter {
	
	@Autowired private MenuService menuService;
	@Autowired private CategoryService categoryService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		List<Menu> listMenus = menuService.listAll();
		List<Category> listCategories = categoryService.listAll();
		
		request.setAttribute("listMenus", listMenus);
		request.setAttribute("listCategories", listCategories);
		
		chain.doFilter(request, response);
	}

}
