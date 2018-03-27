package com.excilys.java.formation.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.ComputerDAOImpl;
import com.excilys.java.formation.persistence.implementations.ConnectionManager;
import com.excilys.java.formation.persistence.implementations.DAOException;

public class ComputerDAOImplTest{


    @BeforeEach
    void before() throws SQLException, InstantiationException, IllegalAccessException,  ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        destroyTables();
        createTableCompany();
        createTableComputer();
        populateTableCompany();
        populateTableComputer();
    }


    void createTableCompany() throws  SQLException, ClassNotFoundException{
        Connection conn = ConnectionManager.INSTANCE.open();
        String sql = "  create table computer (" +
                "    id                        bigint not null identity," +
                "    name                      varchar(255)," +
                "    introduced                date NULL," +
                "    discontinued              date NULL," +
                "    company_id                bigint default NULL," +
                "    constraint pk_computer primary key (id));";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        conn.close();

    }


    void createTableComputer() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionManager.INSTANCE.open();
        String sql = "  create table company (" +
                "    id bigint not null identity," +
                "    name varchar(255)," +
                "    constraint pk_company primary key (id));";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt = conn.prepareStatement("alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;");
        stmt.executeUpdate();
        conn.close();
    }

    void populateTableComputer() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO computer (name, company_id, introduced, discontinued) VALUES ('HP1', 0, NULL, NULL);");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("INSERT INTO computer (name, company_id, introduced, discontinued) VALUES ('Ordi1', NULL, '1998-01-01' , NULL);");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("INSERT INTO computer (name, company_id, introduced, discontinued) values ('Apple IIe', 2,null,null);");
        stmt.executeUpdate();
        conn.close();
    }

    void populateTableCompany() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO company (name) VALUES ('HP');");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("INSERT INTO company (name) VALUES ('Dell');");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("INSERT INTO company (name) VALUES ('Apple');");
        stmt.executeUpdate();
        conn.close();

    }

    void destroyTables() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("drop table if exists computer;");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("drop table if exists company;");
        stmt.executeUpdate();
        conn.close();
    }


    @Test
    void getAllTest() throws DAOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        List<Computer> computers = ComputerDAOImpl.INSTANCE.getAll();
        assertEquals(computers.size(), 3);
        Computer comp0 = computers.get(0);
        assertEquals(comp0.getName(), "HP1");
        assertEquals(comp0.getId(), 0L);
        assertNull(comp0.getIntroduced());
        assertNull(comp0.getDiscontinued());
        assertEquals(comp0.getCompany().getId(), 0);
        assertEquals(comp0.getCompany().getName(), "HP");
    }


    @Test
    void getComputerByIdTest() throws InstantiationException, IllegalAccessException, DAOException, SQLException {
        Optional<Computer> computerOpt = ComputerDAOImpl.INSTANCE.getComputerById(-1);
        assertFalse(computerOpt.isPresent());

        computerOpt = ComputerDAOImpl.INSTANCE.getComputerById(10);
        assertFalse(computerOpt.isPresent());

        computerOpt = ComputerDAOImpl.INSTANCE.getComputerById(2);
        assertTrue(computerOpt.isPresent());
        Computer computer = computerOpt.get();
        assertEquals(computer.getName(), "Apple IIe");
        assertEquals(computer.getId(), 2);
        assertEquals(computer.getCompany().getId(), 2);
        assertEquals(computer.getCompany().getName(), "Apple");
    }


    @Test
    void createComputerTest () throws  DAOException, SQLException, ClassNotFoundException{
        Computer c0 = new Computer ("Ordi1", null, null, new Company(1, null));
        Optional<Long> id = ComputerDAOImpl.INSTANCE.createComputer(c0);
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE id = ?");
        stmt.setLong(1, id.get());
        ResultSet res =	stmt.executeQuery();
        assertTrue(res.next());
        assertEquals(Optional.of(res.getLong(1)), id);
        assertEquals(res.getString(2), c0.getName());
        assertEquals(res.getDate(3), c0.getIntroduced());
        assertEquals(res.getDate(4), c0.getDiscontinued());
        assertEquals(res.getLong(5), c0.getCompany().getId());
        assertEquals(res.getString(6), "Dell");

    }

}
