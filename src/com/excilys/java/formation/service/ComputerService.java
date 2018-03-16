package com.excilys.java.formation.service;

import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;
import com.excilys.java.formation.persistence.*;
import com.excilys.java.formation.validator.*;
import com.excilys.java.formation.entities.*;

public enum ComputerService {

	INSTANCE;
	
	public List<Computer> getComputerList() throws SQLException, ClassNotFoundException {
		return ComputerDAOImpl.INSTANCE.getAll();
	}
	
	public List<Computer> getComputerList(int offset, int size) throws ClassNotFoundException, SQLException {
		return ComputerDAOImpl.INSTANCE.get(offset, size);
	}
	
	public Computer getComputerById(String strId) throws SQLException, ClassNotFoundException, 
											NoComputerInResultSetException, ValidatorException{
		Long id = Validator.getLongPrimId(strId);
		return ComputerDAOImpl.INSTANCE.getComputerById(id);
	}
	
	public Long createComputer(String name, String introducedStr, String discontinuedStr, String compIdStr)
					throws SQLException, ValidatorException, ClassNotFoundException{

		ComputerValidator.INSTANCE.checkName(name);
		LocalDate introducedDate = ComputerValidator.INSTANCE.getDate(introducedStr);
		LocalDate discontinuedDate = ComputerValidator.INSTANCE.getDate(discontinuedStr);
		ComputerValidator.INSTANCE.checkDates(introducedDate, discontinuedDate);
		Long companyId = CompanyValidator.INSTANCE.checkCompanyIdOrNull(compIdStr);
		Company company = new Company();
		company.setId(companyId);
		return ComputerDAOImpl.INSTANCE.createComputer(new Computer(name, introducedDate, discontinuedDate, company));
	}
	
	public boolean updateComputer (ComputerStringAttributes compStr) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerStringBuilder computerBuilder = new ComputerStringBuilder();
		computerBuilder.build(compStr);
		return ComputerDAOImpl.INSTANCE.update(computerBuilder.build(compStr));
		
		/*Long compId = computerValidator.checkComputerId(compStr.getId());
		Long companyId = companyValidator.checkCompanyIdOrNull(compStr.getCompanyId());
		computerValidator.checkName(compStr.getName());
		LocalDate introduced = computerValidator.getDate(compStr.getIntroduced());
		LocalDate discontinued = computerValidator.getDate(compStr.getDiscontinued());
		computerValidator.checkDates(introduced, discontinued);
		ComputerDAO computerDAO = ComputerDAOImpl.INSTANCE;
		Company company = new Company();
		company.setId(companyId);*/
		
		//return computerDAO.update(new Computer(compId, compStr.getName(), introduced, discontinued, company));
		
	}

	public boolean deleteComputer(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAO computerDAO = ComputerDAOImpl.INSTANCE;
		Long id;
		id = Validator.getLongPrimId(strId);
		return computerDAO.delete(id);
	}
	
	public int count () throws ClassNotFoundException, SQLException {
		return ComputerDAOImpl.INSTANCE.count();
	}
	
}
