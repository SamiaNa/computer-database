package com.excilys.java.formation.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.entities.Company;

@Component
public class CompanyDTOMapper {

    private static final Logger logger = LoggerFactory.getLogger(CompanyDTOMapper.class);


    /**
     * Maps a Company object to a CompanyDTO object
     * @param a Company object
     * @return a CompanyDTO object
     */
    public CompanyDTO toDTO(Company company)   {
        CompanyDTO companyDTO = new CompanyDTO();
        if (company == null) {
            companyDTO = null;
        } else {
            companyDTO.setName(company.getName());
            companyDTO.setId(company.getId());
        }
        logger.debug("Company {}  mapped to CompanyDTO {}", company, companyDTO);
        return companyDTO;
    }

    /**
     * Maps a CompanyDTO object to a Company object
     * @param a CompanyDTO object
     * @return a Company object
     */
    public Company toCompany(CompanyDTO companyDTO)   {
        if (companyDTO == null) {
            return null;
        }
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        logger.debug("CompanyDTO {} mapped to company", companyDTO);
        return company;
    }

    /**
     * Maps a CompanyDTO list to a Company list
     * @param a list of CompanyDTO objects
     * @return a list of Company objects
     */
    public List<CompanyDTO> toDTOList(List<Company> companies)   {
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(toDTO(c));
        }
        return companiesDTO;
    }

    /**
     * Maps a Company list to a CompanyDTO list
     * @param a list of Company objects
     * @return a list of CompanyDTO objects
     */
    public List<Company> toCompanyList(List<CompanyDTO> companiesDTO)   {
        List<Company> companies = new ArrayList<>();
        for (CompanyDTO c : companiesDTO) {
            companies.add(toCompany(c));
        }
        return companies;

    }
}
