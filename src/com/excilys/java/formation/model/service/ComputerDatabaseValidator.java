package com.excilys.java.formation.model.service;


public abstract class ComputerDatabaseValidator {

	public long getLongPrimId (String strId) throws ValidatorException {
		try {
			return Long.parseLong(strId);
		}catch (NumberFormatException e){
			if (strId.equals("")) {
				throw new ValidatorException ("Enter a number");
			}
			throw new ValidatorException("Only numbers are accepted as id");
		}
	}
}
