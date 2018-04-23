package com.excilys.java.formation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.implementations.CompanyDAOJdbc;
import com.excilys.java.formation.persistence.implementations.DAOException;

@Service
@EnableTransactionManagement
public class CompanyService {

    private static Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private CompanyDAOJdbc companyDAO;

    @Autowired
    public CompanyService(CompanyDAOJdbc companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<Company> getCompanyList() {
        return companyDAO.getAll();
    }

    public List<Company> getCompanyList(int offset, int size) {
        return companyDAO.get(offset, size);
    }

    public int count() {
        return companyDAO.count();
    }

    public List<Company> getCompaniesByName(String name) {
        return companyDAO.getByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) throws ServiceException {
        try {
            companyDAO.delete(id);
        } catch (DAOException e) {
            logger.error("Exception in delete ({})", id, e);
            throw new ServiceException(e);
        }
    }
}