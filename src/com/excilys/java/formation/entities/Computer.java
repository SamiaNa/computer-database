package com.excilys.java.formation.entities;

import java.sql.Date;

public class Computer {
	
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Long companyId;
	
	public Computer (long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Computer (long id, String name, Date introduced, Date discontinued, Long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	
	public Computer (String name, Date introduced, Date discontinued, Long companyId) {
		this.id = -1;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Long getId() {
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

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public String toString() {
		return "Computer :  ID = "+this.id+" \nNAME = "+ this.name+", INTRODUCED = "+this.introduced+
				", DISCONTINUED =  "+this.discontinued+", COMPANY ID = "+this.companyId;
	}

}
