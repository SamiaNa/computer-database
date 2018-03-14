package com.excilys.java.formation.model.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.entities.ComputerStringAttributes;
import com.excilys.java.formation.model.persistence.ComputerDAO;
import com.excilys.java.formation.model.persistence.NoComputerInResultSetException;

public class ComputerService {

	private static ComputerService computerService;
	
	public static ComputerService getService() throws SQLException {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}
	

	public List<Computer> getComputersList() throws SQLException, ClassNotFoundException {
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		return computerDAO.getAll();
	}
	
	public List<Computer> getComputersList(int offset, int size) throws ClassNotFoundException, SQLException {
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		return computerDAO.get(offset, size);
	}
	
	public Computer getComputerById(String strId) throws SQLException, ClassNotFoundException, 
											NoComputerInResultSetException, ValidatorException{
		ComputerValidator computerValidator = ComputerValidator.getValidator();
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		Long id;
		id = computerValidator.getLongId(strId);
		return computerDAO.getComputerById(id);
	}
	
	public boolean createComputer(String name, String introducedStr, String discontinuedStr, String compIdStr)
					throws SQLException, ValidatorException, ClassNotFoundException{

		ComputerValidator computerValidator = ComputerValidator.getValidator();
		CompanyValidator companyValidator = CompanyValidator.getValidator();
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		computerValidator.checkName(name);
		Date introducedDate = computerValidator.getDate(introducedStr);
		Date discontinuedDate = computerValidator.getDate(discontinuedStr);
		computerValidator.checkDates(introducedDate, discontinuedDate);
		Long companyId = companyValidator.checkCompanyId(compIdStr);
		return computerDAO.createComputer(new Computer(name, introducedDate, discontinuedDate, companyId));
	}
	
	public boolean updateComputer (ComputerStringAttributes compStr) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.getValidator();
		CompanyValidator companyValidator = CompanyValidator.getValidator();
		Long compId = computerValidator.checkComputerId(compStr.getId());
		Long companyId = companyValidator.checkCompanyId(compStr.getCompanyId());
		computerValidator.checkName(compStr.getName());
		Date introduced = computerValidator.getDate(compStr.getIntroduced());
		Date discontinued = computerValidator.getDate(compStr.getDiscontinued());
		computerValidator.checkDates(introduced, discontinued);
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		return computerDAO.update(new Computer(compId, compStr.getName(), introduced, discontinued, companyId));
		
	}

	public boolean deleteComputer(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerValidator computerValidator = ComputerValidator.getValidator();
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		Long id;
		id = computerValidator.getLongId(strId);
		return computerDAO.delete(id);
	}
	
	public int count () throws ClassNotFoundException, SQLException {
		return ComputerDAO.getDAO().count();
	}
	
}
