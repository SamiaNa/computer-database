package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.mapper.MapperException;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerDTOPage extends ComputerPage {

    private List<ComputerDTO> DTOElements;
    private Logger logger = LoggerFactory.getLogger(ComputerDTOPage.class);

    public ComputerDTOPage() {
        super();
        this.DTOElements = new ArrayList<>();
    }

    public ComputerDTOPage(int pageNumber, int size) {
        super(pageNumber, size);
    }

    @Override
    public void updateList() throws ValidatorException, ServiceException {
        logger.info("Updating computer list : page number ="+pageNumber+", page size="+size);
        try {
            this.DTOElements = ComputerDTOMapper.INSTANCE
                    .toDTOList(ComputerService.INSTANCE.getComputerList(pageNumber, size));
        }catch(MapperException e) {
            logger.error("Exception in updateList()", e);
            throw new ServiceException(e);
        }
    }


    public List<ComputerDTO> getDTOElements(){
        return this.DTOElements;
    }


}
