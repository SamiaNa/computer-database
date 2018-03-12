package com.excilys.java.formation.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.ComputerDAO;

public class ComputerService {

	private ComputerDAO compDAO;
	
	public ComputerService(Connection conn) {
		this.compDAO = new ComputerDAO(conn);
	}
	
	public void printListComputers() throws SQLException {
		List<Computer> computers = compDAO.get();
		for (Computer c : computers) {
			System.out.println(c);
		}
	}
}
