package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerDTOPage extends ComputerPage {

    private List<ComputerDTO> DTOElements;
    private static final Logger logger = LoggerFactory.getLogger(ComputerDTOPage.class);

    public ComputerDTOPage() {
        super();
        this.DTOElements = new ArrayList<>();
    }

    public ComputerDTOPage(int pageNumber, int size) {
        super(pageNumber, size);
    }

    @Override
    public void updateList() throws ValidatorException, ServiceException {
        logger.info("Updating computer list : page number =" + offset + ", page size=" + size);
        this.DTOElements = ComputerDTOMapper.INSTANCE.toDTOList(ComputerService.INSTANCE.getComputerList(offset, size));
    }

    @Override
    public void updateList(String name) throws ValidatorException, ServiceException {
        logger.info("Updating computer list (search : {}) page number = {}", name, offset, size);
        this.DTOElements = ComputerDTOMapper.INSTANCE.toDTOList(ComputerService.INSTANCE.getByName(name, offset, size));
    }

    @Override
    public void updateListOrderBy(String orderCriteria, String order) throws ValidatorException, ServiceException {
        logger.info("Updating computer list (orderBy : {}) page number = {}", orderCriteria, offset, size);
        this.DTOElements = ComputerDTOMapper.INSTANCE
                .toDTOList(ComputerService.INSTANCE.getByOrder(orderCriteria, order, offset, size));

    }

    @Override
    public void updateListOrderBy(String orderCriteria, String order, String search)
            throws ValidatorException, ServiceException {
        logger.debug("Updating computer list, page number = {}, page size = {}, order by = {}, search = {}", offset,
                size, orderCriteria, search);
        this.DTOElements = ComputerDTOMapper.INSTANCE
                .toDTOList(ComputerService.INSTANCE.getByOrder(orderCriteria, order, search, offset, size));
    }


    public List<ComputerDTO> getDTOElements() {
        return this.DTOElements;
    }

}
