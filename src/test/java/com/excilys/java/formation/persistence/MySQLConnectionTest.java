package com.excilys.java.formation.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.excilys.java.formation.persistence.ConnectionManager;


class MySQLConnectionTest {

	@Test
	void test() throws ClassNotFoundException, SQLException {
		Connection c = ConnectionManager.open();
		assertTrue(c.isValid(100));
		Connection c1 = ConnectionManager.open();
		assertTrue(c == c1);
		c.close();
		assertTrue(c.isClosed());
		assertTrue(c1.isClosed());
	}

}
