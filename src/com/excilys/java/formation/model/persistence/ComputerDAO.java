package com.excilys.java.formation.model.persistence;

import java.util.List;

import com.excilys.java.formation.mapper.Computer;

import java.util.ArrayList;
import java.sql.*;

public class ComputerDAO {
	
	private static ComputerDAO computerDAO;
	public ComputerDAO() {
	}
	
	public static ComputerDAO getDAO() {
		if (computerDAO == null) {
			computerDAO = new ComputerDAO();
		}
		return computerDAO;
	}
	
	/**
	 * Returns the list of all computers in the database
	 * @return ArrayList of Computer
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> getAll() throws SQLException, ClassNotFoundException {
		Statement stmt = null;
		Connection connection = MySQLConnection.getConnection();
		List<Computer> computers = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			String query = "SELECT * FROM computer";
			stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery(query);
			connection.commit();
			while (res.next()) {
				computers.add(new Computer (res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getLong(5)));
			}
					
		}catch(SQLException se) {
			
			for (Throwable e : se) {
				System.out.println("Problem : "+e);	
			}
			connection.rollback();
			
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return computers;

	}
	
/*
	public List<Computer> get(int offset, int size) throws SQLException {
		PreparedStatement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {
			conn.setAutoCommit(false);
			String query = "SELECT * FROM computer LIMIT ?,? ";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			conn.commit();
			while (res.next()) {
				computers.add(new Computer (res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getLong(5)));
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
		return computers;

	}
	*/
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 *//*
	public Computer getComputerDetails(long id) throws SQLException  {
		PreparedStatement stmt = null;
		Computer c = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "SELECT * FROM computer WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			conn.commit();
	
			if (res.next()) {
				c = new Computer (res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getLong(5));
			}
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
		return c;
	}

	public void createComputer(Computer c) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			if (c.getCompany_id() != null) {
				String selectQuery = "SELECT id FROM company WHERE id = ?";
				stmt = conn.prepareStatement(selectQuery);
				stmt.setLong(1, c.getCompany_id());
				ResultSet rSet = stmt.executeQuery();
				if (!rSet.next()) {
					System.out.println("No existing company with id "+c.getId());
					return;
				}
				stmt.close();
			}
			String insertQuery = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, c.getName());
			if (c.getIntroduced() == null) {
				stmt.setNull(3, java.sql.Types.DATE);
			}else {
				stmt.setDate(3, java.sql.Date.valueOf(c.getIntroduced().toString()));
			}

			if (c.getDiscontinued()== null) {
				stmt.setNull(4, java.sql.Types.DATE);
			}else {
				stmt.setDate(4, java.sql.Date.valueOf(c.getDiscontinued().toString()));
			}
			if (c.getCompany_id() == null) {
				stmt.setNull(2, java.sql.Types.BIGINT);
			}else {
				stmt.setLong(2, c.getCompany_id());
			}
			int res = stmt.executeUpdate();
			conn.commit();
	
			if (res == 1) {
				System.out.println("Successful creation");
			}else {
				System.out.println("Creation failed");
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public void delete(long id) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "DELETE FROM computer WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setLong(1,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful deletion");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public void updateName (long id, String name) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET name = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setLong(2,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public void updateIntroduced (long id, Date t) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET introduced = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			if (t == null) {
				stmt.setNull(1, java.sql.Types.DATE);
			}else {
				stmt.setDate(1, java.sql.Date.valueOf(t.toString()));
			}
			stmt.setLong(2, id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	public void updateDiscontinued (long id, Date t) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET discontinued = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			if (t == null) {
				stmt.setNull(1, java.sql.Types.DATE);
			}else {
				stmt.setDate(1, java.sql.Date.valueOf(t.toString()));
			}
			stmt.setLong(2,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	public void updateCompanyID (long id, long companyId) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
				String selectQuery = "SELECT id FROM company WHERE id = ?";
				stmt = conn.prepareStatement(selectQuery);
				stmt.setLong(1, companyId);
				ResultSet rSet = stmt.executeQuery();
				if (!rSet.next()) {
					System.out.println("No existing company with id "+companyId);
					System.out.println("No modification made to company_id");
					return;	
				}
				stmt.close();
			String query = "UPDATE computer SET company_id = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, companyId);
			stmt.setLong(2,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public int count() throws SQLException {
		PreparedStatement stmt = null;
		int count = - 1;
		try {
			
			conn.setAutoCommit(false);
			String selectQuery = "SELECT count(id) FROM computer";
			stmt = conn.prepareStatement(selectQuery);
			ResultSet rSet = stmt.executeQuery();
			rSet.next();
			count = rSet.getInt(1);
			stmt.close();
			conn.commit();
			return count;
			
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		}
	return -1;
	}
	
	*/
	
}
