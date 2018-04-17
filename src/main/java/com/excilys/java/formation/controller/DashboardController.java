package com.excilys.java.formation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.java.formation.page.ComputerDTOPage;
import com.excilys.java.formation.page.ComputerPage;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

@Controller
@RequestMapping(value = { "/", "/Dashboard" })
public class DashboardController {

    private static Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private static final String SEARCH = "search";
    private static final String ORDER = "order";
    private static final String BY = "by";
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model, @RequestParam(value = "pageNumber", required = false) String pageNumberStr,
            @RequestParam(value = "pageSize", required = false) String pageSizeStr,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "by", required = false) String by) throws ValidatorException, ServiceException {
        int pageNumber = getUnsignedIntFromParam(pageNumberStr, 1);
        int pageSize = getUnsignedIntFromParam(pageSizeStr, 10);
        if (pageNumber == -1 || pageSize == -1) {
            return ("redirect:static/views/404.jsp");
        }
        ComputerDTOPage computerPage = new ComputerDTOPage(computerService);
        computerPage.getPage(by, order, search, pageNumber, pageSize);
        setAttributes(model, by, order, search, computerPage);
        return "dashboard";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(ModelMap model, @RequestParam(value = "pageNumber", required = false) String pageNumberStr,
            @RequestParam(value = "pageSize", required = false) String pageSizeStr,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "by", required = false) String by) throws ValidatorException, ServiceException {
        String checked = (String) model.get("selection");
        if (checked != null) {
            String[] computerIds = checked.split(",");
            List<Long> ids = new ArrayList<>();
            for (String strId : computerIds) {
                ids.add(Long.parseLong(strId));
            }
            computerService.deleteComputer(ids);
        }
        return doGet(model, pageNumberStr, pageSizeStr, search, order, by);
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