package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.mapper.CompanyDTOMapper;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class AddCompanyServlet
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AddComputer.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/addComputer.jsp");
        List<CompanyDTO> companyList = CompanyDTOMapper.INSTANCE
                .toDTOList(companyService.getCompanyList());
        request.setAttribute("companyList", companyList);
        rd.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/static/views/addComputer.jsp");
        try {
            CompanyDTO companyDTO = CompanyDTO.getCompanyDTOFromString(request.getParameter("companyId"));
            Builder computerDTOBuilder =
                    new Builder().withName(request.getParameter("name"))
                    .withIntroduced(request.getParameter("introduced"))
                    .withDiscontinued(request.getParameter("discontinued"))
                    .withCompany(companyDTO);
            computerService
            .createComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
        } catch (ValidatorException e) {
            request.setAttribute("res", e.getMessage());
        } catch (ServiceException e) {
            logger.error("Exception in doPost AddCompanyServlet", e);
            throw new ServletException(e);
        }
        rd.forward(request, response);
    }
}