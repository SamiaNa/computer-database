package com.excilys.java.formation.model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Company;

public class CompanyMapper {

	private static CompanyMapper companyMapper;
	
	private CompanyMapper() {
		
	}
	
	public static CompanyMapper getMapper() {
		if (companyMapper == null) {
			companyMapper = new CompanyMapper();
		}
		return companyMapper;
	}
	
	/**
	 * Creates an ArrayList of companies from a ResultSet
	 * @param res a resultSet obtained after a query on table company
	 * @return a ArrayList of companies
	 * @throws SQLException
	 */
	public List<Company> createCompanyListFromResultSet(ResultSet res) throws SQLException {
		List <Company> companies = new ArrayList<>();
		while (res.next()) {
			companies.add(new Company (res.getLong(1), res.getString(2)));
		}
		return companies;
	}
	
	/**
	 * Creates a Company objet from a ResultSet
	 * @param res a resultSet from a query on table company
	 * @param id
	 * @return a company
	 * @throws SQLException
	 * @throws NoComputerInResultSetException if the resultSet is empty
	 */
	public Company createCompanyFromResultSet(ResultSet res) throws SQLException, NoComputerInResultSetException {
		if (res.next()) {
			return new Company(res.getLong(1), res.getString(2));
		} else {
			throw new NoComputerInResultSetException();
		}
	}
}
