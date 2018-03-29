package com.excilys.java.formation.persistence.interfaces;

import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.DAOException;

public interface ComputerDAO {

    /**
     * Returns the list of all computers in the database
     *
     * @return ArrayList of Computer
     * @throws DAOException
     */
    List<Computer> getAll() throws DAOException;

    /**
     * Returns a list of all computers between lines offset and offset + size
     *
     * @param offset
     *            from which to start selecting lines
     * @param size
     *            the maximum number of lines to select
     * @return an ArrayList of computers
     * @throws DAOException
     */
    List<Computer> get(int offset, int size) throws  DAOException;

    /**
     * Returns the computer with the specified id in the database
     *
     * @param id
     *            the primary key of the computer to find
     * @return a Computer object
     * @throws DAOException
     */

    Optional<Computer> getComputerById(long id) throws DAOException;

    Optional<Long> createComputer(Computer c) throws DAOException;

    void update(Computer c) throws DAOException;

    void delete(long id) throws DAOException;

    int count() throws DAOException;

    int count(String name) throws DAOException;

    List <Computer> getByName(String s, int offset, int limit) throws DAOException;


    List<Computer> getByOrder(String orderCriteria, String order, int offset, int limit ) throws DAOException;

    List<Computer> getByOrder(String orderCriteria, String order, String search, int offset, int limit)
            throws DAOException;

}