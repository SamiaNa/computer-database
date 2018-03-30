package com.excilys.java.formation.persistence.implementations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerMapper;
import com.excilys.java.formation.persistence.interfaces.ComputerDAO;

public enum ComputerDAOImpl implements ComputerDAO {

    INSTANCE;

    private static String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";
    private static String SELECT_ORDER = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static String SELECT_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?;";
    private static String SELECT_BY_ID_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ;";
    private static String SELECT_BY_NAME = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE (computer.name LIKE ? OR company.name LIKE ?) LIMIT ? OFFSET ?;";
    private static String INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?);";
    private static String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private static String DELETE = "DELETE FROM computer WHERE id = ?;";
    private static String DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id = ?;";
    private static String COUNT = "SELECT COUNT(id) FROM computer";
    private static String COUNT_NAME = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?;";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ComputerMapper computerMapper = ComputerMapper.INSTANCE;
    private final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    @Override
    public List<Computer> getAll() throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_ALL);) {
            logger.debug("(getAll) Query : " + stmt.toString());
            ResultSet res = stmt.executeQuery();
            return computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in getAll()", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> get(int offset, int size) throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_LIMIT);) {
            stmt.setInt(1, size);
            stmt.setInt(2, offset);
            logger.debug("(get) Query : " + stmt.toString());
            ResultSet res = stmt.executeQuery();
            return computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in get(offset, size)", offset, size, e);
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Computer> getComputerById(long id) throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_JOIN)) {
            stmt.setLong(1, id);
            logger.debug("(getComputerById) Query : " + stmt.toString());
            ResultSet res = stmt.executeQuery();
            return computerMapper.createComputerFromResultSet(res);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in getComputerById({}), id", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> getByName(String name, int offset, int limit) throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME);) {
            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, "%" + name + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            logger.debug("(getByName) Query : " + stmt.toString());
            ResultSet res = stmt.executeQuery();
            return computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in getByName({}, {}, {})", name, offset, limit, e);
            throw new DAOException(e);
        }

    }

    @Override
    public List<Computer> getByOrder(String orderCriteria, String order, int offset, int limit) throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(
                        SELECT_ORDER + " ORDER BY " + orderCriteria + " " + order + " LIMIT ? OFFSET ?;");) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            logger.info("(getByName) Query : " + stmt.toString());
            ResultSet res = stmt.executeQuery();
            return computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in getByOrder({}, {}, {})", orderCriteria, offset, limit, e);
            throw new DAOException(e);
        }

    }

    @Override
    public List<Computer> getByOrder(String orderCriteria, String order, String search, int offset, int limit)
            throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(
                        SELECT_ORDER + " WHERE (computer.name LIKE ? OR company.name LIKE ?) ORDER BY " + orderCriteria
                        + " " + order + " LIMIT ? OFFSET ?;");) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            logger.info("(getByName) Query : " + stmt.toString());
            ResultSet res = stmt.executeQuery();
            return computerMapper.createComputerListFromResultSet(res);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in getByOrder({}, {}, {})", orderCriteria, offset, limit, e);
            throw new DAOException(e);
        }

    }

    private void setDateOrNull(LocalDate d, PreparedStatement stmt, int position) throws DAOException {
        try {
            if (d == null) {
                stmt.setNull(position, java.sql.Types.DATE);
            } else {
                stmt.setDate(position, Date.valueOf(d));
            }
        } catch (SQLException e) {
            logger.error("Exception in setDateOrNull({}, {}, {})", d, stmt, position, e);
            throw new DAOException(e);
        }
    }

    private void setCompanyIdOrNull(Company c, PreparedStatement stmt, int position) throws DAOException {
        try {
            if (c == null) {
                stmt.setNull(position, java.sql.Types.BIGINT);
            } else {
                stmt.setLong(position, c.getId());
            }
        } catch (SQLException e) {
            logger.error("Exception in setCompanyIdOrNull({}, {}, {})", c, stmt, position, e);
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Long> createComputer(Computer c) throws DAOException {
        try (Connection connection = connectionManager.open();
                AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(connection, false);
                AutoRollback autoRollback = new AutoRollback(connection);
                PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            stmt.setString(1, c.getName());
            setCompanyIdOrNull(c.getCompany(), stmt, 2);
            setDateOrNull(c.getIntroduced(), stmt, 3);
            setDateOrNull(c.getDiscontinued(), stmt, 4);
            logger.debug("(createComputer) Query : " + stmt.toString());
            stmt.executeUpdate();
            autoRollback.commit();
            ResultSet res = stmt.getGeneratedKeys();
            if (res.next()) {
                return Optional.of(res.getLong(1));
            } else {
                return Optional.empty();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in createComptuer({})", c, e);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Computer c) throws DAOException {
        try (Connection connection = connectionManager.open();
                AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(connection, false);
                AutoRollback autoRollback = new AutoRollback(connection);
                PreparedStatement stmt = connection.prepareStatement(UPDATE);) {
            stmt.setString(1, c.getName());
            setDateOrNull(c.getIntroduced(), stmt, 2);
            setDateOrNull(c.getDiscontinued(), stmt, 3);
            setCompanyIdOrNull(c.getCompany(), stmt, 4);
            stmt.setLong(5, c.getId());
            logger.debug("(update) Query : " + stmt.toString());
            stmt.executeUpdate();
            autoRollback.commit();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in update({c})", c, e);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = connectionManager.open();
                AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(connection, false);
                AutoRollback autoRollback = new AutoRollback(connection);
                PreparedStatement stmt = connection.prepareStatement(DELETE);) {
            stmt.setLong(1, id);
            logger.debug("(delete) Query : " + stmt.toString());
            stmt.executeUpdate();
            autoRollback.commit();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in delete({})", id, e);
            throw new DAOException(e);
        }
    }

    public void delete(Connection connection, long id) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_BY_COMPANY);) {
            stmt.setLong(1, id);
            logger.debug("(delete) Query : " + stmt.toString());
            stmt.executeUpdate();
        }catch (SQLException e) {
            logger.error("Exception in delete({}, {})", connection, id);
            throw new DAOException(e);
        }

    }

    public void delete(List<Long> ids) throws DAOException {
        try (Connection connection = connectionManager.open();
                AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(connection, false);
                AutoRollback autoRollback = new AutoRollback(connection);
                PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            for (long id : ids) {
                stmt.setLong(1, id);
                logger.debug("(delete) Query : " + stmt.toString());
                stmt.executeUpdate();
            }
            autoRollback.commit();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in delete({})", ids.toString(), e);
            throw new DAOException(e);
        }
    }

    @Override
    public int count() throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(COUNT);) {
            ResultSet rSet = stmt.executeQuery();
            logger.debug("(count) Query : " + stmt.toString());
            if (rSet.next()) {
                return rSet.getInt(1);
            }
            return -1;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in count()", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int count(String name) throws DAOException {
        try (Connection connection = connectionManager.open();
                PreparedStatement stmt = connection.prepareStatement(COUNT_NAME);) {
            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, "%" + name + "%");
            logger.debug("(count) Query : " + stmt.toString());
            ResultSet rSet = stmt.executeQuery();
            if (rSet.next()) {
                return rSet.getInt(1);
            }
            return -1;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception in count()", e);
            throw new DAOException(e);
        }
    }

}
