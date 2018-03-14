package com.excilys.java.formation.model.service;


public abstract class ComputerDatabaseValidator {

		/**
		 * Converts string argument to Long
		 * @param strId the id to convert
		 * @return long
		 * @throws ValidatorException if the string is not a number, not empty or not "null"
		 */
		protected  Long getLongId (String strId) throws ValidatorException {
			try {
				return Long.parseLong(strId);
			}catch (NumberFormatException e){
				if (strId.equals("") || strId.toLowerCase().equals("null")) {
					return null;
				}
				throw new ValidatorException("Only numbers are accepted as id");
			}
		}

}
