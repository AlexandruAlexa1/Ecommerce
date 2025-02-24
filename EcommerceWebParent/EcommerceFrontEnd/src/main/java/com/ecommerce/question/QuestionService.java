package com.ecommerce.question;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.common.entity.Customer;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.exception.QuestionNotFoundException;

@Service
@Transactional
public class QuestionService {
	public static final int QUESTIONS_PER_PAGE = 5;
	
	@Autowired private QuestionRepository questionRepo;
	
	public Page<Question> listByPage(Customer customer, int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, QUESTIONS_PER_PAGE, sort);
		
		if (keyword != null && !keyword.isEmpty()) {
			return questionRepo.findByCustomer(customer.getId(), keyword, pageable);
		}
		
		return questionRepo.findByCustomer(customer.getId(), pageable);
	}
	
	public Page<Question> listByProduct(Product product, int pageNum, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, QUESTIONS_PER_PAGE, sort);
		Page<Question> page = questionRepo.findByProduct(product, pageable);
		
		return page;
	}
	
	public Question get(Integer id) throws QuestionNotFoundException {
		try {
			return questionRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new QuestionNotFoundException("Could not find any question with ID " + id);
		}
	}
	
	public Page<Question> list3MostRecentQuestionsByProduct(Product product) {
		Sort sort = Sort.by("askTime").descending();
		Pageable pageable = PageRequest.of(0, 3, sort);
		
		return questionRepo.findByProduct(product, pageable);
	}
	
	public void save(Question question) {
		questionRepo.save(question);
	}
	
	public Integer getQuestionCount(Product product) {
		return questionRepo.getQuestionCount(product.getId());
	}
	
	public Integer getAnswerCount(Product product) {
		return questionRepo.getAnswerCount(product.getId());
	}
	
}