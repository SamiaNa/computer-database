package com.excilys.java.formation.model.service;

import java.sql.SQLException;

import com.excilys.java.formation.model.persistence.CompanyDAO;

public class CompanyValidator extends ComputerDatabaseValidator{
	
	private static CompanyValidator companyValidator;
	
	private CompanyValidator() {	
	}
	
	public static CompanyValidator getValidator() {
		if (companyValidator == null) {
			companyValidator = new CompanyValidator();
		}
		return companyValidator;
	}
	
	
	/**
	 * Converts string argument to Long
	 * @param strId the id to convert
	 * @return long
	 * @throws ValidatorException if the string is not a number, not empty or not "null"
	 */
	public  Long getLongId (String strId) throws ValidatorException {
		try {
			return Long.parseLong(strId);
		}catch (NumberFormatException e){
			if (strId.equals("") || strId.toLowerCase().equals("null")) {
				return null;
			}
			throw new ValidatorException("Only numbers are accepted as id");
		}
	}

	/**
	 * Converts string argument to long and tests if a company with the corresponding id exists
	 * @param strId the id of the company to check
	 * @return the id (Long)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException
	 */
	public Long checkCompanyId (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		long id = getLongPrimId(strId);
		CompanyDAO companyDAO = CompanyDAO.getDAO();
		if (!companyDAO.checkCompanyById(id)) {
			throw new ValidatorException("No existing company with id "+id);
		}
		return id;
	}
	

	public Long checkCompanyIdOrNull (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		Long id = getLongId(strId);
		if (id == null)
			return id;
		CompanyDAO companyDAO = CompanyDAO.getDAO();
		if (!companyDAO.checkCompanyById(id)) {
			throw new ValidatorException("No existing company with id "+id);
		}
		return id;
	}
}
