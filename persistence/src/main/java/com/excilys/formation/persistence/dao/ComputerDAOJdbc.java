package com.excilys.formation.persistence.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.formation.binding.mappers.ComputerRowMapper;
import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.core.entities.QCompany;
import com.excilys.formation.core.entities.QComputer;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
public class ComputerDAOJdbc {

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
    private ComputerRowMapper computerRowMapper;


    private HibernateQueryFactory queryFactory;
    

    @Autowired
    public ComputerDAOJdbc( SessionFactory sessionFactory, ComputerRowMapper computerRowMapper) {
        this.queryFactory = new HibernateQueryFactory(sessionFactory.openSession());
        this.computerRowMapper = computerRowMapper;
    }
    
   /*@Autowired
    private ComputerDAOJdbc(ComputerRowMapper computerRowMapper) {
        this.computerRowMapper = computerRowMapper;
    }*/

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ComputerDAOJdbc() {
        super();
    }

    public List<Computer> getAll() {
        return jdbcTemplate.query(SELECT_ALL, computerRowMapper);
    }

    public List<Computer> get(int offset, int size) {
    	return (List<Computer>) queryFactory.from(QComputer.computer).offset(offset).limit(size).fetch();
    	//return jdbcTemplate.query(SELECT_LIMIT, computerRowMapper, size, offset);
    }

    public Optional<Computer> getComputerById(long id) {
    	
    	List<Computer> computer = jdbcTemplate.query(SELECT_BY_ID_JOIN, computerRowMapper, id);
        try {
            return Optional.of(computer.get(0));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public List<Computer> getByName(String name, int offset, int limit) {
        String nameParam = "%" + name + "%";
        List<Computer> list = jdbcTemplate.query(SELECT_BY_NAME, computerRowMapper, nameParam, nameParam, limit,
                offset);
        return list;
    }

    public List<Computer> getByOrder(String orderCriteria, String order, int offset, int limit)
            throws DAOException {
        checkOrder(order);
    
        logger.info(SELECT_ORDER + " ORDER BY " + getColumnName(orderCriteria) + " " + order + " LIMIT ? OFFSET ?;");
        return jdbcTemplate.query(
                SELECT_ORDER + " ORDER BY " + getColumnName(orderCriteria) + " " + order + " LIMIT ? OFFSET ?;",
                new ComputerRowMapper(), limit, offset);
    }

    private String getColumnName(String orderCriteria) throws DAOException {
    
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
            throw new DAOException(message);
        }
    }

    private void checkOrder(String order) throws DAOException {
        if (!order.equalsIgnoreCase(ASCENDING) && !order.equalsIgnoreCase(DESCENDING)) {
            String message = "Unknown parameter " + order;
            logger.error(message);
            throw new DAOException(message);
        }

    }

    public List<Computer> getByOrder(String orderCriteria, String order, String search, int offset, int limit)
            throws DAOException {
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

    public long createComputer(Computer c) throws DAOException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            try {
                PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, c.getName());
                setCompanyIdOrNull(c.getCompany(), stmt, 2);
                setDateOrNull(c.getIntroduced(), stmt, 3);
                setDateOrNull(c.getDiscontinued(), stmt, 4);
                return stmt;
            } catch (DAOException e) {
                throw new SQLException(e);
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void update(Computer c) {
        logger.info("Computer {}", c);
        
        jdbcTemplate.update(UPDATE, c.getName(), c.getIntroduced(), c.getDiscontinued(),
                (c.getCompany() == null) ? null : c.getCompany().getId(), c.getId());
    }

    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    public void deleteCompany(long id) {
        jdbcTemplate.update(DELETE_BY_COMPANY, id);
    }

    public void delete(List<Long> ids) {
        for (long id : ids) {
            jdbcTemplate.update(DELETE, id);
        }
    }

    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    public int count(String name) {
        return jdbcTemplate.queryForObject(COUNT_NAME, Integer.class, "%" + name + "%", "%" + name + "%");
    }

}