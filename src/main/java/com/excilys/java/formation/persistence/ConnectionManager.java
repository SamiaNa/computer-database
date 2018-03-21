package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public enum ConnectionManager {

    INSTANCE;
    private static Connection conn = null;
    private static final String RESOURCE_PATH = "connection";

    /****
     * ouvre plusieurs connections
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    synchronized public Connection open() throws ClassNotFoundException, SQLException {
        if (conn == null || conn.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH);
            String url = resources.getString("url");
            String user = resources.getString("user");
            String pass = resources.getString("pass");
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }

    synchronized public Connection open(String url, String user, String pass)
            throws ClassNotFoundException, SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }

    /**
     * Closes mysql connection to computer-database-db if not already closed
     *
     * @throws SQLException
     */
    synchronized public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void printExceptionList(SQLException se) {
        for (Throwable e : se) {
            System.out.println("Problem : " + e);
        }
    }
}
