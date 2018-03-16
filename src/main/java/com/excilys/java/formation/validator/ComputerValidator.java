package com.excilys.java.formation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.SQLException;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.ComputerDAOImpl;
import com.excilys.java.formation.persistence.NoComputerInResultSetException;

public enum ComputerValidator{

	INSTANCE;

	public void checkName (String name) throws ValidatorException {
		if (name == "" || name.toLowerCase().equals("null") ) {
			throw new ValidatorException("Name can't be an empty string or 'null' String");
		}
	}

	public LocalDate getDate (String strDate) throws ValidatorException {
		LocalDate date;
		if (strDate.toLowerCase().equals("null") || strDate.equals("")) {
			 date = null;
		}else {
			try {
				date = LocalDate.parse(strDate);
			}catch(DateTimeParseException e) {
				throw new ValidatorException("Date format must be YYYY-MM-DD");
			}
		}
		return date;
	}
	
	public void checkDates (Computer computer) throws ValidatorException {
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)){
			throw new ValidatorException ("Date of introduction must be anterior to date of discontinuation");
		}
	}
	
	public void checkDates (LocalDate introduced, LocalDate discontinued) throws ValidatorException {
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)){
			throw new ValidatorException ("Date of introduction must be anterior to date of discontinuation");
		}
	}
	
	public Long checkComputerId (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		long id = Validator.getLongPrimId(strId);
		ComputerDAO computerDAO = ComputerDAOImpl.INSTANCE;
		try {
			computerDAO.getComputerById(id);
			return id;
		}catch(NoComputerInResultSetException e){
			throw new ValidatorException("No existing company with id "+id);
		}
	}

}
