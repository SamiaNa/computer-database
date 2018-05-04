package com.excilys.formation.binding.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.formation.binding.exceptions.MapperException;
import com.excilys.formation.core.entities.Company;

@Component
public class CompanyMapper {

    private static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Creates an ArrayList of companies from a ResultSet
     *
     * @param a
     *            ResultSet of companies
     * @return an list of companies
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
     * Creates a Company object from a ResultSet
     *
     * @param a
     *            ResultSet of companies
     * @return a Company
     * @throws SQLException
     * @throws NoComputerInResultSetException
     *             if the resultSet is empty
     */
    public Company createCompanyFromResultSet(ResultSet res) throws MapperException {
    	try {
        if (res.next()) {
            logger.debug("Creating a Company from a resultSet");
            return new Company(res.getLong(1), res.getString(2));
        } else {
            logger.error("Company was not created");
            throw new MapperException();
        }}catch(SQLException e) {
        	throw new MapperException(e);
        }
    }
}