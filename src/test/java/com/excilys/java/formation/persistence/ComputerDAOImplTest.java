package com.excilys.java.formation.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.persist.HsqlDatabaseProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.ComputerDAOImpl;
import com.excilys.java.formation.persistence.implementations.ConnectionManager;
import com.excilys.java.formation.persistence.implementations.DAOException;

public class ComputerDAOImplTest{

    @BeforeEach
    void before() throws SQLException, InstantiationException, IllegalAccessException,  ClassNotFoundException, IOException, SqlToolError {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        Connection connection = ConnectionManager.INSTANCE.open();
        InputStream inputStream = HsqlDatabaseProperties.class.getResourceAsStream("/hsqldb_script.sql");
        SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
                new File("."));
        sqlFile.setConnection(connection);
        sqlFile.execute();
    }



    @Test
    void testGetAll() throws DAOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
    void testGetComputerByValidId() throws DAOException {
        Optional<Computer> computerOpt = ComputerDAOImpl.INSTANCE.getComputerById(-1);
        assertFalse(computerOpt.isPresent());
    }

    @Test
    void testGetComputerByInvalidId() throws DAOException {
        Optional<Computer> computerOpt = ComputerDAOImpl.INSTANCE.getComputerById(2);
        assertTrue(computerOpt.isPresent());
        Computer computer = computerOpt.get();
        assertEquals(computer.getName(), "Apple IIe");
        assertEquals(computer.getId(), 2);
        assertEquals(computer.getCompany().getId(), 2);
        assertEquals(computer.getCompany().getName(), "Apple");
    }

    @Test
    void testCreateComputerValid () throws  DAOException, SQLException, ClassNotFoundException{
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
        res.close();
        stmt.close();
        conn.close();
    }

    @Test
    void testCreateComputerInvalid() throws DAOException, ClassNotFoundException, SQLException {
        Computer c = new Computer (500, "OrdiPBDate", LocalDate.parse("2010-01-02"), LocalDate.parse("2005-02-03"), new Company(1, null));
        Optional<Long> id = ComputerDAOImpl.INSTANCE.createComputer(c);
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE id = ?");
        stmt.setLong(1, id.get());
        ResultSet res = stmt.executeQuery();
        assertTrue(res.next());
        assertEquals(Optional.of(res.getLong(1)), id);
        assertEquals(res.getString(2), c.getName());
        assertEquals(res.getDate(3).toLocalDate(), c.getIntroduced());
        assertEquals(res.getDate(4).toLocalDate(), c.getDiscontinued());
        assertEquals(res.getLong(5), c.getCompany().getId());
        assertEquals(res.getString(6), "Dell");
        res.close();
        stmt.close();
        conn.close();
    }

    @Test
    void testCreateComputerInvalidError() throws DAOException, ClassNotFoundException, SQLException {
        Computer c = new Computer (500, "OrdiPBDate", LocalDate.parse("2010-01-02"), LocalDate.parse("2005-02-03"), new Company(150, null));
        Assertions.assertThrows(DAOException.class, () -> ComputerDAOImpl.INSTANCE.createComputer(c));
    }

    @Test
    void testCount() throws DAOException{
        int count = ComputerDAOImpl.INSTANCE.count();
        assertEquals(3, count);
    }

    @Test
    void testCountWithName() throws DAOException{
        int count = ComputerDAOImpl.INSTANCE.count("1");
        assertEquals(2, count);
    }

    @Test
    void testDelete() throws DAOException, ClassNotFoundException, SQLException {
        ComputerDAOImpl.INSTANCE.delete(1);
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM computer;");
        ResultSet res = stmt.executeQuery();
        res.next();
        assertEquals(2, res.getInt(1));
        stmt = conn.prepareStatement("SELECT computer.id FROM computer;");
        res = stmt.executeQuery();
        res.next();
        assertEquals(0, res.getInt(1));
        res.next();
        assertEquals(2, res.getInt(1));
        res.close();
        stmt.close();
        conn.close();
    }

    @Test
    void testDeleteNotInDB() throws DAOException {
        ComputerDAOImpl.INSTANCE.delete(5);
        ComputerDAOImpl.INSTANCE.delete(-1);
    }

    @Test
    void testDeleteListAll() throws DAOException, ClassNotFoundException, SQLException {
        ComputerDAOImpl.INSTANCE.delete(Arrays.asList(0l,1l,2l));
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM computer;");
        ResultSet res = stmt.executeQuery();
        res.next();
        assertEquals(0, res.getInt(1));
    }

    @Test
    void testDeleteEmptyList() throws DAOException, ClassNotFoundException, SQLException {
        ComputerDAOImpl.INSTANCE.delete(new ArrayList<Long>());
        Connection conn = ConnectionManager.INSTANCE.open();
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM computer;");
        ResultSet res = stmt.executeQuery();
        res.next();
        assertEquals(3, res.getInt(1));
    }
}
