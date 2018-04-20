package com.excilys.java.formation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.CompanyDTOMapper;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.page.ComputerDTOPage;
import com.excilys.java.formation.page.ComputerPage;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

@Controller
@Profile("!CLI")
public class ComputerDatabaseController {

    private static Logger logger = LoggerFactory.getLogger(ComputerDatabaseController.class);
    private static final String SEARCH = "search";
    private static final String ORDER = "order";
    private static final String BY = "by";

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerDTOMapper computerDTOMapper;

    @Autowired
    private CompanyDTOMapper companyDTOMapper;

    @GetMapping(value = { "/", "/Dashboard" })
    public String doGetDashboard(Locale locale, ModelMap model,
            @RequestParam(value = "pageNumber", required = false) String pageNumberStr,
            @RequestParam(value = "pageSize", required = false) String pageSizeStr,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "by", required = false) String by) throws ValidatorException, ServiceException {

        int pageNumber = getUnsignedIntFromParam(pageNumberStr, 1);
        int pageSize = getUnsignedIntFromParam(pageSizeStr, 10);
        if (pageNumber == -1 || pageSize == -1) {
            return ("redirect:404.jsp");
        }
        ComputerDTOPage computerPage = new ComputerDTOPage(computerService, computerDTOMapper);
        computerPage.getPage(by, order, search, pageNumber, pageSize);
        setAttributes(model, by, order, search, computerPage);
        return "dashboard";
    }

    @PostMapping(value = { "/", "/Dashboard" })
    public String doPostDashboard(Locale locale, ModelMap model,
            @RequestParam(value = "pageNumber", required = false) String pageNumberStr,
            @RequestParam(value = "pageSize", required = false) String pageSizeStr,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "by", required = false) String by,
            @RequestParam(value = "selection") String checked) throws ValidatorException, ServiceException {
        logger.info("   Checked = {}", checked);
        if (checked != null) {
            String[] computerIds = checked.split(",");
            List<Long> ids = new ArrayList<>();
            for (String strId : computerIds) {
                ids.add(Long.parseLong(strId));
            }
            computerService.deleteComputer(ids);
        }
        return doGetDashboard(locale, model, pageNumberStr, pageSizeStr, search, order, by);
    }

    @GetMapping(value = { "/EditComputer" })
    protected String doGetEdit(ModelMap model, @RequestParam(value = "id") long id,
            @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
        try {
            Optional<Computer> optComp = computerService.getComputerById(id);
            if (optComp.isPresent()) {
                List<Company> companyList = companyService.getCompanyList();
                model.addAttribute("companyList", companyList);
                model.addAttribute("computer", computerDTOMapper.toDTO(optComp.get()));
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

    /*
    @PostMapping(value = { "/EditComputer" })
    protected String doPostEdit(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
        try {
            logger.info("-----------\nEDIT COMPUTER\n---------\n{}", computerDTO);
            computerService.updateComputer(computerDTOMapper.toComputer(computerDTO));
            return "editComputer";
        } catch (NumberFormatException | ValidatorException e) {
            logger.error("Exception in doPost ", e);
            return "404";
        }

    }*/


    @PostMapping(value = {"/EditComputer"})
    protected String doPostEdit(ModelMap model,
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
            computerService.updateComputer(computerDTOMapper.toComputer(computerDTOBuilder.build()));
            return "editComputer";
        } catch (NumberFormatException | ValidatorException e) {
            logger.error("Exception in doPost ", e);
            return "404";
        }

    }

    @GetMapping(value = { "/AddComputer" })
    public String doGet(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
        List<CompanyDTO> companyList = companyDTOMapper.toDTOList(companyService.getCompanyList());
        model.addAttribute("companyList", companyList);
        return "addComputer";
    }

    @PostMapping(value = { "/AddComputer" })
    public String doPost(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
        try {
            computerService.createComputer(computerDTOMapper.toComputer(computerDTO));
        } catch (ValidatorException e) {
            model.addAttribute("res", e.getMessage());
            return "404";
        } catch (ServiceException e) {
            logger.error("Exception in doPost AddCompanyServlet", e);
            return "404";
        }
        return ("addComputer");
    }

    private int getUnsignedIntFromParam(String numberStr, int defaultValue) {
        int number = defaultValue;
        if (numberStr != null) {
            try {
                number = Integer.parseUnsignedInt(numberStr);
            } catch (NumberFormatException e) {
                logger.error("Failed to parse {} as unsigned int", numberStr);
                return -1;
            }
        }
        return number;
    }

    private void setAttributes(ModelMap model, String by, String order, String search, ComputerPage page) {
        model.addAttribute(ORDER, order);
        model.addAttribute(SEARCH, search);
        model.addAttribute(BY, by);
        model.addAttribute("page", page);
    }

}
