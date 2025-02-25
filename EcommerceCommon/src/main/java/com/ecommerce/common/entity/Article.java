package com.ecommerce.common.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
public class Article extends IdBasedEntity {
	
	@Column(length = 256, nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Column(length = 500, nullable = false)
	private String alias;
	
	@Column(nullable = false)
	private String type;
	
	@Column(name = "updated_time")
	private Date updatedTime;
	
	private boolean published;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(
			name = "sections_articles",
			joinColumns = @JoinColumn(name = "article_id"),
			inverseJoinColumns = @JoinColumn(name = "section_id")
			)
	private List<Section> sections;
	
	public Article() {
		
	}
	
	public Article(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", content=" + content + ", alias=" + alias + ", type=" + type
				+ ", updatedTime=" + updatedTime + ", published=" + published + ", user=" + user.getFullName() + "]";
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	
}
