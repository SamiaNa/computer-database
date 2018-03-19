package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.CompanyDAOImpl;

public enum CompanyService {
	
	INSTANCE;
	
	
	public List<Company> getCompaniesList() throws SQLException, ClassNotFoundException {
		CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
		return companyDAO.getAll();
	}
	
	public List<Company> getCompaniesList(int offset, int size) throws ClassNotFoundException, SQLException{
		CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
		return companyDAO.get(offset, size);
	}
	
	public int count() throws ClassNotFoundException, SQLException {
		return CompanyDAOImpl.INSTANCE.count();
	}

	public List<Company> getCompaniesByName(String name) throws ClassNotFoundException, SQLException{
		CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
		return companyDAO.getByName(name);
		
	}
}
