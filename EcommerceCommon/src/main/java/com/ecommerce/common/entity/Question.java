package com.ecommerce.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ecommerce.common.entity.product.Product;

@Entity
@Table(name = "questions")
public class Question extends IdBasedEntity {
	
	@Column(name = "question_content", length = 128)
	private String questionContent;
	
	@Column(name = "answer_content", length = 300)
	private String answerContent;
	
	@Column(name = "ask_time")
	private Date askTime;
	
	@Column(name = "answer_time")
	private Date answerTime;
	
	private int votes;
	
	@Column(name = "approval_status")
	private boolean approvalStatus;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Transient
	private boolean upvotedByCurrentCustomer;
	
	@Transient
	private boolean downvotedByCurrentCustomer;

	public Question() {
	}
	
	public Question(Integer id) {
		this.id = id;
	}

	public Question(String questionContent, Date askTime, Customer customer, Product product) {
		this.questionContent = questionContent;
		this.askTime = askTime;
		this.customer = customer;
		this.product = product;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Date getAskTime() {
		return askTime;
	}

	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public boolean isApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public boolean isAnswer() {
		if (answerContent == null || answerContent.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "Question [questionContent=" + questionContent + ", answerContent=" + answerContent + ", askTime="
				+ askTime + ", answerTime=" + answerTime + ", votes=" + votes + ", approvalStatus=" + approvalStatus
				+ ", customer=" + customer + ", product=" + product + ", user=" + this.user + "]";
	}

	public boolean isUpvotedByCurrentCustomer() {
		return upvotedByCurrentCustomer;
	}

	public void setUpvotedByCurrentCustomer(boolean upvotedByCurrentCustomer) {
		this.upvotedByCurrentCustomer = upvotedByCurrentCustomer;
	}

	public boolean isDownvotedByCurrentCustomer() {
		return downvotedByCurrentCustomer;
	}

	public void setDownvotedByCurrentCustomer(boolean downvotedByCurrentCustomer) {
		this.downvotedByCurrentCustomer = downvotedByCurrentCustomer;
	}
	
	
}
