package com.ecommerce.common.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.ecommerce.common.entity.product.Product;

@Entity
@Table(name = "sections")
public class Section extends IdBasedEntity {
	
	private String heading;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private SectionType type;
	
	private int position;
	
	private boolean enabled;
	
	@ManyToMany
	@JoinTable(
			name = "sections_products",
			joinColumns = @JoinColumn(name = "section_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id")
			)
	private List<Product> products;
	
	@ManyToMany
	@JoinTable(
			name = "sections_categories",
			joinColumns = @JoinColumn(name = "section_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	private List<Category> categories;
	
	@ManyToMany
	@JoinTable(
			name = "sections_brands",
			joinColumns = @JoinColumn(name = "section_id"),
			inverseJoinColumns = @JoinColumn(name = "brand_id")
			)
	private List<Brand> brands;
	
	@ManyToMany
	@JoinTable(
			name = "sections_articles",
			joinColumns = @JoinColumn(name = "section_id"),
			inverseJoinColumns = @JoinColumn(name = "article_id")
			)
	private List<Article> articles;
	
	public Section() {
		
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SectionType getType() {
		return type;
	}

	public void setType(SectionType type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "Section [ID= " + this.id +  ", headline=" + heading + ", description=" + description + ", type=" + type + ", position="
				+ position + ", enabled=" + enabled + "]";
	}
	
}
