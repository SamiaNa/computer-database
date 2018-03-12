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
				c = new Computer (res.getInt(1), res.getString(2), res.getTimestamp(3), res.getTimestamp(4), res.getInt(5));
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
			String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getName());
			stmt.setTimestamp(2, c.getIntroduced());
			stmt.setTimestamp(3, c.getDiscontinued());
			stmt.setLong(4, c.getCompany_id());
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
}
