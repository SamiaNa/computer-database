package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class EditComptuer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(EditComputer.class);
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/editComputer.jsp");
        logger.info("Edit Computer doGet");
        try {
            String idStr = request.getParameter("id");
            logger.info("Id to parse {}", idStr);
            long id = Long.parseLong(idStr);
            Optional<Computer> optComp = computerService.getComputerById(id);
            if (optComp.isPresent()) {
                List<Company> companyList = companyService.getCompanyList();
                request.setAttribute("companyList", companyList);
                request.setAttribute("computer", ComputerDTOMapper.INSTANCE.toDTO(optComp.get()));
            } else {
                rd = request.getRequestDispatcher("/static/views/404.jsp");
                request.setAttribute("message", "No computer with id " + idStr);
            }
            rd.forward(request, response);
        } catch (ServiceException e) {
            rd = request.getRequestDispatcher("/static/views/500.jsp");
            request.setAttribute("stacktrace", e.getStackTrace());
            rd.forward(request, response);
            return;
        } catch (NumberFormatException e) {
            logger.error("Exception in doGet EditComputer", e);
            response.sendRedirect("static/views/404.jsp");
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/editComputer.jsp");
        try {
            String companyIdStr = request.getParameter("companyId");
            CompanyDTO companyDTO = CompanyDTO.getCompanyDTOFromString(companyIdStr);
            Builder computerDTOBuilder = new Builder();
            computerDTOBuilder.withId(request.getParameter("id")).withName(request.getParameter("name"))
            .withIntroduced(request.getParameter("introduced"))
            .withDiscontinued(request.getParameter("discontinued")).withCompany(companyDTO);
            computerService.createComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
            rd.forward(request, response);
        } catch (ServiceException | NumberFormatException | ValidatorException e) {
            rd = request.getRequestDispatcher("/static/views/404.jsp");
            logger.error("Exception in doPost ", e);
            rd.forward(request, response);
        }

    }

}