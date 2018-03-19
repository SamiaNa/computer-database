package com.excilys.java.formation.entities;

public class Company {

	private long id;
	private String name;

	public Company() {

	}
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Id=")
		.append(this.id)
		.append(", name=")
		.append(this.name);
		return str.toString();
	}
}
