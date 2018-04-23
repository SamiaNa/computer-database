package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.DAOException;

@Component
public class ComputerMapper {

    private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * Creates a list of computer from a computer ResultSet
     *
     * @param a
     *            Computer ResultSet
     * @return a list of computers
     * @throws DAOException
     */
    public List<Computer> createComputerListFromResultSet(ResultSet res) throws DAOException {
        List<Computer> computers = new ArrayList<>();
        try {
            while (res.next()) {
                computers.add(createComputerAfterNext(res));
            }
            return computers;
        } catch (SQLException e) {
            logger.error("Exception in createComputerListFromResultSet", e);
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Creates a computer from a ResultSet
     *
     * @param a
     *            ResultSet
     * @return an Optional of Computer
     * @throws DAOException
     */
    public Optional<Computer> createComputerFromResultSet(ResultSet res) throws DAOException {
        try {
            if (res.next()) {
                return Optional.of(createComputerAfterNext(res));
            } else {
                return Optional.ofNullable(null);
            }
        } catch (SQLException e) {
            logger.error("Exception in createComputerFromResultSet", e);
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Creates a computer from a ResultSet after a call to next
     *
     * @param a
     *            ResultSet
     * @return a Computer
     * @throws DAOException
     */
    public Computer createComputerAfterNext(ResultSet res) throws DAOException {
        try {
            Company company = new Company(res.getLong(5), res.getString(6));
            if (res.wasNull()) {
                company = null;
            }
            return new Computer.ComputerBuilder().withId(res.getLong(1)).withName(res.getString(2))
                    .withIntroduced(sqlDateToLocalDateOrNull(res.getDate(3)))
                    .withDiscontinued(sqlDateToLocalDateOrNull(res.getDate(4))).withCompany(company).build();
        } catch (SQLException e) {
            logger.error("Exception in createComputerAfterNext", e);
            throw new DAOException(e.getMessage());
        }
    }

    public LocalDate sqlDateToLocalDateOrNull(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }
}
