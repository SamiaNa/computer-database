package com.excilys.java.formation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.ComputerDAOImpl;
import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;

public enum ComputerValidator {

    INSTANCE;
    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
    public void checkName(String name) throws ValidatorException {
        if (name.trim().equals("") || name.equalsIgnoreCase("null")) {
            throw new ValidatorException("Name can't be an empty string or 'null' String");
        }
    }

    public LocalDate getDate(String strDate) throws ValidatorException {
        LocalDate date;
        if (strDate.equalsIgnoreCase("null") || strDate.equals("")) {
            date = null;
        } else {
            try {
                date = LocalDate.parse(strDate);
            } catch (DateTimeParseException e) {
                logger.error("Failed to parse "+strDate+" as a LocalDate in getDate")
;                throw new ValidatorException("Date format must be YYYY-MM-DD");
            }
        }
        return date;
    }

    public void checkDates(Computer computer) throws ValidatorException {
        LocalDate introduced = computer.getIntroduced();
        LocalDate discontinued = computer.getDiscontinued();
        if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
            logger.error("Validation error "+introduced+" is after "+discontinued+" in checkDates");
            throw new ValidatorException("Date of introduction must be anterior to date of discontinuation");
        }
    }

    public Long checkComputerId(String strId) throws DAOException, ValidatorException, ConnectionException {
        try {
            long id = Long.parseLong(strId);
            ComputerDAO computerDAO = ComputerDAOImpl.INSTANCE;
            computerDAO.getComputerById(id);
            return id;
        } catch (NumberFormatException e) {
            logger.error("Failed to parse "+strId+" as a Long in checkComputerId");
            throw new ValidatorException("Only numbers are accepted as id");
        }
    }
    
   

}
