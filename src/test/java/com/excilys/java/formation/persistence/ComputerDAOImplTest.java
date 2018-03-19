package com.excilys.java.formation.persistence;

import java.sql.Connection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mysql.cj.jdbc.PreparedStatement;


@RunWith(MockitoJUnitRunner.class)
public class ComputerDAOImplTest {

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement stmt;

	@Test
	void testGetAll() {
	}

}
