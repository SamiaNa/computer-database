package com.excilys.java.formation.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.ComputerDAO;

public class ComputerService {

	private ComputerDAO compDAO;
	
	public ComputerService(Connection conn) {
		this.compDAO = new ComputerDAO(conn);
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
	
	/**
	 * Prints computer details or message if no existing computer with id
	 * @param id
	 * @throws SQLException
	 */
	public void printComputerDetails(int id) throws SQLException {
		Computer c = compDAO.getComputerDetails(id);
		if (c == null) {
			System.out.println("No computer found with id "+id);
		}else {
			System.out.println(c);
		}
	}
	
	public void createComputer(String name, Timestamp ti, Timestamp td, long company_id) throws SQLException{
		compDAO.createComputer(new Computer(name, ti, td, company_id));
	}
}
