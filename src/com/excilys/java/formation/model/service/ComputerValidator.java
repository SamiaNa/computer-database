package com.excilys.java.formation.model.service;

import java.sql.Date;
import java.sql.SQLException;

import com.excilys.java.formation.model.persistence.CompanyDAO;
import com.excilys.java.formation.model.persistence.ComputerDAO;
import com.excilys.java.formation.model.persistence.NoComputerInResultSetException;

public class ComputerValidator {

	private static ComputerValidator computerValidator;
	
	private ComputerValidator() {
		
	}
	public static ComputerValidator getValidator() {
		if (computerValidator == null) {
			computerValidator = new ComputerValidator();
		}
		return computerValidator;
	}

	public  long getLongId (String strId) throws ValidatorException {
		try {
			return Long.parseLong(strId);
		}catch (NumberFormatException e){
			throw new ValidatorException("Only numbers are accepted as id");
		}
	}
	
	public void checkName (String name) throws ValidatorException {
		if (name == "") {
			throw new ValidatorException("Name can't be an empty string");
		}
	}
	
	public Date getDate (String strDate) {
		Date date;
		if (strDate.toLowerCase().equals("null") || strDate.equals("")) {
			 date = null;
		}else {
			date = Date.valueOf(strDate);
		}
		return date;
	}
	
	public Long checkCompanyId (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		if (strId == null || strId.toLowerCase().equals("null")) return null;
		long id = getLongId(strId);
		CompanyDAO companyDAO = CompanyDAO.getDAO();
		if (!companyDAO.checkCompanyById(id)) {
			throw new ValidatorException("No existing company with id "+id);
		}
		return id;
		}
	
	/*
	public void checkDates (Date dIntroduced, Date dDiscontinued) {
		if (dIntroduced != null && dDiscontinued != null && dIntroduced.after(dDiscontinued)) {
			throw
		}
	}*/
}
