package com.excilys.java.formation.persistence.implementations;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerRowMapper;
import com.excilys.java.formation.persistence.interfaces.ComputerDAO;
import com.excilys.java.formation.validator.ValidatorException;
import com.mysql.cj.api.jdbc.Statement;

@Repository
public class ComputerDAOJdbc implements ComputerDAO {

    private static final String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";
    private static final String SELECT_ORDER = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String SELECT_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?;";
    private static final String SELECT_BY_ID_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ;";
    private static final String SELECT_BY_NAME = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE (computer.name LIKE ? OR company.name LIKE ?) LIMIT ? OFFSET ?;";
    private static final String INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
    private static final String DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id = ?;";
    private static final String COUNT = "SELECT COUNT(id) FROM computer";
    private static final String COUNT_NAME = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?;";

    private static final String COMPUTER_ID = "idcomputer";
    private static final String COMPUTER_NAME = "namecomputer";
    private static final String COMPUTER_INTRO = "introcomputer";
    private static final String COMPUTER_DISC = "disccomputer";
    private static final String COMPUTER_COMPANY = "namecompany";

    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Computer> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new ComputerRowMapper());
    }

    @Override
    public List<Computer> get(int offset, int size) {
        return jdbcTemplate.query(SELECT_LIMIT, new ComputerRowMapper(), size, offset);
    }

    @Override
    public Optional<Computer> getComputerById(long id) throws DAOException {
        List<Computer> computer = jdbcTemplate.query(SELECT_BY_ID_JOIN, new ComputerRowMapper(), id);
        try {
            return Optional.of(computer.get(0));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Computer> getByName(String name, int offset, int limit) {
        String nameParam = "%" + name + "%";
        List<Computer> list = jdbcTemplate.query(SELECT_BY_NAME, new ComputerRowMapper(), nameParam, nameParam, limit,
                offset);
        return list;
    }

    @Override
    public List<Computer> getByOrder(String orderCriteria, String order, int offset, int limit)
            throws ValidatorException {
        checkOrder(order);
        return jdbcTemplate.query(
                SELECT_ORDER + " ORDER BY " + getColumnName(orderCriteria) + " " + order + " LIMIT ? OFFSET ?;",
                new ComputerRowMapper(), limit, offset);
    }

    private String getColumnName(String orderCriteria) throws ValidatorException {
        switch (orderCriteria) {
        case COMPUTER_ID:
            return "computer.id";
        case COMPUTER_NAME:
            return "computer.name";
        case COMPUTER_INTRO:
            return "computer.introduced";
        case COMPUTER_DISC:
            return "computer.discontinued";
        case COMPUTER_COMPANY:
            return "company.name";
        default:
            String message = "Unknown order criteria : " + orderCriteria;
            logger.error(message);
            throw new ValidatorException(message);
        }
    }

    private void checkOrder(String order) throws ValidatorException {
        if (!order.equalsIgnoreCase(ASCENDING) && !order.equalsIgnoreCase(DESCENDING)) {
            String message = "Unknown parameter " + order;
            logger.error(message);
            throw new ValidatorException(message);
        }

    }

    @Override
    public List<Computer> getByOrder(String orderCriteria, String order, String search, int offset, int limit)
            throws ValidatorException {
        checkOrder(order);
        String searchParam = "%" + search + "%";
        return jdbcTemplate.query(
                SELECT_ORDER + " WHERE (computer.name LIKE ? OR company.name LIKE ?) ORDER BY "
                        + getColumnName(orderCriteria) + " " + order + " LIMIT ? OFFSET ?;",
                        new ComputerRowMapper(), searchParam, searchParam, limit, offset);

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
    public long createComputer(Computer c) throws DAOException {
        logger.info("In create computer\n computer = {}", c);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            try {
                logger.info("Create computer");
                PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                logger.info("After create statement");
                stmt.setString(1, c.getName());
                logger.info("After set name");
                setCompanyIdOrNull(c.getCompany(), stmt, 2);
                logger.info("After set company");
                setDateOrNull(c.getIntroduced(), stmt, 3);
                logger.info("After intro");
                setDateOrNull(c.getDiscontinued(), stmt, 4);
                logger.info("After disc");
                return stmt;
            } catch (DAOException e) {
                throw new SQLException(e);
            }
        }, keyHolder);
        logger.info("After update");
        logger.info("{}", keyHolder.getKey());
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Computer c) {
        jdbcTemplate.update(UPDATE, c.getName(), c.getIntroduced(), c.getDiscontinued(),
                (c.getCompany() == null) ? null : c.getCompany().getId(), c.getId());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    public void deleteCompany(long id) {
        jdbcTemplate.update(DELETE_BY_COMPANY, id);
    }

    @Override
    public void delete(List<Long> ids) {
        for (long id : ids) {
            jdbcTemplate.update(DELETE, id);
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public int count(String name) {
        return jdbcTemplate.queryForObject(COUNT_NAME, Integer.class, "%" + name + "%", "%" + name + "%");
    }

}