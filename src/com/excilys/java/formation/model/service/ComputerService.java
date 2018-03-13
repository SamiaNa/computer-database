package com.excilys.java.formation.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.ComputerDAO;

public class ComputerService {

	private ComputerDAO compDAO;
	private ComputerPageService compPage;
	
	
	public ComputerService(Connection conn) {
		this.compDAO = new ComputerDAO(conn);
		this.compPage = new ComputerPageService(10, conn);
	}
	
	/**
	 * Prints the list of computers in the database to stdout
	 * @throws SQLException
	 */
	public void printListComputers() throws SQLException {
		List<Computer> computers = compDAO.get();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}
	
	public void printPagedList() throws SQLException {
		List <Computer> computers =  compPage.getPage();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}
	
	public void printNextPage() throws SQLException {
		List<Computer> computers =  compPage.getNextPage();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}
	public List<Computer> get(int offset, int size) throws SQLException {
		return compDAO.get( offset,  size);
	}
	
	/**
	 * Prints computer details or message if no existing computer with id
	 * @param id
	 * @throws SQLException
	 */
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
	
	public void createComputer(String name, Date ti, Date td, Long company_id) throws SQLException{
		if (ti != null && td != null && ti.after(td)){
			System.out.println("Date of introduction must be anterior to date of discontinuation");
		}else {
			compDAO.createComputer(new Computer(name, ti, td, company_id));
		}
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
	
}
