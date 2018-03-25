package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerMapper;

public enum ComputerDAOImpl implements ComputerDAO {

    INSTANCE;

    private static String SELECT_ALL_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";
    private static String SELECT_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?;";
    private static String SELECT_BY_ID_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ;";
    private static String SELECT_BY_NAME = "SELECT id, name FROM computer WHERE name LIKE ?;";
    private static String INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?);";
    private static String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private static String DELETE = "DELETE FROM computer WHERE id = ?;";
    private static String COUNT = "SELECT COUNT(id) FROM computer";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Computer> getAll() throws  DAOException, ConnectionException {
        ComputerMapper computerMapper = ComputerMapper.INSTANCE;
        List<Computer> computers = new ArrayList<>();
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_JOIN);) {
            logger.debug("(getAll) Query : "+stmt.toString());
            ResultSet res = stmt.executeQuery();
            computers = computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
        return computers;
    }

    @Override
    public List<Computer> get(int offset, int size) throws ConnectionException, DAOException {
        ComputerMapper computerMapper = ComputerMapper.INSTANCE;
        List<Computer> computers = new ArrayList<>();
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_LIMIT);) {
            stmt.setInt(1, size);
            stmt.setInt(2, offset);
            logger.debug("(get) Query : "+stmt.toString());
            ResultSet res = stmt.executeQuery();
            computers = computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
        return computers;
    }

    @Override
    public Optional<Computer> getComputerById(long id) throws DAOException, ConnectionException {
        ComputerMapper computerMapper = ComputerMapper.INSTANCE;
        Optional<Computer> c = Optional.empty();
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_JOIN)) {
            stmt.setLong(1, id);
            logger.debug("(getComputerById) Query : "+stmt.toString());
            ResultSet res = stmt.executeQuery();
            c = computerMapper.createComputerFromResultSet(res, id);
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
        return c;
    }

    public List<Computer> getByName(String name) throws DAOException, ConnectionException {
        ComputerMapper computerMapper = ComputerMapper.INSTANCE;
        List<Computer> computers = new ArrayList<>();
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME);) {
            stmt.setString(1, "%" + name + "%");
            logger.debug("(getByName) Query : "+stmt.toString());
            ResultSet res = stmt.executeQuery();
            computers = computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
        return computers;
    }

    private void setDateOrNull(LocalDate d, PreparedStatement stmt, int position) throws SQLException {
        if (d == null) {
            stmt.setNull(position, java.sql.Types.DATE);
        } else {
            stmt.setDate(position, Date.valueOf(d));
        }
    }

    private void setCompanyIdOrNull(Company c, PreparedStatement stmt, int position) throws SQLException {
        if (c == null) {
            stmt.setNull(position, java.sql.Types.BIGINT);
        } else {
            stmt.setLong(position, c.getId());
        }
    }

    @Override
    public Long createComputer(Computer c) throws DAOException, ConnectionException, DAOConstraintException {
        long id = -1;
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, c.getName());
            setCompanyIdOrNull(c.getCompany(), stmt, 2);
            setDateOrNull(c.getIntroduced(), stmt, 3);
            setDateOrNull(c.getDiscontinued(), stmt, 4);
            stmt.executeUpdate();
            ResultSet res = stmt.getGeneratedKeys();
            res.next();
            id = res.getLong(1);
            logger.debug("(createComputer) Query : "+stmt.toString());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOConstraintException(e.getMessage());
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
        return id;
    }

    @Override
    public boolean update(Computer c) throws ConnectionException, DAOException {
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(UPDATE);) {
            stmt.setString(1, c.getName());
            setDateOrNull(c.getIntroduced(), stmt, 2);
            setDateOrNull(c.getDiscontinued(), stmt, 3);
            setCompanyIdOrNull(c.getCompany(), stmt, 4);
            stmt.setLong(5, c.getId());
            logger.debug("(update) Query : "+stmt.toString());
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws DAOException, ConnectionException {
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(DELETE);) {
            stmt.setLong(1, id);
            logger.debug("(delete) Query : "+stmt.toString());
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
    }

    @Override
    public int count() throws DAOException, ConnectionException {
        try (Connection connection = ConnectionManager.INSTANCE.open();
                PreparedStatement stmt = connection.prepareStatement(COUNT);) {
            ResultSet rSet = stmt.executeQuery();
            logger.debug("(count) Query : "+stmt.toString());
            if (rSet.next()) {
                return rSet.getInt(1);
            }
            return -1;
        } catch (SQLException se) {
            for (Throwable e : se) {
                logger.error(e.toString());
            }
            throw new DAOException(se.getMessage());
        }
    }

}
