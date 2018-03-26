package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ServiceException;

public class CompanyPage extends Page {


    private List<Company> elements;
    private static Logger logger = LoggerFactory.getLogger(CompanyPage.class);

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

    public void updateList() throws ServiceException  {
        logger.debug("Updating company list, page number = "+pageNumber+", page size="+size);
        this.elements = CompanyService.INSTANCE.getCompanyList(pageNumber, size);
    }

    @Override
    public void getPage(int pageNumber, int pageSize) throws ServiceException {
        this.count = CompanyService.INSTANCE.count();
        this.size = pageSize;
        super.offsetGetPage(pageNumber, count);
        logger.debug("Getting page "+pageNumber+" with page size="+size);
        updateList();
    }

    @Override
    public void nextPage() throws ServiceException  {
        this.count = CompanyService.INSTANCE.count();
        super.offsetNextPage(count);
        logger.debug("Getting page "+pageNumber+" with page size="+size);
        updateList();
    }

    @Override
    public void prevPage() throws ServiceException  {
        super.offsetPrevPage();
        logger.debug("Getting page "+pageNumber+" with page size="+size);
        updateList();

    }

    public List<Company> getElements(){
        return this.elements;
    }



}
