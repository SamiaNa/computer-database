package com.excilys.java.formation.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

public enum ComputerDTOMapper {

    INSTANCE;
    private static final String NULL = "";

    public String dateToString(LocalDate date) {
        if (date == null) {
            return NULL;
        }
        return date.toString();
    }

    public LocalDate stringToLocalDate(String str) {
        if (str.equals(NULL)) {
            return null;
        }
        return LocalDate.parse(str);
    }

    public ComputerDTO toDTO(Computer computer) {
        ComputerDTO cDTO = new ComputerDTO();
        cDTO.setName(computer.getName());
        cDTO.setId(computer.getId());
        cDTO.setIntroduced(dateToString(computer.getIntroduced()));
        cDTO.setDiscontinued(dateToString(computer.getDiscontinued()));
        Company company = computer.getCompany();
        if (company == null) {
            cDTO.setCompanyName(NULL);
            cDTO.setCompanyId(NULL);
        } else {
            cDTO.setCompanyName(computer.getCompany().getName());
            cDTO.setCompanyId(String.valueOf(computer.getCompany().getId()));
        }
        return cDTO;
    }

    public Computer toComputer(ComputerDTO computerDTO) {
        Company company = new Company();
        if (computerDTO.getCompanyId() == NULL) {
            company = null;
        }else {
            company.setId(Long.parseLong(computerDTO.getCompanyId()));
            company.setName(computerDTO.getCompanyName());
        }
        return new Computer(computerDTO.getId(), computerDTO.getName(),
                stringToLocalDate(computerDTO.getIntroduced()), stringToLocalDate(computerDTO.getDiscontinued()),
                company);
    }

    public List<ComputerDTO> toDTOList(List<Computer> computers){
        List<ComputerDTO> computersDTO = new ArrayList <>();
        for (Computer c : computers) {
            computersDTO.add(toDTO(c));
        }
        return computersDTO;
    }

    public List<Computer> toComputerList(List<ComputerDTO> computersDTO){
        List<Computer> computers = new ArrayList <>();
        for (ComputerDTO c : computersDTO) {
            computers.add(toComputer(c));
        }
        return computers;

    }

}