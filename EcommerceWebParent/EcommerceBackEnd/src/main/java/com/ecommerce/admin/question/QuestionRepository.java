package com.ecommerce.admin.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.admin.paging.SearchRepository;
import com.ecommerce.common.entity.Question;

public interface QuestionRepository extends SearchRepository<Question, Integer>{
	
	@Query("SELECT q FROM Question q WHERE CONCAT(q.customer.firstName, ' ', q.customer.lastName) LIKE %?1% OR "
			+ "q.product.name LIKE %?1% OR "
			+ "q.questionContent LIKE %?1% OR "
			+ "q.answerContent LIKE %?1%")
	public Page<Question> findAll(String keyword, Pageable pageable);
	
	public Page<Question> findAll(Pageable pageable);
	
	public Long countById(Integer id);
	
	@Query("UPDATE Question q SET q.approvalStatus = ?2 WHERE q.id = ?1")
	@Modifying
	public void updateApprovalStatus(Integer questionId, boolean status);
}
