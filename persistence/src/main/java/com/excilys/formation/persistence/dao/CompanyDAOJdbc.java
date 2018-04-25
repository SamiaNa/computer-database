package com.excilys.formation.persistence.dao;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.excilys.formation.binding.mappers.CompanyRowMapper;
import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.QCompany;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAOJdbc {

    private static final String SELECT = "SELECT id, name FROM company;";
    private static final String SELECT_LIMIT = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
    private static final String SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?;";
    private static final String SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?;";
    private static final String COUNT = "SELECT COUNT(id) FROM company;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";

    private JdbcTemplate jdbcTemplate;
    private CompanyRowMapper companyRowMapper;
    private ComputerDAOJdbc computerDAO;
    private LocalSessionFactoryBean sessionFactory;
    

    @Autowired
    public CompanyDAOJdbc(CompanyRowMapper companyRowMapper, ComputerDAOJdbc computerDAO, LocalSessionFactoryBean sessionFactory) {
        this.companyRowMapper = companyRowMapper;
        this.computerDAO = computerDAO;
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Company> getAll() {
    	Session session = sessionFactory.getObject().openSession();
    	HibernateQuery<Company> query = new HibernateQuery<>(session);
    	QCompany qcompany = QCompany.company;
    	return query.from(qcompany).fetch();
    	//close
    }

    public List<Company> get(int offset, int size) {
        return jdbcTemplate.query(SELECT_LIMIT, companyRowMapper, size, offset);
    }

    public List<Company> getByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME, companyRowMapper, "%" + name + "%");
    }

    public boolean checkCompanyById(long id) throws DAOException {
        List<Company> companies = jdbcTemplate.query(SELECT_BY_ID, companyRowMapper, id);
        if (companies.size() == 1) {
            return true;
        }
        if (companies.isEmpty()) {
            return false;
        }
        throw new DAOException("Expected number of rows : 0 or 1, actual number of rows " + companies.size());
    }

    public int count() {
    	Session session = sessionFactory.getObject().openSession();
    	HibernateQuery<Company> query = new HibernateQuery<>(session);
    	QCompany qcompany = QCompany.company;
    	return (int) query.from(qcompany).fetchCount();
    }

    public void delete(long id) {
        computerDAO.deleteCompany(id);
        jdbcTemplate.update(DELETE, id);
    }
}