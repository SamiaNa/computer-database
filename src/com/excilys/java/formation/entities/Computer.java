package com.excilys.java.formation.entities;

import java.time.LocalDate;

public class Computer {
	
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public Computer (long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Computer (long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Computer (String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = -1;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Id=").append(this.id)
		.append(", name=").append(this.name)
		.append(", introduced=").append(this.introduced)
		.append(", discontinued=").append(this.discontinued)
		.append(", company id=").append(this.company);
		return str.toString();
	}

}
