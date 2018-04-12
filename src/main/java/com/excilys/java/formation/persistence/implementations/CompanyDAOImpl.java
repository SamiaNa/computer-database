package com.excilys.java.formation.persistence.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;
import com.excilys.java.formation.persistence.interfaces.CompanyDAO;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private static final String SELECT = "SELECT id, name FROM company;";
    private static final String SELECT_LIMIT = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
    private static final String SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?;";
    private static final String SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?;";
    private static final String COUNT = "SELECT COUNT(id) FROM company;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    @Autowired
    private ComputerDAOImpl computerDAO;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Company> getAll() throws DAOException {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement stmt = connection.prepareStatement(SELECT);) {
            logger.debug("(getAll) Query : {}", stmt);
            ResultSet res = stmt.executeQuery();
            companies = companyMapper.createCompanyListFromResultSet(res);
            return companies;
        } catch (SQLException e) {
            logger.error("Exception in getAll()", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> get(int offset, int size) throws DAOException {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement stmt = connection.prepareStatement(SELECT_LIMIT);) {
            stmt.setInt(1, size);
            stmt.setInt(2, offset);
            logger.debug("(get) Query : {}", stmt);
            ResultSet res = stmt.executeQuery();
            companies = companyMapper.createCompanyListFromResultSet(res);
            return companies;
        } catch (SQLException e) {
            logger.error("Exception in get({}, {})", offset, size, e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> getByName(String name) throws DAOException {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME);) {
            stmt.setString(1, "%" + name + "%");
            logger.debug("(getByName) Query : {}", stmt);
            try (ResultSet res = stmt.executeQuery()) {
                companies = companyMapper.createCompanyListFromResultSet(res);
                return companies;
            }
        } catch (SQLException e) {
            logger.error("Exception in getByName({})", name, e);
            throw new DAOException(e);
        }
    }

    @Override
    public boolean checkCompanyById(long id) throws DAOException {
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);) {
            stmt.setLong(1, id);
            logger.debug("(checkCompanyById) Query : {}", stmt);
            try (ResultSet res = stmt.executeQuery()) {
                return res.next();
            }
        } catch (SQLException e) {
            logger.error("Exception in checkCompanyByID({})", id, e);
            throw new DAOException(e);
        }
    }

    @Override
    public int count() throws DAOException {
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement stmt = connection.prepareStatement(COUNT);
                ResultSet res = stmt.executeQuery()) {
            logger.debug("(count) Query : {}", stmt);
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            logger.error("Exception in count()", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            computerDAO.delete(connection, id);
            stmt.setLong(1, id);
            logger.debug("(count) Query : {}", stmt);
            stmt.executeUpdate();
        } catch (SQLException | DAOException e) {
            logger.error("Exception in delete({})", id, e);
            throw new DAOException(e);
        }
    }
}