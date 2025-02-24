package com.ecommerce.admin.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.order.Order;
import com.ecommerce.common.entity.order.OrderDetail;
import com.ecommerce.common.entity.order.OrderStatus;
import com.ecommerce.common.entity.order.OrderTrack;
import com.ecommerce.common.entity.order.PaymentMethod;
import com.ecommerce.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepositoryTests {

	@Autowired private OrderRepository repo;
	@Autowired private EntityManager entityManager;
	
	@Test
	public void testCreateOrder() {
		Customer customer = entityManager.find(Customer.class, 1);
		Product product = entityManager.find(Product.class, 14);
		
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderTime(new Date());
		order.copyAddressFromCustomer();
		
		order.setShippingCost(10);
		order.setProductCost(product.getCost());
		order.setTax(0);
		order.setSubtotal(product.getPrice());
		order.setTotal(product.getPrice() + 10);
		
		order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		order.setOrderStatus(OrderStatus.NEW);
		order.setDeliverDate(new Date());
		order.setDeliverDays(1);
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setOrder(order);
		orderDetail.setProductCost(product.getCost());
		orderDetail.setShippingCost(10);
		orderDetail.setQuantity(1);
		orderDetail.setSubtotal(product.getPrice());
		orderDetail.setUnitPrice(product.getPrice());
		
		order.getOrderDetails().add(orderDetail);
		
		Order savedOrder = repo.save(order);
		
		assertThat(savedOrder).isNotNull();
		assertThat(savedOrder.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewOrderWithMultipleProducts() {
		Customer customer = entityManager.find(Customer.class, 13);
		Product product1 = entityManager.find(Product.class, 15);
		Product product2 = entityManager.find(Product.class, 19);
		
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderTime(new Date());
		order.copyAddressFromCustomer();
		
		order.setShippingCost(30);
		order.setProductCost(product1.getCost() + product2.getCost());
		order.setTax(0);
		float subtotal = product1.getPrice() + product2.getPrice();
		order.setSubtotal(subtotal);
		order.setTotal(subtotal + 30);
		
		OrderDetail orderDetail1 = new OrderDetail();
		orderDetail1.setProduct(product1);
		orderDetail1.setOrder(order);
		orderDetail1.setProductCost(product1.getCost());
		orderDetail1.setShippingCost(10);
		orderDetail1.setQuantity(1);
		orderDetail1.setSubtotal(product1.getPrice());
		orderDetail1.setUnitPrice(product1.getPrice());
		
		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setProduct(product2);
		orderDetail2.setOrder(order);
		orderDetail2.setProductCost(product2.getCost());
		orderDetail2.setShippingCost(20);
		orderDetail2.setQuantity(2);
		orderDetail2.setSubtotal(product2.getPrice());
		orderDetail2.setUnitPrice(product2.getPrice());
		
		order.getOrderDetails().add(orderDetail1);
		order.getOrderDetails().add(orderDetail2);
		
		Order savedOrder = repo.save(order);
		
		assertThat(savedOrder).isNotNull();
		assertThat(savedOrder.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListOrders() {
		Iterable<Order> orders = repo.findAll();
		
		assertThat(orders).hasSizeGreaterThan(0);
		
		orders.forEach(System.out::println);
	}
	
	@Test
	public void testUpdateOrder() {
		Integer orderId = 1;
		Order order = repo.findById(orderId).get();
		
		order.setDeliverDate(new Date());
		order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		
		Order updatedOrder = repo.save(order);
		
		assertThat(updatedOrder.getPaymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
	}
	
	@Test
	public void testGetOrder() {
		Integer orderId = 4;
		Order order = repo.findById(orderId).get();
		
		assertThat(order).isNotNull();
		
		System.out.println(order);
	}
	
	@Test
	public void testDeleteOrder() {
		Integer orderId = 3;
		repo.deleteById(orderId);
		
		Optional<Order> order = repo.findById(orderId);
		
		assertThat(!order.isPresent());
	}
	
	@Test
	public void testUpdateOrderTracks() {
		Integer orderId = 1;
		Order order = repo.findById(orderId).get();
		
		OrderTrack newTrack = new OrderTrack();
		newTrack.setOrder(order);
		newTrack.setUpdatedTime(new Date());
		newTrack.setStatus(OrderStatus.NEW);
		newTrack.setNotes(OrderStatus.NEW.defaultDescription());
		
		List<OrderTrack> orderTracks = order.getOrderTracks();
		orderTracks.add(newTrack);
		
		Order updatedOrder = repo.save(order);
		
		assertThat(updatedOrder.getOrderTracks()).hasSizeGreaterThan(0);
	}
	
	@Test
	public void testFindByOrderTimeBetween() throws ParseException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = dateFormatter.parse("2021-02-15");
		Date endTime = dateFormatter.parse("2024-02-25");
		
		List<Order> listOrders = repo.findByOrderTimeBetween(startTime, endTime);
		
		assertThat(listOrders.size()).isGreaterThan(0);
		
		for (Order order : listOrders) {
			System.out.printf("%s | %s | %.2f | %.2f | %.2f \n", 
					order.getId(), order.getOrderTime(), order.getProductCost(),
					order.getSubtotal(), order.getTotal());
		}
	}
}
