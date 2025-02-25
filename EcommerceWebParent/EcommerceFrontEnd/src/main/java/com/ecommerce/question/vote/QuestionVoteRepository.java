package com.ecommerce.question.vote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.common.entity.QuestionVote;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Integer> {

	@Query("SELECT v FROM QuestionVote v WHERE v.question.id = ?1 AND v.customer.id = ?2")
	public QuestionVote findByQuestionAndCustomer(Integer questionId, Integer customerId);
	
	@Query("SELECT v FROM QuestionVote v WHERE v.question.product.id = ?1 AND v.customer.id = ?2")
	public List<QuestionVote> findByProductAndCustomer(Integer productId, Integer customerId);
}
