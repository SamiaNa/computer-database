package com.excilys.java.formation.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.ConnectionException;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public enum ComputerDTOMapper {

    INSTANCE;
    private static final String NULL = "";
    private static Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);

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

    public ComputerDTO toDTO(Computer computer) throws ValidatorException, DAOException, ConnectionException {
        Builder cDTO = new Builder();
        cDTO.setName(computer.getName())
        .setId(computer.getId())
        .setIntroduced(dateToString(computer.getIntroduced()))
        .setDiscontinued(dateToString(computer.getDiscontinued()));
        Company company = computer.getCompany();
        if (company == null) {
            cDTO.setCompanyName(NULL);
            cDTO.setCompanyId(NULL);
        } else {
            cDTO.setCompanyName(computer.getCompany().getName());
            cDTO.setCompanyId(String.valueOf(computer.getCompany().getId()));
        }
        logger.debug(cDTO.toString());
        logger.debug("Computer "+computer+" mapped to ComputerDTO "+cDTO);
        return cDTO.build();
    }

    public Computer toComputer(ComputerDTO computerDTO) throws ValidatorException, DAOException, ConnectionException {
        Company company = new Company();
        if (computerDTO.getCompanyId() == NULL) {
            company = null;
        }else {
            CompanyValidator.INSTANCE.checkCompanyIdOrNull(computerDTO.getCompanyId());
            company.setId(Long.parseLong(computerDTO.getCompanyId()));
            company.setName(computerDTO.getCompanyName());
        }

        logger.debug("ComputerDTO "+computerDTO+" mapped to computer");
        Computer computer = new Computer(computerDTO.getId(), computerDTO.getName(),
                stringToLocalDate(computerDTO.getIntroduced()), stringToLocalDate(computerDTO.getDiscontinued()),
                company);
        ComputerValidator.INSTANCE.checkDates(computer);
        ComputerValidator.INSTANCE.checkName(computer.getName());
        return computer;
    }

    public List<ComputerDTO> toDTOList(List<Computer> computers) throws ValidatorException, DAOException, ConnectionException{
        List<ComputerDTO> computersDTO = new ArrayList <>();
        for (Computer c : computers) {
            computersDTO.add(toDTO(c));
        }
        return computersDTO;
    }

    public List<Computer> toComputerList(List<ComputerDTO> computersDTO) throws ValidatorException, DAOException, ConnectionException{
        List<Computer> computers = new ArrayList <>();
        for (ComputerDTO c : computersDTO) {
            computers.add(toComputer(c));
        }
        return computers;

    }

}