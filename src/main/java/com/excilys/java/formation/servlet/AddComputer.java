package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Computer;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/static/views/addComputer.jsp");
            List<CompanyDTO> companyList = CompanyDTOMapper.INSTANCE
                    .toDTOList(CompanyService.INSTANCE.getCompanyList());
            request.setAttribute("companyList", companyList);
            rd.forward(request, response);
        } catch (ServiceException e) {
            logger.error("Exception in addComputerServlet doGet", e);
            response.sendRedirect("static/views/500.jsp");
        }
    }



    private void setInfoMsg(HttpServletRequest request, Optional<Computer> optComp) {
        if (optComp.isPresent()) {
            request.setAttribute("res", "Computer added ");
        } else {
            request.setAttribute("res", "Error");
        }
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
            Optional<Computer> optComp = ComputerService.INSTANCE
                    .createComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
            setInfoMsg(request, optComp);
        } catch (ValidatorException e) {
            request.setAttribute("res", e.getMessage());
        } catch (ServiceException e) {
            logger.error("Exception in doPost AddCompanyServlet", e);
            throw new ServletException(e);
        }
        rd.forward(request, response);
    }
}