package com.excilys.java.formation.model.persistence;

import java.util.List;

import com.excilys.java.formation.mapper.Computer;

import java.util.ArrayList;
import java.sql.*;

public class ComputerDAO {
	
	Connection conn;
	
	public ComputerDAO(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Returns the list of all computers in the database
	 * @return ArrayList of Computer
	 * @throws SQLException
	 */
	public List<Computer> get() throws SQLException {
		Statement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {

			conn.setAutoCommit(false);
			String query = "SELECT * FROM computer";
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			conn.commit();
			while (res.next()) {
				computers.add(new Computer (res.getLong(1), res.getString(2), res.getTimestamp(3), res.getTimestamp(4), res.getLong(5)));
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
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
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
				c = new Computer (res.getLong(1), res.getString(2), res.getTimestamp(3), res.getTimestamp(4), res.getLong(5));
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
			stmt.setTimestamp(3, c.getIntroduced());
			stmt.setTimestamp(4, c.getDiscontinued());
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
	
	public void updateIntroduced (long id, Timestamp t) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET introduced = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setTimestamp(1, t);
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
	public void updateDiscontinued (long id, Timestamp t) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET discontinued = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setTimestamp(1, t);
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
	
}
