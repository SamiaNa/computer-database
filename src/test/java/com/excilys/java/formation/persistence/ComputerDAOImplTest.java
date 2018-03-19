package com.excilys.java.formation.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.java.formation.entities.Computer;

public class ComputerDAOImplTest{


	void createTableCompany() {

	}

	void createTableComputer(Connection conn) throws SQLException {
		// Create and populate table Company
		String sql = "  create table company (" +
				"    id bigint not null identity," +
				"    name varchar(255)," +
				"    constraint pk_company primary key (id));";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.executeUpdate();
		stmt = conn.prepareStatement("INSERT INTO company (name) VALUES ('HP');");
		stmt.executeUpdate();
		stmt = conn.prepareStatement("INSERT INTO company (name) VALUES ('Dell');");
		stmt.executeUpdate();
		stmt = conn.prepareStatement("INSERT INTO company (name) VALUES ('Apple');");
		stmt.executeUpdate();

	}

	void createTableCompany(Connection conn) throws SQLException{
		// Create and populate table Computer
		String sql = "  create table computer (" +
				"    id                        bigint not null identity," +
				"    name                      varchar(255)," +
				"    introduced                date NULL," +
				"    discontinued              date NULL," +
				"    company_id                bigint default NULL," +
				"    constraint pk_computer primary key (id));";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.executeUpdate();
		stmt = conn.prepareStatement("INSERT INTO computer (name, company_id, introduced, discontinued) VALUES ('HP1', 0, NULL, NULL);");
		stmt.executeUpdate();
		stmt = conn.prepareStatement("INSERT INTO computer (name, company_id, introduced, discontinued) VALUES ('Ordi1', NULL, '1998-01-01' , NULL);");
		stmt.executeUpdate();
		stmt = conn.prepareStatement("insert into computer (name, company_id, introduced, discontinued) values ('Apple IIe', 2,null,null);");
		stmt.executeUpdate();
	}

	void destroyTables(Connection conn) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("drop table if exists computer;");
		stmt.executeUpdate();
		stmt = conn.prepareStatement("drop table if exists company;");
		stmt.executeUpdate();
	}

	@Test
	void getAllTest() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection conn = ConnectionManager.open("jdbc:hsqldb:file:testdb", "sa", "");
		destroyTables(conn);
		createTableComputer(conn);
		createTableCompany(conn);
		List<Computer> computers = ComputerDAOImpl.INSTANCE.getAll();
		assertEquals(computers.size(), 3);
		Computer comp0 = computers.get(0);
		assertEquals(comp0.getName(), "HP1");
		assertEquals(comp0.getId(), 0L);
		assertNull(comp0.getIntroduced());
		assertNull(comp0.getDiscontinued());
		assertEquals(comp0.getCompany().getId(), 0);
		assertEquals(comp0.getCompany().getName(), "HP");
		conn.close();
	}

}
