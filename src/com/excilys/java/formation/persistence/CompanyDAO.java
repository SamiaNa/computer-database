package com.excilys.java.formation.persistence;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.CompanyMapper;

import java.util.ArrayList;
import java.sql.*;

public enum CompanyDAO {
	
	INSTANCE;
	

	/**
	 * Creates a list of Companies object from the database
	 * @return an ArrayList of all the companies in the databse
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Company> getAll() throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionManager.getConnection();
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		Statement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM company");
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	}
	
	/**
	 * Returns a list of all the companies between lines offset and offset + size
	 * @param offset 
	 * @param size
	 * @return a list of all companies between offset and offset + size
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Company> get(int offset, int size) throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionManager.getConnection();
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
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
			ConnectionManager.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	}
	
	/**
	 * Returns a list of all the companies between lines offset and offset + size
	 * @param offset 
	 * @param size
	 * @return a list of all companies between offset and offset + size
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Company> getByName(String name) throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionManager.getConnection();
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		PreparedStatement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM company WHERE name LIKE ?");
			stmt.setString(1, "%"+name+"%");
			ResultSet res = stmt.executeQuery();
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	}
	
	/**
	 * Check if a company with the specified id exists
	 * @param id of the company to check
	 * @return true if a company with the specified id exists
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkCompanyById(long id) throws SQLException, ClassNotFoundException  {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM company WHERE id = ?");
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			return res.next();
			} catch(SQLException se) {
				ConnectionManager.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return false;
	}
	
	/**
	 * Returns the number of companies in the database
	 * @return number of lines in table company
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int count() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT COUNT(id) FROM company");
			ResultSet res = stmt.executeQuery();
			connection.commit();
			res.next();
			return res.getInt(1);
			} catch(SQLException se) {
				ConnectionManager.printExceptionList(se);
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

