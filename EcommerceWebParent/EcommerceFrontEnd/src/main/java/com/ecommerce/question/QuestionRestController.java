package com.ecommerce.question;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ControllerHelper;
import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.product.ProductService;

@RestController
public class QuestionRestController {
	@Autowired private QuestionService questionService;
	@Autowired private ProductService productService;
	@Autowired private ControllerHelper helper;

	@PostMapping("/questions/post_question/{productId}")
	public String saveQuestion(@PathVariable("productId") Integer productId, @RequestBody Question question,
			HttpServletRequest request) throws ProductNotFoundException {
		Customer customer = helper.getAuthenticatedCustomer(request);
		Product product = productService.getProduct(productId);

		question.setCustomer(customer);
		question.setProduct(product);
		question.setAskTime(new Date());

		questionService.save(question);

		return "Your question has been posted and awaiting for approval.";
	}

}
