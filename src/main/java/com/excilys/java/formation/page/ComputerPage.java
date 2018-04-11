package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerPage extends Page {

    private List<Computer> elements;
    private static final Logger logger = LoggerFactory.getLogger(ComputerPage.class);
    private static final ComputerService computerService = ComputerService.INSTANCE;

    public ComputerPage() {
        this.offset = 0;
        this.size = DEFAULT_SIZE;
        this.elements = new ArrayList<>();
    }

    public ComputerPage(int pageNumber, int size) {
        this.offset = pageNumber;
        this.size = size;
        this.elements = new ArrayList<>();
    }

    public void updateList() throws ServiceException {
        logger.debug("Updating computer list, page number = {}, page size = {}", offset, size);
        this.elements = computerService.getComputerList(offset, size);
    }

    public void setOffsetAndPageNumber() {
        logger.debug("setOffsetAndPageNumber : number = {}, size = {}, count =");
        this.offset = super.offsetGetPage(number, count);
        this.number = Math.min(number, this.getNumberOfPages());
        logger.debug("Getting page {} with page size={}", this.number, this.size);
    }


    @Override
    public void getPage() throws ValidatorException, ServiceException {
        if (StringUtils.isBlank(search)) {
            this.count = computerService.count();
            setOffsetAndPageNumber();
            if (StringUtils.isBlank(order)) {
                this.elements = computerService.getComputerList(offset, size);
            } else {
                this.elements = computerService.getByOrder(orderCriteria, order, offset, size);
            }
        } else {
            this.count = computerService.count(search);
            setOffsetAndPageNumber();
            if (StringUtils.isBlank(order)) {
                this.elements = computerService.getByName(search, offset, size);
            } else {
                this.elements = computerService.getByOrder(orderCriteria, order, search, offset, size);
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
