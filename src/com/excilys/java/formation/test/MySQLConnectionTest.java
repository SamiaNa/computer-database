package com.excilys.java.formation.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.excilys.java.formation.persistence.MySQLConnection;


class MySQLConnectionTest {

	@Test
	void test() throws ClassNotFoundException, SQLException {
		Connection c = MySQLConnection.getConnection();
		assertTrue(c.isValid(100));
		Connection c1 = MySQLConnection.getConnection();
		assertTrue(c == c1);
		c.close();
		assertTrue(c.isClosed());
		assertTrue(c1.isClosed());
	}

}
