package com.ecommerce.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ecommerce.common.entity.Question;
import com.ecommerce.common.entity.product.Product;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Integer>{

	@Query("SELECT q FROM Question q WHERE q.customer.id = ?1")
	public Page<Question> findByCustomer(Integer customerId, Pageable pageable);
	
	@Query("SELECT q FROM Question q WHERE q.customer.id = ?1 AND (q.product.name LIKE %?2% "
			+ "OR q.questionContent LIKE %?2% OR q.answerContent LIKE %?2%)")
	public Page<Question> findByCustomer(Integer customerId, String keyword, Pageable pageable);
	
	@Query("SELECT q FROM Question q WHERE q.product = ?1 AND q.approvalStatus = true")
	public Page<Question> findByProduct(Product product, Pageable pageable);
	
	@Query("UPDATE Question q SET q.votes = COALESCE((SELECT SUM(v.votes) FROM QuestionVote v"
			+ " WHERE v.question.id = ?1), 0) WHERE q.id = ?1")
	@Modifying
	public void updateVoteCount(Integer questionId);
	
	@Query("SELECT q.votes FROM Question q WHERE q.id = ?1")
	public Integer getVoteCount(Integer questionId);
	
	@Query("SELECT COUNT(q.id) FROM Question q WHERE q.product.id = ?1 AND q.approvalStatus = true")
	public Integer getQuestionCount(Integer productId);
	
	@Query("SELECT COUNT(q.id) FROM Question q WHERE q.product.id = ?1 AND q.answerContent != ''")
	public Integer getAnswerCount(Integer productId);
}

