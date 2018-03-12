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
				computers.add(new Computer (res.getInt(1), res.getString(2), res.getDate(3), res.getDate(4), res.getInt(5)));
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

}
