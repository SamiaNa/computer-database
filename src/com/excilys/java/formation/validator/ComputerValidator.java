package com.excilys.java.formation.validator;

import java.sql.Date;
import java.sql.SQLException;
import org.slf4j.*;

import com.excilys.java.formation.persistence.ComputerDAO;

public class ComputerValidator extends ComputerDatabaseValidator{

	private static ComputerValidator computerValidator;
	
	private ComputerValidator() {
		
	}
	public static ComputerValidator getValidator() {
		if (computerValidator == null) {
			computerValidator = new ComputerValidator();
		}
		return computerValidator;
	}


	public void checkName (String name) throws ValidatorException {
		if (name == "" || name.toLowerCase().equals("null") ) {
			throw new ValidatorException("Name can't be an empty string or 'null' String");
		}
	}

	public Date getDate (String strDate) throws ValidatorException {
		Date date;
		if (strDate.toLowerCase().equals("null") || strDate.equals("")) {
			 date = null;
		}else {
			try {
				date = Date.valueOf(strDate);
			}catch(IllegalArgumentException e) {
				Logger log = LoggerFactory.getLogger(getClass());
				log.debug("Invalid date input _"+strDate+"_");
				throw new ValidatorException("Date format must be YYYY-MM-DD");
			}
		}
		return date;
	}
	
	public void checkDates (Date dIntroduced, Date dDiscontinued) throws ValidatorException {
		if (dIntroduced != null && dDiscontinued != null && dIntroduced.after(dDiscontinued)){
			throw new ValidatorException ("Date of introduction must be anterior to date of discontinuation");
		}
	}
	
	public  Long checkComputerId (String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		long id = getLongPrimId(strId);
		ComputerDAO computerDAO = ComputerDAO.getDAO();
		if (!computerDAO.checkComputerById(id)) {
			throw new ValidatorException("No existing company with id "+id);
		}
		return id;
	}

}
