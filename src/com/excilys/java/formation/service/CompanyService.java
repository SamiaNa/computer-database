package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public enum CompanyService {
	
	INSTANCE;
	
	
	/**
	 * Prints the list of companies in the database to stdout
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void printCompaniesList() throws SQLException, ClassNotFoundException {
		CompanyDAO companyDAO = CompanyDAO.INSTANCE;
		List<Company> companies = companyDAO.getAll();
		for (Company c : companies) {
			System.out.println(c);
		}
	}
	
	public List<Company> getCompaniesList() throws SQLException, ClassNotFoundException {
		CompanyDAO companyDAO = CompanyDAO.INSTANCE;
		return companyDAO.getAll();
	}
	
	public List<Company> getCompaniesList(int offset, int size) throws ClassNotFoundException, SQLException{
		CompanyDAO companyDAO = CompanyDAO.INSTANCE;
		return companyDAO.get(offset, size);
	}
	
	public int count () throws ClassNotFoundException, SQLException {
		return CompanyDAO.INSTANCE.count();
	}

	public List<Company> getCompaniesByName(String name) throws ClassNotFoundException, SQLException{
		CompanyDAO companyDAO = CompanyDAO.INSTANCE;
		return companyDAO.getByName(name);
		
	}
}
