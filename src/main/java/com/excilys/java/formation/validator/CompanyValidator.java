package com.excilys.java.formation.validator;

import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.CompanyDAOImpl;
import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;

public enum CompanyValidator {

    INSTANCE;

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
            if (strId.equals("") || strId.toLowerCase().equals("null")) {
                return null;
            }
            throw new ValidatorException("Only numbers are accepted as id");
        }
    }

    public Long checkCompanyIdOrNull(String strId) throws DAOException, ValidatorException, ConnectionException {
        Long id = getLongId(strId);
        if (id == null) {
            return id;
        }
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        if (!companyDAO.checkCompanyById(id)) {
            throw new ValidatorException("No existing company with id " + id);
        }
        return id;
    }
}
