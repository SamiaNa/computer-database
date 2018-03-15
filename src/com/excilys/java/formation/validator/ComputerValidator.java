package com.excilys.java.formation.validator;

import java.time.LocalDate;
import java.sql.SQLException;
import org.slf4j.*;

import com.excilys.java.formation.persistence.ComputerDAO;

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
			}catch(IllegalArgumentException e) {
				Logger log = LoggerFactory.getLogger(getClass());
				log.debug("Invalid date input _"+strDate+"_");
				throw new ValidatorException("Date format must be YYYY-MM-DD");
			}
		}
		return date;
	}
	
	public void checkDates (LocalDate dIntroduced, LocalDate dDiscontinued) throws ValidatorException {
		if (dIntroduced != null && dDiscontinued != null && dIntroduced.isAfter(dDiscontinued)){
			throw new ValidatorException ("Date of introduction must be anterior to date of discontinuation");
		}
	}
	
	public  Long checkComputerId (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		long id = Validator.getLongPrimId(strId);
		ComputerDAO computerDAO = ComputerDAO.INSTANCE;
		if (!computerDAO.checkComputerById(id)) {
			throw new ValidatorException("No existing company with id "+id);
		}
		return id;
	}

}
