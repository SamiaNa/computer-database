package com.excilys.java.formation.model.service;

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
}
