package com.excilys.java.formation.service;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.CompanyDAOImpl;
import com.excilys.java.formation.persistence.DAOException;

public enum CompanyService {

    INSTANCE;

    public List<Company> getCompanyList() throws DAOException, ClassNotFoundException {
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        return companyDAO.getAll();
    }

    public List<Company> getCompanyList(int offset, int size) throws ClassNotFoundException, DAOException {
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        return companyDAO.get(offset, size);
    }

    public int count() throws ClassNotFoundException, DAOException {
        return CompanyDAOImpl.INSTANCE.count();
    }

    public List<Company> getCompaniesByName(String name) throws ClassNotFoundException, DAOException {
        CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
        return companyDAO.getByName(name);

    }
}
