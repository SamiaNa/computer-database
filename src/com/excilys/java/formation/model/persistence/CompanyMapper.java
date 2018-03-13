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
	
	public List<Company> createCompanyListFromResultSet(ResultSet res) throws SQLException {
		List <Company> companies = new ArrayList<>();
		while (res.next()) {
			companies.add(new Company (res.getLong(1), res.getString(2)));
		}
		return companies;
	}
}
