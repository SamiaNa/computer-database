package com.excilys.java.formation.entities;

import java.sql.SQLException;

import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerStringBuilder {
	
	private Computer computer;
	
	public ComputerStringBuilder() {
		
	}
	
	public ComputerStringBuilder setName(String name) throws ValidatorException {
		ComputerValidator.INSTANCE.checkName(name);
		computer.setName(name);
		return this;
	}
	
	public ComputerStringBuilder setId(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		computer.setId(ComputerValidator.INSTANCE.checkComputerId(strId));
		return this;
	}
	
	public ComputerStringBuilder setCompany(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		Company company = new Company();
		company.setId(CompanyValidator.INSTANCE.checkCompanyIdOrNull(strId));
		computer.setCompany(company);
		return this;
	}
	
	public ComputerStringBuilder setIntroduced(String introducedStr) throws ValidatorException {
		computer.setIntroduced(
				ComputerValidator.INSTANCE.getDate(introducedStr));
		return this;
	}
	
	public ComputerStringBuilder setDiscontinued(String discontinuedStr) throws ValidatorException {
		this.computer.setDiscontinued(
				ComputerValidator.INSTANCE.getDate(discontinuedStr));
		return this;
	}
	
	public Computer build() {
		return computer;
	}
	
	public Computer build(ComputerStringAttributes compStr) throws ClassNotFoundException, ValidatorException, SQLException {
		this.setName(compStr.getName())
			.setCompany(compStr.getCompanyId())
			.setId(compStr.getId())
			.setIntroduced(compStr.getIntroduced())
			.setDiscontinued(compStr.getDiscontinued());
		return computer;
	}
}
