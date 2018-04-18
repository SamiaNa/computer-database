package com.excilys.java.formation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.mapper.CompanyDTOMapper;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

@Controller
@RequestMapping(value = { "/AddComputer" })
public class AddComputerController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {
        List<CompanyDTO> companyList = CompanyDTOMapper.INSTANCE.toDTOList(companyService.getCompanyList());
        model.addAttribute("companyList", companyList);
        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(ModelMap model,
            @RequestParam(value = "companyId") String companyIdStr,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "introduced") String introduced,
            @RequestParam(value = "discontinued") String discontinued) {
        try {
            CompanyDTO companyDTO = CompanyDTO.getCompanyDTOFromString(companyIdStr);
            Builder computerDTOBuilder =
                    new Builder().withName(name)
                    .withIntroduced(introduced)
                    .withDiscontinued(discontinued)
                    .withCompany(companyDTO);
            computerService
            .createComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
        } catch (ValidatorException e) {
            model.addAttribute("res", e.getMessage());
            return "404";
        } catch (ServiceException e) {
            logger.error("Exception in doPost AddCompanyServlet", e);
            return "404";
        }
        return ("addComputer");
    }

}
