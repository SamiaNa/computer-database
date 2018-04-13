package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.persist.HsqlDatabaseProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.ComputerDAOJdbc;
import com.excilys.java.formation.persistence.implementations.DAOException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ComputerDAOImplTest{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ComputerDAOJdbc computerDAO;

    @Before
    public void before() throws SQLException, InstantiationException, IllegalAccessException,  ClassNotFoundException, IOException, SqlToolError {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        Connection connection = DataSourceUtils.getConnection(dataSource);

        InputStream inputStream = HsqlDatabaseProperties.class.getResourceAsStream("/hsqldb_script.sql");
        SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
                new File("."));
        sqlFile.setConnection(connection);
        sqlFile.execute();
    }



    @Test
    public void testGetAll() throws DAOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        List<Computer> computers = computerDAO.getAll();
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
    public void testGetComputerByValidId() throws DAOException {
        Optional<Computer> computerOpt = computerDAO.getComputerById(-1);
        assertFalse(computerOpt.isPresent());
    }

    @Test
    public void testGetComputerByInvalidId() throws DAOException {
        Optional<Computer> computerOpt = computerDAO.getComputerById(2);
        assertTrue(computerOpt.isPresent());
        Computer computer = computerOpt.get();
        assertEquals(computer.getName(), "Apple IIe");
        assertEquals(computer.getId(), 2);
        assertEquals(computer.getCompany().getId(), 2);
        assertEquals(computer.getCompany().getName(), "Apple");
    }


    @Test
    public void testCreateComputerValid () throws  DAOException, SQLException, ClassNotFoundException{
        Computer c0 = new Computer ("Ordi1", null, null, new Company(1, null));
        Optional<Long> id = computerDAO.createComputer(c0);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        PreparedStatement stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE id = ?");
        stmt.setLong(1, id.get());
        ResultSet res = stmt.executeQuery();
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
    public void testCreateComputerInvalid() throws DAOException, ClassNotFoundException, SQLException {
        Computer c = new Computer (500, "OrdiPBDate", LocalDate.parse("2010-01-02"), LocalDate.parse("2005-02-03"), new Company(1, null));
        Optional<Long> id = computerDAO.createComputer(c);
        Connection conn = DataSourceUtils.getConnection(dataSource);
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



    @Test(expected=DAOException.class)
    public void testCreateComputerInvalidError() throws DAOException, ClassNotFoundException, SQLException {
        Computer c = new Computer (500, "OrdiPBDate", LocalDate.parse("2010-01-02"), LocalDate.parse("2005-02-03"), new Company(150, null));
        computerDAO.createComputer(c);
    }


    @Test
    public void testCount() throws DAOException{
        int count = computerDAO.count();
        assertEquals(3, count);
    }

    @Test
    public void testCountWithName() throws DAOException{
        int count = computerDAO.count("1");
        assertEquals(2, count);
    }

    @Test
    public void testDelete() throws DAOException, ClassNotFoundException, SQLException {
        computerDAO.delete(1);
        Connection conn = DataSourceUtils.getConnection(dataSource);
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
    public void testDeleteNotInDB() throws DAOException {
        computerDAO.delete(5);
        computerDAO.delete(-1);
    }

    @Test
    public void testDeleteListAll() throws DAOException, ClassNotFoundException, SQLException {
        computerDAO.delete(Arrays.asList(0l,1l,2l));
        Connection conn = DataSourceUtils.getConnection(dataSource);
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM computer;");
        ResultSet res = stmt.executeQuery();
        res.next();
        assertEquals(0, res.getInt(1));
    }

    @Test
    public void testDeleteEmptyList() throws DAOException, ClassNotFoundException, SQLException {
        computerDAO.delete(new ArrayList<Long>());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM computer;");
        ResultSet res = stmt.executeQuery();
        res.next();
        assertEquals(3, res.getInt(1));
    }

}
