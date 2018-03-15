package com.excilys.java.formation.model.persistence;

import java.sql.*;
public class MySQLConnection {

	private static Connection conn = null;
	
	private MySQLConnection() {
	}
	
	/**
	 *  Creates or return a connection to mysql database
	 * @return Connection to mysql database computer-database-db
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	synchronized public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null || conn.isClosed()) {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
			conn = DriverManager.getConnection(url, "admincdb", "qwerty1234");
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
