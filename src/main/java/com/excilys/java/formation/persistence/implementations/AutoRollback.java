package com.excilys.java.formation.persistence.implementations;

import java.sql.Connection;
import java.sql.SQLException;

public class AutoRollback implements AutoCloseable {

    private Connection conn;
    private boolean committed;

    public AutoRollback(Connection conn) {
        this.conn = conn;
    }

    public void commit() throws SQLException {
        conn.commit();
        committed = true;
    }

    @Override
    public void close() throws SQLException {
        if(!committed) {
            conn.rollback();
        }
    }

}