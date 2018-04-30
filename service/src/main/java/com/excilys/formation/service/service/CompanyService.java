package com.excilys.formation.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.core.entities.Company;
import com.excilys.formation.persistence.dao.CompanyDAOJdbc;

@Service
@EnableTransactionManagement
public class CompanyService {

    private CompanyDAOJdbc companyDAO;

    @Autowired
    public CompanyService(CompanyDAOJdbc companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<Company> getCompanyList() {
        return companyDAO.getAll();
    }

    public List<Company> getCompanyList(long offset, long size) {
        return companyDAO.get(offset, size);
    }

    public long count() {
        return companyDAO.count();
    }

    public List<Company> getCompaniesByName(String name) {
        return companyDAO.getByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) throws ServiceException {
            companyDAO.delete(id);

    }
}