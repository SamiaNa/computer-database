package com.excilys.java.formation.service;

import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;
import com.excilys.java.formation.persistence.*;
import com.excilys.java.formation.validator.*;
import com.excilys.java.formation.entities.*;

public enum ComputerService {

	INSTANCE;
	
	public List<Computer> getComputersList() throws SQLException, ClassNotFoundException {
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		return computerDAO.getAll();
	}
	
	public List<Computer> getComputersList(int offset, int size) throws ClassNotFoundException, SQLException {
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		return computerDAO.get(offset, size);
	}
	
	public Computer getComputerById(String strId) throws SQLException, ClassNotFoundException, 
											NoComputerInResultSetException, ValidatorException{
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		Long id;
		id = Validator.getLongPrimId(strId);
		return computerDAO.getComputerById(id);
	}
	
	public Long createComputer(String name, String introducedStr, String discontinuedStr, String compIdStr)
					throws SQLException, ValidatorException, ClassNotFoundException{

		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		computerValidator.checkName(name);
		LocalDate introducedDate = computerValidator.getDate(introducedStr);
		LocalDate discontinuedDate = computerValidator.getDate(discontinuedStr);
		computerValidator.checkDates(introducedDate, discontinuedDate);
		Long companyId = companyValidator.checkCompanyIdOrNull(compIdStr);
		Company company = new Company();
		company.setId(companyId);
		return computerDAO.createComputer(new Computer(name, introducedDate, discontinuedDate, company));
	}
	
	public boolean updateComputer (ComputerStringAttributes compStr) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.INSTANCE;
		CompanyValidator companyValidator = CompanyValidator.INSTANCE;
		Long compId = computerValidator.checkComputerId(compStr.getId());
		Long companyId = companyValidator.checkCompanyIdOrNull(compStr.getCompanyId());
		computerValidator.checkName(compStr.getName());
		LocalDate introduced = computerValidator.getDate(compStr.getIntroduced());
		LocalDate discontinued = computerValidator.getDate(compStr.getDiscontinued());
		computerValidator.checkDates(introduced, discontinued);
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		Company company = new Company();
		company.setId(companyId);
		return computerDAO.update(new Computer(compId, compStr.getName(), introduced, discontinued, company));
		
	}

	public boolean deleteComputer(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		Long id;
		id = Validator.getLongPrimId(strId);
		return computerDAO.delete(id);
	}
	
	public int count () throws ClassNotFoundException, SQLException {
		return ComputerDAO.INSTANCE.count();
	}
	
}
