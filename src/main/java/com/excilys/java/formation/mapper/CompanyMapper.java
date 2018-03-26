package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.implementations.NoComputerInResultSetException;

public enum CompanyMapper {

    INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Creates an ArrayList of companies from a ResultSet
     *
     * @param res
     *            a resultSet obtained after a query on table company
     * @return a ArrayList of companies
     * @throws SQLException
     */
    public List<Company> createCompanyListFromResultSet(ResultSet res) throws SQLException {
        List<Company> companies = new ArrayList<>();
        logger.info("Creating a Company ArrayList from a result set");
        while (res.next()) {
            companies.add(new Company(res.getLong(1), res.getString(2)));
        }
        return companies;
    }

    /**
     * Creates a Company objet from a ResultSet
     *
     * @param res
     *            a resultSet from a query on table company
     * @param id
     * @return a company
     * @throws SQLException
     * @throws NoComputerInResultSetException
     *             if the resultSet is empty
     */
    public Company createCompanyFromResultSet(ResultSet res) throws SQLException, NoComputerInResultSetException {
        if (res.next()) {
            logger.debug("Creating a Company from a resultSet");
            return new Company(res.getLong(1), res.getString(2));
        } else {
            logger.error("Company was not created");
            throw new NoComputerInResultSetException();
        }
    }
}
