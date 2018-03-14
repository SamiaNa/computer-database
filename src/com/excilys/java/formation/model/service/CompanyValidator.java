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
	 * Converts string argument to long and tests if a company with the corresponding id exists
	 * @param strId the id of the company to check
	 * @return the id (Long)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException
	 */
	public Long checkCompanyId (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		if (strId == null || strId.toLowerCase().equals("null")) return null;
		long id = getLongId(strId);
		CompanyDAO companyDAO = CompanyDAO.getDAO();
		if (!companyDAO.checkCompanyById(id)) {
			throw new ValidatorException("No existing company with id "+id);
		}
		return id;
	}
}
