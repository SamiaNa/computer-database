package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerPage extends Page {

    private List<Computer> elements;
    private static final Logger logger = LoggerFactory.getLogger(ComputerPage.class);

    @Autowired
    private  ComputerService computerService;

    public ComputerPage(ComputerService computerService) {
        this.offset = 0;
        this.size = DEFAULT_SIZE;
        this.elements = new ArrayList<>();
        this.computerService = computerService;
    }

    public ComputerPage(ComputerService computerService, int pageNumber, int size) {
        this.offset = pageNumber;
        this.size = size;
        this.elements = new ArrayList<>();
        this.computerService = computerService;
    }

    public void updateList() throws ServiceException {
        logger.debug("Updating computer list, page number = {}, page size = {}", offset, size);
        this.elements = computerService.getComputerList(offset, size);
    }

    public void updateList(String name) throws ServiceException, ValidatorException {
        logger.debug("Updating computer list, page number = {}, page size = {}, search = {}", offset, size, name);
        this.elements = computerService.getByName(name, offset, size);
    }

    public void updateListOrderBy(String orderCriteria, String order) throws ValidatorException, ServiceException {
        logger.debug("Updating computer list, page number = {}, page size = {}, order by = {}", offset, size,
                orderCriteria);
        this.elements = computerService.getByOrder(orderCriteria, order, offset, size);
    }

    public void updateListOrderBy(String orderCriteria, String order, String search)
            throws ValidatorException, ServiceException {
        logger.debug("Updating computer list, page number = {}, page size = {}, order by = {}, search = {}", offset,
                size, orderCriteria, search);
        this.elements = computerService.getByOrder(orderCriteria, order, search, offset, size);
    }

    public void getPageHelper(int pageNumber, int pageSize) {
        this.size = pageSize;
        this.offset = super.offsetGetPage(pageNumber, count);
        this.number = Math.min(pageNumber, this.getNumberOfPages());
        logger.debug("Getting page {} with page size={}", this.number, this.size);
    }

    @Override
    public void getPage(int pageNumber, int pageSize) throws ValidatorException, ServiceException {
        this.count = computerService.count();
        getPageHelper(pageNumber, pageSize);
        updateList();
    }

    public void getPage(String name, int pageNumber, int pageSize) throws ValidatorException, ServiceException {
        this.count = computerService.count(name);
        getPageHelper(pageNumber, pageSize);
        updateList(name);
        logger.info("List size {}", this.elements.size());
    }

    public void getPageOrder(String orderCriteria, String order, int pageNumber, int pageSize)
            throws ValidatorException, ServiceException {
        this.count = computerService.count();
        getPageHelper(pageNumber, pageSize);
        updateListOrderBy(orderCriteria, order);
        logger.info("List size {}", this.elements.size());
    }

    public void getPageOrder(String orderCriteria, String order, String name, int pageNumber, int pageSize)
            throws ValidatorException, ServiceException {
        this.count = computerService.count(name);
        getPageHelper(pageNumber, pageSize);
        updateListOrderBy(orderCriteria, order, name);
        logger.info("getPageOrder({}, {}, {}, {}, {}) List size {}", orderCriteria, order, name, pageNumber, pageSize,
                this.elements.size());
    }

    @Override
    public void getPage(String orderCriteria, String order, String name, int pageNumber, int pageSize) throws ValidatorException, ServiceException {
        this.number = pageNumber;
        this.size = pageSize;
        if (StringUtils.isBlank(search)) {
            if (StringUtils.isBlank(order)) {
                getPage(pageNumber, pageSize);
            } else {
                getPageOrder(orderCriteria, order, pageNumber, pageSize);
            }
        } else {
            if (StringUtils.isBlank(order)) {
                getPage(name, pageNumber, pageSize);
            } else {
                getPageOrder(orderCriteria, order, name, pageNumber, pageSize);
            }
        }
    }

    @Override
    public void nextPage() throws ValidatorException, ServiceException {
        this.count = computerService.count();
        super.offsetNextPage(count);
        logger.debug("Getting page {} with page size = {}", offset, size);
        updateList();
    }

    @Override
    public void prevPage() throws ValidatorException, ServiceException {
        super.offsetPrevPage();
        logger.debug("Getting page {} with page size = {}", offset, size);
        updateList();

    }

    public List<Computer> getElements() {
        return this.elements;
    }

}
