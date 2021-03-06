package com.excilys.formation.service.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.service.validator.CompanyValidator;
import com.excilys.formation.service.validator.ComputerValidator;
import com.excilys.formation.service.validator.ValidatorException;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.persistence.dao.CompanyDAOJdbc;
import com.excilys.formation.persistence.dao.ComputerDAOJdbc;

@Component
public class ComputerValidator {

    private CompanyValidator companyValidator;

    @Autowired
    public ComputerValidator(CompanyValidator companyValidator) {
        this.companyValidator = companyValidator;
    }

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
                logger.error("Failed to parse {} as a LocalDate in getDate", strDate);
                throw new ValidatorException("Date format must be YYYY-MM-DD");
            }
        }
        return date;
    }

    public void checkDates(Computer computer) throws ValidatorException {
        LocalDate introduced = computer.getIntroduced();
        LocalDate discontinued = computer.getDiscontinued();
        if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
            logger.error("Validation error {} is after {} in checkDates", introduced, discontinued);
            throw new ValidatorException("Date of introduction must be anterior to date of discontinuation");
        }
    }

    public Long checkComputerId(String strId, ComputerDAOJdbc computerDAO) throws ValidatorException {
        try {
            long id = Long.parseLong(strId);
            computerDAO.getComputerById(id);
            return id;
        } catch (NumberFormatException e) {
            logger.error("Failed to parse {} as a Long in checkComputerId", strId);
            throw new ValidatorException("Only numbers are accepted as id");
        }
    }

    public void checkComputer(CompanyDAOJdbc companyDAO, Computer computer) throws ValidatorException {
        checkDates(computer);
        checkName(computer.getName());
        companyValidator.checkCompanyOrNull(companyDAO, computer.getCompany());
    }

}
