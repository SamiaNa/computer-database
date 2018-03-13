package com.excilys.java.formation.mapper;

import java.sql.Date;

public class Computer {
	
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Long company_id;
	
	public Computer (long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Computer (long id, String name, Date introduced, Date discontinued, Long company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	
	public Computer (String name, Date introduced, Date discontinued, Long company_id) {
		this.id = -1;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Long getCompany_id() {
		return this.company_id;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	
	@Override
	public String toString() {
		return "Computer :  ID = "+this.id+" \nNAME = "+ this.name+", INTRODUCED = "+this.introduced+
				", DISCONTINUED =  "+this.discontinued+", COMPANY ID = "+this.company_id;
	}

}
