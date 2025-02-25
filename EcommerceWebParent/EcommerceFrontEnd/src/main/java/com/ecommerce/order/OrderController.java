package com.ecommerce.order;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.ControllerHelper;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.order.Order;
import com.ecommerce.common.entity.order.OrderDetail;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.review.ReviewService;

@Controller
public class OrderController {
	@Autowired private OrderService orderService;
	@Autowired private ReviewService reviewService;
	@Autowired private ControllerHelper controllerHelper;
	
	@GetMapping("/orders")
	public String listFirstPage(HttpServletRequest request, Model model) {
		return listOrdersByPage(1, "orderTime", "desc", null, request, model);
	}
	
	@GetMapping("/orders/page/{pageNum}")
	public String listOrdersByPage(@PathVariable("pageNum") Integer pageNum, String sortField, String sortDir,
			String orderKeyword, HttpServletRequest request, Model model) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		Page<Order> page = orderService.listForCustomerByPage(pageNum, sortField, sortDir, customer.getId(), orderKeyword);
		List<Order> listOrders = page.getContent();
		
		long startCount = (pageNum - 1) * OrderService.ORDERS_PER_PAGE + 1;
		long endCount = startCount + OrderService.ORDERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("orderKeyword", orderKeyword);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		model.addAttribute("moduleURL", "/orders");
		
		return "orders/orders_customer";
	}
	
	@GetMapping("/orders/detail/{id}")
	public String viewOrderDetails(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		Order order = orderService.getOrder(id, customer);
		
		setProductReviewableStatus(customer, order);
		
		model.addAttribute("order", order);
		
		return "orders/order_details_modal";
	}

	private void setProductReviewableStatus(Customer customer, Order order) {
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Product product = orderDetail.getProduct();
			Integer productId = product.getId();
			
			boolean didCustomerReviewProduct = reviewService.didCustomerReviewProduct(customer, productId);
			product.setReviewedByCustomer(didCustomerReviewProduct);
			
			if (!didCustomerReviewProduct) {
				boolean canCustomerReviewProduct = reviewService.canCustomerReviewProduct(customer, productId);
				product.setCustomerCanReview(canCustomerReviewProduct);
			}
		}
	}
}








