package com.excilys.java.formation.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

@Component
public class ComputerDTOMapper {

    private static final String EMPTY = "";
    private static final Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);
    private CompanyDTOMapper companyDTOMapper;
    private ComputerValidator computerValidator;

    @Autowired
    private ComputerDTOMapper(CompanyDTOMapper companyDTOMapper, ComputerValidator computerValidator) {
        this.companyDTOMapper = companyDTOMapper;
        this.computerValidator = computerValidator;
    }

    /**
     * Maps a Computer object to a ComputerDTO object
     *
     * @param a
     *            Computer
     * @return a ComputerDTO
     */
    public ComputerDTO toDTO(Computer computer) {
        Builder cDTO = new Builder();
        cDTO.withName(computer.getName()).withId(computer.getId())
        .withIntroduced(localDateToString(computer.getIntroduced()))
        .withDiscontinued(localDateToString(computer.getDiscontinued()))
        .withCompany(companyDTOMapper.toDTO(computer.getCompany()));
        logger.debug("Computer {} mapped to ComputerDTO {}", computer, cDTO);
        return cDTO.build();
    }

    /**
     * Maps a ComputerDTO object to a Computer object
     *
     * @param a
     *            ComputerDTO
     * @return a Computer
     */
    public Computer toComputer(ComputerDTO computerDTO) throws ValidatorException {
        logger.debug("ComputerDTO {} mapped to computer", computerDTO);
        Computer computer = new Computer.ComputerBuilder().withId(computerDTO.getId()).withName(computerDTO.getName())
                .withIntroduced(stringToLocalDate(computerDTO.getIntroduced()))
                .withDiscontinued(stringToLocalDate(computerDTO.getDiscontinued()))
                .withCompany(companyDTOMapper.toCompany(computerDTO.getCompany())).build();
        computerValidator.checkDates(computer);
        computerValidator.checkName(computer.getName());
        return computer;
    }

    /**
     * Maps a ComputerDTO list to a Computer list
     *
     * @param a
     *            ComputerDTO list
     * @return a Computer list
     */
    public List<ComputerDTO> toDTOList(List<Computer> computers) {
        List<ComputerDTO> computersDTO = new ArrayList<>();
        for (Computer c : computers) {
            computersDTO.add(toDTO(c));
        }
        return computersDTO;
    }

    /**
     * Maps a Computer list to a ComputerDTO list
     *
     * @param a
     *            Computer list
     * @return a ComputerDTO list
     */
    public List<Computer> toComputerList(List<ComputerDTO> computersDTO) throws ValidatorException {
        List<Computer> computers = new ArrayList<>();
        for (ComputerDTO c : computersDTO) {
            computers.add(toComputer(c));
        }
        return computers;

    }

    public String localDateToString(LocalDate date) {
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

}