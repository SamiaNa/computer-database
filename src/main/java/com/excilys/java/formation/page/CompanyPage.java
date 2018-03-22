package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;

public class CompanyPage extends Page {


    private List<Company> elements;

    public CompanyPage() {
        this.pageNumber = 0;
        this.size = DEFAULT_SIZE;
        this.elements = new ArrayList<>();
    }

    public CompanyPage(int pageNumber, int size) {
        this.pageNumber = pageNumber;
        this.size = size;
        this.elements = new ArrayList<>();
    }

    public void updateList() throws ClassNotFoundException, DAOException {
        this.elements = CompanyService.INSTANCE.getCompanyList(pageNumber, size);
    }

    @Override
    public void getPage(int pageNumber, int pageSize) throws  ClassNotFoundException, DAOException{
        this.count = CompanyService.INSTANCE.count();
        this.size = pageSize;
        super.offsetGetPage(pageNumber, count);
        updateList();
    }

    @Override
    public void nextPage() throws  ClassNotFoundException, DAOException {
        this.count = CompanyService.INSTANCE.count();
        super.offsetNextPage(count);
        updateList();
    }

    @Override
    public void prevPage() throws  ClassNotFoundException, DAOException {
        super.offsetPrevPage();
        updateList();

    }

    public List<Company> getElements(){
        return this.elements;
    }



}
