package com.ecommerce.admin.question;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.admin.product.ProductRepository;
import com.ecommerce.common.entity.Question;
import com.ecommerce.common.exception.QuestionNotFoundException;

@Service
@Transactional
public class QuestionService {
	public static final int QUESTIONS_PER_PAGE = 5;
	
	@Autowired private QuestionRepository questionRepo;
	@Autowired private ProductRepository productRepo;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, QUESTIONS_PER_PAGE, questionRepo);
	}
	
	public Question get(Integer id) throws QuestionNotFoundException {
		try {
			return questionRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new QuestionNotFoundException("Could not find any Question with ID " + id);
		}
	}
	
	public void save(Question questionInForm) {
		Question questionInDB = questionRepo.findById(questionInForm.getId()).get();
		questionInForm.setCustomer(questionInDB.getCustomer());
		questionInForm.setProduct(questionInDB.getProduct());
		questionInForm.setAskTime(questionInDB.getAskTime());
		questionInForm.setAnswerTime(new Date());
		questionInForm.setVotes(questionInDB.getVotes());
		
		Integer productId = questionInForm.getProduct().getId();
		productRepo.updateAnswerCount(productId);
		productRepo.updateQuestionCount(productId); 
		
		questionRepo.save(questionInForm);
	}
	
	public void delete(Integer id) throws QuestionNotFoundException {
		Long countById = questionRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new QuestionNotFoundException("Could not find any Question with ID " + id);
		}
		
		questionRepo.deleteById(id);
	}
	
	public void updateApprovalStatus(Integer questionId, boolean status) {
		Question question = questionRepo.findById(questionId).get();
		Integer productId = question.getProduct().getId();
		productRepo.updateQuestionCount(productId);
		
		questionRepo.updateApprovalStatus(questionId, status);
	}
}
