package com.excilys.java.formation.page;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;

public class CompanyPage extends Page {

    List<Company> companies;
    CompanyService companyService;

    public CompanyPage(int size) throws DAOException, ClassNotFoundException {
        this.offset = 0;
        this.size = size;
        this.companyService = CompanyService.INSTANCE;
        this.companies = companyService.getCompaniesList(offset, size);
        this.dbSize = companyService.count();

    }

    public void updateList() throws DAOException, ClassNotFoundException {
        this.companies = companyService.getCompaniesList(offset, size);
    }

    @Override
    public void nextPage() throws DAOException, ClassNotFoundException {
        this.dbSize = companyService.count();
        super.offsetNextPage(dbSize);
        updateList();
    }

    @Override
    public void prevPage() throws DAOException, ClassNotFoundException {
        super.offsetPrevPage();
        updateList();
    }

    public int getOffset() {
        return this.offset;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public void printPage() {
        for (Company c : this.companies) {
            System.out.println(c);
        }
    }

    @Override
    public void getPage(int pageNumber) throws DAOException, ClassNotFoundException {
        this.dbSize = companyService.count();
        super.offsetGetPage(pageNumber, dbSize);
        this.companies = companyService.getCompaniesList(offset, size);
    }

}
