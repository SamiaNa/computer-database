package com.excilys.java.formation.persistence.interfaces;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.implementations.DAOException;

public interface CompanyDAO {

    /**
     * Creates a list of Companies object from the database
     *
     * @return an ArrayList of all the companies in the database
     * @throws DAOException
     */
    List<Company> getAll() throws DAOException;

    /**
     * Returns a list of all the companies between lines offset and offset + size
     *
     * @param offset
     * @param size
     * @return a list of all companies between offset and offset + size
     * @throws DAOException
     */
    List<Company> get(int offset, int size) throws DAOException;

    /**
     * Returns a list of all the companies between lines offset and offset + size
     *
     * @param offset
     * @param size
     * @return a list of all companies between offset and offset + size
     * @throws DAOException
     */
    List<Company> getByName(String name) throws DAOException;

    /**
     * Check if a company with the specified id exists
     *
     * @param id of the company to check
     * @return true if a company with the specified id exists
     * @throws DAOException
     */
    boolean checkCompanyById(long id) throws DAOException;

    /**
     * Returns the number of companies in the database
     *
     * @return number of lines in table company
     * @throws DAOException
     */
    int count() throws DAOException;

    void delete (long id) throws DAOException;

}