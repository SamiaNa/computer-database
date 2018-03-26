package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOException;

public enum ComputerMapper {

    INSTANCE;
    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    public LocalDate toLocalDateOrNull(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

    public List<Computer> createComputerListFromResultSet(ResultSet res) throws  DAOException {
        List<Computer> computers = new ArrayList<>();
        try {
            while (res.next()) {
                computers.add(createComputerAfterNext(res));
            }
            return computers;
        }catch(SQLException e) {
            logger.error("Exception in createComputerListFromResultSet", e);
            throw new DAOException(e.getMessage());
        }
    }

    public Computer createComputerAfterNext(ResultSet res) throws DAOException {
        Computer c;
        try {
            c = new Computer(res.getLong(1), res.getString(2), toLocalDateOrNull(res.getDate(3)),
                    toLocalDateOrNull(res.getDate(4)), null);
            Company company = new Company(res.getLong(5), res.getString(6));
            if (res.wasNull())
                company = null;
            c.setCompany(company);
            return c;
        } catch (SQLException e) {
            logger.error("Exception in createComputerAfterNext", e);
            throw new DAOException(e.getMessage());
        }
    }
    public Optional<Computer> createComputerFromResultSet(ResultSet res, long id) throws DAOException {
        try {
            if (res.next()) {
                return Optional.of(createComputerAfterNext(res));
            } else {
                return Optional.ofNullable(null);
            }
        }catch (SQLException e) {
            logger.error("Exception in createComputerFromResultSet", e);
            throw new DAOException(e.getMessage());
        }
    }

}
