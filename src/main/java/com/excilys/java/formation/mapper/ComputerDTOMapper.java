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
import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public enum ComputerDTOMapper {

    INSTANCE;
    private static final String EMPTY = "";
    private static final String NULL = "null";
    private static final Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);
    private static final CompanyValidator companyValidator = CompanyValidator.INSTANCE;
    private static final ComputerValidator computerValidator = ComputerValidator.INSTANCE;

    public String dateToString(LocalDate date) {
        if (date == null) {
            return EMPTY;
        }
        return date.toString();
    }

    public LocalDate stringToLocalDate(String str) {
        if (str.equals(EMPTY)) {
            return null;
        }
        return LocalDate.parse(str);
    }

    public ComputerDTO toDTO(Computer computer) throws ValidatorException {
        Builder cDTO = new Builder();
        cDTO.withName(computer.getName())
        .withId(computer.getId())
        .withIntroduced(dateToString(computer.getIntroduced()))
        .withDiscontinued(dateToString(computer.getDiscontinued()));
        Company company = computer.getCompany();
        if (company == null) {
            cDTO.withCompanyName(EMPTY);
            cDTO.withCompanyId(EMPTY);
        } else {
            cDTO.withCompanyName(computer.getCompany().getName());
            cDTO.withCompanyId(String.valueOf(computer.getCompany().getId()));
        }
        logger.debug("Computer " + computer + " mapped to ComputerDTO " + cDTO);
        return cDTO.build();
    }

    public Computer toComputer(ComputerDTO computerDTO) throws ValidatorException {
        Company company = new Company();
        logger.debug("ComputerDTO"+ computerDTO.getCompanyId());
        if (computerDTO.getCompanyId().equals(EMPTY) || computerDTO.getCompanyId().equals(NULL)) {
            company = null;
        } else {
            companyValidator.checkCompanyIdOrNull(computerDTO.getCompanyId());
            company.setId(Long.parseLong(computerDTO.getCompanyId()));
            company.setName(computerDTO.getCompanyName());
        }
        logger.debug("ComputerDTO " + computerDTO + " mapped to computer");
        Computer computer = new Computer.ComputerBuilder()
                .withId(computerDTO.getId())
                .withName(computerDTO.getName())
                .withIntroduced(stringToLocalDate(computerDTO.getIntroduced()))
                .withDiscontinued(stringToLocalDate(computerDTO.getDiscontinued()))
                .withCompany(company)
                .build();
        computerValidator.checkDates(computer);
        computerValidator.checkName(computer.getName());
        return computer;
    }

    public List<ComputerDTO> toDTOList(List<Computer> computers) throws ValidatorException {
        List<ComputerDTO> computersDTO = new ArrayList<>();
        for (Computer c : computers) {
            computersDTO.add(toDTO(c));
        }
        return computersDTO;
    }

    public List<Computer> toComputerList(List<ComputerDTO> computersDTO) throws ValidatorException {
        List<Computer> computers = new ArrayList<>();
        for (ComputerDTO c : computersDTO) {
            computers.add(toComputer(c));
        }
        return computers;

    }

}