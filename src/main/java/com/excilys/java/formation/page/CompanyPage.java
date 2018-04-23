package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public class CompanyPage extends Page {

    private List<Company> elements;

    private static final Logger logger = LoggerFactory.getLogger(CompanyPage.class);

    private CompanyService companyService;


    @Autowired
    public CompanyPage(CompanyService companyService) {
        this.offset = 0;
        this.size = DEFAULT_SIZE;
        this.elements = new ArrayList<>();
        this.companyService = companyService;
    }

    public CompanyPage(CompanyService companyService, int pageNumber, int size) {
        this.offset = pageNumber;
        this.size = size;
        this.elements = new ArrayList<>();
        this.companyService = companyService;
    }

    public void updateList() throws ServiceException  {
        logger.debug("Updating company list, page number = {}, page size = {}", offset, size);
        this.elements = companyService.getCompanyList(offset, size);
    }

    @Override
    public void getPage(int pageNumber, int pageSize) throws ServiceException {
        this.count = companyService.count();
        this.size = pageSize;
        super.offsetGetPage(pageNumber, count);
        logger.debug("Getting page {} with page size= {}", pageNumber, size);
        updateList();
    }

    @Override
    public void nextPage() throws ServiceException  {
        this.count = companyService.count();
        super.offsetNextPage(count);
        logger.debug("Getting page {} with page size={}", offset, size);
        updateList();
    }

    @Override
    public void prevPage() throws ServiceException  {
        super.offsetPrevPage();
        logger.debug("Getting page {} with page size={}", offset, size);
        updateList();

    }

    public List<Company> getElements(){
        return this.elements;
    }


    @Override
    public void getPage(String orderCriteria, String order, String search, int pageNumber, int pageSize) throws ValidatorException, ServiceException {

    }


}