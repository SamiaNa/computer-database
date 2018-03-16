package com.excilys.java.formation.entities;

import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

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
	
	public Computer (ComputerStringBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
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



public static class ComputerStringBuilder {
	
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public ComputerStringBuilder() {
		
	}
	
	public ComputerStringBuilder setName(String name) throws ValidatorException {
		ComputerValidator.INSTANCE.checkName(name);
		this.name = name;
		return this;
	}
	
	public ComputerStringBuilder setId(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		this.id = ComputerValidator.INSTANCE.checkComputerId(strId);
		return this;
	}
	
	public ComputerStringBuilder setCompany(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		if (!strId.equals("null")) {
			Company comp = new Company();
			comp.setId(CompanyValidator.INSTANCE.checkCompanyIdOrNull(strId));
			this.company = comp;
		}
		return this;
	}
	
	public ComputerStringBuilder setIntroduced(String introducedStr) throws ValidatorException {
		introduced = ComputerValidator.INSTANCE.getDate(introducedStr);
		return this;
	}
	
	public ComputerStringBuilder setDiscontinued(String discontinuedStr) throws ValidatorException {
		discontinued = ComputerValidator.INSTANCE.getDate(discontinuedStr);
		return this;
	}
	
	public Computer build() {
		return new Computer (this);
	}
	
	public Computer build(ComputerStringAttributes compStr) throws ClassNotFoundException, ValidatorException, SQLException {
		this.setName(compStr.getName())
			.setCompany(compStr.getCompanyId())
			.setId(compStr.getId())
			.setIntroduced(compStr.getIntroduced())
			.setDiscontinued(compStr.getDiscontinued());
		return new Computer(this);
	}
}
}
