package com.excilys.java.formation.persistence.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ConnectionManager implements AutoCloseable{

    INSTANCE;
    private static Connection conn = null;
    private static final String RESOURCE_PATH = "connection";
    private Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    /****
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConnectionException
     */
    synchronized public Connection open() throws ClassNotFoundException, SQLException  {
        logger.info("Trying to get database connection");
        if (conn == null || conn.isClosed()) {
            logger.info("No existing connection, establishing new connection");
            Class.forName("com.mysql.cj.jdbc.Driver");
            ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH, Locale.getDefault());
            String url = resources.getString("url");
            String user = resources.getString("user");
            String pass = resources.getString("pass");
            conn = DriverManager.getConnection(url, user, pass);
            logger.info("Connection succefully established");
        }
        return conn;
    }


    synchronized public Connection open(String url, String user, String pass) throws SQLException  {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }

    /**
     * Closes mysql connection to computer-database-db if not already closed
     * @throws ConnectionException
     *
     * @throws SQLException
     */
    @Override
    synchronized public void close() throws SQLException   {
        if (conn != null) {
            conn.close();
        }
    }


}
