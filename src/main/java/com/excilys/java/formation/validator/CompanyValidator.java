package com.excilys.java.formation.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.persistence.interfaces.CompanyDAO;

@Component
public class CompanyValidator {

    private static final Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
    /**
     * Converts string argument to Long
     *
     * @param strId
     *            the id to convert
     * @return long
     * @throws ValidatorException
     *             if the string is not a number, not empty or not "null"
     */
    public Long getLongId(String strId) throws ValidatorException {
        try {
            return Long.parseLong(strId);
        } catch (NumberFormatException e) {
            if (strId.equals("") || strId.equalsIgnoreCase("null")) {
                return null;
            }
            logger.error("Validator error : failed to parse {} as a Long", strId);
            throw new ValidatorException("Only numbers are accepted as id");
        }
    }

    public Long checkCompanyIdOrNull(CompanyDAO companyDAO, String strId) throws ValidatorException {
        Long id = getLongId(strId);
        if (id != null) {
            checkCompanyOrNull(companyDAO, new Company(id, null));
        }
        return id;
    }

    public void checkCompanyOrNull(CompanyDAO companyDAO, Company company) throws ValidatorException {
        logger.info("Check company or null {}", company);
        if (company != null) {
            long id = company.getId();
            try {
                if (!companyDAO.checkCompanyById(id)) {
                    throw new ValidatorException(("No existing company with id " + id));
                }
            }catch(DAOException e) {
                logger.error("Exception in checkCompanyOrNull({})", id, e);
                throw new ValidatorException(e);
            }
        }
    }
}
