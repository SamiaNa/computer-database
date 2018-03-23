package com.excilys.java.formation.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;

public interface ComputerDAO {

    /**
     * Returns the list of all computers in the database
     *
     * @return ArrayList of Computer
     * @throws SQLException
     * @throws
     * @throws ConnectionException
     */
    List<Computer> getAll() throws DAOException, ConnectionException;

    /**
     * Returns a list of all computers between lines offset and offset + size
     *
     * @param offset
     *            from which to start selecting lines
     * @param size
     *            the maximum number of lines to select
     * @return an ArrayList of computers
     * @throws
     * @throws ConnectionException
     * @throws SQLException
     */
    List<Computer> get(int offset, int size) throws  DAOException, ConnectionException;

    /**
     * Returns the computer with the specified id in the database
     *
     * @param id
     *            the primary key of the computer to find
     * @return a Computer object
     * @throws SQLException
     * @throws NoComputerInResultSetException
     *             if there is no computer with the corresponding id
     * @throws
     * @throws ConnectionException
     */

    Optional<Computer> getComputerById(long id) throws DAOException,  ConnectionException;

    Long createComputer(Computer c) throws DAOException,  DAOConstraintException, ConnectionException;

    boolean update(Computer c) throws DAOException,  ConnectionException;

    boolean delete(long id) throws DAOException,  ConnectionException;

    int count() throws DAOException,  ConnectionException;

}