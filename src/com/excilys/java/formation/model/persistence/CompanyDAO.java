package com.excilys.java.formation.model.persistence;

import java.util.List;

import com.excilys.java.formation.mapper.Company;

import java.util.ArrayList;
import java.sql.*;

public class CompanyDAO {

	Connection conn;
	
	public CompanyDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Company> get() throws SQLException{
		Statement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {

			conn.setAutoCommit(false);
			String query = "SELECT * FROM company";
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			conn.commit();
			while (res.next()) {
				companies.add(new Company (res.getInt(1), res.getString(2)));
			}
					
		}catch(SQLException se) {
			
			for (Throwable e : se) {
				System.out.println("Problem : "+e);	
			}
			conn.rollback();
			
		}finally {
			
			if (stmt != null) {
				stmt.close();
			}
			
		}
		return companies;
	}
}

