package com.excilys.java.formation.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;

public interface ComputerDAO {

	/**
	 * Returns the list of all computers in the database
	 * @return ArrayList of Computer
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	List<Computer> getAll() throws ClassNotFoundException, SQLException;

	/**
	 * Returns a list of all computers between lines offset and offset + size
	 * @param offset from which to start selecting lines
	 * @param size the maximum number of lines to select
	 * @return an ArrayList of computers
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	List<Computer> get(int offset, int size) throws ClassNotFoundException, SQLException;

	/**
	 * Returns the computer with the specified id in the database
	 * @param id the primary key of the computer to find
	 * @return a Computer object
	 * @throws SQLException
	 * @throws NoComputerInResultSetException if there is no computer with the corresponding id
	 * @throws ClassNotFoundException
	 */

	Computer getComputerById(long id) throws SQLException, NoComputerInResultSetException, ClassNotFoundException;

	Long createComputer(Computer c) throws SQLException, ClassNotFoundException;

	boolean update(Computer c) throws SQLException, ClassNotFoundException;

	boolean delete(long id) throws SQLException, ClassNotFoundException;

	int count() throws SQLException, ClassNotFoundException;

}