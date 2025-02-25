package com.ecommerce.admin.order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.admin.security.EcommerceUserDetails;
import com.ecommerce.admin.setting.SettingService;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.order.Order;
import com.ecommerce.common.entity.order.OrderDetail;
import com.ecommerce.common.entity.order.OrderStatus;
import com.ecommerce.common.entity.order.OrderTrack;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.entity.setting.Setting;
import com.ecommerce.common.exception.OrderNotFoundException;

@Controller
public class OrderController {
	@Autowired private OrderService orderService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/orders")
	public String listFirstPage(Model model, HttpServletRequest request, @AuthenticationPrincipal EcommerceUserDetails loggedUser) {
		return listByPage(1, "orderTime", "desc", null, model, request, loggedUser);
	}

	@GetMapping("/orders/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, String sortField, String sortDir,
			String keyword, Model model, HttpServletRequest request,
			@AuthenticationPrincipal EcommerceUserDetails loggedUser) {
		if (sortDir == null || sortDir.isEmpty()) sortDir = "asc";
		
		Page<Order> page = orderService.listByPage(pageNum, sortField, sortDir, keyword);
		List<Order> listOrders = page.getContent();
		
		long startCount = (pageNum - 1) * OrderService.ORDERS_PER_PAGE + 1;
		long endCount = startCount + OrderService.ORDERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		model.addAttribute("moduleURL", "/orders");
		
		loadCurrencySettings(request);
		
		if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("SalesPerson") && loggedUser.hasRole("Shipper")) {
			return "orders/orders_shipper";
		}
		
		return "orders/orders";
	}
	
	private void loadCurrencySettings(HttpServletRequest request) {
		List<Setting> currencySettings = settingService.getCurrencySettings();
		
		for (Setting setting : currencySettings) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}
	}
	
	@GetMapping("/orders/detail/{id}")
	public String viewOrderDetails(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra, HttpServletRequest request,
			@AuthenticationPrincipal EcommerceUserDetails loggedUser) {
		try {
			Order order = orderService.get(id);
			loadCurrencySettings(request);
			
			boolean isVisibleForAdminOrSalesperson = false;
			
			if (loggedUser.hasRole("Admin") || loggedUser.hasRole("SalesPerson")) {
				isVisibleForAdminOrSalesperson = true;
			}
			
			model.addAttribute("isVisibleForAdminOrSalesperson", isVisibleForAdminOrSalesperson);
			model.addAttribute("order", order);
			
			return "orders/order_detail_modal";
		} catch (OrderNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/orders";
		}
	}
	
	@GetMapping("/orders/delete/{id}")
	public String deleteOrder(@PathVariable(name = "id") Integer id, RedirectAttributes ra) {
		try {
			orderService.delete(id);
			ra.addFlashAttribute("message", "The order ID " + id + " has been deleted.");
		} catch (OrderNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/orders";
	}
	
	@GetMapping("/orders/edit/{id}")
	public String editOrder(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Order order = orderService.get(id);
			List<Country> listCountries = orderService.listAllCountries();
			
			model.addAttribute("pageTitle", "Edit Order (ID: " + id + ")");
			model.addAttribute("order", order);
			model.addAttribute("listCountries", listCountries);
			
			return "orders/order_form";
			
		} catch (OrderNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/orders";
		}
	}
	
	@PostMapping("/orders/save")
	public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes ra) {
		String countryName = request.getParameter("countryName");
		order.setCountry(countryName);

		updateProductDetails(order, request);
		updateOrderTracks(order, request);
		
		orderService.save(order);
		
		ra.addFlashAttribute("message", "The order ID " + order.getId() + " has been updated successfuly.");
		
		return "redirect:/orders";
	}

	private void updateOrderTracks(Order order, HttpServletRequest request) {
		String[] trackIds = request.getParameterValues("trackId");
		String[] trackStatuses = request.getParameterValues("trackStatus");
		String[] trackDates = request.getParameterValues("trackDate");
		String[] trackNotes = request.getParameterValues("trackNotes");
		
		List<OrderTrack> orderTracks = order.getOrderTracks();
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		
		for(int i = 0; i < trackIds.length; i++) {
			OrderTrack trackRecord = new OrderTrack();
			
			Integer trackId = Integer.parseInt(trackIds[i]);
			if (trackId > 0) {
				trackRecord.setId(trackId);
			}
			
			trackRecord.setOrder(order);
			trackRecord.setStatus(OrderStatus.valueOf(trackStatuses[i]));
			trackRecord.setNotes(trackNotes[i]);
			try {
				trackRecord.setUpdatedTime(dateFormatter.parse(trackDates[i]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			orderTracks.add(trackRecord);
		}
	}

	private void updateProductDetails(Order order, HttpServletRequest request) {
		String[] detailIds = request.getParameterValues("detailId");
		String[] productIds = request.getParameterValues("productId");
		String[] productDetailCost = request.getParameterValues("productDetailCost");
		String[] quantities = request.getParameterValues("quantity");
		String[] productPrices = request.getParameterValues("productPrice");
		String[] productSubtotals = request.getParameterValues("productSubtotal");
		String[] productShipCosts = request.getParameterValues("productShipCost");
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		
		for (int i = 0; i < detailIds.length; i++) {
			System.out.println("Detail ID: " + detailIds[i]);
			System.out.println("\t Product ID: " + productIds[i]);
			System.out.println("\t Cost: " + productDetailCost[i]);
			System.out.println("\t Quantity: " + quantities[i]);
			System.out.println("\t Price: " + productPrices[i]);
			System.out.println("\t Subtotal: " + productSubtotals[i]);
			System.out.println("\t Ship cost: " + productShipCosts[i]);
			
			OrderDetail orderDetail = new OrderDetail();
			Integer detailId = Integer.parseInt(detailIds[i]);
			if (detailId > 0) {
				orderDetail.setId(detailId);
			}
			
			orderDetail.setOrder(order);
			orderDetail.setProduct(new Product(Integer.parseInt(productIds[i])));
			orderDetail.setProductCost(Float.parseFloat(productDetailCost[i]));
			orderDetail.setQuantity(Integer.parseInt(quantities[i]));
			orderDetail.setUnitPrice(Float.parseFloat(productPrices[i]));
			orderDetail.setSubtotal(Float.parseFloat(productSubtotals[i]));
			orderDetail.setShippingCost(Float.parseFloat(productShipCosts[i]));
			
			orderDetails.add(orderDetail);
		}
	}
}











