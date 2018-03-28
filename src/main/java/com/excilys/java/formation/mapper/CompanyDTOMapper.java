package com.excilys.java.formation.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.entities.Company;

public enum CompanyDTOMapper {

    INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(CompanyDTOMapper.class);


    public CompanyDTO toDTO(Company company)   {
        CompanyDTO companyDTO = new CompanyDTO();
        if (company == null) {
            companyDTO = null;
        } else {
            companyDTO.setName(company.getName());
            companyDTO.setId(company.getId());
        }
        logger.debug("Company " + company + " mapped to CompanyDTO " + companyDTO);
        return companyDTO;
    }

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

    public List<CompanyDTO> toDTOList(List<Company> companies)   {
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(toDTO(c));
        }
        return companiesDTO;
    }

    public List<Company> toCompanyList(List<CompanyDTO> companiesDTO)   {
        List<Company> companies = new ArrayList<>();
        for (CompanyDTO c : companiesDTO) {
            companies.add(toCompany(c));
        }
        return companies;

    }
}
