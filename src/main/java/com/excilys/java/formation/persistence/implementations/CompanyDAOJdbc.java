package com.excilys.java.formation.persistence.implementations;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyRowMapper;

@Repository
public class CompanyDAOJdbc{

    private static final String SELECT = "SELECT id, name FROM company;";
    private static final String SELECT_LIMIT = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
    private static final String SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?;";
    private static final String SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?;";
    private static final String COUNT = "SELECT COUNT(id) FROM company;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CompanyRowMapper companyRowMapper;

    @Autowired
    private ComputerDAOJdbc computerDAO;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Company> getAll() {
        return jdbcTemplate.query(SELECT, companyRowMapper);
    }

    public List<Company> get(int offset, int size)  {
        return jdbcTemplate.query(SELECT_LIMIT, companyRowMapper, size, offset);
    }


    public List<Company> getByName(String name)  {
        return jdbcTemplate.query(SELECT_BY_NAME, companyRowMapper, "%"+name+"%");
    }


    public boolean checkCompanyById(long id) throws DAOException {
        List<Company> companies =  jdbcTemplate.query(SELECT_BY_ID,  companyRowMapper, id);
        if (companies.size() == 1) {
            return true;
        }
        if (companies.isEmpty()) {
            return false;
        }
        throw new DAOException("Expected number of rows : 0 or 1, actual number of rows "+companies.size());
    }

    public int count()  {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }


    public void delete(long id) throws DAOException {
        computerDAO.deleteCompany(id);
        jdbcTemplate.update(DELETE, id);
    }
}