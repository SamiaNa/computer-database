package com.excilys.java.formation.persistence;

import java.util.List;

import com.excilys.java.formation.entities.Company;

public interface CompanyDAO {

    /**
     * Creates a list of Companies object from the database
     *
     * @return an ArrayList of all the companies in the database
     * @throws DAOException
     * @throws ClassNotFoundException
     */
    List<Company> getAll() throws DAOException, ClassNotFoundException;

    /**
     * Returns a list of all the companies between lines offset and offset + size
     *
     * @param offset
     * @param size
     * @return a list of all companies between offset and offset + size
     * @throws DAOException
     * @throws ClassNotFoundException
     */
    List<Company> get(int offset, int size) throws DAOException, ClassNotFoundException;

    /**
     * Returns a list of all the companies between lines offset and offset + size
     *
     * @param offset
     * @param size
     * @return a list of all companies between offset and offset + size
     * @throws DAOException
     * @throws ClassNotFoundException
     */
    List<Company> getByName(String name) throws DAOException, ClassNotFoundException;

    /**
     * Check if a company with the specified id exists
     *
     * @param id
     *            of the company to check
     * @return true if a company with the specified id exists
     * @throws DAOException
     * @throws ClassNotFoundException
     */
    boolean checkCompanyById(long id) throws DAOException, ClassNotFoundException;

    /**
     * Returns the number of companies in the database
     *
     * @return number of lines in table company
     * @throws ClassNotFoundException
     * @throws DAOException
     */
    int count() throws ClassNotFoundException, DAOException;

}