package com.excilys.java.formation.model.persistence;

import java.util.List;

import com.excilys.java.formation.entities.Company;

import java.util.ArrayList;
import java.sql.*;

public class CompanyDAO {
	
	private static CompanyDAO companyDAO;
	
	private CompanyDAO() {
		
	}
	
	public static CompanyDAO getDAO () {
		if (companyDAO == null) {
			companyDAO = new CompanyDAO();
		}
		return companyDAO;
	}
	


	public List<Company> getAll() throws SQLException, ClassNotFoundException{
		Connection connection = MySQLConnection.getConnection();
		ComputerMapper computerMapper = ComputerMapper.getMapper();
		Statement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM company");
			connection.commit();
			while (res.next()) {
				companies.add(new Company (res.getInt(1), res.getString(2)));
			}
		}catch(SQLException se) {		
			MySQLConnection.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	
	}
}

