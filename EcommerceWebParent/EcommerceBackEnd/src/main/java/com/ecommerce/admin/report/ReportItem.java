package com.ecommerce.admin.report;

public class ReportItem {
	private String identifier;
	private float grossSales;
	private float netSales;
	private int ordersCount;
	private int productsCount;
	
	public ReportItem() {
	}
	
	public ReportItem(String identifier, float grossSales, float netSales) {
		this.identifier = identifier;
		this.grossSales = grossSales;
		this.netSales = netSales;
	}

	public ReportItem(String identifier, float grossSales, float netSales, int productsCount) {
		this.identifier = identifier;
		this.grossSales = grossSales;
		this.netSales = netSales;
		this.productsCount = productsCount;
	}

	public ReportItem(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public float getGrossSales() {
		return grossSales;
	}
	public void setGrossSales(float grossSales) {
		this.grossSales = grossSales;
	}
	public float getNetSales() {
		return netSales;
	}
	public void setNetSales(float netSales) {
		this.netSales = netSales;
	}
	public int getOrdersCount() {
		return ordersCount;
	}
	public void setOrdersCount(int ordersCount) {
		this.ordersCount = ordersCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportItem other = (ReportItem) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

	public void addGrossSales(float amount) {
		this.grossSales += amount;
	}

	public void addNetSales(float amount) {
		this.netSales += amount;
	}

	public void increaseOrdersCount() {
		this.ordersCount++;
	}

	public int getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(int productsCount) {
		this.productsCount = productsCount;
	}

	public void increaseProductCount(int count) {
		this.productsCount += count;
	}
}
