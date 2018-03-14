package com.excilys.java.formation.model.persistence;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

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
		CompanyMapper companyMapper = CompanyMapper.getMapper();
		Statement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM company");
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
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
	
	public List<Company> get(int offset, int size) throws SQLException, ClassNotFoundException{
		Connection connection = MySQLConnection.getConnection();
		CompanyMapper companyMapper = CompanyMapper.getMapper();
		PreparedStatement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM company LIMIT ?,?");
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
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
	public boolean checkCompanyById(long id) throws SQLException, ClassNotFoundException  {
		Connection connection = MySQLConnection.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM company WHERE id = ?");
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			return res.next();
			} catch(SQLException se) {
				MySQLConnection.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return false;
	}
	
	public int count() throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnection.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT COUNT(id) FROM company");
			ResultSet res = stmt.executeQuery();
			connection.commit();
			res.next();
			return res.getInt(1);
			} catch(SQLException se) {
				MySQLConnection.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return -1;
	}
}

