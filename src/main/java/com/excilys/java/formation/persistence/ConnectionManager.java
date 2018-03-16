package com.excilys.java.formation.persistence;

import java.sql.*;
import java.util.ResourceBundle;
public enum ConnectionManager {

	INSTANCE;
	private static Connection conn = null;
	private static final String RESOURCE_PATH = "com.excilys.java.formation.persistence.connection";
	
	/**
	 *  Creates or return a connection to mysql database
	 * @return Connection to mysql database computer-database-db
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	synchronized public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null || conn.isClosed()) {
			ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH);
			String url = resources.getString("url");
			String user = resources.getString("user");
			String pass = resources.getString("pass");
			String driver = resources.getString("driver");
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		}
		return conn;
	}
	
	
	/**
	 * Closes mysql connection to computer-database-db if not already closed
	 * @throws SQLException
	 */
	synchronized public static void close () throws SQLException {
			if (conn != null) {
				conn.close();
			}
	}
	
	public static void printExceptionList(SQLException se) {
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
	}
}
