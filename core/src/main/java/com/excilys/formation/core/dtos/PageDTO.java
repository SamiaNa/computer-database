package com.excilys.formation.core.dtos;

public class PageDTO {
	
	private long size;
	private long number;
	private String order;
	private String orderBy;
	private String search;
	
	public PageDTO(long number, long size, String order, String orderBy, String search) {
		this.number = number;
		this.size = size;
		this.order = order;
		this.orderBy = orderBy;
		this.search = search;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	

	
}
