package com.ecommerce.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menus")

public class Menu extends IdBasedEntity {

	@Column(nullable = false)
	private String title;
	
	@OneToOne
	@JoinColumn(name = "article_id")
	private Article article;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private String alias;
	
	private Integer position;
	
	private boolean enabled;
	
	public Menu() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Menu [title=" + title + ", article=" + article.getTitle() + ", type=" + type + ", alias=" + alias + ", position="
				+ position + ", enabled=" + enabled + "]";
	}
	
	
}
