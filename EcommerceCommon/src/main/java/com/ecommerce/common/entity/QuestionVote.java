package com.ecommerce.common.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "question_votes")
public class QuestionVote extends IdBasedEntity {
	private static final int VOTE_UP_POINT = 1;
	private static final int VOTE_DOWN_POINT = -1;
	
	private int votes;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void voteUp() {
		this.votes = VOTE_UP_POINT;
	}
	
	public void voteDown() {
		this.votes = VOTE_DOWN_POINT;
	}
	
	@Transient
	public boolean isUpvoted() {
		return this.votes == VOTE_UP_POINT;
	}
	
	@Transient
	public boolean isDownvoted() {
		return this.votes == VOTE_DOWN_POINT;
	}

	@Override
	public String toString() {
		return "QuestionVote [votes=" + votes + ", customer=" + customer.getFullName() + ", question=" + question.getQuestionContent() + "]";
	}
	
}
