package com.excilys.java.formation.persistence.implementations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionManager{

    INSTANCE;
    private static final String RESOURCE_PATH = "connection";
    private Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH, Locale.getDefault());
            String url = resources.getString("url");
            String user = resources.getString("user");
            String pass = resources.getString("pass");
            int maxPoolSize = Integer.parseInt(resources.getString("maximumPoolSize"));
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setMaximumPoolSize(maxPoolSize);
            ds = new HikariDataSource(config);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  Connection open() throws SQLException, ClassNotFoundException {
        return ds.getConnection();
    }



}
