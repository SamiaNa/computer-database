package com.excilys.java.formation.service;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.implementations.CompanyDAOImpl;
import com.excilys.java.formation.persistence.implementations.ConnectionException;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.persistence.interfaces.CompanyDAO;

public enum CompanyService {

    INSTANCE;

    public List<Company> getCompanyList() throws DAOException, ConnectionException {
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        return companyDAO.getAll();
    }

    public List<Company> getCompanyList(int offset, int size) throws ConnectionException, DAOException {
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        return companyDAO.get(offset, size);
    }

    public int count() throws ConnectionException, DAOException {
        return CompanyDAOImpl.INSTANCE.count();
    }

    public List<Company> getCompaniesByName(String name) throws ConnectionException, DAOException {
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        return companyDAO.getByName(name);

    }
}
