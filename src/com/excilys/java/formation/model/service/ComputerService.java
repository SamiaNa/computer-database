package com.excilys.java.formation.model.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
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
	
	/**
	 * Prints the list of computers in the database to stdout
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void printListComputers() throws SQLException, ClassNotFoundException {
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		List<Computer> computers = computerDAO.getAll();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}
	
	public List<Computer> getListComputers() throws SQLException, ClassNotFoundException {
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		return computerDAO.getAll();
	}
	
	public void printComputerById(String strId) throws SQLException, ClassNotFoundException{
		ComputerValidator computerValidator = ComputerValidator.getValidator();
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		Long id;
		Computer c;
		try {
			id = computerValidator.getLongId(strId);
			c = computerDAO.getComputerById(id);
			System.out.println(c);
		} catch (ValidatorException ve) {
			System.out.println(ve.getMessage());
		} catch (NoComputerInResultSetException ce) {
			System.out.println(ce.getMessage());
		}
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
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		computerValidator.checkName(name);
		Date introducedDate = computerValidator.getDate(introducedStr);
		Date discontinuedDate = computerValidator.getDate(discontinuedStr);
		computerValidator.checkDates(introducedDate, discontinuedDate);
		Long companyId = computerValidator.checkCompanyId(compIdStr);
		return computerDAO.createComputer(new Computer(name, introducedDate, discontinuedDate, companyId));
	}

	/*
	public void printPagedList() throws SQLException {
		List <Computer> computers =  compPage.getPage();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}*/
	/*
	public void printNextPage() throws SQLException {
		List<Computer> computers =  compPage.getNextPage();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}
	
	public void printPrevPage() throws SQLException {
		List <Computer> computers =  compPage.getPrevPage();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}*/
	/*
	public List<Computer> get(int offset, int size) throws SQLException {
		return compDAO.get( offset,  size);
	}
	
	/**
	 * Prints computer details or message if no existing computer with id
	 * @param id
	 * @throws SQLException
	 */
	/*
	public void printComputerDetails(long id) throws SQLException {
		Computer c = compDAO.getComputerDetails(id);
		if (c == null) {
			System.out.println("No computer found with id "+id);
		}else {
			System.out.println(c);
		}
	}
	
	public Computer getComputerDetails(long id) throws SQLException{
		return compDAO.getComputerDetails(id);
	}
	
	
	
	public void deleteComputer(long id) throws SQLException {
		compDAO.delete(id);
	}
	
	public void updateComputerName (long id, String name) throws SQLException {
		compDAO.updateName(id, name);
	}
	
	public void updateComputerIntroduced (long id, Date ti, Date td) throws SQLException {
		if (ti != null && td != null && ti.after(td)){
			System.out.println("Date of introduction must be anterior to date of discontinuation");
		}else {
			compDAO.updateIntroduced(id, ti);
		}
	}
	
	public void updateComputerDiscontinued (long id, Date ti, Date td) throws SQLException {
		if (ti != null && td != null && ti.after(td)){
			System.out.println("Date of introduction must be anterior to date of discontinuation");
		}else {
		compDAO.updateDiscontinued(id, td);
		}
	}
	
	public void updateComputerCompanyID (long id, long companyId) throws SQLException {
		compDAO.updateCompanyID (id, companyId);
	}
	
	public int getNumberOfComputers() throws SQLException {
		return compDAO.count();
	}
	*/
}
