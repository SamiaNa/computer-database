package com.excilys.java.formation.controller;

import java.util.List;
import java.util.Optional;

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
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

@Controller
@RequestMapping(value = { "/EditComputer" })
public class EditComputerController {

    private static final Logger logger = LoggerFactory.getLogger(EditComputerController.class);

    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(ModelMap model, @RequestParam(value = "id") long id)  {
        try {
            Optional<Computer> optComp = computerService.getComputerById(id);
            if (optComp.isPresent()) {
                List<Company> companyList = companyService.getCompanyList();
                model.addAttribute("companyList", companyList);
                model.addAttribute("computer", ComputerDTOMapper.INSTANCE.toDTO(optComp.get()));
            } else {
                model.addAttribute("message", "No computer with id " + id);
                return "404";
            }
            return "editComputer";
        } catch (ServiceException e) {
            model.addAttribute("stacktrace", e.getStackTrace());
            return "500";
        } catch (NumberFormatException e) {
            logger.error("Exception in doGet EditComputer", e);
            return "redirect:404";
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(ModelMap model,
            @RequestParam(value = "companyId") String companyIdStr,
            @RequestParam(value = "id") String computerId,
            @RequestParam(value = "name") String computerName,
            @RequestParam(value = "introduced") String computerIntro,
            @RequestParam(value = "discontinued") String computerDisc
            )
    {
        try {
            CompanyDTO companyDTO = CompanyDTO.getCompanyDTOFromString(companyIdStr);
            Builder computerDTOBuilder = new Builder();
            computerDTOBuilder.withId(computerId).withName(computerName)
            .withIntroduced(computerIntro)
            .withDiscontinued(computerDisc).withCompany(companyDTO);
            logger.info("Call to updateComputer");
            computerService.updateComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
            return "editComputer";
        } catch (NumberFormatException | ValidatorException e) {
            logger.error("Exception in doPost ", e);
            return "404";
        }

    }

}