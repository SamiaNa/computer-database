package com.excilys.java.formation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.model.persistence.CompanyDAO;

public class CompanyService {
	
	private static CompanyService companyService;
	
	private CompanyService() {
		
	}

	public static CompanyService getService() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}
	
	/**
	 * Prints the list of companies in the database to stdout
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void printCompaniesList() throws SQLException, ClassNotFoundException {
		CompanyDAO companyDAO = CompanyDAO.getDAO();
		List<Company> companies = companyDAO.getAll();
		for (Company c : companies) {
			System.out.println(c);
		}
	}
	

}
