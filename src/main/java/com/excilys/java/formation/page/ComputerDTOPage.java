package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerDTOPage extends ComputerPage {

    private List<ComputerDTO> dTOElements;
    private static final ComputerService computerService = ComputerService.INSTANCE;

    public ComputerDTOPage() {
        super();
        this.dTOElements = new ArrayList<>();
    }

    public ComputerDTOPage(int pageNumber, int size) {
        super(pageNumber, size);
    }

    @Override
    public void getPage() throws ValidatorException, ServiceException {
        if (StringUtils.isBlank(search)) {
            this.count = computerService.count();
            setOffsetAndPageNumber();
            if (StringUtils.isBlank(order)) {
                this.dTOElements = ComputerDTOMapper.INSTANCE.toDTOList(computerService.getComputerList(offset, size));
            } else {
                this.dTOElements = ComputerDTOMapper.INSTANCE.toDTOList(computerService.getByOrder(orderCriteria, order, offset, size));
            }
        } else {
            this.count = computerService.count(search);
            setOffsetAndPageNumber();
            if (StringUtils.isBlank(order)) {
                this.dTOElements = ComputerDTOMapper.INSTANCE.toDTOList(computerService.getByName(search, offset, size));
            } else {
                this.dTOElements = ComputerDTOMapper.INSTANCE.toDTOList(computerService.getByOrder(orderCriteria, order, search, offset, size));
            }
        }
    }

    public List<ComputerDTO> getDTOElements() {
        return this.dTOElements;
    }

}
