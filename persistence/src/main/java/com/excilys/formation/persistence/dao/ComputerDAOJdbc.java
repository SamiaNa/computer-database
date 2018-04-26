package com.excilys.formation.persistence.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.binding.mappers.ComputerRowMapper;
import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.core.entities.QComputer;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;


@Repository
public class ComputerDAOJdbc {

    private static final String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";
    private static final String SELECT_ORDER = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String SELECT_BY_ID_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ;";
    private static final String SELECT_BY_NAME = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE (computer.name LIKE ? OR company.name LIKE ?) LIMIT ? OFFSET ?;";

    private static final String COMPUTER_ID = "idcomputer";
    private static final String COMPUTER_NAME = "namecomputer";
    private static final String COMPUTER_INTRO = "introcomputer";
    private static final String COMPUTER_DISC = "disccomputer";
    private static final String COMPUTER_COMPANY = "namecompany";

    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";
    
    private static final QComputer qComputer = QComputer.computer;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private ComputerRowMapper computerRowMapper;


    private HibernateQueryFactory queryFactory;
    private SessionFactory sessionFactory;
    
    @Autowired
    public ComputerDAOJdbc( SessionFactory sessionFactory, ComputerRowMapper computerRowMapper) {
        this.queryFactory = new HibernateQueryFactory(sessionFactory.openSession());
        this.computerRowMapper = computerRowMapper;
        this.sessionFactory = sessionFactory;
    }
    


    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ComputerDAOJdbc() {
        super();
    }

    public List<Computer> getAll() {
    	return (List<Computer>) queryFactory.from(qComputer).fetch();
    }

    public List<Computer> get(int offset, int size) {
    	return (List<Computer>) queryFactory.from(qComputer).offset(offset).limit(size).fetch();
    }


    public Optional<Computer> getComputerById(long id) {
    	List<Computer> computer = (List<Computer>) queryFactory.from(qComputer).where(qComputer.id.eq(id)).fetch();
        try {
            return Optional.of(computer.get(0));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public List<Computer> getByName(String name, int offset, int limit) {
    	return (List<Computer>) queryFactory.from(qComputer).where(qComputer.name.like("%"+name+"%")).limit(limit).offset(offset).fetch();
    }

    public List<Computer> getByOrder(String orderCriteria, String order, int offset, int limit)
            throws DAOException {
        checkOrder(order);
        return (List<Computer>) queryFactory.from(qComputer).orderBy(getColumnName(orderCriteria)).limit(limit).offset(offset)
        return jdbcTemplate.query(
                SELECT_ORDER + " ORDER BY " + getColumnName(orderCriteria) + " " + order + " LIMIT ? OFFSET ?;",
                new ComputerRowMapper(), limit, offset);
    }

    private Path<?> getColumnName(String orderCriteria) throws DAOException {
    
        switch (orderCriteria) {
        case COMPUTER_ID:
            return qComputer.id;
        case COMPUTER_NAME:
            return qComputer.name;
        case COMPUTER_INTRO:
            return qComputer.introduced;
        case COMPUTER_DISC:
            return qComputer.discontinued;
        case COMPUTER_COMPANY:
            return qComputer.company;
        default:
            String message = "Unknown order criteria : " + orderCriteria;
            logger.error(message);
            throw new DAOException(message);
        }
    }
    
    private long getOrder(String order, Path<?> path) {
    	switch(order) {
    	case ASCENDING:
    		return path.asc();
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



    public long createComputer(Computer c) throws DAOException {
        sessionFactory.openSession().save(c);
        return c.getId();
    }

    public void update(Computer c) {
    	QComputer computer = QComputer.computer;
    	queryFactory.update(computer).where(computer.id.eq(c.getId()))
    	.set(computer.name, c.getName())
    	.set(computer.introduced, c.getIntroduced())
    	.set(computer.discontinued, c.getDiscontinued())
    	.set(computer.company, c.getCompany())
    	.execute();
    }

    public void delete(long id) {
    	queryFactory.delete(qComputer).where(qComputer.id.eq(id))
    	.execute();
    }

    public void deleteCompany(long id) {
    	queryFactory.delete(qComputer).where(qComputer.company.id.eq(id));
    }

    public void delete(List<Long> ids) {
        for (long id : ids) {
        	queryFactory.delete(qComputer).where(qComputer.id.eq(id));
        }
    }

    public int count() {
    	return (int) queryFactory.from(qComputer).fetchCount();
    }

    public int count(String name) {
    	return (int) queryFactory.from(qComputer).where(qComputer.name.like("%"+name+"%")).fetchCount();
    }

}