package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

@Component
public class ComputerDTOPage extends ComputerPage {

    private List<ComputerDTO> dTOElements;

    private static final Logger logger = LoggerFactory.getLogger(ComputerDTOPage.class);

    private ComputerDTOMapper computerDTOMapper;

    private ComputerService computerService;

    @Autowired
    public ComputerDTOPage(ComputerService computerService, ComputerDTOMapper computerDTOMapper) {
        super(computerService);
        this.dTOElements = new ArrayList<>();
        this.computerService  = computerService;
        this.computerDTOMapper = computerDTOMapper;
    }

    public ComputerDTOPage(ComputerService computerService, int pageNumber, int size) {
        super(computerService, pageNumber, size);
    }

    @Override
    public void updateList() throws  ServiceException {
        logger.info("Updating computer list : page number = {}, page size={}", offset ,size);
        this.dTOElements = computerDTOMapper.toDTOList(computerService.getComputerList(offset, size));
    }

    @Override
    public void updateList(String name) throws ServiceException, ValidatorException {
        logger.info("Updating computer list (search : {}) page number = {}, page size={}", name, offset, size);
        this.dTOElements = computerDTOMapper.toDTOList(computerService.getByName(name, offset, size));
    }

    @Override
    public void updateListOrderBy(String orderCriteria, String order) throws ValidatorException, ServiceException {
        logger.info("Updating computer list (orderBy : {}) page number = {}, order={},  page size={}", orderCriteria, order, offset, size);
        this.dTOElements = computerDTOMapper
                .toDTOList(computerService.getByOrder(orderCriteria, order, offset, size));

    }

    @Override
    public void updateListOrderBy(String orderCriteria, String order, String search)
            throws ValidatorException, ServiceException {
        logger.debug("Updating computer list, page number = {}, page size = {}, order by = {}, search = {}", offset,
                size, orderCriteria, search);
        this.dTOElements = computerDTOMapper
                .toDTOList(computerService.getByOrder(orderCriteria, order, search, offset, size));
    }


    public List<ComputerDTO> getDTOElements() {
        return this.dTOElements;
    }

}
