package com.excilys.java.formation.entities;

public class Company {

	private Long id;
	private String name;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Company (long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Id="+this.id+", name="+ this.name;
	}
}
