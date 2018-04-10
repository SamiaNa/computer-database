package com.excilys.java.formation.persistence.implementations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionManager{

    INSTANCE;
    private static final String RESOURCE_PATH = "connection";
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    public  Connection open() throws SQLException, ClassNotFoundException {
        if (ds == null) {
            ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH, Locale.getDefault());
            String url = resources.getString("url");
            String user = resources.getString("user");
            String pass = resources.getString("pass");
            int maxPoolSize = Integer.parseInt(resources.getString("maximumPoolSize"));
            Class.forName(resources.getString("driver"));
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setMaximumPoolSize(maxPoolSize);
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }



}