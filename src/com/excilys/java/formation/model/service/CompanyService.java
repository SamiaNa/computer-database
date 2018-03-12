package com.excilys.java.formation.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.mapper.Company;
import com.excilys.java.formation.model.persistence.CompanyDAO;

public class CompanyService {
	private CompanyDAO companyDAO;
	
	public CompanyService(Connection conn) {
		this.companyDAO = new CompanyDAO(conn);
	}
	
	/**
	 * Prints the list of companies in the database to stdout
	 * @throws SQLException
	 */
	public void printListCompanies() throws SQLException {
		List<Company> computers = companyDAO.get();
		for (Company c : computers) {
			System.out.println(c);
		}
	}
	
	/*public void printComputerDetails(int id) throws SQLException {
		Computer c = compDAO.getComputerDetails(id);
		if (c == null) {
			System.out.println("No computer found with id : "+id);
		}else {
			System.out.println(c);
		}
	}*/
}
