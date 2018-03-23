package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

public class ComputerDTOPage extends ComputerPage {

    private List<ComputerDTO> DTOElements;
    public ComputerDTOPage() {
        super();
        this.DTOElements = new ArrayList<>();
    }

    public ComputerDTOPage(int pageNumber, int size) {
        super(pageNumber, size);
    }

    @Override
    public void updateList() throws ClassNotFoundException, DAOException {
        this.DTOElements = ComputerDTOMapper.INSTANCE
                .toDTOList(ComputerService.INSTANCE.getComputerList(pageNumber, size));
    }


    public List<ComputerDTO> getDTOElements(){
        return this.DTOElements;
    }


}
