package com.excilys.java.formation.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.persistence.implementations.CompanyDAOImpl;
import com.excilys.java.formation.persistence.implementations.DAOException;

public enum CompanyValidator {

    INSTANCE;
    private final static Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
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
            logger.error("Validator error : failed to parse "+strId+" as a Long");
            throw new ValidatorException("Only numbers are accepted as id");
        }
    }

    public Long checkCompanyIdOrNull(String strId) throws ValidatorException {
        Long id = getLongId(strId);
        if (id == null) {
            return id;
        }
        try {
            if (!CompanyDAOImpl.INSTANCE.checkCompanyById(id)) {
                throw new ValidatorException("No existing company with id " + id);
            }
        } catch (DAOException e) {
            logger.error("Exception in checkCompanyIdOrNull({})", strId, e);
            throw new ValidatorException(e);
        }
        return id;
    }
}
