package com.excilys.java.formation.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;

public interface CompanyDAO {

	/**
	 * Creates a list of Companies object from the database
	 * @return an ArrayList of all the companies in the database
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	List<Company> getAll() throws SQLException, ClassNotFoundException;

	/**
	 * Returns a list of all the companies between lines offset and offset + size
	 * @param offset 
	 * @param size
	 * @return a list of all companies between offset and offset + size
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	List<Company> get(int offset, int size) throws SQLException, ClassNotFoundException;

	/**
	 * Returns a list of all the companies between lines offset and offset + size
	 * @param offset 
	 * @param size
	 * @return a list of all companies between offset and offset + size
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	List<Company> getByName(String name) throws SQLException, ClassNotFoundException;

	/**
	 * Check if a company with the specified id exists
	 * @param id of the company to check
	 * @return true if a company with the specified id exists
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	boolean checkCompanyById(long id) throws SQLException, ClassNotFoundException;

	/**
	 * Returns the number of companies in the database
	 * @return number of lines in table company
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	int count() throws ClassNotFoundException, SQLException;

}