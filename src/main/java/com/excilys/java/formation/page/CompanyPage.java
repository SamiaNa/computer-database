package com.excilys.java.formation.page;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;

public final class CompanyPage extends Page {

    private static volatile CompanyPage instance;

    private CompanyPage() {

    }

    public static synchronized CompanyPage getPage() {
        if (instance == null) {
            instance = new CompanyPage();
        }
        return instance;
    }


    public List<Company> getPage(int pageNumber, int pageSize) throws ClassNotFoundException, DAOException {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        int offset = super.getOffset(pageNumber, pageSize);
        return CompanyService.INSTANCE.getCompanyList(offset, pageSize);
    }

    @Override
    public void printPage(int pageNumber, int pageSize) throws ClassNotFoundException, DAOException {
        List<Company> companies = getPage(pageNumber, pageSize);
        for (Company c : companies) {
            System.out.println(c);
        }
    }

}
