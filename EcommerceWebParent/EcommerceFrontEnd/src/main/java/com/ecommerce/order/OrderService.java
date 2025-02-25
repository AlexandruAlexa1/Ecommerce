package com.ecommerce.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.checkout.CheckoutInfo;
import com.ecommerce.common.entity.Address;
import com.ecommerce.common.entity.CartItem;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.order.Order;
import com.ecommerce.common.entity.order.OrderDetail;
import com.ecommerce.common.entity.order.OrderStatus;
import com.ecommerce.common.entity.order.OrderTrack;
import com.ecommerce.common.entity.order.PaymentMethod;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.OrderNotFoundException;

@Service
public class OrderService {
	public static final int ORDERS_PER_PAGE = 5;
	@Autowired private OrderRepository repo;
	
	public Order createOrder(Customer customer, Address address, List<CartItem> cartItems,
			PaymentMethod paymentMethod, CheckoutInfo checkoutInfo) {
		Order newOrder = new Order();
		newOrder.setOrderTime(new Date());
		
		if(paymentMethod.equals(PaymentMethod.PAYPAL)) {
			newOrder.setOrderStatus(OrderStatus.PAID);
		} else {
			newOrder.setOrderStatus(OrderStatus.NEW);
		}
		
		newOrder.setCustomer(customer);
		newOrder.setProductCost(checkoutInfo.getProductCost());
		newOrder.setSubtotal(checkoutInfo.getProductTotal());
		newOrder.setShippingCost(checkoutInfo.getShippingCostTotal());
		newOrder.setTax(0.0f);
		newOrder.setTotal(checkoutInfo.getPaymentTotal());
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setDeliverDays(checkoutInfo.getDeliverDays());
		newOrder.setDeliverDate(checkoutInfo.getDeliverDate());
		
		if (address == null) {
			newOrder.copyAddressFromCustomer();
		} else {
			newOrder.copyShippingAddress(address);
		}
		
		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
		
		for(CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setUnitPrice(product.getDiscountPercent());
			orderDetail.setProductCost(product.getCost() * cartItem.getQuantity());
			orderDetail.setShippingCost(cartItem.getSubtotal());
			orderDetail.setShippingCost(cartItem.getShippingCost());
			
			orderDetails.add(orderDetail);
		}
		
		OrderTrack track = new OrderTrack();
		track.setOrder(newOrder);
		track.setStatus(OrderStatus.NEW);
		track.setNotes(OrderStatus.NEW.defaultDescription());
		track.setUpdatedTime(new Date());
		
		newOrder.getOrderTracks().add(track);
		
		return repo.save(newOrder);
	}
	
	public Page<Order> listForCustomerByPage(Integer pageNum, String sortField, String sortDir,
			Integer customerId, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);
		
		Page<Order> page = null;
		
		if (keyword != null && !keyword.isEmpty()) {
			page = repo.findAll(keyword, customerId, pageable);
		} else {
			page = repo.findAll(customerId, pageable);
		}
		
		return page;
	}
	
	public Order getOrder(Integer orderId, Customer customer) {
		return repo.findByIdAndCustomer(orderId, customer);
	}
	
	public void setOrderReturnRequested(OrderReturnRequest request, Customer customer) throws OrderNotFoundException {
		Order order = repo.findByIdAndCustomer(request.getOrderId(), customer);
		if (order == null) {
			throw new OrderNotFoundException("Order ID " + request.getOrderId() + " not found");
		}
		
		if (order.isReturnRequestd()) return;
		
		OrderTrack track = new OrderTrack();
		track.setOrder(order);
		track.setUpdatedTime(new Date());
		track.setStatus(OrderStatus.RETURN_REQUESTED);
		
		String notes = "Reason: " + request.getReason();
		if (!"".equals(request.getNote())) {
			notes += ". " + request.getNote();
		}
		
		track.setNotes(notes);
		
		order.getOrderTracks().add(track);
		order.setOrderStatus(OrderStatus.RETURN_REQUESTED);
		
		repo.save(order);
	}
}
