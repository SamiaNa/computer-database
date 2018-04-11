package com.excilys.java.formation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.implementations.CompanyDAOImpl;
import com.excilys.java.formation.persistence.implementations.DAOException;

@Service
public class CompanyService {

    private static Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyDAOImpl companyDAO;

    public List<Company> getCompanyList() throws ServiceException {
        try {
            return companyDAO.getAll();
        } catch (DAOException e) {
            logger.error("Exception in getCompanyList", e);
            throw new ServiceException(e);
        }
    }

    public List<Company> getCompanyList(int offset, int size) throws ServiceException {
        try {
            return companyDAO.get(offset, size);
        } catch (DAOException e) {
            logger.error("Exception in getCompanyList ({}, {})", offset, size, e);
            throw new ServiceException(e);
        }
    }

    public int count() throws ServiceException {
        try {
            return companyDAO.count();
        } catch (DAOException e) {
            logger.error("Exception in count", e);
            throw new ServiceException(e);
        }
    }

    public List<Company> getCompaniesByName(String name) throws ServiceException {
        try {
            return companyDAO.getByName(name);
        } catch (DAOException e) {
            logger.error("Exception in getCompaniesByName({})", name, e);
            throw new ServiceException(e);
        }
    }

    public void delete (long id) throws ServiceException {
        try {
            companyDAO.delete(id);
        }catch (DAOException e) {
            logger.error("Exception in delete ({})", id, e);
            throw new ServiceException(e);
        }
    }
}